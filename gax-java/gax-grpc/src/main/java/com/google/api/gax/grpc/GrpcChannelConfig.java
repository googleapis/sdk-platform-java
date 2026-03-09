package com.google.api.gax.grpc;

import com.google.api.core.InternalApi;
import com.google.auth.Credentials;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import javax.annotation.Nullable;

@InternalApi
@AutoValue
abstract class GrpcChannelConfig {

  abstract String endpoint();

  @Nullable
  abstract String mtlsEndpoint();

  @Nullable
  abstract Credentials credentials();

  abstract boolean useS2A();

  @Nullable
  abstract Boolean attemptDirectPath();

  @Nullable
  abstract Boolean attemptDirectPathXds();

  @Nullable
  abstract Boolean allowNonDefaultServiceAccount();

  @Nullable
  abstract ImmutableMap<String, ?> directPathServiceConfig();

  abstract ImmutableList<InstantiatingGrpcChannelProvider.HardBoundTokenTypes>
      allowedHardBoundTokenTypes();

  static Builder builder() {
    return new AutoValue_GrpcChannelConfig.Builder();
  }

  @AutoValue.Builder
  abstract static class Builder {
    abstract Builder setEndpoint(String endpoint);

    abstract Builder setMtlsEndpoint(String mtlsEndpoint);

    abstract Builder setCredentials(Credentials credentials);

    abstract Builder setUseS2A(boolean useS2A);

    abstract Builder setAttemptDirectPath(Boolean attemptDirectPath);

    abstract Builder setAttemptDirectPathXds(Boolean attemptDirectPathXds);

    abstract Builder setAllowNonDefaultServiceAccount(Boolean allowNonDefaultServiceAccount);

    abstract Builder setDirectPathServiceConfig(ImmutableMap<String, ?> directPathServiceConfig);

    abstract Builder setAllowedHardBoundTokenTypes(
        List<InstantiatingGrpcChannelProvider.HardBoundTokenTypes> types);

    abstract GrpcChannelConfig build();
  }
}
