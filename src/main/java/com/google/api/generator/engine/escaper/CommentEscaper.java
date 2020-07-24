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

package com.google.api.generator.engine.escaper;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;

public class CommentEscaper {
  private static class SpecialCharEscaper extends Escaper {
    // Handle escape characters (https://docs.oracle.com/javase/tutorial/java/data/characters.html)
    // for the comments here, else JavaFormmater cannot properly format the string comment.
    // `"` and `'` are overlooked because the comments will not be surrounded by `"` or `'`.
    private static final Escaper escaper =
        Escapers.builder()
            .addEscape('\t', "\\t")
            .addEscape('\b', "\\b")
            .addEscape('\r', "\\r")
            .addEscape('\f', "\\f")
            .addEscape('\n', "\\n")
            .addEscape('\\', "\\\\")
            .build();

    private SpecialCharEscaper() {}

    @Override
    public String escape(String sourceString) {
      return escaper.escape(sourceString);
    }
  }

  private static class HtmlEscaper extends Escaper {
    // Based on the observation of the generated java files, we escape the following
    // four characters by html escaper. We do not directly use HtmlEscapers here because
    // it escapes`<>&\"'` as specified by HTML 4.01
    private static final Escaper escaper =
        Escapers.builder()
            .addEscape('<', "&lt;")
            .addEscape('>', "&gt;")
            .addEscape('&', "&amp;")
            .addEscape('*', "&#42;")
            .build();

    private HtmlEscaper() {}

    @Override
    public String escape(String sourceString) {
      return escaper.escape(sourceString);
    }
  }

  public static String specialCharEscape(String source) {
    return new SpecialCharEscaper().escape(source);
  }

  public static String htmlEscaper(String source) {
    return new HtmlEscaper().escape(source);
  }
  // Escape all special characters at the same time, it will be called
  // in Line/Block Comment classes since they will not add extra special
  // characters like `<>*` in the comment.
  public static String escape(String source) {
    return specialCharEscape(htmlEscaper(source));
  }
}
