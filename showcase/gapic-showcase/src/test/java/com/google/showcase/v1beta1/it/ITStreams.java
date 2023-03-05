package com.google.showcase.v1beta1.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.paging.Page;
import com.google.showcase.v1beta1.IdentityClient;
import com.google.showcase.v1beta1.IdentityClient.ListUsersPagedResponse;
import com.google.showcase.v1beta1.IdentitySettings;
import com.google.showcase.v1beta1.ListUsersRequest;
import com.google.showcase.v1beta1.User;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ITStreams {
  private IdentityClient client;

  @Before
  public void initClient() throws GeneralSecurityException, IOException {
    IdentitySettings identitySettings =
        IdentitySettings.newBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                IdentitySettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    client = IdentityClient.create(identitySettings);
  }

  @After
  public void destroyClient() {
    client.close();
  }

  @Test
  public void verifyStreamAll() {
    create100Users();
    ListUsersRequest request = ListUsersRequest.newBuilder()
        .setPageSize(50)
        .setPageToken("PageToken123")
        .build();
    ListUsersPagedResponse response = client.listUsers(request);
    assertEquals(100, response.getPage().streamAll().count());
  }

  @Test
  public void verifyStreamValues() {
    create100Users();
    ListUsersRequest request = ListUsersRequest.newBuilder()
        .setPageSize(50)
        .setPageToken("PageToken123")
        .build();
    Page<User> pages = client.listUsers(request).getPage();
    assertEquals(50, pages.streamValues().count());
    assertEquals(50, pages.getNextPage().streamValues().count());
    assertFalse(pages.hasNextPage());
  }

  private void create100Users() {
    Random random = new Random();
    for (int i = 0; i < 100; i++) {
      String randomName = String.format("random%s", random.nextInt());
      String randomEmail = String.format("%s@google.com", randomName);
      client.createUser(randomName, randomEmail);
    }
  }
}
