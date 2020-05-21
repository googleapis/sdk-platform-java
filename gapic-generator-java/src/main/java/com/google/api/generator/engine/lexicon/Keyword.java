package com.google.api.generator.engine.lexicon;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

public class Keyword {
  @VisibleForTesting
  static final ImmutableList<String> KEYWORDS =
      ImmutableList.of(
          "abstract",
          "continue",
          "for",
          "new",
          "switch",
          "assert",
          "default",
          "if",
          "package",
          "synchronized",
          "boolean",
          "do",
          "goto",
          "private",
          "this",
          "break",
          "double",
          "implements",
          "protected",
          "throw",
          "byte",
          "else",
          "import",
          "public",
          "throws",
          "case",
          "enum",
          "instanceof",
          "return",
          "transient",
          "catch",
          "extends",
          "int",
          "short",
          "try",
          "char",
          "final",
          "interface",
          "static",
          "void",
          "class",
          "finally",
          "long",
          "strictfp",
          "volatile",
          "const",
          "float",
          "native",
          "super",
          "while");

  public static boolean isKeyword(String s) {
    return KEYWORDS.contains(s);
  }
}
