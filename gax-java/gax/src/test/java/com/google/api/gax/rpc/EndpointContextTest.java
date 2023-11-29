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
import com.google.common.truth.Truth;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EndpointContextTest {
  private static final String DEFAULT_ENDPOINT = "test.googleapis.com";
  private static final String DEFAULT_MTLS_ENDPOINT = "test.mtls.googleapis.com";
  private static final EndpointContext DEFAULT_ENDPOINT_CONTEXT =
      EndpointContext.newBuilder()
          .setClientSettingsEndpoint(DEFAULT_ENDPOINT)
          .setMtlsEndpoint(DEFAULT_MTLS_ENDPOINT)
          .build();

  @Test
  public void mtlsEndpointResolver_switchToMtlsAllowedIsFalse_returnsEndpoint() throws IOException {
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            true,
            MtlsProvider.MtlsEndpointUsagePolicy.AUTO,
            FakeMtlsProvider.createTestMtlsKeyStore(),
            "",
            false);
    Truth.assertThat(
            DEFAULT_ENDPOINT_CONTEXT.mtlsEndpointResolver(
                mtlsProvider, DEFAULT_ENDPOINT, DEFAULT_MTLS_ENDPOINT, false))
        .isEqualTo(DEFAULT_ENDPOINT);
  }

  @Test
  public void mtlsEndpointResolver_switchToMtlsAllowedIsTrue_mtlsUsageAlways_returnsMtlsEndpoint()
      throws IOException {
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            true,
            MtlsProvider.MtlsEndpointUsagePolicy.ALWAYS,
            FakeMtlsProvider.createTestMtlsKeyStore(),
            "",
            false);
    Truth.assertThat(
            DEFAULT_ENDPOINT_CONTEXT.mtlsEndpointResolver(
                mtlsProvider, DEFAULT_ENDPOINT, DEFAULT_MTLS_ENDPOINT, true))
        .isEqualTo(DEFAULT_MTLS_ENDPOINT);
  }

  @Test
  public void mtlsEndpointResolver_switchToMtlsAllowedIsTrue_mtlsUsageNever_returnsEndpoint()
      throws IOException {
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            true,
            MtlsProvider.MtlsEndpointUsagePolicy.NEVER,
            FakeMtlsProvider.createTestMtlsKeyStore(),
            "",
            false);
    Truth.assertThat(
            DEFAULT_ENDPOINT_CONTEXT.mtlsEndpointResolver(
                mtlsProvider, DEFAULT_ENDPOINT, DEFAULT_MTLS_ENDPOINT, true))
        .isEqualTo(DEFAULT_ENDPOINT);
  }

  @Test
  public void
      mtlsEndpointResolver_switchToMtlsAllowedIsTrue_useCertificateIsTrue_hasMtlsKeystore_returnsMtlsEndpoint()
          throws IOException {
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(
            true,
            MtlsProvider.MtlsEndpointUsagePolicy.AUTO,
            FakeMtlsProvider.createTestMtlsKeyStore(),
            "",
            false);
    Truth.assertThat(
            DEFAULT_ENDPOINT_CONTEXT.mtlsEndpointResolver(
                mtlsProvider, DEFAULT_ENDPOINT, DEFAULT_MTLS_ENDPOINT, true))
        .isEqualTo(DEFAULT_MTLS_ENDPOINT);
  }

  @Test
  public void
      mtlsEndpointResolver_switchToMtlsAllowedIsTrue_useCertificateIsFalse_nullMtlsKeystore_returnsEndpoint()
          throws IOException {
    MtlsProvider mtlsProvider =
        new FakeMtlsProvider(false, MtlsProvider.MtlsEndpointUsagePolicy.AUTO, null, "", false);
    Truth.assertThat(
            DEFAULT_ENDPOINT_CONTEXT.mtlsEndpointResolver(
                mtlsProvider, DEFAULT_ENDPOINT, DEFAULT_MTLS_ENDPOINT, true))
        .isEqualTo(DEFAULT_ENDPOINT);
  }

  @Test
  public void determineEndpoint_nullCustomEndpoints_throwsIllegalStateException() {
    EndpointContext endpointContext =
        DEFAULT_ENDPOINT_CONTEXT
            .toBuilder()
            .setClientSettingsEndpoint(null)
            .setTransportChannelEndpoint(null)
            .build();
    assertThrows(IllegalStateException.class, endpointContext::determineEndpoint);
  }
}
