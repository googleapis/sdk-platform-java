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
	"fmt"
	"log/slog"
	"os"

	"cloud.google.com/java/internal/librariangen/generate"
	"cloud.google.com/java/internal/librariangen/languagecontainer"
	"cloud.google.com/java/internal/librariangen/release"
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
	if len(args) < 2 {
		slog.Error("librariangen: expected a command")
		return 1
	}

	// The --version flag is a special case and not a command.
	if args[1] == "--version" {
		fmt.Println(version)
		return 0
	}

	container := languagecontainer.LanguageContainer{
		Generate:    generate.Generate,
		ReleaseInit: release.Init,
	}
	return languagecontainer.Run(args[1:], &container)
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
