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
// source: google/api/auth.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

public interface AuthRequirementOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.AuthRequirement)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * [id][google.api.AuthProvider.id] from authentication provider.
   *
   * Example:
   *
   *     provider_id: bookstore_auth
   * </pre>
   *
   * <code>string provider_id = 1;</code>
   *
   * @return The providerId.
   */
  java.lang.String getProviderId();

  /**
   *
   *
   * <pre>
   * [id][google.api.AuthProvider.id] from authentication provider.
   *
   * Example:
   *
   *     provider_id: bookstore_auth
   * </pre>
   *
   * <code>string provider_id = 1;</code>
   *
   * @return The bytes for providerId.
   */
  com.google.protobuf.ByteString getProviderIdBytes();

  /**
   *
   *
   * <pre>
   * NOTE: This will be deprecated soon, once AuthProvider.audiences is
   * implemented and accepted in all the runtime components.
   *
   * The list of JWT
   * [audiences](https://tools.ietf.org/html/draft-ietf-oauth-json-web-token-32#section-4.1.3).
   * that are allowed to access. A JWT containing any of these audiences will
   * be accepted. When this setting is absent, only JWTs with audience
   * "https://[Service_name][google.api.Service.name]/[API_name][google.protobuf.Api.name]"
   * will be accepted. For example, if no audiences are in the setting,
   * LibraryService API will only accept JWTs with the following audience
   * "https://library-example.googleapis.com/google.example.library.v1.LibraryService".
   *
   * Example:
   *
   *     audiences: bookstore_android.apps.googleusercontent.com,
   *                bookstore_web.apps.googleusercontent.com
   * </pre>
   *
   * <code>string audiences = 2;</code>
   *
   * @return The audiences.
   */
  java.lang.String getAudiences();

  /**
   *
   *
   * <pre>
   * NOTE: This will be deprecated soon, once AuthProvider.audiences is
   * implemented and accepted in all the runtime components.
   *
   * The list of JWT
   * [audiences](https://tools.ietf.org/html/draft-ietf-oauth-json-web-token-32#section-4.1.3).
   * that are allowed to access. A JWT containing any of these audiences will
   * be accepted. When this setting is absent, only JWTs with audience
   * "https://[Service_name][google.api.Service.name]/[API_name][google.protobuf.Api.name]"
   * will be accepted. For example, if no audiences are in the setting,
   * LibraryService API will only accept JWTs with the following audience
   * "https://library-example.googleapis.com/google.example.library.v1.LibraryService".
   *
   * Example:
   *
   *     audiences: bookstore_android.apps.googleusercontent.com,
   *                bookstore_web.apps.googleusercontent.com
   * </pre>
   *
   * <code>string audiences = 2;</code>
   *
   * @return The bytes for audiences.
   */
  com.google.protobuf.ByteString getAudiencesBytes();
}
