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

// Protobuf Java Version: 3.25.8
package com.google.api;

public interface GoSettingsOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.GoSettings)
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
   * Map of service names to renamed services. Keys are the package relative
   * service names and values are the name to be used for the service client
   * and call options.
   *
   * publishing:
   *   go_settings:
   *     renamed_services:
   *       Publisher: TopicAdmin
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_services = 2;</code>
   */
  int getRenamedServicesCount();

  /**
   *
   *
   * <pre>
   * Map of service names to renamed services. Keys are the package relative
   * service names and values are the name to be used for the service client
   * and call options.
   *
   * publishing:
   *   go_settings:
   *     renamed_services:
   *       Publisher: TopicAdmin
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_services = 2;</code>
   */
  boolean containsRenamedServices(java.lang.String key);

  /** Use {@link #getRenamedServicesMap()} instead. */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String> getRenamedServices();

  /**
   *
   *
   * <pre>
   * Map of service names to renamed services. Keys are the package relative
   * service names and values are the name to be used for the service client
   * and call options.
   *
   * publishing:
   *   go_settings:
   *     renamed_services:
   *       Publisher: TopicAdmin
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_services = 2;</code>
   */
  java.util.Map<java.lang.String, java.lang.String> getRenamedServicesMap();

  /**
   *
   *
   * <pre>
   * Map of service names to renamed services. Keys are the package relative
   * service names and values are the name to be used for the service client
   * and call options.
   *
   * publishing:
   *   go_settings:
   *     renamed_services:
   *       Publisher: TopicAdmin
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_services = 2;</code>
   */
  /* nullable */
  java.lang.String getRenamedServicesOrDefault(
      java.lang.String key,
      /* nullable */
      java.lang.String defaultValue);

  /**
   *
   *
   * <pre>
   * Map of service names to renamed services. Keys are the package relative
   * service names and values are the name to be used for the service client
   * and call options.
   *
   * publishing:
   *   go_settings:
   *     renamed_services:
   *       Publisher: TopicAdmin
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_services = 2;</code>
   */
  java.lang.String getRenamedServicesOrThrow(java.lang.String key);
}
