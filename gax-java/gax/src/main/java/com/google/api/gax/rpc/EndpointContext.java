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
import com.google.common.base.Strings;
import java.io.IOException;
import javax.annotation.Nullable;

/** Contains the fields required to resolve the endpoint and Universe Domain */
@InternalApi
@AutoValue
public abstract class EndpointContext {
  public static final String INVALID_UNIVERSE_DOMAIN_ERROR_TEMPLATE =
      "The configured universe domain (%s) does not match the universe domain found in the credentials (%s). If you haven't configured the universe domain explicitly, `googleapis.com` is the default.";

  /**
   * ServiceName is host URI for Google Cloud Services. It follows the format of
   * `{ServiceName}.googleapis.com`. For example, speech.googleapis.com would have a ServiceName of
   * speech and cloudasset.googleapis.com would have a ServiceName of cloudasset.
   */
  @Nullable
  public abstract String serviceName();

  /**
   * Universe Domain is the domain for Google Cloud Services. It follows the format of
   * `{ServiceName}.{UniverseDomain}`. For example, speech.googleapis.com would have a Universe
   * Domain value of `googleapis.com` and cloudasset.test.com would have a Universe Domain of
   * `test.com`. If this value is not set, this will default to `googleapis.com`.
   */
  @Nullable
  public abstract String universeDomain();

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

  public abstract boolean usingGDCH();

  public abstract String resolvedUniverseDomain();

  public abstract String resolvedEndpoint();

  public abstract Builder toBuilder();

  public static Builder newBuilder() {
    return new AutoValue_EndpointContext.Builder()
        .setSwitchToMtlsEndpointAllowed(false)
        .setUsingGDCH(false);
  }

  /** Check that the User configured universe domain matches the Credentials' universe domain */
  public boolean hasValidUniverseDomain(Credentials credentials) throws IOException {
    if (usingGDCH()) {
      // GDC-H has no universe domain, always return true.
      return true;
    } else if (credentials == null) {
      // If passed with NoCredentialsProvider, match with GDU
      return resolvedUniverseDomain().equals(Credentials.GOOGLE_DEFAULT_UNIVERSE);
    }
    return resolvedUniverseDomain().equals(credentials.getUniverseDomain());
  }

