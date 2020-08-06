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
import javax.annotation.Nullable;

@AutoValue
public abstract class Field {
  public abstract String name();

  public abstract TypeNode type();

  public abstract boolean isRepeated();

  @Nullable
  public abstract ResourceReference resourceReference();

  public boolean hasResourceReference() {
    return type().equals(TypeNode.STRING) && resourceReference() != null;
  }

  public static Builder builder() {
    return new AutoValue_Field.Builder().setIsRepeated(false);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setName(String name);

    public abstract Builder setType(TypeNode type);

    public abstract Builder setIsRepeated(boolean isRepeated);

    public abstract Builder setResourceReference(ResourceReference resourceReference);

    public abstract Field build();
  }
}
