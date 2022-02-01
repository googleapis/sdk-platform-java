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

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.auto.value.AutoValue;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@AutoValue
public abstract class GapicClass {
  // TODO(miraleung): Add enum for resource name classes.
  public enum Kind {
    MAIN,
    STUB,
    TEST,
    PROTO
  };

  public abstract Kind kind();

  public abstract ClassDefinition classDefinition();

  public abstract Set<Sample> samples();

  public static GapicClass create(Kind kind, ClassDefinition classDefinition) {
    return builder().setKind(kind).setClassDefinition(classDefinition).build();
  }

  public static GapicClass create(
      Kind kind, ClassDefinition classDefinition, Set<Sample> samples) {
    return builder().setKind(kind).setClassDefinition(classDefinition).setSamples(samples).build();
  }

  static Builder builder() {
    return new AutoValue_GapicClass.Builder().setSamples(Collections.emptySet());
  }

  abstract Builder toBuilder();

  public final GapicClass setSamples(Set<Sample> samples) {
    return toBuilder().setSamples(samples).build();
  }

  @AutoValue.Builder
  abstract static class Builder {
    abstract Builder setKind(Kind kind);

    abstract Builder setClassDefinition(ClassDefinition classDefinition);

    abstract Builder setSamples(Set<Sample> samples);

    abstract GapicClass build();
  }
}
