/*
 * Copyright 2020 Google LLC
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

package com.google.api;

public interface JavaSettingsOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.JavaSettings)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The package name to use in Java. Clobbers the java_package option
   * set in the protobuf. This should be used **only** by APIs
   * who have already set the language_settings.java.package_name" field
   * in gapic.yaml. API teams should use the protobuf java_package option
   * where possible.
   * Example of a YAML configuration::
   *  publishing:
   *    java_settings:
   *      library_package: com.google.cloud.pubsub.v1
   * </pre>
   *
   * <code>string library_package = 1;</code>
   *
   * @return The libraryPackage.
   */
  java.lang.String getLibraryPackage();
  /**
   *
   *
   * <pre>
   * The package name to use in Java. Clobbers the java_package option
   * set in the protobuf. This should be used **only** by APIs
   * who have already set the language_settings.java.package_name" field
   * in gapic.yaml. API teams should use the protobuf java_package option
   * where possible.
   * Example of a YAML configuration::
   *  publishing:
   *    java_settings:
   *      library_package: com.google.cloud.pubsub.v1
   * </pre>
   *
   * <code>string library_package = 1;</code>
   *
   * @return The bytes for libraryPackage.
   */
  com.google.protobuf.ByteString getLibraryPackageBytes();

  /**
   *
   *
   * <pre>
   * Configure the Java class name to use instead of the service's for its
   * corresponding generated GAPIC client. Keys are fully-qualified
   * service names as they appear in the protobuf (including the full
   * the language_settings.java.interface_names" field in gapic.yaml. API
   * teams should otherwise use the service name as it appears in the
   * protobuf.
   * Example of a YAML configuration::
   *  publishing:
   *    java_settings:
   *      service_class_names:
   *        - google.pubsub.v1.Publisher: TopicAdmin
   *        - google.pubsub.v1.Subscriber: SubscriptionAdmin
   * </pre>
   *
   * <code>map&lt;string, string&gt; service_class_names = 2;</code>
   */
  int getServiceClassNamesCount();
  /**
   *
   *
   * <pre>
   * Configure the Java class name to use instead of the service's for its
   * corresponding generated GAPIC client. Keys are fully-qualified
   * service names as they appear in the protobuf (including the full
   * the language_settings.java.interface_names" field in gapic.yaml. API
   * teams should otherwise use the service name as it appears in the
   * protobuf.
   * Example of a YAML configuration::
   *  publishing:
   *    java_settings:
   *      service_class_names:
   *        - google.pubsub.v1.Publisher: TopicAdmin
   *        - google.pubsub.v1.Subscriber: SubscriptionAdmin
   * </pre>
   *
   * <code>map&lt;string, string&gt; service_class_names = 2;</code>
   */
  boolean containsServiceClassNames(java.lang.String key);
  /** Use {@link #getServiceClassNamesMap()} instead. */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String> getServiceClassNames();
  /**
   *
   *
   * <pre>
   * Configure the Java class name to use instead of the service's for its
   * corresponding generated GAPIC client. Keys are fully-qualified
   * service names as they appear in the protobuf (including the full
   * the language_settings.java.interface_names" field in gapic.yaml. API
   * teams should otherwise use the service name as it appears in the
   * protobuf.
   * Example of a YAML configuration::
   *  publishing:
   *    java_settings:
   *      service_class_names:
   *        - google.pubsub.v1.Publisher: TopicAdmin
   *        - google.pubsub.v1.Subscriber: SubscriptionAdmin
   * </pre>
   *
   * <code>map&lt;string, string&gt; service_class_names = 2;</code>
   */
  java.util.Map<java.lang.String, java.lang.String> getServiceClassNamesMap();
  /**
   *
   *
   * <pre>
   * Configure the Java class name to use instead of the service's for its
   * corresponding generated GAPIC client. Keys are fully-qualified
   * service names as they appear in the protobuf (including the full
   * the language_settings.java.interface_names" field in gapic.yaml. API
   * teams should otherwise use the service name as it appears in the
   * protobuf.
   * Example of a YAML configuration::
   *  publishing:
   *    java_settings:
   *      service_class_names:
   *        - google.pubsub.v1.Publisher: TopicAdmin
   *        - google.pubsub.v1.Subscriber: SubscriptionAdmin
   * </pre>
   *
   * <code>map&lt;string, string&gt; service_class_names = 2;</code>
   */
  /* nullable */
  java.lang.String getServiceClassNamesOrDefault(
      java.lang.String key,
      /* nullable */
      java.lang.String defaultValue);
  /**
   *
   *
   * <pre>
   * Configure the Java class name to use instead of the service's for its
   * corresponding generated GAPIC client. Keys are fully-qualified
   * service names as they appear in the protobuf (including the full
   * the language_settings.java.interface_names" field in gapic.yaml. API
   * teams should otherwise use the service name as it appears in the
   * protobuf.
   * Example of a YAML configuration::
   *  publishing:
   *    java_settings:
   *      service_class_names:
   *        - google.pubsub.v1.Publisher: TopicAdmin
   *        - google.pubsub.v1.Subscriber: SubscriptionAdmin
   * </pre>
   *
   * <code>map&lt;string, string&gt; service_class_names = 2;</code>
   */
  java.lang.String getServiceClassNamesOrThrow(java.lang.String key);

  /**
   *
   *
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 3;</code>
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
   * <code>.google.api.CommonLanguageSettings common = 3;</code>
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
   * <code>.google.api.CommonLanguageSettings common = 3;</code>
   */
  com.google.api.CommonLanguageSettingsOrBuilder getCommonOrBuilder();
}
