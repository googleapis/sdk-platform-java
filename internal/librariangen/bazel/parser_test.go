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

package bazel

import (
	"os"
	"path/filepath"
	"testing"
)

func TestParse(t *testing.T) {
	content := `
java_grpc_library(
    name = "asset_java_grpc",
    srcs = [":asset_proto"],
    deps = [":asset_java_proto"],
)

java_gapic_library(
    name = "asset_java_gapic",
    srcs = [":asset_proto_with_info"],
    gapic_yaml = "cloudasset_gapic.yaml",
    grpc_service_config = "cloudasset_grpc_service_config.json",
    rest_numeric_enums = True,
    service_yaml = "cloudasset_v1.yaml",
    test_deps = [
        ":asset_java_grpc",
        "//google/iam/v1:iam_java_grpc",
    ],
    transport = 'grpc+rest',
    deps = [
        ":asset_java_proto",
        "//google/api:api_java_proto",
        "//google/iam/v1:iam_java_proto",
    ],
)
`
	tmpDir := t.TempDir()
	buildPath := filepath.Join(tmpDir, "BUILD.bazel")
	if err := os.WriteFile(buildPath, []byte(content), 0644); err != nil {
		t.Fatalf("failed to write test file: %v", err)
	}

	got, err := Parse(tmpDir)
	if err != nil {
		t.Fatalf("Parse() failed: %v", err)
	}

	t.Run("HasGAPIC", func(t *testing.T) {
		if !got.HasGAPIC() {
			t.Error("HasGAPIC() = false; want true")
		}
	})
	t.Run("ServiceYAML", func(t *testing.T) {
		if want := "cloudasset_v1.yaml"; got.ServiceYAML() != want {
			t.Errorf("ServiceYAML() = %q; want %q", got.ServiceYAML(), want)
		}
	})
	t.Run("GapicYAML", func(t *testing.T) {
		if want := "cloudasset_gapic.yaml"; got.GapicYAML() != want {
			t.Errorf("GapicYAML() = %q; want %q", got.GapicYAML(), want)
		}
	})
	t.Run("GRPCServiceConfig", func(t *testing.T) {
		if want := "cloudasset_grpc_service_config.json"; got.GRPCServiceConfig() != want {
			t.Errorf("GRPCServiceConfig() = %q; want %q", got.GRPCServiceConfig(), want)
		}
	})
	t.Run("Transport", func(t *testing.T) {
		if want := "grpc+rest"; got.Transport() != want {
			t.Errorf("Transport() = %q; want %q", got.Transport(), want)
		}
	})
	t.Run("HasRESTNumericEnums", func(t *testing.T) {
		if !got.HasRESTNumericEnums() {
			t.Error("HasRESTNumericEnums() = false; want true")
		}
	})
}

func TestParse_configIsTarget(t *testing.T) {
	content := `
java_grpc_library(
    name = "asset_java_grpc",
    srcs = [":asset_proto"],
    deps = [":asset_java_proto"],
)

java_gapic_library(
    name = "asset_java_gapic",
    srcs = [":asset_proto_with_info"],
    gapic_yaml = ":cloudasset_gapic.yaml",
    grpc_service_config = "cloudasset_grpc_service_config.json",
    rest_numeric_enums = True,
    service_yaml = ":cloudasset_v1.yaml",
    test_deps = [
        ":asset_java_grpc",
        "//google/iam/v1:iam_java_grpc",
    ],
    transport = "grpc+rest",
    deps = [
        ":asset_java_proto",
        "//google/api:api_java_proto",
        "//google/iam/v1:iam_java_proto",
    ],
)
`
	tmpDir := t.TempDir()
	buildPath := filepath.Join(tmpDir, "BUILD.bazel")
	if err := os.WriteFile(buildPath, []byte(content), 0644); err != nil {
		t.Fatalf("failed to write test file: %v", err)
	}

	got, err := Parse(tmpDir)
	if err != nil {
		t.Fatalf("Parse() failed: %v", err)
	}

	if want := "cloudasset_v1.yaml"; got.ServiceYAML() != want {
		t.Errorf("ServiceYAML() = %q; want %q", got.ServiceYAML(), want)
	}
	if want := "cloudasset_gapic.yaml"; got.GapicYAML() != want {
		t.Errorf("GapicYAML() = %q; want %q", got.GapicYAML(), want)
	}
}

