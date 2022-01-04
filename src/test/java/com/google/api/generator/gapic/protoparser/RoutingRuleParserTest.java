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

import static com.google.api.generator.gapic.protoparser.RoutingRuleParser.PATH_TEMPLATE_WRONG_NUMBER_OF_NAMED_SEGMENT_ERROR_MESSAGE;
import static com.google.api.generator.gapic.protoparser.RoutingRuleParser.WILDCARD_PATTERN;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.RoutingHeaders;
import com.google.api.generator.gapic.model.RoutingHeaders.RoutingHeader;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.RoutingRuleParserTestingOuterClass;
import java.util.Map;
import org.junit.Test;

public class RoutingRuleParserTest {

  private static final FileDescriptor TESTING_FILE_DESCRIPTOR =
      RoutingRuleParserTestingOuterClass.getDescriptor();
  private static final Map<String, Message> MESSAGES =
      Parser.parseMessages(TESTING_FILE_DESCRIPTOR);
  private static final ServiceDescriptor TESTING_SERVICE =
      TESTING_FILE_DESCRIPTOR.getServices().get(0);

  @Test
  public void shouldReturnEmptyRoutingHeadersIfMethodHasNoRoutingRules() {
    MethodDescriptor rpcMethod = TESTING_SERVICE.getMethods().get(0);
    Message inputMessage = MESSAGES.get("com." + rpcMethod.getInputType().getFullName());
    RoutingHeaders routingHeaders = RoutingRuleParser.parse(rpcMethod, inputMessage, MESSAGES);
    assertThat(routingHeaders.routingHeadersList()).isEmpty();
  }

  @Test
  public void shouldSetPathTemplateToWildcardIfNotDefined() {
    MethodDescriptor rpcMethod = TESTING_SERVICE.getMethods().get(1);
    Message inputMessage = MESSAGES.get("com." + rpcMethod.getInputType().getFullName());
    RoutingHeaders routingHeaders = RoutingRuleParser.parse(rpcMethod, inputMessage, MESSAGES);
    RoutingHeader routingHeader =
        RoutingHeader.create("name", "name", String.format(WILDCARD_PATTERN, "name"));
    assertThat(routingHeaders.routingHeadersList()).containsExactly(routingHeader);
  }

  @Test
  public void shouldThrowExceptionIfPathTemplateHasZeroNamedSegment() {
    MethodDescriptor rpcMethod = TESTING_SERVICE.getMethods().get(2);
    Message inputMessage = MESSAGES.get("com." + rpcMethod.getInputType().getFullName());
    IllegalArgumentException illegalArgumentException =
        assertThrows(
            IllegalArgumentException.class,
            () -> RoutingRuleParser.parse(rpcMethod, inputMessage, MESSAGES));
    assertThat(illegalArgumentException.getMessage())
        .isEqualTo(
            String.format(
                PATH_TEMPLATE_WRONG_NUMBER_OF_NAMED_SEGMENT_ERROR_MESSAGE, "/v1beta1/tests/*"));
  }

  @Test
  public void shouldThrowExceptionIfPathTemplateHasMoreThanOneNamedSegment() {
    MethodDescriptor rpcMethod = TESTING_SERVICE.getMethods().get(3);
    Message inputMessage = MESSAGES.get("com." + rpcMethod.getInputType().getFullName());
    IllegalArgumentException illegalArgumentException =
        assertThrows(
            IllegalArgumentException.class,
            () -> RoutingRuleParser.parse(rpcMethod, inputMessage, MESSAGES));
    assertThat(illegalArgumentException.getMessage())
        .isEqualTo(
            String.format(
                PATH_TEMPLATE_WRONG_NUMBER_OF_NAMED_SEGMENT_ERROR_MESSAGE,
                "/v1beta1/{name=tests/*}/{second_name=*}"));
  }
}
