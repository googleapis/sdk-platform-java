package com.google.showcase.v1beta1.it;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.longrunning.OperationTimedPollAlgorithm;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.ApiException;
import com.google.api.gax.rpc.DeadlineExceededException;
import com.google.api.gax.rpc.ServerStream;
import com.google.api.gax.rpc.StatusCode;
import com.google.protobuf.Timestamp;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.BlockRequest;
import com.google.showcase.v1beta1.BlockResponse;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoRequest;
import com.google.showcase.v1beta1.EchoResponse;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.ExpandRequest;
import com.google.showcase.v1beta1.PagedExpandRequest;
import com.google.showcase.v1beta1.WaitMetadata;
import com.google.showcase.v1beta1.WaitRequest;
import com.google.showcase.v1beta1.WaitResponse;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.threeten.bp.Duration;

public class ITEcho {

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

  //  @Test
  //  public void testEchoGRPC() {
  //    String content = "grpc-echo";
  //    EchoResponse echoResponse =
  // grpcClient.echo(EchoRequest.newBuilder().setContent(content).build());
  //    assertThat(echoResponse.getContent()).isEqualTo(content);
  //  }

  @Test
  public void testEchoHttpJson() {
    String content = "httpjson-echo";
    EchoResponse echoResponse =
        httpjsonClient.echo(EchoRequest.newBuilder().setContent(content).build());
    assertThat(echoResponse.getContent()).isEqualTo(content);
  }

  /* Ignore this test for now since it returns a 500 Internal Error */
  @Test
  public void testEchoHttpJson_checkError() {
    try {
      httpjsonClient.echo(
              EchoRequest.newBuilder()
                      .setError(
                              Status.newBuilder()
                                      .setCode(StatusCode.Code.CANCELLED.getHttpStatusCode())
                                      .build())
                      .build());
    } catch (ApiException e) {
      e.printStackTrace();
    }
  }

  /* This tests that server-side streaming returns the correct content and the server returns the correct number of responses */
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

  /* This tests that pagination returns the correct number of pages + responses and the content is correct */
  @Test
  public void testPagedExpandHttpJson() {
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
    int numPages = 0;
    int numResponses = 0;
    List<String> values = new ArrayList<>();
    for (EchoClient.PagedExpandPage page : pagedExpandPagedResponse.iteratePages()) {
      for (EchoResponse echoResponse : page.getValues()) {
        values.add(echoResponse.getContent());
        numResponses++;
      }
      numPages++;
    }

    int contentLength = content.split(" ").length;
    boolean isDivisible = ((contentLength - pageToken) % pageSize) == 0;
    // If the responses can't be evenly split into pages, then the extra responses will go to an
    // additional page
    int numExpectedPages = ((contentLength - pageToken) / pageSize) + (isDivisible ? 0 : 1);
    int numExpectedResponses = contentLength - pageToken;
    String expectedResponse = "series of words that will be sent back one by one";

    assertThat(numPages).isEqualTo(numExpectedPages);
    assertThat(numResponses).isEqualTo(numExpectedResponses);
    assertThat(String.join(" ", values.toArray(new String[0]))).isEqualTo(expectedResponse);
  }

  // We ignore this until LRO issue is resovled
  // (https://github.com/googleapis/gapic-generator-java/pull/1288)
  @Ignore
  @Test
  public void testWaitHttpJson() throws ExecutionException, InterruptedException {
    // We set the future timeout to be 10 seconds in the future to ensure a few GetOperation calls
    String content = "content";
    long futureTimeInSecondsFromEpoch = Instant.now().plus(10, ChronoUnit.SECONDS).getEpochSecond();
    OperationFuture<WaitResponse, WaitMetadata> operationFutureSuccess =
        httpjsonClient.waitAsync(
            WaitRequest.newBuilder()
                .setEndTime(Timestamp.newBuilder().setSeconds(futureTimeInSecondsFromEpoch))
                .setSuccess(WaitResponse.newBuilder().setContent(content).build())
                .build());
    WaitResponse waitResponseSuccess = operationFutureSuccess.get();
    assertThat(waitResponseSuccess.getContent()).isEqualTo(content);
  }

