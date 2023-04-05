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

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.protobuf.FieldMask;
import com.google.showcase.v1beta1.CreateUserRequest;
import com.google.showcase.v1beta1.DeleteUserRequest;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.IdentityClient;
import com.google.showcase.v1beta1.IdentitySettings;
import com.google.showcase.v1beta1.ListUsersRequest;
import com.google.showcase.v1beta1.ListUsersResponse;
import com.google.showcase.v1beta1.UpdateUserRequest;
import com.google.showcase.v1beta1.User;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ITCrud {

  private IdentityClient httpJsonClient;

  @Before
  public void setup() throws GeneralSecurityException, IOException {
    // Create HttpJson IdentityClient
    IdentitySettings httpJsonIdentitySettings =
        IdentitySettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    httpJsonClient = IdentityClient.create(httpJsonIdentitySettings);

    // Ensure an empty state before each run
    cleanupData(httpJsonClient);
  }

  @After
  public void cleanup() {
    httpJsonClient.close();
  }

  private void cleanupData(IdentityClient identityClient) {
    IdentityClient.ListUsersPagedResponse pagedResponse =
        identityClient.listUsers(ListUsersRequest.newBuilder().setPageSize(5).build());
    for (IdentityClient.ListUsersPage listUsersPage : pagedResponse.iteratePages()) {
      for (User user : listUsersPage.getResponse().getUsersList()) {
        identityClient.deleteUser(user.getName());
      }
    }
  }

  // This test runs through the four CRUD operations. The operations are
  // set to build off each other and live inside this one test case.
  // The tests run in the order of: Create -> List -> Update -> Delete
  @Test
  public void testHttpJson_CRUD() {
    User user =
        User.newBuilder()
            .setDisplayName("Jane Doe")
            .setEmail("janedoe@example.com")
            .setNickname("Doe")
            .setHeightFeet(5)
            .setAge(25)
            .build();
    User createUserResponse =
        httpJsonClient.createUser(CreateUserRequest.newBuilder().setUser(user).build());

    assertThat(createUserResponse.getDisplayName()).isEqualTo(user.getDisplayName());
    assertThat(createUserResponse.getEmail()).isEqualTo(user.getEmail());
    assertThat(createUserResponse.getNickname()).isEqualTo(user.getNickname());
    assertThat(createUserResponse.getHeightFeet()).isEqualTo(user.getHeightFeet());
    assertThat(createUserResponse.getAge()).isEqualTo(user.getAge());

    // Assert that the server populates these fields
    assertThat(createUserResponse.getName()).isNotEmpty();
    assertThat(createUserResponse.getCreateTime()).isNotNull();
    assertThat(createUserResponse.getUpdateTime()).isNotNull();
    assertThat(createUserResponse.getEnableNotifications()).isNotNull();

    // Assert that only one User exists
    IdentityClient.ListUsersPagedResponse listUsersPagedResponse =
        httpJsonClient.listUsers(ListUsersRequest.newBuilder().setPageSize(5).build());
    ListUsersResponse listUsersResponse = listUsersPagedResponse.getPage().getResponse();
    assertThat(listUsersResponse.getUsersList().size()).isEqualTo(1);

    // Assert that the user that exists is Jane Doe. Check that the response
    // from both List (pagination) and Get returns Jane Doe
    // List Users
    User listUserResponse = listUsersResponse.getUsers(0);
    assertThat(listUserResponse).isEqualTo(createUserResponse);

    // Get User
    User getUserResponse = httpJsonClient.getUser(createUserResponse.getName());
    assertThat(getUserResponse).isEqualTo(createUserResponse);

    // Update multiple fields in the User. Age + Nickname are not included in the FieldMask
    User updateUser =
        getUserResponse
            .toBuilder()
            .setAge(50)
            .setNickname("Smith")
            .setEmail("janedoe@jane.com")
            .setHeightFeet(6.0)
            .setEnableNotifications(true)
            .build();
    User updateUserResponse =
        httpJsonClient.updateUser(
            UpdateUserRequest.newBuilder()
                .setUser(updateUser)
                .setUpdateMask(
                    FieldMask.newBuilder()
                        .addAllPaths(Arrays.asList("email", "height_feet", "enable_notifications"))
                        .build())
                .build());

    // Assert that only the fields in the FieldMask are updated correctly
    assertThat(updateUserResponse).isNotEqualTo(getUserResponse);
    assertThat(updateUserResponse.getAge()).isEqualTo(getUserResponse.getAge());
    assertThat(updateUserResponse.getNickname()).isEqualTo(getUserResponse.getNickname());

    assertThat(updateUserResponse.getEmail()).isEqualTo(updateUser.getEmail());
    assertThat(updateUserResponse.getHeightFeet()).isEqualTo(updateUser.getHeightFeet());
    assertThat(updateUserResponse.getEnableNotifications())
        .isEqualTo(updateUser.getEnableNotifications());

    // Delete the User
    httpJsonClient.deleteUser(
        DeleteUserRequest.newBuilder().setName(getUserResponse.getName()).build());

    listUsersPagedResponse =
        httpJsonClient.listUsers(ListUsersRequest.newBuilder().setPageSize(5).build());
    assertThat(listUsersPagedResponse.getPage().getResponse().getUsersList().size()).isEqualTo(0);
  }
}
