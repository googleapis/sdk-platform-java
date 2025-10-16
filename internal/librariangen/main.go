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
	"log/slog"
	"os"

	"cloud.google.com/java/internal/librariangen/languagecontainer"
	"cloud.google.com/java/internal/librariangen/message"
)

const version = "0.1.0"

// javaContainer implements the LanguageContainer interface for Java.
type javaContainer struct{}

func (c *javaContainer) Generate(context.Context, *languagecontainer.GenerateCommandEnv) error {
	// Java-specific implementation for the "generate" command.
	slog.Warn("librariangen: generate command is not yet implemented")

	return nil
}

func (c *javaContainer) Configure(ctx context.Context,
	request *languagecontainer.ConfigureCommandEnv) (*message.ConfigureResponse, error) {
	// Java-specific implementation for the "configure" command.
	slog.Warn("librariangen: configure command is not yet implemented")
	return nil, nil
}

// main is the entrypoint for the librariangen CLI.
func main() {
	logLevel := parseLogLevel(os.Getenv("GOOGLE_SDK_JAVA_LOGGING_LEVEL"))
	slog.SetDefault(slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
		Level: logLevel,
	})))
	args := os.Args
	slog.Info("librariangen: invoked", "args", args)
	container := javaContainer{}
	os.Exit(languagecontainer.Run(os.Args, &container))
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
