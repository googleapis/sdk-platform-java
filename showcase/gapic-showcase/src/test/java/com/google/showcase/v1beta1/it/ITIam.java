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
import com.google.showcase.v1beta1.EchoClient;
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
  private static EchoClient grpcClient;
  private static EchoClient httpjsonClient;
  private String resource;

  @BeforeClass
  public static void createClients() throws Exception {
    grpcClient = TestClientInitializer.createGrpcEchoClient();
    httpjsonClient = TestClientInitializer.createHttpJsonEchoClient();
  }

  @Before
  public void setupTests() {
    resource = "users/" + UUID.randomUUID().toString().substring(0, 8);
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
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder().setPolicy(DEFAULT_POLICY).setResource(resource).build();
    Policy policy = grpcClient.setIamPolicy(policyRequest);
    assertThat(policy).isEqualTo(DEFAULT_POLICY);
  }

  @Test
  public void testHttpJson_setIamPolicy() {
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder().setPolicy(DEFAULT_POLICY).setResource(resource).build();
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
                SetIamPolicyRequest.newBuilder().setResource(resource).build()));
  }

  @Test
  public void testHttpJson_setIamPolicy_missingPolicy() {
    assertThrows(
        InvalidArgumentException.class,
        () ->
            httpjsonClient.setIamPolicy(
                SetIamPolicyRequest.newBuilder().setResource(resource).build()));
  }

  @Test
  public void testGrpc_getIamPolicy() {
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder().setPolicy(DEFAULT_POLICY).setResource(resource).build();
    grpcClient.setIamPolicy(policyRequest);

    Policy policy =
        grpcClient.getIamPolicy(GetIamPolicyRequest.newBuilder().setResource(resource).build());
    assertThat(policy).isEqualTo(DEFAULT_POLICY);
  }

  @Test
  public void testHttpJson_getIamPolicy() throws InterruptedException {
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder().setPolicy(DEFAULT_POLICY).setResource(resource).build();
    Policy setIamPolicy = httpjsonClient.setIamPolicy(policyRequest);
    System.out.println(setIamPolicy.getBindingsList() + " " + resource);

    Thread.sleep(1000);

    Policy policy =
        httpjsonClient.getIamPolicy(GetIamPolicyRequest.newBuilder().setResource(resource).build());
    assertThat(policy).isEqualTo(DEFAULT_POLICY);
    System.out.println(resource);
  }

  @Test
  public void testGrpc_getIamPolicy_doesNotExist() {
    assertThrows(
        NotFoundException.class,
        () ->
            grpcClient.getIamPolicy(
                GetIamPolicyRequest.newBuilder().setResource(resource).build()));
  }

  @Test
  public void testHttpJson_getIamPolicy_doesNotExist() {
    assertThrows(
        NotFoundException.class,
        () ->
            httpjsonClient.getIamPolicy(
                GetIamPolicyRequest.newBuilder().setResource(resource).build()));
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
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder().setPolicy(DEFAULT_POLICY).setResource(resource).build();
    grpcClient.setIamPolicy(policyRequest);
    List<String> permissions = ImmutableList.of("foo.create");
    TestIamPermissionsResponse testIamPermissionsResponse =
        grpcClient.testIamPermissions(
            TestIamPermissionsRequest.newBuilder()
                .setResource(resource)
                .addAllPermissions(permissions)
                .build());
    assertThat(testIamPermissionsResponse.getPermissionsList())
        .containsExactlyElementsIn(permissions);
  }

  @Test
  public void testHttpJson_testIamPermissions() {
    SetIamPolicyRequest policyRequest =
        SetIamPolicyRequest.newBuilder().setPolicy(DEFAULT_POLICY).setResource(resource).build();
    httpjsonClient.setIamPolicy(policyRequest);
    List<String> permissions = ImmutableList.of("foo.create");
    TestIamPermissionsResponse testIamPermissionsResponse =
        httpjsonClient.testIamPermissions(
            TestIamPermissionsRequest.newBuilder()
                .setResource(resource)
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
                TestIamPermissionsRequest.newBuilder().setResource(resource).build()));
  }

  @Test
  public void testHttpJson_testIamPermissions_doesNotExist() {
    assertThrows(
        NotFoundException.class,
        () ->
            httpjsonClient.testIamPermissions(
                TestIamPermissionsRequest.newBuilder().setResource(resource).build()));
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
