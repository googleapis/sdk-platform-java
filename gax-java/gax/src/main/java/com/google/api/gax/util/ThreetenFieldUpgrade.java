package com.google.api.gax.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to mark autovalue class method that refer to a deprecated threeten field Used in
 * testing
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ThreetenFieldUpgrade {
  enum FieldRole {
    JAVA_TIME_GETTER,
    THREETEN_GETTER,
    JAVA_TIME_SETTER,
    THREETEN_SETTER
  }

  FieldRole role();

  String key();
}
