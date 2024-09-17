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
// source: google/api/endpoint.proto

// Protobuf Java Version: 3.25.4
package com.google.api;

public interface EndpointOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.Endpoint)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The canonical name of this endpoint.
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
   * The canonical name of this endpoint.
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
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @return A list containing the aliases.
   */
  java.util.List<java.lang.String> getAliasesList();
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @return The count of aliases.
   */
  int getAliasesCount();
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @param index The index of the element to return.
   * @return The aliases at the given index.
   */
  java.lang.String getAliases(int index);
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the aliases at the given index.
   */
  com.google.protobuf.ByteString getAliasesBytes(int index);

  /**
   *
   *
   * <pre>
   * The specification of an Internet routable address of API frontend that will
   * handle requests to this [API
   * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
   * either a valid IPv4 address or a fully-qualified domain name. For example,
   * "8.8.8.8" or "myservice.appspot.com".
   * </pre>
   *
   * <code>string target = 101;</code>
   *
   * @return The target.
   */
  java.lang.String getTarget();
  /**
   *
   *
   * <pre>
   * The specification of an Internet routable address of API frontend that will
   * handle requests to this [API
   * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
   * either a valid IPv4 address or a fully-qualified domain name. For example,
   * "8.8.8.8" or "myservice.appspot.com".
   * </pre>
   *
   * <code>string target = 101;</code>
   *
   * @return The bytes for target.
   */
  com.google.protobuf.ByteString getTargetBytes();

  /**
   *
   *
   * <pre>
   * Allowing
   * [CORS](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing), aka
   * cross-domain traffic, would allow the backends served from this endpoint to
   * receive and respond to HTTP OPTIONS requests. The response will be used by
   * the browser to determine whether the subsequent cross-origin request is
   * allowed to proceed.
   * </pre>
   *
   * <code>bool allow_cors = 5;</code>
   *
   * @return The allowCors.
   */
  boolean getAllowCors();
}
