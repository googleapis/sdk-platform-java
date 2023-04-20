package com.google.showcase.v1beta1.it;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.longrunning.OperationSnapshot;
import com.google.api.gax.longrunning.OperationTimedPollAlgorithm;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.StatusCode;
import com.google.api.gax.rpc.UnaryCallSettings;
import com.google.protobuf.Timestamp;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.WaitMetadata;
import com.google.showcase.v1beta1.WaitRequest;
import com.google.showcase.v1beta1.WaitResponse;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import org.junit.Test;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;
import org.threeten.bp.temporal.ChronoUnit;

public class ITLongRunningOperation {

  @Test
  public void testGRPC_unaryCallableLRO_successfulResponse()
      throws IOException, ExecutionException, InterruptedException {
    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder
        .waitOperationSettings()
        .setInitialCallSettings(
            UnaryCallSettings.<WaitRequest, OperationSnapshot>newUnaryCallSettingsBuilder()
                .setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED)
                .setRetrySettings(
                    RetrySettings.newBuilder()
                        .setInitialRpcTimeout(Duration.ofMillis(5000L))
                        .setRpcTimeoutMultiplier(1.0)
                        .setMaxRpcTimeout(Duration.ofMillis(5000L))
                        .setTotalTimeout(Duration.ofMillis(5000L))
                        .build())
                .build())
        .setPollingAlgorithm(
            OperationTimedPollAlgorithm.create(
                RetrySettings.newBuilder()
                    .setInitialRetryDelay(Duration.ofMillis(1000L))
                    .setRetryDelayMultiplier(2.0)
                    .setMaxRetryDelay(Duration.ofMillis(5000L))
                    .setInitialRpcTimeout(Duration.ZERO)
                    .setRpcTimeoutMultiplier(1.0)
                    .setMaxRpcTimeout(Duration.ZERO)
                    .setTotalTimeout(Duration.ofMillis(15000L))
                    .build()));
    EchoSettings grpcEchoSettings = EchoSettings.create(grpcEchoSettingsBuilder.build());
    grpcEchoSettings =
        grpcEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    try (EchoClient grpcClient = EchoClient.create(grpcEchoSettings)) {
      long epochSecondsInFuture = Instant.now().plus(5, ChronoUnit.SECONDS).getEpochSecond();
      WaitRequest waitRequest =
          WaitRequest.newBuilder()
              .setSuccess(WaitResponse.newBuilder().setContent("gRPCWaitContent_5sDelay_noRetry"))
              .setEndTime(Timestamp.newBuilder().setSeconds(epochSecondsInFuture).build())
              .build();
      OperationFuture<WaitResponse, WaitMetadata> operationFuture =
          grpcClient.waitOperationCallable().futureCall(waitRequest);
      WaitResponse waitResponse = operationFuture.get();
      assertThat(waitResponse.getContent()).isEqualTo("gRPCWaitContent_5sDelay_noRetry");
      // Due to jitter, we cannot guarantee the number of attempts. Jitter affects the
      // retry/ poll delay.
      int attemptCount =
          operationFuture.getPollingFuture().getAttemptSettings().getAttemptCount() + 1;
      assertThat(attemptCount).isAtLeast(1);
    }
  }

  @Test
  public void testHttpJson_unaryCallableLRO_successfulResponse()
      throws IOException, GeneralSecurityException, ExecutionException, InterruptedException {
    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    httpJsonEchoSettingsBuilder
        .waitOperationSettings()
        .setInitialCallSettings(
            UnaryCallSettings.<WaitRequest, OperationSnapshot>newUnaryCallSettingsBuilder()
                .setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED)
                .setRetrySettings(
                    RetrySettings.newBuilder()
                        .setInitialRpcTimeout(Duration.ofMillis(5000L))
                        .setRpcTimeoutMultiplier(1.0)
                        .setMaxRpcTimeout(Duration.ofMillis(5000L))
                        .setTotalTimeout(Duration.ofMillis(5000L))
                        .build())
                .build())
        .setPollingAlgorithm(
            OperationTimedPollAlgorithm.create(
                RetrySettings.newBuilder()
                    .setInitialRetryDelay(Duration.ofMillis(1000L))
                    .setRetryDelayMultiplier(2.0)
                    .setMaxRetryDelay(Duration.ofMillis(5000L))
                    .setInitialRpcTimeout(Duration.ZERO)
                    .setRpcTimeoutMultiplier(1.0)
                    .setMaxRpcTimeout(Duration.ZERO)
                    .setTotalTimeout(Duration.ofMillis(15000L))
                    .build()));
    EchoSettings httpJsonEchoSettings = EchoSettings.create(httpJsonEchoSettingsBuilder.build());
    httpJsonEchoSettings =
        httpJsonEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    try (EchoClient httpJsonClient = EchoClient.create(httpJsonEchoSettings)) {
      long epochSecondsInFuture = Instant.now().plus(5, ChronoUnit.SECONDS).getEpochSecond();
      WaitRequest waitRequest =
          WaitRequest.newBuilder()
              .setSuccess(
                  WaitResponse.newBuilder().setContent("httpjsonWaitContent_5sDelay_noRetry"))
              .setEndTime(Timestamp.newBuilder().setSeconds(epochSecondsInFuture).build())
              .build();
      OperationFuture<WaitResponse, WaitMetadata> operationFuture =
          httpJsonClient.waitOperationCallable().futureCall(waitRequest);
      WaitResponse waitResponse = operationFuture.get();
      assertThat(waitResponse.getContent()).isEqualTo("httpjsonWaitContent_5sDelay_noRetry");
      // Due to jitter, we cannot guarantee the number of attempts. Jitter affects the
      // retry/ poll delay.
      int attemptCount =
          operationFuture.getPollingFuture().getAttemptSettings().getAttemptCount() + 1;
      assertThat(attemptCount).isAtLeast(1);
    }
  }

  @Test
  public void testGRPC_unaryCallableLRO_unsuccessfulResponse_retryPolling() throws IOException {
    EchoStubSettings.Builder grpcEchoSettingsBuilder = EchoStubSettings.newBuilder();
    grpcEchoSettingsBuilder
        .waitOperationSettings()
        .setInitialCallSettings(
            UnaryCallSettings.<WaitRequest, OperationSnapshot>newUnaryCallSettingsBuilder()
                .setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED)
                .setRetrySettings(
                    RetrySettings.newBuilder()
                        .setInitialRpcTimeout(Duration.ofMillis(5000L))
                        .setRpcTimeoutMultiplier(1.0)
                        .setMaxRpcTimeout(Duration.ofMillis(5000L))
                        .setTotalTimeout(Duration.ofMillis(5000L))
                        .build())
                .build())
        .setPollingAlgorithm(
            OperationTimedPollAlgorithm.create(
                RetrySettings.newBuilder()
                    .setInitialRetryDelay(Duration.ofMillis(1000L))
                    .setRetryDelayMultiplier(2.0)
                    .setMaxRetryDelay(Duration.ofMillis(3000L))
                    .setInitialRpcTimeout(Duration.ZERO)
                    .setRpcTimeoutMultiplier(1.0)
                    .setMaxRpcTimeout(Duration.ZERO)
                    .setTotalTimeout(Duration.ofMillis(5000L))
                    .build()));
    EchoSettings grpcEchoSettings = EchoSettings.create(grpcEchoSettingsBuilder.build());
    grpcEchoSettings =
        grpcEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    try (EchoClient grpcClient = EchoClient.create(grpcEchoSettings)) {
      long epochSecondsInFuture = Instant.now().plus(6, ChronoUnit.SECONDS).getEpochSecond();
      WaitRequest waitRequest =
          WaitRequest.newBuilder()
              .setSuccess(WaitResponse.newBuilder().setContent("gRPCWaitContent_6sDelay_noRetry"))
              .setEndTime(Timestamp.newBuilder().setSeconds(epochSecondsInFuture).build())
              .build();
      OperationFuture<WaitResponse, WaitMetadata> operationFuture =
          grpcClient.waitOperationCallable().futureCall(waitRequest);
      assertThrows(CancellationException.class, operationFuture::get);
    }
  }

  @Test
  public void testHttpJson_unaryCallableLRO_unsuccessfulResponse_retryPolling()
      throws IOException, GeneralSecurityException {
    EchoStubSettings.Builder httpJsonEchoSettingsBuilder = EchoStubSettings.newHttpJsonBuilder();
    httpJsonEchoSettingsBuilder
        .waitOperationSettings()
        .setInitialCallSettings(
            UnaryCallSettings.<WaitRequest, OperationSnapshot>newUnaryCallSettingsBuilder()
                .setRetryableCodes(StatusCode.Code.DEADLINE_EXCEEDED)
                .setRetrySettings(
                    RetrySettings.newBuilder()
                        .setInitialRpcTimeout(Duration.ofMillis(5000L))
                        .setRpcTimeoutMultiplier(1.0)
                        .setMaxRpcTimeout(Duration.ofMillis(5000L))
                        .setTotalTimeout(Duration.ofMillis(5000L))
                        .build())
                .build())
        .setPollingAlgorithm(
            OperationTimedPollAlgorithm.create(
                RetrySettings.newBuilder()
                    .setInitialRetryDelay(Duration.ofMillis(1000L))
                    .setRetryDelayMultiplier(2.0)
                    .setMaxRetryDelay(Duration.ofMillis(3000L))
                    .setInitialRpcTimeout(Duration.ZERO)
                    .setRpcTimeoutMultiplier(1.0)
                    .setMaxRpcTimeout(Duration.ZERO)
                    .setTotalTimeout(Duration.ofMillis(5000L))
                    .build()));
    EchoSettings httpJsonEchoSettings = EchoSettings.create(httpJsonEchoSettingsBuilder.build());
    httpJsonEchoSettings =
        httpJsonEchoSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                EchoSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    try (EchoClient httpJsonClient = EchoClient.create(httpJsonEchoSettings)) {
      long epochSecondsInFuture = Instant.now().plus(6, ChronoUnit.SECONDS).getEpochSecond();
      WaitRequest waitRequest =
          WaitRequest.newBuilder()
              .setSuccess(
                  WaitResponse.newBuilder().setContent("httpjsonWaitContent_6sDelay_noRetry"))
              .setEndTime(Timestamp.newBuilder().setSeconds(epochSecondsInFuture).build())
              .build();
      OperationFuture<WaitResponse, WaitMetadata> operationFuture =
          httpJsonClient.waitOperationCallable().futureCall(waitRequest);
      assertThrows(CancellationException.class, operationFuture::get);
    }
  }
}
