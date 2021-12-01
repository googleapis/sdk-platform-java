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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.google.api.generator.engine.ast.*;
import java.util.UUID;
import org.junit.Test;

public class SampleUtilTest {
  @Test
  public void composeSampleMethodName() {
    String expected = "echoClientWait";

    String clientName = "EchoClient";
    String methodName = "wait";
    String result = SampleUtil.composeSampleMethodName(clientName, methodName);
    assertEquals(expected, result);

    clientName = "echoClient";
    methodName = "Wait";
    result = SampleUtil.composeSampleMethodName(clientName, methodName);
    assertEquals(expected, result);

    clientName = "EchoClient";
    methodName = "Wait";
    result = SampleUtil.composeSampleMethodName(clientName, methodName);
    assertEquals(expected, result);

    clientName = "echoClient";
    methodName = "wait";
    result = SampleUtil.composeSampleMethodName(clientName, methodName);
    assertEquals(expected, result);
  }

  @Test
  public void composeSampleMethodNameEmpty() {
    String emptyclientName = "";
    String methodName = "wait";
    assertThrows(
        IllegalArgumentException.class,
        () -> SampleUtil.composeSampleMethodName(emptyclientName, methodName));

    String clientName = "EchoClient";
    String emptyMethodName = "";
    assertThrows(
        IllegalArgumentException.class,
        () -> SampleUtil.composeSampleMethodName(clientName, emptyMethodName));
    ;
  }

  @Test
  public void systemOutPrintVariable() {
    String content = "Testing systemOutPrintVariable" + UUID.randomUUID();
    String result = SampleCodeWriter.write(SampleUtil.systemOutPrint(content));
    String expected = "System.out.println(\"" + content + "\")";
    assertEquals(expected, result);
  }

  @Test
  public void systemOutPrintString() {
    String testingVarName = "testingVar";
    VariableExpr testingVar =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.STRING).setName(testingVarName).build());
    String result = SampleCodeWriter.write(SampleUtil.systemOutPrint(testingVar));
    String expected = "System.out.println(" + testingVarName + ")";
    assertEquals(expected, result);
  }
}
