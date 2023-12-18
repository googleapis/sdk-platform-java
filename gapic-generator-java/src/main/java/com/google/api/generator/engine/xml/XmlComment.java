package com.google.api.generator.engine.xml;

public abstract class XmlComment implements XmlNode {
  public abstract String text();

  @Override
  public void accept(XmlNodeVisitor visitor) {
    visitor.visit(this);
  }

}
