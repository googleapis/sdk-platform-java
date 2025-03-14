package com.google.showcase.v1beta1.it.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.logging.SDKLoggingMdcJsonProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.logstash.logback.encoder.LogstashEncoder;

public class TestMdcAppender extends AppenderBase<ILoggingEvent> {

  private final LogstashEncoder encoder;

  private final ObjectMapper objectMapper;

  private final List<JsonNode> jsonNodes;

  public TestMdcAppender() {
    encoder = new LogstashEncoder();
    encoder.addProvider(new SDKLoggingMdcJsonProvider());
    objectMapper = new ObjectMapper();
    jsonNodes = new ArrayList<>();
  }

  @Override
  public void start() {
    encoder.start();
    super.start();
  }

  @Override
  public void stop() {
    encoder.stop();
    super.stop();
  }

  @Override
  protected void append(ILoggingEvent eventObject) {
    byte[] encodedBytes = encoder.encode(eventObject);
    try {
      jsonNodes.add(objectMapper.readTree(encodedBytes));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<JsonNode> getLoggingEntries() {
    return Collections.unmodifiableList(jsonNodes);
  }
}
