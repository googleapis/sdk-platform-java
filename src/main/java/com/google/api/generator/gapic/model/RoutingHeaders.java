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

import com.google.auto.value.AutoValue;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import java.util.List;

@AutoValue
public abstract class RoutingHeaders {

  public abstract ImmutableList<RoutingHeader> routingHeadersList();

  public static Builder builder() {
    return new AutoValue_RoutingHeaders.Builder().setRoutingHeadersList(ImmutableList.of());
  }

  @AutoValue
  public abstract static class RoutingHeader {

    public abstract String fieldName();

    public abstract String name();

    public abstract String pattern();

    public static RoutingHeaders.RoutingHeader create(String field, String name, String pattern) {
      return new AutoValue_RoutingHeaders_RoutingHeader(field, name, pattern);
    }

    public List<String> getDescendantFieldNames() {
      return Splitter.on(".").splitToList(fieldName());
    }
  }

  @AutoValue.Builder
  public abstract static class Builder {
    abstract ImmutableList.Builder<RoutingHeader> routingHeadersListBuilder();

    public final Builder addRoutingHeader(RoutingHeader routingHeader) {
      routingHeadersListBuilder().add(routingHeader);
      return this;
    }

    public abstract Builder setRoutingHeadersList(ImmutableList<RoutingHeader> routingHeadersList);

    public abstract RoutingHeaders build();
  }
}
