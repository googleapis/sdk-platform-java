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

package main

import (
	"context"
	"errors"
	"flag"
	"fmt"
	"log/slog"
	"os"
	"strings"

	"cloud.google.com/java/internal/librariangen/generate"
)

const version = "0.1.0"

// main is the entrypoint for the librariangen CLI.
func main() {
	os.Exit(runCLI(os.Args))
}

var (
	generateFunc = generate.Generate
)

func runCLI(args []string) int {
	logLevel := parseLogLevel(os.Getenv("GOOGLE_SDK_JAVA_LOGGING_LEVEL"))
	slog.SetDefault(slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
		Level: logLevel,
	})))
	slog.Info("librariangen: invoked", "args", args)
	if err := run(context.Background(), args[1:]); err != nil {
		slog.Error("librariangen: failed", "error", err)
		return 1
	}
	slog.Info("librariangen: finished successfully")
	return 0
}

func parseLogLevel(logLevelEnv string) slog.Level {
	switch logLevelEnv {
	case "debug":
		return slog.LevelDebug
	case "quiet":
		return slog.LevelError + 1
	default:
		return slog.LevelInfo
	}
}

// run executes the appropriate command based on the CLI's invocation arguments.
// The idiomatic structure is `librariangen [command] [flags]`.
func run(ctx context.Context, args []string) error {
	if len(args) < 1 {
		return errors.New("librariangen: expected a command")
	}

	// The --version flag is a special case and not a command.
	if args[0] == "--version" {
		fmt.Println(version)
		return nil
	}

	cmd := args[0]
	flags := args[1:]

	if strings.HasPrefix(cmd, "-") {
		return fmt.Errorf("librariangen: command cannot be a flag: %s", cmd)
	}

	switch cmd {
	case "generate":
		return handleGenerate(ctx, flags)
	case "release-init":
		slog.Warn("librariangen: release-init command is not yet implemented")
		return nil
	case "configure":
		slog.Warn("librariangen: configure command is not yet implemented")
		return nil
	case "build":
		slog.Warn("librariangen: build command is not yet implemented")
		return nil
	default:
		return fmt.Errorf("librariangen: unknown command: %s (with flags %s)", cmd, flags)
	}

}

// handleGenerate parses flags for the generate command and calls the generator.
func handleGenerate(ctx context.Context, args []string) error {
	cfg := &generate.Config{}
	generateFlags := flag.NewFlagSet("generate", flag.ContinueOnError)
	generateFlags.StringVar(&cfg.LibrarianDir, "librarian", "/librarian", "Path to the librarian-tool input directory. Contains generate-request.json.")
	generateFlags.StringVar(&cfg.InputDir, "input", "/input", "Path to the .librarian/generator-input directory from the language repository.")
	generateFlags.StringVar(&cfg.OutputDir, "output", "/output", "Path to the empty directory where librariangen writes its output.")
	generateFlags.StringVar(&cfg.SourceDir, "source", "/source", "Path to a complete checkout of the googleapis repository.")
	if err := generateFlags.Parse(args); err != nil {
		return fmt.Errorf("librariangen: failed to parse flags: %w", err)
	}
	return generateFunc(ctx, cfg)
}
