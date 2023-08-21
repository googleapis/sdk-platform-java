package com.google.showcase.v1beta1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * This service is used showcase the four main types of rpcs - unary, server
 * side streaming, client side streaming, and bidirectional streaming. This
 * service also exposes methods that explicitly implement server delay, and
 * paginated calls. Set the 'showcase-trailer' metadata key on any method
 * to have the values echoed in the response trailers. Set the 
 * 'x-goog-request-params' metadata key on any method to have the values
 * echoed in the response headers.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: schema/google/showcase/v1beta1/echo.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class EchoGrpc {

  private EchoGrpc() {}

  public static final String SERVICE_NAME = "google.showcase.v1beta1.Echo";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EchoRequest,
      com.google.showcase.v1beta1.EchoResponse> getEchoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Echo",
      requestType = com.google.showcase.v1beta1.EchoRequest.class,
      responseType = com.google.showcase.v1beta1.EchoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EchoRequest,
      com.google.showcase.v1beta1.EchoResponse> getEchoMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EchoRequest, com.google.showcase.v1beta1.EchoResponse> getEchoMethod;
    if ((getEchoMethod = EchoGrpc.getEchoMethod) == null) {
      synchronized (EchoGrpc.class) {
        if ((getEchoMethod = EchoGrpc.getEchoMethod) == null) {
          EchoGrpc.getEchoMethod = getEchoMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.EchoRequest, com.google.showcase.v1beta1.EchoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Echo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EchoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EchoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EchoMethodDescriptorSupplier("Echo"))
              .build();
        }
      }
    }
    return getEchoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.ExpandRequest,
      com.google.showcase.v1beta1.EchoResponse> getExpandMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Expand",
      requestType = com.google.showcase.v1beta1.ExpandRequest.class,
      responseType = com.google.showcase.v1beta1.EchoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.ExpandRequest,
      com.google.showcase.v1beta1.EchoResponse> getExpandMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.ExpandRequest, com.google.showcase.v1beta1.EchoResponse> getExpandMethod;
    if ((getExpandMethod = EchoGrpc.getExpandMethod) == null) {
      synchronized (EchoGrpc.class) {
        if ((getExpandMethod = EchoGrpc.getExpandMethod) == null) {
          EchoGrpc.getExpandMethod = getExpandMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.ExpandRequest, com.google.showcase.v1beta1.EchoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Expand"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.ExpandRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EchoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EchoMethodDescriptorSupplier("Expand"))
              .build();
        }
      }
    }
    return getExpandMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EchoRequest,
      com.google.showcase.v1beta1.EchoResponse> getCollectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Collect",
      requestType = com.google.showcase.v1beta1.EchoRequest.class,
      responseType = com.google.showcase.v1beta1.EchoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EchoRequest,
      com.google.showcase.v1beta1.EchoResponse> getCollectMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EchoRequest, com.google.showcase.v1beta1.EchoResponse> getCollectMethod;
    if ((getCollectMethod = EchoGrpc.getCollectMethod) == null) {
      synchronized (EchoGrpc.class) {
        if ((getCollectMethod = EchoGrpc.getCollectMethod) == null) {
          EchoGrpc.getCollectMethod = getCollectMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.EchoRequest, com.google.showcase.v1beta1.EchoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Collect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EchoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EchoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EchoMethodDescriptorSupplier("Collect"))
              .build();
        }
      }
    }
    return getCollectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EchoRequest,
      com.google.showcase.v1beta1.EchoResponse> getChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Chat",
      requestType = com.google.showcase.v1beta1.EchoRequest.class,
      responseType = com.google.showcase.v1beta1.EchoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EchoRequest,
      com.google.showcase.v1beta1.EchoResponse> getChatMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EchoRequest, com.google.showcase.v1beta1.EchoResponse> getChatMethod;
    if ((getChatMethod = EchoGrpc.getChatMethod) == null) {
      synchronized (EchoGrpc.class) {
        if ((getChatMethod = EchoGrpc.getChatMethod) == null) {
          EchoGrpc.getChatMethod = getChatMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.EchoRequest, com.google.showcase.v1beta1.EchoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Chat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EchoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EchoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EchoMethodDescriptorSupplier("Chat"))
              .build();
        }
      }
    }
    return getChatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.PagedExpandRequest,
      com.google.showcase.v1beta1.PagedExpandResponse> getPagedExpandMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PagedExpand",
      requestType = com.google.showcase.v1beta1.PagedExpandRequest.class,
      responseType = com.google.showcase.v1beta1.PagedExpandResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.PagedExpandRequest,
      com.google.showcase.v1beta1.PagedExpandResponse> getPagedExpandMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.PagedExpandRequest, com.google.showcase.v1beta1.PagedExpandResponse> getPagedExpandMethod;
    if ((getPagedExpandMethod = EchoGrpc.getPagedExpandMethod) == null) {
      synchronized (EchoGrpc.class) {
        if ((getPagedExpandMethod = EchoGrpc.getPagedExpandMethod) == null) {
          EchoGrpc.getPagedExpandMethod = getPagedExpandMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.PagedExpandRequest, com.google.showcase.v1beta1.PagedExpandResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PagedExpand"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.PagedExpandRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.PagedExpandResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EchoMethodDescriptorSupplier("PagedExpand"))
              .build();
        }
      }
    }
    return getPagedExpandMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.PagedExpandLegacyRequest,
      com.google.showcase.v1beta1.PagedExpandResponse> getPagedExpandLegacyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PagedExpandLegacy",
      requestType = com.google.showcase.v1beta1.PagedExpandLegacyRequest.class,
      responseType = com.google.showcase.v1beta1.PagedExpandResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.PagedExpandLegacyRequest,
      com.google.showcase.v1beta1.PagedExpandResponse> getPagedExpandLegacyMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.PagedExpandLegacyRequest, com.google.showcase.v1beta1.PagedExpandResponse> getPagedExpandLegacyMethod;
    if ((getPagedExpandLegacyMethod = EchoGrpc.getPagedExpandLegacyMethod) == null) {
      synchronized (EchoGrpc.class) {
        if ((getPagedExpandLegacyMethod = EchoGrpc.getPagedExpandLegacyMethod) == null) {
          EchoGrpc.getPagedExpandLegacyMethod = getPagedExpandLegacyMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.PagedExpandLegacyRequest, com.google.showcase.v1beta1.PagedExpandResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PagedExpandLegacy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.PagedExpandLegacyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.PagedExpandResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EchoMethodDescriptorSupplier("PagedExpandLegacy"))
              .build();
        }
      }
    }
    return getPagedExpandLegacyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.PagedExpandRequest,
      com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse> getPagedExpandLegacyMappedMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PagedExpandLegacyMapped",
      requestType = com.google.showcase.v1beta1.PagedExpandRequest.class,
      responseType = com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.PagedExpandRequest,
      com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse> getPagedExpandLegacyMappedMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.PagedExpandRequest, com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse> getPagedExpandLegacyMappedMethod;
    if ((getPagedExpandLegacyMappedMethod = EchoGrpc.getPagedExpandLegacyMappedMethod) == null) {
      synchronized (EchoGrpc.class) {
        if ((getPagedExpandLegacyMappedMethod = EchoGrpc.getPagedExpandLegacyMappedMethod) == null) {
          EchoGrpc.getPagedExpandLegacyMappedMethod = getPagedExpandLegacyMappedMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.PagedExpandRequest, com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PagedExpandLegacyMapped"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.PagedExpandRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EchoMethodDescriptorSupplier("PagedExpandLegacyMapped"))
              .build();
        }
      }
    }
    return getPagedExpandLegacyMappedMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.WaitRequest,
      com.google.longrunning.Operation> getWaitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Wait",
      requestType = com.google.showcase.v1beta1.WaitRequest.class,
      responseType = com.google.longrunning.Operation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.WaitRequest,
      com.google.longrunning.Operation> getWaitMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.WaitRequest, com.google.longrunning.Operation> getWaitMethod;
    if ((getWaitMethod = EchoGrpc.getWaitMethod) == null) {
      synchronized (EchoGrpc.class) {
        if ((getWaitMethod = EchoGrpc.getWaitMethod) == null) {
          EchoGrpc.getWaitMethod = getWaitMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.WaitRequest, com.google.longrunning.Operation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Wait"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.WaitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.longrunning.Operation.getDefaultInstance()))
              .setSchemaDescriptor(new EchoMethodDescriptorSupplier("Wait"))
              .build();
        }
      }
    }
    return getWaitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.BlockRequest,
      com.google.showcase.v1beta1.BlockResponse> getBlockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Block",
      requestType = com.google.showcase.v1beta1.BlockRequest.class,
      responseType = com.google.showcase.v1beta1.BlockResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.BlockRequest,
      com.google.showcase.v1beta1.BlockResponse> getBlockMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.BlockRequest, com.google.showcase.v1beta1.BlockResponse> getBlockMethod;
    if ((getBlockMethod = EchoGrpc.getBlockMethod) == null) {
      synchronized (EchoGrpc.class) {
        if ((getBlockMethod = EchoGrpc.getBlockMethod) == null) {
          EchoGrpc.getBlockMethod = getBlockMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.BlockRequest, com.google.showcase.v1beta1.BlockResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Block"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.BlockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.BlockResponse.getDefaultInstance()))
              .setSchemaDescriptor(new EchoMethodDescriptorSupplier("Block"))
              .build();
        }
      }
    }
    return getBlockMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EchoStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EchoStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EchoStub>() {
        @java.lang.Override
        public EchoStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EchoStub(channel, callOptions);
        }
      };
    return EchoStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EchoBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EchoBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EchoBlockingStub>() {
        @java.lang.Override
        public EchoBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EchoBlockingStub(channel, callOptions);
        }
      };
    return EchoBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EchoFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EchoFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EchoFutureStub>() {
        @java.lang.Override
        public EchoFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EchoFutureStub(channel, callOptions);
        }
      };
    return EchoFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * This service is used showcase the four main types of rpcs - unary, server
   * side streaming, client side streaming, and bidirectional streaming. This
   * service also exposes methods that explicitly implement server delay, and
   * paginated calls. Set the 'showcase-trailer' metadata key on any method
   * to have the values echoed in the response trailers. Set the 
   * 'x-goog-request-params' metadata key on any method to have the values
   * echoed in the response headers.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * This method simply echoes the request. This method showcases unary RPCs.
     * </pre>
     */
    default void echo(com.google.showcase.v1beta1.EchoRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEchoMethod(), responseObserver);
    }

    /**
     * <pre>
     * This method splits the given content into words and will pass each word back
     * through the stream. This method showcases server-side streaming RPCs.
     * </pre>
     */
    default void expand(com.google.showcase.v1beta1.ExpandRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExpandMethod(), responseObserver);
    }

    /**
     * <pre>
     * This method will collect the words given to it. When the stream is closed
     * by the client, this method will return the a concatenation of the strings
     * passed to it. This method showcases client-side streaming RPCs.
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoRequest> collect(
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getCollectMethod(), responseObserver);
    }

    /**
     * <pre>
     * This method, upon receiving a request on the stream, will pass the same
     * content back on the stream. This method showcases bidirectional
     * streaming RPCs.
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoRequest> chat(
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getChatMethod(), responseObserver);
    }

    /**
     * <pre>
     * This is similar to the Expand method but instead of returning a stream of
     * expanded words, this method returns a paged list of expanded words.
     * </pre>
     */
    default void pagedExpand(com.google.showcase.v1beta1.PagedExpandRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.PagedExpandResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPagedExpandMethod(), responseObserver);
    }

    /**
     * <pre>
     * This is similar to the PagedExpand except that it uses
     * max_results instead of page_size, as some legacy APIs still
     * do. New APIs should NOT use this pattern.
     * </pre>
     */
    default void pagedExpandLegacy(com.google.showcase.v1beta1.PagedExpandLegacyRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.PagedExpandResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPagedExpandLegacyMethod(), responseObserver);
    }

    /**
     * <pre>
     * This method returns a map containing lists of words that appear in the input, keyed by their
     * initial character. The only words returned are the ones included in the current page,
     * as determined by page_token and page_size, which both refer to the word indices in the
     * input. This paging result consisting of a map of lists is a pattern used by some legacy
     * APIs. New APIs should NOT use this pattern.
     * </pre>
     */
    default void pagedExpandLegacyMapped(com.google.showcase.v1beta1.PagedExpandRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPagedExpandLegacyMappedMethod(), responseObserver);
    }

    /**
     * <pre>
     * This method will wait for the requested amount of time and then return.
     * This method showcases how a client handles a request timeout.
     * </pre>
     */
    default void wait(com.google.showcase.v1beta1.WaitRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWaitMethod(), responseObserver);
    }

    /**
     * <pre>
     * This method will block (wait) for the requested amount of time
     * and then return the response or error.
     * This method showcases how a client handles delays or retries.
     * </pre>
     */
    default void block(com.google.showcase.v1beta1.BlockRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.BlockResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBlockMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Echo.
   * <pre>
   * This service is used showcase the four main types of rpcs - unary, server
   * side streaming, client side streaming, and bidirectional streaming. This
   * service also exposes methods that explicitly implement server delay, and
   * paginated calls. Set the 'showcase-trailer' metadata key on any method
   * to have the values echoed in the response trailers. Set the 
   * 'x-goog-request-params' metadata key on any method to have the values
   * echoed in the response headers.
   * </pre>
   */
  public static abstract class EchoImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return EchoGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Echo.
   * <pre>
   * This service is used showcase the four main types of rpcs - unary, server
   * side streaming, client side streaming, and bidirectional streaming. This
   * service also exposes methods that explicitly implement server delay, and
   * paginated calls. Set the 'showcase-trailer' metadata key on any method
   * to have the values echoed in the response trailers. Set the 
   * 'x-goog-request-params' metadata key on any method to have the values
   * echoed in the response headers.
   * </pre>
   */
  public static final class EchoStub
      extends io.grpc.stub.AbstractAsyncStub<EchoStub> {
    private EchoStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EchoStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EchoStub(channel, callOptions);
    }

    /**
     * <pre>
     * This method simply echoes the request. This method showcases unary RPCs.
     * </pre>
     */
    public void echo(com.google.showcase.v1beta1.EchoRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEchoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * This method splits the given content into words and will pass each word back
     * through the stream. This method showcases server-side streaming RPCs.
     * </pre>
     */
    public void expand(com.google.showcase.v1beta1.ExpandRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getExpandMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * This method will collect the words given to it. When the stream is closed
     * by the client, this method will return the a concatenation of the strings
     * passed to it. This method showcases client-side streaming RPCs.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoRequest> collect(
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getCollectMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * This method, upon receiving a request on the stream, will pass the same
     * content back on the stream. This method showcases bidirectional
     * streaming RPCs.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoRequest> chat(
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getChatMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * This is similar to the Expand method but instead of returning a stream of
     * expanded words, this method returns a paged list of expanded words.
     * </pre>
     */
    public void pagedExpand(com.google.showcase.v1beta1.PagedExpandRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.PagedExpandResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPagedExpandMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * This is similar to the PagedExpand except that it uses
     * max_results instead of page_size, as some legacy APIs still
     * do. New APIs should NOT use this pattern.
     * </pre>
     */
    public void pagedExpandLegacy(com.google.showcase.v1beta1.PagedExpandLegacyRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.PagedExpandResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPagedExpandLegacyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * This method returns a map containing lists of words that appear in the input, keyed by their
     * initial character. The only words returned are the ones included in the current page,
     * as determined by page_token and page_size, which both refer to the word indices in the
     * input. This paging result consisting of a map of lists is a pattern used by some legacy
     * APIs. New APIs should NOT use this pattern.
     * </pre>
     */
    public void pagedExpandLegacyMapped(com.google.showcase.v1beta1.PagedExpandRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPagedExpandLegacyMappedMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * This method will wait for the requested amount of time and then return.
     * This method showcases how a client handles a request timeout.
     * </pre>
     */
    public void wait(com.google.showcase.v1beta1.WaitRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getWaitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * This method will block (wait) for the requested amount of time
     * and then return the response or error.
     * This method showcases how a client handles delays or retries.
     * </pre>
     */
    public void block(com.google.showcase.v1beta1.BlockRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.BlockResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBlockMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Echo.
   * <pre>
   * This service is used showcase the four main types of rpcs - unary, server
   * side streaming, client side streaming, and bidirectional streaming. This
   * service also exposes methods that explicitly implement server delay, and
   * paginated calls. Set the 'showcase-trailer' metadata key on any method
   * to have the values echoed in the response trailers. Set the 
   * 'x-goog-request-params' metadata key on any method to have the values
   * echoed in the response headers.
   * </pre>
   */
  public static final class EchoBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EchoBlockingStub> {
    private EchoBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EchoBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EchoBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * This method simply echoes the request. This method showcases unary RPCs.
     * </pre>
     */
    public com.google.showcase.v1beta1.EchoResponse echo(com.google.showcase.v1beta1.EchoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEchoMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * This method splits the given content into words and will pass each word back
     * through the stream. This method showcases server-side streaming RPCs.
     * </pre>
     */
    public java.util.Iterator<com.google.showcase.v1beta1.EchoResponse> expand(
        com.google.showcase.v1beta1.ExpandRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getExpandMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * This is similar to the Expand method but instead of returning a stream of
     * expanded words, this method returns a paged list of expanded words.
     * </pre>
     */
    public com.google.showcase.v1beta1.PagedExpandResponse pagedExpand(com.google.showcase.v1beta1.PagedExpandRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPagedExpandMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * This is similar to the PagedExpand except that it uses
     * max_results instead of page_size, as some legacy APIs still
     * do. New APIs should NOT use this pattern.
     * </pre>
     */
    public com.google.showcase.v1beta1.PagedExpandResponse pagedExpandLegacy(com.google.showcase.v1beta1.PagedExpandLegacyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPagedExpandLegacyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * This method returns a map containing lists of words that appear in the input, keyed by their
     * initial character. The only words returned are the ones included in the current page,
     * as determined by page_token and page_size, which both refer to the word indices in the
     * input. This paging result consisting of a map of lists is a pattern used by some legacy
     * APIs. New APIs should NOT use this pattern.
     * </pre>
     */
    public com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse pagedExpandLegacyMapped(com.google.showcase.v1beta1.PagedExpandRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPagedExpandLegacyMappedMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * This method will wait for the requested amount of time and then return.
     * This method showcases how a client handles a request timeout.
     * </pre>
     */
    public com.google.longrunning.Operation wait(com.google.showcase.v1beta1.WaitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getWaitMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * This method will block (wait) for the requested amount of time
     * and then return the response or error.
     * This method showcases how a client handles delays or retries.
     * </pre>
     */
    public com.google.showcase.v1beta1.BlockResponse block(com.google.showcase.v1beta1.BlockRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBlockMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Echo.
   * <pre>
   * This service is used showcase the four main types of rpcs - unary, server
   * side streaming, client side streaming, and bidirectional streaming. This
   * service also exposes methods that explicitly implement server delay, and
   * paginated calls. Set the 'showcase-trailer' metadata key on any method
   * to have the values echoed in the response trailers. Set the 
   * 'x-goog-request-params' metadata key on any method to have the values
   * echoed in the response headers.
   * </pre>
   */
  public static final class EchoFutureStub
      extends io.grpc.stub.AbstractFutureStub<EchoFutureStub> {
    private EchoFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EchoFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EchoFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * This method simply echoes the request. This method showcases unary RPCs.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.EchoResponse> echo(
        com.google.showcase.v1beta1.EchoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEchoMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * This is similar to the Expand method but instead of returning a stream of
     * expanded words, this method returns a paged list of expanded words.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.PagedExpandResponse> pagedExpand(
        com.google.showcase.v1beta1.PagedExpandRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPagedExpandMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * This is similar to the PagedExpand except that it uses
     * max_results instead of page_size, as some legacy APIs still
     * do. New APIs should NOT use this pattern.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.PagedExpandResponse> pagedExpandLegacy(
        com.google.showcase.v1beta1.PagedExpandLegacyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPagedExpandLegacyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * This method returns a map containing lists of words that appear in the input, keyed by their
     * initial character. The only words returned are the ones included in the current page,
     * as determined by page_token and page_size, which both refer to the word indices in the
     * input. This paging result consisting of a map of lists is a pattern used by some legacy
     * APIs. New APIs should NOT use this pattern.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse> pagedExpandLegacyMapped(
        com.google.showcase.v1beta1.PagedExpandRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPagedExpandLegacyMappedMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * This method will wait for the requested amount of time and then return.
     * This method showcases how a client handles a request timeout.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> wait(
        com.google.showcase.v1beta1.WaitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getWaitMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * This method will block (wait) for the requested amount of time
     * and then return the response or error.
     * This method showcases how a client handles delays or retries.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.BlockResponse> block(
        com.google.showcase.v1beta1.BlockRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBlockMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ECHO = 0;
  private static final int METHODID_EXPAND = 1;
  private static final int METHODID_PAGED_EXPAND = 2;
  private static final int METHODID_PAGED_EXPAND_LEGACY = 3;
  private static final int METHODID_PAGED_EXPAND_LEGACY_MAPPED = 4;
  private static final int METHODID_WAIT = 5;
  private static final int METHODID_BLOCK = 6;
  private static final int METHODID_COLLECT = 7;
  private static final int METHODID_CHAT = 8;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ECHO:
          serviceImpl.echo((com.google.showcase.v1beta1.EchoRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse>) responseObserver);
          break;
        case METHODID_EXPAND:
          serviceImpl.expand((com.google.showcase.v1beta1.ExpandRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse>) responseObserver);
          break;
        case METHODID_PAGED_EXPAND:
          serviceImpl.pagedExpand((com.google.showcase.v1beta1.PagedExpandRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.PagedExpandResponse>) responseObserver);
          break;
        case METHODID_PAGED_EXPAND_LEGACY:
          serviceImpl.pagedExpandLegacy((com.google.showcase.v1beta1.PagedExpandLegacyRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.PagedExpandResponse>) responseObserver);
          break;
        case METHODID_PAGED_EXPAND_LEGACY_MAPPED:
          serviceImpl.pagedExpandLegacyMapped((com.google.showcase.v1beta1.PagedExpandRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse>) responseObserver);
          break;
        case METHODID_WAIT:
          serviceImpl.wait((com.google.showcase.v1beta1.WaitRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
          break;
        case METHODID_BLOCK:
          serviceImpl.block((com.google.showcase.v1beta1.BlockRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.BlockResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_COLLECT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.collect(
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse>) responseObserver);
        case METHODID_CHAT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.chat(
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EchoResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getEchoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.EchoRequest,
              com.google.showcase.v1beta1.EchoResponse>(
                service, METHODID_ECHO)))
        .addMethod(
          getExpandMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.ExpandRequest,
              com.google.showcase.v1beta1.EchoResponse>(
                service, METHODID_EXPAND)))
        .addMethod(
          getCollectMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.EchoRequest,
              com.google.showcase.v1beta1.EchoResponse>(
                service, METHODID_COLLECT)))
        .addMethod(
          getChatMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.EchoRequest,
              com.google.showcase.v1beta1.EchoResponse>(
                service, METHODID_CHAT)))
        .addMethod(
          getPagedExpandMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.PagedExpandRequest,
              com.google.showcase.v1beta1.PagedExpandResponse>(
                service, METHODID_PAGED_EXPAND)))
        .addMethod(
          getPagedExpandLegacyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.PagedExpandLegacyRequest,
              com.google.showcase.v1beta1.PagedExpandResponse>(
                service, METHODID_PAGED_EXPAND_LEGACY)))
        .addMethod(
          getPagedExpandLegacyMappedMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.PagedExpandRequest,
              com.google.showcase.v1beta1.PagedExpandLegacyMappedResponse>(
                service, METHODID_PAGED_EXPAND_LEGACY_MAPPED)))
        .addMethod(
          getWaitMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.WaitRequest,
              com.google.longrunning.Operation>(
                service, METHODID_WAIT)))
        .addMethod(
          getBlockMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.BlockRequest,
              com.google.showcase.v1beta1.BlockResponse>(
                service, METHODID_BLOCK)))
        .build();
  }

  private static abstract class EchoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EchoBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.google.showcase.v1beta1.EchoOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Echo");
    }
  }

  private static final class EchoFileDescriptorSupplier
      extends EchoBaseDescriptorSupplier {
    EchoFileDescriptorSupplier() {}
  }

  private static final class EchoMethodDescriptorSupplier
      extends EchoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EchoMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EchoGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EchoFileDescriptorSupplier())
              .addMethod(getEchoMethod())
              .addMethod(getExpandMethod())
              .addMethod(getCollectMethod())
              .addMethod(getChatMethod())
              .addMethod(getPagedExpandMethod())
              .addMethod(getPagedExpandLegacyMethod())
              .addMethod(getPagedExpandLegacyMappedMethod())
              .addMethod(getWaitMethod())
              .addMethod(getBlockMethod())
              .build();
        }
      }
    }
    return result;
  }
}
