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
import com.google.common.collect.ImmutableList;
import java.util.List;

@AutoValue
public abstract class ConcreteReference implements Reference {
  private static final String COMMA = ", ";
  private static final String DOT = ".";
  private static final String LEFT_ANGLE = "<";
  private static final String RIGHT_ANGLE = ">";
  private static final String QUESTION_MARK = "?";

  // Private.
  abstract Class clazz();

  @Override
  public abstract ImmutableList<Reference> generics();

  @Override
  public abstract boolean isStaticImport();

  @Override
  public String name() {
    StringBuilder sb = new StringBuilder();
    if (this.equals(TypeNode.WILDCARD_REFERENCE)) {
      sb.append(QUESTION_MARK);
    } else {
      if (hasEnclosingClass() && !isStaticImport()) {
        sb.append(clazz().getEnclosingClass().getSimpleName());
        sb.append(DOT);
      }
      sb.append(clazz().getSimpleName());
    }
    if (!generics().isEmpty()) {
      sb.append(LEFT_ANGLE);
      for (int i = 0; i < generics().size(); i++) {
        Reference r = generics().get(i);
        sb.append(r.name());
        if (i < generics().size() - 1) {
          sb.append(COMMA);
        }
      }
      sb.append(RIGHT_ANGLE);
    }
    return sb.toString();
  }

  @Override
  public String pakkage() {
    return clazz().getPackage().getName();
  }

  @Override
  public String enclosingClassName() {
    if (!hasEnclosingClass()) {
      return null;
    }
    return clazz().getEnclosingClass().getSimpleName();
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

  @Override
  public Reference copyAndSetGenerics(List<Reference> generics) {
    return toBuilder().setGenerics(generics).build();
  }

  public static ConcreteReference withClazz(Class clazz) {
    return builder().setClazz(clazz).build();
  }

  public static Builder builder() {
    return new AutoValue_ConcreteReference.Builder()
        .setGenerics(ImmutableList.of())
        .setIsStaticImport(false);
  }

  // Private.
  abstract Builder toBuilder();

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setClazz(Class clazz);

    public abstract Builder setGenerics(List<Reference> clazzes);

    public abstract Builder setIsStaticImport(boolean isStaticImport);

    public abstract ConcreteReference autoBuild();

    // Private.
    abstract Class clazz();

    abstract boolean isStaticImport();

    public ConcreteReference build() {
      setIsStaticImport(clazz().getEnclosingClass() != null && isStaticImport());
      return autoBuild();
    }
  }
}
