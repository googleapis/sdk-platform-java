/*
 * Copyright 2021 Google LLC
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

package com.google.iam.v1;

import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.iam.v1.stub.IAMPolicyStub;
import com.google.iam.v1.stub.IAMPolicyStubSettings;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Service Description: ## API Overview
 *
 * <p>Manages Identity and Access Management (IAM) policies.
 *
 * <p>Any implementation of an API that offers access control features implements the
 * google.iam.v1.IAMPolicy interface.
 *
 * <p>## Data model
 *
 * <p>Access control is applied when a principal (user or service account), takes some action on a
 * resource exposed by a service. Resources, identified by URI-like names, are the unit of access
 * control specification. Service implementations can choose the granularity of access control and
 * the supported permissions for their resources. For example one database service may allow access
 * control to be specified only at the Table level, whereas another might allow access control to
 * also be specified at the Column level.
 *
 * <p>## Policy Structure
 *
 * <p>See google.iam.v1.Policy
 *
 * <p>This is intentionally not a CRUD style API because access control policies are created and
 * deleted implicitly with the resources to which they are attached.
 *
 * <p>This class provides the ability to make remote calls to the backing service through method
 * calls that map to API methods. Sample code to get started:
 *
 * <pre>{@code
 * package com.google.example;
 *
 * import com.google.iam.v1.IAMPolicyClient;
 * import com.google.iam.v1.Policy;
 * import com.google.iam.v1.SetIamPolicyRequest;
 *
 * public class IAMPolicyClientSetIamPolicy {
 *
 *   public static void main(String[] args) throws Exception {
 *     iAMPolicyClientSetIamPolicy();
 *   }
 *
 *   public static void iAMPolicyClientSetIamPolicy() throws Exception {
 *     try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
 *       SetIamPolicyRequest request =
 *           SetIamPolicyRequest.newBuilder()
 *               .setResource("SetIamPolicyRequest1223629066".toString())
 *               .setPolicy(Policy.newBuilder().build())
 *               .build();
 *       Policy response = iAMPolicyClient.setIamPolicy(request);
 *     }
 *   }
 * }
 * }</pre>
 *
 * <p>Note: close() needs to be called on the IAMPolicyClient object to clean up resources such as
 * threads. In the example above, try-with-resources is used, which automatically calls close().
 *
 * <p>The surface of this class includes several types of Java methods for each of the API's
 * methods:
 *
 * <ol>
 *   <li>A "flattened" method. With this type of method, the fields of the request type have been
 *       converted into function parameters. It may be the case that not all fields are available as
 *       parameters, and not every API method will have a flattened method entry point.
 *   <li>A "request object" method. This type of method only takes one parameter, a request object,
 *       which must be constructed before the call. Not every API method will have a request object
 *       method.
 *   <li>A "callable" method. This type of method takes no parameters and returns an immutable API
 *       callable object, which can be used to initiate calls to the service.
 * </ol>
 *
 * <p>See the individual methods for example code.
 *
 * <p>Many parameters require resource names to be formatted in a particular way. To assist with
 * these names, this class includes a format method for each type of name, and additionally a parse
 * method to extract the individual identifiers contained within names that are returned.
 *
 * <p>This class can be customized by passing in a custom instance of IAMPolicySettings to create().
 * For example:
 *
 * <p>To customize credentials:
 *
 * <pre>{@code
 * package com.google.example;
 *
 * import com.google.api.gax.core.FixedCredentialsProvider;
 * import com.google.iam.v1.IAMPolicyClient;
 * import com.google.iam.v1.IAMPolicySettings;
 * import com.google.iam.v1.myCredentials;
 *
 * public class IAMPolicyClientCreate {
 *
 *   public static void main(String[] args) throws Exception {
 *     iAMPolicyClientCreate();
 *   }
 *
 *   public static void iAMPolicyClientCreate() throws Exception {
 *     IAMPolicySettings iAMPolicySettings =
 *         IAMPolicySettings.newBuilder()
 *             .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
 *             .build();
 *     IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create(iAMPolicySettings);
 *   }
 * }
 * }</pre>
 *
 * <p>To customize the endpoint:
 *
 * <pre>{@code
 * package com.google.example;
 *
 * import com.google.iam.v1.IAMPolicyClient;
 * import com.google.iam.v1.IAMPolicySettings;
 * import com.google.iam.v1.myEndpoint;
 *
 * public class IAMPolicyClientClassHeaderEndpoint {
 *
 *   public static void main(String[] args) throws Exception {
 *     iAMPolicyClientClassHeaderEndpoint();
 *   }
 *
 *   public static void iAMPolicyClientClassHeaderEndpoint() throws Exception {
 *     IAMPolicySettings iAMPolicySettings =
 *         IAMPolicySettings.newBuilder().setEndpoint(myEndpoint).build();
 *     IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create(iAMPolicySettings);
 *   }
 * }
 * }</pre>
 *
 * <p>Please refer to the GitHub repository's samples for more quickstart code snippets.
 */
