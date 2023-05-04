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

import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ITDynamicRoutingHeaders {
  private EchoClient grpcClient;
  private EchoClient httpJsonClient;

  @Before
  public void createClients() throws Exception {
    // Create gRPC Echo Client
    grpcClient = TestClientInitializer.createGrpcEchoClient();
    // Create Http JSON Echo Client
    httpJsonClient = TestClientInitializer.createHttpJsonEchoClient();
  }

  @After
  public void destroyClient() {
    grpcClient.close();
    httpJsonClient.close();
  }

  @Ignore
  @Test
  public void testGrpc_matchesHeaderName() {
    EchoResponse response = grpcClient.echo(EchoRequest.newBuilder().setHeader("potato").build());
  }

  @Test
  public void testHttpJson_matchesHeaderName() {
    EchoResponse response = httpJsonClient.echo(EchoRequest.newBuilder().setHeader("potato").build());
  }
}
