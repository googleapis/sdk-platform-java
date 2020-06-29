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
import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class ConcreteReference implements Reference {
  @Override
  public abstract List<Reference> generics();

  @Override
  public String name() {
    StringBuilder sb = new StringBuilder();
    sb.append(clazz().getSimpleName());
    if (!generics().isEmpty()) {
      sb.append("<");
      for (int i = 0; i < generics().size(); i++) {
        Reference r = generics().get(i);
        sb.append(r.name());
        if (i < generics().size() - 1) {
          sb.append(", ");
        }
      }
      sb.append(">");
    }
    return sb.toString();
  }

  @Override
  public String fullName() {
    return clazz().getCanonicalName();
  }

  @Override
  public boolean hasEnclosingClass() {
    return clazz().getEnclosingClass() != null;
  }

  @Override
  public boolean isFromPackage(String pkg) {
    return clazz().getPackage().getName().equals(pkg);
  }

  @Override
  public boolean isSupertypeOrEquals(Reference other) {
    if (generics().size() != other.generics().size()) {
      return false;
    }

    if (!isAssignableFrom(other)) {
      return false;
    }

    for (int i = 0; i < generics().size(); i++) {
      Reference thisGeneric = generics().get(i);
      Reference otherGeneric = other.generics().get(i);
      if (!thisGeneric.isSupertypeOrEquals(otherGeneric)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean isAssignableFrom(Reference other) {
    if (!(other instanceof ConcreteReference)) {
      return false;
    }
    return clazz().isAssignableFrom(((ConcreteReference) other).clazz());
  }

  public static ConcreteReference withClazz(Class clazz) {
    return builder().setClazz(clazz).build();
  }

  // Private.
  abstract Class clazz();

  public static Builder builder() {
    return new AutoValue_ConcreteReference.Builder().setGenerics(Collections.emptyList());
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ConcreteReference)) {
      return false;
    }

    ConcreteReference ref = (ConcreteReference) o;
    return clazz().equals(ref.clazz()) && generics().equals(ref.generics());
  }

  @Override
  public int hashCode() {
    return 17 * clazz().hashCode() + 31 * generics().hashCode();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setClazz(Class clazz);

    public abstract Builder setGenerics(List<Reference> clazzes);

    public abstract ConcreteReference build();
  }
}
