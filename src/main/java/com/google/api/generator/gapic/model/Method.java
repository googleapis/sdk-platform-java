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
public abstract class Method {
  public abstract String name();

  public abstract String inputTypeName();

  public abstract String outputTypeName();

  // TODO(miraleung): Parse annotations, comments.

  public static Builder builder() {
    return new AutoValue_Method.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setName(String name);

    public abstract Builder setInputTypeName(String name);

    public abstract Builder setOutputTypeName(String name);

    public abstract Method build();
  }
}
