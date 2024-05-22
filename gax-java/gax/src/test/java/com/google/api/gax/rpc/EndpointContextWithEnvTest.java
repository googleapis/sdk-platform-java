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
import com.google.common.truth.Truth;
import io.grpc.Status;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mockito;

@ExtendWith(EnvironmentVariableExtension.class)
class EndpointContextWithEnvTest {

  private static final String DEFAULT_ENDPOINT = "test.googleapis.com:443";
  private static final String DEFAULT_MTLS_ENDPOINT = "test.mtls.googleapis.com:443";
  private EndpointContext.Builder defaultEndpointContextBuilder;
  private StatusCode statusCode;

  @BeforeEach
  void setUp() throws IOException {
    defaultEndpointContextBuilder =
        EndpointContext.newBuilder()
            .setServiceName("test")
            .setUniverseDomain(Credentials.GOOGLE_DEFAULT_UNIVERSE)
            .setClientSettingsEndpoint(DEFAULT_ENDPOINT)
            .setMtlsEndpoint(DEFAULT_MTLS_ENDPOINT);
    statusCode = Mockito.mock(StatusCode.class);
    Mockito.when(statusCode.getCode()).thenReturn(StatusCode.Code.UNAUTHENTICATED);
    Mockito.when(statusCode.getTransportCode()).thenReturn(Status.Code.UNAUTHENTICATED);
  }


  // This Universe Domain should match the `GOOGLE_CLOUD_UNIVERSE_DOMAIN` Env Var
  // For this test running locally or in CI, check that the Env Var is set properly.
  // This test should only run when the maven profile `EnvVarTest` is enabled.
  @Test
  void endpointContextBuild_universeDomainEnvVarSet() throws IOException {
    String envVarUniverseDomain = "random.com";
    EndpointContext endpointContext =
        defaultEndpointContextBuilder
            .setUniverseDomain(null)
            .setClientSettingsEndpoint(null)
            .build();
    Truth.assertThat(endpointContext.resolvedEndpoint()).isEqualTo("test.random.com:443");
    Truth.assertThat(endpointContext.resolvedUniverseDomain()).isEqualTo(envVarUniverseDomain);
  }

  // This Universe Domain should match the `GOOGLE_CLOUD_UNIVERSE_DOMAIN` Env Var
  // For this test running locally or in CI, check that the Env Var is set properly.
  // This test should only run when the maven profile `EnvVarTest` is enabled.
  @Test
  void endpointContextBuild_multipleUniverseDomainConfigurations_clientSettingsHasPriority()
      throws IOException {
    // This test has `GOOGLE_CLOUD_UNIVERSE_DOMAIN` = `random.com`
    String clientSettingsUniverseDomain = "clientSettingsUniverseDomain.com";
    EndpointContext endpointContext =
        defaultEndpointContextBuilder
            .setUniverseDomain(clientSettingsUniverseDomain)
            .setClientSettingsEndpoint(null)
            .build();
    Truth.assertThat(endpointContext.resolvedEndpoint())
        .isEqualTo("test.clientSettingsUniverseDomain.com:443");
    // Client Settings Universe Domain (if set) takes priority
    Truth.assertThat(endpointContext.resolvedUniverseDomain())
        .isEqualTo(clientSettingsUniverseDomain);
  }
}

class EnvironmentVariableExtension implements BeforeEachCallback, AfterEachCallback {

  private final static String GOOGLE_CLOUD_UNIVERSE_DOMAIN = "GOOGLE_CLOUD_UNIVERSE_DOMAIN";

  @Override
  public void beforeEach(ExtensionContext context) {
    System.setProperty(GOOGLE_CLOUD_UNIVERSE_DOMAIN, "random.com");
  }

  @Override
  public void afterEach(ExtensionContext context) {
    System.clearProperty(GOOGLE_CLOUD_UNIVERSE_DOMAIN);
  }
}