package com.google.showcase.v1beta1.it;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.truth.Truth;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * This IT tests the different user configurations allowed and their effects on endpoint and
 * universe domain resolution.
 *
 * <p>This test will be enhanced in the future when the settings are able to return the resolved
 * endpoint and universe domain values.
 */
public class ITEndpointContext {

  private static final String DEFAULT_ENDPOINT = "test.googleapis.com:443";

  // Default (no configuration)
  // This test is very similar to `endpointResolution_userConfiguration`. This test is kept
  // as future enhancements could allow this test to not have to explicitly set the endpoint.
  @Test
  public void endpointResolution_default() throws InterruptedException, IOException {
    EchoClient echoClient = null;
    try {
      // This is not how a client is created by default:
      // 1. The default usage is EchoClient.create(), but for showcase tests run in CI, the
      // client must be supplied with Credentials.
      // 2. Configure the serviceName. Normally this should be configured by the user at all,
      // but showcase clients do not have a serviceName.
      EchoSettings echoSettings =
          EchoSettings.newBuilder()
              .setCredentialsProvider(NoCredentialsProvider.create())
              .setServiceName("test")
              .build();
      echoClient = EchoClient.create(echoSettings);
      Truth.assertThat(echoClient.getSettings().getEndpoint()).isEqualTo(DEFAULT_ENDPOINT);
      Truth.assertThat(echoClient.getSettings().getUniverseDomain())
          .isEqualTo(GoogleCredentials.GOOGLE_DEFAULT_UNIVERSE);
    } finally {
      if (echoClient != null) {
        echoClient.close();
        echoClient.awaitTermination(
            TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
      }
    }
  }

  // User configuration
  @Test
  public void endpointResolution_userSetEndpoint() throws InterruptedException, IOException {
    String customEndpoint = "test.com:123";
    EchoClient echoClient = null;
    try {
      EchoSettings echoSettings =
          EchoSettings.newBuilder()
              .setCredentialsProvider(NoCredentialsProvider.create())
              .setServiceName("test")
              .setEndpoint(customEndpoint)
              .build();
      echoClient = EchoClient.create(echoSettings);
      Truth.assertThat(echoClient.getSettings().getEndpoint()).isEqualTo(customEndpoint);
      Truth.assertThat(echoClient.getSettings().getUniverseDomain())
          .isEqualTo(GoogleCredentials.GOOGLE_DEFAULT_UNIVERSE);
    } finally {
      if (echoClient != null) {
        echoClient.close();
        echoClient.awaitTermination(
            TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
      }
    }
  }

  @Test
  public void endpointResolution_userSetUniverseDomain() throws IOException, InterruptedException {
    String customUniverseDomain = "random.com";
    EchoClient echoClient = null;
    try {
      EchoSettings echoSettings =
          EchoSettings.newBuilder()
              .setCredentialsProvider(NoCredentialsProvider.create())
              .setServiceName("test")
              .setUniverseDomain(customUniverseDomain)
              .build();
      echoClient = EchoClient.create(echoSettings);
      // If user configured the universe domain, the endpoint is constructed from it
      Truth.assertThat(echoClient.getSettings().getEndpoint()).isEqualTo("test.random.com:443");
      Truth.assertThat(echoClient.getSettings().getUniverseDomain())
          .isEqualTo(customUniverseDomain);
    } finally {
      if (echoClient != null) {
        echoClient.close();
        echoClient.awaitTermination(
            TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
      }
    }
  }

  @Test
  public void endpointResolution_userSetEndpointAndUniverseDomain()
      throws IOException, InterruptedException {
    String customEndpoint = "custom.endpoint.com:443";
    String customUniverseDomain = "random.com";
    EchoClient echoClient = null;
    try {
      EchoSettings echoSettings =
          EchoSettings.newBuilder()
              .setCredentialsProvider(NoCredentialsProvider.create())
              .setServiceName("test")
              .setEndpoint(customEndpoint)
              .setUniverseDomain(customUniverseDomain)
              .build();
      echoClient = EchoClient.create(echoSettings);
      // Custom Endpoint sets the endpoint for the client to use
      Truth.assertThat(echoClient.getSettings().getEndpoint()).isEqualTo(customEndpoint);
      // The universe domain doesn't match the endpoint. The call will fail validation when RPC is
      // called.
      Truth.assertThat(echoClient.getSettings().getUniverseDomain())
          .isEqualTo(customUniverseDomain);
    } finally {
      if (echoClient != null) {
        echoClient.close();
        echoClient.awaitTermination(
            TestClientInitializer.AWAIT_TERMINATION_SECONDS, TimeUnit.SECONDS);
      }
    }
  }

  // Default in Builder (no configuration)
  @Test
  public void endpointResolution_defaultViaBuilder() {
    EchoSettings.Builder echoSettingsBuilder = EchoSettings.newBuilder();
    // The getter in the builder returns the user set value. No configuration
    // means the getter will return null
    Truth.assertThat(echoSettingsBuilder.getEndpoint()).isEqualTo(null);
  }

  // User configuration in Builder
  @Test
  public void endpointResolution_userConfigurationViaBuilder() {
    String customEndpoint = "test.com:123";
    EchoSettings.Builder echoSettingsBuilder =
        EchoSettings.newBuilder().setEndpoint(customEndpoint);
    Truth.assertThat(echoSettingsBuilder.getEndpoint()).isEqualTo(customEndpoint);
  }
}
