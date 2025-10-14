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

// Package message defines data types which the Librarian CLI and language
// containers exchange.
// There shouldn't be CLI-specific data type or language container-specific
// data types in this package.
// TODO(b/447404382): Move this package to the https://github.com/googleapis/librarian
// GitHub repository once the interface is finalized.
// This package must not have any Java-specific implementation.
package message

// GenerateRequest is the JSON message sent to the language container by
// the Librarian CLI when the "generate" command is invoked.
type GenerateRequest struct {
}

// GenerateResponse is the JSON message sent back to the Librarian CLI
// by the language container after processing the "generate" command.
type GenerateResponse struct {
}

// ConfigureRequest is the JSON message sent to the language container by
// the Librarian CLI when the "configure" command is invoked.
type ConfigureRequest struct {
}

// ConfigureResponse is the JSON message sent back to the Librarian CLI
// by the language container after processing the "configure" command.
type ConfigureResponse struct {
}
