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

public interface AuthenticationOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.Authentication)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * A list of authentication rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.AuthenticationRule rules = 3;</code>
   */
  java.util.List<com.google.api.AuthenticationRule> getRulesList();

  /**
   *
   *
   * <pre>
   * A list of authentication rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.AuthenticationRule rules = 3;</code>
   */
  com.google.api.AuthenticationRule getRules(int index);

  /**
   *
   *
   * <pre>
   * A list of authentication rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.AuthenticationRule rules = 3;</code>
   */
  int getRulesCount();

  /**
   *
   *
   * <pre>
   * A list of authentication rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.AuthenticationRule rules = 3;</code>
   */
  java.util.List<? extends com.google.api.AuthenticationRuleOrBuilder> getRulesOrBuilderList();

  /**
   *
   *
   * <pre>
   * A list of authentication rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.AuthenticationRule rules = 3;</code>
   */
  com.google.api.AuthenticationRuleOrBuilder getRulesOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * Defines a set of authentication providers that a service supports.
   * </pre>
   *
   * <code>repeated .google.api.AuthProvider providers = 4;</code>
   */
  java.util.List<com.google.api.AuthProvider> getProvidersList();

  /**
   *
   *
   * <pre>
   * Defines a set of authentication providers that a service supports.
   * </pre>
   *
   * <code>repeated .google.api.AuthProvider providers = 4;</code>
   */
  com.google.api.AuthProvider getProviders(int index);

  /**
   *
   *
   * <pre>
   * Defines a set of authentication providers that a service supports.
   * </pre>
   *
   * <code>repeated .google.api.AuthProvider providers = 4;</code>
   */
  int getProvidersCount();

  /**
   *
   *
   * <pre>
   * Defines a set of authentication providers that a service supports.
   * </pre>
   *
   * <code>repeated .google.api.AuthProvider providers = 4;</code>
   */
  java.util.List<? extends com.google.api.AuthProviderOrBuilder> getProvidersOrBuilderList();

  /**
   *
   *
   * <pre>
   * Defines a set of authentication providers that a service supports.
   * </pre>
   *
   * <code>repeated .google.api.AuthProvider providers = 4;</code>
   */
  com.google.api.AuthProviderOrBuilder getProvidersOrBuilder(int index);
}
