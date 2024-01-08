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

import static org.junit.Assert.assertThrows;

import com.google.api.gax.rpc.mtls.MtlsProvider;
import com.google.api.gax.rpc.testing.FakeMtlsProvider;
import com.google.auth.Credentials;
import com.google.common.truth.Truth;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EndpointContextTest {
  private static final String DEFAULT_ENDPOINT = "test.googleapis.com:443";
  private static final String DEFAULT_MTLS_ENDPOINT = "test.mtls.googleapis.com:443";
  private EndpointContext.Builder defaultEndpointContextBuilder;

  @Before
  public void setUp() throws IOException {
    defaultEndpointContextBuilder =
        EndpointContext.newBuilder()
            .setServiceName("test")
            .setUniverseDomain(Credentials.GOOGLE_DEFAULT_UNIVERSE)
            .setClientSettingsEndpoint(DEFAULT_ENDPOINT)
            .setMtlsEndpoint(DEFAULT_MTLS_ENDPOINT);
  }

  @Test
  public void mtlsEndpointResolver_switchToMtlsAllowedIsFalse() throws IOException {
    boolean useClientCertificate = true;
    boolean throwExceptionForGetKeyStore = false;
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            useClientCertificate,
            MtlsProvider.MtlsEndpointUsagePolicy.AUTO,
            FakeMtlsProvider.createTestMtlsKeyStore(),
            "",
            throwExceptionForGetKeyStore);
    boolean switchToMtlsEndpointAllowed = false;
    Truth.assertThat(
            defaultEndpointContextBuilder.mtlsEndpointResolver(
                DEFAULT_ENDPOINT, DEFAULT_MTLS_ENDPOINT, switchToMtlsEndpointAllowed, mtlsProvider))
        .isEqualTo(DEFAULT_ENDPOINT);
  }

  @Test
  public void mtlsEndpointResolver_switchToMtlsAllowedIsTrue_mtlsUsageAuto() throws IOException {
    boolean useClientCertificate = true;
    boolean throwExceptionForGetKeyStore = false;
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            useClientCertificate,
            MtlsProvider.MtlsEndpointUsagePolicy.AUTO,
            FakeMtlsProvider.createTestMtlsKeyStore(),
            "",
            throwExceptionForGetKeyStore);
    boolean switchToMtlsEndpointAllowed = true;
    Truth.assertThat(
            defaultEndpointContextBuilder.mtlsEndpointResolver(
                DEFAULT_ENDPOINT, DEFAULT_MTLS_ENDPOINT, switchToMtlsEndpointAllowed, mtlsProvider))
        .isEqualTo(DEFAULT_MTLS_ENDPOINT);
  }

  @Test
  public void mtlsEndpointResolver_switchToMtlsAllowedIsTrue_mtlsUsageAlways() throws IOException {
    boolean useClientCertificate = true;
    boolean throwExceptionForGetKeyStore = false;
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            useClientCertificate,
            MtlsProvider.MtlsEndpointUsagePolicy.ALWAYS,
            FakeMtlsProvider.createTestMtlsKeyStore(),
            "",
            throwExceptionForGetKeyStore);
    boolean switchToMtlsEndpointAllowed = true;
    Truth.assertThat(
            defaultEndpointContextBuilder.mtlsEndpointResolver(
                DEFAULT_ENDPOINT, DEFAULT_MTLS_ENDPOINT, switchToMtlsEndpointAllowed, mtlsProvider))
        .isEqualTo(DEFAULT_MTLS_ENDPOINT);
  }

  @Test
  public void mtlsEndpointResolver_switchToMtlsAllowedIsTrue_mtlsUsageNever() throws IOException {
    boolean useClientCertificate = true;
    boolean throwExceptionForGetKeyStore = false;
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            useClientCertificate,
            MtlsProvider.MtlsEndpointUsagePolicy.NEVER,
            FakeMtlsProvider.createTestMtlsKeyStore(),
            "",
            throwExceptionForGetKeyStore);
    boolean switchToMtlsEndpointAllowed = true;
    Truth.assertThat(
            defaultEndpointContextBuilder.mtlsEndpointResolver(
                DEFAULT_ENDPOINT, DEFAULT_MTLS_ENDPOINT, switchToMtlsEndpointAllowed, mtlsProvider))
        .isEqualTo(DEFAULT_ENDPOINT);
  }

  @Test
  public void
      mtlsEndpointResolver_switchToMtlsAllowedIsTrue_useCertificateIsFalse_nullMtlsKeystore()
          throws IOException {
    boolean useClientCertificate = false;
    boolean throwExceptionForGetKeyStore = false;
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            useClientCertificate,
            MtlsProvider.MtlsEndpointUsagePolicy.AUTO,
            null,
            "",
            throwExceptionForGetKeyStore);
    boolean switchToMtlsEndpointAllowed = true;
    Truth.assertThat(
            defaultEndpointContextBuilder.mtlsEndpointResolver(
                DEFAULT_ENDPOINT, DEFAULT_MTLS_ENDPOINT, switchToMtlsEndpointAllowed, mtlsProvider))
        .isEqualTo(DEFAULT_ENDPOINT);
  }

  @Test
  public void mtlsEndpointResolver_getKeyStore_throwsIOException() throws IOException {
    boolean useClientCertificate = true;
    boolean throwExceptionForGetKeyStore = true;
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            useClientCertificate,
            MtlsProvider.MtlsEndpointUsagePolicy.AUTO,
            null,
            "",
            throwExceptionForGetKeyStore);
    boolean switchToMtlsEndpointAllowed = true;
    assertThrows(
        IOException.class,
        () ->
            defaultEndpointContextBuilder.mtlsEndpointResolver(
                DEFAULT_ENDPOINT,
                DEFAULT_MTLS_ENDPOINT,
                switchToMtlsEndpointAllowed,
                mtlsProvider));
  }

  @Test
  public void endpointContextBuild_noUniverseDomain_usesClientSettingsEndpoint()
      throws IOException {
    EndpointContext endpointContext =
        defaultEndpointContextBuilder.setClientSettingsEndpoint(DEFAULT_ENDPOINT).build();
    Truth.assertThat(endpointContext.resolvedEndpoint()).isEqualTo(DEFAULT_ENDPOINT);
    Truth.assertThat(endpointContext.resolvedUniverseDomain())
        .isEqualTo(Credentials.GOOGLE_DEFAULT_UNIVERSE);
  }

  @Test
  public void endpointContextBuild_noUniverseDomain_usesTransportChannelProviderEndpoint()
      throws IOException {
    String transportChannelProviderEndpoint = "random.endpoint.com:443";
    EndpointContext endpointContext =
        defaultEndpointContextBuilder
            .setClientSettingsEndpoint(null)
            .setTransportChannelProviderEndpoint(transportChannelProviderEndpoint)
            .build();
    Truth.assertThat(endpointContext.resolvedEndpoint())
        .isEqualTo(transportChannelProviderEndpoint);
    Truth.assertThat(endpointContext.resolvedUniverseDomain())
        .isEqualTo(Credentials.GOOGLE_DEFAULT_UNIVERSE);
  }

  @Test
  public void endpointContextBuild_noUniverseDomain_overrideUsesTransportChannelProviderEndpoint()
      throws IOException {
    String transportChannelProviderEndpoint = "random.endpoint.com";
    EndpointContext endpointContext =
        defaultEndpointContextBuilder
            .setClientSettingsEndpoint(DEFAULT_ENDPOINT)
            .setTransportChannelProviderEndpoint(transportChannelProviderEndpoint)
            .build();
    Truth.assertThat(endpointContext.resolvedEndpoint())
        .isEqualTo(transportChannelProviderEndpoint);
    Truth.assertThat(endpointContext.resolvedUniverseDomain())
        .isEqualTo(Credentials.GOOGLE_DEFAULT_UNIVERSE);
  }

  @Test
  public void endpointContextBuild_emptyStringUniverseDomain_throwsIllegalArgumentException() {
    EndpointContext.Builder endpointContextBuilder =
        defaultEndpointContextBuilder.setUniverseDomain("");
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, endpointContextBuilder::build);
    Truth.assertThat(exception.getMessage())
        .isEqualTo("The universe domain value cannot be empty.");
  }

  @Test
  public void endpointContextBuild_GDUUniverseDomain() throws IOException {
    EndpointContext endpointContext = defaultEndpointContextBuilder.build();
    Truth.assertThat(endpointContext.resolvedEndpoint()).isEqualTo(DEFAULT_ENDPOINT);
    Truth.assertThat(endpointContext.resolvedUniverseDomain())
        .isEqualTo(Credentials.GOOGLE_DEFAULT_UNIVERSE);
  }

  @Test
  public void endpointContextBuild_nonGDUUniverseDomain() throws IOException {
    String universeDomain = "random.com";
    EndpointContext endpointContext =
        defaultEndpointContextBuilder.setUniverseDomain(universeDomain).build();
    Truth.assertThat(endpointContext.resolvedEndpoint()).isEqualTo(DEFAULT_ENDPOINT);
    Truth.assertThat(endpointContext.resolvedUniverseDomain()).isEqualTo(universeDomain);
  }

  @Test
  public void endpointContextBuild_noUniverseDomain_noEndpoints() throws IOException {
    String expectedEndpoint = "random.googleapis.com:443";
    EndpointContext endpointContext =
        defaultEndpointContextBuilder
            .setServiceName("random")
            .setClientSettingsEndpoint(null)
            .setTransportChannelProviderEndpoint(null)
            .build();
    Truth.assertThat(endpointContext.resolvedEndpoint()).isEqualTo(expectedEndpoint);
    Truth.assertThat(endpointContext.resolvedUniverseDomain())
        .isEqualTo(Credentials.GOOGLE_DEFAULT_UNIVERSE);
  }

  @Test
  public void endpointContextBuild_mtlsConfigured_GDU() throws IOException {
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            true,
            MtlsProvider.MtlsEndpointUsagePolicy.ALWAYS,
            FakeMtlsProvider.createTestMtlsKeyStore(),
            "",
            false);
    EndpointContext endpointContext =
        defaultEndpointContextBuilder
            .setClientSettingsEndpoint(null)
            .setTransportChannelProviderEndpoint(null)
            .setSwitchToMtlsEndpointAllowed(true)
            .setMtlsProvider(mtlsProvider)
            .build();
    Truth.assertThat(endpointContext.resolvedEndpoint()).isEqualTo(DEFAULT_MTLS_ENDPOINT);
    Truth.assertThat(endpointContext.resolvedUniverseDomain())
        .isEqualTo(Credentials.GOOGLE_DEFAULT_UNIVERSE);
  }

  @Test
  public void endpointContextBuild_mtlsConfigured_nonGDU_throwsIllegalArgumentException()
      throws IOException {
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            true,
            MtlsProvider.MtlsEndpointUsagePolicy.ALWAYS,
            FakeMtlsProvider.createTestMtlsKeyStore(),
            "",
            false);
    EndpointContext.Builder endpointContextBuilder =
        defaultEndpointContextBuilder
            .setUniverseDomain("random.com")
            .setClientSettingsEndpoint(null)
            .setTransportChannelProviderEndpoint(null)
            .setSwitchToMtlsEndpointAllowed(true)
            .setMtlsProvider(mtlsProvider);
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, endpointContextBuilder::build);
    Truth.assertThat(exception.getMessage())
        .isEqualTo("mTLS is not supported in any universe other than googleapis.com");
  }

  @Test
  public void endpointContextBuild_gdchFlow_setUniverseDomain() throws IOException {
    EndpointContext.Builder endpointContextBuilder =
        defaultEndpointContextBuilder.setUsingGDCH(true);
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, endpointContextBuilder::build);
    Truth.assertThat(exception.getMessage())
        .isEqualTo("Universe domain configuration is incompatible with GDC-H");
  }

  @Test
  public void endpointContextBuild_gdchFlow_noUniverseDomain_noCustomEndpoint() throws IOException {
    EndpointContext endpointContext =
        defaultEndpointContextBuilder
            .setUniverseDomain(null)
            .setUsingGDCH(true)
            .setClientSettingsEndpoint(null)
            .build();
    Truth.assertThat(endpointContext.resolvedEndpoint()).isEqualTo(DEFAULT_ENDPOINT);
    Truth.assertThat(endpointContext.resolvedUniverseDomain())
        .isEqualTo(Credentials.GOOGLE_DEFAULT_UNIVERSE);
  }

  @Test
  public void endpointContextBuild_gdchFlow_noUniverseDomain_customEndpoint() throws IOException {
    String clientSettingsEndpoint = "random.endpoint.com:443";
    EndpointContext endpointContext =
        defaultEndpointContextBuilder
            .setUniverseDomain(null)
            .setUsingGDCH(true)
            .setClientSettingsEndpoint(clientSettingsEndpoint)
            .build();
    Truth.assertThat(endpointContext.resolvedEndpoint()).isEqualTo(clientSettingsEndpoint);
    Truth.assertThat(endpointContext.resolvedUniverseDomain())
        .isEqualTo(Credentials.GOOGLE_DEFAULT_UNIVERSE);
  }
}
