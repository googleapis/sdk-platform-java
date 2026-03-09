package com.google.api.gax.grpc;

import com.google.api.core.InternalApi;
import com.google.api.gax.rpc.mtls.CertificateBasedAccess;
import com.google.auth.Credentials;
import com.google.auth.mtls.MtlsProvider;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.SecureSessionAgent;
import com.google.auth.oauth2.SecureSessionAgentConfig;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import io.grpc.CallCredentials;
import io.grpc.ChannelCredentials;
import io.grpc.CompositeChannelCredentials;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannelBuilder;
import io.grpc.TlsChannelCredentials;
import io.grpc.alts.GoogleDefaultChannelCredentials;
import io.grpc.auth.MoreCallCredentials;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.net.ssl.KeyManagerFactory;

@InternalApi
class GrpcChannelFactory {
  private static final Logger LOG = Logger.getLogger(GrpcChannelFactory.class.getName());

  private static final String MTLS_MDS_ROOT_PATH = "/run/google-mds-mtls/root.crt";
  private static final String MTLS_MDS_CERT_CHAIN_AND_KEY_PATH = "/run/google-mds-mtls/client.key";

  private final GrpcCapabilities capabilities;
  private final SecureSessionAgent s2aConfigProvider;
  private final CertificateBasedAccess certificateBasedAccess;
  @Nullable private final MtlsProvider mtlsProvider;
  private final GrpcChannelConfig config;

  private final ChannelFlavor flavor;

  private enum ChannelFlavor {
    DIRECT_PATH,
    MTLS_DCA,
    S2A,
    DEFAULT
  }

  // This is initialized once for the lifetime of the application. This enables re-using
  // channels to S2A.
  private static volatile ChannelCredentials s2aChannelCredentials;

  public GrpcChannelFactory(
      GrpcCapabilities capabilities,
      SecureSessionAgent s2aConfigProvider,
      CertificateBasedAccess certificateBasedAccess,
      @Nullable MtlsProvider mtlsProvider,
      GrpcChannelConfig config) {
    this.capabilities = capabilities;
    this.s2aConfigProvider = s2aConfigProvider;
    this.certificateBasedAccess = certificateBasedAccess;
    this.mtlsProvider = mtlsProvider;
    this.config = config;
    this.flavor = determineFlavor();
  }

  private ChannelFlavor determineFlavor() {
    if (capabilities.canUseDirectPath(config)) {
      return ChannelFlavor.DIRECT_PATH;
    }
    try {
      if (canUseMtlsDca()) {
        return ChannelFlavor.MTLS_DCA;
      }
    } catch (IOException | GeneralSecurityException e) {
      LOG.log(Level.WARNING, "Error checking for mTLS DCA availability: " + e.getMessage());
    }
    if (config.useS2A()) {
      return ChannelFlavor.S2A;
    }
    return ChannelFlavor.DEFAULT;
  }

  public ManagedChannelBuilder<?> createBuilder() throws IOException {
    switch (flavor) {
      case DIRECT_PATH:
        return createDirectPathBuilder();
      case MTLS_DCA:
        try {
          return createMtlsDcaBuilder();
        } catch (GeneralSecurityException e) {
          throw new IOException(e);
        }
      case S2A:
        return createS2ABuilder();
      default:
        return createDefaultBuilder();
    }
  }

