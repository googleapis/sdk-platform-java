package com.google.api.gax.grpc;

import com.google.auth.Credentials;
import io.grpc.ManagedChannel;

import java.io.IOException;

public class GrpcTransportChannelDelegate extends GrpcTransportChannel {

    private GrpcTransportChannel grpcTransportChannel;

    private Credentials credentials;

    private ChannelPoolSettings channelPoolSettings;
    private InstantiatingGrpcChannelProvider instantiatingGrpcChannelProvider;
    public GrpcTransportChannelDelegate(ChannelPoolSettings channelPoolSettings, InstantiatingGrpcChannelProvider instantiatingGrpcChannelProvider, Credentials credentials) {
        this.channelPoolSettings = channelPoolSettings;
        this.instantiatingGrpcChannelProvider = instantiatingGrpcChannelProvider;
        this.credentials = credentials;
    }

    @Override
    ManagedChannel getManagedChannel() {
        if (grpcTransportChannel == null) {
            synchronized (this) {
                if (grpcTransportChannel == null) {
//            String universeDomain = credentials.getUniverseDomain(); Auth should throw exception if it does not exist
                    String universeDomain = "googleapis.com";
                    String endpoint = instantiatingGrpcChannelProvider.getEndpoint().split(".")[0] + "." + universeDomain + ":443";
                    try {
                        grpcTransportChannel = GrpcTransportChannel.create(
                                ChannelPool.create(
                                        channelPoolSettings, () -> instantiatingGrpcChannelProvider.createSingleChannel(endpoint)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
        return grpcTransportChannel.getManagedChannel();
    }

}
