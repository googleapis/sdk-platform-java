package com.google.api.generator.engine.lexicon;

import java.util.regex.Pattern;

// TODO(miraleung): Add logical representations for each operator so we can use them in expression
// parsing.
public class Operator {
  private static final Pattern OPERATOR_PATTERN =
      Pattern.compile("[\\+\\-\\*\\/%\\=\\!\\&\\|<>\\?\\:\\^]");

  public static boolean containsOperator(String str) {
    return OPERATOR_PATTERN.matcher(str).find();
  }
}
