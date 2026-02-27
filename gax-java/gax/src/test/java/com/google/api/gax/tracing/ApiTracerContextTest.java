/*
 * Copyright 2026 Google LLC
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google LLC nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.google.api.gax.tracing;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.gax.rpc.LibraryMetadata;
import com.google.common.collect.ImmutableMap;
import io.grpc.MethodDescriptor;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ApiTracerContextTest {

  @Test
  void testGetAttemptAttributes_serverAddress() {
    ApiTracerContext context =
        ApiTracerContext.newBuilder()
            .setLibraryMetadata(LibraryMetadata.empty())
            .setServerAddress("test-address")
            .build();
    Map<String, String> attributes = context.getAttemptAttributes();

    assertThat(attributes)
        .containsEntry(ObservabilityAttributes.SERVER_ADDRESS_ATTRIBUTE, "test-address");
  }

  @Test
  void testGetAttemptAttributes_repo() {
    LibraryMetadata libraryMetadata =
        LibraryMetadata.newBuilder().setRepository("test-repo").build();
    ApiTracerContext context =
        ApiTracerContext.newBuilder().setLibraryMetadata(libraryMetadata).build();
    Map<String, String> attributes = context.getAttemptAttributes();

    assertThat(attributes).containsEntry(ObservabilityAttributes.REPO_ATTRIBUTE, "test-repo");
  }

  @Test
  void testGetAttemptAttributes_artifact() {
    LibraryMetadata libraryMetadata =
        LibraryMetadata.newBuilder().setArtifactName("test-artifact").build();
    ApiTracerContext context =
        ApiTracerContext.newBuilder().setLibraryMetadata(libraryMetadata).build();
    Map<String, String> attributes = context.getAttemptAttributes();

    assertThat(attributes)
        .containsEntry(ObservabilityAttributes.ARTIFACT_ATTRIBUTE, "test-artifact");
  }

  @Test
  void testGetAttemptAttributes_empty() {
    ApiTracerContext context = ApiTracerContext.empty();
    Map<String, String> attributes = context.getAttemptAttributes();

    assertThat(attributes).isEmpty();
  }

  @Test
  void testGetSpanNamePartsGrpc() {
    @SuppressWarnings("unchecked")
    MethodDescriptor<?, ?> descriptor =
        MethodDescriptor.newBuilder()
            .setType(MethodDescriptor.MethodType.SERVER_STREAMING)
            .setFullMethodName("google.bigtable.v2.Bigtable/ReadRows")
            .setRequestMarshaller(Mockito.mock(MethodDescriptor.Marshaller.class))
            .setResponseMarshaller(Mockito.mock(MethodDescriptor.Marshaller.class))
            .build();

    ApiTracerContext context =
        ApiTracerContext.newBuilder()
            .setLibraryMetadata(LibraryMetadata.empty())
            .setRpcMethod(descriptor.getFullMethodName())
            .setTransport(ApiTracerContext.Transport.GRPC)
            .build();
    assertThat(context.getClientName()).isEqualTo("Bigtable");
    assertThat(context.getMethodName()).isEqualTo("ReadRows");
  }

  @Test
  void testGetSpanNamePartsGrpc_withSuffix() {
    @SuppressWarnings("unchecked")
    MethodDescriptor<?, ?> descriptor =
        MethodDescriptor.newBuilder()
            .setType(MethodDescriptor.MethodType.SERVER_STREAMING)
            .setFullMethodName("google.bigtable.v2.Bigtable/ReadRows")
            .setRequestMarshaller(Mockito.mock(MethodDescriptor.Marshaller.class))
            .setResponseMarshaller(Mockito.mock(MethodDescriptor.Marshaller.class))
            .build();

    ApiTracerContext context =
        ApiTracerContext.newBuilder()
            .setLibraryMetadata(LibraryMetadata.empty())
            .setRpcMethod(descriptor.getFullMethodName())
            .setTransport(ApiTracerContext.Transport.GRPC)
            .setMethodNameSuffix("Operation")
            .build();
    assertThat(context.getClientName()).isEqualTo("Bigtable");
    assertThat(context.getMethodName()).isEqualTo("ReadRowsOperation");
  }

  @Test
  void testGetSpanNamePartsUnqualifiedGrpc() {
    @SuppressWarnings("unchecked")
    MethodDescriptor<?, ?> descriptor =
        MethodDescriptor.newBuilder()
            .setType(MethodDescriptor.MethodType.SERVER_STREAMING)
            .setFullMethodName("UnqualifiedService/ReadRows")
            .setRequestMarshaller(Mockito.mock(MethodDescriptor.Marshaller.class))
            .setResponseMarshaller(Mockito.mock(MethodDescriptor.Marshaller.class))
            .build();

    ApiTracerContext context =
        ApiTracerContext.newBuilder()
            .setLibraryMetadata(LibraryMetadata.empty())
            .setRpcMethod(descriptor.getFullMethodName())
            .setTransport(ApiTracerContext.Transport.GRPC)
            .build();
    assertThat(context.getClientName()).isEqualTo("UnqualifiedService");
    assertThat(context.getMethodName()).isEqualTo("ReadRows");
  }

  @Test
  void testGetSpanNamePartsHttp() {
    Map<String, String[]> validNames =
        ImmutableMap.of(
            "compute.projects.disableXpnHost", new String[] {"compute.projects", "disableXpnHost"},
            "client.method", new String[] {"client", "method"});

    for (Map.Entry<String, String[]> entry : validNames.entrySet()) {
      @SuppressWarnings("unchecked")
      MethodDescriptor<?, ?> descriptor =
          MethodDescriptor.newBuilder()
              .setFullMethodName(entry.getKey())
              .setType(MethodDescriptor.MethodType.UNARY)
              .setRequestMarshaller(Mockito.mock(MethodDescriptor.Marshaller.class))
              .setResponseMarshaller(Mockito.mock(MethodDescriptor.Marshaller.class))
              .build();

      ApiTracerContext context =
          ApiTracerContext.newBuilder()
              .setLibraryMetadata(LibraryMetadata.empty())
              .setRpcMethod(descriptor.getFullMethodName())
              .setTransport(ApiTracerContext.Transport.HTTP)
              .build();
      assertThat(context.getClientName()).isEqualTo(entry.getValue()[0]);
      assertThat(context.getMethodName()).isEqualTo(entry.getValue()[1]);
    }
  }
}
