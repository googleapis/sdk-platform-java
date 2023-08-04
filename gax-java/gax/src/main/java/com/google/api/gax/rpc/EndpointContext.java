/*
 * Copyright 2023 Google LLC
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
package com.google.api.gax.rpc;

import com.google.auth.Credentials;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

@AutoValue
public abstract class EndpointContext {
  private static final String DEFAULT_PORT = "443";
  private static final String UNIVERSE_DOMAIN_TEMPLATE = "SERVICE.UNIVERSE_DOMAIN:PORT";

  public static EndpointContext of(Credentials credentials, String endpoint, String mtlsEndpoint) {
    return builder()
        .setCredentials(credentials)
        .setEndpoint(endpoint)
        .setMtlsEndpoint(mtlsEndpoint)
        .build();
  }

  @Nullable
  public abstract Credentials credentials();
  @Nullable
  public abstract String endpoint();
  @Nullable
  public abstract String mtlsEndpoint();

  public static Builder builder() {
    return new AutoValue_EndpointContext.Builder();
  }

  public String resolveEndpoint() {
    return endpoint();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setCredentials(Credentials credentials);

    public abstract Builder setEndpoint(String endpoint);

    public abstract Builder setMtlsEndpoint(String mtlsEndpoint);

    public abstract EndpointContext build();
  }
}
