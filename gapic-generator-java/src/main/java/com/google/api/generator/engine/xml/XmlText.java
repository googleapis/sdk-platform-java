package com.google.api.generator.engine.xml;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import jdk.internal.joptsimple.internal.Strings;

public abstract class XmlText implements XmlNode {

  public abstract String text();

  @Override
  public void accept(XmlNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static XmlText.Builder builder() {
    return new AutoValue_XmlText.Builder()
        .setText(Strings.EMPTY);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract XmlText.Builder setText(String text);
    abstract XmlText autoBuild();
    public XmlText build() {
      XmlText base = autoBuild();
      Preconditions.checkNotNull(base.text());
      return base;
    }
  }
}
