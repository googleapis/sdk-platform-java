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

/**
 * The interfaces provided are listed below, along with usage samples.
 *
 * <p>======================= WickedClient =======================
 *
 * <p>Service Description: This service is used to show a Service with either non-enabled or
 * non-eligible RPCs for HttpJson (Http 1.1). Non-Enabled: Missing the (google.api.http) annotation
 * to enabled it Non-Eligible: BIDI and Client side streaming are not supported with Http 1.1
 * Service name is reference to `No REST for the Wicked` (Credit: {@literal @}BurkeDavison)
 *
 * <p>Sample for WickedClient:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * try (WickedClient wickedClient = WickedClient.create()) {
 *   EvilRequest request =
 *       EvilRequest.newBuilder().setMaliciousIdea("maliciousIdea712541645").build();
 *   EvilResponse response = wickedClient.craftEvilPlan(request);
 * }
 * }</pre>
 */
@Generated("by gapic-generator-java")
package com.google.showcase.v1beta1;

import javax.annotation.Generated;
