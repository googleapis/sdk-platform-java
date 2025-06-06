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
// source: google/api/config_change.proto

// Protobuf Java Version: 3.25.8
package com.google.api;

public interface ConfigChangeOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.ConfigChange)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Object hierarchy path to the change, with levels separated by a '.'
   * character. For repeated fields, an applicable unique identifier field is
   * used for the index (usually selector, name, or id). For maps, the term
   * 'key' is used. If the field has no unique identifier, the numeric index
   * is used.
   * Examples:
   * - visibility.rules[selector=="google.LibraryService.ListBooks"].restriction
   * - quota.metric_rules[selector=="google"].metric_costs[key=="reads"].value
   * - logging.producer_destinations[0]
   * </pre>
   *
   * <code>string element = 1;</code>
   *
   * @return The element.
   */
  java.lang.String getElement();

  /**
   *
   *
   * <pre>
   * Object hierarchy path to the change, with levels separated by a '.'
   * character. For repeated fields, an applicable unique identifier field is
   * used for the index (usually selector, name, or id). For maps, the term
   * 'key' is used. If the field has no unique identifier, the numeric index
   * is used.
   * Examples:
   * - visibility.rules[selector=="google.LibraryService.ListBooks"].restriction
   * - quota.metric_rules[selector=="google"].metric_costs[key=="reads"].value
   * - logging.producer_destinations[0]
   * </pre>
   *
   * <code>string element = 1;</code>
   *
   * @return The bytes for element.
   */
  com.google.protobuf.ByteString getElementBytes();

  /**
   *
   *
   * <pre>
   * Value of the changed object in the old Service configuration,
   * in JSON format. This field will not be populated if ChangeType == ADDED.
   * </pre>
   *
   * <code>string old_value = 2;</code>
   *
   * @return The oldValue.
   */
  java.lang.String getOldValue();

  /**
   *
   *
   * <pre>
   * Value of the changed object in the old Service configuration,
   * in JSON format. This field will not be populated if ChangeType == ADDED.
   * </pre>
   *
   * <code>string old_value = 2;</code>
   *
   * @return The bytes for oldValue.
   */
  com.google.protobuf.ByteString getOldValueBytes();

  /**
   *
   *
   * <pre>
   * Value of the changed object in the new Service configuration,
   * in JSON format. This field will not be populated if ChangeType == REMOVED.
   * </pre>
   *
   * <code>string new_value = 3;</code>
   *
   * @return The newValue.
   */
  java.lang.String getNewValue();

  /**
   *
   *
   * <pre>
   * Value of the changed object in the new Service configuration,
   * in JSON format. This field will not be populated if ChangeType == REMOVED.
   * </pre>
   *
   * <code>string new_value = 3;</code>
   *
   * @return The bytes for newValue.
   */
  com.google.protobuf.ByteString getNewValueBytes();

  /**
   *
   *
   * <pre>
   * The type for this change, either ADDED, REMOVED, or MODIFIED.
   * </pre>
   *
   * <code>.google.api.ChangeType change_type = 4;</code>
   *
   * @return The enum numeric value on the wire for changeType.
   */
  int getChangeTypeValue();

  /**
   *
   *
   * <pre>
   * The type for this change, either ADDED, REMOVED, or MODIFIED.
   * </pre>
   *
   * <code>.google.api.ChangeType change_type = 4;</code>
   *
   * @return The changeType.
   */
  com.google.api.ChangeType getChangeType();

  /**
   *
   *
   * <pre>
   * Collection of advice provided for this change, useful for determining the
   * possible impact of this change.
   * </pre>
   *
   * <code>repeated .google.api.Advice advices = 5;</code>
   */
  java.util.List<com.google.api.Advice> getAdvicesList();

  /**
   *
   *
   * <pre>
   * Collection of advice provided for this change, useful for determining the
   * possible impact of this change.
   * </pre>
   *
   * <code>repeated .google.api.Advice advices = 5;</code>
   */
  com.google.api.Advice getAdvices(int index);

  /**
   *
   *
   * <pre>
   * Collection of advice provided for this change, useful for determining the
   * possible impact of this change.
   * </pre>
   *
   * <code>repeated .google.api.Advice advices = 5;</code>
   */
  int getAdvicesCount();

  /**
   *
   *
   * <pre>
   * Collection of advice provided for this change, useful for determining the
   * possible impact of this change.
   * </pre>
   *
   * <code>repeated .google.api.Advice advices = 5;</code>
   */
  java.util.List<? extends com.google.api.AdviceOrBuilder> getAdvicesOrBuilderList();

  /**
   *
   *
   * <pre>
   * Collection of advice provided for this change, useful for determining the
   * possible impact of this change.
   * </pre>
   *
   * <code>repeated .google.api.Advice advices = 5;</code>
   */
  com.google.api.AdviceOrBuilder getAdvicesOrBuilder(int index);
}
