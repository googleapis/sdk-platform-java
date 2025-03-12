package com.google.api.logging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SDKLoggingMdcJsonProviderTest {

  private SDKLoggingMdcJsonProvider provider = new SDKLoggingMdcJsonProvider();
  private JsonGenerator generator = mock(JsonGenerator.class);
  private ILoggingEvent event = mock(ILoggingEvent.class);
  private Map<String, String> mdc;

  @BeforeEach
  void init() {
    mdc = new HashMap<>();
    when(event.getMDCPropertyMap()).thenReturn(mdc);
  }

  @Test
  void testWriteJsonStringToJsonTree() throws IOException {
    mdc.put(
        "json1",
        "{\n"
            + "  \"@version\": \"1\",\n"
            + "  \"textPayload\": \"Received response\",\n"
            + "  \"response.payload\": {\n"
            + "    \"name\": \"example\",\n"
            + "    \"state\": \"ACTIVE\"\n"
            + "  }\n"
            + "}");

    provider.writeTo(generator, event);
    verify(generator).writeFieldName("json1");
    verify(generator).writeTree(any(JsonNode.class));
  }

  @Test
  void testWriteIllegalJsonFormatToString() throws IOException {
    mdc.put(
        "json1",
        "{\n"
            + "  \"@version\": \"1\",\n"
            + "  \"textPayload\": \"Received response\",\n"
            + "  \"response.payload\": {\n"
            + "    \"name\": \"example\",\n"
            + "    \"state\": \"ACTIVE\",\n" // the last semicolon is redundant.
            + "  }\n"
            + "}");


    provider.writeTo(generator, event);
    verify(generator).writeFieldName("json1");
    verify(generator, never()).writeTree(any(JsonNode.class));
    verify(generator).writeObject(anyString());
  }
}
