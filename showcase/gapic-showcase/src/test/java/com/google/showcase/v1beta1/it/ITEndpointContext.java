package com.google.showcase.v1beta1.it;

import com.google.api.gax.core.NoCredentialsProvider;
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
  public static final String SHOWCASE_DEFAULT_ENDPOINT = "localhost:7469";

  // Default (no configuration)
  @Test
  public void endpointResolution_default() throws InterruptedException, IOException {
    EchoClient echoClient = null;
    try {
      // The default usage is EchoClient.create(), but for showcase tests run in CI, the
      // client must be supplied with Credentials.
      EchoSettings echoSettings =
          EchoSettings.newBuilder().setCredentialsProvider(NoCredentialsProvider.create()).build();
      echoClient = EchoClient.create(echoSettings);
      Truth.assertThat(echoClient.getSettings().getEndpoint()).isEqualTo(SHOWCASE_DEFAULT_ENDPOINT);
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
  public void endpointResolution_userConfiguration() throws InterruptedException, IOException {
    String customEndpoint = "test.com:123";
    EchoClient echoClient = null;
    try {
      EchoSettings echoSettings =
          EchoSettings.newBuilder()
              .setCredentialsProvider(NoCredentialsProvider.create())
              .setEndpoint(customEndpoint)
              .build();
      echoClient = EchoClient.create(echoSettings);
      Truth.assertThat(echoClient.getSettings().getEndpoint()).isEqualTo(customEndpoint);
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
  public void endpointResolution_defaultBuilder() {
    EchoSettings.Builder echoSettingsBuilder = EchoSettings.newBuilder();
    Truth.assertThat(echoSettingsBuilder.getEndpoint()).isEqualTo(SHOWCASE_DEFAULT_ENDPOINT);
  }

  // User configuration in Builder
  @Test
  public void endpointResolution_userConfigurationBuilder() {
    String customEndpoint = "test.com:123";
    EchoSettings.Builder echoSettingsBuilder =
        EchoSettings.newBuilder().setEndpoint(customEndpoint);
    Truth.assertThat(echoSettingsBuilder.getEndpoint()).isEqualTo(customEndpoint);
  }
}
