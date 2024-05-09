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
import com.google.protobuf.Empty;
import com.google.protobuf.FieldMask;
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javax.annotation.Generated;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Generated("by gapic-generator-java")
class SequenceServiceClientTest {
  private static MockIAMPolicy mockIAMPolicy;
  private static MockLocations mockLocations;
  private static MockSequenceService mockSequenceService;
  private static MockServiceHelper mockServiceHelper;
  private LocalChannelProvider channelProvider;
  private SequenceServiceClient client;

  @BeforeAll
  public static void startStaticServer() {
    mockSequenceService = new MockSequenceService();
    mockLocations = new MockLocations();
    mockIAMPolicy = new MockIAMPolicy();
    mockServiceHelper =
        new MockServiceHelper(
            UUID.randomUUID().toString(),
            Arrays.<MockGrpcService>asList(mockSequenceService, mockLocations, mockIAMPolicy));
    mockServiceHelper.start();
  }

  @AfterAll
  public static void stopServer() {
    mockServiceHelper.stop();
  }

  @BeforeEach
  void setUp() throws IOException {
    mockServiceHelper.reset();
    channelProvider = mockServiceHelper.createChannelProvider();
    SequenceServiceSettings settings =
        SequenceServiceSettings.newBuilder()
            .setTransportChannelProvider(channelProvider)
            .setCredentialsProvider(NoCredentialsProvider.create())
            .build();
    client = SequenceServiceClient.create(settings);
  }

  @AfterEach
  void tearDown() throws Exception {
    client.close();
  }

