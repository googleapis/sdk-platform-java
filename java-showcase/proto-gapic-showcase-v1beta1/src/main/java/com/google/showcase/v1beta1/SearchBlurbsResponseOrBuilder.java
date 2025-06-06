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
// source: schema/google/showcase/v1beta1/messaging.proto

// Protobuf Java Version: 3.25.8
package com.google.showcase.v1beta1;

public interface SearchBlurbsResponseOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.SearchBlurbsResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Blurbs that matched the search query.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
   */
  java.util.List<com.google.showcase.v1beta1.Blurb> getBlurbsList();

  /**
   *
   *
   * <pre>
   * Blurbs that matched the search query.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
   */
  com.google.showcase.v1beta1.Blurb getBlurbs(int index);

  /**
   *
   *
   * <pre>
   * Blurbs that matched the search query.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
   */
  int getBlurbsCount();

  /**
   *
   *
   * <pre>
   * Blurbs that matched the search query.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
   */
  java.util.List<? extends com.google.showcase.v1beta1.BlurbOrBuilder> getBlurbsOrBuilderList();

  /**
   *
   *
   * <pre>
   * Blurbs that matched the search query.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
   */
  com.google.showcase.v1beta1.BlurbOrBuilder getBlurbsOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * A token to retrieve next page of results.
   * Pass this value in SearchBlurbsRequest.page_token field in the subsequent
   * call to `google.showcase.v1beta1.Blurb&#92;SearchBlurbs` method to
   * retrieve the next page of results.
   * </pre>
   *
   * <code>string next_page_token = 2;</code>
   *
   * @return The nextPageToken.
   */
  java.lang.String getNextPageToken();

  /**
   *
   *
   * <pre>
   * A token to retrieve next page of results.
   * Pass this value in SearchBlurbsRequest.page_token field in the subsequent
   * call to `google.showcase.v1beta1.Blurb&#92;SearchBlurbs` method to
   * retrieve the next page of results.
   * </pre>
   *
   * <code>string next_page_token = 2;</code>
   *
   * @return The bytes for nextPageToken.
   */
  com.google.protobuf.ByteString getNextPageTokenBytes();
}
