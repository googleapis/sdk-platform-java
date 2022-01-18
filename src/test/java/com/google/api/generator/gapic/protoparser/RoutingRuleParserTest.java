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
import static org.junit.Assert.assertThrows;

import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.RoutingHeaders;
import com.google.api.generator.gapic.model.RoutingHeaders.RoutingHeader;
import com.google.explicit.dynamic.routing.header.RoutingRuleParserTestingOuterClass;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
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
  public void parse_shouldReturnNullRoutingHeadersIfMethodHasNoRoutingRules() {
    RoutingHeaders actual = getRoutingHeaders(0);
    assertThat(actual).isNull();
  }

  @Test
  public void parse_shouldSetPathTemplateToWildcardIfNotDefined() {
    RoutingHeaders actual = getRoutingHeaders(1);
    RoutingHeader expected = RoutingHeader.create("name", "name", String.format("{%s=**}", "name"));
    assertThat(actual.routingHeadersList()).containsExactly(expected);
  }

  @Test
  public void parse_shouldThrowExceptionIfPathTemplateHasZeroNamedSegment() {
    IllegalArgumentException illegalArgumentException =
        assertThrows(IllegalArgumentException.class, () -> getRoutingHeaders(2));
    assertThat(illegalArgumentException.getMessage())
        .isEqualTo(
            String.format(
                "There needs to be one and only one named segment in path template %s",
                "/v1beta1/tests/*"));
  }

  @Test
  public void parse_shouldThrowExceptionIfPathTemplateHasMoreThanOneNamedSegment() {
    IllegalArgumentException illegalArgumentException =
        assertThrows(IllegalArgumentException.class, () -> getRoutingHeaders(3));
    assertThat(illegalArgumentException.getMessage())
        .isEqualTo(
            String.format(
                "There needs to be one and only one named segment in path template %s",
                "/v1beta1/{name=tests/*}/{second_name=*}"));
  }

  @Test
  public void parse_shouldParseRoutingRulesWithOneParameter() {
    RoutingHeaders actual = getRoutingHeaders(4);
    RoutingHeader expected = RoutingHeader.create("name", "rename", "/v1beta1/{rename=tests/*}");
    assertThat(actual.routingHeadersList()).containsExactly(expected);
  }

  @Test
  public void parse_shouldParseRoutingRulesWithMultipleParameter() {
    RoutingHeaders actual = getRoutingHeaders(5);
    RoutingHeader expectedHeader1 =
        RoutingHeader.create("name", "rename", "/v1beta1/{rename=tests/*}");
    RoutingHeader expectedHeader2 =
        RoutingHeader.create("routing_id", "id", "/v1beta1/{id=projects/*}/tables/*");
    assertThat(actual.routingHeadersList()).containsExactly(expectedHeader1, expectedHeader2);
  }

  @Test
  public void parse_shouldParseRoutingRulesWithNestedFields() {
    RoutingHeaders actual = getRoutingHeaders(6);
    RoutingHeader expectedHeader1 =
        RoutingHeader.create("account.name", "rename", "/v1beta1/{rename=tests/*}");
    assertThat(actual.routingHeadersList()).containsExactly(expectedHeader1);
  }

  @Test
  public void parse_shouldThrowExceptionIfFieldValidationFailed() {
    assertThrows(Exception.class, () -> getRoutingHeaders(7));
  }

  private RoutingHeaders getRoutingHeaders(int testingIndex) {
    MethodDescriptor rpcMethod = TESTING_SERVICE.getMethods().get(testingIndex);
    Message inputMessage = MESSAGES.get("com." + rpcMethod.getInputType().getFullName());
    return RoutingRuleParser.parse(rpcMethod, inputMessage, MESSAGES);
  }
}
