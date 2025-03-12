package com.google.api.logging;

import static org.mockito.Mockito.when;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

public class SDKLoggingMdcJsonProviderTest {
  private SDKLoggingMdcJsonProvider provider = new SDKLoggingMdcJsonProvider();

  @Mock
  private JsonGenerator generator;

  @Mock
  private ILoggingEvent event;

  private Map<String, String> mdc;

  @BeforeEach
  public void setup() {
    mdc = new LinkedHashMap<>();
    mdc.put("name1", "value1");
    mdc.put("name2", "value2");
    mdc.put("name3", "value3");
    when(event.getMDCPropertyMap()).thenReturn(mdc);
  }
}
