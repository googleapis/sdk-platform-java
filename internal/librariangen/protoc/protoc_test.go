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

package protoc

import (
	"path/filepath"
	"strings"
	"testing"

	"github.com/google/go-cmp/cmp"
)

// mockConfigProvider is a mock implementation of the ConfigProvider interface for testing.
type mockConfigProvider struct {
	serviceYAML       string
	gapicYAML         string
	grpcServiceConfig string
	transport         string
	restNumericEnums  bool
	hasGAPIC          bool
}

func (m *mockConfigProvider) ServiceYAML() string       { return m.serviceYAML }
func (m *mockConfigProvider) GapicYAML() string         { return m.gapicYAML }
func (m *mockConfigProvider) GRPCServiceConfig() string { return m.grpcServiceConfig }
func (m *mockConfigProvider) Transport() string         { return m.transport }
func (m *mockConfigProvider) HasRESTNumericEnums() bool { return m.restNumericEnums }
func (m *mockConfigProvider) HasGAPIC() bool            { return m.hasGAPIC }

func TestBuild(t *testing.T) {
	// The testdata directory is a curated version of a valid protoc
	// import path that contains all the necessary proto definitions.
	sourceDir, err := filepath.Abs("../testdata/generate/source")
	if err != nil {
		t.Fatalf("failed to get absolute path for sourceDir: %v", err)
	}
	tests := []struct {
		name    string
		apiPath string
		config  mockConfigProvider
		want    []string
	}{
		{
			name:    "java_grpc_library rule",
			apiPath: "google/cloud/workflows/v1",
			config: mockConfigProvider{
				transport:         "grpc",
				grpcServiceConfig: "workflows_grpc_service_config.json",
				gapicYAML:         "workflows_gapic.yaml",
				serviceYAML:       "workflows_v1.yaml",
				restNumericEnums:  true,
				hasGAPIC:          true,
			},
			want: []string{
				"protoc",
				"--experimental_allow_proto3_optional",
				"--java_out=/output/proto",
				"--java_grpc_out=/output/grpc",
				"--java_gapic_out=metadata:/output/gapic",
				"--java_gapic_opt=" + strings.Join([]string{
					"api-service-config=" + filepath.Join(sourceDir, "google/cloud/workflows/v1/workflows_v1.yaml"),
					"gapic-config=" + filepath.Join(sourceDir, "google/cloud/workflows/v1/workflows_gapic.yaml"),
					"grpc-service-config=" + filepath.Join(sourceDir, "google/cloud/workflows/v1/workflows_grpc_service_config.json"),
					"transport=grpc",
					"rest-numeric-enums",
				}, ","),
				"-I=" + sourceDir,
				filepath.Join(sourceDir, "google/cloud/workflows/v1/workflows.proto"),
			},
		},
		{
			name:    "java_proto_library rule with legacy gRPC",
			apiPath: "google/cloud/secretmanager/v1beta2",
			config: mockConfigProvider{
				transport:         "grpc",
				grpcServiceConfig: "secretmanager_grpc_service_config.json",
				serviceYAML:       "secretmanager_v1beta2.yaml",
				restNumericEnums:  true,
				hasGAPIC:          true,
			},
			want: []string{
				"protoc",
				"--experimental_allow_proto3_optional",
				"--java_out=/output/proto",
				"--java_grpc_out=/output/grpc",
				"--java_gapic_out=metadata:/output/gapic",
				"--java_gapic_opt=" + strings.Join([]string{
					"api-service-config=" + filepath.Join(sourceDir, "google/cloud/secretmanager/v1beta2/secretmanager_v1beta2.yaml"),
					"grpc-service-config=" + filepath.Join(sourceDir, "google/cloud/secretmanager/v1beta2/secretmanager_grpc_service_config.json"),
					"transport=grpc",
					"rest-numeric-enums",
				}, ","),
				"-I=" + sourceDir,
				filepath.Join(sourceDir, "google/cloud/secretmanager/v1beta2/secretmanager.proto"),
			},
		},
		{
			// Note: we don't have a separate test directory with a proto-only library;
			// the config is used to say "don't generate GAPIC".
			name:    "proto-only",
			apiPath: "google/cloud/secretmanager/v1beta2",
			config: mockConfigProvider{
				hasGAPIC: false,
			},
			want: []string{
				"protoc",
				"--experimental_allow_proto3_optional",
				"--java_out=/output/proto",
				"-I=" + sourceDir,
				filepath.Join(sourceDir, "google/cloud/secretmanager/v1beta2/secretmanager.proto"),
			},
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			outputConfig := &OutputConfig{
				GAPICDir: "/output/gapic",
				GRPCDir:  "/output/grpc",
				ProtoDir: "/output/proto",
			}
			got, err := Build(filepath.Join(sourceDir, tt.apiPath), &tt.config, sourceDir, outputConfig)
			if err != nil {
				t.Fatalf("Build() failed: %v", err)
			}

			if diff := cmp.Diff(tt.want, got); diff != "" {
				t.Errorf("Build() mismatch (-want +got):\n%s", diff)
			}
		})
	}
}
