package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class StringObjectValue implements ObjectValue {

  @Override
  public TypeNode type() {
    return TypeNode.BOOLEAN;
  }

  public abstract String value();

  public static Builder builder() {
    return new AutoValue_StringObjectValue.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setValue(String value);

    public abstract StringObjectValue build();
  }
}
