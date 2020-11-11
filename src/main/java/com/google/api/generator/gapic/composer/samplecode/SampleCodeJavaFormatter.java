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

package com.google.api.generator.gapic.composer.samplecode;

import com.google.common.annotations.VisibleForTesting;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

public final class SampleCodeJavaFormatter {

  private SampleCodeJavaFormatter() {}

  private static final Formatter FORMATTER = new Formatter();
  private static final String RIGHT_BRACE = "}";
  private static final String LEFT_BRACE = "{";
  private static final String NEWLINE = "\n";

  private static final String FAKE_CLASS_TITLE =
      String.format("public class FakeClass %s%s", LEFT_BRACE, NEWLINE);
  private static final String FAKE_METHOD_TITLE =
      String.format("void fakeMethod() %s%s", LEFT_BRACE, NEWLINE);
  private static final String FAKE_METHOD_CLOSE = String.format("%s%s", RIGHT_BRACE, NEWLINE);
  private static final String FAKE_CLASS_CLOSE = String.format("%s", RIGHT_BRACE);

  /**
   * This method is used to format sample code string where composed by statements based on Google
   * Java style guide.
   *
   * @param sampleCode A string is composed by statements.
   * @return String Formatted sample code string based on google java style.
   */
  public static String format(String sampleCode) {
    final StringBuffer buffer = new StringBuffer();
    buffer.append(FAKE_CLASS_TITLE);
    buffer.append(FAKE_METHOD_TITLE);
    buffer.append(sampleCode);
    buffer.append(FAKE_METHOD_CLOSE);
    buffer.append(FAKE_CLASS_CLOSE);

    String formattedString = null;
    try {
      formattedString = FORMATTER.formatSource(buffer.toString());
    } catch (FormatterException e) {
      throw new FormatException(
          String.format("The sample code should be string where is composed by statements; %s", e));
    }
    return formattedString
        .replaceAll("^([^\n]*\n){2}|([^\n]*\n){2}$", "")
        .replaceAll("(?m)^ {4}", "")
        .trim();
  }

  @VisibleForTesting
  protected static class FormatException extends RuntimeException {
    public FormatException(String errorMessage) {
      super(errorMessage);
    }
  }
}
