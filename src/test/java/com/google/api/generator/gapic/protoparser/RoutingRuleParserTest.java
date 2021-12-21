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
import static org.junit.Assert.assertEquals;

import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.RoutingHeaders;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.TestingOuterClass;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;

public class RoutingRuleParserTest {

  // Do we have to test against a real proto file
  // Maybe manually create a MethodDescriptor and test against it? more flexibility and precise.
  // testing.proto is being used by other tests as well
  @Test
  public void parseRoutingRuleAnnotation() {
    FileDescriptor testingFileDescriptor = TestingOuterClass.getDescriptor();
    ServiceDescriptor testingService = testingFileDescriptor.getServices().get(0);
    assertEquals("Testing", testingService.getName());

    Map<String, Message> messages = Parser.parseMessages(testingFileDescriptor);

    // GetTest method.
    MethodDescriptor rpcMethod = testingService.getMethods().get(5);
    Message inputMessage = messages.get("com.google.showcase.v1beta1.GetTestRequest");
    RoutingHeaders routingHeaders = RoutingRuleParser.parse(rpcMethod, inputMessage, messages);
    assertThat(
            routingHeaders.routingHeadersSet().stream()
                .map(routingHeader -> routingHeader.field() + " -> " + routingHeader.name())
                .collect(Collectors.toList()))
        .containsExactly("name -> rename");
  }
}
