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

@AutoValue
public abstract class ResourceReference {

  public abstract String resourceTypeString();

  public abstract boolean isChildType();

  public static ResourceReference withType(String resourceTypeString) {
    return builder().setResourceTypeString(resourceTypeString).setIsChildType(false).build();
  }

  public static ResourceReference withChildType(String resourceTypeString) {
    return builder().setResourceTypeString(resourceTypeString).setIsChildType(true).build();
  }

  // Private.
  static Builder builder() {
    return new AutoValue_ResourceReference.Builder();
  }

  // Private.
  @AutoValue.Builder
  abstract static class Builder {
    abstract Builder setResourceTypeString(String resourceTypeString);

    abstract Builder setIsChildType(boolean isChildType);

    abstract ResourceReference build();
  }
}
