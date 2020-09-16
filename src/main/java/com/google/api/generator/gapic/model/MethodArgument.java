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

@AutoValue
public abstract class MethodArgument implements Comparable<MethodArgument> {
  public abstract String name();

  public abstract TypeNode type();

  // Records the path of nested types in descending order, excluding type() (which would have
  // appeared as the last element).
  public abstract ImmutableList<TypeNode> nestedTypes();

  // Returns true if this is a resource name helper tyep.
  public abstract boolean isResourceNameHelper();

  @Override
  public int compareTo(MethodArgument other) {
    int compareVal = type().compareTo(other.type());
    if (compareVal == 0) {
      compareVal = name().compareTo(other.name());
    }
    return compareVal;
  }

  public static Builder builder() {
    return new AutoValue_MethodArgument.Builder()
        .setNestedTypes(ImmutableList.of())
        .setIsResourceNameHelper(false);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setName(String name);

    public abstract Builder setType(TypeNode type);

    public abstract Builder setNestedTypes(List<TypeNode> nestedTypes);

    public abstract Builder setIsResourceNameHelper(boolean isResourceNameHelper);

    public abstract MethodArgument build();
  }
}
