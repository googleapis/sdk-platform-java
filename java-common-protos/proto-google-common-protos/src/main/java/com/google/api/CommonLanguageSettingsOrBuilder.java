/*
 * Copyright 2024 Google LLC
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

public interface CommonLanguageSettingsOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.CommonLanguageSettings)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Link to automatically generated reference documentation.  Example:
   * https://cloud.google.com/nodejs/docs/reference/asset/latest
   * </pre>
   *
   * <code>string reference_docs_uri = 1 [deprecated = true];</code>
   *
   * @deprecated google.api.CommonLanguageSettings.reference_docs_uri is deprecated. See
   *     google/api/client.proto;l=122
   * @return The referenceDocsUri.
   */
  @java.lang.Deprecated
  java.lang.String getReferenceDocsUri();
  /**
   *
   *
   * <pre>
   * Link to automatically generated reference documentation.  Example:
   * https://cloud.google.com/nodejs/docs/reference/asset/latest
   * </pre>
   *
   * <code>string reference_docs_uri = 1 [deprecated = true];</code>
   *
   * @deprecated google.api.CommonLanguageSettings.reference_docs_uri is deprecated. See
   *     google/api/client.proto;l=122
   * @return The bytes for referenceDocsUri.
   */
  @java.lang.Deprecated
  com.google.protobuf.ByteString getReferenceDocsUriBytes();

  /**
   *
   *
   * <pre>
   * The destination where API teams want this client library to be published.
   * </pre>
   *
   * <code>repeated .google.api.ClientLibraryDestination destinations = 2;</code>
   *
   * @return A list containing the destinations.
   */
  java.util.List<com.google.api.ClientLibraryDestination> getDestinationsList();
  /**
   *
   *
   * <pre>
   * The destination where API teams want this client library to be published.
   * </pre>
   *
   * <code>repeated .google.api.ClientLibraryDestination destinations = 2;</code>
   *
   * @return The count of destinations.
   */
  int getDestinationsCount();
  /**
   *
   *
   * <pre>
   * The destination where API teams want this client library to be published.
   * </pre>
   *
   * <code>repeated .google.api.ClientLibraryDestination destinations = 2;</code>
   *
   * @param index The index of the element to return.
   * @return The destinations at the given index.
   */
  com.google.api.ClientLibraryDestination getDestinations(int index);
  /**
   *
   *
   * <pre>
   * The destination where API teams want this client library to be published.
   * </pre>
   *
   * <code>repeated .google.api.ClientLibraryDestination destinations = 2;</code>
   *
   * @return A list containing the enum numeric values on the wire for destinations.
   */
  java.util.List<java.lang.Integer> getDestinationsValueList();
  /**
   *
   *
   * <pre>
   * The destination where API teams want this client library to be published.
   * </pre>
   *
   * <code>repeated .google.api.ClientLibraryDestination destinations = 2;</code>
   *
   * @param index The index of the value to return.
   * @return The enum numeric value on the wire of destinations at the given index.
   */
  int getDestinationsValue(int index);

  /**
   *
   *
   * <pre>
   * Configuration for which RPCs should be generated in the GAPIC client.
   * </pre>
   *
   * <code>.google.api.SelectiveGapicGeneration selective_gapic_generation = 3;</code>
   *
   * @return Whether the selectiveGapicGeneration field is set.
   */
  boolean hasSelectiveGapicGeneration();
  /**
   *
   *
   * <pre>
   * Configuration for which RPCs should be generated in the GAPIC client.
   * </pre>
   *
   * <code>.google.api.SelectiveGapicGeneration selective_gapic_generation = 3;</code>
   *
   * @return The selectiveGapicGeneration.
   */
  com.google.api.SelectiveGapicGeneration getSelectiveGapicGeneration();
  /**
   *
   *
   * <pre>
   * Configuration for which RPCs should be generated in the GAPIC client.
   * </pre>
   *
   * <code>.google.api.SelectiveGapicGeneration selective_gapic_generation = 3;</code>
   */
  com.google.api.SelectiveGapicGenerationOrBuilder getSelectiveGapicGenerationOrBuilder();
}
