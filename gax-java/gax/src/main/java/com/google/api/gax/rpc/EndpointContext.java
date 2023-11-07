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
import com.google.auth.Credentials;
import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@InternalApi
@AutoValue
public abstract class EndpointContext {
  private static final String GOOGLE_DEFAULT_UNIVERSE = "googleapis.com";
  private static final String DEFAULT_PORT = "443";
  private static final String ENDPOINT_TEMPLATE = "https://SERVICE_NAME.UNIVERSE_DOMAIN:PORT";
  private static final Pattern ENDPOINT_REGEX =
      Pattern.compile("^(https\\:\\/\\/)?(www.)?[a-zA-Z]+\\.[\\S]+(\\:\\d)?$");

  @Nullable
  public abstract String clientSettingsEndpoint();

  @Nullable
  public abstract String transportChannelEndpoint();

  @Nullable
  public abstract String mtlsEndpoint();

  public abstract boolean switchToMtlsEndpointAllowed();

  @Nullable
  public abstract String universeDomain();

  @VisibleForTesting
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
    if (resolvedEndpoint != null && resolvedUniverseDomain != null) {
      return;
    }
    String customEndpoint =
        transportChannelEndpoint() != null ? transportChannelEndpoint() : clientSettingsEndpoint();
    Preconditions.checkNotNull(customEndpoint, "Client Library cannot have an null endpoint");
    MtlsProvider mtlsProvider = mtlsProvider() == null ? new MtlsProvider() : mtlsProvider();
    // If the Universe Domain is not specified, use the GDU
    if (universeDomain() == null) {
      resolvedEndpoint =
          mtlsEndpointResolver(
              mtlsProvider, customEndpoint, mtlsEndpoint(), switchToMtlsEndpointAllowed());
      resolvedUniverseDomain = GOOGLE_DEFAULT_UNIVERSE;
      return;
    }
    // Check if it matches the ENDPOINT_TEMPLATE format
    Matcher matcher = ENDPOINT_REGEX.matcher(customEndpoint);
    Preconditions.checkState(matcher.matches(), "Endpoint: " + customEndpoint + " is invalid");

    customEndpoint =
        mtlsEndpointResolver(
            mtlsProvider, customEndpoint, mtlsEndpoint(), switchToMtlsEndpointAllowed());
    // mTLS is not supported yet. If mTLS is enabled, use that endpoint.
    if (customEndpoint.contains("mtls")) {
      resolvedEndpoint = customEndpoint;
      resolvedUniverseDomain = GOOGLE_DEFAULT_UNIVERSE;
      return;
    }

    if (customEndpoint.contains("https://")) {
      customEndpoint = customEndpoint.substring(8);
    }

    // Parse the custom endpoint for the service name, universe domain, and the port
    int periodIndex = customEndpoint.indexOf('.');
    int colonIndex = customEndpoint.indexOf(':');
    String serviceName;
    String universeDomain;
    String port = DEFAULT_PORT;
    if (colonIndex != -1) {
      universeDomain = customEndpoint.substring(periodIndex + 1, colonIndex);
      port = customEndpoint.substring(colonIndex + 1);
    } else {
      universeDomain = customEndpoint.substring(periodIndex + 1);
    }
    serviceName = customEndpoint.substring(0, periodIndex);

    // TODO: Build out logic for resolving endpoint
    resolvedEndpoint = buildEndpoint(serviceName, universeDomain, port);
    resolvedUniverseDomain = universeDomain;
  }

  private String mtlsEndpointResolver(
      MtlsProvider mtlsProvider,
      String endpoint,
      String mtlsEndpoint,
      boolean switchToMtlsEndpointAllowed)
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

  private String buildEndpoint(String serviceName, String universeDomain, String port) {
    return ENDPOINT_TEMPLATE
        .replace("SERVICE_NAME", serviceName)
        .replace("UNIVERSE_DOMAIN", universeDomain)
        .replace("PORT", port);
  }

  public String resolveEndpoint(Credentials credentials) throws IOException {
    if (resolvedEndpoint == null) {
      determineEndpoint();
    }
    return resolvedEndpoint;
  }

  public String resolveUniverseDomain(Credentials credentials) throws IOException {
    if (resolvedUniverseDomain == null) {
      determineEndpoint();
    }
    return resolvedUniverseDomain;
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setClientSettingsEndpoint(String clientSettingsEndpoint);

    public abstract Builder setTransportChannelEndpoint(String transportChannelEndpoint);

    public abstract Builder setMtlsEndpoint(String mtlsEndpoint);

    public abstract Builder setSwitchToMtlsEndpointAllowed(boolean switchToMtlsEndpointAllowed);

    public abstract Builder setUniverseDomain(String universeDomain);

    @VisibleForTesting
    public abstract Builder setMtlsProvider(MtlsProvider mtlsProvider);

    public abstract EndpointContext build();
  }
}
