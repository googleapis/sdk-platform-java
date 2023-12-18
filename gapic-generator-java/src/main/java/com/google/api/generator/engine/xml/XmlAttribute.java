package com.google.api.generator.engine.xml;

import com.google.auto.value.AutoValue;
import jdk.internal.joptsimple.internal.Strings;

public abstract class XmlAttribute implements XmlNode {
  public abstract String name();
  public abstract String value();

  @Override
  public void accept(XmlNodeVisitor visitor) {

    visitor.visit(this);
  }

  public static XmlAttribute.Builder builder() {
    return new AutoValue_XmlAttribute.Builder()
        .setName(Strings.EMPTY)
        .setValue(Strings.EMPTY);
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract XmlAttribute.Builder setName(String name);
    public abstract XmlAttribute.Builder setValue(String value);
    abstract XmlText autoBuild();
    public XmlText build() {
      XmlText base = autoBuild();
  }
}
