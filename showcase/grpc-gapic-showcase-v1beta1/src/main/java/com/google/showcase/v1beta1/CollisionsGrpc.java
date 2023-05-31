package com.google.showcase.v1beta1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: showcase/gapic-showcase-extended/proto/collisions.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CollisionsGrpc {

  private CollisionsGrpc() {}

  public static final String SERVICE_NAME = "google.showcase.v1beta1.Collisions";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.Request,
      com.google.longrunning.Operation> getDoSomethingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "doSomething",
      requestType = com.google.showcase.v1beta1.Request.class,
      responseType = com.google.longrunning.Operation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.Request,
      com.google.longrunning.Operation> getDoSomethingMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.Request, com.google.longrunning.Operation> getDoSomethingMethod;
    if ((getDoSomethingMethod = CollisionsGrpc.getDoSomethingMethod) == null) {
      synchronized (CollisionsGrpc.class) {
        if ((getDoSomethingMethod = CollisionsGrpc.getDoSomethingMethod) == null) {
          CollisionsGrpc.getDoSomethingMethod = getDoSomethingMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.Request, com.google.longrunning.Operation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "doSomething"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.longrunning.Operation.getDefaultInstance()))
              .setSchemaDescriptor(new CollisionsMethodDescriptorSupplier("doSomething"))
              .build();
        }
      }
    }
    return getDoSomethingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CollisionsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollisionsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollisionsStub>() {
        @java.lang.Override
        public CollisionsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollisionsStub(channel, callOptions);
        }
      };
    return CollisionsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CollisionsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollisionsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollisionsBlockingStub>() {
        @java.lang.Override
        public CollisionsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollisionsBlockingStub(channel, callOptions);
        }
      };
    return CollisionsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CollisionsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CollisionsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CollisionsFutureStub>() {
        @java.lang.Override
        public CollisionsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CollisionsFutureStub(channel, callOptions);
        }
      };
    return CollisionsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void doSomething(com.google.showcase.v1beta1.Request request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDoSomethingMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Collisions.
   */
  public static abstract class CollisionsImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CollisionsGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Collisions.
   */
  public static final class CollisionsStub
      extends io.grpc.stub.AbstractAsyncStub<CollisionsStub> {
    private CollisionsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollisionsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollisionsStub(channel, callOptions);
    }

    /**
     */
    public void doSomething(com.google.showcase.v1beta1.Request request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDoSomethingMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Collisions.
   */
  public static final class CollisionsBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CollisionsBlockingStub> {
    private CollisionsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollisionsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollisionsBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.longrunning.Operation doSomething(com.google.showcase.v1beta1.Request request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDoSomethingMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Collisions.
   */
  public static final class CollisionsFutureStub
      extends io.grpc.stub.AbstractFutureStub<CollisionsFutureStub> {
    private CollisionsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CollisionsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CollisionsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> doSomething(
        com.google.showcase.v1beta1.Request request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDoSomethingMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_DO_SOMETHING = 0;

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
        case METHODID_DO_SOMETHING:
          serviceImpl.doSomething((com.google.showcase.v1beta1.Request) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getDoSomethingMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.showcase.v1beta1.Request,
              com.google.longrunning.Operation>(
                service, METHODID_DO_SOMETHING)))
        .build();
  }

  private static abstract class CollisionsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CollisionsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.google.showcase.v1beta1.CollisionsOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Collisions");
    }
  }

  private static final class CollisionsFileDescriptorSupplier
      extends CollisionsBaseDescriptorSupplier {
    CollisionsFileDescriptorSupplier() {}
  }

  private static final class CollisionsMethodDescriptorSupplier
      extends CollisionsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CollisionsMethodDescriptorSupplier(String methodName) {
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
      synchronized (CollisionsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CollisionsFileDescriptorSupplier())
              .addMethod(getDoSomethingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
