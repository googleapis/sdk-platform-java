/*
 * Copyright 2024 Google LLC
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
package com.google.api.gax.grpc;

import static org.junit.Assert.*;

import com.google.api.gax.grpc.testing.FakeGrpcChannel;
import com.google.api.gax.grpc.testing.FakeMethodDescriptor;
import com.google.api.gax.rpc.EndpointContext;
import com.google.common.base.Objects;
import com.google.protobuf.Empty;
import io.grpc.ClientCall;
import org.junit.Test;
import org.mockito.Mockito;

public class GrpcDirectCallableTest {

  @Test
  public void testFutureCall_withRequestMutator() {
    // Given
    TestMessage expectedMessage = new TestMessage("Mutated");
    GrpcCallSettings<TestMessage, Empty> grpcCallSettings =
        GrpcCallSettings.<TestMessage, Empty>newBuilder()
            .setShouldAwaitTrailers(false)
            .setRequestMutator(testMessage -> expectedMessage)
            .setMethodDescriptor(FakeMethodDescriptor.create())
            .build();
    GrpcDirectCallable<TestMessage, Empty> grpcDirectCallable =
        new GrpcDirectCallable<>(grpcCallSettings);
    ClientCall<TestMessage, Empty> clientCall = Mockito.mock(ClientCall.class);
    FakeGrpcChannel fakeChannel = new FakeGrpcChannel(clientCall);
    EndpointContext endpointContext = Mockito.mock(EndpointContext.class);

    GrpcCallContext grpcCallContext =
        GrpcCallContext.createDefault()
            .withChannel(fakeChannel)
            .withEndpointContext(endpointContext);

    // When
    grpcDirectCallable.futureCall(new TestMessage("Before"), grpcCallContext);

    // Then
    Mockito.verify(clientCall).sendMessage(expectedMessage);
  }

  @Test
  public void testFutureCall_withNullRequestMutator() {
    // Given
    TestMessage expectedMessage = new TestMessage("Before");
    GrpcCallSettings<TestMessage, Empty> grpcCallSettings =
        GrpcCallSettings.<TestMessage, Empty>newBuilder()
            .setShouldAwaitTrailers(false)
            .setRequestMutator(null)
            .setMethodDescriptor(FakeMethodDescriptor.create())
            .build();
    GrpcDirectCallable<TestMessage, Empty> grpcDirectCallable =
        new GrpcDirectCallable<>(grpcCallSettings);
    ClientCall<TestMessage, Empty> clientCall = Mockito.mock(ClientCall.class);
    FakeGrpcChannel fakeChannel = new FakeGrpcChannel(clientCall);
    EndpointContext endpointContext = Mockito.mock(EndpointContext.class);

    GrpcCallContext grpcCallContext =
        GrpcCallContext.createDefault()
            .withChannel(fakeChannel)
            .withEndpointContext(endpointContext);

    // When
    grpcDirectCallable.futureCall(new TestMessage("Before"), grpcCallContext);

    // Then
    Mockito.verify(clientCall).sendMessage(expectedMessage);
  }

  class TestMessage {
    private final String name;

    public TestMessage(String name) {
      this.name = name;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof TestMessage)) {
        return false;
      }
      TestMessage that = (TestMessage) o;
      return Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(name);
    }
  }
}
