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
import java.util.List;
import java.util.Map;

@AutoValue
public abstract class GapicContext {
  // Maps the message name (as it appears in the protobuf) to Messages.
  public abstract ImmutableMap<String, Message> messages();

  // Maps the resource type string to ResourceNames.
  public abstract ImmutableMap<String, ResourceName> resourceNames();

  public abstract ImmutableList<Service> services();

  public static Builder builder() {
    return new AutoValue_GapicContext.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setMessages(Map<String, Message> messages);

    public abstract Builder setResourceNames(Map<String, ResourceName> resourceNames);

    public abstract Builder setServices(List<Service> services);

    public abstract GapicContext build();
  }
}
