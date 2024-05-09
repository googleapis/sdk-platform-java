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

package com.google.showcase.v1beta1;

import static com.google.showcase.v1beta1.ComplianceClient.ListLocationsPagedResponse;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GaxGrpcProperties;
import com.google.api.gax.grpc.testing.LocalChannelProvider;
import com.google.api.gax.grpc.testing.MockGrpcService;
import com.google.api.gax.grpc.testing.MockServiceHelper;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.ListLocationsResponse;
import com.google.cloud.location.Location;
import com.google.common.collect.Lists;
import com.google.iam.v1.AuditConfig;
import com.google.iam.v1.Binding;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.GetPolicyOptions;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.FieldMask;
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import javax.annotation.Generated;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Generated("by gapic-generator-java")
class ComplianceClientTest {
  private static MockCompliance mockCompliance;
  private static MockIAMPolicy mockIAMPolicy;
  private static MockLocations mockLocations;
  private static MockServiceHelper mockServiceHelper;
  private LocalChannelProvider channelProvider;
  private ComplianceClient client;

  @BeforeAll
  static void startStaticServer() {
    mockCompliance = new MockCompliance();
    mockLocations = new MockLocations();
    mockIAMPolicy = new MockIAMPolicy();
    mockServiceHelper =
        new MockServiceHelper(
            UUID.randomUUID().toString(),
            Arrays.<MockGrpcService>asList(mockCompliance, mockLocations, mockIAMPolicy));
    mockServiceHelper.start();
  }

  @AfterAll
  static void stopServer() {
    mockServiceHelper.stop();
  }

  @BeforeEach
  void setUp() throws IOException {
    mockServiceHelper.reset();
    channelProvider = mockServiceHelper.createChannelProvider();
    ComplianceSettings settings =
        ComplianceSettings.newBuilder()
            .setTransportChannelProvider(channelProvider)
            .setCredentialsProvider(NoCredentialsProvider.create())
            .build();
    client = ComplianceClient.create(settings);
  }

  @AfterEach
  void tearDown() throws Exception {
    client.close();
  }

