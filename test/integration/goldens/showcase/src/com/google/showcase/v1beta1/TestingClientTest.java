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

import static com.google.showcase.v1beta1.TestingClient.ListSessionsPagedResponse;
import static com.google.showcase.v1beta1.TestingClient.ListTestsPagedResponse;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GaxGrpcProperties;
import com.google.api.gax.grpc.testing.LocalChannelProvider;
import com.google.api.gax.grpc.testing.MockGrpcService;
import com.google.api.gax.grpc.testing.MockServiceHelper;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.common.collect.Lists;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.ByteString;
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
public class TestingClientTest {
  private static MockServiceHelper mockServiceHelper;
  private static MockTesting mockTesting;
  private LocalChannelProvider channelProvider;
  private TestingClient client;

  @BeforeClass
  public static void startStaticServer() {
    mockTesting = new MockTesting();
    mockServiceHelper =
        new MockServiceHelper(
            UUID.randomUUID().toString(), Arrays.<MockGrpcService>asList(mockTesting));
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
    TestingSettings settings =
        TestingSettings.newBuilder()
            .setTransportChannelProvider(channelProvider)
            .setCredentialsProvider(NoCredentialsProvider.create())
            .build();
    client = TestingClient.create(settings);
  }

  @After
  public void tearDown() throws Exception {
    client.close();
  }

