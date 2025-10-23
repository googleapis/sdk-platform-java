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
	"path/filepath"
	"testing"

	"cloud.google.com/java/internal/librariangen/message"
	"github.com/google/go-cmp/cmp"
)

func TestNewConfig(t *testing.T) {
	// This reads generate-request.json from testdata.
	librarianDir := filepath.Join("..", "testdata")
	want := &Config{
		Context: &Context{
			LibrarianDir: librarianDir,
			InputDir:     "in",
			OutputDir:    "out",
			SourceDir:    "source",
		},
		Request: &message.Library{
			ID:      "chronicle",
			Version: "0.1.1",
			APIs: []message.API{
				{
					Path:          "google/cloud/chronicle/v1",
					ServiceConfig: "chronicle_v1.yaml",
				},
			},
			SourcePaths: []string{
				"chronicle",
				"internal/generated/snippets/chronicle",
			},
			PreserveRegex: []string{
				"chronicle/aliasshim/aliasshim.go",
				"chronicle/CHANGES.md",
			},
			RemoveRegex: []string{
				"chronicle",
				"internal/generated/snippets/chronicle",
			},
		},
	}

	ctx := &Context{
		LibrarianDir: librarianDir,
		InputDir:     "in",
		OutputDir:    "out",
		SourceDir:    "source",
	}
	got, err := NewConfig(ctx)
	if err != nil {
		t.Fatal(err)
	}

	if diff := cmp.Diff(want, got); diff != "" {
		t.Errorf("NewConfig() mismatch (-want +got):\n%s", diff)
	}
}

func TestNewConfig_validate(t *testing.T) {
	tests := []struct {
		name    string
		context *Context
	}{
		{
			name:    "empty librarian dir",
			context: &Context{},
		},
		{
			name: "empty input dir",
			context: &Context{
				LibrarianDir: "librarian",
			},
		},
		{
			name: "empty output dir",
			context: &Context{
				LibrarianDir: "librarian",
				InputDir:     "in",
			},
		},
		{
			name: "empty source dir",
			context: &Context{
				LibrarianDir: "librarian",
				InputDir:     "in",
				OutputDir:    "out",
			},
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if _, err := NewConfig(tt.context); err == nil {
				t.Error("NewConfig() error = nil, want not nil")
			}
		})
	}
}
