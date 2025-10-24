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

package languagecontainer

import (
	"context"
	"encoding/json"
	"os"
	"path/filepath"
	"testing"

	"cloud.google.com/java/internal/librariangen/languagecontainer/release"
	"cloud.google.com/java/internal/librariangen/message"
	"github.com/google/go-cmp/cmp"
)

func TestRun(t *testing.T) {
	tmpDir := t.TempDir()
	if err := os.WriteFile(filepath.Join(tmpDir, "release-init-request.json"), []byte("{}"), 0644); err != nil {
		t.Fatal(err)
	}
	tests := []struct {
		name     string
		args     []string
		wantCode int
		wantErr  bool
	}{
		{
			name:     "unknown command",
			args:     []string{"foo"},
			wantCode: 1,
		},
		{
			name:     "build command",
			args:     []string{"build"},
			wantCode: 1, // Not implemented yet
		},
		{
			name:     "configure command",
			args:     []string{"configure"},
			wantCode: 1, // Not implemented yet
		},
		{
			name:     "generate command",
			args:     []string{"generate"},
			wantCode: 1, // Not implemented yet
		},
		{
			name:     "release-init command success",
			args:     []string{"release-init", "-librarian", tmpDir},
			wantCode: 0,
		},
		{
			name:     "release-init command failure",
			args:     []string{"release-init", "-librarian", tmpDir},
			wantCode: 1,
			wantErr:  true,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			container := LanguageContainer{
				ReleaseInit: func(ctx context.Context, c *release.Config) (*message.ReleaseInitResponse, error) {
					if tt.wantErr {
						return nil, os.ErrNotExist
					}
					return &message.ReleaseInitResponse{}, nil
				},
			}
			if gotCode := Run(tt.args, &container); gotCode != tt.wantCode {
				t.Errorf("Run() = %v, want %v", gotCode, tt.wantCode)
			}
		})
	}
}

func TestRun_noArgs(t *testing.T) {
	defer func() {
		if r := recover(); r == nil {
			t.Errorf("The code did not panic")
		}
	}()
	Run([]string{}, &LanguageContainer{})
}

func TestRun_ReleaseInitWritesResponse(t *testing.T) {
	tmpDir := t.TempDir()
	if err := os.WriteFile(filepath.Join(tmpDir, "release-init-request.json"), []byte("{}"), 0644); err != nil {
		t.Fatal(err)
	}
	args := []string{"release-init", "-librarian", tmpDir}
	want := &message.ReleaseInitResponse{Error: "test error"}
	container := LanguageContainer{
		ReleaseInit: func(ctx context.Context, c *release.Config) (*message.ReleaseInitResponse, error) {
			return want, nil
		},
	}

	if code := Run(args, &container); code != 0 {
		t.Errorf("Run() = %v, want 0", code)
	}

	responsePath := filepath.Join(tmpDir, "release-init-response.json")
	bytes, err := os.ReadFile(responsePath)
	if err != nil {
		t.Fatal(err)
	}
	got := &message.ReleaseInitResponse{}
	if err := json.Unmarshal(bytes, got); err != nil {
		t.Fatal(err)
	}
	if diff := cmp.Diff(want, got); diff != "" {
		t.Errorf("response mismatch (-want +got):\n%s", diff)
	}
}

func TestRun_ReleaseInitReadsContextArgs(t *testing.T) {
	tmpDir := t.TempDir()
	librarianDir := filepath.Join(tmpDir, "librarian")
	if err := os.Mkdir(librarianDir, 0755); err != nil {
		t.Fatal(err)
	}
	if err := os.WriteFile(filepath.Join(librarianDir, "release-init-request.json"), []byte("{}"), 0644); err != nil {
		t.Fatal(err)
	}
	repoDir := filepath.Join(tmpDir, "repo")
	if err := os.Mkdir(repoDir, 0755); err != nil {
		t.Fatal(err)
	}
	outputDir := filepath.Join(tmpDir, "output")
	if err := os.Mkdir(outputDir, 0755); err != nil {
		t.Fatal(err)
	}
	args := []string{"release-init", "-librarian", librarianDir, "-repo", repoDir, "-output", outputDir}
	var gotConfig *release.Config
	container := LanguageContainer{
		ReleaseInit: func(ctx context.Context, c *release.Config) (*message.ReleaseInitResponse, error) {
			gotConfig = c
			return &message.ReleaseInitResponse{}, nil
		},
	}
	if code := Run(args, &container); code != 0 {
		t.Errorf("Run() = %v, want 0", code)
	}
	if got, want := gotConfig.Context.LibrarianDir, librarianDir; got != want {
		t.Errorf("gotConfig.Context.LibrarianDir = %q, want %q", got, want)
	}
	if got, want := gotConfig.Context.RepoDir, repoDir; got != want {
		t.Errorf("gotConfig.Context.RepoDir = %q, want %q", got, want)
	}
	if got, want := gotConfig.Context.OutputDir, outputDir; got != want {
		t.Errorf("gotConfig.Context.OutputDir = %q, want %q", got, want)
	}
}
