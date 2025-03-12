package com.google.api.logging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
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
  public void setup() {
    mdc = new HashMap<>();
    mdc.put("json1", "{\n"
        + "  \"@version\": \"1\",\n"
        + "  \"textPayload\": \"Received response\",\n"
        + "  \"response.payload\": {\n"
        + "    \"name\": \"example\",\n"
        + "    \"state\": \"ACTIVE\"\n"
        + "  }\n"
        + "}");
    when(event.getMDCPropertyMap()).thenReturn(mdc);
  }

  @Test
  void testUnwrapped() throws IOException {

    provider.writeTo(generator, event);

    verify(generator).writeFieldName("json1");
    verify(generator).writeTree(any());
  }
}