  private ManagedChannelBuilder<?> createDirectPathBuilder() {
    String endpoint = config.endpoint();
    int colon = endpoint.lastIndexOf(':');
    String serviceAddress = endpoint.substring(0, colon);
    int port = Integer.parseInt(endpoint.substring(colon + 1));

    Credentials credentials = config.credentials();
    CallCredentials callCreds = MoreCallCredentials.from(credentials);
    CallCredentials altsCallCredentials = null;
    if (config
        .allowedHardBoundTokenTypes()
        .contains(InstantiatingGrpcChannelProvider.HardBoundTokenTypes.ALTS)) {
      altsCallCredentials =
          createHardBoundTokensCallCredentials(
              credentials, ComputeEngineCredentials.GoogleAuthTransport.ALTS, null);
    }

    ChannelCredentials channelCreds =
        GoogleDefaultChannelCredentials.newBuilder()
            .callCredentials(callCreds)
            .altsCallCredentials(altsCallCredentials)
            .build();

    ManagedChannelBuilder<?> builder;
    if (capabilities.isDirectPathXdsEnabled(config)) {
      builder = Grpc.newChannelBuilder("google-c2p:///" + serviceAddress, channelCreds);
    } else {
      builder = Grpc.newChannelBuilderForAddress(serviceAddress, port, channelCreds);
      if (config.directPathServiceConfig() != null) {
        builder.defaultServiceConfig(config.directPathServiceConfig());
      }
    }

    builder.keepAliveTime(
        InstantiatingGrpcChannelProvider.DIRECT_PATH_KEEP_ALIVE_TIME_SECONDS,
        java.util.concurrent.TimeUnit.SECONDS);
    builder.keepAliveTimeout(
        InstantiatingGrpcChannelProvider.DIRECT_PATH_KEEP_ALIVE_TIMEOUT_SECONDS,
        java.util.concurrent.TimeUnit.SECONDS);

    return builder;
  }

  private ManagedChannelBuilder<?> createMtlsDcaBuilder()
      throws IOException, GeneralSecurityException {
    ChannelCredentials credentials = createMtlsChannelCredentials();
    return Grpc.newChannelBuilder(config.endpoint(), credentials);
  }

  private ManagedChannelBuilder<?> createS2ABuilder() {
    ChannelCredentials channelCredentials = createS2ASecuredChannelCredentials();
    if (channelCredentials != null) {
      if (config
          .allowedHardBoundTokenTypes()
          .contains(InstantiatingGrpcChannelProvider.HardBoundTokenTypes.MTLS_S2A)) {
        CallCredentials mtlsS2ACallCredentials =
            createHardBoundTokensCallCredentials(
                config.credentials(),
                ComputeEngineCredentials.GoogleAuthTransport.MTLS,
                ComputeEngineCredentials.BindingEnforcement.ON);
        if (mtlsS2ACallCredentials != null) {
          channelCredentials =
              CompositeChannelCredentials.create(channelCredentials, mtlsS2ACallCredentials);
        }
      }
      return Grpc.newChannelBuilder(config.mtlsEndpoint(), channelCredentials);
    }
    return createDefaultBuilder();
  }

  private ManagedChannelBuilder<?> createDefaultBuilder() {
    String endpoint = config.endpoint();
    int colon = endpoint.lastIndexOf(':');
    String serviceAddress = endpoint.substring(0, colon);
    int port = Integer.parseInt(endpoint.substring(colon + 1));

    ManagedChannelBuilder<?> builder = ManagedChannelBuilder.forAddress(serviceAddress, port);
    builder.disableServiceConfigLookUp();
    return builder;
  }

  private boolean canUseMtlsDca() throws IOException, GeneralSecurityException {
    return createMtlsChannelCredentials() != null;
  }

  @VisibleForTesting
  ChannelCredentials createMtlsChannelCredentials() throws IOException, GeneralSecurityException {
    if (mtlsProvider == null) {
      return null;
    }
    if (certificateBasedAccess.useMtlsClientCertificate()) {
      KeyStore mtlsKeyStore = mtlsProvider.getKeyStore();
      if (mtlsKeyStore != null) {
        KeyManagerFactory factory =
            KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        factory.init(mtlsKeyStore, new char[] {});
        return TlsChannelCredentials.newBuilder().keyManager(factory.getKeyManagers()).build();
      }
    }
    return null;
  }

