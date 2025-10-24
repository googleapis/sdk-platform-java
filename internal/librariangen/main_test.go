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
	"log/slog"
	"testing"
)

func TestRunCLI(t *testing.T) {
	tests := []struct {
		name     string
		args     []string
		wantCode int
	}{
		{
			name:     "success",
			args:     []string{"librariangen", "--version"},
			wantCode: 0,
		},
		{
			name:     "failure",
			args:     []string{"librariangen", "foo"},
			wantCode: 1,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if gotCode := runCLI(tt.args); gotCode != tt.wantCode {
				t.Errorf("runCLI() = %v, want %v", gotCode, tt.wantCode)
			}
		})
	}
}

func TestParseLogLevel(t *testing.T) {
	tests := []struct {
		name  string
		level string
		want  slog.Level
	}{
		{"default", "", slog.LevelInfo},
		{"debug", "debug", slog.LevelDebug},
		{"quiet", "quiet", slog.LevelError + 1},
		{"invalid", "foo", slog.LevelInfo},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := parseLogLevel(tt.level); got != tt.want {
				t.Errorf("parseLogLevel() = %v, want %v", got, tt.want)
			}
		})
	}
}