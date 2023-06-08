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

import static com.google.showcase.v1beta1.SequenceServiceClient.ListLocationsPagedResponse;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GaxGrpcProperties;
import com.google.api.gax.grpc.testing.LocalChannelProvider;
import com.google.api.gax.grpc.testing.MockGrpcService;
import com.google.api.gax.grpc.testing.MockServiceHelper;
import com.google.api.gax.grpc.testing.MockStreamObserver;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.StatusCode;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.ListLocationsResponse;
import com.google.cloud.location.Location;
import com.google.common.collect.Lists;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javax.annotation.Generated;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@Generated("by gapic-generator-java")
public class SequenceServiceClientTest {
  private static MockLocations mockLocations;
  private static MockSequenceService mockSequenceService;
  private static MockServiceHelper mockServiceHelper;
  private LocalChannelProvider channelProvider;
  private SequenceServiceClient client;

  @BeforeClass
  public static void startStaticServer() {
    mockSequenceService = new MockSequenceService();
    mockLocations = new MockLocations();
    mockServiceHelper =
        new MockServiceHelper(
            UUID.randomUUID().toString(),
            Arrays.<MockGrpcService>asList(mockSequenceService, mockLocations));
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
  public void createStreamingSequenceTest() throws Exception {
    StreamingSequence expectedResponse =
        StreamingSequence.newBuilder()
            .setName(StreamingSequenceName.of("[STREAMING_SEQUENCE]").toString())
            .setContent("content951530617")
            .addAllResponses(new ArrayList<StreamingSequence.Response>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    StreamingSequence streamingSequence = StreamingSequence.newBuilder().build();

    StreamingSequence actualResponse = client.createStreamingSequence(streamingSequence);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    CreateStreamingSequenceRequest actualRequest =
        ((CreateStreamingSequenceRequest) actualRequests.get(0));

    Assert.assertEquals(streamingSequence, actualRequest.getStreamingSequence());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void createStreamingSequenceExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      StreamingSequence streamingSequence = StreamingSequence.newBuilder().build();
      client.createStreamingSequence(streamingSequence);
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
  public void getStreamingSequenceReportTest() throws Exception {
    StreamingSequenceReport expectedResponse =
        StreamingSequenceReport.newBuilder()
            .setName(StreamingSequenceReportName.of("[STREAMING_SEQUENCE]").toString())
            .addAllAttempts(new ArrayList<StreamingSequenceReport.Attempt>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    StreamingSequenceReportName name = StreamingSequenceReportName.of("[STREAMING_SEQUENCE]");

    StreamingSequenceReport actualResponse = client.getStreamingSequenceReport(name);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    GetStreamingSequenceReportRequest actualRequest =
        ((GetStreamingSequenceReportRequest) actualRequests.get(0));

    Assert.assertEquals(name.toString(), actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void getStreamingSequenceReportExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      StreamingSequenceReportName name = StreamingSequenceReportName.of("[STREAMING_SEQUENCE]");
      client.getStreamingSequenceReport(name);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void getStreamingSequenceReportTest2() throws Exception {
    StreamingSequenceReport expectedResponse =
        StreamingSequenceReport.newBuilder()
            .setName(StreamingSequenceReportName.of("[STREAMING_SEQUENCE]").toString())
            .addAllAttempts(new ArrayList<StreamingSequenceReport.Attempt>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    String name = "name3373707";

    StreamingSequenceReport actualResponse = client.getStreamingSequenceReport(name);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    GetStreamingSequenceReportRequest actualRequest =
        ((GetStreamingSequenceReportRequest) actualRequests.get(0));

    Assert.assertEquals(name, actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void getStreamingSequenceReportExceptionTest2() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      String name = "name3373707";
      client.getStreamingSequenceReport(name);
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

  @Test
  public void attemptStreamingSequenceTest() throws Exception {
    AttemptStreamingSequenceResponse expectedResponse =
        AttemptStreamingSequenceResponse.newBuilder().setContent("content951530617").build();
    mockSequenceService.addResponse(expectedResponse);
    AttemptStreamingSequenceRequest request =
        AttemptStreamingSequenceRequest.newBuilder()
            .setName(StreamingSequenceName.of("[STREAMING_SEQUENCE]").toString())
            .build();

    MockStreamObserver<AttemptStreamingSequenceResponse> responseObserver =
        new MockStreamObserver<>();

    ServerStreamingCallable<AttemptStreamingSequenceRequest, AttemptStreamingSequenceResponse>
        callable = client.attemptStreamingSequenceCallable();
    callable.serverStreamingCall(request, responseObserver);

    List<AttemptStreamingSequenceResponse> actualResponses = responseObserver.future().get();
    Assert.assertEquals(1, actualResponses.size());
    Assert.assertEquals(expectedResponse, actualResponses.get(0));
  }

  @Test
  public void attemptStreamingSequenceExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);
    AttemptStreamingSequenceRequest request =
        AttemptStreamingSequenceRequest.newBuilder()
            .setName(StreamingSequenceName.of("[STREAMING_SEQUENCE]").toString())
            .build();

    MockStreamObserver<AttemptStreamingSequenceResponse> responseObserver =
        new MockStreamObserver<>();

    ServerStreamingCallable<AttemptStreamingSequenceRequest, AttemptStreamingSequenceResponse>
        callable = client.attemptStreamingSequenceCallable();
    callable.serverStreamingCall(request, responseObserver);

    try {
      List<AttemptStreamingSequenceResponse> actualResponses = responseObserver.future().get();
      Assert.fail("No exception thrown");
    } catch (ExecutionException e) {
      Assert.assertTrue(e.getCause() instanceof InvalidArgumentException);
      InvalidArgumentException apiException = ((InvalidArgumentException) e.getCause());
      Assert.assertEquals(StatusCode.Code.INVALID_ARGUMENT, apiException.getStatusCode().getCode());
    }
  }

  @Test
  public void listLocationsTest() throws Exception {
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

    Assert.assertEquals(1, resources.size());
    Assert.assertEquals(expectedResponse.getLocationsList().get(0), resources.get(0));

    List<AbstractMessage> actualRequests = mockLocations.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    ListLocationsRequest actualRequest = ((ListLocationsRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertEquals(request.getFilter(), actualRequest.getFilter());
    Assert.assertEquals(request.getPageSize(), actualRequest.getPageSize());
    Assert.assertEquals(request.getPageToken(), actualRequest.getPageToken());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void listLocationsExceptionTest() throws Exception {
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
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void getLocationTest() throws Exception {
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
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockLocations.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    GetLocationRequest actualRequest = ((GetLocationRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void getLocationExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockLocations.addException(exception);

    try {
      GetLocationRequest request = GetLocationRequest.newBuilder().setName("name3373707").build();
      client.getLocation(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }
}
