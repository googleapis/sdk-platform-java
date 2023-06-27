package com.google.api.gax.tracing;

public interface ClientMetricsTracer {

    void recordCurrentChannelSize(int channelSize);

    default String channelSizeName() {
        return "channel_size";
    };
}
