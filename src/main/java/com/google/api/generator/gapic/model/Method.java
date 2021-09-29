// Copyright 2020 Google LLC
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

import com.google.api.generator.engine.ast.TypeNode;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;

@AutoValue
public abstract class Method {
  public enum Stream {
    NONE,
    CLIENT,
    SERVER,
    BIDI
  };

  public abstract String name();

  public abstract Stream stream();

  public abstract TypeNode inputType();

  public abstract TypeNode outputType();

  public abstract boolean isBatching();

  public boolean isPaged() {
    return pageSizeFieldName() != null;
  }

  @Nullable
  public abstract String pageSizeFieldName();

  public abstract boolean isDeprecated();

  @Nullable
  public abstract LongrunningOperation lro();

  @Nullable
  public abstract String description();

  // Example: google.iam.v1.IAMPolicy.
  @Nullable
  public abstract String mixedInApiName();

  // HttpBinding pending dotted reference support.
  @Nullable
  public abstract HttpBindings httpBindings();

  // Example from Expand in echo.proto: Thet TypeNodes that map to
  // [["content", "error"], ["content", "error", "info"]].
  public abstract ImmutableList<List<MethodArgument>> methodSignatures();

  public boolean hasLro() {
    return lro() != null;
  }

  public boolean hasDescription() {
    return description() != null;
  }

  public boolean hasHttpBindings() {
    return httpBindings() != null && !httpBindings().pathParameters().isEmpty();
  }

  public boolean isMixin() {
    return mixedInApiName() != null;
  }

  public abstract Builder toBuilder();

  public static Builder builder() {
    return new AutoValue_Method.Builder()
        .setStream(Stream.NONE)
        .setMethodSignatures(ImmutableList.of())
        .setIsBatching(false)
        .setIsDeprecated(false);
  }

  public static Stream toStream(boolean isClientStreaming, boolean isServerStreaming) {
    if (!isClientStreaming && !isServerStreaming) {
      return Stream.NONE;
    }
    if (isClientStreaming && isServerStreaming) {
      return Stream.BIDI;
    }
    if (isClientStreaming) {
      return Stream.CLIENT;
    }
    return Stream.SERVER;
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setName(String name);

    public abstract Builder setInputType(TypeNode inputType);

    public abstract Builder setOutputType(TypeNode outputType);

    public abstract Builder setStream(Stream stream);

    public abstract Builder setLro(LongrunningOperation lro);

    public abstract Builder setDescription(String description);

    public abstract Builder setMixedInApiName(String mixedInApiName);

    public abstract Builder setHttpBindings(HttpBindings httpBindings);

    public abstract Builder setMethodSignatures(List<List<MethodArgument>> methodSignature);

    public abstract Builder setIsBatching(boolean isBatching);

    public abstract Builder setPageSizeFieldName(String pagedFieldName);

    public abstract Builder setIsDeprecated(boolean isDeprecated);

    public abstract Method build();
  }
}
