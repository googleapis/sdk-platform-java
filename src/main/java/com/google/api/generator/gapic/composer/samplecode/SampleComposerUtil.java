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
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Expression;
import com.google.cloud.tools.snippetgen.configlanguage.v1.Type;
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

  // Get return type from full message Type
  public static String convertMessageTypeToReturnType(String messageType) {
    String[] splitString = messageType.split("\\.");
    return splitString[splitString.length-1];
  }

  //   Convert expression type to String
  // TODO: add handling for BytesValue, ComplexValue, MapValue, ConditionalValue
  public static String convertExpressionToString(Expression expression) {
    if(expression.hasBooleanValue()){
      return String.valueOf(expression.getBooleanValue());
    }
    if(expression.hasStringValue()){
      return expression.getStringValue();
    }
    if(expression.hasNameValue()){
      String name = JavaStyle.toLowerCamelCase(expression.getNameValue().getName());
      String path = JavaStyle.toUpperCamelCase(expression.getNameValue().getPathList().stream().collect(Collectors.joining(".")));
      if(path.length() > 0){
        path = ".get" + path + "()";
      }
      return name + path;
    }
    if(expression.hasComplexValue()){
      return expression.getComplexValue().getPropertiesMap().toString();
    }
    else{
      return null;
    }
  }

  //   Convert IterationStatement to Statement
  // Currently only works for repeated_iteration
  // TODO: handle for other iteration_types
//  public static Statement convertIterationToStatement(com.google.cloud.tools.snippetgen.configlanguage.v1.Statement.Iteration iteration) {
//    if(iteration.hasRepeatedIteration()){
//    }
//    return null;
//  }

  // Create Statement from standardOutput
  public static MethodInvocationExpr systemOutPrint(String content) {
    return composeSystemOutPrint(ValueExpr.withValue(StringObjectValue.withValue(content)));
  }

  private static MethodInvocationExpr composeSystemOutPrint(Expr content) {
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

}
