package com.google.showcase.v1beta1.it;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.rpc.CancelledException;
import com.google.api.gax.rpc.ServerStream;
import com.google.api.gax.rpc.StatusCode;
import com.google.common.collect.ImmutableList;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.*;
import io.grpc.ManagedChannelBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

public class ITServerSideStreaming {

    private static EchoClient grpcClient;

    private static EchoClient httpJsonClient;

    @Before
    public void createClients() throws IOException, GeneralSecurityException {
        // Create gRPC Echo Client
        EchoSettings grpcEchoSettings =
                EchoSettings.newBuilder()
                        .setCredentialsProvider(NoCredentialsProvider.create())
                        .setTransportChannelProvider(
                                InstantiatingGrpcChannelProvider.newBuilder()
                                        .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                                        .build())
                        .build();
        grpcClient = EchoClient.create(grpcEchoSettings);

        // Create Http JSON Echo Client
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
        httpJsonClient = EchoClient.create(httpJsonEchoSettings);
    }

    @After
    public void destroyClient() {
        grpcClient.close();
        httpJsonClient.close();
    }

    @Test
    public void testGrpc_consumesAllResponses() {
        String content = "The early bird catches the worm";
        ServerStream<EchoResponse> responseStream = grpcClient.expandCallable().call(ExpandRequest.newBuilder().setContent(content).build());
        ArrayList<String> responses = new ArrayList<>();
        for (EchoResponse response : responseStream) {
            responses.add(response.getContent());
        }

        assertThat(responses).containsExactlyElementsIn(ImmutableList.of("The", "early", "bird", "catches", "the", "worm"));
    }

    @Test
    public void testGrpc_errorHandling() {
        String content = "The early bird catches the worm";
        Status cancelledStatus = Status.newBuilder().setCode(StatusCode.Code.CANCELLED.ordinal()).build();
        ServerStream<EchoResponse> responseStream = grpcClient.expandCallable().call(
                ExpandRequest.newBuilder().setContent(content).setError(cancelledStatus).build());
    }

}
