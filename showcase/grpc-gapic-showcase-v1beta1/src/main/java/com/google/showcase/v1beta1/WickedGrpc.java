package com.google.showcase.v1beta1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: showcase/gapic-showcase-extended/proto/wicked.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class WickedGrpc {

  private WickedGrpc() {}

  public static final String SERVICE_NAME = "google.showcase.v1beta1.Wicked";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EvilRequest,
      com.google.showcase.v1beta1.EvilResponse> getCraftEvilPlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CraftEvilPlan",
      requestType = com.google.showcase.v1beta1.EvilRequest.class,
      responseType = com.google.showcase.v1beta1.EvilResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EvilRequest,
      com.google.showcase.v1beta1.EvilResponse> getCraftEvilPlanMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EvilRequest, com.google.showcase.v1beta1.EvilResponse> getCraftEvilPlanMethod;
    if ((getCraftEvilPlanMethod = WickedGrpc.getCraftEvilPlanMethod) == null) {
      synchronized (WickedGrpc.class) {
        if ((getCraftEvilPlanMethod = WickedGrpc.getCraftEvilPlanMethod) == null) {
          WickedGrpc.getCraftEvilPlanMethod = getCraftEvilPlanMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.EvilRequest, com.google.showcase.v1beta1.EvilResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CraftEvilPlan"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EvilRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EvilResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WickedMethodDescriptorSupplier("CraftEvilPlan"))
              .build();
        }
      }
    }
    return getCraftEvilPlanMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EvilRequest,
      com.google.showcase.v1beta1.EvilResponse> getBrainstormEvilPlansMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "BrainstormEvilPlans",
      requestType = com.google.showcase.v1beta1.EvilRequest.class,
      responseType = com.google.showcase.v1beta1.EvilResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EvilRequest,
      com.google.showcase.v1beta1.EvilResponse> getBrainstormEvilPlansMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EvilRequest, com.google.showcase.v1beta1.EvilResponse> getBrainstormEvilPlansMethod;
    if ((getBrainstormEvilPlansMethod = WickedGrpc.getBrainstormEvilPlansMethod) == null) {
      synchronized (WickedGrpc.class) {
        if ((getBrainstormEvilPlansMethod = WickedGrpc.getBrainstormEvilPlansMethod) == null) {
          WickedGrpc.getBrainstormEvilPlansMethod = getBrainstormEvilPlansMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.EvilRequest, com.google.showcase.v1beta1.EvilResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "BrainstormEvilPlans"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EvilRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EvilResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WickedMethodDescriptorSupplier("BrainstormEvilPlans"))
              .build();
        }
      }
    }
    return getBrainstormEvilPlansMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EvilRequest,
      com.google.showcase.v1beta1.EvilResponse> getPersuadeEvilPlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PersuadeEvilPlan",
      requestType = com.google.showcase.v1beta1.EvilRequest.class,
      responseType = com.google.showcase.v1beta1.EvilResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EvilRequest,
      com.google.showcase.v1beta1.EvilResponse> getPersuadeEvilPlanMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EvilRequest, com.google.showcase.v1beta1.EvilResponse> getPersuadeEvilPlanMethod;
    if ((getPersuadeEvilPlanMethod = WickedGrpc.getPersuadeEvilPlanMethod) == null) {
      synchronized (WickedGrpc.class) {
        if ((getPersuadeEvilPlanMethod = WickedGrpc.getPersuadeEvilPlanMethod) == null) {
          WickedGrpc.getPersuadeEvilPlanMethod = getPersuadeEvilPlanMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.EvilRequest, com.google.showcase.v1beta1.EvilResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PersuadeEvilPlan"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EvilRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EvilResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WickedMethodDescriptorSupplier("PersuadeEvilPlan"))
              .build();
        }
      }
    }
    return getPersuadeEvilPlanMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WickedStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WickedStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WickedStub>() {
        @java.lang.Override
        public WickedStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WickedStub(channel, callOptions);
        }
      };
    return WickedStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WickedBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WickedBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WickedBlockingStub>() {
        @java.lang.Override
        public WickedBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WickedBlockingStub(channel, callOptions);
        }
      };
    return WickedBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WickedFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WickedFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WickedFutureStub>() {
        @java.lang.Override
        public WickedFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WickedFutureStub(channel, callOptions);
        }
      };
    return WickedFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void craftEvilPlan(com.google.showcase.v1beta1.EvilRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCraftEvilPlanMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilRequest> brainstormEvilPlans(
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getBrainstormEvilPlansMethod(), responseObserver);
    }

    /**
     */
    default io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilRequest> persuadeEvilPlan(
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getPersuadeEvilPlanMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Wicked.
   */
  public static abstract class WickedImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return WickedGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Wicked.
   */
  public static final class WickedStub
      extends io.grpc.stub.AbstractAsyncStub<WickedStub> {
    private WickedStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WickedStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WickedStub(channel, callOptions);
    }

    /**
     */
    public void craftEvilPlan(com.google.showcase.v1beta1.EvilRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCraftEvilPlanMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilRequest> brainstormEvilPlans(
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getBrainstormEvilPlansMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilRequest> persuadeEvilPlan(
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getPersuadeEvilPlanMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Wicked.
   */
  public static final class WickedBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<WickedBlockingStub> {
    private WickedBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WickedBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WickedBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.showcase.v1beta1.EvilResponse craftEvilPlan(com.google.showcase.v1beta1.EvilRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCraftEvilPlanMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Wicked.
   */
  public static final class WickedFutureStub
      extends io.grpc.stub.AbstractFutureStub<WickedFutureStub> {
    private WickedFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WickedFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WickedFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.EvilResponse> craftEvilPlan(
        com.google.showcase.v1beta1.EvilRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCraftEvilPlanMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CRAFT_EVIL_PLAN = 0;
  private static final int METHODID_BRAINSTORM_EVIL_PLANS = 1;
  private static final int METHODID_PERSUADE_EVIL_PLAN = 2;

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
        case METHODID_CRAFT_EVIL_PLAN:
          serviceImpl.craftEvilPlan((com.google.showcase.v1beta1.EvilRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilResponse>) responseObserver);
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
        case METHODID_BRAINSTORM_EVIL_PLANS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.brainstormEvilPlans(
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilResponse>) responseObserver);
        case METHODID_PERSUADE_EVIL_PLAN:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.persuadeEvilPlan(
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EvilResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCraftEvilPlanMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.EvilRequest,
              com.google.showcase.v1beta1.EvilResponse>(
                service, METHODID_CRAFT_EVIL_PLAN)))
        .addMethod(
          getBrainstormEvilPlansMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.EvilRequest,
              com.google.showcase.v1beta1.EvilResponse>(
                service, METHODID_BRAINSTORM_EVIL_PLANS)))
        .addMethod(
          getPersuadeEvilPlanMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.EvilRequest,
              com.google.showcase.v1beta1.EvilResponse>(
                service, METHODID_PERSUADE_EVIL_PLAN)))
        .build();
  }

  private static abstract class WickedBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WickedBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.google.showcase.v1beta1.WickedOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Wicked");
    }
  }

  private static final class WickedFileDescriptorSupplier
      extends WickedBaseDescriptorSupplier {
    WickedFileDescriptorSupplier() {}
  }

  private static final class WickedMethodDescriptorSupplier
      extends WickedBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WickedMethodDescriptorSupplier(String methodName) {
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
      synchronized (WickedGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WickedFileDescriptorSupplier())
              .addMethod(getCraftEvilPlanMethod())
              .addMethod(getBrainstormEvilPlansMethod())
              .addMethod(getPersuadeEvilPlanMethod())
              .build();
        }
      }
    }
    return result;
  }
}
