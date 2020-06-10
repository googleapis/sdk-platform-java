package com.google.api.generator.engine.basics;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Variable {
  public abstract Identifier identifier();

  public abstract Type type();

  public static Builder builder() {
    return new AutoValue_Variable.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setType(Type type);

    public abstract Builder setIdentifier(Identifier identifier);

    public abstract Variable build();
  }
}
