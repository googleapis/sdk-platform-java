package com.google.showcase.v1beta1;

import static org.junit.Assert.assertEquals;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FirstHttpIT {

  private static EchoClient client;

  @BeforeClass
  public static void createClient() throws IOException, GeneralSecurityException {
    EchoSettings echoSettings = EchoSettings.newBuilder()
        .setCredentialsProvider(NoCredentialsProvider.create())
        .setTransportChannelProvider(EchoSettings.defaultHttpJsonTransportProviderBuilder()
            .setHttpTransport(new NetHttpTransport.Builder().doNotValidateCertificate().build())
            .setEndpoint("http://localhost:7469").build())
        .build();

    client = EchoClient.create(echoSettings);
  }

  @AfterClass
  public static void destroyClient() {
    client.close();
  }

  @Test
  public void testEcho() {
    assertEquals("http-echo?", echo("http-echo?"));
    assertEquals("http-echo!", echo("http-echo!"));
  }

  private String echo(String value) {
    EchoResponse response = client.echo(EchoRequest.newBuilder().setContent(value).build());
    return response.getContent();
  }

}
