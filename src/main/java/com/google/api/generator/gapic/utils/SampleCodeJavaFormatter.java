package com.google.api.generator.gapic.utils;

import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.writer.JavaFormatter;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import java.util.List;

public final class SampleCodeJavaFormatter {

  private SampleCodeJavaFormatter() {}

  private static final JavaWriterVisitor javaWriterVisitor = new JavaWriterVisitor();
  private static final StringBuffer buffer = new StringBuffer();

  private static final String RIGHT_BRACE = "}";
  private static final String LEFT_BRACE = "{";
  private static final String NEWLINE = "\n";

  private static final String FAKE_CLASS_TITLE =
      String.format("public class FakeClass %s%s", LEFT_BRACE, NEWLINE);
  private static final String FAKE_METHOD_TITLE =
      String.format("void FakeMethod() %s%s", LEFT_BRACE, NEWLINE);
  private static final String FAKE_METHOD_CLOSE = String.format("%s%s", RIGHT_BRACE, NEWLINE);
  private static final String FAKE_CLASS_CLOSE = String.format("%s%s", RIGHT_BRACE, NEWLINE);

  public static String format(List<Statement> statements) {
    buffer.append(FAKE_CLASS_TITLE);
    buffer.append(FAKE_METHOD_TITLE);
    statements(statements);
    buffer.append(javaWriterVisitor.write());
    buffer.append(FAKE_METHOD_CLOSE);
    buffer.append(FAKE_CLASS_CLOSE);

    String formattedString = JavaFormatter.format(buffer.toString());
    return formattedString
        .replaceAll("^([^\n]*\n){2}|([^\n]*\n){2}$", "")
        .replaceAll("(?m)^ {4}", "");
  }

  private static void statements(List<Statement> statements) {
    for (Statement statement : statements) {
      statement.accept(javaWriterVisitor);
    }
  }
}
