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
	"bytes"
	"context"
	"errors"
	"log/slog"
	"os"
	"path/filepath"
	"strings"
	"testing"
)

// testEnv encapsulates a temporary test environment.
type testEnv struct {
	tmpDir       string
	librarianDir string
	sourceDir    string
	outputDir    string
}

// newTestEnv creates a new test environment.
func newTestEnv(t *testing.T) *testEnv {
	t.Helper()
	tmpDir, err := os.MkdirTemp("", "generator-test")
	if err != nil {
		t.Fatalf("failed to create temp dir: %v", err)
	}
	e := &testEnv{tmpDir: tmpDir}
	e.librarianDir = filepath.Join(tmpDir, "librarian")
	e.sourceDir = filepath.Join(tmpDir, "source")
	e.outputDir = filepath.Join(tmpDir, "output")
	for _, dir := range []string{e.librarianDir, e.sourceDir, e.outputDir} {
		if err := os.Mkdir(dir, 0755); err != nil {
			t.Fatalf("failed to create dir %s: %v", dir, err)
		}
	}
	return e
}

// cleanup removes the temporary directory.
func (e *testEnv) cleanup(t *testing.T) {
	t.Helper()
	if err := os.RemoveAll(e.tmpDir); err != nil {
		t.Fatalf("failed to remove temp dir: %v", err)
	}
}

// writeRequestFile writes a generate-request.json file.
func (e *testEnv) writeRequestFile(t *testing.T, content string) {
	t.Helper()
	p := filepath.Join(e.librarianDir, "generate-request.json")
	if err := os.WriteFile(p, []byte(content), 0644); err != nil {
		t.Fatalf("failed to write request file: %v", err)
	}
}

// writeBazelFile writes a BUILD.bazel file.
func (e *testEnv) writeBazelFile(t *testing.T, apiPath, content string) {
	t.Helper()
	apiDir := filepath.Join(e.sourceDir, apiPath)
	if err := os.MkdirAll(apiDir, 0755); err != nil {
		t.Fatalf("failed to create api dir: %v", err)
	}
	// Create a fake .proto file, which is required by the protoc command builder.
	if err := os.WriteFile(filepath.Join(apiDir, "fake.proto"), nil, 0644); err != nil {
		t.Fatalf("failed to write fake proto file: %v", err)
	}
	p := filepath.Join(apiDir, "BUILD.bazel")
	if err := os.WriteFile(p, []byte(content), 0644); err != nil {
		t.Fatalf("failed to write bazel file: %v", err)
	}
}

// writeServiceYAML writes a service.yaml file.
func (e *testEnv) writeServiceYAML(t *testing.T, apiPath, title string) {
	t.Helper()
	apiDir := filepath.Join(e.sourceDir, apiPath)
	content := "title: " + title
	p := filepath.Join(apiDir, "service.yaml")
	if err := os.WriteFile(p, []byte(content), 0644); err != nil {
		t.Fatalf("failed to write service yaml: %v", err)
	}
}

func createFakeZip(t *testing.T, path string) {
	t.Helper()
	// Create a new zip archive.
	newZipFile, err := os.Create(path)
	if err != nil {
		t.Fatalf("failed to create zip file: %v", err)
	}
	defer newZipFile.Close()

	zipWriter := zip.NewWriter(newZipFile)
	defer zipWriter.Close()

	// Create a temporary empty zip file to be included in the main zip file.
	tmpfile, err := os.CreateTemp("", "temp-zip-*.zip")
	if err != nil {
		t.Fatalf("failed to create temp file: %v", err)
	}
	defer os.Remove(tmpfile.Name())

	tempZipWriter := zip.NewWriter(tmpfile)
	// Add the src/main/java directory to the inner zip file.
	_, err = tempZipWriter.Create("src/main/java/")
	if err != nil {
		t.Fatalf("failed to create directory in zip: %v", err)
	}
	_, err = tempZipWriter.Create("src/test/java/")
	if err != nil {
		t.Fatalf("failed to create directory in zip: %v", err)
	}
	tempZipWriter.Close()

	// Read the content of the temporary zip file.
	zipBytes, err := os.ReadFile(tmpfile.Name())
	if err != nil {
		t.Fatalf("failed to read temp zip file: %v", err)
	}

	// Add the temporary zip file to the main zip file as temp-codegen.srcjar.
	w, err := zipWriter.Create("temp-codegen.srcjar")
	if err != nil {
		t.Fatalf("failed to create empty file in zip: %v", err)
	}
	_, err = w.Write(zipBytes)
	if err != nil {
		t.Fatalf("failed to write content to zip: %v", err)
	}
}

