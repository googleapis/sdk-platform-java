package com.google.api.generator.engine.lexicon;

import com.google.common.collect.ImmutableList;
import java.util.regex.Pattern;

// TODO(miraleung): Add logical representations for each operator so we can use them in expression
// parsing.
public class Operator {
  private static final ImmutableList<String> OPERATORS =
      ImmutableList.of("+", "-", "*", "/", "%", "=", "!", "&", "|", "<", ">", "?", ":", "^");

  private static final Pattern OPERATOR_PATTERN =
      Pattern.compile("[\\+\\-\\*\\/%\\=\\!\\&\\|<>\\?\\:\\^]");

  public static boolean containsOperator(String str) {
    return OPERATOR_PATTERN.matcher(str).find();
  }
}