func TestConfig_Validate(t *testing.T) {
	tests := []struct {
		name    string
		cfg     *Config
		wantErr bool
	}{
		{
			name: "valid GAPIC",
			cfg: &Config{
				hasGAPIC:          true,
				gapicYAML:         "a",
				serviceYAML:       "b",
				grpcServiceConfig: "c",
				transport:         "d",
			},
			wantErr: false,
		},
		{
			name:    "valid non-GAPIC",
			cfg:     &Config{},
			wantErr: false,
		},
		{
			name:    "gRPC service config and transport are optional",
			cfg:     &Config{hasGAPIC: true, serviceYAML: "b", gapicYAML: "a"},
			wantErr: false,
		},
		{
			name:    "missing serviceYAML",
			cfg:     &Config{hasGAPIC: true, grpcServiceConfig: "c", transport: "d"},
			wantErr: true,
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if err := tt.cfg.Validate(); (err != nil) != tt.wantErr {
				t.Errorf("Config.Validate() error = %v, wantErr %v", err, tt.wantErr)
			}
		})
	}
}

func TestParse_noGapic(t *testing.T) {
	content := `
java_grpc_library(
    name = "asset_java_grpc",
    srcs = [":asset_proto"],
    deps = [":asset_java_proto"],
)
`
	tmpDir := t.TempDir()
	buildPath := filepath.Join(tmpDir, "BUILD.bazel")
	if err := os.WriteFile(buildPath, []byte(content), 0644); err != nil {
		t.Fatalf("failed to write test file: %v", err)
	}

	got, err := Parse(tmpDir)
	if err != nil {
		t.Fatalf("Parse() failed: %v", err)
	}

	if got.HasGAPIC() {
		t.Error("HasGAPIC() = true; want false")
	}
}

func TestParse_missingSomeAttrs(t *testing.T) {
	content := `
java_gapic_library(
    name = "asset_java_gapic",
    service_yaml = "cloudasset_v1.yaml",
)
`
	tmpDir := t.TempDir()
	buildPath := filepath.Join(tmpDir, "BUILD.bazel")
	if err := os.WriteFile(buildPath, []byte(content), 0644); err != nil {
		t.Fatalf("failed to write test file: %v", err)
	}

	got, err := Parse(tmpDir)
	if err != nil {
		t.Fatalf("Parse() failed: %v", err)
	}

	if got.GRPCServiceConfig() != "" {
		t.Errorf("GRPCServiceConfig() = %q; want \"\"", got.GRPCServiceConfig())
	}
	if got.Transport() != "" {
		t.Errorf("Transport() = %q; want \"\"", got.Transport())
	}
	if got.HasRESTNumericEnums() {
		t.Error("HasRESTNumericEnums() = true; want false")
	}
}

func TestParse_invalidBoolAttr(t *testing.T) {
	content := `
java_gapic_library(
    name = "asset_java_gapic",
    rest_numeric_enums = "not-a-bool",
)
`
	tmpDir := t.TempDir()
	buildPath := filepath.Join(tmpDir, "BUILD.bazel")
	if err := os.WriteFile(buildPath, []byte(content), 0644); err != nil {
		t.Fatalf("failed to write test file: %v", err)
	}

	_, err := Parse(tmpDir)
	if err == nil {
		t.Error("Parse() succeeded; want error")
	}
}

func TestParse_noBuildFile(t *testing.T) {
	tmpDir := t.TempDir()
	_, err := Parse(tmpDir)
	if err == nil {
		t.Error("Parse() succeeded; want error")
	}
}