  @Test
  public void createSessionTest() throws Exception {
    Session expectedResponse =
        Session.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
    mockTesting.addResponse(expectedResponse);

    CreateSessionRequest request =
        CreateSessionRequest.newBuilder().setSession(Session.newBuilder().build()).build();

    Session actualResponse = client.createSession(request);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockTesting.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    CreateSessionRequest actualRequest = ((CreateSessionRequest) actualRequests.get(0));

    Assert.assertEquals(request.getSession(), actualRequest.getSession());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void createSessionExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockTesting.addException(exception);

    try {
      CreateSessionRequest request =
          CreateSessionRequest.newBuilder().setSession(Session.newBuilder().build()).build();
      client.createSession(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void getSessionTest() throws Exception {
    Session expectedResponse =
        Session.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
    mockTesting.addResponse(expectedResponse);

    GetSessionRequest request =
        GetSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();

    Session actualResponse = client.getSession(request);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockTesting.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    GetSessionRequest actualRequest = ((GetSessionRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void getSessionExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockTesting.addException(exception);

    try {
      GetSessionRequest request =
          GetSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
      client.getSession(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void listSessionsTest() throws Exception {
    Session responsesElement = Session.newBuilder().build();
    ListSessionsResponse expectedResponse =
        ListSessionsResponse.newBuilder()
            .setNextPageToken("")
            .addAllSessions(Arrays.asList(responsesElement))
            .build();
    mockTesting.addResponse(expectedResponse);

    ListSessionsRequest request =
        ListSessionsRequest.newBuilder()
            .setPageSize(883849137)
            .setPageToken("pageToken873572522")
            .build();

    ListSessionsPagedResponse pagedListResponse = client.listSessions(request);

    List<Session> resources = Lists.newArrayList(pagedListResponse.iterateAll());

    Assert.assertEquals(1, resources.size());
    Assert.assertEquals(expectedResponse.getSessionsList().get(0), resources.get(0));

    List<AbstractMessage> actualRequests = mockTesting.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    ListSessionsRequest actualRequest = ((ListSessionsRequest) actualRequests.get(0));

    Assert.assertEquals(request.getPageSize(), actualRequest.getPageSize());
    Assert.assertEquals(request.getPageToken(), actualRequest.getPageToken());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void listSessionsExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockTesting.addException(exception);

    try {
      ListSessionsRequest request =
          ListSessionsRequest.newBuilder()
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      client.listSessions(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void deleteSessionTest() throws Exception {
    Empty expectedResponse = Empty.newBuilder().build();
    mockTesting.addResponse(expectedResponse);

    DeleteSessionRequest request =
        DeleteSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();

    client.deleteSession(request);

    List<AbstractMessage> actualRequests = mockTesting.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    DeleteSessionRequest actualRequest = ((DeleteSessionRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void deleteSessionExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockTesting.addException(exception);

    try {
      DeleteSessionRequest request =
          DeleteSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
      client.deleteSession(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void reportSessionTest() throws Exception {
    ReportSessionResponse expectedResponse =
        ReportSessionResponse.newBuilder().addAllTestRuns(new ArrayList<TestRun>()).build();
    mockTesting.addResponse(expectedResponse);

    ReportSessionRequest request =
        ReportSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();

    ReportSessionResponse actualResponse = client.reportSession(request);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockTesting.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    ReportSessionRequest actualRequest = ((ReportSessionRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void reportSessionExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockTesting.addException(exception);

    try {
      ReportSessionRequest request =
          ReportSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
      client.reportSession(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void listTestsTest() throws Exception {
    com.google.showcase.v1beta1.Test responsesElement =
        com.google.showcase.v1beta1.Test.newBuilder().build();
    ListTestsResponse expectedResponse =
        ListTestsResponse.newBuilder()
            .setNextPageToken("")
            .addAllTests(Arrays.asList(responsesElement))
            .build();
    mockTesting.addResponse(expectedResponse);

    ListTestsRequest request =
        ListTestsRequest.newBuilder()
            .setParent(SessionName.of("[SESSION]").toString())
            .setPageSize(883849137)
            .setPageToken("pageToken873572522")
            .build();

    ListTestsPagedResponse pagedListResponse = client.listTests(request);

    List<com.google.showcase.v1beta1.Test> resources =
        Lists.newArrayList(pagedListResponse.iterateAll());

    Assert.assertEquals(1, resources.size());
    Assert.assertEquals(expectedResponse.getTestsList().get(0), resources.get(0));

    List<AbstractMessage> actualRequests = mockTesting.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    ListTestsRequest actualRequest = ((ListTestsRequest) actualRequests.get(0));

    Assert.assertEquals(request.getParent(), actualRequest.getParent());
    Assert.assertEquals(request.getPageSize(), actualRequest.getPageSize());
    Assert.assertEquals(request.getPageToken(), actualRequest.getPageToken());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void listTestsExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockTesting.addException(exception);

    try {
      ListTestsRequest request =
          ListTestsRequest.newBuilder()
              .setParent(SessionName.of("[SESSION]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      client.listTests(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void deleteTestTest() throws Exception {
    Empty expectedResponse = Empty.newBuilder().build();
    mockTesting.addResponse(expectedResponse);

    DeleteTestRequest request =
        DeleteTestRequest.newBuilder()
            .setName(TestName.of("[SESSION]", "[TEST]").toString())
            .build();

    client.deleteTest(request);

    List<AbstractMessage> actualRequests = mockTesting.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    DeleteTestRequest actualRequest = ((DeleteTestRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void deleteTestExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockTesting.addException(exception);

    try {
      DeleteTestRequest request =
          DeleteTestRequest.newBuilder()
              .setName(TestName.of("[SESSION]", "[TEST]").toString())
              .build();
      client.deleteTest(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void verifyTestTest() throws Exception {
    VerifyTestResponse expectedResponse =
        VerifyTestResponse.newBuilder().setIssue(Issue.newBuilder().build()).build();
    mockTesting.addResponse(expectedResponse);

    VerifyTestRequest request =
        VerifyTestRequest.newBuilder()
            .setName(TestName.of("[SESSION]", "[TEST]").toString())
            .setAnswer(ByteString.EMPTY)
            .addAllAnswers(new ArrayList<ByteString>())
            .build();

    VerifyTestResponse actualResponse = client.verifyTest(request);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockTesting.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    VerifyTestRequest actualRequest = ((VerifyTestRequest) actualRequests.get(0));

    Assert.assertEquals(request.getName(), actualRequest.getName());
    Assert.assertEquals(request.getAnswer(), actualRequest.getAnswer());
    Assert.assertEquals(request.getAnswersList(), actualRequest.getAnswersList());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void verifyTestExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockTesting.addException(exception);

    try {
      VerifyTestRequest request =
          VerifyTestRequest.newBuilder()
              .setName(TestName.of("[SESSION]", "[TEST]").toString())
              .setAnswer(ByteString.EMPTY)
              .addAllAnswers(new ArrayList<ByteString>())
              .build();
      client.verifyTest(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }
}
