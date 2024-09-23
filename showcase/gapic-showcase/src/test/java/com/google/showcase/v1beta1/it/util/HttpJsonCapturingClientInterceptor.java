/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.showcase.v1beta1.it.util;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.httpjson.HttpJsonClientInterceptor;
import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.api.gax.rpc.StubSettings;
import com.google.common.collect.ImmutableList;
import com.google.showcase.v1beta1.it.util.GrpcCapturingClientInterceptor;
import com.google.showcase.v1beta1.it.util.HttpJsonCapturingClientInterceptor;
import com.google.showcase.v1beta1.it.util.TestClientInitializer;
import com.google.showcase.v1beta1.stub.ComplianceStubSettings;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import com.google.api.gax.httpjson.HttpJsonMetadata;
import java.io.IOException;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.google.api.gax.httpjson.HttpJsonCallOptions;
import com.google.api.gax.httpjson.HttpJsonChannel;
import com.google.api.gax.httpjson.HttpJsonClientCall;
import com.google.api.gax.httpjson.ApiMethodDescriptor;
import com.google.api.gax.httpjson.ForwardingHttpJsonClientCall;
import com.google.api.gax.httpjson.ForwardingHttpJsonClientCallListener;
import io.grpc.Status;

/** Implements a client interceptor to retrieve the response headers from a HTTP client request. */
public class HttpJsonCapturingClientInterceptor implements HttpJsonClientInterceptor {
    public HttpJsonMetadata metadata;

    @Override
    public <RequestT, ResponseT> HttpJsonClientCall<RequestT, ResponseT> interceptCall(
            ApiMethodDescriptor<RequestT, ResponseT> method,
            HttpJsonCallOptions callOptions,
            HttpJsonChannel next) {
        HttpJsonClientCall<RequestT, ResponseT> call = next.newCall(method, callOptions);
        return new ForwardingHttpJsonClientCall.SimpleForwardingHttpJsonClientCall<
                RequestT, ResponseT>(call) {
            @Override
            public void start(Listener<ResponseT> responseListener, HttpJsonMetadata requestHeaders) {
                Listener<ResponseT> forwardingResponseListener =
                        new ForwardingHttpJsonClientCallListener.SimpleForwardingHttpJsonClientCallListener<
                                ResponseT>(responseListener) {
                            @Override
                            public void onHeaders(HttpJsonMetadata responseHeaders) {
                                metadata = responseHeaders;
                                super.onHeaders(responseHeaders);
                            }

                            @Override
                            public void onMessage(ResponseT message) {
                                super.onMessage(message);
                            }

                            @Override
                            public void onClose(int statusCode, HttpJsonMetadata trailers) {
                                super.onClose(statusCode, trailers);
                            }
                        };

                super.start(forwardingResponseListener, requestHeaders);
            }
        };
    }
}