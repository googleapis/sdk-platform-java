/*
 * Copyright 2025 Google LLC
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
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/client.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

public interface PythonSettingsOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.PythonSettings)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   *
   * @return Whether the common field is set.
   */
  boolean hasCommon();
  /**
   *
   *
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   *
   * @return The common.
   */
  com.google.api.CommonLanguageSettings getCommon();
  /**
   *
   *
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   */
  com.google.api.CommonLanguageSettingsOrBuilder getCommonOrBuilder();

  /**
   *
   *
   * <pre>
   * Experimental features to be included during client library generation.
   * </pre>
   *
   * <code>.google.api.PythonSettings.ExperimentalFeatures experimental_features = 2;</code>
   *
   * @return Whether the experimentalFeatures field is set.
   */
  boolean hasExperimentalFeatures();
  /**
   *
   *
   * <pre>
   * Experimental features to be included during client library generation.
   * </pre>
   *
   * <code>.google.api.PythonSettings.ExperimentalFeatures experimental_features = 2;</code>
   *
   * @return The experimentalFeatures.
   */
  com.google.api.PythonSettings.ExperimentalFeatures getExperimentalFeatures();
  /**
   *
   *
   * <pre>
   * Experimental features to be included during client library generation.
   * </pre>
   *
   * <code>.google.api.PythonSettings.ExperimentalFeatures experimental_features = 2;</code>
   */
  com.google.api.PythonSettings.ExperimentalFeaturesOrBuilder getExperimentalFeaturesOrBuilder();
}
