package com.google.api.gax.grpc;

import com.google.api.core.InternalApi;
import com.google.api.gax.rpc.internal.EnvironmentProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@InternalApi
class GrpcCapabilities {
  private final EnvironmentProvider envProvider;
  private String systemProductName;

  static final String DIRECT_PATH_ENV_DISABLE_DIRECT_PATH = "GOOGLE_CLOUD_DISABLE_DIRECT_PATH";
  static final String DIRECT_PATH_ENV_ENABLE_XDS = "GOOGLE_CLOUD_ENABLE_DIRECT_PATH_XDS";
  static final String GCE_PRODUCTION_NAME_PRIOR_2016 = "Google";
  static final String GCE_PRODUCTION_NAME_AFTER_2016 = "Google Compute Engine";

  GrpcCapabilities(EnvironmentProvider envProvider) {
    this.envProvider = envProvider;
  }

  @VisibleForTesting
  void setSystemProductName(String systemProductName) {
    this.systemProductName = systemProductName;
  }

  public boolean isOnComputeEngine() {
    String osName = System.getProperty("os.name");
    if ("Linux".equals(osName)) {
      String productName = getSystemProductName();
      return productName.contains(GCE_PRODUCTION_NAME_PRIOR_2016)
          || productName.contains(GCE_PRODUCTION_NAME_AFTER_2016);
    }
    return false;
  }

  private String getSystemProductName() {
    if (systemProductName != null) {
      return systemProductName;
    }
    try {
      return Files.asCharSource(new File("/sys/class/dmi/id/product_name"), StandardCharsets.UTF_8)
          .readFirstLine();
    } catch (IOException e) {
      return "";
    }
  }

  public boolean canUseDirectPath(GrpcChannelConfig config) {
    return isDirectPathEnabled(config)
        && isCredentialDirectPathCompatible(config)
        && isOnComputeEngine()
        && canUseDirectPathWithUniverseDomain(config.endpoint());
  }

  private boolean isDirectPathEnabled(GrpcChannelConfig config) {
    String disableDirectPathEnv = envProvider.getenv(DIRECT_PATH_ENV_DISABLE_DIRECT_PATH);
    if (Boolean.parseBoolean(disableDirectPathEnv)) {
      return false;
    }
    return Boolean.TRUE.equals(config.attemptDirectPath());
  }

  public boolean isDirectPathXdsEnabled(GrpcChannelConfig config) {
    String directPathXdsEnv = envProvider.getenv(DIRECT_PATH_ENV_ENABLE_XDS);
    return Boolean.parseBoolean(directPathXdsEnv)
        || Boolean.TRUE.equals(config.attemptDirectPathXds());
  }

  private boolean isCredentialDirectPathCompatible(GrpcChannelConfig config) {
    Credentials credentials = config.credentials();
    if (credentials == null) {
      return false;
    }
    if (Boolean.TRUE.equals(config.allowNonDefaultServiceAccount())) {
      return true;
    }
    return credentials instanceof ComputeEngineCredentials;
  }

  private boolean canUseDirectPathWithUniverseDomain(String endpoint) {
    return endpoint.contains(Credentials.GOOGLE_DEFAULT_UNIVERSE);
  }
}
