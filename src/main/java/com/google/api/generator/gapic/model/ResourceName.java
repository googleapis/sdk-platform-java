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
import java.util.List;
import javax.annotation.Nullable;

@AutoValue
public abstract class ResourceName {
  // The original binding variable name.
  // This should be in lower_snake_case in the proto, and expected to be surrounded by braces.
  // Example: In projects/{project}/billingAccounts/billing_account, the variable name would be
  // "billing_account."
  public abstract String variableName();

  // The Java package where the resource name was defined.
  public abstract String pakkage();

  // The resource type.
  public abstract String resourceTypeString();

  // A list of patterns such as projects/{project}/locations/{location}/resources/{this_resource}.
  public abstract ImmutableList<String> patterns();

  // The message in which this resource was defined. Optional.
  // This is expected to be empty for file-level definitions.
  @Nullable
  public abstract String parentMessageName();

  public boolean hasParentMessageName() {
    return parentMessageName() != null;
  }

  public static Builder builder() {
    return new AutoValue_ResourceName.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setVariableName(String variableName);

    public abstract Builder setPakkage(String pakkage);

    public abstract Builder setResourceTypeString(String resourceTypeString);

    public abstract Builder setPatterns(List<String> patterns);

    public abstract Builder setParentMessageName(String parentMessageName);

    public abstract ResourceName build();
  }
}
