package com.google.api.generator.engine.lexicon;

import java.util.regex.Pattern;

// TODO(miraleung): Refactor this out into int, bool, null, float, string literal classes.
public class Literal {
  private static final String BOOLEAN_TRUE = "true";
  private static final String BOOLEAN_FALSE = "false";
  private static final String NULL_VALUE = "null";

  private static final Pattern INTEGER_PATTERN = Pattern.compile("^[0-9]+$");
  private static final Pattern LONG_PATTERN = Pattern.compile("^[0-9]+[Ll]?$");
  private static final Pattern FLOAT_PATTERN =
      Pattern.compile("^[0-9]+([fF]|(\\.(([0-9]+[fF])|[fF])))?$");
  private static final Pattern DOUBLE_PATTERN =
      Pattern.compile("^[0-9]+(\\.[0-9]+)?(\\.?[eE]\\-?[0-9]+)$");

  public static boolean isBooleanLiteral(String str) {
    return str.equals(BOOLEAN_TRUE) || str.equals(BOOLEAN_FALSE);
  }

  public static boolean isIntegerLiteral(String str) {
    return INTEGER_PATTERN.matcher(str).matches();
  }

  public static boolean isLongLiteral(String str) {
    return LONG_PATTERN.matcher(str).matches();
  }

  public static boolean isFloatLiteral(String str) {
    return INTEGER_PATTERN.matcher(str).matches() || FLOAT_PATTERN.matcher(str).matches();
  }

  public static boolean isDoubleLiteral(String str) {
    return isFloatLiteral(str) || DOUBLE_PATTERN.matcher(str).matches();
  }

  public static boolean isNullLiteral(String str) {
    return str.equals(NULL_VALUE);
  }
}
