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

      if (!hasWrittenStart && getFieldName() != null) {
        generator.writeObjectFieldStart(getFieldName());
        hasWrittenStart = true;
      }
      generator.writeFieldName(fieldName);
      String entryValueString = entry.getValue();
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
