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

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.common.collect.ImmutableList;
import com.google.rpc.Status;
import com.google.showcase.v1beta1.CreateSequenceRequest;
import com.google.showcase.v1beta1.Sequence;
import com.google.showcase.v1beta1.SequenceReport;
import com.google.showcase.v1beta1.SequenceServiceClient;
import com.google.showcase.v1beta1.SequenceServiceSettings;
import com.google.showcase.v1beta1.stub.SequenceServiceStubSettings;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.threeten.bp.Duration;
import org.threeten.bp.Instant;

public class ITRetry {
  @Test
  public void testGRPC_sequence() throws IOException {
    SequenceServiceSettings grpcSequenceSettings =
        SequenceServiceSettings.newBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                SequenceServiceStubSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    try (SequenceServiceClient grpcClient = SequenceServiceClient.create(grpcSequenceSettings)) {
      Sequence sequence = grpcClient.createSequence(CreateSequenceRequest.newBuilder().build());
      Instant now = Instant.now();
      grpcClient.attemptSequence(sequence.getName());
      String sequenceReportName = sequence.getName() + "/sequenceReport";
      SequenceReport sequenceReport = grpcClient.getSequenceReport(sequenceReportName);
      List<SequenceReport.Attempt> attemptList = sequenceReport.getAttemptsList();
      assertThat(attemptList.size()).isEqualTo(1);
      SequenceReport.Attempt attempt = attemptList.get(0);
      Instant attemptDeadlineDuration =
          Instant.ofEpochSecond(
              attempt.getAttemptDeadline().getSeconds(), attempt.getAttemptDeadline().getNanos());
      Duration duration = Duration.between(now, attemptDeadlineDuration);
      assertThat(duration.minus(Duration.ofSeconds(10))).isLessThan(Duration.ofMillis(10));
    }
  }

  @Test
  public void testHttpJson_sequence() throws IOException, GeneralSecurityException {
    SequenceServiceSettings httpJsonSequenceSettings =
        SequenceServiceSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                SequenceServiceSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    try (SequenceServiceClient httpJsonClient =
        SequenceServiceClient.create(httpJsonSequenceSettings)) {
      Sequence sequence = httpJsonClient.createSequence(CreateSequenceRequest.newBuilder().build());
      Instant now = Instant.now();
      httpJsonClient.attemptSequence(sequence.getName());
      String sequenceReportName = sequence.getName() + "/sequenceReport";
      SequenceReport sequenceReport = httpJsonClient.getSequenceReport(sequenceReportName);
      List<SequenceReport.Attempt> attemptList = sequenceReport.getAttemptsList();
      assertThat(attemptList.size()).isEqualTo(1);
      SequenceReport.Attempt attempt = attemptList.get(0);
      Instant attemptDeadlineDuration =
          Instant.ofEpochSecond(
              attempt.getAttemptDeadline().getSeconds(), attempt.getAttemptDeadline().getNanos());
      Duration duration = Duration.between(now, attemptDeadlineDuration);
      assertThat(duration.minus(Duration.ofSeconds(10))).isLessThan(Duration.ofMillis(10));
    }
  }

