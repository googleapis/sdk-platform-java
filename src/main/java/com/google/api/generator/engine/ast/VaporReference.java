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
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

@AutoValue
public abstract class VaporReference implements Reference {
  private static final String DOT = ".";
  private static final String LEFT_ANGLE = "<";
  private static final String RIGHT_ANGLE = ">";
  private static final String COMMA = ", ";

  @Override
  public abstract ImmutableList<Reference> generics();

  @Override
  public abstract String name();

  @Override
  public abstract String pakkage();

  @Nullable
  @Override
  public abstract String enclosingClassName();

  @Nullable
  @Override
  public Reference wildcardUpperBound() {
    return null;
  }

  @Override
  public String fullName() {
    // TODO(unsupported): Nested classes with depth greater than 1.
    if (hasEnclosingClass()) {
      return String.format("%s.%s.%s", pakkage(), enclosingClassName(), plainName());
    }
    return String.format("%s.%s", pakkage(), plainName());
  }

  @Override
  public abstract boolean isStaticImport();

  @Override
  public boolean hasEnclosingClass() {
    return !Strings.isNullOrEmpty(enclosingClassName());
  }

  @Override
  public boolean isFromPackage(String pkg) {
    return pakkage().equals(pkg);
  }

  @Override
  public boolean isSupertypeOrEquals(Reference other) {
    // Not handling more complex cases for VaporReference.
    return equals(other);
  }

  @Override
  public boolean isAssignableFrom(Reference other) {
    // Not handling this for VaporReference.
    return false;
  }

  @Override
  public boolean isWildcard() {
    return false;
  }

  abstract String plainName();

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof VaporReference)) {
      return false;
    }

    VaporReference ref = (VaporReference) o;
    return pakkage().equals(ref.pakkage())
        && name().equals(ref.name())
        && generics().equals(ref.generics())
        && Objects.equals(enclosingClassName(), ref.enclosingClassName());
  }

  @Override
  public int hashCode() {
    int hash = 17 * pakkage().hashCode() + 19 * name().hashCode() + 23 * generics().hashCode();
    if (!Strings.isNullOrEmpty(enclosingClassName())) {
      hash += 29 * enclosingClassName().hashCode();
    }
    return hash;
  }

  @Override
  public Reference copyAndSetGenerics(List<Reference> generics) {
    return toBuilder().setGenerics(generics).build();
  }

  public static Builder builder() {
    return new AutoValue_VaporReference.Builder()
        .setGenerics(ImmutableList.of())
        .setIsStaticImport(false);
  }

  // Private.
  abstract Builder toBuilder();

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setName(String name);

    public abstract Builder setPakkage(String pakkage);

    public abstract Builder setGenerics(List<Reference> clazzes);

    public abstract Builder setEnclosingClassName(String enclosingClassName);

    public abstract Builder setIsStaticImport(boolean isStaticImport);

    // Private.
    abstract Builder setPlainName(String plainName);

    abstract String name();

    abstract ImmutableList<Reference> generics();

    @Nullable
    abstract String enclosingClassName();

    abstract boolean isStaticImport();

    abstract VaporReference autoBuild();

    public VaporReference build() {
      // Validate the name.
      IdentifierNode.builder().setName(name()).build();
      // No exception thrown, so we can proceed.

      setPlainName(name());

      setIsStaticImport(enclosingClassName() != null && isStaticImport());

      StringBuilder sb = new StringBuilder();
      if (enclosingClassName() != null && !isStaticImport()) {
        sb.append(enclosingClassName());
        sb.append(DOT);
      }

      sb.append(name());
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
      setName(sb.toString());

      return autoBuild();
    }
  }
}
