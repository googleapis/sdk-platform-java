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
// source: google/api/documentation.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

public interface DocumentationOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.Documentation)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * A short description of what the service does. The summary must be plain
   * text. It becomes the overview of the service displayed in Google Cloud
   * Console.
   * NOTE: This field is equivalent to the standard field `description`.
   * </pre>
   *
   * <code>string summary = 1;</code>
   *
   * @return The summary.
   */
  java.lang.String getSummary();
  /**
   *
   *
   * <pre>
   * A short description of what the service does. The summary must be plain
   * text. It becomes the overview of the service displayed in Google Cloud
   * Console.
   * NOTE: This field is equivalent to the standard field `description`.
   * </pre>
   *
   * <code>string summary = 1;</code>
   *
   * @return The bytes for summary.
   */
  com.google.protobuf.ByteString getSummaryBytes();

  /**
   *
   *
   * <pre>
   * The top level pages for the documentation set.
   * </pre>
   *
   * <code>repeated .google.api.Page pages = 5;</code>
   */
  java.util.List<com.google.api.Page> getPagesList();
  /**
   *
   *
   * <pre>
   * The top level pages for the documentation set.
   * </pre>
   *
   * <code>repeated .google.api.Page pages = 5;</code>
   */
  com.google.api.Page getPages(int index);
  /**
   *
   *
   * <pre>
   * The top level pages for the documentation set.
   * </pre>
   *
   * <code>repeated .google.api.Page pages = 5;</code>
   */
  int getPagesCount();
  /**
   *
   *
   * <pre>
   * The top level pages for the documentation set.
   * </pre>
   *
   * <code>repeated .google.api.Page pages = 5;</code>
   */
  java.util.List<? extends com.google.api.PageOrBuilder> getPagesOrBuilderList();
  /**
   *
   *
   * <pre>
   * The top level pages for the documentation set.
   * </pre>
   *
   * <code>repeated .google.api.Page pages = 5;</code>
   */
  com.google.api.PageOrBuilder getPagesOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * A list of documentation rules that apply to individual API elements.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.DocumentationRule rules = 3;</code>
   */
  java.util.List<com.google.api.DocumentationRule> getRulesList();
  /**
   *
   *
   * <pre>
   * A list of documentation rules that apply to individual API elements.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.DocumentationRule rules = 3;</code>
   */
  com.google.api.DocumentationRule getRules(int index);
  /**
   *
   *
   * <pre>
   * A list of documentation rules that apply to individual API elements.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.DocumentationRule rules = 3;</code>
   */
  int getRulesCount();
  /**
   *
   *
   * <pre>
   * A list of documentation rules that apply to individual API elements.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.DocumentationRule rules = 3;</code>
   */
  java.util.List<? extends com.google.api.DocumentationRuleOrBuilder> getRulesOrBuilderList();
  /**
   *
   *
   * <pre>
   * A list of documentation rules that apply to individual API elements.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.DocumentationRule rules = 3;</code>
   */
  com.google.api.DocumentationRuleOrBuilder getRulesOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * The URL to the root of documentation.
   * </pre>
   *
   * <code>string documentation_root_url = 4;</code>
   *
   * @return The documentationRootUrl.
   */
  java.lang.String getDocumentationRootUrl();
  /**
   *
   *
   * <pre>
   * The URL to the root of documentation.
   * </pre>
   *
   * <code>string documentation_root_url = 4;</code>
   *
   * @return The bytes for documentationRootUrl.
   */
  com.google.protobuf.ByteString getDocumentationRootUrlBytes();

  /**
   *
   *
   * <pre>
   * Specifies the service root url if the default one (the service name
   * from the yaml file) is not suitable. This can be seen in any fully
   * specified service urls as well as sections that show a base that other
   * urls are relative to.
   * </pre>
   *
   * <code>string service_root_url = 6;</code>
   *
   * @return The serviceRootUrl.
   */
  java.lang.String getServiceRootUrl();
  /**
   *
   *
   * <pre>
   * Specifies the service root url if the default one (the service name
   * from the yaml file) is not suitable. This can be seen in any fully
   * specified service urls as well as sections that show a base that other
   * urls are relative to.
   * </pre>
   *
   * <code>string service_root_url = 6;</code>
   *
   * @return The bytes for serviceRootUrl.
   */
  com.google.protobuf.ByteString getServiceRootUrlBytes();

  /**
   *
   *
   * <pre>
   * Declares a single overview page. For example:
   * &lt;pre&gt;&lt;code&gt;documentation:
   *   summary: ...
   *   overview: &amp;#40;== include overview.md ==&amp;#41;
   * &lt;/code&gt;&lt;/pre&gt;
   * This is a shortcut for the following declaration (using pages style):
   * &lt;pre&gt;&lt;code&gt;documentation:
   *   summary: ...
   *   pages:
   *   - name: Overview
   *     content: &amp;#40;== include overview.md ==&amp;#41;
   * &lt;/code&gt;&lt;/pre&gt;
   * Note: you cannot specify both `overview` field and `pages` field.
   * </pre>
   *
   * <code>string overview = 2;</code>
   *
   * @return The overview.
   */
  java.lang.String getOverview();
  /**
   *
   *
   * <pre>
   * Declares a single overview page. For example:
   * &lt;pre&gt;&lt;code&gt;documentation:
   *   summary: ...
   *   overview: &amp;#40;== include overview.md ==&amp;#41;
   * &lt;/code&gt;&lt;/pre&gt;
   * This is a shortcut for the following declaration (using pages style):
   * &lt;pre&gt;&lt;code&gt;documentation:
   *   summary: ...
   *   pages:
   *   - name: Overview
   *     content: &amp;#40;== include overview.md ==&amp;#41;
   * &lt;/code&gt;&lt;/pre&gt;
   * Note: you cannot specify both `overview` field and `pages` field.
   * </pre>
   *
   * <code>string overview = 2;</code>
   *
   * @return The bytes for overview.
   */
  com.google.protobuf.ByteString getOverviewBytes();
}
