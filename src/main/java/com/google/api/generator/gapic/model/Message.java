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
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AutoValue
public abstract class Message {
  public abstract String name();

  // TODO(unsupported): oneof fields are parsed as separate ones because field flattening refers to
  // a specific field.
  public abstract ImmutableList<Field> fields();

  public abstract TypeNode type();

  public abstract ImmutableMap<String, Field> fieldMap();

  public static Builder builder() {
    return new AutoValue_Message.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setName(String name);

    public abstract Builder setFields(List<Field> fields);

    public abstract Builder setType(TypeNode type);

    abstract Builder setFieldMap(Map<String, Field> fieldMap);

    abstract ImmutableList<Field> fields();

    abstract Message autoBuild();

    public Message build() {
      setFieldMap(fields().stream().collect(Collectors.toMap(f -> f.name(), f -> f)));
      return autoBuild();
    }
  }
}
