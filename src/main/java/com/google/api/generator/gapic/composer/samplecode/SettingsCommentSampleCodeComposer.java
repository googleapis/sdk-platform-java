package com.google.api.generator.gapic.composer.samplecode;

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class SettingsCommentSampleCodeComposer {

  private static final String BUILDER_NAME_PATTERN = "%sBuilder";
  private static final String STUB = "Stub";
  private static final String EMPTY_STRING = "";

  public static String composeSettingClassHeaderSampleCode(Method method, TypeNode classType) {
    String className = classType.reference().name();
    TypeNode builderType =
        TypeNode.withReference(
            VaporReference.builder()
                .setEnclosingClassNames(classType.reference().name())
                .setName("Builder")
                .setPakkage(classType.reference().pakkage())
                .build());
    Variable builderVar =
        Variable.builder()
            .setName(getClassSettingsBuilderName(className))
            .setType(builderType)
            .build();

    VariableExpr localSettingsVarExpr = VariableExpr.withVariable(builderVar);

    Expr settingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(classType)
            .setMethodName("newBuilder")
            .setReturnType(builderType)
            .build();

    Expr initLocalSettingsExpr =
        AssignmentExpr.builder()
            .setVariableExpr(localSettingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(settingsBuilderExpr)
            .build();

    MethodInvocationExpr retrySettingsMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(localSettingsVarExpr)
            .setMethodName(JavaStyle.toLowerCamelCase(String.format("%sSettings", method.name())))
            .setReturnType(method.outputType())
            .build();

    MethodInvocationExpr timeoutArExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(
                TypeNode.withReference(ConcreteReference.withClazz(Duration.class)))
            .setMethodName("ofSeconds")
            .setArguments(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setType(TypeNode.INT).setValue("30").build()))
            .build();

    MethodInvocationExpr timeoutBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(
                        MethodInvocationExpr.builder()
                            .setExprReferenceExpr(retrySettingsMethodExpr)
                            .setMethodName("getRetrySettings")
                            .build())
                    .setMethodName("toBuilder")
                    .build())
            .setMethodName("setTotalTimeout")
            .setArguments(Arrays.asList(timeoutArExpr))
            .build();

    MethodInvocationExpr retrySettingsArgExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(timeoutBuilderMethodExpr)
            .setMethodName("build")
            .build();

    MethodInvocationExpr settingBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(retrySettingsMethodExpr)
            .setMethodName("setRetrySettings")
            .setArguments(Arrays.asList(retrySettingsArgExpr))
            .build();

    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setType(classType)
                .setName(getServiceSettingsName(className))
                .build());

    AssignmentExpr settingBuildAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(localSettingsVarExpr)
                    .setMethodName("build")
                    .setReturnType(classType)
                    .build())
            .build();

    List<Statement> statements =
        Arrays.asList(initLocalSettingsExpr, settingBuilderMethodExpr, settingBuildAssignmentExpr)
            .stream()
            .map(e -> ExprStatement.withExpr(e))
            .collect(Collectors.toList());
    return SampleCodeJavaFormatter.format(writeStatements(statements));
  }

  private static String getServiceSettingsName(String className) {
    return JavaStyle.toLowerCamelCase(className).replace(STUB, EMPTY_STRING);
  }

  private static String getClassSettingsBuilderName(String className) {
    return JavaStyle.toLowerCamelCase(
            String.format(BUILDER_NAME_PATTERN, JavaStyle.toLowerCamelCase(className)))
        .replace(STUB, EMPTY_STRING);
  }

  private static String writeStatements(List<Statement> statements) {
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    for (Statement statement : statements) {
      statement.accept(visitor);
    }
    return visitor.write();
  }
}
