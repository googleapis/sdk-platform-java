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

package com.google.showcase.v1beta1.it.util;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoSettings;
import io.grpc.ManagedChannelBuilder;

public class TestClientInitializer {

    public static EchoClient createGrpcEchoClient() throws Exception {
        EchoSettings grpcEchoSettings =
                EchoSettings.newBuilder()
                        .setCredentialsProvider(NoCredentialsProvider.create())
                        .setTransportChannelProvider(
                                EchoSettings.defaultGrpcTransportProviderBuilder()
                                        .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                                        .build())
                        .build();
        return EchoClient.create(grpcEchoSettings);
    }

    public static EchoClient createHttpJsonEchoClient() throws Exception{
        EchoSettings httpJsonEchoSettings =
                EchoSettings.newHttpJsonBuilder()
                        .setCredentialsProvider(NoCredentialsProvider.create())
                        .setTransportChannelProvider(
                                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                                        .setHttpTransport(
                                                new NetHttpTransport.Builder().doNotValidateCertificate().build())
                                        .setEndpoint("http://localhost:7469")
                                        .build())
                        .build();
        return EchoClient.create(httpJsonEchoSettings);
    }
}
