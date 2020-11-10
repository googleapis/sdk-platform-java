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

package com.google.api.generator.gapic.utils;

import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.writer.JavaFormatter;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import java.util.List;

public final class SampleCodeJavaFormatter {

  private SampleCodeJavaFormatter() {}

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
   * format utilize google-java-format to format the sample code statements where these are wrapped
   * in a FakeClass to pretend as a valid source code. Because google-java-format is a program that
   * reformats Java source code.
   */
  public static String format(List<Statement> statements) {
    final StringBuffer buffer = new StringBuffer();
    buffer.append(FAKE_CLASS_TITLE);
    buffer.append(FAKE_METHOD_TITLE);
    buffer.append(writeStatements(statements));
    buffer.append(FAKE_METHOD_CLOSE);
    buffer.append(FAKE_CLASS_CLOSE);

    String formattedString = JavaFormatter.format(buffer.toString());
    return formattedString
        .replaceAll("^([^\n]*\n){2}|([^\n]*\n){2}$", "")
        .replaceAll("(?m)^ {4}", "")
        .trim();
  }

  private static String writeStatements(List<Statement> statements) {
    JavaWriterVisitor javaWriterVisitor = new JavaWriterVisitor();
    for (Statement statement : statements) {
      statement.accept(javaWriterVisitor);
    }
    return javaWriterVisitor.write();
  }
}
