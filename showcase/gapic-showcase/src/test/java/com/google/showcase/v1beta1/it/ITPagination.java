package com.google.showcase.v1beta1.it;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.gax.rpc.ServerStream;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.ExpandRequest;
import com.google.showcase.v1beta1.PagedExpandRequest;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITPagination {

  private static EchoClient grpcClient;
  private static EchoClient httpjsonClient;

  @BeforeClass
  public static void createClients() throws Exception {
    // Create gRPC Echo Client
    grpcClient = TestClientInitializer.createGrpcEchoClient();
    // Create Http JSON Echo Client
    httpjsonClient = TestClientInitializer.createHttpJsonEchoClient();
  }

  @AfterClass
  public static void destroyClients() throws InterruptedException {
    grpcClient.close();
    grpcClient.awaitTermination(TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
    httpjsonClient.close();
    httpjsonClient.awaitTermination(
        TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
  }

  // This tests that pagination returns the correct number of pages + responses and the content is
  // correct. It tests pagination from Server-Side Streaming.
  @Test
  public void testExpandGrpc() {
    String content = "Testing the entire response is the same";
    int expected = content.split(" ").length;
    ServerStream<EchoResponse> echoResponses =
        grpcClient.expandCallable().call(ExpandRequest.newBuilder().setContent(content).build());
    List<String> values = new ArrayList<>();
    for (EchoResponse echoResponse : echoResponses) {
      values.add(echoResponse.getContent());
    }

    String response = String.join(" ", values);
    assertThat(values.size()).isEqualTo(expected);
    assertThat(response).isEqualTo(content);
  }

  // This tests that pagination returns the correct number of pages + responses and the content is
  // correct. It tests pagination from Server-Side Streaming.
  @Test
  public void testExpandHttpJson() {
    String content = "Testing the entire response is the same";
    int expected = content.split(" ").length;
    ServerStream<EchoResponse> echoResponses =
        httpjsonClient
            .expandCallable()
            .call(ExpandRequest.newBuilder().setContent(content).build());
    List<String> values = new ArrayList<>();
    for (EchoResponse echoResponse : echoResponses) {
      values.add(echoResponse.getContent());
    }

    String response = String.join(" ", values);
    assertThat(values.size()).isEqualTo(expected);
    assertThat(response).isEqualTo(content);
  }

  // This tests that pagination returns the correct number of pages + responses and the content is
  // correct. It tests pagination from ServerSideStreaming.
  //
  // The pageToken is where the streaming responses come back from and the page size denotes
  // how many of the responses come back together. i.e for PageSize = 2 and PageToken = 1, see
  // below:
  //         | A | Series  | Of  | Words | That  | Will  | Be  | Sent  | Back  | One | By  | One
  // Page #  | X | 1       | 1   | 2     | 2     | 3     | 3   | 4     | 4     | 5   | 5   | 6
  // Token # | 0 | 1       | 2   | 3     | 4     | 5     | 6   | 7     | 8     | 9   | 10  | 11
  @Test
  public void testPagedExpandWithTokenGrpc() {
    int pageSize = 2;
    int pageToken = 1;
    String content = "A series of words that will be sent back one by one";
    int contentLength = content.split(" ").length;

    EchoClient.PagedExpandPagedResponse pagedExpandPagedResponse =
        grpcClient.pagedExpand(
            PagedExpandRequest.newBuilder()
                .setContent(content)
                .setPageSize(pageSize)
                .setPageToken(String.valueOf(pageToken))
                .build());
    String[] expected = content.split(" ");
    int numResponses = 0;
    int numPages = 0;
    for (EchoClient.PagedExpandPage page : pagedExpandPagedResponse.iteratePages()) {
      for (EchoResponse echoResponse : page.getValues()) {
        // Add pageToken as offset to the expected array to start indexing at the pageToken
        assertThat(echoResponse.getContent()).isEqualTo(expected[numResponses + pageToken]);
        numResponses++;
      }
      numPages++;
    }

    int numExpectedPages = ((contentLength - pageToken) / pageSize);
    // If the responses can't be evenly split into pages, then the extra responses
    // will go to an additional page
    if ((contentLength - pageToken) % pageSize != 0) {
      numExpectedPages++;
    }
    int numExpectedResponses = contentLength - pageToken;

    assertThat(numPages).isEqualTo(numExpectedPages);
    assertThat(numResponses).isEqualTo(numExpectedResponses);
  }

  // This tests that pagination returns the correct number of pages + responses and the content is
  // correct. It tests pagination from ServerSideStreaming.
  //
  // The pageToken is where the streaming responses come back from and the page size denotes
  // how many of the responses come back together. i.e for PageSize = 2 and PageToken = 1, see
  // below:
  //         | A | Series  | Of  | Words | That  | Will  | Be  | Sent  | Back  | One | By  | One
  // Page #  | X | 1       | 1   | 2     | 2     | 3     | 3   | 4     | 4     | 5   | 5   | 6
  // Token # | 0 | 1       | 2   | 3     | 4     | 5     | 6   | 7     | 8     | 9   | 10  | 11
  @Test
  public void testPagedExpandWithTokenHttpJson() {
    int pageSize = 2;
    int pageToken = 1;
    String content = "A series of words that will be sent back one by one";
    int contentLength = content.split(" ").length;

    EchoClient.PagedExpandPagedResponse pagedExpandPagedResponse =
        httpjsonClient.pagedExpand(
            PagedExpandRequest.newBuilder()
                .setContent(content)
                .setPageSize(pageSize)
                .setPageToken(String.valueOf(pageToken))
                .build());
    String[] expected = content.split(" ");
    int numResponses = 0;
    int numPages = 0;
    for (EchoClient.PagedExpandPage page : pagedExpandPagedResponse.iteratePages()) {
      for (EchoResponse echoResponse : page.getValues()) {
        // Add pageToken as offset to the expected array to start indexing at the pageToken
        assertThat(echoResponse.getContent()).isEqualTo(expected[numResponses + pageToken]);
        numResponses++;
      }
      numPages++;
    }

    int numExpectedPages = ((contentLength - pageToken) / pageSize);
    // If the responses can't be evenly split into pages, then the extra responses
    // will go to an additional page
    if ((contentLength - pageToken) % pageSize != 0) {
      numExpectedPages++;
    }
    int numExpectedResponses = contentLength - pageToken;

    assertThat(numPages).isEqualTo(numExpectedPages);
    assertThat(numResponses).isEqualTo(numExpectedResponses);
  }
}
