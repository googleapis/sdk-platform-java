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

package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;
import org.apache.commons.lang3.StringEscapeUtils;

@AutoValue
public abstract class StringObjectValue implements ObjectValue {

  @Override
  public TypeNode type() {
    return TypeNode.STRING;
  }

  public abstract String value();

  public static Builder builder() {
    return new AutoValue_StringObjectValue.Builder();
  }

  public static StringObjectValue withValue(String value) {
    return builder().setValue(value).build();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setValue(String value);

    public abstract StringObjectValue autobuild();

    public StringObjectValue build() {
      String value = autobuild().value();
      String change = "\"" + StringEscapeUtils.escapeJava(value) + "\"";
      setValue(change);
      return autobuild();
    }
  }

  @Override
  public String toString() {
    return value();
  }
}
