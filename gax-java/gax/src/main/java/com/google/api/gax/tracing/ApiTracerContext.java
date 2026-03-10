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
import org.apache.http.protocol.HTTP;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
//gcp.client.service [consistent across T4 spans]
//gcp.client.version [consistent across T4 spans]
//        rpc.system.name (e.g., grpc, http) [consistent across T4 spans]
//        rpc.response.status_code (for gRPC and HTTP) [from last T4 span]
//        rpc.method (for gRPC and HTTP) [from the last T4 span]
//url.domain [consistent across T4 spans]
//        url.template (for HTTP) [from the first T4 span]
//        http.response.status_code (for HTTP) [from the last T4 span]
//server.address [from the last T4 span]
//server.port [from the last T4 span]
//        error.type (if the overall T3 operation failed)

/**
 * A context object that contains information used to infer attributes that are common for all
 * {@link ApiTracer}s.
 *
 * <p>For internal use only.
 */
@InternalApi
@AutoValue
public abstract class ApiTracerContext {
  @Nullable
  public abstract String serverAddress();

  public abstract LibraryMetadata libraryMetadata();

  @Nullable
  public abstract String serviceName();

  @Nullable
  public abstract String urlDomain();

  @Nullable
  public abstract String urlTemplate();

  /**
   * @return a map of attributes to be included in attempt-level spans
   */
  public Map<String, String> getAttemptAttributes() {
    Map<String, String> attributes = new HashMap<>();
    if (serverAddress() != null) {
      attributes.put(ObservabilityAttributes.SERVER_ADDRESS_ATTRIBUTE, serverAddress());
    }
    if (libraryMetadata().repository() != null) {
      attributes.put(ObservabilityAttributes.REPO_ATTRIBUTE, libraryMetadata().repository());
    }
    if (libraryMetadata().artifactName() != null) {
      attributes.put(ObservabilityAttributes.ARTIFACT_ATTRIBUTE, libraryMetadata().artifactName());
    }
    return attributes;
  }

  Map<String, String> getMetricsAttributes() {
    Map<String, String> attributes = new HashMap<>();
    if (serverAddress() != null) {
      attributes.put(ObservabilityAttributes.SERVER_ADDRESS_ATTRIBUTE, serverAddress());
    }
    if (serviceName() != null) {
      attributes.put("gcp.client.service", serviceName());
    }
    if (transport() == HTTP) {
      if (urlDomain() != null) {
        attributes.put("url.domain", serviceName());
      }
      if (serviceName() != null) {
        attributes.put("url.template", serviceName());
      }
    }
    return attributes;
  }

  public static ApiTracerContext empty() {
    return newBuilder().setLibraryMetadata(LibraryMetadata.empty()).build();
  }

  public static Builder newBuilder() {
    return new AutoValue_ApiTracerContext.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setServerAddress(@Nullable String serverAddress);

    public abstract Builder setLibraryMetadata(LibraryMetadata gapicProperties);

    public abstract ApiTracerContext build();
  }
}
