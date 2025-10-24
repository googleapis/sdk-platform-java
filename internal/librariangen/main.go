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
	"fmt"
	"log/slog"
	"os"
	"strings"
)

const version = "0.1.0"

// main is the entrypoint for the librariangen CLI.
func main() {
	os.Exit(runCLI(os.Args))
}

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
		slog.Warn("librariangen: generate command is not yet implemented")
		return nil
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