  public EndpointContext merge(EndpointContext input) {
    if (input == null) {
      return this;
    }
    Builder builder = this.toBuilder();
    String serviceName = input.serviceName();
    if (serviceName != null) {
      builder.setServiceName(serviceName);
    }
    String universeDomain = input.universeDomain();
    if (universeDomain != null) {
      builder.setUniverseDomain(universeDomain);
    }
    String clientSettingsEndpoint = input.clientSettingsEndpoint();
    if (clientSettingsEndpoint != null) {
      builder.setClientSettingsEndpoint(clientSettingsEndpoint);
    }
    String transportChannelProviderEndpoint = input.transportChannelProviderEndpoint();
    if (transportChannelProviderEndpoint != null) {
      builder.setTransportChannelProviderEndpoint(transportChannelProviderEndpoint);
    }
    String mtlsEndpoint = input.mtlsEndpoint();
    if (mtlsEndpoint != null) {
      builder.setMtlsEndpoint(mtlsEndpoint);
    }
    builder.setSwitchToMtlsEndpointAllowed(input.switchToMtlsEndpointAllowed());
    MtlsProvider mtlsProvider = input.mtlsProvider();
    if (mtlsEndpoint != null) {
      builder.setMtlsProvider(mtlsProvider);
    }
    builder.setUsingGDCH(input.usingGDCH());
    try {
      return builder.build();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @AutoValue.Builder
  public abstract static class Builder {
    /**
     * ServiceName is host URI for Google Cloud Services. It follows the format of
     * `{ServiceName}.googleapis.com`. For example, speech.googleapis.com would have a ServiceName
     * of speech and cloudasset.googleapis.com would have a ServiceName of cloudasset.
     */
    public abstract Builder setServiceName(String serviceName);

    /**
     * Universe Domain is the domain for Google Cloud Services. It follows the format of
     * `{ServiceName}.{UniverseDomain}`. For example, speech.googleapis.com would have a Universe
     * Domain value of `googleapis.com` and cloudasset.test.com would have a Universe Domain of
     * `test.com`. If this value is not set, this will default to `googleapis.com`.
     */
    public abstract Builder setUniverseDomain(String universeDomain);

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

    public abstract Builder setUsingGDCH(boolean usingGDCH);

    public abstract Builder setResolvedEndpoint(String resolvedEndpoint);

    public abstract Builder setResolvedUniverseDomain(String resolvedUniverseDomain);

    abstract String serviceName();

    abstract String universeDomain();

    abstract String clientSettingsEndpoint();

    abstract String transportChannelProviderEndpoint();

    abstract String mtlsEndpoint();

    abstract boolean switchToMtlsEndpointAllowed();

    abstract MtlsProvider mtlsProvider();

    abstract boolean usingGDCH();

    abstract String resolvedUniverseDomain();

    abstract EndpointContext autoBuild();

    private String determineUniverseDomain() {
      if (usingGDCH()) {
        // GDC-H has no concept of Universe Domain. User should not set a custom value
        if (universeDomain() != null) {
          throw new IllegalArgumentException(
              "Universe domain configuration is incompatible with GDC-H");
        }
        return Credentials.GOOGLE_DEFAULT_UNIVERSE;
      }
      // Check for "" (empty string)
      if (universeDomain() != null && universeDomain().isEmpty()) {
        throw new IllegalArgumentException("The universe domain value cannot be empty.");
      }
      // Override with user set universe domain if provided
      return universeDomain() != null ? universeDomain() : Credentials.GOOGLE_DEFAULT_UNIVERSE;
    }

    /** Determines the fully resolved endpoint and universe domain values */
    private String determineEndpoint() throws IOException {
      MtlsProvider mtlsProvider = mtlsProvider() == null ? new MtlsProvider() : mtlsProvider();
      // TransportChannelProvider's endpoint will override the ClientSettings' endpoint
      String customEndpoint =
          transportChannelProviderEndpoint() == null
              ? clientSettingsEndpoint()
              : transportChannelProviderEndpoint();

      // GDC-H has a separate flow
      if (usingGDCH()) {
        if (customEndpoint == null) {
          return buildEndpointTemplate(serviceName(), resolvedUniverseDomain());
        }
        return customEndpoint;
      }

      // If user does not provide a custom endpoint, build one with the universe domain
      if (Strings.isNullOrEmpty(customEndpoint)) {
        customEndpoint = buildEndpointTemplate(serviceName(), resolvedUniverseDomain());
      }

      String endpoint =
          mtlsEndpointResolver(
              customEndpoint, mtlsEndpoint(), switchToMtlsEndpointAllowed(), mtlsProvider);

      // Check if mTLS is configured with non-GDU
      if (endpoint.equals(mtlsEndpoint())
          && !resolvedUniverseDomain().equals(Credentials.GOOGLE_DEFAULT_UNIVERSE)) {
        throw new IllegalArgumentException(
            "mTLS is not supported in any universe other than googleapis.com");
      }

      return endpoint;
    }

    // Default to port 443 for HTTPS. Using HTTP requires explicitly setting the endpoint
    private String buildEndpointTemplate(String serviceName, String resolvedUniverseDomain) {
      return serviceName + "." + resolvedUniverseDomain + ":443";
    }

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

    public EndpointContext build() throws IOException {
      // The Universe Domain is used to resolve the Endpoint. It should be resolved first
      setResolvedUniverseDomain(determineUniverseDomain());
      setResolvedEndpoint(determineEndpoint());
      return autoBuild();
    }
  }
}
