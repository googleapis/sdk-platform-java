package com.google.api.generator.engine.lexicon;

import java.util.regex.Pattern;

public class InvalidSymbol {
  private static final Pattern INVALID_SYMBOL_PATTERN = Pattern.compile("[#`~'\"\\\\]");

  public static boolean containsInvalidSymbol(String str) {
    return INVALID_SYMBOL_PATTERN.matcher(str).find();
  }
}
