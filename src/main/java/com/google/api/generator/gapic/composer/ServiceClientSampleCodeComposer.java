package com.google.api.generator.gapic.composer;

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
import com.google.api.generator.gapic.composer.samplecode.SampleCodeJavaFormatter;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceClientSampleCodeComposer {
  private static final String SETTINGS_NAME_PATTERN = "%sSettings";
  private static final String CLASS_NAME_PATTERN = "%sClient";

  public static String composeClassHeaderCredentialsSampleCode(
      Service service, Map<String, TypeNode> types) {
    String settingsVarName = JavaStyle.toLowerCamelCase(getSettingsName(service.name()));
    TypeNode settingsVarType = types.get(getSettingsName(service.name()));
    VariableExpr settingsVarExpr = createVariableExpr(settingsVarName, settingsVarType);
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(settingsVarType)
            .setMethodName("newBuilder")
            .build();
    TypeNode fixedCredentialProvideType =
        TypeNode.withReference(ConcreteReference.withClazz(FixedCredentialsProvider.class));
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
    Expr initSettingsVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(buildMethodExpr)
            .build();

    String clientName = JavaStyle.toLowerCamelCase(getClientClassName(service.name()));
    TypeNode clientType = types.get(getClientClassName(service.name()));
    VariableExpr clientVarExpr = createVariableExpr(clientName, clientType);
    MethodInvocationExpr createMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(clientType)
            .setArguments(settingsVarExpr)
            .setMethodName("create")
            .setReturnType(clientType)
            .build();
    Expr initClientVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(createMethodExpr)
            .build();

    return writeSampleCode(Arrays.asList(initSettingsVarExpr, initClientVarExpr));
  }

  // ======================================== Helpers ==========================================//

  private static String getClientClassName(String serviceName) {
    return String.format(CLASS_NAME_PATTERN, serviceName);
  }

  private static String getSettingsName(String serviceName) {
    return String.format(SETTINGS_NAME_PATTERN, serviceName);
  }

  private static String writeSampleCode(List<Expr> exprs) {
    List<Statement> statements =
        exprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    for (Statement statement : statements) {
      statement.accept(visitor);
    }
    return SampleCodeJavaFormatter.format(visitor.write());
  }

  private static VariableExpr createVariableExpr(String variableName, TypeNode type) {
    return VariableExpr.withVariable(
        Variable.builder().setName(variableName).setType(type).build());
  }
}
