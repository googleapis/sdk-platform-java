package com.google.api.gax.httpjson;

import com.google.api.core.ApiFuture;
import com.google.api.gax.rpc.ApiCallContext;
import com.google.api.gax.rpc.RequestParamsExtractor;
import com.google.api.gax.rpc.RequestUrlParamsEncoder;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.common.base.Preconditions;

/**
 * A {@code UnaryCallable} that extracts values from the fields of the request and inserts them into
 * headers.
 *
 * <p>Package-private for internal usage.
 */
class HttpJsonUnaryRequestParamCallable<RequestT, ResponseT> extends UnaryCallable<RequestT, ResponseT> {
  private final UnaryCallable<RequestT, ResponseT> callable;
  private final RequestUrlParamsEncoder<RequestT> paramsEncoder;

  public HttpJsonUnaryRequestParamCallable(UnaryCallable<RequestT, ResponseT> callable, RequestParamsExtractor<RequestT> paramsExtractor) {
    this.callable = Preconditions.checkNotNull(callable);
    this.paramsEncoder =
            new RequestUrlParamsEncoder<>(Preconditions.checkNotNull(paramsExtractor), false);
  }

  @Override
  public ApiFuture<ResponseT> futureCall(RequestT request, ApiCallContext context) {
    HttpJsonCallContext newCallContext =
            HttpJsonCallContext.createDefault()
                    .nullToSelf(context)
                    .withRequestParamsDynamicHeaderOption(paramsEncoder.encode(request));

    return callable.futureCall(request, newCallContext);
  }
}
