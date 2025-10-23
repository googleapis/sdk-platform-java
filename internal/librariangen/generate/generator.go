// Copyright 2025 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package generate

import (
	"archive/zip"
	"context"
	"errors"
	"fmt"
	"io"
	"log/slog"
	"os"
	"path/filepath"
	"strings"

	"cloud.google.com/java/internal/librariangen/bazel"
	"cloud.google.com/java/internal/librariangen/execv"
	"cloud.google.com/java/internal/librariangen/message"
	"cloud.google.com/java/internal/librariangen/protoc"
)

// Test substitution vars.
var (
	bazelParse   = bazel.Parse
	execvRun     = execv.Run
	requestParse = message.ParseLibrary
	protocBuild  = protoc.Build
)

// Config holds the internal librariangen configuration for the generate command.
type Config struct {
	// LibrarianDir is the path to the librarian-tool input directory.
	// It is expected to contain the generate-request.json file.
	LibrarianDir string
	// InputDir is the path to the .librarian/generator-input directory from the
	// language repository.
	InputDir string
	// OutputDir is the path to the empty directory where librariangen writes
	// its output.
	OutputDir string
	// SourceDir is the path to a complete checkout of the googleapis repository.
	SourceDir string
}

// Validate ensures that the configuration is valid.
func (c *Config) Validate() error {
	if c.LibrarianDir == "" {
		return errors.New("librariangen: librarian directory must be set")
	}
	if c.InputDir == "" {
		return errors.New("librariangen: input directory must be set")
	}
	if c.OutputDir == "" {
		return errors.New("librariangen: output directory must be set")
	}
	if c.SourceDir == "" {
		return errors.New("librariangen: source directory must be set")
	}
	return nil
}

// Generate is the main entrypoint for the `generate` command. It orchestrates
// the entire generation process.
func Generate(ctx context.Context, cfg *Config) error {
	if err := cfg.Validate(); err != nil {
		return fmt.Errorf("librariangen: invalid configuration: %w", err)
	}
	slog.Debug("librariangen: generate command started")
	outputConfig := &protoc.OutputConfig{
		GAPICDir: filepath.Join(cfg.OutputDir, "gapic"),
		GRPCDir:  filepath.Join(cfg.OutputDir, "grpc"),
		ProtoDir: filepath.Join(cfg.OutputDir, "proto"),
	}
	defer func() {
		if err := cleanupIntermediateFiles(outputConfig); err != nil {
			slog.Error("librariangen: failed to clean up intermediate files", "error", err)
		}
	}()

	generateReq, err := readGenerateReq(cfg.LibrarianDir)
	if err != nil {
		return fmt.Errorf("librariangen: failed to read request: %w", err)
	}

	if err := invokeProtoc(ctx, cfg, generateReq, outputConfig); err != nil {
		return fmt.Errorf("librariangen: gapic generation failed: %w", err)
	}

	// Unzip the temp-codegen.srcjar.
	srcjarPath := filepath.Join(outputConfig.GAPICDir, "temp-codegen.srcjar")
	srcjarDest := outputConfig.GAPICDir
	if err := unzip(srcjarPath, srcjarDest); err != nil {
		return fmt.Errorf("librariangen: failed to unzip %s: %w", srcjarPath, err)
	}

	if err := restructureOutput(cfg.OutputDir, generateReq.ID); err != nil {
		return fmt.Errorf("librariangen: failed to restructure output: %w", err)
	}

	slog.Debug("librariangen: generate command finished")
	return nil
}

