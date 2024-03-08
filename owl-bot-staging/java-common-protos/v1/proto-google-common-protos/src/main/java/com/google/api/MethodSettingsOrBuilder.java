// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/client.proto

// Protobuf Java Version: 3.25.2
package com.google.api;

public interface MethodSettingsOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.api.MethodSettings)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The fully qualified name of the method, for which the options below apply.
   * This is used to find the method to apply the options.
   * </pre>
   *
   * <code>string selector = 1;</code>
   * @return The selector.
   */
  java.lang.String getSelector();
  /**
   * <pre>
   * The fully qualified name of the method, for which the options below apply.
   * This is used to find the method to apply the options.
   * </pre>
   *
   * <code>string selector = 1;</code>
   * @return The bytes for selector.
   */
  com.google.protobuf.ByteString
      getSelectorBytes();

  /**
   * <pre>
   * Describes settings to use for long-running operations when generating
   * API methods for RPCs. Complements RPCs that use the annotations in
   * google/longrunning/operations.proto.
   *
   * Example of a YAML configuration::
   *
   *  publishing:
   *    method_settings:
   *      - selector: google.cloud.speech.v2.Speech.BatchRecognize
   *        long_running:
   *          initial_poll_delay:
   *            seconds: 60 # 1 minute
   *          poll_delay_multiplier: 1.5
   *          max_poll_delay:
   *            seconds: 360 # 6 minutes
   *          total_poll_timeout:
   *             seconds: 54000 # 90 minutes
   * </pre>
   *
   * <code>.google.api.MethodSettings.LongRunning long_running = 2;</code>
   * @return Whether the longRunning field is set.
   */
  boolean hasLongRunning();
  /**
   * <pre>
   * Describes settings to use for long-running operations when generating
   * API methods for RPCs. Complements RPCs that use the annotations in
   * google/longrunning/operations.proto.
   *
   * Example of a YAML configuration::
   *
   *  publishing:
   *    method_settings:
   *      - selector: google.cloud.speech.v2.Speech.BatchRecognize
   *        long_running:
   *          initial_poll_delay:
   *            seconds: 60 # 1 minute
   *          poll_delay_multiplier: 1.5
   *          max_poll_delay:
   *            seconds: 360 # 6 minutes
   *          total_poll_timeout:
   *             seconds: 54000 # 90 minutes
   * </pre>
   *
   * <code>.google.api.MethodSettings.LongRunning long_running = 2;</code>
   * @return The longRunning.
   */
  com.google.api.MethodSettings.LongRunning getLongRunning();
  /**
   * <pre>
   * Describes settings to use for long-running operations when generating
   * API methods for RPCs. Complements RPCs that use the annotations in
   * google/longrunning/operations.proto.
   *
   * Example of a YAML configuration::
   *
   *  publishing:
   *    method_settings:
   *      - selector: google.cloud.speech.v2.Speech.BatchRecognize
   *        long_running:
   *          initial_poll_delay:
   *            seconds: 60 # 1 minute
   *          poll_delay_multiplier: 1.5
   *          max_poll_delay:
   *            seconds: 360 # 6 minutes
   *          total_poll_timeout:
   *             seconds: 54000 # 90 minutes
   * </pre>
   *
   * <code>.google.api.MethodSettings.LongRunning long_running = 2;</code>
   */
  com.google.api.MethodSettings.LongRunningOrBuilder getLongRunningOrBuilder();

  /**
   * <pre>
   * List of top-level fields of the request message, that should be
   * automatically populated by the client libraries based on their
   * (google.api.field_info).format. Currently supported format: UUID4.
   *
   * Example of a YAML configuration:
   *
   *  publishing:
   *    method_settings:
   *      - selector: google.example.v1.ExampleService.CreateExample
   *        auto_populated_fields:
   *        - request_id
   * </pre>
   *
   * <code>repeated string auto_populated_fields = 3;</code>
   * @return A list containing the autoPopulatedFields.
   */
  java.util.List<java.lang.String>
      getAutoPopulatedFieldsList();
  /**
   * <pre>
   * List of top-level fields of the request message, that should be
   * automatically populated by the client libraries based on their
   * (google.api.field_info).format. Currently supported format: UUID4.
   *
   * Example of a YAML configuration:
   *
   *  publishing:
   *    method_settings:
   *      - selector: google.example.v1.ExampleService.CreateExample
   *        auto_populated_fields:
   *        - request_id
   * </pre>
   *
   * <code>repeated string auto_populated_fields = 3;</code>
   * @return The count of autoPopulatedFields.
   */
  int getAutoPopulatedFieldsCount();
  /**
   * <pre>
   * List of top-level fields of the request message, that should be
   * automatically populated by the client libraries based on their
   * (google.api.field_info).format. Currently supported format: UUID4.
   *
   * Example of a YAML configuration:
   *
   *  publishing:
   *    method_settings:
   *      - selector: google.example.v1.ExampleService.CreateExample
   *        auto_populated_fields:
   *        - request_id
   * </pre>
   *
   * <code>repeated string auto_populated_fields = 3;</code>
   * @param index The index of the element to return.
   * @return The autoPopulatedFields at the given index.
   */
  java.lang.String getAutoPopulatedFields(int index);
  /**
   * <pre>
   * List of top-level fields of the request message, that should be
   * automatically populated by the client libraries based on their
   * (google.api.field_info).format. Currently supported format: UUID4.
   *
   * Example of a YAML configuration:
   *
   *  publishing:
   *    method_settings:
   *      - selector: google.example.v1.ExampleService.CreateExample
   *        auto_populated_fields:
   *        - request_id
   * </pre>
   *
   * <code>repeated string auto_populated_fields = 3;</code>
   * @param index The index of the value to return.
   * @return The bytes of the autoPopulatedFields at the given index.
   */
  com.google.protobuf.ByteString
      getAutoPopulatedFieldsBytes(int index);
}