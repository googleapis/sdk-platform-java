/*
 * Copyright 2025 Google LLC
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

package com.google.api.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import net.logstash.logback.composite.loggingevent.MdcJsonProvider;

public class SDKLoggingMdcJsonProvider extends MdcJsonProvider {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
    Map<String, String> mdcProperties = event.getMDCPropertyMap();
    if (mdcProperties == null || mdcProperties.isEmpty()) {
      return;
    }

    boolean hasWrittenStart = false;
    for (Map.Entry<String, String> entry : mdcProperties.entrySet()) {
      String fieldName = entry.getKey();
      String entryValueString = entry.getValue();
      // an entry will be skipped if one of the scenario happens:
      // 1. key or value is null
      // 2. includeMdcKeyNames is not empty and the key is not in the list
      // 3. excludeMdcKeyNames is not empty and the key is in the list
      if (fieldName == null || entryValueString == null
          || !(getIncludeMdcKeyNames().isEmpty() || getIncludeMdcKeyNames().contains(fieldName))
          || (!getExcludeMdcKeyNames().isEmpty() && getExcludeMdcKeyNames().contains(fieldName))) {
        continue;
      }

      if (!hasWrittenStart && getFieldName() != null) {
        generator.writeObjectFieldStart(getFieldName());
        hasWrittenStart = true;
      }
      generator.writeFieldName(fieldName);

      try {
        generator.writeTree(convertToTreeNode(entryValueString));
      } catch (JsonProcessingException e) {
        // in case of conversion exception, just use String
        generator.writeObject(entryValueString);
      }
    }
    if (hasWrittenStart) {
      generator.writeEndObject();
    }
  }

  private JsonNode convertToTreeNode(String jsonString) throws JsonProcessingException {
    return objectMapper.readTree(jsonString);
  }
}
