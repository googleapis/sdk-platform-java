package com.google.api.generator.engine.xml;

import com.google.api.generator.engine.xml.XmlNodeVisitor;

public interface XmlNode {
  public void accept(XmlNodeVisitor visitor);

}
