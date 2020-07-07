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

package com.google.api.generator.engine.ast;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;

public class JavaEscaper {
  private static final SpecialSequenceEscaper specialSequenceEscaper = new SpecialSequenceEscaper();

  private static class SpecialSequenceEscaper extends Escaper {
    @Override
    public String escape(String sourceString) {
      Escaper escaper =
          Escapers.builder()
              .addEscape('\t', "\\t")
              .addEscape('\b', "\\b")
              .addEscape('\n', "\\n")
              .addEscape('\r', "\\r")
              .addEscape('\f', "\\f")
              .addEscape('"', "\\\"")
              .addEscape('\\', "\\\\")
              .build();
      return escaper.escape(sourceString);
    }
  }

  public static String escape(String source) {
    try {
      return specialSequenceEscaper.escape(source);
    } catch (IllegalArgumentException e) {
      throw new EscaperException(String.format("Input String can not be formatted: %s", e));
    }
  }

  private static class EscaperException extends RuntimeException {
    public EscaperException(String errorMessage) {
      super(errorMessage);
    }
  }
}
