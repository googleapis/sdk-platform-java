// Copyright 2022 Google LLC
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
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ForStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicSnippetConfig;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Expression;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Type;
import java.util.Arrays;
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

  static String createOverloadDisambiguation(List<VariableExpr> methodArgVarExprs) {
    if (methodArgVarExprs.isEmpty()) {
      return "Noargs";
    }
    return methodArgVarExprs.stream()
        .map(
            arg ->
                JavaStyle.toUpperCamelCase(
                    arg.variable().type().reference() == null
                        ? arg.variable().type().typeKind().name().toLowerCase()
                        : arg.variable().type().reference().name().toLowerCase()))
        .collect(Collectors.joining());
  }

  public static List<Sample> handleDuplicateSamples(List<Sample> samples) {
    //  grab all distinct samples and group by sample name
    //  ie: { "echo", ["echo(request"],
    //        "echoString", ["echo(parent)", "echo(child)", "echo(context)"],
    //        "echoDelete", ["echo.delete(request)"] }
    Map<String, List<Sample>> distinctSamplesGroupedByName =
        samples.stream().distinct().collect(Collectors.groupingBy(s -> s.name()));

    // collect samples that don't have duplicates
    // ie: ["echo", "echoDelete"]
    List<Sample> uniqueSamples =
        distinctSamplesGroupedByName.entrySet().stream()
            .filter(entry -> entry.getValue().size() < 2)
            .map(entry -> entry.getValue().get(0))
            .collect(Collectors.toList());

    if (uniqueSamples.size() == distinctSamplesGroupedByName.size()) {
      return uniqueSamples;
    }

    // collect samples that do have duplicates
    // ie: ["echoString"]
    List<Map.Entry<String, List<Sample>>> duplicateDistinctSamples =
        distinctSamplesGroupedByName.entrySet().stream()
            .filter(entry -> entry.getValue().size() > 1)
            .collect(Collectors.toList());

    // update similar samples regionTag/name so filesname/regiontag are unique
    // ie: ["echo", "echoDelete", "echoString", "echoString1"]
    for (Map.Entry<String, List<Sample>> entry : duplicateDistinctSamples) {
      int sampleNum = 0;
      for (Sample sample : entry.getValue()) {
        Sample uniqueSample = sample;
        //  first sample will be "canonical", not updating name
        if (sampleNum != 0) {
          uniqueSample =
              sample.withRegionTag(
                  sample
                      .regionTag()
                      .withOverloadDisambiguation(
                          sample.regionTag().overloadDisambiguation() + sampleNum));
        }
        uniqueSamples.add(uniqueSample);
        sampleNum++;
      }
    }
    return uniqueSamples;
  }

  // Convert Type to String for use as Return Type in configured sample. Handles Scalar, Enum, and
  // Message Types.
  // TODO: Handle RepeatedType (P1), MapType (P1), BytesType (P3)
  public static String convertTypeToString(Type type) {
    if (type.hasMessageType()) {
      return type.getMessageType().getMessageFullName();
    }
    if (type.hasScalarType()) {
      return type.getScalarType().name();
    }
    if (type.hasEnumType()) {
      return type.getEnumType().getEnumFullName();
    } else {
      return "";
    }
  }

  // If the return type is a message Type, need to do additional parsing to get return Type for
  // sample method
  public static String convertTypeToReturnType(String type) {
    String[] splitString = type.split("\\.");
    return splitString[splitString.length - 1];
  }

  // Handle configLanguage.Type conversion
  // Handles Scalar, Enum, and Message Types
  // TODO: Handle Repeated type (P1), Map type (P1), and Bytes type (P3)

  // Convert configLanguage.Type to TypeNode
  // https://developers.google.com/protocol-buffers/docs/proto3#scalar
  public static TypeNode convertScalarTypeToTypeNode(Type.ScalarType configType) {
    switch (configType) {
      case TYPE_BOOL:
        return TypeNode.BOOLEAN;
      case TYPE_FLOAT:
        return TypeNode.FLOAT;
      case TYPE_INT64:
      case TYPE_FIXED64:
      case TYPE_SFIXED64:
      case TYPE_SINT64:
        return TypeNode.LONG;
      case TYPE_UINT64:
      case TYPE_INT32:
      case TYPE_FIXED32:
      case TYPE_UINT32:
      case TYPE_SFIXED32:
      case TYPE_SINT32:
        return TypeNode.INT;
      default:
        return TypeNode.STRING;
    }
  }

  // Convert configLanguage.Type to java.Lang type in string
  // https://developers.google.com/protocol-buffers/docs/proto3#scalar
  public static String convertScalarTypeToJavaTypeString(Type.ScalarType configType) {
    switch (configType) {
      case TYPE_BOOL:
        return ("Boolean");
      case TYPE_FLOAT:
        return ("Float");
      case TYPE_INT64:
      case TYPE_FIXED64:
      case TYPE_SFIXED64:
      case TYPE_SINT64:
        return ("Long");
      case TYPE_UINT64:
      case TYPE_INT32:
      case TYPE_FIXED32:
      case TYPE_UINT32:
      case TYPE_SFIXED32:
      case TYPE_SINT32:
        return ("Int");
      default:
        return ("String");
    }
  }

  // Convert config.Language Expression to String
  // Currently handles Boolean, String, NameValue Expressions
  // TODO: add handling for ComplexValue (P0), Enum (P0), Number (P0), NameValue with Path (P1),
  // ListValue (P1), MapValue (P1), ConditionalValue (P2), NullValue (P2), BytesValue (P3), and
  // DefaultValue (P3)
  public static String convertExpressionToString(Expression expression) {
    if (expression.hasBooleanValue()) {
      return String.valueOf(expression.getBooleanValue());
    }
    if (expression.hasStringValue()) {
      return expression.getStringValue();
    }
    if (expression.hasNameValue()) {
      String name = JavaStyle.toLowerCamelCase(expression.getNameValue().getName());
      String path =
          JavaStyle.toUpperCamelCase(
              expression.getNameValue().getPathList().stream().collect(Collectors.joining(".")));
      if (path.length() > 0) {
        path = ".get" + path + "()";
      }
      return name + path;
    }
    if (expression.hasComplexValue()) {
      return expression.getComplexValue().getPropertiesMap().toString();
    } else {
      return null;
    }
  }

  // Convert configLanguage.Statement to Statement
  // Handles Standard Output, Return, Declaration, and Declaration Assignment Statements
  // TODO: Handle Iteration.Repeated (P1), Iteration.Map (P1), Conditional (P2),
  // Iteration.NumberSequence (P2), and Iteration.bytes (P3) Statements
  // Currently only works for repeated_iteration
  //        List<CustomClass.ClassItem> itemsList = createdCustomClass.getItemsList();
  //        for(items : itemsList){
  //          System.out.println(item)
  //        }
  public static Statement convertIterationTypeStatementToStatement(
      com.google.cloud.tools.snippetgen.configlanguage.v1.Statement statement) {
    if (statement.hasIteration() && statement.getIteration().hasRepeatedIteration()) {
      String name = statement.getIteration().getRepeatedIteration().getCurrentName();
      //      String element =
      // statement.getIteration().getRepeatedIteration().getRepeatedElements();
      TypeNode iterationType =
          TypeNode.withReference(
              VaporReference.builder()
                  .setName("ClassItem")
                  .setPakkage("com.google.cloud.speech.v1.CustomClass")
                  .build());

      Variable variable = Variable.builder().setName(name).setType(iterationType).build();
      VariableExpr variableExpr =
          VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

      VariableExpr objVariableExpr =
          VariableExpr.builder()
              .setVariable(
                  Variable.builder().setType(TypeNode.OBJECT).setName("createdCustomClass").build())
              .setIsDecl(true)
              .build();

      MethodInvocationExpr methodExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(objVariableExpr.toBuilder().setIsDecl(false).build())
              .setMethodName("getItemsList")
              .build();

      Statement BodyStatement = ExprStatement.withExpr(systemOutPrint(variableExpr));

      return ForStatement.builder()
          .setLocalVariableExpr(variableExpr)
          .setCollectionExpr(methodExpr)
          .setBody(Arrays.asList(BodyStatement))
          .build();
    }
    return null;
  }

  // Convert configLanguage.Statement of StandardOutput to Statement
  // Support StandardOutput
  // TODO: Support Return (P0), Declaration (P0), Declaration Assigment (P0)
  // TODO: Support Iteration.Repeated (P1), Iteration.Map (P1), Conditional (P2), Iteration.NumberSequence(P2), Iteration.bytes(P3)
  public static Statement convertStandardOutputStatementToStatement(
          com.google.cloud.tools.snippetgen.configlanguage.v1.Statement statement) {
    if (statement.hasStandardOutput()) {
      if(statement.getStandardOutput().getValue().hasStringValue()) {
        String val = convertExpressionToString(statement.getStandardOutput().getValue());
        Expr content = ValueExpr.withValue(StringObjectValue.withValue(val));
        return ExprStatement.withExpr(composeSystemOutPrint(content));
      }
      if(statement.getStandardOutput().getValue().hasNameValue()){
        // If path does not exist in NameValue, print out Name
        if(statement.getStandardOutput().getValue().getNameValue().getPathList().isEmpty()){
          VariableExpr content =
                  VariableExpr.builder()
                          .setVariable(
                                  Variable.builder()
                                          .setType(TypeNode.STRING)
                                          .setName(JavaStyle.toLowerCamelCase(statement.getStandardOutput().getValue().getNameValue().getName()))
                                          .build())
                          .setIsDecl(true)
                          .build();
          return ExprStatement.withExpr(systemOutPrint(content));
        }
        // If path exists in NameValue, print out Name.get<Path>
        TypeNode contentType =
                TypeNode.withReference(
                        ConcreteReference.builder()
                                .setClazz(Object.class)
                                .build());
        VariableExpr contentVarExpr =
                VariableExpr.withVariable(
                        Variable.builder().setName(JavaStyle.toLowerCamelCase(statement.getStandardOutput().getValue().getNameValue().getName())).setType(contentType).build());
        MethodInvocationExpr initialGetMethodInvocationExpr =
                MethodInvocationExpr.builder()
                        .setExprReferenceExpr(contentVarExpr)
                        .setMethodName(String.format("get%s", JavaStyle.toUpperCamelCase(statement.getStandardOutput().getValue().getNameValue().getPathList().get(0))))
                        .setReturnType(TypeNode.VOID)
                        .build();

        // If path is nested, recursively add get() methods
//        for(String pathField : statement.getStandardOutput().getValue().getNameValue().getPathList()){
//          MethodInvocationExpr nextGetMethodInvocationExpr =
//                  MethodInvocationExpr.builder()
//                          .setExprReferenceExpr(initialGetMethodInvocationExpr)
//                          .setMethodName(String.format("get%s", JavaStyle.toUpperCamelCase(pathField)))
//                          .setReturnType(TypeNode.VOID)
//                          .build();
//          return ExprStatement.withExpr(systemOutPrint(nextGetMethodInvocationExpr));
//        }
        return ExprStatement.withExpr(systemOutPrint(initialGetMethodInvocationExpr));
      }
    }
    // Return empty print statement if invalid
    return ExprStatement.withExpr(systemOutPrint(""));
    }

  // Create Statement from standardOutput
  public static MethodInvocationExpr systemOutPrint(String content) {
    return composeSystemOutPrint(ValueExpr.withValue(StringObjectValue.withValue(content)));
  }

  private static MethodInvocationExpr systemOutPrint(VariableExpr variableExpr) {
    return composeSystemOutPrint(variableExpr.toBuilder().setIsDecl(false).build());
  }

  public static  MethodInvocationExpr systemOutPrint(MethodInvocationExpr nestedVariableExpr) {
    return composeSystemOutPrint(nestedVariableExpr.toBuilder().build());
  }

  public static MethodInvocationExpr composeSystemOutPrint(Expr content) {
    VaporReference out =
        VaporReference.builder()
            .setEnclosingClassNames("System")
            .setName("out")
            .setPakkage("java.lang")
            .build();
    return MethodInvocationExpr.builder()
        .setStaticReferenceType(TypeNode.withReference(out))
        .setMethodName("println")
        .setArguments(content)
        .build();
  }

  public static MethodInvocationExpr setNestedValue(String content) {
    return composeNestedValue(ValueExpr.withValue(StringObjectValue.withValue(content)));
  }

  private static MethodInvocationExpr composeNestedValue(Expr content) {
    VaporReference out =
        VaporReference.builder()
            .setEnclosingClassNames("CustomClass")
            .setName("out")
            .setPakkage("java.lang")
            .build();
    return MethodInvocationExpr.builder()
        .setStaticReferenceType(TypeNode.withReference(out))
        .setMethodName("println")
        .setArguments(content)
        .build();
  }
}
