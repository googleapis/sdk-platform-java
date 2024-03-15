/*
 * Copyright 2023 Google LLC
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

// Protobuf Java Version: 3.25.2
package com.google.api;

public interface AuthenticationRuleOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.AuthenticationRule)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Selects the methods to which this rule applies.
   *
   * Refer to [selector][google.api.DocumentationRule.selector] for syntax
   * details.
   * </pre>
   *
   * <code>string selector = 1;</code>
   *
   * @return The selector.
   */
  java.lang.String getSelector();
  /**
   *
   *
   * <pre>
   * Selects the methods to which this rule applies.
   *
   * Refer to [selector][google.api.DocumentationRule.selector] for syntax
   * details.
   * </pre>
   *
   * <code>string selector = 1;</code>
   *
   * @return The bytes for selector.
   */
  com.google.protobuf.ByteString getSelectorBytes();

  /**
   *
   *
   * <pre>
   * The requirements for OAuth credentials.
   * </pre>
   *
   * <code>.google.api.OAuthRequirements oauth = 2;</code>
   *
   * @return Whether the oauth field is set.
   */
  boolean hasOauth();
  /**
   *
   *
   * <pre>
   * The requirements for OAuth credentials.
   * </pre>
   *
   * <code>.google.api.OAuthRequirements oauth = 2;</code>
   *
   * @return The oauth.
   */
  com.google.api.OAuthRequirements getOauth();
  /**
   *
   *
   * <pre>
   * The requirements for OAuth credentials.
   * </pre>
   *
   * <code>.google.api.OAuthRequirements oauth = 2;</code>
   */
  com.google.api.OAuthRequirementsOrBuilder getOauthOrBuilder();

  /**
   *
   *
   * <pre>
   * If true, the service accepts API keys without any other credential.
   * This flag only applies to HTTP and gRPC requests.
   * </pre>
   *
   * <code>bool allow_without_credential = 5;</code>
   *
   * @return The allowWithoutCredential.
   */
  boolean getAllowWithoutCredential();

  /**
   *
   *
   * <pre>
   * Requirements for additional authentication providers.
   * </pre>
   *
   * <code>repeated .google.api.AuthRequirement requirements = 7;</code>
   */
  java.util.List<com.google.api.AuthRequirement> getRequirementsList();
  /**
   *
   *
   * <pre>
   * Requirements for additional authentication providers.
   * </pre>
   *
   * <code>repeated .google.api.AuthRequirement requirements = 7;</code>
   */
  com.google.api.AuthRequirement getRequirements(int index);
  /**
   *
   *
   * <pre>
   * Requirements for additional authentication providers.
   * </pre>
   *
   * <code>repeated .google.api.AuthRequirement requirements = 7;</code>
   */
  int getRequirementsCount();
  /**
   *
   *
   * <pre>
   * Requirements for additional authentication providers.
   * </pre>
   *
   * <code>repeated .google.api.AuthRequirement requirements = 7;</code>
   */
  java.util.List<? extends com.google.api.AuthRequirementOrBuilder> getRequirementsOrBuilderList();
  /**
   *
   *
   * <pre>
   * Requirements for additional authentication providers.
   * </pre>
   *
   * <code>repeated .google.api.AuthRequirement requirements = 7;</code>
   */
  com.google.api.AuthRequirementOrBuilder getRequirementsOrBuilder(int index);
}