// invokeProtoc handles the protoc GAPIC generation logic for the 'generate' CLI command.
// It reads a request file, and for each API specified, it invokes protoc
// to generate the client library. It returns the module path and the path to the service YAML.
func invokeProtoc(ctx context.Context, cfg *Config, generateReq *message.Library, outputConfig *protoc.OutputConfig) error {
	for _, api := range generateReq.APIs {
		apiServiceDir := filepath.Join(cfg.SourceDir, api.Path)
		slog.Info("processing api", "service_dir", apiServiceDir)
		bazelConfig, err := bazelParse(apiServiceDir)
		if err != nil {
			return fmt.Errorf("librariangen: failed to parse BUILD.bazel for %s: %w", apiServiceDir, err)
		}
		args, err := protocBuild(apiServiceDir, bazelConfig, cfg.SourceDir, outputConfig)
		if err != nil {
			return fmt.Errorf("librariangen: failed to build protoc command for api %q in library %q: %w", api.Path, generateReq.ID, err)
		}

		// Create protoc output directories.
		for _, dir := range []string{outputConfig.ProtoDir, outputConfig.GRPCDir, outputConfig.GAPICDir} {
			if err := os.MkdirAll(dir, 0755); err != nil {
				return err
			}
		}

		if err := execvRun(ctx, args, cfg.OutputDir); err != nil {
			return fmt.Errorf("librariangen: protoc failed for api %q in library %q: %w, execvRun error: %v", api.Path, generateReq.ID, err, err)
		}
	}
	return nil
}

// readGenerateReq reads generate-request.json from the librarian-tool input directory.
// The request file tells librariangen which library and APIs to generate.
// It is prepared by the Librarian tool and mounted at /librarian.
func readGenerateReq(librarianDir string) (*message.Library, error) {
	reqPath := filepath.Join(librarianDir, "generate-request.json")
	slog.Debug("librariangen: reading generate request", "path", reqPath)

	generateReq, err := requestParse(reqPath)
	if err != nil {
		return nil, err
	}
	slog.Debug("librariangen: successfully unmarshalled request", "library_id", generateReq.ID)
	return generateReq, nil
}

// moveFiles moves all files (and directories) from sourceDir to targetDir.
func moveFiles(sourceDir, targetDir string) error {
	files, err := os.ReadDir(sourceDir)
	if err != nil {
		return fmt.Errorf("librariangen: failed to read dir %s: %w", sourceDir, err)
	}
	for _, f := range files {
		oldPath := filepath.Join(sourceDir, f.Name())
		newPath := filepath.Join(targetDir, f.Name())
		slog.Debug("librariangen: moving file", "from", oldPath, "to", newPath)
		if err := os.Rename(oldPath, newPath); err != nil {
			return fmt.Errorf("librariangen: failed to move %s to %s: %w, os.Rename error: %v", oldPath, newPath, err, err)
		}
	}
	return nil
}

