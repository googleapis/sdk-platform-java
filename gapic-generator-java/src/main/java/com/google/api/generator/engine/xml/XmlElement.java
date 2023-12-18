package com.google.api.generator.engine.xml;

import com.google.api.generator.engine.ast.ArrayExpr;
import com.google.api.generator.engine.ast.AutoValue_ArrayExpr;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.List;

public abstract class XmlElement implements XmlNode {
  public abstract List<XmlNode> children();
  public abstract String tag();

  @Override
  public void accept(XmlNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static XmlElement.Builder builder() {
    return new AutoValue_XmlElement.Builder().setChildren(ImmutableList.of());
  }
  @AutoValue.Builder
  public abstract static class Builder {

    public abstract List<XmlNode> children();
    abstract XmlElement.Builder setChildren(List<XmlNode> children);
    public abstract XmlElement.Builder setTag(String tag);
    public XmlElement.Builder addChildElement(XmlNode node) {
      return setChildren(new ImmutableList.Builder<XmlNode>().addAll(children()).add(node).build());
    }
    public XmlElement.Builder clear() {
      return setChildren(ImmutableList.of());
    }

    public abstract XmlElement autoBuild();
    public XmlElement build() {
      XmlElement base = autoBuild();
      Preconditions.checkState(base.tag().matches("[a-zA-Z0-9]+"),
          "only alphanumeric characters are allowed as an element tag");
      return base;
    }
  }
}
