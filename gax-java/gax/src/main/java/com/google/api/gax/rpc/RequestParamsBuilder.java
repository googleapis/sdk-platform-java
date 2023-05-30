/*
 * Copyright 2022 Google LLC
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

import com.google.api.client.util.escape.PercentEscaper;
import com.google.api.core.BetaApi;
import com.google.api.pathtemplate.PathTemplate;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/**
 * This builder class builds a request params map that will be used by autogenerated implementation
 * of {@link RequestParamsExtractor}.
 */
@BetaApi
public class RequestParamsBuilder {

  private static final PercentEscaper PERCENT_ESCAPER = new PercentEscaper(".-~_");

  private final ImmutableMap.Builder<String, String> paramsBuilder;

  private RequestParamsBuilder() {
    this.paramsBuilder = ImmutableMap.builder();
  }

  public static RequestParamsBuilder create() {
    return new RequestParamsBuilder();
  }

  /**
   * Add an entry to paramsBuilder by match-and-extract field values from requests based on
   * pre-configured path templates. This method is called repeatedly for each configured routing
   * rule parameter, it's possible that the incoming field value from request is null or there is no
   * matches found, we'll continue the match-and-extract process for the next routing rule parameter
   * in such case. This method will percent encode both the header key and header value.
   *
   * @param fieldValue the field value from a request
   * @param headerKey the header key for the routing header param
   * @param pathTemplate {@link PathTemplate} the path template used for match-and-extract
   */
  public void add(String fieldValue, String headerKey, PathTemplate pathTemplate) {
    if (fieldValue == null || fieldValue.isEmpty()) {
      return;
    }
    Map<String, String> matchedValues = pathTemplate.match(fieldValue);
    if (matchedValues != null && matchedValues.containsKey(headerKey)) {
      String encodedKey = percentEncodeString(headerKey);
      String encodedValue = percentEncodeString(matchedValues.get(headerKey));
      paramsBuilder.put(encodedKey, encodedValue);
    }
  }

  /**
   * Add an entry to paramsBuilder with key-value pairing of (headerKey, fieldValue). The only
   * validation done is to ensure the headerKey and fieldValue are not null and non-empty. This
   * method will percent encode both the header key and header value.
   *
   * @param headerKey the header key for the routing header param
   * @param fieldValue the field value from a request
   */
  public void add(String headerKey, String fieldValue) {
    if (headerKey == null || headerKey.isEmpty() || fieldValue == null || fieldValue.isEmpty()) {
      return;
    }
    String encodedKey = percentEncodeString(headerKey);
    String encodedValue = percentEncodeString(fieldValue);
    paramsBuilder.put(encodedKey, encodedValue);
  }

  // Percent encode the value passed in
  private String percentEncodeString(String value) {
    return PERCENT_ESCAPER.escape(value);
  }

  public Map<String, String> build() {
    return paramsBuilder.buildKeepingLast();
  }
}
