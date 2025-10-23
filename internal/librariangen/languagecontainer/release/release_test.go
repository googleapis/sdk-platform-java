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

package release

import (
	"encoding/json"
	"os"
	"path/filepath"
	"testing"

	"cloud.google.com/java/internal/librariangen/message"
	"github.com/google/go-cmp/cmp"
)

func TestReadReleaseInitRequest(t *testing.T) {
	want := &message.ReleaseInitRequest{
		Libraries: []*message.Library{
			{
				ID:      "google-cloud-secretmanager-v1",
				Version: "1.3.0",
				Changes: []*message.Change{
					{
						Type:          "feat",
						Subject:       "add new UpdateRepository API",
						Body:          "This adds the ability to update a repository's properties.",
						PiperCLNumber: "786353207",
						CommitHash: "9461532e7d19c8d71709ec3b502e5d81340fb661",
					},
					{
						Type:          "docs",
						Subject:       "fix typo in BranchRule comment",
						Body:          "",
						PiperCLNumber: "786353207",
						CommitHash: "9461532e7d19c8d71709ec3b502e5d81340fb661",
					},
				},
				APIs: []message.API{
					{
						Path: "google/cloud/secretmanager/v1",
					},
					{
						Path: "google/cloud/secretmanager/v1beta",
					},
				},
				SourcePaths: []string{
					"secretmanager",
					"other/location/secretmanager",
				},
				ReleaseTriggered: true,
			},
		},
	}
	bytes, err := os.ReadFile(filepath.Join("..", "testdata", "release-init-request.json"))
	if err != nil {
		t.Fatal(err)
	}
	got := &message.ReleaseInitRequest{}
	if err := json.Unmarshal(bytes, got); err != nil {
		t.Fatal(err)
	}
	if diff := cmp.Diff(want, got); diff != "" {
		t.Errorf("Unmarshal() mismatch (-want +got):\n%s", diff)
	}
}
