// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.gapic.model;

import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;

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
      setApiVersion(sanitizeVersion(apiVersion()));
      setApiShortName(sanitizeAttributes(apiShortName()));
      setServiceName(sanitizeAttributes(serviceName()));
      setRpcName(sanitizeAttributes(rpcName()));
      setOverloadDisambiguation(sanitizeAttributes(overloadDisambiguation()));
      return autoBuild();
    }

    private final String sanitizeAttributes(String attribute) {
      return JavaStyle.toLowerCamelCase(attribute.replaceAll("[^a-zA-Z0-9]", ""));
    }

    private final String sanitizeVersion(String version) {
      return JavaStyle.toLowerCamelCase(version.replaceAll("[^a-zA-Z0-9.]", ""));
    }
  }

  public enum RegionTagRegion {
    START,
    END
  }

  public String generate() {
    Preconditions.checkState(!apiShortName().isEmpty(), "apiShortName can't be empty");
    Preconditions.checkState(!serviceName().isEmpty(), "serviceName can't be empty");
    Preconditions.checkState(!rpcName().isEmpty(), "rpcName can't be empty");

    String rt = apiShortName() + "_";
    if (!apiVersion().isEmpty()) {
      rt = rt + apiVersion() + "_";
    }
    rt = rt + "generated_" + serviceName() + "_" + rpcName();
    if (!overloadDisambiguation().isEmpty()) {
      rt = rt + "_" + overloadDisambiguation();
    }

    return rt.toLowerCase();
  }

  public static CommentStatement generateTag(
      RegionTagRegion regionTagRegion, String regionTagContent) {
    return CommentStatement.withComment(
        LineComment.withComment("[" + regionTagRegion.name() + " " + regionTagContent + "]"));
  }
}
