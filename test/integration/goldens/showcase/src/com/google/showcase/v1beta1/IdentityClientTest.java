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

import static com.google.showcase.v1beta1.IdentityClient.ListUsersPagedResponse;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GaxGrpcProperties;
import com.google.api.gax.grpc.testing.LocalChannelProvider;
import com.google.api.gax.grpc.testing.MockGrpcService;
import com.google.api.gax.grpc.testing.MockServiceHelper;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.common.collect.Lists;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Empty;
import com.google.protobuf.FieldMask;
import com.google.protobuf.Timestamp;
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
public class IdentityClientTest {
  private static MockIdentity mockIdentity;
  private static MockServiceHelper mockServiceHelper;
  private LocalChannelProvider channelProvider;
  private IdentityClient client;

  @BeforeClass
  public static void startStaticServer() {
    mockIdentity = new MockIdentity();
    mockServiceHelper =
        new MockServiceHelper(
            UUID.randomUUID().toString(), Arrays.<MockGrpcService>asList(mockIdentity));
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
    IdentitySettings settings =
        IdentitySettings.newBuilder()
            .setTransportChannelProvider(channelProvider)
            .setCredentialsProvider(NoCredentialsProvider.create())
            .build();
    client = IdentityClient.create(settings);
  }

  @After
  public void tearDown() throws Exception {
    client.close();
  }

