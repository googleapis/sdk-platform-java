package com.google.api.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import net.logstash.logback.composite.loggingevent.MdcJsonProvider;

public class LoggingMdcJsonProvider extends MdcJsonProvider {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
    Map<String, String> mdcProperties = event.getMDCPropertyMap();
    if (mdcProperties != null && !mdcProperties.isEmpty()) {

      boolean hasWrittenStart = false;

      for (Map.Entry<String, String> entry : mdcProperties.entrySet()) {
        // removed ability to override MDC key names
        String fieldName = entry.getKey();

        if (!hasWrittenStart && getFieldName() != null) {
          generator.writeObjectFieldStart(getFieldName());
          hasWrittenStart = true;
        }
        generator.writeFieldName(fieldName);
        String entryValueString = entry.getValue();
        if (isJsonStringJackson(entryValueString)) {
          try {
            generator.writeTree(convertToTreeNode(entryValueString));
          } catch (Exception e) {
            // in case of conversion exception, just use String
            generator.writeObject(entryValueString);
          }
        } else {
          generator.writeObject(entryValueString);
        }
      }
      if (hasWrittenStart) {
        generator.writeEndObject();
      }
    }
  }

  public boolean isJsonStringJackson(String s) {
    try {
      JsonNode jsonNode = objectMapper.readTree(s);
      return jsonNode != null;
    } catch (JsonProcessingException e) {
      return false;
    }
  }

  private JsonNode convertToTreeNode(String jsonString) throws Exception{
    JsonNode jsonNode = objectMapper.readTree(jsonString);
    return jsonNode;
  }
}
