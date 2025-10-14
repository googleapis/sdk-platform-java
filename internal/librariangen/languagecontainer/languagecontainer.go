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
// TODO(b/447404382): Move this package to the https://github.com/googleapis/librarian
// GitHub repository once the interface is finalized.
// This package must not have any Java-specific implementation.
package languagecontainer

import (
	"context"
	"log/slog"

	"cloud.google.com/java/internal/librariangen/message"
)

// GenerateCommandContext holds the context (the file system paths) for
// the generate command. The createGenerateContext function creates
// an instance of this by reading the command line flags and the
// default values.
type GenerateCommandContext struct {
}
type GenerateCommandEnv struct {
	GenerateContext *GenerateCommandContext
	GenerateRequest *message.GenerateRequest
}

type ConfigureCommandContext struct {
}

type ConfigureCommandEnv struct {
	ConfigureContext *ConfigureCommandContext
	ConfigureRequest *message.ConfigureRequest
}

// LanguageContainer defines the interface for language-specific container operations.
type LanguageContainer interface {
	Generate(context.Context, *GenerateCommandEnv) error
	Configure(context.Context, *ConfigureCommandEnv) (*message.ConfigureResponse, error)
	// Other container functions like ReleaseInit and Build would also be part of the interface.
}

// Run would accept an implementation of the LanguageContainer interface.
func Run(args []string, container LanguageContainer) int {
	// Logic to parse args and call the appropriate method on the container.
	// For example, if args[1] is "generate":
	//   request := ... // unmarshal the request from the expected location
	//   err := container.Generate(context.Background(), request)
	//   ...
	if len(args) < 1 {
		panic("args must not be empty")
	}
	switch args[0] {
	case "generate":
		env := createGenerateEnv(args[1:])
		err := container.Generate(context.Background(), env)
		if err != nil {
			// TODO: Save it as a response file.
			panic(err)
		}
		return 0
	case "configure":
		env := createConfigureEnv(args[1:])
		resp, err := container.Configure(context.Background(), env)
		if err != nil {
			panic(err)
		}
		// TODO: Save it as a response file.
		_ = resp
		return 0
	case "release-init":
		slog.Warn("librariangen: release-init command is not yet implemented")
		return 1
	case "build":
		slog.Warn("librariangen: build command is not yet implemented")
		return 1
	default:
		slog.Error("librariangen: unknown command: %s (with flags %v)", args[0], args)
		return 1
	}
	return 0
}

// https://github.com/googleapis/librarian/blob/main/doc/language-onboarding.md#generate
func createGenerateEnv(args []string) *GenerateCommandEnv {
	generateContext := createGenerateCommandContext(args)
	return &GenerateCommandEnv{
		GenerateContext: generateContext,
	}
}

func createGenerateCommandContext(args []string) *GenerateCommandContext {
	// TODO: Parse args and create the context.
	return &GenerateCommandContext{}
}

func createConfigureCommandContext(args []string) *ConfigureCommandContext {
	// TODO: Parse args and create the context.
	return &ConfigureCommandContext{}
}

func createConfigureEnv(args []string) *ConfigureCommandEnv {
	configureContext := createConfigureCommandContext(args)
	return &ConfigureCommandEnv{
		ConfigureContext: configureContext,
	}
}
