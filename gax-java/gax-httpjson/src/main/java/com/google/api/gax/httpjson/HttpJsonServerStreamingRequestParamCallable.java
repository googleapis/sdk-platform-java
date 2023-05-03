package com.google.api.gax.httpjson;

import com.google.api.gax.rpc.ApiCallContext;
import com.google.api.gax.rpc.RequestParamsExtractor;
import com.google.api.gax.rpc.RequestUrlParamsEncoder;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.common.base.Preconditions;

public class HttpJsonServerStreamingRequestParamCallable<RequestT, ResponseT>
        extends ServerStreamingCallable<RequestT, ResponseT> {
  private final ServerStreamingCallable<RequestT, ResponseT> callable;
  private final RequestUrlParamsEncoder<RequestT> paramsEncoder;

  HttpJsonServerStreamingRequestParamCallable(
          ServerStreamingCallable<RequestT, ResponseT> callable,
          RequestParamsExtractor<RequestT> paramsExtractor) {
    this.callable = Preconditions.checkNotNull(callable);
    this.paramsEncoder =
            new RequestUrlParamsEncoder<>(Preconditions.checkNotNull(paramsExtractor), false);
  }

  @Override
  public void call(RequestT request, ResponseObserver<ResponseT> responseObserver, ApiCallContext context) {
    HttpJsonCallContext newCallContext =
            HttpJsonCallContext.createDefault()
                    .nullToSelf(context)
                    .withRequestParamsDynamicHeaderOption(paramsEncoder.encode(request));
    callable.call(request, responseObserver, newCallContext);
  }
}
