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
	"errors"
	"fmt"
	"log/slog"
	"os"
	"path/filepath"
	"regexp"
	"strconv"
	"strings"
	"sync"
)

// Config holds configuration extracted from a googleapis BUILD.bazel file.
type Config struct {
	gapicYAML         string
	grpcServiceConfig string
	restNumericEnums  bool
	serviceYAML       string
	transport         string
	hasGAPIC          bool
}

// HasGAPIC indicates whether the GAPIC generator should be run.
func (c *Config) HasGAPIC() bool { return c.hasGAPIC }

// GapicYAML is the GAPIC config file in the API version directory in googleapis.
func (c *Config) GapicYAML() string { return c.gapicYAML }

// ServiceYAML is the client config file in the API version directory in googleapis.
func (c *Config) ServiceYAML() string { return c.serviceYAML }

// GRPCServiceConfig is name of the gRPC service config JSON file.
func (c *Config) GRPCServiceConfig() string { return c.grpcServiceConfig }

// Transport is typically one of "grpc", "rest" or "grpc+rest".
func (c *Config) Transport() string { return c.transport }

// HasRESTNumericEnums indicates whether the generated client should support numeric enums.
func (c *Config) HasRESTNumericEnums() bool { return c.restNumericEnums }

// Validate ensures that the configuration is valid.
func (c *Config) Validate() error {
	if c.hasGAPIC {
		if c.serviceYAML == "" {
			return errors.New("librariangen: serviceYAML is not set")
		}
	}
	return nil
}

var javaGapicLibraryRE = regexp.MustCompile(`java_gapic_library\((?s:.)*?\)`)
// Parse reads a BUILD.bazel file from the given directory and extracts the
// relevant configuration from the java_gapic_library rule.
func Parse(dir string) (*Config, error) {
	c := &Config{}
	fp := filepath.Join(dir, "BUILD.bazel")
	data, err := os.ReadFile(fp)
	if err != nil {
		return nil, fmt.Errorf("librariangen: failed to read BUILD.bazel file %s: %w", fp, err)
	}
	content := string(data)

	gapicLibraryBlock := javaGapicLibraryRE.FindString(content)
	if gapicLibraryBlock != "" {
		c.hasGAPIC = true
		c.grpcServiceConfig = findString(gapicLibraryBlock, "grpc_service_config")
		c.serviceYAML = strings.TrimPrefix(findString(gapicLibraryBlock, "service_yaml"), ":")
		c.transport = findString(gapicLibraryBlock, "transport")
		if c.restNumericEnums, err = findBool(gapicLibraryBlock, "rest_numeric_enums"); err != nil {
			return nil, fmt.Errorf("librariangen: failed to parse BUILD.bazel file %s: %w", fp, err)
		}
		c.gapicYAML = strings.TrimPrefix(findString(gapicLibraryBlock, "gapic_yaml"), ":")
	}
	if err := c.Validate(); err != nil {
		return nil, fmt.Errorf("librariangen: invalid bazel config in %s: %w", dir, err)
	}
	slog.Debug("librariangen: bazel config loaded", "conf", c)
	return c, nil
}

var reCache = &sync.Map{}

func getRegexp(key, pattern string) *regexp.Regexp {
	val, ok := reCache.Load(key)
	if !ok {
		val = regexp.MustCompile(pattern)
		reCache.Store(key, val)
	}
	return val.(*regexp.Regexp)
}

func findString(content, name string) string {
	re := getRegexp("findString_"+name, fmt.Sprintf(`%s\s*=\s*(?:"([^"]+)"|'([^']+)'){1}`, name))
	match := re.FindStringSubmatch(content)
	if len(match) > 2 {
		if match[1] != "" {
			return match[1] // Double-quoted
		}
		return match[2] // Single-quoted
	}
	slog.Debug("librariangen: failed to find string attr in BUILD.bazel", "name", name)
	return ""
}

func findBool(content, name string) (bool, error) {
	re := getRegexp("findBool_"+name, fmt.Sprintf(`%s\s*=\s*(\w+)`, name))
	if match := re.FindStringSubmatch(content); len(match) > 1 {
		if b, err := strconv.ParseBool(match[1]); err == nil {
			return b, nil
		}
		return false, fmt.Errorf("librariangen: failed to parse bool attr in BUILD.bazel: %q, got: %q", name, match[1])
	}
	slog.Debug("librariangen: failed to find bool attr in BUILD.bazel", "name", name)
	return false, nil
}