  @Test
  public void createUserTest() throws Exception {
    User expectedResponse =
        User.newBuilder()
            .setName(UserName.of("[USER]").toString())
            .setDisplayName("displayName1714148973")
            .setEmail("email96619420")
            .setCreateTime(Timestamp.newBuilder().build())
            .setUpdateTime(Timestamp.newBuilder().build())
            .setAge(96511)
            .setHeightFeet(-1032737338)
            .setNickname("nickname70690926")
            .setEnableNotifications(true)
            .build();
    mockIdentity.addResponse(expectedResponse);

    String displayName = "displayName1714148973";
    String email = "email96619420";

    User actualResponse = client.createUser(displayName, email);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    CreateUserRequest actualRequest = ((CreateUserRequest) actualRequests.get(0));

    Assert.assertEquals(displayName, actualRequest.getUser().getDisplayName());
    Assert.assertEquals(email, actualRequest.getUser().getEmail());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void createUserExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      String displayName = "displayName1714148973";
      String email = "email96619420";
      client.createUser(displayName, email);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void createUserTest2() throws Exception {
    User expectedResponse =
        User.newBuilder()
            .setName(UserName.of("[USER]").toString())
            .setDisplayName("displayName1714148973")
            .setEmail("email96619420")
            .setCreateTime(Timestamp.newBuilder().build())
            .setUpdateTime(Timestamp.newBuilder().build())
            .setAge(96511)
            .setHeightFeet(-1032737338)
            .setNickname("nickname70690926")
            .setEnableNotifications(true)
            .build();
    mockIdentity.addResponse(expectedResponse);

    String displayName = "displayName1714148973";
    String email = "email96619420";
    int age = 96511;
    String nickname = "nickname70690926";
    boolean enableNotifications = true;
    double heightFeet = -1032737338;

    User actualResponse =
        client.createUser(displayName, email, age, nickname, enableNotifications, heightFeet);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    CreateUserRequest actualRequest = ((CreateUserRequest) actualRequests.get(0));

    Assert.assertEquals(displayName, actualRequest.getUser().getDisplayName());
    Assert.assertEquals(email, actualRequest.getUser().getEmail());
    Assert.assertEquals(age, actualRequest.getUser().getAge());
    Assert.assertEquals(nickname, actualRequest.getUser().getNickname());
    Assert.assertEquals(enableNotifications, actualRequest.getUser().getEnableNotifications());
    Assert.assertEquals(heightFeet, actualRequest.getUser().getHeightFeet(), 0.0001);
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void createUserExceptionTest2() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      String displayName = "displayName1714148973";
      String email = "email96619420";
      int age = 96511;
      String nickname = "nickname70690926";
      boolean enableNotifications = true;
      double heightFeet = -1032737338;
      client.createUser(displayName, email, age, nickname, enableNotifications, heightFeet);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void getUserTest() throws Exception {
    User expectedResponse =
        User.newBuilder()
            .setName(UserName.of("[USER]").toString())
            .setDisplayName("displayName1714148973")
            .setEmail("email96619420")
            .setCreateTime(Timestamp.newBuilder().build())
            .setUpdateTime(Timestamp.newBuilder().build())
            .setAge(96511)
            .setHeightFeet(-1032737338)
            .setNickname("nickname70690926")
            .setEnableNotifications(true)
            .build();
    mockIdentity.addResponse(expectedResponse);

    UserName name = UserName.of("[USER]");

    User actualResponse = client.getUser(name);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    GetUserRequest actualRequest = ((GetUserRequest) actualRequests.get(0));

    Assert.assertEquals(name.toString(), actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void getUserExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      UserName name = UserName.of("[USER]");
      client.getUser(name);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void getUserTest2() throws Exception {
    User expectedResponse =
        User.newBuilder()
            .setName(UserName.of("[USER]").toString())
            .setDisplayName("displayName1714148973")
            .setEmail("email96619420")
            .setCreateTime(Timestamp.newBuilder().build())
            .setUpdateTime(Timestamp.newBuilder().build())
            .setAge(96511)
            .setHeightFeet(-1032737338)
            .setNickname("nickname70690926")
            .setEnableNotifications(true)
            .build();
    mockIdentity.addResponse(expectedResponse);

    String name = "name3373707";

    User actualResponse = client.getUser(name);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    GetUserRequest actualRequest = ((GetUserRequest) actualRequests.get(0));

    Assert.assertEquals(name, actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void getUserExceptionTest2() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      String name = "name3373707";
      client.getUser(name);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void updateUserTest() throws Exception {
    User expectedResponse =
        User.newBuilder()
            .setName(UserName.of("[USER]").toString())
            .setDisplayName("displayName1714148973")
            .setEmail("email96619420")
            .setCreateTime(Timestamp.newBuilder().build())
            .setUpdateTime(Timestamp.newBuilder().build())
            .setAge(96511)
            .setHeightFeet(-1032737338)
            .setNickname("nickname70690926")
            .setEnableNotifications(true)
            .build();
    mockIdentity.addResponse(expectedResponse);

    UpdateUserRequest request =
        UpdateUserRequest.newBuilder()
            .setUser(User.newBuilder().build())
            .setUpdateMask(FieldMask.newBuilder().build())
            .build();

    User actualResponse = client.updateUser(request);
    Assert.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    UpdateUserRequest actualRequest = ((UpdateUserRequest) actualRequests.get(0));

    Assert.assertEquals(request.getUser(), actualRequest.getUser());
    Assert.assertEquals(request.getUpdateMask(), actualRequest.getUpdateMask());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void updateUserExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      UpdateUserRequest request =
          UpdateUserRequest.newBuilder()
              .setUser(User.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      client.updateUser(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void deleteUserTest() throws Exception {
    Empty expectedResponse = Empty.newBuilder().build();
    mockIdentity.addResponse(expectedResponse);

    UserName name = UserName.of("[USER]");

    client.deleteUser(name);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    DeleteUserRequest actualRequest = ((DeleteUserRequest) actualRequests.get(0));

    Assert.assertEquals(name.toString(), actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void deleteUserExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      UserName name = UserName.of("[USER]");
      client.deleteUser(name);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void deleteUserTest2() throws Exception {
    Empty expectedResponse = Empty.newBuilder().build();
    mockIdentity.addResponse(expectedResponse);

    String name = "name3373707";

    client.deleteUser(name);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    DeleteUserRequest actualRequest = ((DeleteUserRequest) actualRequests.get(0));

    Assert.assertEquals(name, actualRequest.getName());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void deleteUserExceptionTest2() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      String name = "name3373707";
      client.deleteUser(name);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  public void listUsersTest() throws Exception {
    User responsesElement = User.newBuilder().build();
    ListUsersResponse expectedResponse =
        ListUsersResponse.newBuilder()
            .setNextPageToken("")
            .addAllUsers(Arrays.asList(responsesElement))
            .build();
    mockIdentity.addResponse(expectedResponse);

    ListUsersRequest request =
        ListUsersRequest.newBuilder()
            .setPageSize(883849137)
            .setPageToken("pageToken873572522")
            .build();

    ListUsersPagedResponse pagedListResponse = client.listUsers(request);

    List<User> resources = Lists.newArrayList(pagedListResponse.iterateAll());

    Assert.assertEquals(1, resources.size());
    Assert.assertEquals(expectedResponse.getUsersList().get(0), resources.get(0));

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assert.assertEquals(1, actualRequests.size());
    ListUsersRequest actualRequest = ((ListUsersRequest) actualRequests.get(0));

    Assert.assertEquals(request.getPageSize(), actualRequest.getPageSize());
    Assert.assertEquals(request.getPageToken(), actualRequest.getPageToken());
    Assert.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  public void listUsersExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      ListUsersRequest request =
          ListUsersRequest.newBuilder()
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      client.listUsers(request);
      Assert.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }
}