func TestGenerate(t *testing.T) {
	singleAPIRequest := `{"id": "foo", "apis": [{"path": "api/v1"}]}`
	validBazel := `
java_gapic_library(
    name = "v1_gapic",
    grpc_service_config = "service_config.json",
    service_yaml = "service.yaml",
    transport = "grpc",
)
`
	invalidBazel := `
java_gapic_library(
    name = "v1_gapic",
)
`
	tests := []struct {
		name               string
		setup              func(e *testEnv, t *testing.T)
		protocErr          error
		wantErr            bool
		wantProtocRunCount int
	}{
		{
			name: "happy path",
			setup: func(e *testEnv, t *testing.T) {
				e.writeRequestFile(t, singleAPIRequest)
				e.writeBazelFile(t, "api/v1", validBazel)
				e.writeServiceYAML(t, "api/v1", "My API")
			},
			wantErr:            false,
			wantProtocRunCount: 1,
		},
		{
			name: "missing request file",
			setup: func(e *testEnv, t *testing.T) {
				e.writeBazelFile(t, "api/v1", validBazel)
			},
			wantErr: true,
		},
		{
			name: "missing bazel file",
			setup: func(e *testEnv, t *testing.T) {
				e.writeRequestFile(t, singleAPIRequest)
			},
			wantErr: true,
		},
		{
			name: "invalid bazel config",
			setup: func(e *testEnv, t *testing.T) {
				e.writeRequestFile(t, singleAPIRequest)
				e.writeBazelFile(t, "api/v1", invalidBazel)
			},
			wantErr: true,
		},
		{
			name: "protoc fails",
			setup: func(e *testEnv, t *testing.T) {
				e.writeRequestFile(t, singleAPIRequest)
				e.writeBazelFile(t, "api/v1", validBazel)
				e.writeServiceYAML(t, "api/v1", "My API")
			},
			protocErr:          errors.New("protoc failed"),
			wantErr:            true,
			wantProtocRunCount: 1,
		},
		{
			name: "unzip fails",
			setup: func(e *testEnv, t *testing.T) {
				e.writeRequestFile(t, singleAPIRequest)
				e.writeBazelFile(t, "api/v1", validBazel)
				e.writeServiceYAML(t, "api/v1", "My API")
				// Create a corrupt zip file.
				zipPath := filepath.Join(e.outputDir, "java_gapic.zip")
				if err := os.WriteFile(zipPath, []byte("not a zip"), 0644); err != nil {
					t.Fatalf("failed to write corrupt zip file: %v", err)
				}
			},
			wantErr:            true,
			wantProtocRunCount: 1,
		},
		{
			name: "restructureOutput fails",
			setup: func(e *testEnv, t *testing.T) {
				e.writeRequestFile(t, singleAPIRequest)
				e.writeBazelFile(t, "api/v1", validBazel)
				e.writeServiceYAML(t, "api/v1", "My API")
				// Make a directory that restructureOutput needs to write to read-only.
				readOnlyDir := filepath.Join(e.outputDir, "google-cloud-foo")
				if err := os.Mkdir(readOnlyDir, 0400); err != nil {
					t.Fatalf("failed to create read-only dir: %v", err)
				}
			},
			wantErr:            true,
			wantProtocRunCount: 1,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			e := newTestEnv(t)
			defer e.cleanup(t)
			tt.setup(e, t)
			var protocRunCount int
			execvRun = func(ctx context.Context, args []string, dir string) error {
				want := "protoc"
				if args[0] != want {
					t.Errorf("protocRun called with %s; want %s", args[0], want)
				}
				if tt.protocErr == nil && tt.name != "unzip fails" {
					// Simulate protoc creating the zip file.
					createFakeZip(t, filepath.Join(e.outputDir, "java_gapic.zip"))
					// Create the directory that is expected by restructureOutput.
					if err := os.MkdirAll(filepath.Join(e.outputDir, "com"), 0755); err != nil {
						t.Fatalf("failed to create directory: %v", err)
					}
					if err := os.MkdirAll(filepath.Join(e.outputDir, "java_gapic_srcjar", "samples", "snippets"), 0755); err != nil {
						t.Fatalf("failed to create directory: %v", err)
					}
				}
				protocRunCount++
				return tt.protocErr
			}
			cfg := &Config{
				LibrarianDir: e.librarianDir,
				InputDir:     "fake-input",
				OutputDir:    e.outputDir,
				SourceDir:    e.sourceDir,
			}
			if err := Generate(context.Background(), cfg); (err != nil) != tt.wantErr {
				t.Errorf("Generate() error = %v, wantErr %v", err, tt.wantErr)
			}
			if protocRunCount != tt.wantProtocRunCount {
				t.Errorf("protocRun called = %v; want %v", protocRunCount, tt.wantProtocRunCount)
			}
		})
	}
}

