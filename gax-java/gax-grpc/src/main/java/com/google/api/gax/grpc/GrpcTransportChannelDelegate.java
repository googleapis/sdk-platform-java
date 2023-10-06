/*
 * Copyright 2023 Google LLC
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google LLC nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.google.api.gax.grpc;

import com.google.auth.Credentials;
import io.grpc.ManagedChannel;
import java.io.IOException;

public class GrpcTransportChannelDelegate extends GrpcTransportChannel {

  private GrpcTransportChannel grpcTransportChannel;

  private Credentials credentials;

  private ChannelPoolSettings channelPoolSettings;

  private InstantiatingGrpcChannelProvider instantiatingGrpcChannelProvider;

  public GrpcTransportChannelDelegate(
      ChannelPoolSettings channelPoolSettings,
      InstantiatingGrpcChannelProvider instantiatingGrpcChannelProvider,
      Credentials credentials) {
    this.channelPoolSettings = channelPoolSettings;
    this.instantiatingGrpcChannelProvider = instantiatingGrpcChannelProvider;
    this.credentials = credentials;
  }

  @Override
  ManagedChannel getManagedChannel() {
    if (grpcTransportChannel == null) {
      synchronized (this) {
        if (grpcTransportChannel == null) {
          //            String universeDomain = credentials.getUniverseDomain(); Auth should throw
          // exception if it does not exist
//          String universeDomain = "googleapis.com";
//          String endpoint =
//              instantiatingGrpcChannelProvider.getEndpoint().split(".")[0]
//                  + "."
//                  + universeDomain
//                  + ":443";
          try {
            grpcTransportChannel =
                GrpcTransportChannel.create(
                    ChannelPool.create(
                        channelPoolSettings,
                        () -> instantiatingGrpcChannelProvider.createSingleChannel(instantiatingGrpcChannelProvider.getEndpoint())));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }
    return grpcTransportChannel.getManagedChannel();
  }
}
