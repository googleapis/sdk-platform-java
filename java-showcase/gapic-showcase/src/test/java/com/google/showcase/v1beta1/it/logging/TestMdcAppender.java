package com.google.showcase.v1beta1.it.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.google.api.logging.SDKLoggingMdcJsonProvider;

public class TestMdcAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

  private final SDKLoggingMdcJsonProvider provider = new SDKLoggingMdcJsonProvider();

  @Override
  protected void append(ILoggingEvent eventObject) {}
}