  private ChannelCredentials createS2ASecuredChannelCredentials() {
    if (s2aChannelCredentials == null) {
      synchronized (GrpcChannelFactory.class) {
        if (s2aChannelCredentials != null) {
          return s2aChannelCredentials;
        }
        SecureSessionAgentConfig config = s2aConfigProvider.getConfig();
        String plaintextAddress = config.getPlaintextAddress();
        String mtlsAddress = config.getMtlsAddress();
        if (Strings.isNullOrEmpty(mtlsAddress)) {
          LOG.log(Level.INFO, "Fallback to plaintext connection to S2A.");
          s2aChannelCredentials = createPlaintextToS2AChannelCredentials(plaintextAddress);
          return s2aChannelCredentials;
        }
        File rootFile = new File(MTLS_MDS_ROOT_PATH);
        File certKeyFile = new File(MTLS_MDS_CERT_CHAIN_AND_KEY_PATH);
        if (rootFile.isFile() && certKeyFile.isFile()) {
          try {
            ChannelCredentials mtlsToS2AChannelCredentials =
                TlsChannelCredentials.newBuilder()
                    .keyManager(certKeyFile, certKeyFile)
                    .trustManager(rootFile)
                    .build();
            s2aChannelCredentials =
                buildS2AChannelCredentials(mtlsAddress, mtlsToS2AChannelCredentials);
          } catch (IOException ignore) {
            LOG.log(
                Level.WARNING,
                "Fallback to plaintext connection to S2A due to error: " + ignore.getMessage());
            s2aChannelCredentials = createPlaintextToS2AChannelCredentials(plaintextAddress);
          }
        } else {
          LOG.log(
              Level.INFO,
              "Fallback to plaintext connection to S2A because MDS credentials do not exist.");
          s2aChannelCredentials = createPlaintextToS2AChannelCredentials(plaintextAddress);
        }
      }
    }
    return s2aChannelCredentials;
  }

  private ChannelCredentials createPlaintextToS2AChannelCredentials(String plaintextAddress) {
    if (Strings.isNullOrEmpty(plaintextAddress)) {
      return null;
    }
    return buildS2AChannelCredentials(plaintextAddress, InsecureChannelCredentials.create());
  }

  private ChannelCredentials buildS2AChannelCredentials(
      String s2aAddress, ChannelCredentials s2aChannelCredentials) {
    try {
      Class<?> s2aChannelCreds = Class.forName("io.grpc.s2a.S2AChannelCredentials");
      Class<?> s2aChannelCredsBuilder = Class.forName("io.grpc.s2a.S2AChannelCredentials$Builder");
      Method newBuilder =
          s2aChannelCreds.getMethod("newBuilder", String.class, ChannelCredentials.class);
      Object retObjBuilder = newBuilder.invoke(null, s2aAddress, s2aChannelCredentials);
      Method build = s2aChannelCredsBuilder.getMethod("build");
      return (ChannelCredentials) build.invoke(retObjBuilder);
    } catch (Throwable t) {
      LOG.log(
          Level.WARNING, "Falling back to default because S2A APIs cannot be used: " + t.getMessage());
      return null;
    }
  }

  private CallCredentials createHardBoundTokensCallCredentials(
      Credentials credentials,
      ComputeEngineCredentials.GoogleAuthTransport googleAuthTransport,
      ComputeEngineCredentials.BindingEnforcement bindingEnforcement) {
    if (!(credentials instanceof ComputeEngineCredentials)) {
      return null;
    }
    ComputeEngineCredentials gceCreds = (ComputeEngineCredentials) credentials;
    ComputeEngineCredentials.Builder gceCredsBuilder = gceCreds.toBuilder();
    return MoreCallCredentials.from(
        ComputeEngineCredentials.newBuilder()
            .setScopes(gceCredsBuilder.getScopes())
            .setHttpTransportFactory(gceCredsBuilder.getHttpTransportFactory())
            .setGoogleAuthTransport(googleAuthTransport)
            .setBindingEnforcement(bindingEnforcement)
            .build());
  }
}