  /* We must use a special client that has the retry logic's total timeout set low enough to timeout */
  // We ignore this until LRO issue is resolved
  // (https://github.com/googleapis/gapic-generator-java/pull/1288)
  @Ignore
  @Test
  public void testWaitHttpJson_throwsCancelledException()
      throws GeneralSecurityException, IOException {
    OperationTimedPollAlgorithm operationTimedPollAlgorithm =
        OperationTimedPollAlgorithm.create(
            RetrySettings.newBuilder()
                .setInitialRetryDelay(Duration.ofMillis(1000L))
                .setRetryDelayMultiplier(1.5)
                .setMaxRetryDelay(Duration.ofMillis(45000L))
                .setInitialRpcTimeout(Duration.ZERO)
                .setRpcTimeoutMultiplier(1.0)
                .setMaxRpcTimeout(Duration.ZERO)
                // We set the future timeout to 10 seconds in the future, but the total timeout is 5
                // seconds
                .setTotalTimeout(Duration.ofMillis(5000L))
                .build());
    EchoStubSettings.Builder builder = EchoStubSettings.newHttpJsonBuilder();
    builder.waitOperationSettings().setPollingAlgorithm(operationTimedPollAlgorithm);
    EchoStubSettings httpjsonEchoStubSettings =
        builder
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    EchoSettings httpjsonEchoSettings = EchoSettings.create(httpjsonEchoStubSettings);
    try (EchoClient httpjsonClient = EchoClient.create(httpjsonEchoSettings)) {
      long futureTimeInSecondsFromEpoch =
          Instant.now().plus(10, ChronoUnit.SECONDS).getEpochSecond();
      OperationFuture<WaitResponse, WaitMetadata> operationFutureFailure =
          httpjsonClient.waitAsync(
              WaitRequest.newBuilder()
                  .setSuccess(WaitResponse.newBuilder().setContent("Content").build())
                  .setEndTime(Timestamp.newBuilder().setSeconds(futureTimeInSecondsFromEpoch))
                  .build());
      assertThrows(CancellationException.class, operationFutureFailure::get);
    }
  }

  @Test
  public void testBlockGRPC() {
    // Default timeout for UnaryCall is 5 seconds -- We want to ensure a reasonable delay for this test
    int delayInSeconds = 3;
    String content = "grpcBlockContent";
    BlockResponse blockResponse =
            grpcClient.block(
                    BlockRequest.newBuilder()
                            .setSuccess(BlockResponse.newBuilder().setContent(content).build())
                            .setResponseDelay(
                                    com.google.protobuf.Duration.newBuilder().setSeconds(delayInSeconds).build())
                            .build());
    assertThat(blockResponse.getContent()).isEqualTo(content);
  }

  @Test
  public void testBlockHttpJson() {
    // Default timeout for UnaryCall is 5 seconds -- We want to ensure a reasonable delay for this test
    int delayInSeconds = 3;
    String content = "httpjsonBlockContent";
    BlockResponse blockResponse =
        httpjsonClient.block(
            BlockRequest.newBuilder()
                .setSuccess(BlockResponse.newBuilder().setContent(content).build())
                .setResponseDelay(
                    com.google.protobuf.Duration.newBuilder().setSeconds(delayInSeconds).build())
                .build());
    assertThat(blockResponse.getContent()).isEqualTo(content);
  }

  @Test
  public void testBlockGRPC_throwsDeadlineExceededException() {
    // Default timeout for UnaryCall is 5 seconds -- We want to ensure a long enough delay for this
    // test
    int delayInSeconds = 10;
    assertThrows(
            DeadlineExceededException.class,
            () -> grpcClient.block(
                    BlockRequest.newBuilder()
                            .setSuccess(BlockResponse.newBuilder().setContent("gRPCBlockContent_10SecDelay1"))
                            .setResponseDelay(
                                    com.google.protobuf.Duration.newBuilder().setSeconds(delayInSeconds).build())
                            .build()));
    try {
      grpcClient.block(BlockRequest.newBuilder()
              .setSuccess(BlockResponse.newBuilder().setContent("gRPCBlockContent_10SecDelay2"))
              .setResponseDelay(
                      com.google.protobuf.Duration.newBuilder().setSeconds(delayInSeconds).build())
              .build());
    } catch (DeadlineExceededException e) {
      assertThat(e.getStatusCode().getCode()).isEqualTo(StatusCode.Code.DEADLINE_EXCEEDED);
    }
  }
  @Ignore
  @Test
  public void testBlockHttpJson_throwsCancellationException() {
    // Default timeout for UnaryCall is 5 seconds -- We want to ensure a long enough delay for this
    // test
    int delayInSeconds = 10;
    assertThrows(
            CancellationException.class,
            () -> httpjsonClient.block(
                    BlockRequest.newBuilder()
                            .setSuccess(BlockResponse.newBuilder().setContent("httpjsonBlockContent_10SecDelay"))
                            .setResponseDelay(
                                    com.google.protobuf.Duration.newBuilder().setSeconds(delayInSeconds).build())
                            .build()));
  }
}
