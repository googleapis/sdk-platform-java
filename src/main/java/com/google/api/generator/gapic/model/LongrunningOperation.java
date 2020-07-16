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

@AutoValue
public abstract class LongrunningOperation {
  public abstract TypeNode responseType();

  public abstract TypeNode metadataType();

  public static LongrunningOperation withTypes(TypeNode responseType, TypeNode metadataType) {
    return builder().setResponseType(responseType).setMetadataType(metadataType).build();
  }

  // Private.
  static Builder builder() {
    return new AutoValue_LongrunningOperation.Builder();
  }

  // Private.
  @AutoValue.Builder
  abstract static class Builder {
    abstract Builder setResponseType(TypeNode responseType);

    abstract Builder setMetadataType(TypeNode metadataType);

    abstract LongrunningOperation build();
  }
}
