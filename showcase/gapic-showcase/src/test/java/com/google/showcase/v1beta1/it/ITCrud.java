package com.google.showcase.v1beta1.it;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.protobuf.FieldMask;
import com.google.showcase.v1beta1.CreateUserRequest;
import com.google.showcase.v1beta1.DeleteUserRequest;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.IdentityClient;
import com.google.showcase.v1beta1.IdentitySettings;
import com.google.showcase.v1beta1.ListUsersRequest;
import com.google.showcase.v1beta1.UpdateUserRequest;
import com.google.showcase.v1beta1.User;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ITCrud {

  @Parameterized.Parameters(name = "CRUD Transport: {0}")
  public static String[] transports() {
    return new String[] {"gRPC", "httpjson"};
  }

  @Parameterized.Parameter(0)
  public String transportName;

  private IdentitySettings getTransportSettings(String transportName)
      throws IOException, GeneralSecurityException {
    switch (transportName) {
      case "gRPC":
        return IdentitySettings.newBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                InstantiatingGrpcChannelProvider.newBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
      case "httpjson":
        return IdentitySettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
      default:
        throw new IllegalArgumentException("Invalid transport name: " + transportName);
    }
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
  public void testUserCRUD() throws IOException, GeneralSecurityException {
    IdentitySettings identitySettings = getTransportSettings(transportName);
    try (IdentityClient identityClient = IdentityClient.create(identitySettings)) {
      cleanupData(identityClient);

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
      User user = identityClient.createUser(createUserRequest);
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
          identityClient.listUsers(listUsersRequest);
      assertThat(listUsersPagedResponse.getPage().getResponse().getUsersList().size()).isEqualTo(1);

      User listUserResponse = listUsersPagedResponse.getPage().getResponse().getUsers(0);
      assertThat(listUserResponse).isEqualTo(user);

      User getUserResponse = identityClient.getUser(user.getName());
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
      User updatedUser = identityClient.updateUser(updateUserRequest);

      assertThat(updatedUser).isNotEqualTo(user);
      assertThat(updatedUser.getEmail()).isNotEqualTo(user.getEmail());
      assertThat(updatedUser.getHeightFeet()).isNotEqualTo(user.getHeightFeet());

      assertThat(updatedUser.getEnableNotifications()).isNotEqualTo(user.getEnableNotifications());
      assertThat(updatedUser.getAge()).isNotNull();

      identityClient.deleteUser(DeleteUserRequest.newBuilder().setName(user.getName()).build());

      listUsersPagedResponse = identityClient.listUsers(listUsersRequest);
      assertThat(listUsersPagedResponse.getPage().getResponse().getUsersList().size()).isEqualTo(0);
    }
  }
}
