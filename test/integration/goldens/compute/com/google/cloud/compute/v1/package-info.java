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

/**
 * The interfaces provided are listed below, along with usage samples.
 *
 * <p>======================= AddressesClient =======================
 *
 * <p>Service Description: Services
 *
 * <p>The Addresses API.
 *
 * <p>Sample for AddressesClient:
 *
 * <pre>{@code
 * try (AddressesClient addressesClient = AddressesClient.create()) {
 *   String project = "project-309310695";
 *   for (Map.Entry<String, AddressesScopedList> element :
 *       addressesClient.aggregatedList(project).iterateAll()) {
 *     // doThingsWith(element);
 *   }
 * }
 * }</pre>
 *
 * <p>======================= RegionOperationsClient =======================
 *
 * <p>Service Description: The RegionOperations API.
 *
 * <p>Sample for RegionOperationsClient:
 *
 * <pre>{@code
 * try (RegionOperationsClient regionOperationsClient = RegionOperationsClient.create()) {
 *   String project = "project-309310695";
 *   String region = "region-934795532";
 *   String operation = "operation1662702951";
 *   Operation response = regionOperationsClient.get(project, region, operation);
 * }
 * }</pre>
 */
@Generated("by gapic-generator-java")
package com.google.cloud.compute.v1;

import javax.annotation.Generated;
