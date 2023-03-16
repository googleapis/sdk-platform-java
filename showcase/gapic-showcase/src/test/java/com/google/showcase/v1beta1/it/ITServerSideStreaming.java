package com.google.showcase.v1beta1.it;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

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
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
  public void testGrpc_receiveStreamedContent() {
    String content = "The rain in Spain stays mainly on the plain!";
    ServerStream<EchoResponse> responseStream =
        grpcClient.expandCallable().call(ExpandRequest.newBuilder().setContent(content).build());
    ArrayList<String> responses = new ArrayList<>();
    for (EchoResponse response : responseStream) {
      responses.add(response.getContent());
    }

    assertThat(responses)
        .containsExactlyElementsIn(
            ImmutableList.of(
                "The", "rain", "in", "Spain", "stays", "mainly", "on", "the", "plain!"));
  }

  @Test
  public void testGrpc_serverError_receiveErrorAfterLastWordInStream() {
    String content = "The rain in Spain";
    Status cancelledStatus =
        Status.newBuilder().setCode(StatusCode.Code.CANCELLED.ordinal()).build();
    ServerStream<EchoResponse> responseStream =
        grpcClient
            .expandCallable()
            .call(ExpandRequest.newBuilder().setContent(content).setError(cancelledStatus).build());
    Iterator<EchoResponse> echoResponseIterator = responseStream.iterator();
    assertThat(echoResponseIterator.next().getContent()).isEqualTo("The");
    assertThat(echoResponseIterator.next().getContent()).isEqualTo("rain");
    assertThat(echoResponseIterator.next().getContent()).isEqualTo("in");
    assertThat(echoResponseIterator.next().getContent()).isEqualTo("Spain");
    CancelledException cancelledException =
        assertThrows(CancelledException.class, echoResponseIterator::next);
    assertThat(cancelledException.getStatusCode().getCode()).isEqualTo(StatusCode.Code.CANCELLED);
  }
}
