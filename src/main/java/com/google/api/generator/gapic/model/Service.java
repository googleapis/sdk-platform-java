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
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;

@AutoValue
public abstract class Service {
  public abstract String name();

  public abstract String defaultHost();

  public abstract ImmutableList<String> oauthScopes();

  public abstract String pakkage();

  public abstract String protoPakkage();

  // For compatibility with other protoc-plugin code generators, e.g. gRPC.
  public abstract String originalJavaPackage();

  // New Java class name as defined in gapic.yaml's language settings.
  public abstract String overriddenName();

  public abstract boolean isDeprecated();

  public abstract ImmutableList<Method> methods();

  @Nullable
  public abstract String description();

  public boolean hasDescription() {
    return !Strings.isNullOrEmpty(description());
  }

  public Method operationPollingMethod() {
    for (Method method : methods()) {
      if (method.isOperationPollingMethod()) {
        return method;
      }
    }
    return null;
  }

  public TypeNode operationServiceStubType() {
    for (Method method : methods()) {
      if (method.hasLro() && method.lro().operationServiceStubType() != null) {
        // All methods within the same service must have the same operationServiceTypeName if
        // present
        return method.lro().operationServiceStubType();
      }
    }
    return null;
  }

  public TypeNode operationType() {
    for (Method method : methods()) {
      if (method.hasLro() && method.lro().operationServiceStubType() != null) {
        return method.outputType();
      }
    }
    return null;
  }

  public boolean hasLroMethods() {
    for (Method method : methods()) {
      if (method.hasLro()) {
        return true;
      }
    }
    return false;
  }

  public boolean hasStandardLroMethods() {
    for (Method method : methods()) {
      if (method.hasLro() && method.lro().operationServiceStubType() == null) {
        return true;
      }
    }
    return false;
  }

  public abstract Builder toBuilder();

  public static Builder builder() {
    return new AutoValue_Service.Builder().setMethods(ImmutableList.of()).setIsDeprecated(false);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setName(String name);

    public abstract Builder setOverriddenName(String overriddenName);

    public abstract Builder setDefaultHost(String defaultHost);

    public abstract Builder setOauthScopes(List<String> oauthScopes);

    public abstract Builder setPakkage(String pakkage);

    public abstract Builder setProtoPakkage(String pakkage);

    public abstract Builder setOriginalJavaPackage(String originalJavaPackage);

    public abstract Builder setIsDeprecated(boolean isDeprecated);

    public abstract Builder setMethods(List<Method> methods);

    public abstract Builder setDescription(String description);

    public abstract Service build();
  }
}
