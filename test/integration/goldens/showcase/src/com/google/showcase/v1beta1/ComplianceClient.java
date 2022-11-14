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

import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.showcase.v1beta1.stub.ComplianceStub;
import com.google.showcase.v1beta1.stub.ComplianceStubSettings;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Service Description: This service is used to test that GAPICs implement various REST-related
 * features correctly. This mostly means transcoding proto3 requests to REST format correctly for
 * various types of HTTP annotations, but it also includes verifying that unknown (numeric) enums
 * received by clients can be round-tripped correctly.
 *
 * <p>This class provides the ability to make remote calls to the backing service through method
 * calls that map to API methods. Sample code to get started:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * try (ComplianceClient complianceClient = ComplianceClient.create()) {
 *   RepeatRequest request =
 *       RepeatRequest.newBuilder()
 *           .setName("name3373707")
 *           .setInfo(ComplianceData.newBuilder().build())
 *           .setServerVerify(true)
 *           .setIntendedBindingUri("intendedBindingUri780142386")
 *           .setFInt32(-1143775883)
 *           .setFInt64(-1143775788)
 *           .setFDouble(-1239459382)
 *           .setPInt32(-858673665)
 *           .setPInt64(-858673570)
 *           .setPDouble(-991225216)
 *           .build();
 *   RepeatResponse response = complianceClient.repeatDataBody(request);
 * }
 * }</pre>
 *
 * <p>Note: close() needs to be called on the ComplianceClient object to clean up resources such as
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
 * <p>This class can be customized by passing in a custom instance of ComplianceSettings to
 * create(). For example:
 *
 * <p>To customize credentials:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * ComplianceSettings complianceSettings =
 *     ComplianceSettings.newBuilder()
 *         .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
 *         .build();
 * ComplianceClient complianceClient = ComplianceClient.create(complianceSettings);
 * }</pre>
 *
 * <p>To customize the endpoint:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * ComplianceSettings complianceSettings =
 *     ComplianceSettings.newBuilder().setEndpoint(myEndpoint).build();
 * ComplianceClient complianceClient = ComplianceClient.create(complianceSettings);
 * }</pre>
 *
 * <p>To use REST (HTTP1.1/JSON) transport (instead of gRPC) for sending and receiving requests over
 * the wire:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * ComplianceSettings complianceSettings = ComplianceSettings.newHttpJsonBuilder().build();
 * ComplianceClient complianceClient = ComplianceClient.create(complianceSettings);
 * }</pre>
 *
 * <p>Please refer to the GitHub repository's samples for more quickstart code snippets.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class ComplianceClient implements BackgroundResource {
  private final ComplianceSettings settings;
  private final ComplianceStub stub;

  /** Constructs an instance of ComplianceClient with default settings. */
  public static final ComplianceClient create() throws IOException {
    return create(ComplianceSettings.newBuilder().build());
  }

  /**
   * Constructs an instance of ComplianceClient, using the given settings. The channels are created
   * based on the settings passed in, or defaults for any settings that are not set.
   */
  public static final ComplianceClient create(ComplianceSettings settings) throws IOException {
    return new ComplianceClient(settings);
  }

  /**
   * Constructs an instance of ComplianceClient, using the given stub for making calls. This is for
   * advanced usage - prefer using create(ComplianceSettings).
   */
  public static final ComplianceClient create(ComplianceStub stub) {
    return new ComplianceClient(stub);
  }

  /**
   * Constructs an instance of ComplianceClient, using the given settings. This is protected so that
   * it is easy to make a subclass, but otherwise, the static factory methods should be preferred.
   */
  protected ComplianceClient(ComplianceSettings settings) throws IOException {
    this.settings = settings;
    this.stub = ((ComplianceStubSettings) settings.getStubSettings()).createStub();
  }

  protected ComplianceClient(ComplianceStub stub) {
    this.settings = null;
    this.stub = stub;
  }

  public final ComplianceSettings getSettings() {
    return settings;
  }

  public ComplianceStub getStub() {
    return stub;
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request. This method exercises sending the entire request
   * object in the REST body.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   RepeatResponse response = complianceClient.repeatDataBody(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final RepeatResponse repeatDataBody(RepeatRequest request) {
    return repeatDataBodyCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request. This method exercises sending the entire request
   * object in the REST body.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   ApiFuture<RepeatResponse> future =
   *       complianceClient.repeatDataBodyCallable().futureCall(request);
   *   // Do something.
   *   RepeatResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyCallable() {
    return stub.repeatDataBodyCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request. This method exercises sending the a message-type
   * field in the REST body. Per AIP-127, only top-level, non-repeated fields can be sent this way.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   RepeatResponse response = complianceClient.repeatDataBodyInfo(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final RepeatResponse repeatDataBodyInfo(RepeatRequest request) {
    return repeatDataBodyInfoCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request. This method exercises sending the a message-type
   * field in the REST body. Per AIP-127, only top-level, non-repeated fields can be sent this way.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   ApiFuture<RepeatResponse> future =
   *       complianceClient.repeatDataBodyInfoCallable().futureCall(request);
   *   // Do something.
   *   RepeatResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyInfoCallable() {
    return stub.repeatDataBodyInfoCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request. This method exercises sending all request fields
   * as query parameters.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   RepeatResponse response = complianceClient.repeatDataQuery(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final RepeatResponse repeatDataQuery(RepeatRequest request) {
    return repeatDataQueryCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request. This method exercises sending all request fields
   * as query parameters.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   ApiFuture<RepeatResponse> future =
   *       complianceClient.repeatDataQueryCallable().futureCall(request);
   *   // Do something.
   *   RepeatResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataQueryCallable() {
    return stub.repeatDataQueryCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request. This method exercises sending some parameters as
   * "simple" path variables (i.e., of the form "/bar/{foo}" rather than "/{foo=bar/&#42;}"), and
   * the rest as query parameters.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   RepeatResponse response = complianceClient.repeatDataSimplePath(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final RepeatResponse repeatDataSimplePath(RepeatRequest request) {
    return repeatDataSimplePathCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request. This method exercises sending some parameters as
   * "simple" path variables (i.e., of the form "/bar/{foo}" rather than "/{foo=bar/&#42;}"), and
   * the rest as query parameters.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   ApiFuture<RepeatResponse> future =
   *       complianceClient.repeatDataSimplePathCallable().futureCall(request);
   *   // Do something.
   *   RepeatResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataSimplePathCallable() {
    return stub.repeatDataSimplePathCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Same as RepeatDataSimplePath, but with a path resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   RepeatResponse response = complianceClient.repeatDataPathResource(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final RepeatResponse repeatDataPathResource(RepeatRequest request) {
    return repeatDataPathResourceCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Same as RepeatDataSimplePath, but with a path resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   ApiFuture<RepeatResponse> future =
   *       complianceClient.repeatDataPathResourceCallable().futureCall(request);
   *   // Do something.
   *   RepeatResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataPathResourceCallable() {
    return stub.repeatDataPathResourceCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Same as RepeatDataSimplePath, but with a trailing resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   RepeatResponse response = complianceClient.repeatDataPathTrailingResource(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final RepeatResponse repeatDataPathTrailingResource(RepeatRequest request) {
    return repeatDataPathTrailingResourceCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Same as RepeatDataSimplePath, but with a trailing resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   ApiFuture<RepeatResponse> future =
   *       complianceClient.repeatDataPathTrailingResourceCallable().futureCall(request);
   *   // Do something.
   *   RepeatResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<RepeatRequest, RepeatResponse>
      repeatDataPathTrailingResourceCallable() {
    return stub.repeatDataPathTrailingResourceCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request, using the HTTP PUT method.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   RepeatResponse response = complianceClient.repeatDataBodyPut(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final RepeatResponse repeatDataBodyPut(RepeatRequest request) {
    return repeatDataBodyPutCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request, using the HTTP PUT method.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   ApiFuture<RepeatResponse> future =
   *       complianceClient.repeatDataBodyPutCallable().futureCall(request);
   *   // Do something.
   *   RepeatResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyPutCallable() {
    return stub.repeatDataBodyPutCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request, using the HTTP PATCH method.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   RepeatResponse response = complianceClient.repeatDataBodyPatch(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final RepeatResponse repeatDataBodyPatch(RepeatRequest request) {
    return repeatDataBodyPatchCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method echoes the ComplianceData request, using the HTTP PATCH method.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   RepeatRequest request =
   *       RepeatRequest.newBuilder()
   *           .setName("name3373707")
   *           .setInfo(ComplianceData.newBuilder().build())
   *           .setServerVerify(true)
   *           .setIntendedBindingUri("intendedBindingUri780142386")
   *           .setFInt32(-1143775883)
   *           .setFInt64(-1143775788)
   *           .setFDouble(-1239459382)
   *           .setPInt32(-858673665)
   *           .setPInt64(-858673570)
   *           .setPDouble(-991225216)
   *           .build();
   *   ApiFuture<RepeatResponse> future =
   *       complianceClient.repeatDataBodyPatchCallable().futureCall(request);
   *   // Do something.
   *   RepeatResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<RepeatRequest, RepeatResponse> repeatDataBodyPatchCallable() {
    return stub.repeatDataBodyPatchCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method requests an enum value from the server. Depending on the contents of EnumRequest,
   * the enum value returned will be a known enum declared in the .proto file, or a made-up enum
   * value the is unknown to the client. To verify that clients can round-trip unknown enum vaues
   * they receive, use the response from this RPC as the request to VerifyEnum()
   *
   * <p>The values of enums sent by the server when a known or unknown value is requested will be
   * the same within a single Showcase server run (this is needed for VerifyEnum() to work) but are
   * not guaranteed to be the same across separate Showcase server runs.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   EnumRequest request = EnumRequest.newBuilder().setUnknownEnum(true).build();
   *   EnumResponse response = complianceClient.getEnum(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final EnumResponse getEnum(EnumRequest request) {
    return getEnumCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method requests an enum value from the server. Depending on the contents of EnumRequest,
   * the enum value returned will be a known enum declared in the .proto file, or a made-up enum
   * value the is unknown to the client. To verify that clients can round-trip unknown enum vaues
   * they receive, use the response from this RPC as the request to VerifyEnum()
   *
   * <p>The values of enums sent by the server when a known or unknown value is requested will be
   * the same within a single Showcase server run (this is needed for VerifyEnum() to work) but are
   * not guaranteed to be the same across separate Showcase server runs.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   EnumRequest request = EnumRequest.newBuilder().setUnknownEnum(true).build();
   *   ApiFuture<EnumResponse> future = complianceClient.getEnumCallable().futureCall(request);
   *   // Do something.
   *   EnumResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<EnumRequest, EnumResponse> getEnumCallable() {
    return stub.getEnumCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method is used to verify that clients can round-trip enum values, which is particularly
   * important for unknown enum values over REST. VerifyEnum() verifies that its request, which is
   * presumably the response that the client previously got to a GetEnum(), contains the correct
   * data. If so, it responds with the same EnumResponse; otherwise, the RPC errors.
   *
   * <p>This works because the values of enums sent by the server when a known or unknown value is
   * requested will be the same within a single Showcase server run, although they are not
   * guaranteed to be the same across separate Showcase server runs.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   EnumResponse request =
   *       EnumResponse.newBuilder()
   *           .setRequest(EnumRequest.newBuilder().build())
   *           .setContinent(Continent.forNumber(0))
   *           .build();
   *   EnumResponse response = complianceClient.verifyEnum(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final EnumResponse verifyEnum(EnumResponse request) {
    return verifyEnumCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method is used to verify that clients can round-trip enum values, which is particularly
   * important for unknown enum values over REST. VerifyEnum() verifies that its request, which is
   * presumably the response that the client previously got to a GetEnum(), contains the correct
   * data. If so, it responds with the same EnumResponse; otherwise, the RPC errors.
   *
   * <p>This works because the values of enums sent by the server when a known or unknown value is
   * requested will be the same within a single Showcase server run, although they are not
   * guaranteed to be the same across separate Showcase server runs.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (ComplianceClient complianceClient = ComplianceClient.create()) {
   *   EnumResponse request =
   *       EnumResponse.newBuilder()
   *           .setRequest(EnumRequest.newBuilder().build())
   *           .setContinent(Continent.forNumber(0))
   *           .build();
   *   ApiFuture<EnumResponse> future = complianceClient.verifyEnumCallable().futureCall(request);
   *   // Do something.
   *   EnumResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<EnumResponse, EnumResponse> verifyEnumCallable() {
    return stub.verifyEnumCallable();
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
