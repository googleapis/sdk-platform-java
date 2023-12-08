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

import com.google.api.core.InternalApi;
import com.google.api.gax.rpc.mtls.MtlsProvider;
import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import java.io.IOException;
import javax.annotation.Nullable;

/** Contains the fields required to resolve the endpoint */
@InternalApi
@AutoValue
public abstract class EndpointContext {
  private static final String DEFAULT_UNIVERSE_DOMAIN = "googleapis.com";

  @Nullable
  public abstract String universeDomain();

  /**
   * ServiceName is host URI for Google Cloud Services. It follows the format of
   * `{ServiceName}.googleapis.com`. For example, speech.googleapis.com would have a ServiceName of
   * speech and cloudasset.googleapis.com would have a ServiceName of cloudasset.
   */
  @Nullable
  public abstract String serviceName();

  /**
   * ClientSettingsEndpoint is the endpoint value set via the ClientSettings/StubSettings classes.
   */
  @Nullable
  public abstract String clientSettingsEndpoint();

  /**
   * TransportChannelProviderEndpoint is the endpoint value set via the TransportChannelProvider
   * class.
   */
  @Nullable
  public abstract String transportChannelProviderEndpoint();

  @Nullable
  public abstract String mtlsEndpoint();

  public abstract boolean switchToMtlsEndpointAllowed();

  @Nullable
  public abstract MtlsProvider mtlsProvider();

  public abstract Builder toBuilder();

  private String resolvedEndpoint;
  private String resolvedUniverseDomain;

  public static Builder newBuilder() {
    return new AutoValue_EndpointContext.Builder().setSwitchToMtlsEndpointAllowed(false);
  }

  @VisibleForTesting
  void determineEndpoint() throws IOException {
    MtlsProvider mtlsProvider = mtlsProvider() == null ? new MtlsProvider() : mtlsProvider();
    String customEndpoint =
        transportChannelProviderEndpoint() == null
            ? clientSettingsEndpoint()
            : transportChannelProviderEndpoint();
    resolvedEndpoint =
        mtlsEndpointResolver(
            customEndpoint, mtlsEndpoint(), switchToMtlsEndpointAllowed(), mtlsProvider);
    resolvedUniverseDomain =
        Strings.isNullOrEmpty(universeDomain()) ? DEFAULT_UNIVERSE_DOMAIN : universeDomain();
  }

  // This takes in parameters because determineEndpoint()'s logic will be updated
  // to pass custom values in.
  // Follows https://google.aip.dev/auth/4114 for resolving the endpoint
  @VisibleForTesting
  String mtlsEndpointResolver(
      String endpoint,
      String mtlsEndpoint,
      boolean switchToMtlsEndpointAllowed,
      MtlsProvider mtlsProvider)
      throws IOException {
    if (switchToMtlsEndpointAllowed && mtlsProvider != null) {
      switch (mtlsProvider.getMtlsEndpointUsagePolicy()) {
        case ALWAYS:
          return mtlsEndpoint;
        case NEVER:
          return endpoint;
        default:
          if (mtlsProvider.useMtlsClientCertificate() && mtlsProvider.getKeyStore() != null) {
            return mtlsEndpoint;
          }
          return endpoint;
      }
    }
    return endpoint;
  }

  /**
   * The resolved endpoint is the computed endpoint after accounting for the custom endpoints and
   * mTLS configurations.
   */
  public String getResolvedEndpoint() {
    return resolvedEndpoint;
  }

  public String getResolvedUniverseDomain() {
    return resolvedUniverseDomain;
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setUniverseDomain(String universeDomain);

    /**
     * ServiceName is host URI for Google Cloud Services. It follows the format of
     * `{ServiceName}.googleapis.com`. For example, speech.googleapis.com would have a ServiceName
     * of speech and cloudasset.googleapis.com would have a ServiceName of cloudasset.
     */
    public abstract Builder setServiceName(String serviceName);

    /**
     * ClientSettingsEndpoint is the endpoint value set via the ClientSettings/StubSettings classes.
     */
    public abstract Builder setClientSettingsEndpoint(String clientSettingsEndpoint);

    /**
     * TransportChannelProviderEndpoint is the endpoint value set via the TransportChannelProvider
     * class.
     */
    public abstract Builder setTransportChannelProviderEndpoint(String transportChannelEndpoint);

    public abstract Builder setMtlsEndpoint(String mtlsEndpoint);

    public abstract Builder setSwitchToMtlsEndpointAllowed(boolean switchToMtlsEndpointAllowed);

    public abstract Builder setMtlsProvider(MtlsProvider mtlsProvider);

    abstract EndpointContext autoBuild();

    public EndpointContext build() throws IOException {
      EndpointContext endpointContext = autoBuild();
      endpointContext.determineEndpoint();
      return endpointContext;
    }
  }
}
