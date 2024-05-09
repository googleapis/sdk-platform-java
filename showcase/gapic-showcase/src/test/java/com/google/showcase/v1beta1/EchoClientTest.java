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

import static com.google.showcase.v1beta1.EchoClient.ListLocationsPagedResponse;
import static com.google.showcase.v1beta1.EchoClient.PagedExpandLegacyMappedPagedResponse;
import static com.google.showcase.v1beta1.EchoClient.PagedExpandPagedResponse;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GaxGrpcProperties;
import com.google.api.gax.grpc.testing.LocalChannelProvider;
import com.google.api.gax.grpc.testing.MockGrpcService;
import com.google.api.gax.grpc.testing.MockServiceHelper;
import com.google.api.gax.grpc.testing.MockStreamObserver;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientStreamingCallable;
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
import com.google.longrunning.Operation;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.Duration;
import com.google.protobuf.FieldMask;
import com.google.rpc.Status;
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
class EchoClientTest {
  private static MockEcho mockEcho;
  private static MockIAMPolicy mockIAMPolicy;
  private static MockLocations mockLocations;
  private static MockServiceHelper mockServiceHelper;
  private LocalChannelProvider channelProvider;
  private EchoClient client;

  @BeforeAll
  public static void startStaticServer() {
    mockEcho = new MockEcho();
    mockLocations = new MockLocations();
    mockIAMPolicy = new MockIAMPolicy();
    mockServiceHelper =
        new MockServiceHelper(
            UUID.randomUUID().toString(),
            Arrays.<MockGrpcService>asList(mockEcho, mockLocations, mockIAMPolicy));
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
    EchoSettings settings =
        EchoSettings.newBuilder()
            .setTransportChannelProvider(channelProvider)
            .setCredentialsProvider(NoCredentialsProvider.create())
            .build();
    client = EchoClient.create(settings);
  }

  @AfterEach
  void tearDown() throws Exception {
    client.close();
  }

