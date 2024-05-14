/*
 * Copyright 2020 Google LLC
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
// source: google/api/client.proto

// Protobuf Java Version: 3.25.3
package com.google.api;

public interface PublishingOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.Publishing)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * A list of API method settings, e.g. the behavior for methods that use the
   * long-running operation pattern.
   * </pre>
   *
   * <code>repeated .google.api.MethodSettings method_settings = 2;</code>
   */
  java.util.List<com.google.api.MethodSettings> getMethodSettingsList();
  /**
   *
   *
   * <pre>
   * A list of API method settings, e.g. the behavior for methods that use the
   * long-running operation pattern.
   * </pre>
   *
   * <code>repeated .google.api.MethodSettings method_settings = 2;</code>
   */
  com.google.api.MethodSettings getMethodSettings(int index);
  /**
   *
   *
   * <pre>
   * A list of API method settings, e.g. the behavior for methods that use the
   * long-running operation pattern.
   * </pre>
   *
   * <code>repeated .google.api.MethodSettings method_settings = 2;</code>
   */
  int getMethodSettingsCount();
  /**
   *
   *
   * <pre>
   * A list of API method settings, e.g. the behavior for methods that use the
   * long-running operation pattern.
   * </pre>
   *
   * <code>repeated .google.api.MethodSettings method_settings = 2;</code>
   */
  java.util.List<? extends com.google.api.MethodSettingsOrBuilder> getMethodSettingsOrBuilderList();
  /**
   *
   *
   * <pre>
   * A list of API method settings, e.g. the behavior for methods that use the
   * long-running operation pattern.
   * </pre>
   *
   * <code>repeated .google.api.MethodSettings method_settings = 2;</code>
   */
  com.google.api.MethodSettingsOrBuilder getMethodSettingsOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * Link to a *public* URI where users can report issues.  Example:
   * https://issuetracker.google.com/issues/new?component=190865&amp;template=1161103
   * </pre>
   *
   * <code>string new_issue_uri = 101;</code>
   *
   * @return The newIssueUri.
   */
  java.lang.String getNewIssueUri();
  /**
   *
   *
   * <pre>
   * Link to a *public* URI where users can report issues.  Example:
   * https://issuetracker.google.com/issues/new?component=190865&amp;template=1161103
   * </pre>
   *
   * <code>string new_issue_uri = 101;</code>
   *
   * @return The bytes for newIssueUri.
   */
  com.google.protobuf.ByteString getNewIssueUriBytes();

  /**
   *
   *
   * <pre>
   * Link to product home page.  Example:
   * https://cloud.google.com/asset-inventory/docs/overview
   * </pre>
   *
   * <code>string documentation_uri = 102;</code>
   *
   * @return The documentationUri.
   */
  java.lang.String getDocumentationUri();
  /**
   *
   *
   * <pre>
   * Link to product home page.  Example:
   * https://cloud.google.com/asset-inventory/docs/overview
   * </pre>
   *
   * <code>string documentation_uri = 102;</code>
   *
   * @return The bytes for documentationUri.
   */
  com.google.protobuf.ByteString getDocumentationUriBytes();

  /**
   *
   *
   * <pre>
   * Used as a tracking tag when collecting data about the APIs developer
   * relations artifacts like docs, packages delivered to package managers,
   * etc.  Example: "speech".
   * </pre>
   *
   * <code>string api_short_name = 103;</code>
   *
   * @return The apiShortName.
   */
  java.lang.String getApiShortName();
  /**
   *
   *
   * <pre>
   * Used as a tracking tag when collecting data about the APIs developer
   * relations artifacts like docs, packages delivered to package managers,
   * etc.  Example: "speech".
   * </pre>
   *
   * <code>string api_short_name = 103;</code>
   *
   * @return The bytes for apiShortName.
   */
  com.google.protobuf.ByteString getApiShortNameBytes();

  /**
   *
   *
   * <pre>
   * GitHub label to apply to issues and pull requests opened for this API.
   * </pre>
   *
   * <code>string github_label = 104;</code>
   *
   * @return The githubLabel.
   */
  java.lang.String getGithubLabel();
  /**
   *
   *
   * <pre>
   * GitHub label to apply to issues and pull requests opened for this API.
   * </pre>
   *
   * <code>string github_label = 104;</code>
   *
   * @return The bytes for githubLabel.
   */
  com.google.protobuf.ByteString getGithubLabelBytes();

  /**
   *
   *
   * <pre>
   * GitHub teams to be added to CODEOWNERS in the directory in GitHub
   * containing source code for the client libraries for this API.
   * </pre>
   *
   * <code>repeated string codeowner_github_teams = 105;</code>
   *
   * @return A list containing the codeownerGithubTeams.
   */
  java.util.List<java.lang.String> getCodeownerGithubTeamsList();
  /**
   *
   *
   * <pre>
   * GitHub teams to be added to CODEOWNERS in the directory in GitHub
   * containing source code for the client libraries for this API.
   * </pre>
   *
   * <code>repeated string codeowner_github_teams = 105;</code>
   *
   * @return The count of codeownerGithubTeams.
   */
  int getCodeownerGithubTeamsCount();
  /**
   *
   *
   * <pre>
   * GitHub teams to be added to CODEOWNERS in the directory in GitHub
   * containing source code for the client libraries for this API.
   * </pre>
   *
   * <code>repeated string codeowner_github_teams = 105;</code>
   *
   * @param index The index of the element to return.
   * @return The codeownerGithubTeams at the given index.
   */
  java.lang.String getCodeownerGithubTeams(int index);
  /**
   *
   *
   * <pre>
   * GitHub teams to be added to CODEOWNERS in the directory in GitHub
   * containing source code for the client libraries for this API.
   * </pre>
   *
   * <code>repeated string codeowner_github_teams = 105;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the codeownerGithubTeams at the given index.
   */
  com.google.protobuf.ByteString getCodeownerGithubTeamsBytes(int index);

  /**
   *
   *
   * <pre>
   * A prefix used in sample code when demarking regions to be included in
   * documentation.
   * </pre>
   *
   * <code>string doc_tag_prefix = 106;</code>
   *
   * @return The docTagPrefix.
   */
  java.lang.String getDocTagPrefix();
  /**
   *
   *
   * <pre>
   * A prefix used in sample code when demarking regions to be included in
   * documentation.
   * </pre>
   *
   * <code>string doc_tag_prefix = 106;</code>
   *
   * @return The bytes for docTagPrefix.
   */
  com.google.protobuf.ByteString getDocTagPrefixBytes();

  /**
   *
   *
   * <pre>
   * For whom the client library is being published.
   * </pre>
   *
   * <code>.google.api.ClientLibraryOrganization organization = 107;</code>
   *
   * @return The enum numeric value on the wire for organization.
   */
  int getOrganizationValue();
  /**
   *
   *
   * <pre>
   * For whom the client library is being published.
   * </pre>
   *
   * <code>.google.api.ClientLibraryOrganization organization = 107;</code>
   *
   * @return The organization.
   */
  com.google.api.ClientLibraryOrganization getOrganization();

  /**
   *
   *
   * <pre>
   * Client library settings.  If the same version string appears multiple
   * times in this list, then the last one wins.  Settings from earlier
   * settings with the same version string are discarded.
   * </pre>
   *
   * <code>repeated .google.api.ClientLibrarySettings library_settings = 109;</code>
   */
  java.util.List<com.google.api.ClientLibrarySettings> getLibrarySettingsList();
  /**
   *
   *
   * <pre>
   * Client library settings.  If the same version string appears multiple
   * times in this list, then the last one wins.  Settings from earlier
   * settings with the same version string are discarded.
   * </pre>
   *
   * <code>repeated .google.api.ClientLibrarySettings library_settings = 109;</code>
   */
  com.google.api.ClientLibrarySettings getLibrarySettings(int index);
  /**
   *
   *
   * <pre>
   * Client library settings.  If the same version string appears multiple
   * times in this list, then the last one wins.  Settings from earlier
   * settings with the same version string are discarded.
   * </pre>
   *
   * <code>repeated .google.api.ClientLibrarySettings library_settings = 109;</code>
   */
  int getLibrarySettingsCount();
  /**
   *
   *
   * <pre>
   * Client library settings.  If the same version string appears multiple
   * times in this list, then the last one wins.  Settings from earlier
   * settings with the same version string are discarded.
   * </pre>
   *
   * <code>repeated .google.api.ClientLibrarySettings library_settings = 109;</code>
   */
  java.util.List<? extends com.google.api.ClientLibrarySettingsOrBuilder>
      getLibrarySettingsOrBuilderList();
  /**
   *
   *
   * <pre>
   * Client library settings.  If the same version string appears multiple
   * times in this list, then the last one wins.  Settings from earlier
   * settings with the same version string are discarded.
   * </pre>
   *
   * <code>repeated .google.api.ClientLibrarySettings library_settings = 109;</code>
   */
  com.google.api.ClientLibrarySettingsOrBuilder getLibrarySettingsOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * Optional link to proto reference documentation.  Example:
   * https://cloud.google.com/pubsub/lite/docs/reference/rpc
   * </pre>
   *
   * <code>string proto_reference_documentation_uri = 110;</code>
   *
   * @return The protoReferenceDocumentationUri.
   */
  java.lang.String getProtoReferenceDocumentationUri();
  /**
   *
   *
   * <pre>
   * Optional link to proto reference documentation.  Example:
   * https://cloud.google.com/pubsub/lite/docs/reference/rpc
   * </pre>
   *
   * <code>string proto_reference_documentation_uri = 110;</code>
   *
   * @return The bytes for protoReferenceDocumentationUri.
   */
  com.google.protobuf.ByteString getProtoReferenceDocumentationUriBytes();

  /**
   *
   *
   * <pre>
   * Optional link to REST reference documentation.  Example:
   * https://cloud.google.com/pubsub/lite/docs/reference/rest
   * </pre>
   *
   * <code>string rest_reference_documentation_uri = 111;</code>
   *
   * @return The restReferenceDocumentationUri.
   */
  java.lang.String getRestReferenceDocumentationUri();
  /**
   *
   *
   * <pre>
   * Optional link to REST reference documentation.  Example:
   * https://cloud.google.com/pubsub/lite/docs/reference/rest
   * </pre>
   *
   * <code>string rest_reference_documentation_uri = 111;</code>
   *
   * @return The bytes for restReferenceDocumentationUri.
   */
  com.google.protobuf.ByteString getRestReferenceDocumentationUriBytes();
}
