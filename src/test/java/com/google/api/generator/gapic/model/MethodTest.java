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

package com.google.api.generator.gapic.model;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.model.HttpBindings.HttpBinding;
import com.google.api.generator.gapic.model.HttpBindings.HttpVerb;
import com.google.api.generator.gapic.model.RoutingHeaderRule.RoutingHeaderParam;
import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import org.junit.Test;

public class MethodTest {

  private static final Method METHOD =
      Method.builder()
          .setName("My method")
          .setInputType(TypeNode.STRING)
          .setOutputType(TypeNode.STRING)
          .build();
  private static final HttpBindings HTTP_BINDINGS =
      HttpBindings.builder()
          .setPathParameters(ImmutableSet.of(HttpBinding.create("table", true, false, "")))
          .setPattern("/pattern/test")
          .setAdditionalPatterns(Arrays.asList("/extra_pattern/test", "/extra_pattern/hey"))
          .setIsAsteriskBody(false)
          .setHttpVerb(HttpVerb.GET)
          .build();

  @Test
  public void toStream() {
    // Argument order: isClientStreaming, isServerStreaming.
    assertThat(Method.toStream(false, false)).isEqualTo(Method.Stream.NONE);
    assertThat(Method.toStream(true, false)).isEqualTo(Method.Stream.CLIENT);
    assertThat(Method.toStream(false, true)).isEqualTo(Method.Stream.SERVER);
    assertThat(Method.toStream(true, true)).isEqualTo(Method.Stream.BIDI);
  }

  @Test
  public void hasRoutingHeaders_shouldReturnFalseIfRoutingHeadersIsNull() {
    assertThat(METHOD.hasRoutingHeaderParams()).isFalse();
  }

  @Test
  public void hasRoutingHeaders_shouldReturnFalseIfRoutingHeadersIsEmpty() {
    Method method =
        METHOD.toBuilder().setRoutingHeaderRule(RoutingHeaderRule.builder().build()).build();
    assertThat(method.hasRoutingHeaderParams()).isFalse();
  }

  @Test
  public void hasRoutingHeaders_shouldReturnTrueIfRoutingHeadersIsNotEmpty() {
    Method method =
        METHOD
            .toBuilder()
            .setRoutingHeaderRule(
                RoutingHeaderRule.builder()
                    .addParam(RoutingHeaderParam.create("table", "routing_id", ""))
                    .build())
            .build();
    assertThat(method.hasRoutingHeaderParams()).isTrue();
  }

  @Test
  public void shouldSetParamsExtractor_shouldReturnTrueIfHasRoutingHeaders() {
    Method method =
        METHOD
            .toBuilder()
            .setRoutingHeaderRule(
                RoutingHeaderRule.builder()
                    .addParam(RoutingHeaderParam.create("table", "routing_id", ""))
                    .build())
            .build();
    assertThat(method.shouldSetParamsExtractor()).isTrue();
  }

  @Test
  public void shouldSetParamsExtractor_shouldReturnTrueIfHasHttpBindingsAndRoutingHeadersIsNull() {
    Method method =
        METHOD.toBuilder().setHttpBindings(HTTP_BINDINGS).setRoutingHeaderRule(null).build();
    assertThat(method.shouldSetParamsExtractor()).isTrue();
  }

  @Test
  public void
      shouldSetParamsExtractor_shouldReturnFalseIfHasHttpBindingsAndRoutingHeadersIsEmpty() {
    Method method =
        METHOD
            .toBuilder()
            .setHttpBindings(HTTP_BINDINGS)
            .setRoutingHeaderRule(RoutingHeaderRule.builder().build())
            .build();
    assertThat(method.shouldSetParamsExtractor()).isFalse();
  }

  @Test
  public void shouldSetParamsExtractor_shouldReturnFalseIfHasNoHttpBindingsAndNoRoutingHeaders() {
    Method method = METHOD.toBuilder().setHttpBindings(null).setRoutingHeaderRule(null).build();
    assertThat(method.shouldSetParamsExtractor()).isFalse();
  }
}
