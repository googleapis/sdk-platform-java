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

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.Location;
import com.google.common.collect.ImmutableList;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoSettings;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class ITCommonServiceMixins {

  private EchoClient grpcClient;

  @Before
  public void createClients() throws IOException {
    // Create gRPC Echo Client
    EchoSettings grpcEchoSettings =
        EchoSettings.newBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    grpcClient = EchoClient.create(grpcEchoSettings);
  }

  @Test
  public void testGrpc_getLocation() {
    GetLocationRequest request =
        GetLocationRequest.newBuilder().setName("projects/showcase/location/us-central1").build();
    Location location = grpcClient.getLocation(request);
    assertThat(location)
        .isEqualTo(
            Location.newBuilder()
                .setName("projects/showcase/location/us-central1")
                .setDisplayName("us-central1")
                .build());
  }

  @Test
  public void testGrpc_listLocations() {
    ListLocationsRequest request =
        ListLocationsRequest.newBuilder().setName("projects/showcase").build();
    EchoClient.ListLocationsPagedResponse locationsPagedResponse =
        grpcClient.listLocations(request);

    ArrayList<Location> actualLocations = new ArrayList<>();
    for (Location location : locationsPagedResponse.iterateAll()) {
      actualLocations.add(location);
    }
    Location locationUsNorth =
        Location.newBuilder()
            .setName("projects/showcase/locations/us-north")
            .setDisplayName("us-north")
            .build();
    Location locationUsSouth =
        Location.newBuilder()
            .setName("projects/showcase/locations/us-south")
            .setDisplayName("us-south")
            .build();
    Location locationUsEast =
        Location.newBuilder()
            .setName("projects/showcase/locations/us-east")
            .setDisplayName("us-east")
            .build();
    Location locationUsWest =
        Location.newBuilder()
            .setName("projects/showcase/locations/us-west")
            .setDisplayName("us-west")
            .build();

    assertThat(actualLocations)
        .containsExactlyElementsIn(
            ImmutableList.of(locationUsNorth, locationUsSouth, locationUsEast, locationUsWest))
        .inOrder();
  }
}
