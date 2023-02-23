/*
 * Copyright 2022 Google LLC
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

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GaxGrpcProperties;
import com.google.api.gax.grpc.testing.LocalChannelProvider;
import com.google.api.gax.grpc.testing.MockGrpcService;
import com.google.api.gax.grpc.testing.MockServiceHelper;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.protobuf.AbstractMessage;
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.annotation.Generated;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@Generated("by gapic-generator-java")
public class ComplianceClientTest {
  private static MockCompliance mockCompliance;
  private static MockServiceHelper mockServiceHelper;
  private LocalChannelProvider channelProvider;
  private ComplianceClient client;

  @BeforeClass
  public static void startStaticServer() {
    mockCompliance = new MockCompliance();
    mockServiceHelper =
        new MockServiceHelper(
            UUID.randomUUID().toString(), Arrays.<MockGrpcService>asList(mockCompliance));
    mockServiceHelper.start();
  }

  @AfterClass
  public static void stopServer() {
    mockServiceHelper.stop();
  }

  @Before
  public void setUp() throws IOException {
    mockServiceHelper.reset();
    channelProvider = mockServiceHelper.createChannelProvider();
    ComplianceSettings settings =
        ComplianceSettings.newBuilder()
            .setTransportChannelProvider(channelProvider)
            .setCredentialsProvider(NoCredentialsProvider.create())
            .build();
    client = ComplianceClient.create(settings);
  }

  @After
  public void tearDown() throws Exception {
    client.close();
  }

  @Test
  public void repeatDataBodyTest() throws Exception {
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
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assert.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assert.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assert.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assert.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assert.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assert.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assert.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assert.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void repeatDataBodyExceptionTest() throws Exception {
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
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void repeatDataBodyInfoTest() throws Exception {
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
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assert.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assert.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assert.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assert.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assert.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assert.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assert.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assert.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void repeatDataBodyInfoExceptionTest() throws Exception {
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
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void repeatDataQueryTest() throws Exception {
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
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assert.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assert.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assert.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assert.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assert.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assert.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assert.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assert.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void repeatDataQueryExceptionTest() throws Exception {
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
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void repeatDataSimplePathTest() throws Exception {
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
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assert.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assert.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assert.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assert.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assert.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assert.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assert.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assert.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void repeatDataSimplePathExceptionTest() throws Exception {
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
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void repeatDataPathResourceTest() throws Exception {
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
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assert.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assert.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assert.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assert.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assert.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assert.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assert.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assert.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void repeatDataPathResourceExceptionTest() throws Exception {
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
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void repeatDataPathTrailingResourceTest() throws Exception {
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
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assert.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assert.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assert.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assert.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assert.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assert.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assert.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assert.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void repeatDataPathTrailingResourceExceptionTest() throws Exception {
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
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void repeatDataBodyPutTest() throws Exception {
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
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assert.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assert.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assert.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assert.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assert.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assert.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assert.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assert.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void repeatDataBodyPutExceptionTest() throws Exception {
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
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void repeatDataBodyPatchTest() throws Exception {
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
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    RepeatRequest actualRequest = ((RepeatRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertEquals(request.getInfo(), actualRequest.getInfo());
    Assert.assertEquals(request.getServerVerify(), actualRequest.getServerVerify());
    Assert.assertEquals(request.getIntendedBindingUri(), actualRequest.getIntendedBindingUri());
    Assert.assertEquals(request.getFInt32(), actualRequest.getFInt32());
    Assert.assertEquals(request.getFInt64(), actualRequest.getFInt64());
    Assert.assertEquals(request.getFDouble(), actualRequest.getFDouble(), 0.0001);
    Assert.assertEquals(request.getPInt32(), actualRequest.getPInt32());
    Assert.assertEquals(request.getPInt64(), actualRequest.getPInt64());
    Assert.assertEquals(request.getPDouble(), actualRequest.getPDouble(), 0.0001);
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void repeatDataBodyPatchExceptionTest() throws Exception {
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
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void getEnumTest() throws Exception {
    EnumResponse expectedResponse =
        EnumResponse.newBuilder()
            .setRequest(EnumRequest.newBuilder().build())
            .setContinent(Continent.forNumber(0))
            .build();
    mockCompliance.addResponse(expectedResponse);

    EnumRequest request = EnumRequest.newBuilder().setUnknownEnum(true).build();

    EnumResponse actualResponse = client.getEnum(request);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    EnumRequest actualRequest = ((EnumRequest) actualRequests.get(0));

    Assert.assertEquals(request.getUnknownEnum(), actualRequest.getUnknownEnum());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void getEnumExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      EnumRequest request = EnumRequest.newBuilder().setUnknownEnum(true).build();
      client.getEnum(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void verifyEnumTest() throws Exception {
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
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockCompliance.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    EnumResponse actualRequest = ((EnumResponse) actualRequests.get(0));

    Assert.assertEquals(request.getRequest(), actualRequest.getRequest());
    Assert.assertEquals(request.getContinent(), actualRequest.getContinent());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void verifyEnumExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockCompliance.addException(exception);

    try {
      EnumResponse request =
          EnumResponse.newBuilder()
              .setRequest(EnumRequest.newBuilder().build())
              .setContinent(Continent.forNumber(0))
              .build();
      client.verifyEnum(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }
}
