/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.api.generator.gapic.model;

public enum Transport {
  REST,
  GRPC,
  // Never used in the context as is, must be split into two contexts (REST and GRPC respectively)
  // instead.
  GRPC_REST;

  /**
   * Parse command line transport argument in the format `grpc+rest`.
   *
   * @param name name of the transport. Valid inputs are "grpc", "rest", "grpc+rest"
   * @return the {@code Transport} enum matching the command line argument
   */
  public static Transport parse(String name) {
    return valueOf(name.replace('+', '_').toUpperCase());
  }
}