func TestConfig_Validate(t *testing.T) {
	tests := []struct {
		name    string
		cfg     *Config
		wantErr bool
	}{
		{
			name: "valid",
			cfg: &Config{
				LibrarianDir: "a",
				InputDir:     "b",
				OutputDir:    "c",
				SourceDir:    "d",
			},
			wantErr: false,
		},
		{
			name: "missing librarian dir",
			cfg: &Config{
				InputDir:  "b",
				OutputDir: "c",
				SourceDir: "d",
			},
			wantErr: true,
		},
		{
			name: "missing input dir",
			cfg: &Config{
				LibrarianDir: "a",
				OutputDir:    "c",
				SourceDir:    "d",
			},
			wantErr: true,
		},
		{
			name: "missing output dir",
			cfg: &Config{
				LibrarianDir: "a",
				InputDir:     "b",
				SourceDir:    "d",
			},
			wantErr: true,
		},
		{
			name: "missing source dir",
			cfg: &Config{
				LibrarianDir: "a",
				InputDir:     "b",
				OutputDir:    "c",
			},
			wantErr: true,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if err := tt.cfg.Validate(); (err != nil) != tt.wantErr {
				t.Errorf("Config.Validate() error = %v, wantErr %v", err, tt.wantErr)
			}
		})
	}
}

func TestRestructureOutput(t *testing.T) {
	e := newTestEnv(t)
	defer e.cleanup(t)
	// Create dummy files and directories to be restructured.
	if err := os.MkdirAll(filepath.Join(e.outputDir, "java_gapic_srcjar", "src", "main", "java", "com"), 0755); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(filepath.Join(e.outputDir, "java_gapic_srcjar", "src", "main", "java", "com", "foo.java"), nil, 0644); err != nil {
		t.Fatal(err)
	}
	if err := os.MkdirAll(filepath.Join(e.outputDir, "java_gapic_srcjar", "src", "test", "java", "com"), 0755); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(filepath.Join(e.outputDir, "java_gapic_srcjar", "src", "test", "java", "com", "foo_test.java"), nil, 0644); err != nil {
		t.Fatal(err)
	}
	if err := os.MkdirAll(filepath.Join(e.outputDir, "com"), 0755); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(filepath.Join(e.outputDir, "com", "bar.proto"), nil, 0644); err != nil {
		t.Fatal(err)
	}
	if err := os.MkdirAll(filepath.Join(e.outputDir, "java_gapic_srcjar", "samples", "snippets", "com"), 0755); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(filepath.Join(e.outputDir, "java_gapic_srcjar", "samples", "snippets", "com", "baz.java"), nil, 0644); err != nil {
		t.Fatal(err)
	}

	if err := restructureOutput(e.outputDir, "my-library"); err != nil {
		t.Fatalf("restructureOutput() failed: %v", err)
	}

	// Check that the files were moved to the correct locations.
	if _, err := os.Stat(filepath.Join(e.outputDir, "google-cloud-my-library", "src", "main", "java", "com", "foo.java")); err != nil {
		t.Errorf("file not moved to main: %v", err)
	}
	if _, err := os.Stat(filepath.Join(e.outputDir, "google-cloud-my-library", "src", "test", "java", "com", "foo_test.java")); err != nil {
		t.Errorf("file not moved to test: %v", err)
	}
	if _, err := os.Stat(filepath.Join(e.outputDir, "proto-google-cloud-my-library-v1", "src", "main", "java", "bar.proto")); err != nil {
		t.Errorf("file not moved to proto: %v", err)
	}
	if _, err := os.Stat(filepath.Join(e.outputDir, "samples", "snippets", "com", "baz.java")); err != nil {
		t.Errorf("file not moved to samples: %v", err)
	}
}

