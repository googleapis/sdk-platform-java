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

import static org.junit.Assert.assertThrows;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.rpc.InvalidArgumentException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NumericEnumsIT {

  private ComplianceClient client;

  @Before
  public void createClient() throws GeneralSecurityException, IOException {
    ComplianceSettings complianceSettings =
        ComplianceSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                ComplianceSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    client = ComplianceClient.create(complianceSettings);
  }

  @After
  public void destroyClient() {
    client.close();
  }

  // See
  // https://github.com/googleapis/gapic-showcase/blob/v0.25.0/util/genrest/resttools/systemparam.go#L37-L46
  @Test
  public void verifyEnums() {
    EnumRequest request = EnumRequest.newBuilder().setUnknownEnum(true).build();

    // EnumResponse initialResponse =
    assertThrows(
        InvalidArgumentException.class,
        () -> client.getEnum(request));

//    EnumResponse verifiedResponse = client.verifyEnum(initialResponse);
//
//    Assert.assertNotNull(initialResponse);
//    Assert.assertEquals(initialResponse, verifiedResponse);
  }
}
