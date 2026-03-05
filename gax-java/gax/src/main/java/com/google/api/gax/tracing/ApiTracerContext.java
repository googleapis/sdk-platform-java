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
import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.Map;
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
  @Nullable
  public abstract String serverAddress();

  @Nullable
  public abstract Integer serverPort();

  public abstract LibraryMetadata libraryMetadata();

  /**
   * @return a map of attributes to be included in attempt-level spans
   */
  public Map<String, Object> getAttemptAttributes() {
    Map<String, Object> attributes = new HashMap<>();
    if (!Strings.isNullOrEmpty(serverAddress())) {
      attributes.put(ObservabilityAttributes.SERVER_ADDRESS_ATTRIBUTE, serverAddress());
    }
    if (serverPort() != null) {
      attributes.put(ObservabilityAttributes.SERVER_PORT_ATTRIBUTE, serverPort());
    }
    if (!Strings.isNullOrEmpty(libraryMetadata().repository())) {
      attributes.put(ObservabilityAttributes.REPO_ATTRIBUTE, libraryMetadata().repository());
    }
    if (!Strings.isNullOrEmpty(libraryMetadata().artifactName())) {
      attributes.put(ObservabilityAttributes.ARTIFACT_ATTRIBUTE, libraryMetadata().artifactName());
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

    public abstract Builder setServerPort(Integer serverPort);

    public abstract ApiTracerContext build();
  }
}
