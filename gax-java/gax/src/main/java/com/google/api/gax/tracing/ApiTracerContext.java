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

import com.google.api.core.InternalApi;
import com.google.api.gax.rpc.LibraryMetadata;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

/**
 * A context object that contains information used to infer attributes that are common for all
 * {@link ApiTracer}s.
 *
 * <p>For internal use only.
 */
@InternalApi
@AutoValue
public abstract class ApiTracerContext {

  public enum Transport {
    GRPC,
    HTTP
  }

  // Used to extract service and method name from a grpc MethodDescriptor.
  static final String GRPC_FULL_METHOD_NAME_REGEX = "^.*?([^./]+)/([^./]+)$";
  static final String HTTP_FULL_METHOD_NAME_REGEX = "^(.+)\\.(.+)$";

  @Nullable
  public abstract String serverAddress();

  public abstract LibraryMetadata libraryMetadata();

  @Nullable
  public String rpcSystemName() {
    if (transport() == null) {
      return null;
    }
    return transport() == Transport.GRPC ? "grpc" : "http";
  }

  @Nullable
  public abstract String rpcMethod();

  @Nullable
  public abstract Transport transport();

  /**
   * @return a map of attributes to be included in attempt-level spans
   */
  public Map<String, String> getAttemptAttributes() {
    Map<String, String> attributes = new HashMap<>();
    if (serverAddress() != null) {
      attributes.put(ObservabilityAttributes.SERVER_ADDRESS_ATTRIBUTE, serverAddress());
    }
    if (rpcMethod() != null) {
      attributes.put(ObservabilityAttributes.RPC_METHOD_ATTRIBUTE, rpcMethod());
    }
    if (rpcSystemName() != null) {
      attributes.put(ObservabilityAttributes.RPC_SYSTEM_NAME_ATTRIBUTE, rpcSystemName());
    }
    if (libraryMetadata().repository() != null) {
      attributes.put(ObservabilityAttributes.REPO_ATTRIBUTE, libraryMetadata().repository());
    }
    if (libraryMetadata().artifactName() != null) {
      attributes.put(ObservabilityAttributes.ARTIFACT_ATTRIBUTE, libraryMetadata().artifactName());
    }
    return attributes;
  }

  /**
   * @return the client name part of the span name
   */
  public String getClientName() {
    return getParsedFullMethodNameParts()[0];
  }

  /**
   * @return the method name part of the span name
   */
  public String getMethodName() {
    return getParsedFullMethodNameParts()[1];
  }

  private String[] getParsedFullMethodNameParts() {
    Preconditions.checkState(rpcMethod() != null, "rpcMethod must be set to get SpanName");
    Preconditions.checkState(transport() != null, "transport must be set to get SpanName");

    String regex =
        transport() == Transport.GRPC ? GRPC_FULL_METHOD_NAME_REGEX : HTTP_FULL_METHOD_NAME_REGEX;
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(rpcMethod());

    Preconditions.checkArgument(matcher.matches(), "Invalid rpcMethod: " + rpcMethod());
    return new String[] {matcher.group(1), matcher.group(2)};
  }

  /**
   * Merges this context with another context. The values in the other context take precedence.
   *
   * @param other the other context to merge with
   * @return a new {@link ApiTracerContext} with merged values
   */
  public ApiTracerContext merge(ApiTracerContext other) {
    Builder builder = toBuilder();
    if (other.serverAddress() != null) {
      builder.setServerAddress(other.serverAddress());
    }
    if (!other.libraryMetadata().isEmpty()) {
      builder.setLibraryMetadata(other.libraryMetadata());
    }
    if (other.rpcMethod() != null) {
      builder.setRpcMethod(other.rpcMethod());
    }
    if (other.transport() != null) {
      builder.setTransport(other.transport());
    }
    return builder.build();
  }

  public static ApiTracerContext empty() {
    return newBuilder().setLibraryMetadata(LibraryMetadata.empty()).build();
  }

  public static Builder newBuilder() {
    return new AutoValue_ApiTracerContext.Builder();
  }

  public abstract Builder toBuilder();

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setServerAddress(@Nullable String serverAddress);

    public abstract Builder setLibraryMetadata(LibraryMetadata gapicProperties);

    public abstract Builder setRpcMethod(@Nullable String rpcMethod);

    public abstract Builder setTransport(@Nullable Transport transport);

    public abstract ApiTracerContext build();
  }
}
