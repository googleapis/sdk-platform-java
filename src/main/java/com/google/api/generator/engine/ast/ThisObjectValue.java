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
import com.google.common.base.Preconditions;

@AutoValue
public abstract class ThisObjectValue implements ObjectValue {
  private static final String THIS_VALUE = "this";
<<<<<<< HEAD
  private static final String PKG_JAVA_LANG = "java.lang";
=======
>>>>>>> 3b77f643cecb5e6b16a513c1721f6edc3a299b1c

  public abstract TypeNode type();

  @Override
  public String value() {
    return THIS_VALUE;
  }

  public static ThisObjectValue withType(TypeNode type) {
    return builder().setType(type).build();
  }

  private static Builder builder() {
    return new AutoValue_ThisObjectValue.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setType(TypeNode type);

    public abstract ThisObjectValue autoBuild();

    public ThisObjectValue build() {
      ThisObjectValue thisObjectValue = autoBuild();
      Preconditions.checkState(
          TypeNode.isReferenceType(thisObjectValue.type()), "this can only refer to object types");
      Preconditions.checkState(
<<<<<<< HEAD
          !thisObjectValue.type().reference().isFromPackage(PKG_JAVA_LANG),
=======
          !TypeNode.isJavaLangObject(thisObjectValue.type()),
>>>>>>> 3b77f643cecb5e6b16a513c1721f6edc3a299b1c
          "The class type should belongs to custom object");
      return thisObjectValue;
    }
  }
}
