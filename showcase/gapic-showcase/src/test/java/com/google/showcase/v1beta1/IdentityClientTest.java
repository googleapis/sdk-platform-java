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

import static com.google.showcase.v1beta1.IdentityClient.ListLocationsPagedResponse;
import static com.google.showcase.v1beta1.IdentityClient.ListUsersPagedResponse;

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
import com.google.protobuf.Empty;
import com.google.protobuf.FieldMask;
import com.google.protobuf.Timestamp;
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
class IdentityClientTest {
  private static MockIAMPolicy mockIAMPolicy;
  private static MockIdentity mockIdentity;
  private static MockLocations mockLocations;
  private static MockServiceHelper mockServiceHelper;
  private LocalChannelProvider channelProvider;
  private IdentityClient client;

  @BeforeAll
  public static void startStaticServer() {
    mockIdentity = new MockIdentity();
    mockLocations = new MockLocations();
    mockIAMPolicy = new MockIAMPolicy();
    mockServiceHelper =
        new MockServiceHelper(
            UUID.randomUUID().toString(),
            Arrays.<MockGrpcService>asList(mockIdentity, mockLocations, mockIAMPolicy));
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
    IdentitySettings settings =
        IdentitySettings.newBuilder()
            .setTransportChannelProvider(channelProvider)
            .setCredentialsProvider(NoCredentialsProvider.create())
            .build();
    client = IdentityClient.create(settings);
  }

  @AfterEach
  void tearDown() throws Exception {
    client.close();
  }

  @Test
  void createUserTest() throws Exception {
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
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    CreateUserRequest actualRequest = ((CreateUserRequest) actualRequests.get(0));

    Assertions.assertEquals(displayName, actualRequest.getUser().getDisplayName());
    Assertions.assertEquals(email, actualRequest.getUser().getEmail());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void createUserExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      String displayName = "displayName1714148973";
      String email = "email96619420";
      client.createUser(displayName, email);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void createUserTest2() throws Exception {
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
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    CreateUserRequest actualRequest = ((CreateUserRequest) actualRequests.get(0));

    Assertions.assertEquals(displayName, actualRequest.getUser().getDisplayName());
    Assertions.assertEquals(email, actualRequest.getUser().getEmail());
    Assertions.assertEquals(age, actualRequest.getUser().getAge());
    Assertions.assertEquals(nickname, actualRequest.getUser().getNickname());
    Assertions.assertEquals(enableNotifications, actualRequest.getUser().getEnableNotifications());
    Assertions.assertEquals(heightFeet, actualRequest.getUser().getHeightFeet(), 0.0001);
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void createUserExceptionTest2() throws Exception {
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
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getUserTest() throws Exception {
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
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    GetUserRequest actualRequest = ((GetUserRequest) actualRequests.get(0));

    Assertions.assertEquals(name.toString(), actualRequest.getName());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void getUserExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      UserName name = UserName.of("[USER]");
      client.getUser(name);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void getUserTest2() throws Exception {
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
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    GetUserRequest actualRequest = ((GetUserRequest) actualRequests.get(0));

    Assertions.assertEquals(name, actualRequest.getName());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void getUserExceptionTest2() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      String name = "name3373707";
      client.getUser(name);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void updateUserTest() throws Exception {
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
    Assertions.assertEquals(expectedResponse, actualResponse);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    UpdateUserRequest actualRequest = ((UpdateUserRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getUser(), actualRequest.getUser());
    Assertions.assertEquals(request.getUpdateMask(), actualRequest.getUpdateMask());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void updateUserExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      UpdateUserRequest request =
          UpdateUserRequest.newBuilder()
              .setUser(User.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      client.updateUser(request);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void deleteUserTest() throws Exception {
    Empty expectedResponse = Empty.newBuilder().build();
    mockIdentity.addResponse(expectedResponse);

    UserName name = UserName.of("[USER]");

    client.deleteUser(name);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    DeleteUserRequest actualRequest = ((DeleteUserRequest) actualRequests.get(0));

    Assertions.assertEquals(name.toString(), actualRequest.getName());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void deleteUserExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      UserName name = UserName.of("[USER]");
      client.deleteUser(name);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void deleteUserTest2() throws Exception {
    Empty expectedResponse = Empty.newBuilder().build();
    mockIdentity.addResponse(expectedResponse);

    String name = "name3373707";

    client.deleteUser(name);

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    DeleteUserRequest actualRequest = ((DeleteUserRequest) actualRequests.get(0));

    Assertions.assertEquals(name, actualRequest.getName());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void deleteUserExceptionTest2() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      String name = "name3373707";
      client.deleteUser(name);
      Assertions.fail("No exception raised");
    } catch (InvalidArgumentException e) {
      // Expected exception.
    }
  }

  @Test
  void listUsersTest() throws Exception {
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

    Assertions.assertEquals(1, resources.size());
    Assertions.assertEquals(expectedResponse.getUsersList().get(0), resources.get(0));

    List<AbstractMessage> actualRequests = mockIdentity.getRequests();
    Assertions.assertEquals(1, actualRequests.size());
    ListUsersRequest actualRequest = ((ListUsersRequest) actualRequests.get(0));

    Assertions.assertEquals(request.getPageSize(), actualRequest.getPageSize());
    Assertions.assertEquals(request.getPageToken(), actualRequest.getPageToken());
    Assertions.assertTrue(
        channelProvider.isHeaderSent(
            ApiClientHeaderProvider.getDefaultApiClientHeaderKey(),
            GaxGrpcProperties.getDefaultApiClientHeaderPattern()));
  }

  @Test
  void listUsersExceptionTest() throws Exception {
    StatusRuntimeException exception = new StatusRuntimeException(io.grpc.Status.INVALID_ARGUMENT);
    mockIdentity.addException(exception);

    try {
      ListUsersRequest request =
          ListUsersRequest.newBuilder()
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      client.listUsers(request);
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
