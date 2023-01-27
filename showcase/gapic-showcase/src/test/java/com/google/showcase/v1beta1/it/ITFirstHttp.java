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

package com.google.showcase.v1beta1.it;

import static org.junit.Assert.assertEquals;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.EchoSettings;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITFirstHttp {

  private static EchoClient client;

  @BeforeClass
  public static void createClient() throws IOException, GeneralSecurityException {
    EchoSettings echoSettings =
        EchoSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
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