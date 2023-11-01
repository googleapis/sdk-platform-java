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
import com.google.auth.Credentials;
import com.google.auto.value.AutoValue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@InternalApi
@AutoValue
public abstract class EndpointContext {
  private static final String DEFAULT_UNIVERSE_DOMAIN = "googleapis.com";
  private static final String DEFAULT_PORT = "443";
  private static final String UNIVERSE_DOMAIN_TEMPLATE = "SERVICE.UNIVERSE_DOMAIN:PORT";
  private static final Pattern ENDPOINT_REGEX = Pattern.compile("^[a-zA-Z]+\\.[\\S]+:\\d+$");

  @Nullable
  public abstract String serviceName();

  @Nullable
  public abstract String clientSettingsEndpoint();

  @Nullable
  public abstract String transportChannelEndpoint();

  @Nullable
  public abstract String mtlsEndpoint();

  public abstract boolean switchToMtlsEndpointAllowed();

  @Nullable
  public abstract String universeDomain();

  public abstract Builder toBuilder();

  private String resolvedEndpoint;
  private String resolvedUniverseDomain;

  public static Builder newBuilder() {
    return new AutoValue_EndpointContext.Builder().setSwitchToMtlsEndpointAllowed(false);
  }

  private void determineEndpoint() {
    if (resolvedEndpoint != null && resolvedUniverseDomain != null) {
      return;
    }
    String customEndpoint =
        transportChannelEndpoint() != null ? transportChannelEndpoint() : clientSettingsEndpoint();
    if (customEndpoint == null) {
      resolvedEndpoint = buildEndpoint("test");
      resolvedUniverseDomain = DEFAULT_UNIVERSE_DOMAIN;
    } else {
      Matcher matcher = ENDPOINT_REGEX.matcher(customEndpoint);
      // Check if it matches the format in the template
      if (!matcher.matches()) {
        // Throw an exception if user's endpoint is not valid
        // throw new Exception("Invalid endpoint: " + customEndpoint);
        return;
      }
      int periodIndex = customEndpoint.indexOf('.');
      int colonIndex = customEndpoint.indexOf(':');
      String serviceName = customEndpoint.substring(0, periodIndex);
      String universeDomain = customEndpoint.substring(periodIndex + 1, colonIndex);
      String port = customEndpoint.substring(colonIndex + 1);
      // TODO: Build out logic for resolving endpoint
      resolvedEndpoint = buildEndpoint(serviceName, universeDomain, port);
      resolvedUniverseDomain = universeDomain;
    }
  }

  private String buildEndpoint(String service) {
    return buildEndpoint(service, DEFAULT_UNIVERSE_DOMAIN, DEFAULT_PORT);
  }

  private String buildEndpoint(String service, String universeDomain, String port) {
    return UNIVERSE_DOMAIN_TEMPLATE
        .replace("SERVICE", service)
        .replace("UNIVERSE_DOMAIN", universeDomain)
        .replace("PORT", port);
  }

  public String resolveEndpoint(Credentials credentials) {
    if (resolvedEndpoint == null) {
      determineEndpoint();
    }
    return resolvedEndpoint;
  }

  public String resolveUniverseDomain(Credentials credentials) {
    if (resolvedUniverseDomain == null) {
      determineEndpoint();
    }
    return resolvedUniverseDomain;
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setServiceName(String serviceName);

    public abstract Builder setClientSettingsEndpoint(String clientSettingsEndpoint);

    public abstract Builder setTransportChannelEndpoint(String transportChannelEndpoint);

    public abstract Builder setMtlsEndpoint(String mtlsEndpoint);

    public abstract Builder setSwitchToMtlsEndpointAllowed(boolean switchToMtlsEndpointAllowed);

    public abstract Builder setUniverseDomain(String universeDomain);

    public abstract EndpointContext build();
  }
}
