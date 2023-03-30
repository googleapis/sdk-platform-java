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

  @Test
  public void testHttpJson_UserCRUD() {
    CreateUserRequest createUserRequest =
        CreateUserRequest.newBuilder()
            .setUser(
                User.newBuilder()
                    .setDisplayName("Jane Doe")
                    .setEmail("janedoe@example.com")
                    .setNickname("Doe")
                    .setHeightFeet(5)
                    .build())
            .build();
    User user = httpJsonClient.createUser(createUserRequest);
    User expected = createUserRequest.getUser();

    assertThat(user.getName()).isNotEmpty();
    assertThat(user.getDisplayName()).isEqualTo(expected.getDisplayName());
    assertThat(user.getEmail()).isEqualTo(expected.getEmail());
    assertThat(user.getCreateTime()).isNotNull();
    assertThat(user.getUpdateTime()).isNotNull();
    assertThat(user.getNickname()).isEqualTo(expected.getNickname());
    assertThat(user.getHeightFeet()).isEqualTo(expected.getHeightFeet());
    assertThat(user.getAge()).isNotNull();
    assertThat(user.getEnableNotifications()).isNotNull();

    ListUsersRequest listUsersRequest = ListUsersRequest.newBuilder().setPageSize(5).build();
    IdentityClient.ListUsersPagedResponse listUsersPagedResponse =
        httpJsonClient.listUsers(listUsersRequest);
    assertThat(listUsersPagedResponse.getPage().getResponse().getUsersList().size()).isEqualTo(1);

    User listUserResponse = listUsersPagedResponse.getPage().getResponse().getUsers(0);
    assertThat(listUserResponse).isEqualTo(user);

    User getUserResponse = httpJsonClient.getUser(user.getName());
    assertThat(getUserResponse).isEqualTo(user);

    UpdateUserRequest updateUserRequest =
        UpdateUserRequest.newBuilder()
            .setUser(
                user.toBuilder()
                    .setEmail("janedoe@jane.com")
                    .setHeightFeet(6.0)
                    .setEnableNotifications(true)
                    .build())
            .setUpdateMask(
                FieldMask.newBuilder()
                    .addAllPaths(Arrays.asList("email", "height_feet", "enable_notifications"))
                    .build())
            .build();
    User updatedUser = httpJsonClient.updateUser(updateUserRequest);

    assertThat(updatedUser).isNotEqualTo(user);
    assertThat(updatedUser.getEmail()).isNotEqualTo(user.getEmail());
    assertThat(updatedUser.getHeightFeet()).isNotEqualTo(user.getHeightFeet());

    assertThat(updatedUser.getEnableNotifications()).isNotEqualTo(user.getEnableNotifications());
    assertThat(updatedUser.getAge()).isNotNull();

    httpJsonClient.deleteUser(DeleteUserRequest.newBuilder().setName(user.getName()).build());

    listUsersPagedResponse = httpJsonClient.listUsers(listUsersRequest);
    assertThat(listUsersPagedResponse.getPage().getResponse().getUsersList().size()).isEqualTo(0);
  }
}