@Generated("by gapic-generator-java")
public class IAMPolicyClient implements BackgroundResource {
  private final IAMPolicySettings settings;
  private final IAMPolicyStub stub;

  /** Constructs an instance of IAMPolicyClient with default settings. */
  public static final IAMPolicyClient create() throws IOException {
    return create(IAMPolicySettings.newBuilder().build());
  }

  /**
   * Constructs an instance of IAMPolicyClient, using the given settings. The channels are created
   * based on the settings passed in, or defaults for any settings that are not set.
   */
  public static final IAMPolicyClient create(IAMPolicySettings settings) throws IOException {
    return new IAMPolicyClient(settings);
  }

  /**
   * Constructs an instance of IAMPolicyClient, using the given stub for making calls. This is for
   * advanced usage - prefer using create(IAMPolicySettings).
   */
  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  public static final IAMPolicyClient create(IAMPolicyStub stub) {
    return new IAMPolicyClient(stub);
  }

  /**
   * Constructs an instance of IAMPolicyClient, using the given settings. This is protected so that
   * it is easy to make a subclass, but otherwise, the static factory methods should be preferred.
   */
  protected IAMPolicyClient(IAMPolicySettings settings) throws IOException {
    this.settings = settings;
    this.stub = ((IAMPolicyStubSettings) settings.getStubSettings()).createStub();
  }

  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  protected IAMPolicyClient(IAMPolicyStub stub) {
    this.settings = null;
    this.stub = stub;
  }

