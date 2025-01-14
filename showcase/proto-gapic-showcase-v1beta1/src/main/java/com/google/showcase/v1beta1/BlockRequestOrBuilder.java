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
// source: schema/google/showcase/v1beta1/echo.proto

// Protobuf Java Version: 3.25.5
package com.google.showcase.v1beta1;

public interface BlockRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.BlockRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The amount of time to block before returning a response.
   * </pre>
   *
   * <code>.google.protobuf.Duration response_delay = 1;</code>
   *
   * @return Whether the responseDelay field is set.
   */
  boolean hasResponseDelay();
  /**
   *
   *
   * <pre>
   * The amount of time to block before returning a response.
   * </pre>
   *
   * <code>.google.protobuf.Duration response_delay = 1;</code>
   *
   * @return The responseDelay.
   */
  com.google.protobuf.Duration getResponseDelay();
  /**
   *
   *
   * <pre>
   * The amount of time to block before returning a response.
   * </pre>
   *
   * <code>.google.protobuf.Duration response_delay = 1;</code>
   */
  com.google.protobuf.DurationOrBuilder getResponseDelayOrBuilder();

  /**
   *
   *
   * <pre>
   * The error that will be returned by the server. If this code is specified
   * to be the OK rpc code, an empty response will be returned.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   *
   * @return Whether the error field is set.
   */
  boolean hasError();
  /**
   *
   *
   * <pre>
   * The error that will be returned by the server. If this code is specified
   * to be the OK rpc code, an empty response will be returned.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   *
   * @return The error.
   */
  com.google.rpc.Status getError();
  /**
   *
   *
   * <pre>
   * The error that will be returned by the server. If this code is specified
   * to be the OK rpc code, an empty response will be returned.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   */
  com.google.rpc.StatusOrBuilder getErrorOrBuilder();

  /**
   *
   *
   * <pre>
   * The response to be returned that will signify successful method call.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.BlockResponse success = 3;</code>
   *
   * @return Whether the success field is set.
   */
  boolean hasSuccess();
  /**
   *
   *
   * <pre>
   * The response to be returned that will signify successful method call.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.BlockResponse success = 3;</code>
   *
   * @return The success.
   */
  com.google.showcase.v1beta1.BlockResponse getSuccess();
  /**
   *
   *
   * <pre>
   * The response to be returned that will signify successful method call.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.BlockResponse success = 3;</code>
   */
  com.google.showcase.v1beta1.BlockResponseOrBuilder getSuccessOrBuilder();

  com.google.showcase.v1beta1.BlockRequest.ResponseCase getResponseCase();
}
