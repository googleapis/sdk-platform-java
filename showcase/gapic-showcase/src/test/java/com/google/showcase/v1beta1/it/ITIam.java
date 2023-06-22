/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.showcase.v1beta1.it;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.api.gax.rpc.NotFoundException;
import com.google.common.collect.ImmutableList;
import com.google.iam.v1.Binding;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.showcase.v1beta1.CreateUserRequest;
import com.google.showcase.v1beta1.IdentityClient;
import com.google.showcase.v1beta1.User;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITIam {
  private static final Policy DEFAULT_POLICY =
      Policy.newBuilder()
          .addBindings(Binding.newBuilder().setRole("foo.editor").addMembers("allUsers"))
          .build();
  private static IdentityClient grpcClient;
  private static IdentityClient httpjsonClient;
  private String userId;
  private String resourceName;

  @BeforeClass
  public static void createClients() throws Exception {
    grpcClient = TestClientInitializer.createGrpcIdentityClient();
    httpjsonClient = TestClientInitializer.createHttpJsonIdentityClient();
  }

  @Before
  public void setupTests() {
    userId = UUID.randomUUID().toString().substring(0, 8);
    resourceName = "users/" + userId;
  }

  @AfterClass
  public static void destroyClients() throws InterruptedException {
    grpcClient.close();
    httpjsonClient.close();

    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
    httpjsonClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testGrpc_setIamPolicy() {
    User user =
        grpcClient.createUser(
            CreateUserRequest.newBuilder()
                .setUser(User.newBuilder().setDisplayName(userId).setEmail(userId + "@google.com"))
                .build());
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder()
            .setPolicy(DEFAULT_POLICY)
            .setResource(user.getName())
            .build();
    Policy policy = grpcClient.setIamPolicy(policyRequest);
    assertThat(policy).isEqualTo(DEFAULT_POLICY);
  }

  @Test
  public void testHttpJson_setIamPolicy() {
    User user =
        httpjsonClient.createUser(
            CreateUserRequest.newBuilder()
                .setUser(User.newBuilder().setDisplayName(userId).setEmail(userId + "@google.com"))
                .build());
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder()
            .setPolicy(DEFAULT_POLICY)
            .setResource(user.getName())
            .build();
    Policy policy = httpjsonClient.setIamPolicy(policyRequest);
    assertThat(policy).isEqualTo(DEFAULT_POLICY);
  }

  @Test
  public void testGrpc_setIamPolicy_missingResource() {
    assertThrows(
        InvalidArgumentException.class,
        () -> grpcClient.setIamPolicy(SetIamPolicyRequest.newBuilder().build()));
  }

  @Test
  public void testHttpJson_setIamPolicy_missingResource() {
    assertThrows(
        InvalidArgumentException.class,
        () -> httpjsonClient.setIamPolicy(SetIamPolicyRequest.newBuilder().build()));
  }

  @Test
  public void testGrpc_setIamPolicy_missingPolicy() {
    assertThrows(
        InvalidArgumentException.class,
        () ->
            grpcClient.setIamPolicy(
                SetIamPolicyRequest.newBuilder().setResource(resourceName).build()));
  }

  @Test
  public void testHttpJson_setIamPolicy_missingPolicy() {
    assertThrows(
        InvalidArgumentException.class,
        () ->
            httpjsonClient.setIamPolicy(
                SetIamPolicyRequest.newBuilder().setResource(resourceName).build()));
  }

  @Test
  public void testGrpc_getIamPolicy() {
    User user =
        grpcClient.createUser(
            CreateUserRequest.newBuilder()
                .setUser(User.newBuilder().setDisplayName(userId).setEmail(userId + "@google.com"))
                .build());
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder()
            .setPolicy(DEFAULT_POLICY)
            .setResource(user.getName())
            .build();
    grpcClient.setIamPolicy(policyRequest);

    Policy policy =
        grpcClient.getIamPolicy(
            GetIamPolicyRequest.newBuilder().setResource(policyRequest.getResource()).build());
    assertThat(policy).isEqualTo(DEFAULT_POLICY);
  }

  @Test
  public void testHttpJson_getIamPolicy() {
    User user =
        httpjsonClient.createUser(
            CreateUserRequest.newBuilder()
                .setUser(User.newBuilder().setDisplayName(userId).setEmail(userId + "@google.com"))
                .build());
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder()
            .setPolicy(DEFAULT_POLICY)
            .setResource(user.getName())
            .build();
    httpjsonClient.setIamPolicy(policyRequest);

    Policy policy =
        httpjsonClient.getIamPolicy(
            GetIamPolicyRequest.newBuilder().setResource(policyRequest.getResource()).build());
    assertThat(policy).isEqualTo(DEFAULT_POLICY);
  }

  @Test
  public void testGrpc_getIamPolicy_doesNotExist() {
    assertThrows(
        NotFoundException.class,
        () ->
            grpcClient.getIamPolicy(
                GetIamPolicyRequest.newBuilder().setResource(resourceName).build()));
  }

  @Test
  public void testHttpJson_getIamPolicy_doesNotExist() {
    assertThrows(
        NotFoundException.class,
        () ->
            httpjsonClient.getIamPolicy(
                GetIamPolicyRequest.newBuilder().setResource(resourceName).build()));
  }

  @Test
  public void testGrpc_getIamPolicy_missingResource() {
    assertThrows(
        InvalidArgumentException.class,
        () -> grpcClient.getIamPolicy(GetIamPolicyRequest.newBuilder().build()));
  }

  @Test
  public void testHttpJson_getIamPolicy_missingResource() {
    assertThrows(
        InvalidArgumentException.class,
        () -> httpjsonClient.getIamPolicy(GetIamPolicyRequest.newBuilder().build()));
  }

  @Test
  public void testGrpc_testIamPermissions() {
    grpcClient.createUser(
        CreateUserRequest.newBuilder()
            .setUser(User.newBuilder().setDisplayName(userId).setEmail(userId + "@google.com"))
            .build());
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder()
            .setPolicy(DEFAULT_POLICY)
            .setResource(resourceName)
            .build();
    grpcClient.setIamPolicy(policyRequest);
    List<String> permissions = ImmutableList.of("foo.create");
    TestIamPermissionsResponse testIamPermissionsResponse =
        grpcClient.testIamPermissions(
            TestIamPermissionsRequest.newBuilder()
                .setResource(policyRequest.getResource())
                .addAllPermissions(permissions)
                .build());
    assertThat(testIamPermissionsResponse.getPermissionsList())
        .containsExactlyElementsIn(permissions);
  }

  @Test
  public void testHttpJson_testIamPermissions() {
    User user =
        httpjsonClient.createUser(
            CreateUserRequest.newBuilder()
                .setUser(User.newBuilder().setDisplayName(userId).setEmail(userId + "@google.com"))
                .build());
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder()
            .setPolicy(DEFAULT_POLICY)
            .setResource(user.getName())
            .build();
    httpjsonClient.setIamPolicy(policyRequest);
    List<String> permissions = ImmutableList.of("foo.create");
    TestIamPermissionsResponse testIamPermissionsResponse =
        httpjsonClient.testIamPermissions(
            TestIamPermissionsRequest.newBuilder()
                .setResource(policyRequest.getResource())
                .addAllPermissions(permissions)
                .build());
    assertThat(testIamPermissionsResponse.getPermissionsList())
        .containsExactlyElementsIn(permissions);
  }

  @Test
  public void testGrpc_testIamPermissions_doesNotExist() {
    assertThrows(
        NotFoundException.class,
        () ->
            grpcClient.testIamPermissions(
                TestIamPermissionsRequest.newBuilder().setResource(resourceName).build()));
  }

  @Test
  public void testHttpJson_testIamPermissions_doesNotExist() {
    assertThrows(
        NotFoundException.class,
        () ->
            httpjsonClient.testIamPermissions(
                TestIamPermissionsRequest.newBuilder().setResource(resourceName).build()));
  }

  @Test
  public void testGrpc_testIamPermissions_missingResource() {
    assertThrows(
        InvalidArgumentException.class,
        () -> grpcClient.testIamPermissions(TestIamPermissionsRequest.newBuilder().build()));
  }

  @Test
  public void testHttpJson_testIamPermissions_missingResource() {
    assertThrows(
        InvalidArgumentException.class,
        () -> httpjsonClient.testIamPermissions(TestIamPermissionsRequest.newBuilder().build()));
  }
}
