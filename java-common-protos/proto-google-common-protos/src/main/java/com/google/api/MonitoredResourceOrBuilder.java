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
// source: google/api/monitored_resource.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

public interface MonitoredResourceOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.MonitoredResource)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Required. The monitored resource type. This field must match
   * the `type` field of a
   * [MonitoredResourceDescriptor][google.api.MonitoredResourceDescriptor]
   * object. For example, the type of a Compute Engine VM instance is
   * `gce_instance`. Some descriptors include the service name in the type; for
   * example, the type of a Datastream stream is
   * `datastream.googleapis.com/Stream`.
   * </pre>
   *
   * <code>string type = 1;</code>
   *
   * @return The type.
   */
  java.lang.String getType();

  /**
   *
   *
   * <pre>
   * Required. The monitored resource type. This field must match
   * the `type` field of a
   * [MonitoredResourceDescriptor][google.api.MonitoredResourceDescriptor]
   * object. For example, the type of a Compute Engine VM instance is
   * `gce_instance`. Some descriptors include the service name in the type; for
   * example, the type of a Datastream stream is
   * `datastream.googleapis.com/Stream`.
   * </pre>
   *
   * <code>string type = 1;</code>
   *
   * @return The bytes for type.
   */
  com.google.protobuf.ByteString getTypeBytes();

  /**
   *
   *
   * <pre>
   * Required. Values for all of the labels listed in the associated monitored
   * resource descriptor. For example, Compute Engine VM instances use the
   * labels `"project_id"`, `"instance_id"`, and `"zone"`.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  int getLabelsCount();

  /**
   *
   *
   * <pre>
   * Required. Values for all of the labels listed in the associated monitored
   * resource descriptor. For example, Compute Engine VM instances use the
   * labels `"project_id"`, `"instance_id"`, and `"zone"`.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  boolean containsLabels(java.lang.String key);

  /** Use {@link #getLabelsMap()} instead. */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String> getLabels();

  /**
   *
   *
   * <pre>
   * Required. Values for all of the labels listed in the associated monitored
   * resource descriptor. For example, Compute Engine VM instances use the
   * labels `"project_id"`, `"instance_id"`, and `"zone"`.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  java.util.Map<java.lang.String, java.lang.String> getLabelsMap();

  /**
   *
   *
   * <pre>
   * Required. Values for all of the labels listed in the associated monitored
   * resource descriptor. For example, Compute Engine VM instances use the
   * labels `"project_id"`, `"instance_id"`, and `"zone"`.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  /* nullable */
  java.lang.String getLabelsOrDefault(
      java.lang.String key,
      /* nullable */
      java.lang.String defaultValue);

  /**
   *
   *
   * <pre>
   * Required. Values for all of the labels listed in the associated monitored
   * resource descriptor. For example, Compute Engine VM instances use the
   * labels `"project_id"`, `"instance_id"`, and `"zone"`.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  java.lang.String getLabelsOrThrow(java.lang.String key);
}
