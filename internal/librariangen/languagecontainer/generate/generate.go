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

// Package generate contains types for language container's generate command.
package generate

import (
	"errors"
	"log/slog"
	"path/filepath"

	"cloud.google.com/java/internal/librariangen/message"
)

// Context holds the directory paths for the generate command.
// https://github.com/googleapis/librarian/blob/main/doc/language-onboarding.md#generate
type Context struct {
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

// Validate ensures that the context is valid.
func (c *Context) Validate() error {
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

// Config for the generate command. This holds the context (the directory paths)
// and the request parsed from the generate-request.json file.
type Config struct {
	Context *Context
	// This request is parsed from the generate-request.json file in
	// the LibrarianDir of the context.
	Request *message.Library
}

// NewConfig creates a new Config, parsing the generate-request.json file
// from the LibrarianDir in the given Context.
func NewConfig(ctx *Context) (*Config, error) {
	reqPath := filepath.Join(ctx.LibrarianDir, "generate-request.json")
	slog.Debug("librariangen: reading generate request", "path", reqPath)

	generateReq, err := message.ParseLibrary(reqPath)
	if err != nil {
		return nil, err
	}
	slog.Debug("librariangen: successfully unmarshalled request", "library_id", generateReq.ID)
	return &Config{
		Context: ctx,
		Request: generateReq,
	}, nil
}

