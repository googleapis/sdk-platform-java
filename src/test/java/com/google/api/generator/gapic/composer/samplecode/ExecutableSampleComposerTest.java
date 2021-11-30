package com.google.api.generator.gapic.composer.samplecode;

import com.google.api.generator.engine.ast.*;
import com.google.api.generator.testutils.LineFormatter;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ExecutableSampleComposerTest {

    @Test
    public void createExecutableSampleEmptySample() {
        String packageName = "com.google.example";
        String sampleMethodName = "echoClientWait";

        String sampleResult = ExecutableSampleComposer.createExecutableSample(packageName, sampleMethodName,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        String expected =
                LineFormatter.lines(
                        "package com.google.example;\n",
                        "\n",
                        "public class EchoClientWait {\n",
                        "\n",
                        "  public static void main(String[] args) {\n",
                        "    echoClientWait();\n",
                        "  }\n",
                        "\n",
                        "  public static void echoClientWait() {}\n",
                        "}\n");

        assertEquals(sampleResult, expected);
    }

    @Test
    public void createExecutableSampleMethodArgsNoVar() {
        String packageName = "com.google.example";
        String sampleMethodName = "echoClientWait";

        Statement sampleBody = ExprStatement.withExpr(SampleUtil.systemOutPrint("Testing " + sampleMethodName));

        String sampleResult = ExecutableSampleComposer.createExecutableSample(packageName, sampleMethodName,
                new ArrayList<>(), ImmutableList.of(sampleBody), new ArrayList<>());

        String expected =
                LineFormatter.lines(
                        "package com.google.example;\n",
                        "\n",
                        "public class EchoClientWait {\n",
                        "\n",
                        "  public static void main(String[] args) {\n",
                        "    echoClientWait();\n",
                        "  }\n",
                        "\n",
                        "  public static void echoClientWait() {\n",
                        "    System.out.println(\"Testing echoClientWait\");\n",
                        "  }\n",
                        "}\n");

        assertEquals(sampleResult, expected);
    }

    @Test
    public void createExecutableSampleMethod() {
        String packageName = "com.google.example";
        String sampleMethodName = "echoClientWait";
        VariableExpr variableExpr = VariableExpr.builder().setVariable(
            Variable.builder().setType(TypeNode.STRING).setName("content").build()).setIsDecl(true).build();

        AssignmentExpr varAssignment = AssignmentExpr.builder()
                .setVariableExpr(variableExpr)
                .setValueExpr(ValueExpr.withValue(StringObjectValue.withValue("Testing " + sampleMethodName)))
                .build();

        Statement sampleBody = ExprStatement.withExpr(SampleUtil.systemOutPrint(variableExpr));

        String sampleResult = ExecutableSampleComposer.createExecutableSample(packageName, sampleMethodName,
                ImmutableList.of(varAssignment), ImmutableList.of(sampleBody), ImmutableList.of(variableExpr));

        String expected =
                LineFormatter.lines(
                        "package com.google.example;\n",
                        "\n",
                        "public class EchoClientWait {\n",
                        "\n",
                        "  public static void main(String[] args) {\n",
                        "    String content = \"Testing echoClientWait\";\n",
                        "    echoClientWait(content);\n",
                        "  }\n",
                        "\n",
                        "  public static void echoClientWait(String content) {\n",
                        "    System.out.println(content);\n",
                        "  }\n",
                        "}\n");

        assertEquals(sampleResult, expected);
    }

    @Test
    public void createExecutableSampleMethodMultipleStatements() {
        String packageName = "com.google.example";
        String sampleMethodName = "echoClientWait";
        VariableExpr variableExpr = VariableExpr.builder().setVariable(
                Variable.builder().setType(TypeNode.STRING).setName("content").build()).setIsDecl(true).build();
        VariableExpr variableExpr2 = VariableExpr.builder().setVariable(
                Variable.builder().setType(TypeNode.STRING).setName("otherContent").build()).setIsDecl(true).build();
        AssignmentExpr varAssignment = AssignmentExpr.builder()
                .setVariableExpr(variableExpr)
                .setValueExpr(ValueExpr.withValue(StringObjectValue.withValue("Testing " + sampleMethodName)))
                .build();
        AssignmentExpr varAssignment2 = AssignmentExpr.builder()
                .setVariableExpr(variableExpr2)
                .setValueExpr(ValueExpr.withValue(StringObjectValue.withValue("Samples " + sampleMethodName)))
                .build();

        Statement bodyStatement = ExprStatement.withExpr(SampleUtil.systemOutPrint(variableExpr));
        Statement bodyStatement2 = ExprStatement.withExpr(SampleUtil.systemOutPrint(variableExpr2));

        String sampleResult = ExecutableSampleComposer.createExecutableSample(packageName, sampleMethodName,
                ImmutableList.of(varAssignment, varAssignment2), ImmutableList.of(bodyStatement, bodyStatement2),
                ImmutableList.of(variableExpr, variableExpr2));

        String expected =
                LineFormatter.lines(
                        "package com.google.example;\n",
                        "\n",
                        "public class EchoClientWait {\n",
                        "\n",
                        "  public static void main(String[] args) {\n",
                        "    String content = \"Testing echoClientWait\";\n",
                        "    String otherContent = \"Samples echoClientWait\";\n",
                        "    echoClientWait(content, otherContent);\n",
                        "  }\n",
                        "\n",
                        "  public static void echoClientWait(String content, String otherContent) {\n",
                        "    System.out.println(content);\n",
                        "    System.out.println(otherContent);\n",
                        "  }\n",
                        "}\n");

        assertEquals(sampleResult, expected);
    }
}
