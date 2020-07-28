package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;

@AutoValue
public abstract class ThisObjectValue implements ObjectValue {
  private static final String THIS_VALUE = "this";
  public abstract TypeNode type();

  @Override
  public String value() {return THIS_VALUE;}

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
          TypeNode.isReferenceType(thisObjectValue.type()),
          "this can only refer to object types"
      );
      Preconditions.checkState(
          !TypeNode.isJavaLang(thisObjectValue.type()),
          "The class type should belongs to custom object"
      );
      return thisObjectValue;
    }
  }
}