  @Test
  void echoTest() throws Exception {
    EchoResponse expectedResponse =
        EchoResponse.newBuilder()
            .setContent("content951530617")
            .setSeverity(Severity.forNumber(0))
            .build();
    mockEcho.addResponse(expectedResponse);

    EchoRequest request =
        EchoRequest.newBuilder()
            .setSeverity(Severity.forNumber(0))
            .setHeader("header-1221270899")
            .setOtherHeader("otherHeader-2026585667")
            .build();

    EchoResponse actualResponse = client.echo(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockEcho.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    EchoRequest actualRequest = ((EchoRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getContent(), actualRequest.getContent());
    Assertions.assertEquals(request.getError(), actualRequest.getError());
    Assertions.assertEquals(request.getSeverity(), actualRequest.getSeverity());
    Assertions.assertEquals(request.getHeader(), actualRequest.getHeader());
    Assertions.assertEquals(request.getOtherHeader(), actualRequest.getOtherHeader());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void echoExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockEcho.addException(exception);

    try {
      EchoRequest request =
          EchoRequest.newBuilder()
              .setSeverity(Severity.forNumber(0))
              .setHeader("header-1221270899")
              .setOtherHeader("otherHeader-2026585667")
              .build();
      client.echo(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void expandTest() throws Exception {
    EchoResponse expectedResponse =
        EchoResponse.newBuilder()
            .setContent("content951530617")
            .setSeverity(Severity.forNumber(0))
            .build();
    mockEcho.addResponse(expectedResponse);
    ExpandRequest request =
        ExpandRequest.newBuilder()
            .setContent("content951530617")
            .setError(Status.newBuilder().build())
            .setStreamWaitTime(Duration.newBuilder().build())
            .build();

    MockStreamObserver<EchoResponse> responseObserver = new MockStreamObserver<>();

    ServerStreamingCallable<ExpandRequest, EchoResponse> callable = client.expandCallable();
    callable.serverStreamingCall(request, responseObserver);

    List<EchoResponse> actualResponses = responseObserver.future().get();
    Assertions.assertEquals(1, actualResponses.size());
    Assertions.assertEquals(expectedResponse, actualResponses.get(0));
  }

  @Test
  void expandExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockEcho.addException(exception);
    ExpandRequest request =
        ExpandRequest.newBuilder()
            .setContent("content951530617")
            .setError(Status.newBuilder().build())
            .setStreamWaitTime(Duration.newBuilder().build())
            .build();

    MockStreamObserver<EchoResponse> responseObserver = new MockStreamObserver<>();

    ServerStreamingCallable<ExpandRequest, EchoResponse> callable = client.expandCallable();
    callable.serverStreamingCall(request, responseObserver);

    try {
      List<EchoResponse> actualResponses = responseObserver.future().get();
      Assertions.fail("No exception thrown");
    } catch (ExecutionException e) {
      Assertions.assertTrue(e.getCause() instanceof InvalidArgumentException);
      InvalidArgumentException apiException = ((InvalidArgumentException) e.getCause());
      Assertions.assertEquals(
          StatusCode.Code.INVALID_ARGUMENT, apiException.getStatusCode().getCode());
    }
  }

  @Test
  void collectTest() throws Exception {
    EchoResponse expectedResponse =
        EchoResponse.newBuilder()
            .setContent("content951530617")
            .setSeverity(Severity.forNumber(0))
            .build();
    mockEcho.addResponse(expectedResponse);
    EchoRequest request =
        EchoRequest.newBuilder()
            .setSeverity(Severity.forNumber(0))
            .setHeader("header-1221270899")
            .setOtherHeader("otherHeader-2026585667")
            .build();

    MockStreamObserver<EchoResponse> responseObserver = new MockStreamObserver<>();

    ClientStreamingCallable<EchoRequest, EchoResponse> callable = client.collectCallable();
    ApiStreamObserver<EchoRequest> requestObserver = callable.clientStreamingCall(responseObserver);

    requestObserver.onNext(request);
    requestObserver.onCompleted();

    List<EchoResponse> actualResponses = responseObserver.future().get();
    Assertions.assertEquals(1, actualResponses.size());
    Assertions.assertEquals(expectedResponse, actualResponses.get(0));
  }

  @Test
  void collectExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockEcho.addException(exception);
    EchoRequest request =
        EchoRequest.newBuilder()
            .setSeverity(Severity.forNumber(0))
            .setHeader("header-1221270899")
            .setOtherHeader("otherHeader-2026585667")
            .build();

    MockStreamObserver<EchoResponse> responseObserver = new MockStreamObserver<>();

    ClientStreamingCallable<EchoRequest, EchoResponse> callable = client.collectCallable();
    ApiStreamObserver<EchoRequest> requestObserver = callable.clientStreamingCall(responseObserver);

    requestObserver.onNext(request);

    try {
      List<EchoResponse> actualResponses = responseObserver.future().get();
      Assertions.fail("No exception thrown");
    } catch (ExecutionException e) {
      Assertions.assertTrue(e.getCause() instanceof InvalidArgumentException);
      InvalidArgumentException apiException = ((InvalidArgumentException) e.getCause());
      Assertions.assertEquals(
          StatusCode.Code.INVALID_ARGUMENT, apiException.getStatusCode().getCode());
    }
  }

  @Test
  void chatTest() throws Exception {
    EchoResponse expectedResponse =
        EchoResponse.newBuilder()
            .setContent("content951530617")
            .setSeverity(Severity.forNumber(0))
            .build();
    mockEcho.addResponse(expectedResponse);
    EchoRequest request =
        EchoRequest.newBuilder()
            .setSeverity(Severity.forNumber(0))
            .setHeader("header-1221270899")
            .setOtherHeader("otherHeader-2026585667")
            .build();

    MockStreamObserver<EchoResponse> responseObserver = new MockStreamObserver<>();

    BidiStreamingCallable<EchoRequest, EchoResponse> callable = client.chatCallable();
    ApiStreamObserver<EchoRequest> requestObserver = callable.bidiStreamingCall(responseObserver);

    requestObserver.onNext(request);
    requestObserver.onCompleted();

    List<EchoResponse> actualResponses = responseObserver.future().get();
    Assertions.assertEquals(1, actualResponses.size());
    Assertions.assertEquals(expectedResponse, actualResponses.get(0));
  }

  @Test
  void chatExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockEcho.addException(exception);
    EchoRequest request =
        EchoRequest.newBuilder()
            .setSeverity(Severity.forNumber(0))
            .setHeader("header-1221270899")
            .setOtherHeader("otherHeader-2026585667")
            .build();

    MockStreamObserver<EchoResponse> responseObserver = new MockStreamObserver<>();

    BidiStreamingCallable<EchoRequest, EchoResponse> callable = client.chatCallable();
    ApiStreamObserver<EchoRequest> requestObserver = callable.bidiStreamingCall(responseObserver);

    requestObserver.onNext(request);

    try {
      List<EchoResponse> actualResponses = responseObserver.future().get();
      Assertions.fail("No exception thrown");
    } catch (ExecutionException e) {
      Assertions.assertTrue(e.getCause() instanceof InvalidArgumentException);
      InvalidArgumentException apiException = ((InvalidArgumentException) e.getCause());
      Assertions.assertEquals(
          StatusCode.Code.INVALID_ARGUMENT, apiException.getStatusCode().getCode());
    }
  }

  @Test
  void pagedExpandTest() throws Exception {
    EchoResponse responsesElement = EchoResponse.newBuilder().build();
    PagedExpandResponse expectedResponse =
        PagedExpandResponse.newBuilder()
            .setNextPageToken("")
            .addAllResponses(Arrays.asList(responsesElement))
            .build();
    mockEcho.addResponse(expectedResponse);

    PagedExpandRequest request =
        PagedExpandRequest.newBuilder()
            .setContent("content951530617")
            .setPageSize(883849137)
            .setPageToken("pageToken873572522")
            .build();

    PagedExpandPagedResponse pagedListResponse = client.pagedExpand(request);

    List<EchoResponse> resources = Lists.newArrayList(pagedListResponse.iterateAll());

    Assertions.assertEquals(1, resources.size());
    Assertions.assertEquals(expectedResponse.getResponsesList().get(0), resources.get(0));

    List<AbstractMessage> actualRequests = mockEcho.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    PagedExpandRequest actualRequest = ((PagedExpandRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getContent(), actualRequest.getContent());
    Assertions.assertEquals(request.getPageSize(), actualRequest.getPageSize());
    Assertions.assertEquals(request.getPageToken(), actualRequest.getPageToken());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void pagedExpandExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockEcho.addException(exception);

    try {
      PagedExpandRequest request =
          PagedExpandRequest.newBuilder()
              .setContent("content951530617")
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      client.pagedExpand(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void pagedExpandLegacyTest() throws Exception {
    PagedExpandResponse expectedResponse =
        PagedExpandResponse.newBuilder()
            .addAllResponses(new ArrayList<EchoResponse>())
            .setNextPageToken("nextPageToken-1386094857")
            .build();
    mockEcho.addResponse(expectedResponse);

    PagedExpandLegacyRequest request =
        PagedExpandLegacyRequest.newBuilder()
            .setContent("content951530617")
            .setMaxResults(1128457243)
            .setPageToken("pageToken873572522")
            .build();

    PagedExpandResponse actualResponse = client.pagedExpandLegacy(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockEcho.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    PagedExpandLegacyRequest actualRequest = ((PagedExpandLegacyRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getContent(), actualRequest.getContent());
    Assertions.assertEquals(request.getMaxResults(), actualRequest.getMaxResults());
    Assertions.assertEquals(request.getPageToken(), actualRequest.getPageToken());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void pagedExpandLegacyExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockEcho.addException(exception);

    try {
      PagedExpandLegacyRequest request =
          PagedExpandLegacyRequest.newBuilder()
              .setContent("content951530617")
              .setMaxResults(1128457243)
              .setPageToken("pageToken873572522")
              .build();
      client.pagedExpandLegacy(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void pagedExpandLegacyMappedTest() throws Exception {
    PagedExpandResponseList responsesElement = PagedExpandResponseList.newBuilder().build();
    PagedExpandLegacyMappedResponse expectedResponse =
        PagedExpandLegacyMappedResponse.newBuilder()
            .setNextPageToken("")
            .putAllAlphabetized(Collections.singletonMap("alphabetized", responsesElement))
            .build();
    mockEcho.addResponse(expectedResponse);

    PagedExpandRequest request =
        PagedExpandRequest.newBuilder()
            .setContent("content951530617")
            .setPageSize(883849137)
            .setPageToken("pageToken873572522")
            .build();

    PagedExpandLegacyMappedPagedResponse pagedListResponse =
        client.pagedExpandLegacyMapped(request);

    List<Map.Entry<String, PagedExpandResponseList>> resources =
        Lists.newArrayList(pagedListResponse.iterateAll());

    Assertions.assertEquals(1, resources.size());
    Assertions.assertEquals(
        expectedResponse.getAlphabetizedMap().entrySet().iterator().next(), resources.get(0));

    List<AbstractMessage> actualRequests = mockEcho.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    PagedExpandRequest actualRequest = ((PagedExpandRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getContent(), actualRequest.getContent());
    Assertions.assertEquals(request.getPageSize(), actualRequest.getPageSize());
    Assertions.assertEquals(request.getPageToken(), actualRequest.getPageToken());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void pagedExpandLegacyMappedExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockEcho.addException(exception);

    try {
      PagedExpandRequest request =
          PagedExpandRequest.newBuilder()
              .setContent("content951530617")
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      client.pagedExpandLegacyMapped(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void waitTest() throws Exception {
    WaitResponse expectedResponse =
        WaitResponse.newBuilder().setContent("content951530617").build();
    Operation resultOperation =
        Operation.newBuilder()
            .setName("waitTest")
            .setDone(true)
            .setResponse(Any.pack(expectedResponse))
            .build();
    mockEcho.addResponse(resultOperation);

    WaitRequest request = WaitRequest.newBuilder().build();

    WaitResponse actualResponse = client.waitAsync(request).get();
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockEcho.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    WaitRequest actualRequest = ((WaitRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getEndTime(), actualRequest.getEndTime());
    Assertions.assertEquals(request.getTtl(), actualRequest.getTtl());
    Assertions.assertEquals(request.getError(), actualRequest.getError());
    Assertions.assertEquals(request.getSuccess(), actualRequest.getSuccess());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void waitExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockEcho.addException(exception);

    try {
      WaitRequest request = WaitRequest.newBuilder().build();
      client.waitAsync(request).get();
      Assertions.fail("No exception raised");
    } catch (ExecutionException e) {
      Assertions.assertEquals(InvalidArgumentException.class, e.getCause().getClass());
      InvalidArgumentException apiException = ((InvalidArgumentException) e.getCause());
      Assertions.assertEquals(
          StatusCode.Code.INVALID_ARGUMENT, apiException.getStatusCode().getCode());
    }
  }

  @Test
  void blockTest() throws Exception {
    BlockResponse expectedResponse =
        BlockResponse.newBuilder().setContent("content951530617").build();
    mockEcho.addResponse(expectedResponse);

    BlockRequest request =
        BlockRequest.newBuilder().setResponseDelay(Duration.newBuilder().build()).build();

    BlockResponse actualResponse = client.block(request);
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockEcho.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    BlockRequest actualRequest = ((BlockRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getResponseDelay(), actualRequest.getResponseDelay());
    Assertions.assertEquals(request.getError(), actualRequest.getError());
    Assertions.assertEquals(request.getSuccess(), actualRequest.getSuccess());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void blockExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockEcho.addException(exception);

    try {
      BlockRequest request =
          BlockRequest.newBuilder().setResponseDelay(Duration.newBuilder().build()).build();
      client.block(request);
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
