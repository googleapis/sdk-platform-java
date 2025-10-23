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
	"fmt"
	"os"
	"path/filepath"
	"strings"
)

// ConfigProvider is an interface that describes the configuration needed
// by the Build function. This allows the protoc package to be decoupled
// from the source of the configuration (e.g., Bazel files, JSON, etc.).
type ConfigProvider interface {
	ServiceYAML() string
	GapicYAML() string
	GRPCServiceConfig() string
	Transport() string
	HasRESTNumericEnums() bool
	HasGAPIC() bool
}

// OutputConfig provides paths to directories to be used for protoc output.
type OutputConfig struct {
	GAPICDir string
	GRPCDir  string
	ProtoDir string
}

// Build constructs the full protoc command arguments for a given API.
func Build(apiServiceDir string, config ConfigProvider, sourceDir string, outputConfig *OutputConfig) ([]string, error) {
	// Gather all .proto files in the API's source directory.
	entries, err := os.ReadDir(apiServiceDir)
	if err != nil {
		return nil, fmt.Errorf("librariangen: failed to read API source directory %s: %w", apiServiceDir, err)
	}

	var protoFiles []string
	for _, entry := range entries {
		if !entry.IsDir() && filepath.Ext(entry.Name()) == ".proto" {
			protoFiles = append(protoFiles, filepath.Join(apiServiceDir, entry.Name()))
		}
	}

	if len(protoFiles) == 0 {
		return nil, fmt.Errorf("librariangen: no .proto files found in %s", apiServiceDir)
	}

	// Construct the protoc command arguments.
	var gapicOpts []string
	if config.HasGAPIC() {
		if config.ServiceYAML() != "" {
			gapicOpts = append(gapicOpts, fmt.Sprintf("api-service-config=%s", filepath.Join(apiServiceDir, config.ServiceYAML())))
		}
		if config.GapicYAML() != "" {
			gapicOpts = append(gapicOpts, fmt.Sprintf("gapic-config=%s", filepath.Join(apiServiceDir, config.GapicYAML())))
		}
		if config.GRPCServiceConfig() != "" {
			gapicOpts = append(gapicOpts, fmt.Sprintf("grpc-service-config=%s", filepath.Join(apiServiceDir, config.GRPCServiceConfig())))
		}
		if config.Transport() != "" {
			gapicOpts = append(gapicOpts, fmt.Sprintf("transport=%s", config.Transport()))
		}
		if config.HasRESTNumericEnums() {
			gapicOpts = append(gapicOpts, "rest-numeric-enums")
		}
	}

	args := []string{
		"protoc",
		"--experimental_allow_proto3_optional",
	}

	args = append(args, fmt.Sprintf("--java_out=%s", outputConfig.ProtoDir))
	if config.Transport() != "" && config.Transport() != "rest" {
		args = append(args, fmt.Sprintf("--java_grpc_out=%s", outputConfig.GRPCDir))
	}
	if config.HasGAPIC() {
		args = append(args, fmt.Sprintf("--java_gapic_out=metadata:%s", outputConfig.GAPICDir))

		if len(gapicOpts) > 0 {
			args = append(args, "--java_gapic_opt="+strings.Join(gapicOpts, ","))
		}
	}

	args = append(args,
		// The -I flag specifies the import path for protoc. All protos
		// and their dependencies must be findable from this path.
		// The /source mount contains the complete googleapis repository.
		"-I="+sourceDir,
	)

	args = append(args, protoFiles...)

	return args, nil
}
