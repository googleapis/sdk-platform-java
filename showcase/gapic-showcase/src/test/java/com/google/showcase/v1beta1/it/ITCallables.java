package com.google.showcase.v1beta1.it;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.ServerStream;
import com.google.api.gax.rpc.StatusCode;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.ExpandRequest;
import com.google.showcase.v1beta1.PagedExpandRequest;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITCallables {
  private static EchoClient grpcClient;
  private static EchoClient httpjsonClient;

  @BeforeClass
  public static void createClient() throws IOException, GeneralSecurityException {
    EchoSettings grpcEchoSettings =
        EchoSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                InstantiatingGrpcChannelProvider.newBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    grpcClient = EchoClient.create(grpcEchoSettings);
    EchoSettings httpjsonEchoSettings =
        EchoSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    httpjsonClient = EchoClient.create(httpjsonEchoSettings);
  }

  @AfterClass
  public static void destroyClient() {
    grpcClient.close();
    httpjsonClient.close();
  }

  @Test
  public void testEchoHttpJson() {
    String content = "httpjson-echo";
    EchoResponse echoResponse =
        httpjsonClient.echo(EchoRequest.newBuilder().setContent(content).build());
    assertThat(echoResponse.getContent()).isEqualTo(content);
  }

  /*
   This tests has the server return an error back as the result.
   We use 404 NOT_FOUND Status as that has the same gRPC <-> HttpJson code mapping (showcase sever
   has a map that translates the code)
   The showcase server expects a gRPC Status Code and the result is the HttpJson's mapped value
    */
  @Test
  public void testEchoHttpJson_checkError() {
    StatusCode.Code notFoundStatusCode = StatusCode.Code.NOT_FOUND;
    try {
      httpjsonClient.echo(
          EchoRequest.newBuilder()
              .setError(Status.newBuilder().setCode(notFoundStatusCode.ordinal()).build())
              .build());
    } catch (ApiException e) {
      assertThat(e.getStatusCode().getCode()).isEqualTo(notFoundStatusCode);
    }
  }

  /*
  This tests that pagination returns the correct number of pages + responses and the content is
  correct. It tests pagination from Server-Side Streaming.
   */
  @Test
  public void testExpandHttpJson() {
    String content = "Testing the entire response is the same";
    ServerStream<EchoResponse> echoResponses =
        httpjsonClient
            .expandCallable()
            .call(ExpandRequest.newBuilder().setContent(content).build());
    int numResponses = 0;
    List<String> values = new ArrayList<>();
    for (EchoResponse echoResponse : echoResponses) {
      values.add(echoResponse.getContent());
      numResponses++;
    }

    String response = String.join(" ", values.toArray(new String[0]));
    assertThat(numResponses).isEqualTo(content.split(" ").length);
    assertThat(response).isEqualTo(content);
  }

  /*
  This tests that pagination returns the correct number of pages + responses and the content is
  correct. It tests pagination from ServerSideStreaming.

  The pageToken is where the streaming responses come back from and the page size denotes
  how many of the responses come back together. i.e for PageSize = 2 and PageToken = 1, see below:
          | A | Series  | Of  | Words | That  | Will  | Be  | Send  | Back  | One | By  | One
  Page #  | X | 1       | 1   | 2     | 2     | 3     | 3   | 4     | 4     | 5   | 5   | 6
  Token # | 0 | 1       | 2   | 3     | 4     | 5     | 6   | 7     | 8     | 9   | 10  | 11
  */
  @Test
  public void testPagedExpandWithTokenHttpJson() {
    int pageSize = 2;
    int pageToken = 1;
    String content = "A series of words that will be sent back one by one";

    EchoClient.PagedExpandPagedResponse pagedExpandPagedResponse =
        httpjsonClient.pagedExpand(
            PagedExpandRequest.newBuilder()
                .setContent(content)
                .setPageSize(pageSize)
                .setPageToken(String.valueOf(pageToken))
                .build());
    String[] words = content.split(" ");
    String[] expected = Arrays.copyOfRange(words, pageToken, words.length);
    int numResponses = 0;
    int numPages = 0;
    for (EchoClient.PagedExpandPage page : pagedExpandPagedResponse.iteratePages()) {
      for (EchoResponse echoResponse : page.getValues()) {
        assertThat(echoResponse.getContent()).isEqualTo(expected[numResponses]);
        numResponses++;
      }
      numPages++;
    }

    int contentLength = words.length;
    boolean isDivisible = ((contentLength - pageToken) % pageSize) == 0;
    // If the responses can't be evenly split into pages, then the extra responses will go to an
    // additional page
    int numExpectedPages = ((contentLength - pageToken) / pageSize) + (isDivisible ? 0 : 1);
    int numExpectedResponses = contentLength - pageToken;

    assertThat(numPages).isEqualTo(numExpectedPages);
    assertThat(numResponses).isEqualTo(numExpectedResponses);
  }
}
