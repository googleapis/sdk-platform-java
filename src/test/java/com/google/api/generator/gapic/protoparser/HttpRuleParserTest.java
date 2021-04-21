// Copyright 2020 Google LLC
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

package com.google.api.generator.gapic.protoparser;

import static com.google.common.truth.Truth.assertThat;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import com.google.api.generator.gapic.model.HttpRuleBindings;
import com.google.api.generator.gapic.model.Message;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.TestingOuterClass;
import java.util.Map;
import org.junit.Test;

public class HttpRuleParserTest {
  @Test
  public void parseHttpAnnotation_basic() {
    FileDescriptor testingFileDescriptor = TestingOuterClass.getDescriptor();
    ServiceDescriptor testingService = testingFileDescriptor.getServices().get(0);
    assertEquals("Testing", testingService.getName());

    Map<String, Message> messages = Parser.parseMessages(testingFileDescriptor);

    // CreateSession method.
    MethodDescriptor rpcMethod = testingService.getMethods().get(0);
    Message inputMessage = messages.get("CreateSessionRequest");
    HttpRuleBindings httpBindings = HttpRuleParser.parse(rpcMethod, inputMessage, messages);
    assertTrue(httpBindings.pathParameters().isEmpty());

    // GetSession method.
    rpcMethod = testingService.getMethods().get(1);
    inputMessage = messages.get("GetSessionRequest");
    httpBindings = HttpRuleParser.parse(rpcMethod, inputMessage, messages);
    assertThat(httpBindings.pathParameters()).containsExactly("name");
  }

  @Test
  public void parseHttpAnnotation_multipleBindings() {
    FileDescriptor testingFileDescriptor = TestingOuterClass.getDescriptor();
    ServiceDescriptor testingService = testingFileDescriptor.getServices().get(0);
    assertEquals("Testing", testingService.getName());

    Map<String, Message> messages = Parser.parseMessages(testingFileDescriptor);

    // VerityTest method.
    MethodDescriptor rpcMethod =
        testingService.getMethods().get(testingService.getMethods().size() - 1);
    Message inputMessage = messages.get("VerifyTestRequest");
    HttpRuleBindings httpBindings = HttpRuleParser.parse(rpcMethod, inputMessage, messages);
    assertThat(httpBindings.pathParameters())
        .containsExactly("answer", "foo", "name", "test_to_verify.name");
  }

  @Test
  public void parseHttpAnnotation_missingFieldFromMessage() {
    FileDescriptor testingFileDescriptor = TestingOuterClass.getDescriptor();
    ServiceDescriptor testingService = testingFileDescriptor.getServices().get(0);
    assertEquals("Testing", testingService.getName());

    Map<String, Message> messages = Parser.parseMessages(testingFileDescriptor);

    // VerityTest method.
    MethodDescriptor rpcMethod =
        testingService.getMethods().get(testingService.getMethods().size() - 1);
    Message inputMessage = messages.get("CreateSessionRequest");
    assertThrows(
        IllegalStateException.class, () -> HttpRuleParser.parse(rpcMethod, inputMessage, messages));
  }
}