  @Test
  void repeatDataBodyTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockCompliance.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataBody(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getName(), actualRequest.getName());
    Assertions.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assertions.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assertions.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assertions.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assertions.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assertions.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assertions.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assertions.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assertions.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void repeatDataBodyExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataBody(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataBodyInfoTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockCompliance.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataBodyInfo(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getName(), actualRequest.getName());
    Assertions.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assertions.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assertions.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assertions.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assertions.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assertions.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assertions.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assertions.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assertions.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void repeatDataBodyInfoExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataBodyInfo(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataQueryTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockCompliance.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataQuery(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getName(), actualRequest.getName());
    Assertions.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assertions.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assertions.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assertions.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assertions.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assertions.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assertions.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assertions.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assertions.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void repeatDataQueryExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataQuery(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataSimplePathTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockCompliance.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataSimplePath(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getName(), actualRequest.getName());
    Assertions.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assertions.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assertions.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assertions.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assertions.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assertions.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assertions.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assertions.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assertions.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void repeatDataSimplePathExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataSimplePath(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataPathResourceTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockCompliance.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataPathResource(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getName(), actualRequest.getName());
    Assertions.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assertions.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assertions.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assertions.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assertions.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assertions.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assertions.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assertions.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assertions.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void repeatDataPathResourceExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataPathResource(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataPathTrailingResourceTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockCompliance.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataPathTrailingResource(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getName(), actualRequest.getName());
    Assertions.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assertions.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assertions.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assertions.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assertions.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assertions.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assertions.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assertions.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assertions.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void repeatDataPathTrailingResourceExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataPathTrailingResource(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataBodyPutTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockCompliance.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataBodyPut(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getName(), actualRequest.getName());
    Assertions.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assertions.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assertions.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assertions.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assertions.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assertions.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assertions.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assertions.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assertions.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void repeatDataBodyPutExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataBodyPut(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void repeatDataBodyPatchTest() throws Exception {
    RepeatResponse expectedResponse =
        RepeatResponse.newBuilder()
            .setRequest(RepeatRequest.newBuilder().build())
            .setBindingUri("bindingUri1514820775")
            .build();
    mockCompliance.addResponse(expectedResponse);

    RepeatRequest request =
        RepeatRequest.newBuilder()
            .setName("name3373707")
            .setInfo(ComplianceData.newBuilder().build())
            .setServerVerify(true)
            .setIntendedBindingUri("intendedBindingUri780142386")
            .setFInt32(-1143775883)
            .setFInt64(-1143775788)
            .setFDouble(-1239459382)
            .setPInt32(-858673665)
            .setPInt64(-858673570)
            .setPDouble(-991225216)
            .build();

    RepeatResponse actualResponse = client.repeatDataBodyPatch(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getName(), actualRequest.getName());
    Assertions.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assertions.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assertions.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assertions.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assertions.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assertions.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assertions.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assertions.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assertions.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void repeatDataBodyPatchExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      RepeatRequest request =
          RepeatRequest.newBuilder()
              .setName("name3373707")
              .setInfo(ComplianceData.newBuilder().build())
              .setServerVerify(true)
              .setIntendedBindingUri("intendedBindingUri780142386")
              .setFInt32(-1143775883)
              .setFInt64(-1143775788)
              .setFDouble(-1239459382)
              .setPInt32(-858673665)
              .setPInt64(-858673570)
              .setPDouble(-991225216)
              .build();
      client.repeatDataBodyPatch(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getEnumTest() throws Exception {
    EnumResponse expectedResponse =
        EnumResponse.newBuilder()
            .setRequest(EnumRequest.newBuilder().build())
            .setContinent(Continent.forNumber(0))
            .build();
    mockCompliance.addResponse(expectedResponse);

    EnumRequest request = EnumRequest.newBuilder().setUnknownEnum(true).build();

    EnumResponse actualResponse = client.getEnum(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    EnumRequest actualRequest = ((EnumRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getUnknownEnum(), actualRequest.getUnknownEnum());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void getEnumExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      EnumRequest request = EnumRequest.newBuilder().setUnknownEnum(true).build();
      client.getEnum(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void verifyEnumTest() throws Exception {
    EnumResponse expectedResponse =
        EnumResponse.newBuilder()
            .setRequest(EnumRequest.newBuilder().build())
            .setContinent(Continent.forNumber(0))
            .build();
    mockCompliance.addResponse(expectedResponse);

    EnumResponse request =
        EnumResponse.newBuilder()
            .setRequest(EnumRequest.newBuilder().build())
            .setContinent(Continent.forNumber(0))
            .build();

    EnumResponse actualResponse = client.verifyEnum(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    EnumResponse actualRequest = ((EnumResponse) actualRequests.get(0));

    Assertions.assertEquals(request.getRequest(), actualRequest.getRequest());
    Assertions.assertEquals(request.getContinent(), actualRequest.getContinent());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void verifyEnumExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      EnumResponse request =
          EnumResponse.newBuilder()
              .setRequest(EnumRequest.newBuilder().build())
              .setContinent(Continent.forNumber(0))
              .build();
      client.verifyEnum(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void listLocationsTest() throws Exception {
    Location responsesElement = Location.newBuilder().build();
    ListLocationsResponse expectedResponse =
        ListLocationsResponse.newBuilder()
            .setNextPageToken("")
            .addAllLocations(Arrays.asList(responsesElement))
            .build();
    mockLocations.addResponse(expectedResponse);

    ListLocationsRequest request =
        ListLocationsRequest.newBuilder()
            .setName("name3373707")
            .setFilter("filter-1274492040")
            .setPageSize(883849137)
            .setPageToken("pageToken873572522")
            .build();

    ListLocationsPagedResponse pagedListResponse = client.listLocations(request);

    List<Location> resources = Lists.newArrayList(pagedListResponse.iterateAll());

    Assertions.assertEquals(1, resources.size());
    Assertions.assertEquals(expectedResponse.getLocationsList().get(0), resources.get(0));

    List<AbstractMessage> actualRequests = mockLocations.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    ListLocationsRequest actualRequest = ((ListLocationsRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getName(), actualRequest.getName());
    Assertions.assertEquals(request.getFilter(), actualRequest.getFilter());
    Assertions.assertEquals(request.getPageSize(), actualRequest.getPageSize());
    Assertions.assertEquals(request.getPageToken(), actualRequest.getPageToken());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void listLocationsExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockLocations.addException(exception);

    try {
      ListLocationsRequest request =
          ListLocationsRequest.newBuilder()
              .setName("name3373707")
              .setFilter("filter-1274492040")
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      client.listLocations(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getLocationTest() throws Exception {
    Location expectedResponse =
        Location.newBuilder()
            .setName("name3373707")
            .setLocationId("locationId1541836720")
            .setDisplayName("displayName1714148973")
            .putAllLabels(new HashMap<String, String>())
            .setMetadata(Any.newBuilder().build())
            .build();
    mockLocations.addResponse(expectedResponse);

    GetLocationRequest request = GetLocationRequest.newBuilder().setName("name3373707").build();

    Location actualResponse = client.getLocation(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockLocations.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    GetLocationRequest actualRequest = ((GetLocationRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getName(), actualRequest.getName());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void getLocationExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockLocations.addException(exception);

    try {
      GetLocationRequest request = GetLocationRequest.newBuilder().setName("name3373707").build();
      client.getLocation(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void setIamPolicyTest() throws Exception {
    Policy expectedResponse =
        Policy.newBuilder()
            .setVersion(351608024)
            .addAllBindings(new ArrayList<Binding>())
            .addAllAuditConfigs(new ArrayList<AuditConfig>())
            .setEtag(ByteString.EMPTY)
            .build();
    mockIAMPolicy.addResponse(expectedResponse);

    SetIamPolicyRequest request =
        SetIamPolicyRequest.newBuilder()
            .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
            .setPolicy(Policy.newBuilder().build())
            .setUpdateMask(FieldMask.newBuilder().build())
            .build();

    Policy actualResponse = client.setIamPolicy(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIAMPolicy.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    SetIamPolicyRequest actualRequest = ((SetIamPolicyRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getResource(), actualRequest.getResource());
    Assertions.assertEquals(request.getPolicy(), actualRequest.getPolicy());
    Assertions.assertEquals(request.getUpdateMask(), actualRequest.getUpdateMask());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void setIamPolicyExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIAMPolicy.addException(exception);

    try {
      SetIamPolicyRequest request =
          SetIamPolicyRequest.newBuilder()
              .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
              .setPolicy(Policy.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      client.setIamPolicy(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getIamPolicyTest() throws Exception {
    Policy expectedResponse =
        Policy.newBuilder()
            .setVersion(351608024)
            .addAllBindings(new ArrayList<Binding>())
            .addAllAuditConfigs(new ArrayList<AuditConfig>())
            .setEtag(ByteString.EMPTY)
            .build();
    mockIAMPolicy.addResponse(expectedResponse);

    GetIamPolicyRequest request =
        GetIamPolicyRequest.newBuilder()
            .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
            .setOptions(GetPolicyOptions.newBuilder().build())
            .build();

    Policy actualResponse = client.getIamPolicy(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIAMPolicy.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    GetIamPolicyRequest actualRequest = ((GetIamPolicyRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getResource(), actualRequest.getResource());
    Assertions.assertEquals(request.getOptions(), actualRequest.getOptions());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void getIamPolicyExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIAMPolicy.addException(exception);

    try {
      GetIamPolicyRequest request =
          GetIamPolicyRequest.newBuilder()
              .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
              .setOptions(GetPolicyOptions.newBuilder().build())
              .build();
      client.getIamPolicy(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void testIamPermissionsTest() throws Exception {
    TestIamPermissionsResponse expectedResponse =
        TestIamPermissionsResponse.newBuilder().addAllPermissions(new ArrayList<String>()).build();
    mockIAMPolicy.addResponse(expectedResponse);

    TestIamPermissionsRequest request =
        TestIamPermissionsRequest.newBuilder()
            .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
            .addAllPermissions(new ArrayList<String>())
            .build();

    TestIamPermissionsResponse actualResponse = client.testIamPermissions(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIAMPolicy.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    TestIamPermissionsRequest actualRequest = ((TestIamPermissionsRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getResource(), actualRequest.getResource());
    Assertions.assertEquals(request.getPermissionsList(), actualRequest.getPermissionsList());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void testIamPermissionsExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIAMPolicy.addException(exception);

    try {
      TestIamPermissionsRequest request =
          TestIamPermissionsRequest.newBuilder()
              .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
              .addAllPermissions(new ArrayList<String>())
              .build();
      client.testIamPermissions(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }
}
