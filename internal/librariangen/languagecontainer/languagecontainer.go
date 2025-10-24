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

// Package languagecontainer defines LanguageContainer interface and
// the Run function to execute commands within the container.
// This package should not have any language-specific implementation or
// Librarian CLI's implementation.
// TODO(b/447404382): Move this package to the https://github.com/googleapis/librarian
// GitHub repository once the interface is finalized.
package languagecontainer

import (
	"context"
	"encoding/json"
	"flag"
	"fmt"
	"log/slog"
	"os"
	"path/filepath"

	"cloud.google.com/java/internal/librariangen/languagecontainer/generate"
	"cloud.google.com/java/internal/librariangen/languagecontainer/release"
	"cloud.google.com/java/internal/librariangen/message"
)

// LanguageContainer defines the functions for language-specific container operations.
type LanguageContainer struct {
	Generate    func(context.Context, *generate.Config) error
	ReleaseInit func(context.Context, *release.Config) (*message.ReleaseInitResponse, error)
	// Other container functions like Generate and Build will also be part of the struct.
}

// Run accepts an implementation of the LanguageContainer.
// The args parameter contains the command-line arguments passed to the container,
// without including the program name. Usually it's os.Args[1:].
func Run(args []string, container *LanguageContainer) int {
	// Logic to parse args and call the appropriate method on the container.
	// For example, if args[1] is "generate":
	//   request := ... // unmarshal the request from the expected location
	//   err := container.Generate(context.Background(), request)
	//   ...
	if len(args) < 1 {
		panic("args must not be empty")
	}
	cmd := args[0]
	flags := args[1:]
	switch cmd {
	case "generate":
		if container.Generate == nil {
			slog.Error("languagecontainer: generate command is not implemented")
			return 1
		}
		return handleGenerate(flags, container)
	case "configure":
		slog.Warn("languagecontainer: configure command is missing")
		return 1
	case "release-init":
		if container.ReleaseInit == nil {
			slog.Error("languagecontainer: generate command is missing")
			return 1
		}
		return handleReleaseInit(flags, container)
	case "build":
		slog.Warn("languagecontainer: build command is not yet implemented")
		return 1
	default:
		slog.Error(fmt.Sprintf("languagecontainer: unknown command: %s (with flags %v)", cmd, flags))
		return 1
	}
}

func handleGenerate(flags []string, container *LanguageContainer) int {
	genCtx := &generate.Context{}
	generateFlags := flag.NewFlagSet("generate", flag.ContinueOnError)
	generateFlags.StringVar(&genCtx.LibrarianDir, "librarian", "/librarian", "Path to the librarian-tool input directory. Contains generate-request.json.")
	generateFlags.StringVar(&genCtx.InputDir, "input", "/input", "Path to the .librarian/generator-input directory from the language repository.")
	generateFlags.StringVar(&genCtx.OutputDir, "output", "/output", "Path to the empty directory where librariangen writes its output.")
	generateFlags.StringVar(&genCtx.SourceDir, "source", "/source", "Path to a complete checkout of the googleapis repository.")
	if err := generateFlags.Parse(flags); err != nil {
		slog.Error("failed to parse flags", "error", err)
		return 1
	}
	cfg, err := generate.NewConfig(genCtx)
	if err != nil {
		slog.Error("failed to create generate config", "error", err)
		return 1
	}
	if err := container.Generate(context.Background(), cfg); err != nil {
		slog.Error("generate failed", "error", err)
		return 1
	}
	slog.Info("languagecontainer: generate command executed successfully")
	return 0
}

func handleReleaseInit(flags []string, container *LanguageContainer) int {
	cfg := &release.Context{}
	releaseInitFlags := flag.NewFlagSet("release-init", flag.ContinueOnError)
	releaseInitFlags.StringVar(&cfg.LibrarianDir, "librarian", "/librarian", "Path to the librarian-tool input directory. Contains release-init-request.json.")
	releaseInitFlags.StringVar(&cfg.RepoDir, "repo", "/repo", "Path to the language repo.")
	releaseInitFlags.StringVar(&cfg.OutputDir, "output", "/output", "Path to the output directory.")
	if err := releaseInitFlags.Parse(flags); err != nil {
		slog.Error("failed to parse flags", "error", err)
		return 1
	}
	requestPath := filepath.Join(cfg.LibrarianDir, "release-init-request.json")
	bytes, err := os.ReadFile(requestPath)
	if err != nil {
		slog.Error("failed to read request file", "path", requestPath, "error", err)
		return 1
	}
	request := &message.ReleaseInitRequest{}
	if err := json.Unmarshal(bytes, request); err != nil {
		slog.Error("failed to parse request JSON", "error", err)
		return 1
	}
	config := &release.Config{
		Context: cfg,
		Request: request,
	}
	response, err := container.ReleaseInit(context.Background(), config)
	if err != nil {
		slog.Error("release-init failed", "error", err)
		return 1
	}
	bytes, err = json.MarshalIndent(response, "", "  ")
	if err != nil {
		slog.Error("failed to marshal response JSON", "error", err)
		return 1
	}
	responsePath := filepath.Join(cfg.LibrarianDir, "release-init-response.json")
	if err := os.WriteFile(responsePath, bytes, 0644); err != nil {
		slog.Error("failed to write response file", "path", responsePath, "error", err)
		return 1
	}
	slog.Info("languagecontainer: release-init command executed successfully")
	return 0
}