func TestUnzip(t *testing.T) {
	e := newTestEnv(t)
	defer e.cleanup(t)

	t.Run("invalid zip file", func(t *testing.T) {
		invalidZipPath := filepath.Join(e.outputDir, "invalid.zip")
		if err := os.WriteFile(invalidZipPath, []byte("not a zip file"), 0644); err != nil {
			t.Fatalf("failed to write invalid zip file: %v", err)
		}
		if err := unzip(invalidZipPath, e.outputDir); err == nil {
			t.Error("unzip() with invalid zip file should return an error")
		}
	})

	t.Run("permission denied", func(t *testing.T) {
		// Create a valid zip file.
		validZipPath := filepath.Join(e.outputDir, "valid.zip")
		if err := os.WriteFile(validZipPath, []byte{}, 0644); err != nil {
			t.Fatalf("failed to write valid zip file: %v", err)
		}
		// Create a zip writer to add a file to the zip.
		f, err := os.OpenFile(validZipPath, os.O_RDWR, 0)
		if err != nil {
			t.Fatalf("failed to open zip file: %v", err)
		}
		defer f.Close() // Ensure file is closed
		zipWriter := zip.NewWriter(f)
		if _, err := zipWriter.Create("file.txt"); err != nil {
			t.Fatalf("failed to create file in zip: %v", err)
		}
		if err := zipWriter.Close(); err != nil { // Check for errors on close
			t.Fatalf("failed to close zip writer: %v", err)
		}

		// Make the output directory read-only.
		readOnlyDir := filepath.Join(e.tmpDir, "readonly")
		if err := os.Mkdir(readOnlyDir, 0400); err != nil {
			t.Fatalf("failed to create read-only dir: %v", err)
		}
		if err := os.Chmod(readOnlyDir, 0400); err != nil {
			t.Fatalf("failed to chmod read-only dir: %v", err)
		}

		if err := unzip(validZipPath, readOnlyDir); err == nil {
			t.Error("unzip() with read-only destination should return an error")
		}
	})

	t.Run("zip slip vulnerability", func(t *testing.T) {
		// Create a zip file with a malicious file path.
		maliciousZipPath := filepath.Join(e.outputDir, "malicious.zip")
		f, err := os.Create(maliciousZipPath)
		if err != nil {
			t.Fatalf("failed to create malicious zip file: %v", err)
		}
		defer f.Close()
		zipWriter := zip.NewWriter(f)
		if _, err := zipWriter.Create("../../pwned.txt"); err != nil {
			t.Fatalf("failed to create malicious file in zip: %v", err)
		}
		zipWriter.Close()

		destDir := filepath.Join(e.outputDir, "unzip-dest")
		if err := os.Mkdir(destDir, 0755); err != nil {
			t.Fatalf("failed to create unzip dest dir: %v", err)
		}

		if err := unzip(maliciousZipPath, destDir); err == nil {
			t.Error("unzip() with malicious zip file should return an error")
		}

		// Check that the malicious file was not created.
		pwnedFile := filepath.Join(e.tmpDir, "pwned.txt")
		if _, err := os.Stat(pwnedFile); !os.IsNotExist(err) {
			t.Errorf("malicious file was created at %s", pwnedFile)
		}
	})
}

func TestMoveFiles(t *testing.T) {
	e := newTestEnv(t)
	defer e.cleanup(t)

	sourceDir, err := os.MkdirTemp(e.tmpDir, "source-move-test")
	if err != nil {
		t.Fatalf("failed to create temp source dir: %v", err)
	}
	destDir := filepath.Join(e.tmpDir, "dest-move-test")
	if err := os.Mkdir(destDir, 0755); err != nil {
		t.Fatalf("failed to create dest dir: %v", err)
	}
	// Make source dir unreadable.
	if err := os.Chmod(sourceDir, 0000); err != nil {
		t.Fatalf("failed to chmod source dir: %v", err)
	}

	if err := moveFiles(sourceDir, destDir); err == nil {
		t.Error("moveFiles() with unreadable source should return an error")
	}
}

func TestCleanupIntermediateFiles(t *testing.T) {
	var buf bytes.Buffer
	slog.SetDefault(slog.New(slog.NewTextHandler(&buf, nil)))

	e := newTestEnv(t)
	defer e.cleanup(t)

	// Create a file that cannot be deleted.
	protectedDir := filepath.Join(e.outputDir, "com")
	if err := os.Mkdir(protectedDir, 0755); err != nil {
		t.Fatalf("failed to create protected dir: %v", err)
	}
	if _, err := os.Create(filepath.Join(protectedDir, "file.txt")); err != nil {
		t.Fatalf("failed to create file in protected dir: %v", err)
	}
	// Make the directory read-only after creating the file.
	if err := os.Chmod(protectedDir, 0500); err != nil {
		t.Fatalf("failed to chmod protected dir: %v", err)
	}
	defer os.Chmod(protectedDir, 0755) // Restore permissions for cleanup.

	cleanupIntermediateFiles(e.outputDir)

	if !strings.Contains(buf.String(), "failed to clean up intermediate file") {
		t.Errorf("cleanupIntermediateFiles() should log an error on failure, but did not. Log: %s", buf.String())
	}
}
