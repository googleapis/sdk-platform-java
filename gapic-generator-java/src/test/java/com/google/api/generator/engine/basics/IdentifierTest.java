package com.google.api.generator.engine.basics;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.api.generator.engine.basics.Identifier.InvalidIdentifierException;
import org.junit.Test;

public class IdentifierTest {
  @Test
  public void createIdentifier_basic() {
    assertValidIdentifier("foobar");
    assertValidIdentifier("x");
    assertValidIdentifier("afalse");
    assertValidIdentifier("a123");
    assertValidIdentifier("False");
    assertValidIdentifier("Object");
    assertValidIdentifier("a_b_c");
    assertValidIdentifier("a_b_____");
  }

  @Test
  public void createIdentifier_nameHasLiteral() {
    assertValidIdentifier("a123L");
    assertValidIdentifier("a10e3");
    assertValidIdentifier("anull");

    assertInvalidIdentifier("123L");
    assertInvalidIdentifier("1.f");
    assertInvalidIdentifier("12345");
    assertInvalidIdentifier("null");
    assertInvalidIdentifier("1e-2");
    assertInvalidIdentifier("1e2");
    assertInvalidIdentifier("false");
    assertInvalidIdentifier("1.00");
    assertInvalidIdentifier(".01");
  }

  @Test
  public void createIdentifier_namdHasInvalidSymbols() {
    assertInvalidIdentifier("a.b");
    assertInvalidIdentifier("a,b");
    assertInvalidIdentifier("a-b");
    assertInvalidIdentifier("a+b");
    assertInvalidIdentifier("-ab");
    assertInvalidIdentifier("-ab");
    assertInvalidIdentifier("[ab]");
    assertInvalidIdentifier("ab)");
    assertInvalidIdentifier("ab=");
    assertInvalidIdentifier("ab%");
    assertInvalidIdentifier("a/b");
    assertInvalidIdentifier("}ab");
    assertInvalidIdentifier("ab|");
    assertInvalidIdentifier("a&b");
    assertInvalidIdentifier("a^b");
    assertInvalidIdentifier("a$b");
    assertInvalidIdentifier("a$b@");
    assertInvalidIdentifier("!abfoo");
    assertInvalidIdentifier("ab'foo");
    assertInvalidIdentifier("ab\"foo");
    assertInvalidIdentifier("abfoo;");
    assertInvalidIdentifier(">abfoo;");
    assertInvalidIdentifier("abfo<o");
    assertInvalidIdentifier("abf?oo");
    assertInvalidIdentifier("abfoo:");
    assertInvalidIdentifier("abfoo\\");
  }

  @Test
  public void createIdentifier_nameHasKeyword() {
    assertValidIdentifier("aclass");

    // Random sampling of keywords.
    assertInvalidIdentifier("class");
    assertInvalidIdentifier("interface");
    assertInvalidIdentifier("abstract");
    assertInvalidIdentifier("float");
    assertInvalidIdentifier("boolean");
    assertInvalidIdentifier("int");
    assertInvalidIdentifier("switch");
    assertInvalidIdentifier("if");
    assertInvalidIdentifier("throws");
    assertInvalidIdentifier("catch");
    assertInvalidIdentifier("void");
    assertInvalidIdentifier("private");
    assertInvalidIdentifier("import");
    assertInvalidIdentifier("return");
    assertInvalidIdentifier("final");
    assertInvalidIdentifier("extends");
    assertInvalidIdentifier("implements");
  }

  private static void assertInvalidIdentifier(String idName) {
    assertThrows(
        InvalidIdentifierException.class,
        () -> {
          Identifier.create(idName);
        });
  }

  private static void assertValidIdentifier(String idName) {
    assertThat(Identifier.create(idName).name()).isEqualTo(idName);
  }
}
