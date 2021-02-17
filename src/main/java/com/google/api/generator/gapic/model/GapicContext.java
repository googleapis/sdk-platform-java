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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gapic.metadata.GapicMetadata;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@AutoValue
public abstract class GapicContext {
  // Keep a non-AutoValue reference to GapicMetadata, since we need to update
  // it iteratively as we generate client methods.
  private GapicMetadata gapicMetadata = defaultGapicMetadata();

  // Maps the message name (as it appears in the protobuf) to Messages.
  public abstract ImmutableMap<String, Message> messages();

  // Maps the resource type string to ResourceNames.
  public abstract ImmutableMap<String, ResourceName> resourceNames();

  public abstract ImmutableList<Service> services();

  // Ensures ordering for deterministic tests.
  public abstract ImmutableList<Service> mixinServices();

  public abstract ImmutableSet<ResourceName> helperResourceNames();

  public GapicMetadata gapicMetadata() {
    return gapicMetadata;
  }

  @Nullable
  public abstract GapicServiceConfig serviceConfig();

  @Nullable
  public abstract com.google.api.Service serviceYamlProto();

  public boolean hasServiceYamlProto() {
    return serviceYamlProto() != null;
  }

  public void updateGapicMetadata(GapicMetadata newMetadata) {
    gapicMetadata = newMetadata;
  }

  static GapicMetadata defaultGapicMetadata() {
    return GapicMetadata.newBuilder()
        .setSchema("1.0")
        .setComment(
            "This file maps proto services/RPCs to the corresponding library clients/methods")
        .setLanguage("java")
        .build();
  }

  public abstract Builder toBuilder();

  public static Builder builder() {
    return new AutoValue_GapicContext.Builder().setMixinServices(Collections.emptyList());
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setMessages(Map<String, Message> messages);

    public abstract Builder setResourceNames(Map<String, ResourceName> resourceNames);

    public abstract Builder setServices(List<Service> services);

    public abstract Builder setMixinServices(List<Service> mixinServices);

    public abstract Builder setHelperResourceNames(Set<ResourceName> helperResourceNames);

    public abstract Builder setServiceConfig(GapicServiceConfig serviceConfig);

    public abstract Builder setServiceYamlProto(com.google.api.Service serviceYamlProto);

    public abstract GapicContext build();
  }
}
