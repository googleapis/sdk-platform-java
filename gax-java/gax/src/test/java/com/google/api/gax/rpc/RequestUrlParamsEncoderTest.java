/*
 * Copyright 2017 Google LLC
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

package com.google.api.gax.rpc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;
import org.mockito.Mockito;

public class RequestUrlParamsEncoderTest {

  private final String key = "key";

  @Test
  public void testEncode() {
    RequestParamsExtractor<String> extractor = getMockExtractor(ImmutableMap.of("param", "value"));
    RequestUrlParamsEncoder<String> encoder = new RequestUrlParamsEncoder<>(extractor);
    String encodedParams = encoder.encode(key);

    assertEquals("param=value", encodedParams);
  }

  @Test
  public void testEncode_multipleEntriesConcatenation() {
    RequestParamsExtractor<String> extractor =
        getMockExtractor(ImmutableMap.of("param1", "value1", "param2", "value2"));
    RequestUrlParamsEncoder<String> encoder = new RequestUrlParamsEncoder<>(extractor);
    String encodedParams = encoder.encode(key);

    assertEquals("param1=value1&param2=value2", encodedParams);
  }

  @Test
  public void testEncode_multipleEntriesConcatenation_encodedKeyAndValues() {
    RequestParamsExtractor<String> extractor =
        getMockExtractor(ImmutableMap.of("param1!@#", "value+1", "param2()", "value+2+&"));
    RequestUrlParamsEncoder<String> encoder = new RequestUrlParamsEncoder<>(extractor);
    String encodedParams = encoder.encode(key);

    assertEquals("param1%21%40%23=value%2B1&param2%28%29=value%2B2%2B%26", encodedParams);
  }

  @Test
  public void testEncodeEmptyMap() {
    RequestParamsExtractor<String> extractor = getMockExtractor(Collections.emptyMap());
    RequestUrlParamsEncoder<String> encoder = new RequestUrlParamsEncoder<>(extractor);
    String encodedParams = encoder.encode(key);
    assertEquals("", encodedParams);
  }

  private RequestParamsExtractor<String> getMockExtractor(Map<String, String> output) {
    @SuppressWarnings("unchecked")
    RequestParamsExtractor<String> extractor =
        (RequestParamsExtractor<String>) Mockito.mock(RequestParamsExtractor.class);
    when(extractor.extract(key)).thenReturn(output);
    return extractor;
  }
}
