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

import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;

public class SampleUtil {
  public static String composeSampleMethodName(String clientName, String methodName) {
    Preconditions.checkArgument(
        !clientName.isEmpty() && !methodName.isEmpty(),
        "clientName and methodName must not be empty");
    return JavaStyle.toLowerCamelCase(clientName + JavaStyle.toUpperCamelCase(methodName));
  }

  public static MethodInvocationExpr systemOutPrint(String content) {
    return composeSystemOutPrint(ValueExpr.withValue(StringObjectValue.withValue(content)));
  }

  public static MethodInvocationExpr systemOutPrint(VariableExpr variableExpr) {
    return composeSystemOutPrint(variableExpr.toBuilder().setIsDecl(false).build());
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
