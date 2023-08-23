package com.google.api.gax.tracing;

public interface ClientMetricsTracer {

  void recordCurrentChannelSize(int channelSize);

  default void recordGaxThread(int threadCount) {};

  default String channelSizeName() {
    return "channel_size";
  };
}
