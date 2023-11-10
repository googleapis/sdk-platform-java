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

import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoErrorDetailsRequest;
import com.google.showcase.v1beta1.EchoErrorDetailsResponse;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITErrorDetails {
  private static EchoClient httpjsonClient;

  @BeforeClass
  public static void createClients() throws Exception {
    // Create Http JSON Echo Client
    httpjsonClient = TestClientInitializer.createHttpJsonEchoClient();
  }

  @AfterClass
  public static void destroyClients() throws InterruptedException {
    httpjsonClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void testEchoErrorDetails() {
    EchoErrorDetailsResponse response = httpjsonClient.echoErrorDetails(
        EchoErrorDetailsRequest
            .newBuilder()
            .setSingleDetailText("singleDetailText1774380934")
            .addAllMultiDetailText(new ArrayList<>())
            .build());
    assertThat(response.hasSingleDetail()).isEqualTo(true);
  }
}
