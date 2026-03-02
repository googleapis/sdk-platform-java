/*
 * Copyright 2018 Google LLC
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google LLC nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.google.api.gax.httpjson;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.api.client.http.HttpMethods;
import com.google.api.gax.tracing.ApiTracerContext;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class HttpJsonCallableFactoryTest {
  @Test
  void testGetApiTracerContext() {
    Map<String, String[]> validNames =
        ImmutableMap.of(
            "compute.projects.disableXpnHost", new String[] {"compute.projects", "disableXpnHost"},
            "client.method", new String[] {"client", "method"});

    for (Entry<String, String[]> entry : validNames.entrySet()) {
      @SuppressWarnings("unchecked")
      ApiMethodDescriptor<?, ?> descriptor =
          ApiMethodDescriptor.newBuilder()
              .setFullMethodName(entry.getKey())
              .setHttpMethod(HttpMethods.POST)
              .setRequestFormatter(Mockito.mock(HttpRequestFormatter.class))
              .setResponseParser(Mockito.mock(HttpResponseParser.class))
              .build();

      ApiTracerContext context = HttpJsonCallableFactory.getApiTracerContext(descriptor);
      assertThat(context.getClientName()).isEqualTo(entry.getValue()[0]);
      assertThat(context.getMethodName()).isEqualTo(entry.getValue()[1]);
    }
  }

  @Test
  void testGetApiTracerContextInvalid() {
    List<String> invalidNames = ImmutableList.of("no_split", ".no_client");

    for (String invalidName : invalidNames) {
      @SuppressWarnings("unchecked")
      ApiMethodDescriptor<?, ?> descriptor =
          ApiMethodDescriptor.newBuilder()
              .setFullMethodName(invalidName)
              .setHttpMethod(HttpMethods.POST)
              .setRequestFormatter(Mockito.mock(HttpRequestFormatter.class))
              .setResponseParser(Mockito.mock(HttpResponseParser.class))
              .build();

      IllegalArgumentException actualError = null;
      try {
        ApiTracerContext context = HttpJsonCallableFactory.getApiTracerContext(descriptor);
        context.getClientName();
        assertWithMessage(
                "Invalid method descriptor should not have a valid client name: %s", invalidName)
            .fail();
      } catch (IllegalArgumentException e) {
        actualError = e;
      }
      assertThat(actualError).isNotNull();
    }
  }

  @Test
  void testGetApiTracerContext_populatesHttpFields() {
    HttpRequestFormatter<?> requestFormatter = mock(HttpRequestFormatter.class);
    ProtoPathTemplate pathTemplate = mock(ProtoPathTemplate.class);
    when(pathTemplate.toRawString()).thenReturn("v1/projects/{project}/echo");
    when(requestFormatter.getPathTemplate()).thenReturn(pathTemplate);

    @SuppressWarnings("unchecked")
    ApiMethodDescriptor<?, ?> descriptor =
        ApiMethodDescriptor.newBuilder()
            .setFullMethodName("google.showcase.v1beta1.Echo/Echo")
            .setHttpMethod(HttpMethods.POST)
            .setRequestFormatter(requestFormatter)
            .setResponseParser(mock(HttpResponseParser.class))
            .build();

    ApiTracerContext context = HttpJsonCallableFactory.getApiTracerContext(descriptor);
    assertThat(context.httpMethod()).isEqualTo("POST");
    assertThat(context.httpPathTemplate()).isEqualTo("v1/projects/{project}/echo");
    assertThat(context.transport()).isEqualTo(ApiTracerContext.Transport.HTTP);
  }
}
