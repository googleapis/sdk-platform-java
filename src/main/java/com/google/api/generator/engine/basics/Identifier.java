// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.engine.basics;

import com.google.api.generator.engine.ast.AstNode;
import com.google.api.generator.engine.lexicon.InvalidSymbol;
import com.google.api.generator.engine.lexicon.Keyword;
import com.google.api.generator.engine.lexicon.Literal;
import com.google.api.generator.engine.lexicon.Operator;
import com.google.api.generator.engine.lexicon.Separator;
import java.util.regex.Pattern;
import javax.validation.constraints.NotNull;

public class Identifier implements AstNode {
  static class InvalidIdentifierException extends RuntimeException {
    public InvalidIdentifierException(String errorMessage) {
      super(errorMessage);
    }
  }

  private static final Pattern NUMERIC_PATTERN = Pattern.compile("^[0-9]");

  private final String identifierName;

  private Identifier(String identifierName) {
    this.identifierName = identifierName;
  }

  public static Identifier create(@NotNull String identifierName)
      throws InvalidIdentifierException {
    if (identifierName.isEmpty()) {
      throw new InvalidIdentifierException("Name cannot be empty.");
    }

    if (NUMERIC_PATTERN.matcher(identifierName.substring(0, 1)).matches()) {
      throw new InvalidIdentifierException("Name cannot begin with a number.");
    }

    if (Literal.isLiteral(identifierName)) {
      throw new InvalidIdentifierException(
          String.format("Name %s cannot be a literal", identifierName));
    }

    if (InvalidSymbol.containsInvalidSymbol(identifierName)
        || Operator.containsOperator(identifierName)
        || Separator.containsSeparator(identifierName)) {
      throw new InvalidIdentifierException(
          String.format("Name %s cannot contain non-alphanumeric characters", identifierName));
    }

    if (Keyword.isKeyword(identifierName)) {
      throw new InvalidIdentifierException(
          String.format("Name %s cannot be a keyword.", identifierName));
    }

    return new Identifier(identifierName);
  }

  public String name() {
    return identifierName;
  }

  @Override
  public String write() {
    return identifierName;
  }

  @Override
  public String toString() {
    return identifierName;
  }
}
