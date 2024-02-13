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
// source: google/cloud/audit/audit_log.proto

// Protobuf Java Version: 3.25.2
package com.google.cloud.audit;

public interface ResourceLocationOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.cloud.audit.ResourceLocation)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The locations of a resource after the execution of the operation.
   * Requests to create or delete a location based resource must populate
   * the 'current_locations' field and not the 'original_locations' field.
   * For example:
   *
   *     "europe-west1-a"
   *     "us-east1"
   *     "nam3"
   * </pre>
   *
   * <code>repeated string current_locations = 1;</code>
   *
   * @return A list containing the currentLocations.
   */
  java.util.List<java.lang.String> getCurrentLocationsList();
  /**
   *
   *
   * <pre>
   * The locations of a resource after the execution of the operation.
   * Requests to create or delete a location based resource must populate
   * the 'current_locations' field and not the 'original_locations' field.
   * For example:
   *
   *     "europe-west1-a"
   *     "us-east1"
   *     "nam3"
   * </pre>
   *
   * <code>repeated string current_locations = 1;</code>
   *
   * @return The count of currentLocations.
   */
  int getCurrentLocationsCount();
  /**
   *
   *
   * <pre>
   * The locations of a resource after the execution of the operation.
   * Requests to create or delete a location based resource must populate
   * the 'current_locations' field and not the 'original_locations' field.
   * For example:
   *
   *     "europe-west1-a"
   *     "us-east1"
   *     "nam3"
   * </pre>
   *
   * <code>repeated string current_locations = 1;</code>
   *
   * @param index The index of the element to return.
   * @return The currentLocations at the given index.
   */
  java.lang.String getCurrentLocations(int index);
  /**
   *
   *
   * <pre>
   * The locations of a resource after the execution of the operation.
   * Requests to create or delete a location based resource must populate
   * the 'current_locations' field and not the 'original_locations' field.
   * For example:
   *
   *     "europe-west1-a"
   *     "us-east1"
   *     "nam3"
   * </pre>
   *
   * <code>repeated string current_locations = 1;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the currentLocations at the given index.
   */
  com.google.protobuf.ByteString getCurrentLocationsBytes(int index);

  /**
   *
   *
   * <pre>
   * The locations of a resource prior to the execution of the operation.
   * Requests that mutate the resource's location must populate both the
   * 'original_locations' as well as the 'current_locations' fields.
   * For example:
   *
   *     "europe-west1-a"
   *     "us-east1"
   *     "nam3"
   * </pre>
   *
   * <code>repeated string original_locations = 2;</code>
   *
   * @return A list containing the originalLocations.
   */
  java.util.List<java.lang.String> getOriginalLocationsList();
  /**
   *
   *
   * <pre>
   * The locations of a resource prior to the execution of the operation.
   * Requests that mutate the resource's location must populate both the
   * 'original_locations' as well as the 'current_locations' fields.
   * For example:
   *
   *     "europe-west1-a"
   *     "us-east1"
   *     "nam3"
   * </pre>
   *
   * <code>repeated string original_locations = 2;</code>
   *
   * @return The count of originalLocations.
   */
  int getOriginalLocationsCount();
  /**
   *
   *
   * <pre>
   * The locations of a resource prior to the execution of the operation.
   * Requests that mutate the resource's location must populate both the
   * 'original_locations' as well as the 'current_locations' fields.
   * For example:
   *
   *     "europe-west1-a"
   *     "us-east1"
   *     "nam3"
   * </pre>
   *
   * <code>repeated string original_locations = 2;</code>
   *
   * @param index The index of the element to return.
   * @return The originalLocations at the given index.
   */
  java.lang.String getOriginalLocations(int index);
  /**
   *
   *
   * <pre>
   * The locations of a resource prior to the execution of the operation.
   * Requests that mutate the resource's location must populate both the
   * 'original_locations' as well as the 'current_locations' fields.
   * For example:
   *
   *     "europe-west1-a"
   *     "us-east1"
   *     "nam3"
   * </pre>
   *
   * <code>repeated string original_locations = 2;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the originalLocations at the given index.
   */
  com.google.protobuf.ByteString getOriginalLocationsBytes(int index);
}
