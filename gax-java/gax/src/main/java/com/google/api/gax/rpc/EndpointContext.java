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

/**
 * EndpointContext is an internal class used by the client library to resolve the endpoint. It is
 * created once the library is initialized should not be updated manually.
 *
 * <p>Contains the fields required to resolve the endpoint and Universe Domain
 */
@InternalApi
@AutoValue
public abstract class EndpointContext {
  private static final String GOOGLE_CLOUD_UNIVERSE_DOMAIN = "GOOGLE_CLOUD_UNIVERSE_DOMAIN";
  private static final String INVALID_UNIVERSE_DOMAIN_ERROR_TEMPLATE =
      "The configured universe domain (%s) does not match the universe domain found in the credentials (%s). If you haven't configured the universe domain explicitly, `googleapis.com` is the default.";
  public static final String UNABLE_TO_RETRIEVE_CREDENTIALS_ERROR_MESSAGE =
      "Unable to retrieve the Universe Domain from the Credentials.";

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

  abstract String resolvedUniverseDomain();

  public abstract String resolvedEndpoint();

  public abstract Builder toBuilder();

  public static Builder newBuilder() {
    return new AutoValue_EndpointContext.Builder()
        .setSwitchToMtlsEndpointAllowed(false)
        .setUsingGDCH(false);
  }

  /**
   * Check that the User configured universe domain matches the Credentials' universe domain. The
   * status code parameter is passed in to this method as it's a limitation of Gax's modules. The
   * transport-neutral module does have access the transport-specific modules (which contain the
   * implementation of the StatusCode). This method is scoped to be internal and should be not be
   * accessed by users.
   *
   * @param credentials Auth Library Credentials
   * @param invalidUniverseDomainStatusCode Transport-specific Status Code to be returned if the
   *     Universe Domain is invalid. For both transports, this is defined to be Unauthorized.
   * @throws IOException Implementation of Auth's Retryable interface which tells the client library
   *     whether the RPC should be retried or not.
   */
  public void validateUniverseDomain(
      Credentials credentials, StatusCode invalidUniverseDomainStatusCode) throws IOException {
    if (usingGDCH()) {
      // GDC-H has no universe domain, return
      return;
    }
    String credentialsUniverseDomain = Credentials.GOOGLE_DEFAULT_UNIVERSE;
    // If credentials is not NoCredentialsProvider, use the Universe Domain inside Credentials
    if (credentials != null) {
      credentialsUniverseDomain = credentials.getUniverseDomain();
    }
    if (!resolvedUniverseDomain().equals(credentialsUniverseDomain)) {
      throw ApiExceptionFactory.createException(
          new Throwable(
              String.format(
                  EndpointContext.INVALID_UNIVERSE_DOMAIN_ERROR_TEMPLATE,
                  resolvedUniverseDomain(),
                  credentialsUniverseDomain)),
          invalidUniverseDomainStatusCode,
          false);
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
      String universeDomain = universeDomain();
      if (usingGDCH()) {
        // GDC-H has no concept of Universe Domain. User should not set a custom value
        if (universeDomain != null) {
          throw new IllegalArgumentException(
              "Universe domain configuration is incompatible with GDC-H");
        }
        return Credentials.GOOGLE_DEFAULT_UNIVERSE;
      }
      // Check for "" (empty string)
      if (universeDomain != null && universeDomain.isEmpty()) {
        throw new IllegalArgumentException("The universe domain value cannot be empty.");
      }
      // If the universe domain wasn't configured explicitly in the settings, check the
      // environment variable for the value
      if (universeDomain == null) {
        universeDomain = System.getenv(GOOGLE_CLOUD_UNIVERSE_DOMAIN);
      }
      // If the universe domain is configured by the user, the universe domain will either be
      // from the settings or from the env var. The value from ClientSettings has priority.
      return universeDomain != null ? universeDomain : Credentials.GOOGLE_DEFAULT_UNIVERSE;
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