  public final IAMPolicySettings getSettings() {
    return settings;
  }

  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  public IAMPolicyStub getStub() {
    return stub;
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Sets the access control policy on the specified resource. Replaces any existing policy.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * package com.google.example;
   *
   * import com.google.iam.v1.IAMPolicyClient;
   * import com.google.iam.v1.Policy;
   * import com.google.iam.v1.SetIamPolicyRequest;
   *
   * public class IAMPolicyClientSetIamPolicy {
   *
   *   public static void main(String[] args) throws Exception {
   *     iAMPolicyClientSetIamPolicy();
   *   }
   *
   *   public static void iAMPolicyClientSetIamPolicy() throws Exception {
   *     try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
   *       SetIamPolicyRequest request =
   *           SetIamPolicyRequest.newBuilder()
   *               .setResource("SetIamPolicyRequest1223629066".toString())
   *               .setPolicy(Policy.newBuilder().build())
   *               .build();
   *       Policy response = iAMPolicyClient.setIamPolicy(request);
   *     }
   *   }
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Policy setIamPolicy(SetIamPolicyRequest request) {
    return setIamPolicyCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Sets the access control policy on the specified resource. Replaces any existing policy.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * package com.google.example;
   *
   * import com.google.api.core.ApiFuture;
   * import com.google.iam.v1.IAMPolicyClient;
   * import com.google.iam.v1.Policy;
   * import com.google.iam.v1.SetIamPolicyRequest;
   *
   * public class IAMPolicyClientSetIamPolicy {
   *
   *   public static void main(String[] args) throws Exception {
   *     iAMPolicyClientSetIamPolicy();
   *   }
   *
   *   public static void iAMPolicyClientSetIamPolicy() throws Exception {
   *     try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
   *       SetIamPolicyRequest request =
   *           SetIamPolicyRequest.newBuilder()
   *               .setResource("SetIamPolicyRequest1223629066".toString())
   *               .setPolicy(Policy.newBuilder().build())
   *               .build();
   *       ApiFuture<Policy> future = iAMPolicyClient.setIamPolicyCallable().futureCall(request);
   *       // Do something.
   *       Policy response = future.get();
   *     }
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<SetIamPolicyRequest, Policy> setIamPolicyCallable() {
    return stub.setIamPolicyCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the access control policy for a resource. Returns an empty policy if the resource exists
   * and does not have a policy set.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * package com.google.example;
   *
   * import com.google.iam.v1.GetIamPolicyRequest;
   * import com.google.iam.v1.GetPolicyOptions;
   * import com.google.iam.v1.IAMPolicyClient;
   * import com.google.iam.v1.Policy;
   *
   * public class IAMPolicyClientGetIamPolicy {
   *
   *   public static void main(String[] args) throws Exception {
   *     iAMPolicyClientGetIamPolicy();
   *   }
   *
   *   public static void iAMPolicyClientGetIamPolicy() throws Exception {
   *     try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
   *       GetIamPolicyRequest request =
   *           GetIamPolicyRequest.newBuilder()
   *               .setResource("GetIamPolicyRequest-1527610370".toString())
   *               .setOptions(GetPolicyOptions.newBuilder().build())
   *               .build();
   *       Policy response = iAMPolicyClient.getIamPolicy(request);
   *     }
   *   }
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Policy getIamPolicy(GetIamPolicyRequest request) {
    return getIamPolicyCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the access control policy for a resource. Returns an empty policy if the resource exists
   * and does not have a policy set.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * package com.google.example;
   *
   * import com.google.api.core.ApiFuture;
   * import com.google.iam.v1.GetIamPolicyRequest;
   * import com.google.iam.v1.GetPolicyOptions;
   * import com.google.iam.v1.IAMPolicyClient;
   * import com.google.iam.v1.Policy;
   *
   * public class IAMPolicyClientGetIamPolicy {
   *
   *   public static void main(String[] args) throws Exception {
   *     iAMPolicyClientGetIamPolicy();
   *   }
   *
   *   public static void iAMPolicyClientGetIamPolicy() throws Exception {
   *     try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
   *       GetIamPolicyRequest request =
   *           GetIamPolicyRequest.newBuilder()
   *               .setResource("GetIamPolicyRequest-1527610370".toString())
   *               .setOptions(GetPolicyOptions.newBuilder().build())
   *               .build();
   *       ApiFuture<Policy> future = iAMPolicyClient.getIamPolicyCallable().futureCall(request);
   *       // Do something.
   *       Policy response = future.get();
   *     }
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<GetIamPolicyRequest, Policy> getIamPolicyCallable() {
    return stub.getIamPolicyCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Returns permissions that a caller has on the specified resource. If the resource does not
   * exist, this will return an empty set of permissions, not a NOT_FOUND error.
   *
   * <p>Note: This operation is designed to be used for building permission-aware UIs and
   * command-line tools, not for authorization checking. This operation may "fail open" without
   * warning.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * package com.google.example;
   *
   * import com.google.iam.v1.IAMPolicyClient;
   * import com.google.iam.v1.TestIamPermissionsRequest;
   * import com.google.iam.v1.TestIamPermissionsResponse;
   * import java.util.ArrayList;
   *
   * public class IAMPolicyClientTestIamPermissions {
   *
   *   public static void main(String[] args) throws Exception {
   *     iAMPolicyClientTestIamPermissions();
   *   }
   *
   *   public static void iAMPolicyClientTestIamPermissions() throws Exception {
   *     try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
   *       TestIamPermissionsRequest request =
   *           TestIamPermissionsRequest.newBuilder()
   *               .setResource("TestIamPermissionsRequest942398222".toString())
   *               .addAllPermissions(new ArrayList<String>())
   *               .build();
   *       TestIamPermissionsResponse response = iAMPolicyClient.testIamPermissions(request);
   *     }
   *   }
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final TestIamPermissionsResponse testIamPermissions(TestIamPermissionsRequest request) {
    return testIamPermissionsCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Returns permissions that a caller has on the specified resource. If the resource does not
   * exist, this will return an empty set of permissions, not a NOT_FOUND error.
   *
   * <p>Note: This operation is designed to be used for building permission-aware UIs and
   * command-line tools, not for authorization checking. This operation may "fail open" without
   * warning.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * package com.google.example;
   *
   * import com.google.api.core.ApiFuture;
   * import com.google.iam.v1.IAMPolicyClient;
   * import com.google.iam.v1.TestIamPermissionsRequest;
   * import com.google.iam.v1.TestIamPermissionsResponse;
   * import java.util.ArrayList;
   *
   * public class IAMPolicyClientTestIamPermissions {
   *
   *   public static void main(String[] args) throws Exception {
   *     iAMPolicyClientTestIamPermissions();
   *   }
   *
   *   public static void iAMPolicyClientTestIamPermissions() throws Exception {
   *     try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
   *       TestIamPermissionsRequest request =
   *           TestIamPermissionsRequest.newBuilder()
   *               .setResource("TestIamPermissionsRequest942398222".toString())
   *               .addAllPermissions(new ArrayList<String>())
   *               .build();
   *       ApiFuture<TestIamPermissionsResponse> future =
   *           iAMPolicyClient.testIamPermissionsCallable().futureCall(request);
   *       // Do something.
   *       TestIamPermissionsResponse response = future.get();
   *     }
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<TestIamPermissionsRequest, TestIamPermissionsResponse>
      testIamPermissionsCallable() {
    return stub.testIamPermissionsCallable();
  }

  @Override
  public final void close() {
    stub.close();
  }

  @Override
  public void shutdown() {
    stub.shutdown();
  }

  @Override
  public boolean isShutdown() {
    return stub.isShutdown();
  }

  @Override
  public boolean isTerminated() {
    return stub.isTerminated();
  }

  @Override
  public void shutdownNow() {
    stub.shutdownNow();
  }

  @Override
  public boolean awaitTermination(long duration, TimeUnit unit) throws InterruptedException {
    return stub.awaitTermination(duration, unit);
  }
}
