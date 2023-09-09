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

/**
 * A client to Connect Gateway API
 *
 * <p>The interfaces provided are listed below, along with usage samples.
 *
 * <p>======================= GatewayServiceClient =======================
 *
 * <p>Service Description: Gateway service is a public API which works as a Kubernetes resource
 * model proxy between end users and registered Kubernetes clusters. Each RPC in this service
 * matches with an HTTP verb. End user will initiate kubectl commands against the Gateway service,
 * and Gateway service will forward user requests to clusters.
 *
 * <p>Sample for GatewayServiceClient:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * try (GatewayServiceClient gatewayServiceClient = GatewayServiceClient.create()) {
 *   HttpBody request =
 *       HttpBody.newBuilder()
 *           .setContentType("contentType-389131437")
 *           .setData(ByteString.EMPTY)
 *           .addAllExtensions(new ArrayList<Any>())
 *           .build();
 *   HttpBody response = gatewayServiceClient.getResource(request);
 * }
 * }</pre>
 */
@Generated("by gapic-generator-java")
package com.google.cloud.gkeconnect.gateway.v1beta1;

import javax.annotation.Generated;
