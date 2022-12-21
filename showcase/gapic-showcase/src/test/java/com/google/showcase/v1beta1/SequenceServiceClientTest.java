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
import com.google.protobuf.Empty;
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
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
public class SequenceServiceClientTest {
  private static MockSequenceService mockSequenceService;
  private static MockServiceHelper mockServiceHelper;
  private LocalChannelProvider channelProvider;
  private SequenceServiceClient client;

  @BeforeClass
  public static void startStaticServer() {
    mockSequenceService = new MockSequenceService();
    mockServiceHelper =
        new MockServiceHelper(
            UUID.randomUUID().toString(), Arrays.<MockGrpcService>asList(mockSequenceService));
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
    SequenceServiceSettings settings =
        SequenceServiceSettings.newBuilder()
            .setTransportChannelProvider(channelProvider)
            .setCredentialsProvider(NoCredentialsProvider.create())
            .build();
    client = SequenceServiceClient.create(settings);
  }

  @After
  public void tearDown() throws Exception {
    client.close();
  }

  @Test
  public void createSequenceTest() throws Exception {
    Sequence expectedResponse =
        Sequence.newBuilder()
            .setName(SequenceName.of("[SEQUENCE]").toString())
            .addAllResponses(new ArrayList<Sequence.Response>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    Sequence sequence = Sequence.newBuilder().build();

    Sequence actualResponse = client.createSequence(sequence);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    CreateSequenceRequest actualRequest = ((CreateSequenceRequest) actualRequests.get(0));

    Assert.assertEquals(sequence, actualRequest.getSequence());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void createSequenceExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      Sequence sequence = Sequence.newBuilder().build();
      client.createSequence(sequence);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void getSequenceReportTest() throws Exception {
    SequenceReport expectedResponse =
        SequenceReport.newBuilder()
            .setName(SequenceReportName.of("[SEQUENCE]").toString())
            .addAllAttempts(new ArrayList<SequenceReport.Attempt>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    SequenceReportName name = SequenceReportName.of("[SEQUENCE]");

    SequenceReport actualResponse = client.getSequenceReport(name);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    GetSequenceReportRequest actualRequest = ((GetSequenceReportRequest) actualRequests.get(0));

    Assert.assertEquals(name.toString(), actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void getSequenceReportExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      SequenceReportName name = SequenceReportName.of("[SEQUENCE]");
      client.getSequenceReport(name);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void getSequenceReportTest2() throws Exception {
    SequenceReport expectedResponse =
        SequenceReport.newBuilder()
            .setName(SequenceReportName.of("[SEQUENCE]").toString())
            .addAllAttempts(new ArrayList<SequenceReport.Attempt>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    String name = "name3373707";

    SequenceReport actualResponse = client.getSequenceReport(name);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    GetSequenceReportRequest actualRequest = ((GetSequenceReportRequest) actualRequests.get(0));

    Assert.assertEquals(name, actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void getSequenceReportExceptionTest2() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      String name = "name3373707";
      client.getSequenceReport(name);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void attemptSequenceTest() throws Exception {
    Empty expectedResponse = Empty.newBuilder().build();
    mockSequenceService.addResponse(expectedResponse);

    SequenceName name = SequenceName.of("[SEQUENCE]");

    client.attemptSequence(name);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    AttemptSequenceRequest actualRequest = ((AttemptSequenceRequest) actualRequests.get(0));

    Assert.assertEquals(name.toString(), actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void attemptSequenceExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      SequenceName name = SequenceName.of("[SEQUENCE]");
      client.attemptSequence(name);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void attemptSequenceTest2() throws Exception {
    Empty expectedResponse = Empty.newBuilder().build();
    mockSequenceService.addResponse(expectedResponse);

    String name = "name3373707";

    client.attemptSequence(name);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    AttemptSequenceRequest actualRequest = ((AttemptSequenceRequest) actualRequests.get(0));

    Assert.assertEquals(name, actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void attemptSequenceExceptionTest2() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      String name = "name3373707";
      client.attemptSequence(name);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }
}
