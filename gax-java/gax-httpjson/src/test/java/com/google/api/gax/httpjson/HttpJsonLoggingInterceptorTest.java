/*
 * Copyright 2024 Google LLC
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import ch.qos.logback.classic.Level;
import com.google.api.gax.httpjson.ApiMethodDescriptor.MethodType;
import com.google.api.gax.logging.LogData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HttpJsonLoggingInterceptorTest {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(HttpJsonLoggingInterceptorTest.class);

  @SuppressWarnings("unchecked")
  private static final ApiMethodDescriptor<String, Integer> method =
      ApiMethodDescriptor.newBuilder()
          .setType(MethodType.UNARY)
          .setRequestFormatter(mock(HttpRequestFormatter.class))
          .setRequestFormatter(mock(HttpRequestFormatter.class))
          .setFullMethodName("FakeClient/fake-method")
          .setResponseParser(mock(HttpResponseParser.class))
          .build();

  @Test
  void testLogRequestInfo() {

    TestAppender testAppender = setupTestLogger(HttpJsonLoggingInterceptorTest.class);
    HttpJsonLoggingInterceptor interceptor = new HttpJsonLoggingInterceptor();
    interceptor.logRequestInfo(method, "fake.endpoint", LogData.builder(), LOGGER);

    Assertions.assertEquals(1, testAppender.events.size());
    assertEquals(Level.INFO, testAppender.events.get(0).getLevel());
    assertEquals(
        "{\"request.url\":\"fake.endpoint\",\"message\":\"Sending HTTP request\",\"rpcName\":\"FakeClient/fake-method\"}",
        testAppender.events.get(0).getMessage());
    testAppender.stop();
  }

  private TestAppender setupTestLogger(Class<?> clazz) {
    TestAppender testAppender = new TestAppender();
    testAppender.start();
    Logger logger = LoggerFactory.getLogger(clazz);
    ((ch.qos.logback.classic.Logger) logger).addAppender(testAppender);
    return testAppender;
  }
}