  @Test
  void createSequenceTest() throws Exception {
    Sequence expectedResponse =
        Sequence.newBuilder()
            .setName(SequenceName.of("[SEQUENCE]").toString())
            .addAllResponses(new ArrayList<Sequence.Response>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    Sequence sequence = Sequence.newBuilder().build();

    Sequence actualResponse = client.createSequence(sequence);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    CreateSequenceRequest actualRequest = ((CreateSequenceRequest) actualRequests.get(0));

    Assertions.assertEquals(sequence, actualRequest.getSequence());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void createSequenceExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      Sequence sequence = Sequence.newBuilder().build();
      client.createSequence(sequence);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void createStreamingSequenceTest() throws Exception {
    StreamingSequence expectedResponse =
        StreamingSequence.newBuilder()
            .setName(StreamingSequenceName.of("[STREAMING_SEQUENCE]").toString())
            .setContent("content951530617")
            .addAllResponses(new ArrayList<StreamingSequence.Response>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    StreamingSequence streamingSequence = StreamingSequence.newBuilder().build();

    StreamingSequence actualResponse = client.createStreamingSequence(streamingSequence);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    CreateStreamingSequenceRequest actualRequest =
        ((CreateStreamingSequenceRequest) actualRequests.get(0));

    Assertions.assertEquals(streamingSequence, actualRequest.getStreamingSequence());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void createStreamingSequenceExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      StreamingSequence streamingSequence = StreamingSequence.newBuilder().build();
      client.createStreamingSequence(streamingSequence);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getSequenceReportTest() throws Exception {
    SequenceReport expectedResponse =
        SequenceReport.newBuilder()
            .setName(SequenceReportName.of("[SEQUENCE]").toString())
            .addAllAttempts(new ArrayList<SequenceReport.Attempt>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    SequenceReportName name = SequenceReportName.of("[SEQUENCE]");

    SequenceReport actualResponse = client.getSequenceReport(name);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    GetSequenceReportRequest actualRequest = ((GetSequenceReportRequest) actualRequests.get(0));

    Assertions.assertEquals(name.toString(), actualRequest.getName());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void getSequenceReportExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      SequenceReportName name = SequenceReportName.of("[SEQUENCE]");
      client.getSequenceReport(name);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getSequenceReportTest2() throws Exception {
    SequenceReport expectedResponse =
        SequenceReport.newBuilder()
            .setName(SequenceReportName.of("[SEQUENCE]").toString())
            .addAllAttempts(new ArrayList<SequenceReport.Attempt>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    String name = "name3373707";

    SequenceReport actualResponse = client.getSequenceReport(name);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    GetSequenceReportRequest actualRequest = ((GetSequenceReportRequest) actualRequests.get(0));

    Assertions.assertEquals(name, actualRequest.getName());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void getSequenceReportExceptionTest2() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      String name = "name3373707";
      client.getSequenceReport(name);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getStreamingSequenceReportTest() throws Exception {
    StreamingSequenceReport expectedResponse =
        StreamingSequenceReport.newBuilder()
            .setName(StreamingSequenceReportName.of("[STREAMING_SEQUENCE]").toString())
            .addAllAttempts(new ArrayList<StreamingSequenceReport.Attempt>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    StreamingSequenceReportName name = StreamingSequenceReportName.of("[STREAMING_SEQUENCE]");

    StreamingSequenceReport actualResponse = client.getStreamingSequenceReport(name);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    GetStreamingSequenceReportRequest actualRequest =
        ((GetStreamingSequenceReportRequest) actualRequests.get(0));

    Assertions.assertEquals(name.toString(), actualRequest.getName());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void getStreamingSequenceReportExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      StreamingSequenceReportName name = StreamingSequenceReportName.of("[STREAMING_SEQUENCE]");
      client.getStreamingSequenceReport(name);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getStreamingSequenceReportTest2() throws Exception {
    StreamingSequenceReport expectedResponse =
        StreamingSequenceReport.newBuilder()
            .setName(StreamingSequenceReportName.of("[STREAMING_SEQUENCE]").toString())
            .addAllAttempts(new ArrayList<StreamingSequenceReport.Attempt>())
            .build();
    mockSequenceService.addResponse(expectedResponse);

    String name = "name3373707";

    StreamingSequenceReport actualResponse = client.getStreamingSequenceReport(name);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    GetStreamingSequenceReportRequest actualRequest =
        ((GetStreamingSequenceReportRequest) actualRequests.get(0));

    Assertions.assertEquals(name, actualRequest.getName());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void getStreamingSequenceReportExceptionTest2() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      String name = "name3373707";
      client.getStreamingSequenceReport(name);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void attemptSequenceTest() throws Exception {
    Empty expectedResponse = Empty.newBuilder().build();
    mockSequenceService.addResponse(expectedResponse);

    SequenceName name = SequenceName.of("[SEQUENCE]");

    client.attemptSequence(name);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    AttemptSequenceRequest actualRequest = ((AttemptSequenceRequest) actualRequests.get(0));

    Assertions.assertEquals(name.toString(), actualRequest.getName());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void attemptSequenceExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      SequenceName name = SequenceName.of("[SEQUENCE]");
      client.attemptSequence(name);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void attemptSequenceTest2() throws Exception {
    Empty expectedResponse = Empty.newBuilder().build();
    mockSequenceService.addResponse(expectedResponse);

    String name = "name3373707";

    client.attemptSequence(name);

    List<AbstractMessage> actualRequests = mockSequenceService.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    AttemptSequenceRequest actualRequest = ((AttemptSequenceRequest) actualRequests.get(0));

    Assertions.assertEquals(name, actualRequest.getName());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void attemptSequenceExceptionTest2() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockSequenceService.addException(exception);

    try {
      String name = "name3373707";
      client.attemptSequence(name);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void attemptStreamingSequenceTest() throws Exception {
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
    Assertions.assertEquals(1, actualResponses.size());
    Assertions.assertEquals(expectedResponse, actualResponses.get(0));
  }

  @Test
  void attemptStreamingSequenceExceptionTest() throws Exception {
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
      Assertions.fail("No exception thrown");
    } catch (ExecutionException e) {
      Assertions.assertTrue(e.getCause() instanceof InvalidArgumentException);
      InvalidArgumentException apiException = ((InvalidArgumentException) e.getCause());
      Assertions.assertEquals(
          StatusCode.Code.INVALID_ARGUMENT, apiException.getStatusCode().getCode());
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
