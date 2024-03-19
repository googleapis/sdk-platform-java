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
// source: google/apps/card/v1/card.proto

// Protobuf Java Version: 3.25.2
package com.google.apps.card.v1;

public interface DateTimePickerOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.apps.card.v1.DateTimePicker)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The name by which the `DateTimePicker` is identified in a form input event.
   *
   * For details about working with form inputs, see [Receive form
   * data](https://developers.google.com/chat/ui/read-form-data).
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The name.
   */
  java.lang.String getName();
  /**
   *
   *
   * <pre>
   * The name by which the `DateTimePicker` is identified in a form input event.
   *
   * For details about working with form inputs, see [Receive form
   * data](https://developers.google.com/chat/ui/read-form-data).
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString getNameBytes();

  /**
   *
   *
   * <pre>
   * The text that prompts users to input a date, a time, or a date and time.
   * For example, if users are scheduling an appointment, use a label such as
   * `Appointment date` or `Appointment date and time`.
   * </pre>
   *
   * <code>string label = 2;</code>
   *
   * @return The label.
   */
  java.lang.String getLabel();
  /**
   *
   *
   * <pre>
   * The text that prompts users to input a date, a time, or a date and time.
   * For example, if users are scheduling an appointment, use a label such as
   * `Appointment date` or `Appointment date and time`.
   * </pre>
   *
   * <code>string label = 2;</code>
   *
   * @return The bytes for label.
   */
  com.google.protobuf.ByteString getLabelBytes();

  /**
   *
   *
   * <pre>
   * Whether the widget supports inputting a date, a time, or the date and time.
   * </pre>
   *
   * <code>.google.apps.card.v1.DateTimePicker.DateTimePickerType type = 3;</code>
   *
   * @return The enum numeric value on the wire for type.
   */
  int getTypeValue();
  /**
   *
   *
   * <pre>
   * Whether the widget supports inputting a date, a time, or the date and time.
   * </pre>
   *
   * <code>.google.apps.card.v1.DateTimePicker.DateTimePickerType type = 3;</code>
   *
   * @return The type.
   */
  com.google.apps.card.v1.DateTimePicker.DateTimePickerType getType();

  /**
   *
   *
   * <pre>
   * The default value displayed in the widget, in milliseconds since [Unix
   * epoch time](https://en.wikipedia.org/wiki/Unix_time).
   *
   * Specify the value based on the type of picker (`DateTimePickerType`):
   *
   * * `DATE_AND_TIME`: a calendar date and time in UTC. For example, to
   *   represent January 1, 2023 at 12:00 PM UTC, use `1672574400000`.
   * * `DATE_ONLY`: a calendar date at 00:00:00 UTC. For example, to represent
   *   January 1, 2023, use `1672531200000`.
   * * `TIME_ONLY`: a time in UTC. For example, to represent 12:00 PM, use
   *   `43200000` (or `12 * 60 * 60 * 1000`).
   * </pre>
   *
   * <code>int64 value_ms_epoch = 4;</code>
   *
   * @return The valueMsEpoch.
   */
  long getValueMsEpoch();

  /**
   *
   *
   * <pre>
   * The number representing the time zone offset from UTC, in minutes.
   * If set, the `value_ms_epoch` is displayed in the specified time zone.
   * If unset, the value defaults to the user's time zone setting.
   * </pre>
   *
   * <code>int32 timezone_offset_date = 5;</code>
   *
   * @return The timezoneOffsetDate.
   */
  int getTimezoneOffsetDate();

  /**
   *
   *
   * <pre>
   * Triggered when the user clicks **Save** or **Clear** from the
   * `DateTimePicker` interface.
   * </pre>
   *
   * <code>.google.apps.card.v1.Action on_change_action = 6;</code>
   *
   * @return Whether the onChangeAction field is set.
   */
  boolean hasOnChangeAction();
  /**
   *
   *
   * <pre>
   * Triggered when the user clicks **Save** or **Clear** from the
   * `DateTimePicker` interface.
   * </pre>
   *
   * <code>.google.apps.card.v1.Action on_change_action = 6;</code>
   *
   * @return The onChangeAction.
   */
  com.google.apps.card.v1.Action getOnChangeAction();
  /**
   *
   *
   * <pre>
   * Triggered when the user clicks **Save** or **Clear** from the
   * `DateTimePicker` interface.
   * </pre>
   *
   * <code>.google.apps.card.v1.Action on_change_action = 6;</code>
   */
  com.google.apps.card.v1.ActionOrBuilder getOnChangeActionOrBuilder();
}
