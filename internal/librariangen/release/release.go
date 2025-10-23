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

// Package release contains the implementation of the release-init command.
package release

import (
	"context"
	"log/slog"

	"cloud.google.com/java/internal/librariangen/languagecontainer/release"
	"cloud.google.com/java/internal/librariangen/message"
)

// Init executes the release init command.
func Init(ctx context.Context, cfg *release.Config) (*message.ReleaseInitResponse, error) {
	slog.Info("release-init: invoked", "config", cfg)
	// TODO(suztomo): implement release-init.
	return &message.ReleaseInitResponse{}, nil
}
