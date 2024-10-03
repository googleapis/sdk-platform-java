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
// source: google/iam/v2beta/deny.proto

// Protobuf Java Version: 3.25.5
package com.google.iam.v2beta;

public interface DenyRuleOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.iam.v2beta.DenyRule)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The identities that are prevented from using one or more permissions on
   * Google Cloud resources. This field can contain the following values:
   *
   * * `principalSet://goog/public:all`: A special identifier that represents
   *   any principal that is on the internet, even if they do not have a Google
   *   Account or are not logged in.
   *
   * * `principal://goog/subject/{email_id}`: A specific Google Account.
   *   Includes Gmail, Cloud Identity, and Google Workspace user accounts. For
   *   example, `principal://goog/subject/alice&#64;example.com`.
   *
   * * `deleted:principal://goog/subject/{email_id}?uid={uid}`: A specific
   *   Google Account that was deleted recently. For example,
   *   `deleted:principal://goog/subject/alice&#64;example.com?uid=1234567890`. If
   *   the Google Account is recovered, this identifier reverts to the standard
   *   identifier for a Google Account.
   *
   * * `principalSet://goog/group/{group_id}`: A Google group. For example,
   *   `principalSet://goog/group/admins&#64;example.com`.
   *
   * * `deleted:principalSet://goog/group/{group_id}?uid={uid}`: A Google group
   *   that was deleted recently. For example,
   *   `deleted:principalSet://goog/group/admins&#64;example.com?uid=1234567890`. If
   *   the Google group is restored, this identifier reverts to the standard
   *   identifier for a Google group.
   *
   * * `principal://iam.googleapis.com/projects/-/serviceAccounts/{service_account_id}`:
   *   A Google Cloud service account. For example,
   *   `principal://iam.googleapis.com/projects/-/serviceAccounts/my-service-account&#64;iam.gserviceaccount.com`.
   *
   * * `deleted:principal://iam.googleapis.com/projects/-/serviceAccounts/{service_account_id}?uid={uid}`:
   *   A Google Cloud service account that was deleted recently. For example,
   *   `deleted:principal://iam.googleapis.com/projects/-/serviceAccounts/my-service-account&#64;iam.gserviceaccount.com?uid=1234567890`.
   *   If the service account is undeleted, this identifier reverts to the
   *   standard identifier for a service account.
   *
   * * `principalSet://goog/cloudIdentityCustomerId/{customer_id}`: All of the
   *   principals associated with the specified Google Workspace or Cloud
   *   Identity customer ID. For example,
   *   `principalSet://goog/cloudIdentityCustomerId/C01Abc35`.
   * </pre>
   *
   * <code>repeated string denied_principals = 1;</code>
   *
   * @return A list containing the deniedPrincipals.
   */
  java.util.List<java.lang.String> getDeniedPrincipalsList();

  /**
   *
   *
   * <pre>
   * The identities that are prevented from using one or more permissions on
   * Google Cloud resources. This field can contain the following values:
   *
   * * `principalSet://goog/public:all`: A special identifier that represents
   *   any principal that is on the internet, even if they do not have a Google
   *   Account or are not logged in.
   *
   * * `principal://goog/subject/{email_id}`: A specific Google Account.
   *   Includes Gmail, Cloud Identity, and Google Workspace user accounts. For
   *   example, `principal://goog/subject/alice&#64;example.com`.
   *
   * * `deleted:principal://goog/subject/{email_id}?uid={uid}`: A specific
   *   Google Account that was deleted recently. For example,
   *   `deleted:principal://goog/subject/alice&#64;example.com?uid=1234567890`. If
   *   the Google Account is recovered, this identifier reverts to the standard
   *   identifier for a Google Account.
   *
   * * `principalSet://goog/group/{group_id}`: A Google group. For example,
   *   `principalSet://goog/group/admins&#64;example.com`.
   *
   * * `deleted:principalSet://goog/group/{group_id}?uid={uid}`: A Google group
   *   that was deleted recently. For example,
   *   `deleted:principalSet://goog/group/admins&#64;example.com?uid=1234567890`. If
   *   the Google group is restored, this identifier reverts to the standard
   *   identifier for a Google group.
   *
   * * `principal://iam.googleapis.com/projects/-/serviceAccounts/{service_account_id}`:
   *   A Google Cloud service account. For example,
   *   `principal://iam.googleapis.com/projects/-/serviceAccounts/my-service-account&#64;iam.gserviceaccount.com`.
   *
   * * `deleted:principal://iam.googleapis.com/projects/-/serviceAccounts/{service_account_id}?uid={uid}`:
   *   A Google Cloud service account that was deleted recently. For example,
   *   `deleted:principal://iam.googleapis.com/projects/-/serviceAccounts/my-service-account&#64;iam.gserviceaccount.com?uid=1234567890`.
   *   If the service account is undeleted, this identifier reverts to the
   *   standard identifier for a service account.
   *
   * * `principalSet://goog/cloudIdentityCustomerId/{customer_id}`: All of the
   *   principals associated with the specified Google Workspace or Cloud
   *   Identity customer ID. For example,
   *   `principalSet://goog/cloudIdentityCustomerId/C01Abc35`.
   * </pre>
   *
   * <code>repeated string denied_principals = 1;</code>
   *
   * @return The count of deniedPrincipals.
   */
  int getDeniedPrincipalsCount();

  /**
   *
   *
   * <pre>
   * The identities that are prevented from using one or more permissions on
   * Google Cloud resources. This field can contain the following values:
   *
   * * `principalSet://goog/public:all`: A special identifier that represents
   *   any principal that is on the internet, even if they do not have a Google
   *   Account or are not logged in.
   *
   * * `principal://goog/subject/{email_id}`: A specific Google Account.
   *   Includes Gmail, Cloud Identity, and Google Workspace user accounts. For
   *   example, `principal://goog/subject/alice&#64;example.com`.
   *
   * * `deleted:principal://goog/subject/{email_id}?uid={uid}`: A specific
   *   Google Account that was deleted recently. For example,
   *   `deleted:principal://goog/subject/alice&#64;example.com?uid=1234567890`. If
   *   the Google Account is recovered, this identifier reverts to the standard
   *   identifier for a Google Account.
   *
   * * `principalSet://goog/group/{group_id}`: A Google group. For example,
   *   `principalSet://goog/group/admins&#64;example.com`.
   *
   * * `deleted:principalSet://goog/group/{group_id}?uid={uid}`: A Google group
   *   that was deleted recently. For example,
   *   `deleted:principalSet://goog/group/admins&#64;example.com?uid=1234567890`. If
   *   the Google group is restored, this identifier reverts to the standard
   *   identifier for a Google group.
   *
   * * `principal://iam.googleapis.com/projects/-/serviceAccounts/{service_account_id}`:
   *   A Google Cloud service account. For example,
   *   `principal://iam.googleapis.com/projects/-/serviceAccounts/my-service-account&#64;iam.gserviceaccount.com`.
   *
   * * `deleted:principal://iam.googleapis.com/projects/-/serviceAccounts/{service_account_id}?uid={uid}`:
   *   A Google Cloud service account that was deleted recently. For example,
   *   `deleted:principal://iam.googleapis.com/projects/-/serviceAccounts/my-service-account&#64;iam.gserviceaccount.com?uid=1234567890`.
   *   If the service account is undeleted, this identifier reverts to the
   *   standard identifier for a service account.
   *
   * * `principalSet://goog/cloudIdentityCustomerId/{customer_id}`: All of the
   *   principals associated with the specified Google Workspace or Cloud
   *   Identity customer ID. For example,
   *   `principalSet://goog/cloudIdentityCustomerId/C01Abc35`.
   * </pre>
   *
   * <code>repeated string denied_principals = 1;</code>
   *
   * @param index The index of the element to return.
   * @return The deniedPrincipals at the given index.
   */
  java.lang.String getDeniedPrincipals(int index);

  /**
   *
   *
   * <pre>
   * The identities that are prevented from using one or more permissions on
   * Google Cloud resources. This field can contain the following values:
   *
   * * `principalSet://goog/public:all`: A special identifier that represents
   *   any principal that is on the internet, even if they do not have a Google
   *   Account or are not logged in.
   *
   * * `principal://goog/subject/{email_id}`: A specific Google Account.
   *   Includes Gmail, Cloud Identity, and Google Workspace user accounts. For
   *   example, `principal://goog/subject/alice&#64;example.com`.
   *
   * * `deleted:principal://goog/subject/{email_id}?uid={uid}`: A specific
   *   Google Account that was deleted recently. For example,
   *   `deleted:principal://goog/subject/alice&#64;example.com?uid=1234567890`. If
   *   the Google Account is recovered, this identifier reverts to the standard
   *   identifier for a Google Account.
   *
   * * `principalSet://goog/group/{group_id}`: A Google group. For example,
   *   `principalSet://goog/group/admins&#64;example.com`.
   *
   * * `deleted:principalSet://goog/group/{group_id}?uid={uid}`: A Google group
   *   that was deleted recently. For example,
   *   `deleted:principalSet://goog/group/admins&#64;example.com?uid=1234567890`. If
   *   the Google group is restored, this identifier reverts to the standard
   *   identifier for a Google group.
   *
   * * `principal://iam.googleapis.com/projects/-/serviceAccounts/{service_account_id}`:
   *   A Google Cloud service account. For example,
   *   `principal://iam.googleapis.com/projects/-/serviceAccounts/my-service-account&#64;iam.gserviceaccount.com`.
   *
   * * `deleted:principal://iam.googleapis.com/projects/-/serviceAccounts/{service_account_id}?uid={uid}`:
   *   A Google Cloud service account that was deleted recently. For example,
   *   `deleted:principal://iam.googleapis.com/projects/-/serviceAccounts/my-service-account&#64;iam.gserviceaccount.com?uid=1234567890`.
   *   If the service account is undeleted, this identifier reverts to the
   *   standard identifier for a service account.
   *
   * * `principalSet://goog/cloudIdentityCustomerId/{customer_id}`: All of the
   *   principals associated with the specified Google Workspace or Cloud
   *   Identity customer ID. For example,
   *   `principalSet://goog/cloudIdentityCustomerId/C01Abc35`.
   * </pre>
   *
   * <code>repeated string denied_principals = 1;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the deniedPrincipals at the given index.
   */
  com.google.protobuf.ByteString getDeniedPrincipalsBytes(int index);

  /**
   *
   *
   * <pre>
   * The identities that are excluded from the deny rule, even if they are
   * listed in the `denied_principals`. For example, you could add a Google
   * group to the `denied_principals`, then exclude specific users who belong to
   * that group.
   *
   * This field can contain the same values as the `denied_principals` field,
   * excluding `principalSet://goog/public:all`, which represents all users on
   * the internet.
   * </pre>
   *
   * <code>repeated string exception_principals = 2;</code>
   *
   * @return A list containing the exceptionPrincipals.
   */
  java.util.List<java.lang.String> getExceptionPrincipalsList();

  /**
   *
   *
   * <pre>
   * The identities that are excluded from the deny rule, even if they are
   * listed in the `denied_principals`. For example, you could add a Google
   * group to the `denied_principals`, then exclude specific users who belong to
   * that group.
   *
   * This field can contain the same values as the `denied_principals` field,
   * excluding `principalSet://goog/public:all`, which represents all users on
   * the internet.
   * </pre>
   *
   * <code>repeated string exception_principals = 2;</code>
   *
   * @return The count of exceptionPrincipals.
   */
  int getExceptionPrincipalsCount();

  /**
   *
   *
   * <pre>
   * The identities that are excluded from the deny rule, even if they are
   * listed in the `denied_principals`. For example, you could add a Google
   * group to the `denied_principals`, then exclude specific users who belong to
   * that group.
   *
   * This field can contain the same values as the `denied_principals` field,
   * excluding `principalSet://goog/public:all`, which represents all users on
   * the internet.
   * </pre>
   *
   * <code>repeated string exception_principals = 2;</code>
   *
   * @param index The index of the element to return.
   * @return The exceptionPrincipals at the given index.
   */
  java.lang.String getExceptionPrincipals(int index);

  /**
   *
   *
   * <pre>
   * The identities that are excluded from the deny rule, even if they are
   * listed in the `denied_principals`. For example, you could add a Google
   * group to the `denied_principals`, then exclude specific users who belong to
   * that group.
   *
   * This field can contain the same values as the `denied_principals` field,
   * excluding `principalSet://goog/public:all`, which represents all users on
   * the internet.
   * </pre>
   *
   * <code>repeated string exception_principals = 2;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the exceptionPrincipals at the given index.
   */
  com.google.protobuf.ByteString getExceptionPrincipalsBytes(int index);

  /**
   *
   *
   * <pre>
   * The permissions that are explicitly denied by this rule. Each permission
   * uses the format `{service_fqdn}/{resource}.{verb}`, where `{service_fqdn}`
   * is the fully qualified domain name for the service. For example,
   * `iam.googleapis.com/roles.list`.
   * </pre>
   *
   * <code>repeated string denied_permissions = 3;</code>
   *
   * @return A list containing the deniedPermissions.
   */
  java.util.List<java.lang.String> getDeniedPermissionsList();

  /**
   *
   *
   * <pre>
   * The permissions that are explicitly denied by this rule. Each permission
   * uses the format `{service_fqdn}/{resource}.{verb}`, where `{service_fqdn}`
   * is the fully qualified domain name for the service. For example,
   * `iam.googleapis.com/roles.list`.
   * </pre>
   *
   * <code>repeated string denied_permissions = 3;</code>
   *
   * @return The count of deniedPermissions.
   */
  int getDeniedPermissionsCount();

  /**
   *
   *
   * <pre>
   * The permissions that are explicitly denied by this rule. Each permission
   * uses the format `{service_fqdn}/{resource}.{verb}`, where `{service_fqdn}`
   * is the fully qualified domain name for the service. For example,
   * `iam.googleapis.com/roles.list`.
   * </pre>
   *
   * <code>repeated string denied_permissions = 3;</code>
   *
   * @param index The index of the element to return.
   * @return The deniedPermissions at the given index.
   */
  java.lang.String getDeniedPermissions(int index);

  /**
   *
   *
   * <pre>
   * The permissions that are explicitly denied by this rule. Each permission
   * uses the format `{service_fqdn}/{resource}.{verb}`, where `{service_fqdn}`
   * is the fully qualified domain name for the service. For example,
   * `iam.googleapis.com/roles.list`.
   * </pre>
   *
   * <code>repeated string denied_permissions = 3;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the deniedPermissions at the given index.
   */
  com.google.protobuf.ByteString getDeniedPermissionsBytes(int index);

  /**
   *
   *
   * <pre>
   * Specifies the permissions that this rule excludes from the set of denied
   * permissions given by `denied_permissions`. If a permission appears in
   * `denied_permissions` _and_ in `exception_permissions` then it will _not_ be
   * denied.
   *
   * The excluded permissions can be specified using the same syntax as
   * `denied_permissions`.
   * </pre>
   *
   * <code>repeated string exception_permissions = 4;</code>
   *
   * @return A list containing the exceptionPermissions.
   */
  java.util.List<java.lang.String> getExceptionPermissionsList();

  /**
   *
   *
   * <pre>
   * Specifies the permissions that this rule excludes from the set of denied
   * permissions given by `denied_permissions`. If a permission appears in
   * `denied_permissions` _and_ in `exception_permissions` then it will _not_ be
   * denied.
   *
   * The excluded permissions can be specified using the same syntax as
   * `denied_permissions`.
   * </pre>
   *
   * <code>repeated string exception_permissions = 4;</code>
   *
   * @return The count of exceptionPermissions.
   */
  int getExceptionPermissionsCount();

  /**
   *
   *
   * <pre>
   * Specifies the permissions that this rule excludes from the set of denied
   * permissions given by `denied_permissions`. If a permission appears in
   * `denied_permissions` _and_ in `exception_permissions` then it will _not_ be
   * denied.
   *
   * The excluded permissions can be specified using the same syntax as
   * `denied_permissions`.
   * </pre>
   *
   * <code>repeated string exception_permissions = 4;</code>
   *
   * @param index The index of the element to return.
   * @return The exceptionPermissions at the given index.
   */
  java.lang.String getExceptionPermissions(int index);

  /**
   *
   *
   * <pre>
   * Specifies the permissions that this rule excludes from the set of denied
   * permissions given by `denied_permissions`. If a permission appears in
   * `denied_permissions` _and_ in `exception_permissions` then it will _not_ be
   * denied.
   *
   * The excluded permissions can be specified using the same syntax as
   * `denied_permissions`.
   * </pre>
   *
   * <code>repeated string exception_permissions = 4;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the exceptionPermissions at the given index.
   */
  com.google.protobuf.ByteString getExceptionPermissionsBytes(int index);

  /**
   *
   *
   * <pre>
   * The condition that determines whether this deny rule applies to a request.
   * If the condition expression evaluates to `true`, then the deny rule is
   * applied; otherwise, the deny rule is not applied.
   *
   * Each deny rule is evaluated independently. If this deny rule does not apply
   * to a request, other deny rules might still apply.
   *
   * The condition can use CEL functions that evaluate
   * [resource
   * tags](https://cloud.google.com/iam/help/conditions/resource-tags). Other
   * functions and operators are not supported.
   * </pre>
   *
   * <code>.google.type.Expr denial_condition = 5;</code>
   *
   * @return Whether the denialCondition field is set.
   */
  boolean hasDenialCondition();

  /**
   *
   *
   * <pre>
   * The condition that determines whether this deny rule applies to a request.
   * If the condition expression evaluates to `true`, then the deny rule is
   * applied; otherwise, the deny rule is not applied.
   *
   * Each deny rule is evaluated independently. If this deny rule does not apply
   * to a request, other deny rules might still apply.
   *
   * The condition can use CEL functions that evaluate
   * [resource
   * tags](https://cloud.google.com/iam/help/conditions/resource-tags). Other
   * functions and operators are not supported.
   * </pre>
   *
   * <code>.google.type.Expr denial_condition = 5;</code>
   *
   * @return The denialCondition.
   */
  com.google.type.Expr getDenialCondition();

  /**
   *
   *
   * <pre>
   * The condition that determines whether this deny rule applies to a request.
   * If the condition expression evaluates to `true`, then the deny rule is
   * applied; otherwise, the deny rule is not applied.
   *
   * Each deny rule is evaluated independently. If this deny rule does not apply
   * to a request, other deny rules might still apply.
   *
   * The condition can use CEL functions that evaluate
   * [resource
   * tags](https://cloud.google.com/iam/help/conditions/resource-tags). Other
   * functions and operators are not supported.
   * </pre>
   *
   * <code>.google.type.Expr denial_condition = 5;</code>
   */
  com.google.type.ExprOrBuilder getDenialConditionOrBuilder();
}
