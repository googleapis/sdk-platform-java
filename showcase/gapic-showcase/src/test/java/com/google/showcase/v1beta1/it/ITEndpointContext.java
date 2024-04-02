package com.google.showcase.v1beta1.it;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.rpc.ClientContext;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.truth.Truth;
import com.google.showcase.v1beta1.EchoClient;
import com.google.showcase.v1beta1.EchoSettings;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import com.google.showcase.v1beta1.stub.EchoStub;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * This IT tests the different user configurations allowed and their effects on endpoint and
 * universe domain resolution.
 *
 * <p>In these tests, the client is not initialized with the default configuration:
 * `EchoClient.create()`. For showcase tests run in CI, the client must be supplied explicitly
 * supplied with NoCredentials.
 */
public class ITEndpointContext {

  /**
   * Inside the test cases below, we must explicitly configure serviceName. Normally this should not
   * be configured at all, but showcase clients do not have a serviceName. The ExtendStubSettings
   * wrapper return the serviceName by overriding the `getServiceName()` result.
   */
  private static class ExtendedEchoStubSettings extends EchoStubSettings {

    protected ExtendedEchoStubSettings(Builder settingsBuilder) throws IOException {
      super(settingsBuilder);
    }

    @Override
    public String getServiceName() {
      return "test";
    }

    public static ExtendedEchoStubSettings.Builder newBuilder() {
      return ExtendedEchoStubSettings.Builder.createDefault();
    }

    public static class Builder extends EchoStubSettings.Builder {

      protected Builder(ClientContext clientContext) {
        super(clientContext);
      }

      private static ExtendedEchoStubSettings.Builder createDefault() {
        Builder builder = new Builder(((ClientContext) null));
        builder.setTransportChannelProvider(defaultTransportChannelProvider());
        builder.setCredentialsProvider(defaultCredentialsProviderBuilder().build());
        builder.setInternalHeaderProvider(defaultApiClientHeaderProviderBuilder().build());
        builder.setMtlsEndpoint(getDefaultMtlsEndpoint());
        builder.setSwitchToMtlsEndpointAllowed(true);
        return builder;
      }

      @Override
      public ExtendedEchoStubSettings build() throws IOException {
        return new ExtendedEchoStubSettings(this);
      }
    }
  }

  /**
   * Without this ClientSettings wrapper, we must expose a serviceName setter to
   * (Client|Stub)Settings and pass the StubSettings to the client. However, this will result in a
   * null ClientSettings (See {@link EchoClient#create(EchoStub)}). Passing the stub to the Client
   * will result in a NPE when doing `Client.getSettings().get(Endpoint|UniverseDomain)` as the
   * ClientSettings is stored as null.
   */
  private static class ExtendedEchoSettings extends EchoSettings {

    protected ExtendedEchoSettings(Builder settingsBuilder) throws IOException {
      super(settingsBuilder);
    }

    public static EchoSettings.Builder newBuilder() {
      return ExtendedEchoSettings.Builder.createDefault();
    }

    public static class Builder extends EchoSettings.Builder {
      protected Builder() throws IOException {}

      private static ExtendedEchoSettings.Builder createDefault() {
        return new ExtendedEchoSettings.Builder(ExtendedEchoStubSettings.newBuilder());
      }

      protected Builder(ClientContext clientContext) {
        super(clientContext);
      }

      protected Builder(EchoSettings settings) {
        super(settings);
      }

      protected Builder(ExtendedEchoStubSettings.Builder stubSettings) {
        super(stubSettings);
      }
    }
  }

  private static final String DEFAULT_ENDPOINT = "test.googleapis.com:443";

  // Default (no configuration)
  @Test
  public void endpointResolution_default() throws InterruptedException, IOException {
    EchoClient echoClient = null;
    try {
      EchoSettings echoSettings =
          ExtendedEchoSettings.newBuilder()
              .setCredentialsProvider(NoCredentialsProvider.create())
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
          ExtendedEchoSettings.newBuilder()
              .setCredentialsProvider(NoCredentialsProvider.create())
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
  public void endpointResolution_userSetUniverseDomainAndNoUserSetEndpoint() throws IOException, InterruptedException {
    String customUniverseDomain = "random.com";
    EchoClient echoClient = null;
    try {
      EchoSettings echoSettings =
          ExtendedEchoSettings.newBuilder()
              .setCredentialsProvider(NoCredentialsProvider.create())
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
          ExtendedEchoSettings.newBuilder()
              .setCredentialsProvider(NoCredentialsProvider.create())
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