func restructureOutput(outputDir, libraryID string) error {
	slog.Debug("librariangen: restructuring output directory", "dir", outputDir)

	// Define source and destination directories.
	gapicSrcDir := filepath.Join(outputDir, "gapic", "src", "main", "java")
	gapicTestDir := filepath.Join(outputDir, "gapic", "src", "test", "java")
	protoSrcDir := filepath.Join(outputDir, "proto")
	resourceNameSrcDir := filepath.Join(outputDir, "gapic", "proto", "src", "main", "java")
	grpcSrcDir := filepath.Join(outputDir, "grpc")
	samplesDir := filepath.Join(outputDir, "gapic", "samples", "snippets")

	// TODO(meltsufin): currently we assume we have a single API variant v1
	gapicDestDir := filepath.Join(outputDir, fmt.Sprintf("google-cloud-%s", libraryID), "src", "main", "java")
	gapicTestDestDir := filepath.Join(outputDir, fmt.Sprintf("google-cloud-%s", libraryID), "src", "test", "java")
	protoDestDir := filepath.Join(outputDir, fmt.Sprintf("proto-google-cloud-%s-v1", libraryID), "src", "main", "java")
	resourceNameDestDir := filepath.Join(outputDir, fmt.Sprintf("proto-google-cloud-%s-v1", libraryID), "src", "main", "java")
	grpcDestDir := filepath.Join(outputDir, fmt.Sprintf("grpc-google-cloud-%s-v1", libraryID), "src", "main", "java")
	samplesDestDir := filepath.Join(outputDir, "samples", "snippets")

	// Create destination directories.
	destDirs := []string{gapicDestDir, gapicTestDestDir, protoDestDir, samplesDestDir, grpcDestDir}
	for _, dir := range destDirs {
		if err := os.MkdirAll(dir, 0755); err != nil {
			return err
		}
	}

	// The resource name directory is not created if there are no resource names
	// to generate. We create it here to avoid errors later.
	if _, err := os.Stat(resourceNameSrcDir); os.IsNotExist(err) {
		if err := os.MkdirAll(resourceNameSrcDir, 0755); err != nil {
			return err
		}
	}

	// Move files that won't have conflicts.
	moves := map[string]string{
		gapicSrcDir:  gapicDestDir,
		gapicTestDir: gapicTestDestDir,
		protoSrcDir:  protoDestDir,
		grpcSrcDir:   grpcDestDir,
		samplesDir:   samplesDestDir,
	}
	for src, dest := range moves {
		if err := moveFiles(src, dest); err != nil {
			return err
		}
	}

	// Merge the resource name files into the proto destination.
	if err := copyAndMerge(resourceNameSrcDir, resourceNameDestDir); err != nil {
		return err
	}

	return nil
}

// copyAndMerge recursively copies the contents of src to dest, merging directories.
func copyAndMerge(src, dest string) error {
	entries, err := os.ReadDir(src)
	if os.IsNotExist(err) {
		return nil
	}
	if err != nil {
		return err
	}

	for _, entry := range entries {
		srcPath := filepath.Join(src, entry.Name())
		destPath := filepath.Join(dest, entry.Name())
		if entry.IsDir() {
			if err := os.MkdirAll(destPath, 0755); err != nil {
				return err
			}
			if err := copyAndMerge(srcPath, destPath); err != nil {
				return err
			}
		} else {
			if err := os.Rename(srcPath, destPath); err != nil {
				return fmt.Errorf("librariangen: failed to move %s to %s: %w, os.Rename error: %v", srcPath, destPath, err, err)
			}
		}
	}
	return nil
}

func cleanupIntermediateFiles(outputConfig *protoc.OutputConfig) error {
	slog.Debug("librariangen: cleaning up intermediate files")
	for _, path := range []string{outputConfig.GAPICDir, outputConfig.GRPCDir, outputConfig.ProtoDir} {
		if err := os.RemoveAll(path); err != nil {
			return fmt.Errorf("failed to clean up intermediate file at %s: %w", path, err)
		}
	}
	return nil
}

func unzip(src, dest string) error {
	r, err := zip.OpenReader(src)
	if err != nil {
		return err
	}
	defer r.Close()

	for _, f := range r.File {
		fpath := filepath.Join(dest, f.Name)

		if !strings.HasPrefix(fpath, filepath.Clean(dest)+string(os.PathSeparator)) {
			return fmt.Errorf("librariangen: illegal file path: %s", fpath)
		}

		if f.FileInfo().IsDir() {
			os.MkdirAll(fpath, os.ModePerm)
			continue
		}

		if err := os.MkdirAll(filepath.Dir(fpath), os.ModePerm); err != nil {
			return err
		}

		outFile, err := os.OpenFile(fpath, os.O_WRONLY|os.O_CREATE|os.O_TRUNC, f.Mode())
		if err != nil {
			return err
		}

		rc, err := f.Open()
		if err != nil {
			outFile.Close()
			return err
		}

		_, copyErr := io.Copy(outFile, rc)
		rc.Close() // Error on read-only file close is less critical
		closeErr := outFile.Close()

		if copyErr != nil {
			return copyErr
		}
		if closeErr != nil {
			return closeErr
		}
	}
	return nil
}
