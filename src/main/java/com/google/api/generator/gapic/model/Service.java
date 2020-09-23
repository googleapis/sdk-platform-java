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

  public abstract ImmutableList<Method> methods();

  @Nullable
  public abstract String description();

  public boolean hasDescription() {
    return !Strings.isNullOrEmpty(description());
  }

  public static Builder builder() {
    return new AutoValue_Service.Builder().setMethods(ImmutableList.of());
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setName(String name);

    public abstract Builder setDefaultHost(String defaultHost);

    public abstract Builder setOauthScopes(List<String> oauthScopes);

    public abstract Builder setPakkage(String pakkage);

    public abstract Builder setProtoPakkage(String pakkage);

    public abstract Builder setMethods(List<Method> methods);

    public abstract Builder setDescription(String description);

    public abstract Service build();
  }
}
