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

import com.google.iam.v1.Binding;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ITIam {
  private static EchoClient grpcClient;
  private static EchoClient httpjsonClient;

  @BeforeClass
  public static void createClients() throws Exception {
    grpcClient = TestClientInitializer.createGrpcEchoClient();
    httpjsonClient = TestClientInitializer.createHttpJsonEchoClient();
  }

  @AfterClass
  public static void destroyClients() throws InterruptedException {
    grpcClient.close();
    httpjsonClient.close();

    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
    httpjsonClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  public void testGrpc_setIamPolicy() {
    SetIamPolicyRequest policyRequest = SetIamPolicyRequest.newBuilder()
            .setPolicy(
                    Policy.newBuilder()
                            .addBindings(Binding.newBuilder().setRole("foo.editor").addMembers("allUsers")))
            .build();
  }
}
