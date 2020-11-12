package com.google.api.generator.gapic.composer.samplecode;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

public class ServiceClientCommentSampleCodeComposer {

  private static final String SETTINGS_NAME_PATTERN = "%sSettings";
  private static final String CLASS_NAME_PATTERN = "%sClient";

  public static String composeClassHeaderMethodSampleCode() {
    // TODO(summerji): implement class header rpc sample code.
    return "string holder";
  }

  public static String composeClassHeaderCredentialsSampleCode(Service service, Map<String, TypeNode> types) {
    String settingsVarName = getSettingsName(service.name());
    TypeNode settingsVarType = types.get(settingsVarName);
    VariableExpr settingsVarExpr = VariableExpr.withVariable(
        Variable.builder()
            .setName(settingsVarName)
            .setType(settingsVarType)
            .build());
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
        .setStaticReferenceType(settingsVarType)
        .setMethodName("newBuilder")
        .build();
    TypeNode fixedCredentialProvideType = TypeNode.withReference(
        ConcreteReference.withClazz(FixedCredentialsProvider.class)
    );
    MethodInvocationExpr credentialArgExpr =
        MethodInvocationExpr.builder()
        .setStaticReferenceType(fixedCredentialProvideType)
        .setArguments(ValueExpr.withValue(StringObjectValue.withValue("myCredentials")))
        .setMethodName("create")
        .build();
    MethodInvocationExpr credentialsMethodExpr =
        MethodInvocationExpr.builder()
        .setExprReferenceExpr(newBuilderMethodExpr)
        .setArguments(credentialArgExpr)
        .setMethodName("setCredentialsProvider")
        .build();
    MethodInvocationExpr buildMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(credentialsMethodExpr)
            .setReturnType(settingsVarType)
            .setMethodName("build")
            .build();

    Expr initLocalSettingsVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(buildMethodExpr)
            .build();

    List<Statement> statements =
        Arrays.asList(
            initLocalSettingsVarExpr
        )
            .stream()
            .map(e -> ExprStatement.withExpr(e))
            .collect(Collectors.toList());
    return SampleCodeJavaFormatter.format(writeStatements(statements));
  }

  private static String getClientClassName(String serviceName) {
    return String.format(CLASS_NAME_PATTERN, serviceName);
  }

  private static String getSettingsName(String serviceName) {
    return String.format(SETTINGS_NAME_PATTERN, serviceName);
  }

  private static String writeStatements(List<Statement> statements) {
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    for (Statement statement : statements) {
      statement.accept(visitor);
    }
    return visitor.write();
  }
}
