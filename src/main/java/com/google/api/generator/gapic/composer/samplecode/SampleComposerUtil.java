package com.google.api.generator.gapic.composer.samplecode;

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SampleComposerUtil {
  // Assign client variable expr with create client.
  // e.g EchoClient echoClient = EchoClient.create()
  static AssignmentExpr assignClientVariableWithCreateMethodExpr(VariableExpr clientVarExpr) {
    return AssignmentExpr.builder()
        .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(
            MethodInvocationExpr.builder()
                .setStaticReferenceType(clientVarExpr.variable().type())
                .setReturnType(clientVarExpr.variable().type())
                .setMethodName("create")
                .build())
        .build();
  }

  static boolean isStringTypedResourceName(
      MethodArgument arg, Map<String, ResourceName> resourceNames) {
    return arg.type().equals(TypeNode.STRING)
        && arg.field().hasResourceReference()
        && resourceNames.containsKey(arg.field().resourceReference().resourceTypeString());
  }

  static boolean isProtoEmptyType(TypeNode type) {
    return type.reference().pakkage().equals("com.google.protobuf")
        && type.reference().name().equals("Empty");
  }

  static String createOverloadDisambiguation(List<VariableExpr> methodArgVarExprs) {
    return methodArgVarExprs.stream()
        .map(
            arg ->
                JavaStyle.toUpperCamelCase(
                    arg.variable().type().reference() == null
                        ? arg.variable().type().typeKind().name().toLowerCase()
                        : arg.variable().type().reference().name().toLowerCase()))
        .collect(Collectors.joining());
  }
}