  @Test
  public void testGRPC_customTimeout() throws IOException {
    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(2000L))
            .setTotalTimeout(Duration.ofMillis(2000L))
            .build();
    SequenceServiceStubSettings.Builder gRPCSequenceServiceStubSettings =
        SequenceServiceStubSettings.newBuilder();
    gRPCSequenceServiceStubSettings
        .attemptSequenceSettings()
        .setRetrySettings(defaultRetrySettings);
    SequenceServiceSettings grpcSequenceServiceSettings =
        SequenceServiceSettings.create(gRPCSequenceServiceStubSettings.build());
    grpcSequenceServiceSettings =
        grpcSequenceServiceSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                SequenceServiceStubSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    try (SequenceServiceClient grpcClient =
        SequenceServiceClient.create(grpcSequenceServiceSettings)) {
      Sequence sequence = grpcClient.createSequence(CreateSequenceRequest.newBuilder().build());
      Instant now = Instant.now();
      grpcClient.attemptSequence(sequence.getName());
      String sequenceReportName = sequence.getName() + "/sequenceReport";
      SequenceReport sequenceReport = grpcClient.getSequenceReport(sequenceReportName);
      List<SequenceReport.Attempt> attemptList = sequenceReport.getAttemptsList();
      assertThat(attemptList.size()).isEqualTo(1);
      SequenceReport.Attempt attempt = attemptList.get(0);
      Instant attemptDeadlineDuration =
          Instant.ofEpochSecond(
              attempt.getAttemptDeadline().getSeconds(), attempt.getAttemptDeadline().getNanos());
      Duration duration = Duration.between(now, attemptDeadlineDuration);
      assertThat(duration.minus(Duration.ofSeconds(2))).isLessThan(Duration.ofMillis(10));
    }
  }

  @Test
  public void testHttpJson_customTimeout() throws IOException, GeneralSecurityException {
    RetrySettings defaultRetrySettings =
        RetrySettings.newBuilder()
            .setRpcTimeoutMultiplier(1.0)
            .setMaxRpcTimeout(Duration.ofMillis(2000L))
            .setTotalTimeout(Duration.ofMillis(2000L))
            .build();
    SequenceServiceStubSettings.Builder httpJsonSequenceServiceStubSettings =
        SequenceServiceStubSettings.newHttpJsonBuilder();
    httpJsonSequenceServiceStubSettings
        .attemptSequenceSettings()
        .setRetrySettings(defaultRetrySettings);
    SequenceServiceSettings httpJsonSequenceSettings =
        SequenceServiceSettings.create(httpJsonSequenceServiceStubSettings.build());
    httpJsonSequenceSettings =
        httpJsonSequenceSettings
            .toBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                SequenceServiceSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    try (SequenceServiceClient httpJsonClient =
        SequenceServiceClient.create(httpJsonSequenceSettings)) {
      Sequence sequence = httpJsonClient.createSequence(CreateSequenceRequest.newBuilder().build());
      Instant now = Instant.now();
      httpJsonClient.attemptSequence(sequence.getName());
      String sequenceReportName = sequence.getName() + "/sequenceReport";
      SequenceReport sequenceReport = httpJsonClient.getSequenceReport(sequenceReportName);
      List<SequenceReport.Attempt> attemptList = sequenceReport.getAttemptsList();
      assertThat(attemptList.size()).isEqualTo(1);
      SequenceReport.Attempt attempt = attemptList.get(0);
      Instant attemptDeadlineDuration =
          Instant.ofEpochSecond(
              attempt.getAttemptDeadline().getSeconds(), attempt.getAttemptDeadline().getNanos());
      Duration duration = Duration.between(now, attemptDeadlineDuration);
      assertThat(duration.minus(Duration.ofSeconds(2))).isLessThan(Duration.ofMillis(10));
    }
  }

  @Test
  public void testGRPC_retrySequence() throws IOException {
    List<Sequence.Response> response =
        ImmutableList.of(
            Sequence.Response.newBuilder()
                .setDelay(com.google.protobuf.Duration.newBuilder().setNanos(100000000))
                .setStatus(Status.newBuilder().setCode(io.grpc.Status.Code.UNKNOWN.value()))
                .build(),
            Sequence.Response.newBuilder()
                .setDelay(com.google.protobuf.Duration.newBuilder().setNanos(100000000))
                .setStatus(Status.newBuilder().setCode(io.grpc.Status.Code.UNKNOWN.value()))
                .build(),
            Sequence.Response.newBuilder()
                .setDelay(com.google.protobuf.Duration.newBuilder().setNanos(100000000))
                .setStatus(Status.newBuilder().setCode(io.grpc.Status.Code.UNKNOWN.value()))
                .build(),
            Sequence.Response.newBuilder()
                .setDelay(com.google.protobuf.Duration.newBuilder().setNanos(100000000))
                .setStatus(Status.newBuilder().setCode(io.grpc.Status.Code.UNKNOWN.value()))
                .build(),
            Sequence.Response.newBuilder()
                .setDelay(com.google.protobuf.Duration.newBuilder().setNanos(100000000))
                .setStatus(Status.newBuilder().setCode(io.grpc.Status.Code.OK.value()))
                .build());
    SequenceServiceSettings grpcSequenceSettings =
        SequenceServiceSettings.newBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                SequenceServiceStubSettings.defaultGrpcTransportProviderBuilder()
                    .setChannelConfigurator(ManagedChannelBuilder::usePlaintext)
                    .build())
            .build();
    try (SequenceServiceClient grpcClient = SequenceServiceClient.create(grpcSequenceSettings)) {
      Sequence sequence =
          grpcClient.createSequence(
              CreateSequenceRequest.newBuilder()
                  .setSequence(Sequence.newBuilder().addAllResponses(response))
                  .build());
      grpcClient.attemptSequence(sequence.getName());
      String sequenceReportName = sequence.getName() + "/sequenceReport";
      SequenceReport sequenceReport = grpcClient.getSequenceReport(sequenceReportName);
      List<SequenceReport.Attempt> attemptList = sequenceReport.getAttemptsList();
      assertThat(attemptList.size()).isEqualTo(response.size());
      int attemptNumber = 0;
      for (SequenceReport.Attempt attempt : attemptList) {
        assertThat(attempt.getStatus()).isEqualTo(response.get(attemptNumber).getStatus());
        if (attemptNumber > 0) {
          Instant currentAttemptInstant =
              Instant.ofEpochSecond(
                  attempt.getAttemptDeadline().getSeconds(),
                  attempt.getAttemptDeadline().getNanos());
          SequenceReport.Attempt previousAttempt = attemptList.get(attemptNumber - 1);
          Instant previousAttemptInstant =
              Instant.ofEpochSecond(
                  previousAttempt.getAttemptDeadline().getSeconds(),
                  previousAttempt.getAttemptDeadline().getNanos());
          Duration duration = Duration.between(previousAttemptInstant, currentAttemptInstant);
          assertThat(duration).isLessThan(Duration.ofMillis(10));
        }
        attemptNumber++;
      }
    }
  }

  @Ignore
  @Test
  public void testHttpJson_retrySequence() throws IOException, GeneralSecurityException {
    List<Sequence.Response> response =
        ImmutableList.of(
            Sequence.Response.newBuilder()
                .setDelay(com.google.protobuf.Duration.newBuilder().setNanos(100000000))
                .setStatus(Status.newBuilder().setCode(io.grpc.Status.Code.UNKNOWN.value()))
                .build(),
            Sequence.Response.newBuilder()
                .setDelay(com.google.protobuf.Duration.newBuilder().setNanos(100000000))
                .setStatus(Status.newBuilder().setCode(io.grpc.Status.Code.UNKNOWN.value()))
                .build(),
            Sequence.Response.newBuilder()
                .setDelay(com.google.protobuf.Duration.newBuilder().setNanos(100000000))
                .setStatus(Status.newBuilder().setCode(io.grpc.Status.Code.UNKNOWN.value()))
                .build(),
            Sequence.Response.newBuilder()
                .setDelay(com.google.protobuf.Duration.newBuilder().setNanos(100000000))
                .setStatus(Status.newBuilder().setCode(io.grpc.Status.Code.UNKNOWN.value()))
                .build(),
            Sequence.Response.newBuilder()
                .setDelay(com.google.protobuf.Duration.newBuilder().setNanos(100000000))
                .setStatus(Status.newBuilder().setCode(io.grpc.Status.Code.OK.value()))
                .build());
    SequenceServiceSettings httpJsonSequenceSettings =
        SequenceServiceSettings.newHttpJsonBuilder()
            .setCredentialsProvider(NoCredentialsProvider.create())
            .setTransportChannelProvider(
                SequenceServiceSettings.defaultHttpJsonTransportProviderBuilder()
                    .setHttpTransport(
                        new NetHttpTransport.Builder().doNotValidateCertificate().build())
                    .setEndpoint("http://localhost:7469")
                    .build())
            .build();
    try (SequenceServiceClient httpJsonClient =
        SequenceServiceClient.create(httpJsonSequenceSettings)) {
      Sequence sequence =
          httpJsonClient.createSequence(
              CreateSequenceRequest.newBuilder()
                  .setSequence(Sequence.newBuilder().addAllResponses(response))
                  .build());
      httpJsonClient.attemptSequence(sequence.getName());
      String sequenceReportName = sequence.getName() + "/sequenceReport";
      SequenceReport sequenceReport = httpJsonClient.getSequenceReport(sequenceReportName);
      List<SequenceReport.Attempt> attemptList = sequenceReport.getAttemptsList();
      assertThat(attemptList.size()).isEqualTo(response.size());
      int attemptNumber = 0;
      for (SequenceReport.Attempt attempt : attemptList) {
        assertThat(attempt.getStatus()).isEqualTo(response.get(attemptNumber).getStatus());
        if (attemptNumber > 0) {
          Instant currentAttemptInstant =
              Instant.ofEpochSecond(
                  attempt.getAttemptDeadline().getSeconds(),
                  attempt.getAttemptDeadline().getNanos());
          SequenceReport.Attempt previousAttempt = attemptList.get(attemptNumber - 1);
          Instant previousAttemptInstant =
              Instant.ofEpochSecond(
                  previousAttempt.getAttemptDeadline().getSeconds(),
                  previousAttempt.getAttemptDeadline().getNanos());
          Duration duration = Duration.between(previousAttemptInstant, currentAttemptInstant);
          assertThat(duration).isLessThan(Duration.ofMillis(10));
        }
        attemptNumber++;
      }
    }
  }
}
