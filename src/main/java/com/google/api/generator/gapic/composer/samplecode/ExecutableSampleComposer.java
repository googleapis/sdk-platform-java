// Copyright 2021 Google LLC
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

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExecutableSampleComposer {
  public static String createExecutableSample(Sample sample, String pakkage) {
    Preconditions.checkNotNull(sample);
    String sampleMethodName = JavaStyle.toLowerCamelCase(sample.getName());
    return SampleCodeWriter.write(
        composeExecutableSample(
            pakkage, sampleMethodName, sample.getVariableAssignments(), sample.getBody()));
  }

  private static ClassDefinition composeExecutableSample(
      String packageName,
      String sampleMethodName,
      List<AssignmentExpr> sampleVariableAssignments,
      List<Statement> sampleBody) {
    String sampleClassName = JavaStyle.toUpperCamelCase(sampleMethodName);
    List<VariableExpr> sampleMethodArgs = composeSampleMethodArgs(sampleVariableAssignments);
    MethodDefinition mainMethod =
        composeMainMethod(
            composeMainBody(
                sampleVariableAssignments,
                composeInvokeMethodStatement(sampleMethodName, sampleMethodArgs)));
    MethodDefinition sampleMethod =
        composeSampleMethod(sampleMethodName, sampleMethodArgs, sampleBody);
    return composeSampleClass(packageName, sampleClassName, mainMethod, sampleMethod);
  }

  private static List<VariableExpr> composeSampleMethodArgs(
      List<AssignmentExpr> sampleVariableAssignments) {
    return sampleVariableAssignments.stream()
        .map(v -> v.variableExpr().toBuilder().setIsDecl(true).build())
        .collect(Collectors.toList());
  }

  private static Statement composeInvokeMethodStatement(
      String sampleMethodName, List<VariableExpr> sampleMethodArgs) {
    List<Expr> invokeArgs =
        sampleMethodArgs.stream()
            .map(arg -> arg.toBuilder().setIsDecl(false).build())
            .collect(Collectors.toList());
    return ExprStatement.withExpr(
        MethodInvocationExpr.builder()
            .setMethodName(sampleMethodName)
            .setArguments(invokeArgs)
            .build());
  }

  private static List<Statement> composeMainBody(
      List<AssignmentExpr> sampleVariableAssignments, Statement invokeMethod) {
    List<ExprStatement> setVariables =
        sampleVariableAssignments.stream()
            .map(var -> ExprStatement.withExpr(var))
            .collect(Collectors.toList());
    List<Statement> body = new ArrayList<>(setVariables);
    body.add(invokeMethod);
    return body;
  }

  private static ClassDefinition composeSampleClass(
      String packageName,
      String sampleClassName,
      MethodDefinition mainMethod,
      MethodDefinition sampleMethod) {
    return ClassDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setPackageString(packageName)
        .setName(sampleClassName)
        .setMethods(ImmutableList.of(mainMethod, sampleMethod))
        .build();
  }

  private static MethodDefinition composeMainMethod(List<Statement> mainBody) {
    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setIsStatic(true)
        .setReturnType(TypeNode.VOID)
        .setName("main")
        .setArguments(
            VariableExpr.builder()
                .setVariable(
                    Variable.builder().setType(TypeNode.STRING_ARRAY).setName("args").build())
                .setIsDecl(true)
                .build())
        .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(Exception.class)))
        .setBody(mainBody)
        .build();
  }

  private static MethodDefinition composeSampleMethod(
      String sampleMethodName,
      List<VariableExpr> sampleMethodArgs,
      List<Statement> sampleMethodBody) {
    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setIsStatic(true)
        .setReturnType(TypeNode.VOID)
        .setName(sampleMethodName)
        .setArguments(sampleMethodArgs)
        .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(Exception.class)))
        .setBody(sampleMethodBody)
        .build();
  }
}
