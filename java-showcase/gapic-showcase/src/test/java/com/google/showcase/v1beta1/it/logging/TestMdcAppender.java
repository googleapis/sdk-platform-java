package com.google.showcase.v1beta1.it.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.google.cloud.sdk.logging.SDKLoggingMdcJsonProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.logstash.logback.encoder.LogstashEncoder;

public class TestMdcAppender extends AppenderBase<ILoggingEvent> {

  private final LogstashEncoder encoder;
  private final List<byte[]> byteLists;

  public TestMdcAppender() {
    encoder = new LogstashEncoder();
    encoder.addProvider(new SDKLoggingMdcJsonProvider());
    byteLists = new ArrayList<>();
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
    byteLists.add(encoder.encode(eventObject));
  }

  public List<byte[]> getByteLists() {
    return Collections.unmodifiableList(byteLists);
  }
}
