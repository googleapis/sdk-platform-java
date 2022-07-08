/**
 * Copyright 2022 Google LLC
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ClazzValue implements ObjectValue {
  @Override
  public abstract TypeNode type();

  @Override
  public abstract String value();

  public static Builder builder() {
    return new AutoValue_ClazzValue.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setType(TypeNode type);

    abstract Builder setValue(String value);

    public abstract TypeNode type();

    abstract ClazzValue autoBuild();

    public ClazzValue build() {
      setValue(type().reference().name() + ".class");
      return autoBuild();
    }
  }
}
