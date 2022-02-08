package com.google.api.generator.gapic.model;

import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RegionTag {
  public abstract String apiShortName();

  public abstract String apiVersion();

  public abstract String serviceName();

  public abstract String rpcName();

  public abstract String overloadDisambiguation();

  public static Builder builder() {
    return new AutoValue_RegionTag.Builder()
        .setApiVersion("")
        .setApiShortName("")
        .setOverloadDisambiguation("");
  }

  abstract RegionTag.Builder toBuilder();

  public final RegionTag withApiVersion(String apiVersion) {
    return toBuilder().setApiVersion(apiVersion).build();
  }

  public final RegionTag withApiShortName(String apiShortName) {
    return toBuilder().setApiShortName(apiShortName).build();
  }

  public final RegionTag withOverloadDisambiguation(String overloadDisambiguation) {
    return toBuilder().setOverloadDisambiguation(overloadDisambiguation).build();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setApiVersion(String apiVersion);

    public abstract Builder setApiShortName(String apiShortName);

    public abstract Builder setServiceName(String serviceName);

    public abstract Builder setRpcName(String rpcName);

    public abstract Builder setOverloadDisambiguation(String overloadDisambiguation);

    abstract String apiVersion();

    abstract String apiShortName();

    abstract String serviceName();

    abstract String rpcName();

    abstract String overloadDisambiguation();

    abstract RegionTag autoBuild();

    public final RegionTag build() {
      setApiVersion(sanitizeAttributes(apiVersion()));
      setApiShortName(sanitizeAttributes(apiShortName()));
      setServiceName(sanitizeAttributes(serviceName()));
      setRpcName(sanitizeAttributes(rpcName()));
      setOverloadDisambiguation(sanitizeAttributes(overloadDisambiguation()));
      return autoBuild();
    }

    private final String sanitizeAttributes(String attribute) {
      return JavaStyle.toLowerCamelCase(attribute.replaceAll("[^a-zA-Z0-9]", ""));
    }
  }
}
