package com.google.showcase.v1beta1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: schema/google/showcase/v1beta1/sequence.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SequenceServiceGrpc {

  private SequenceServiceGrpc() {}

  public static final String SERVICE_NAME = "google.showcase.v1beta1.SequenceService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.CreateSequenceRequest,
      com.google.showcase.v1beta1.Sequence> getCreateSequenceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateSequence",
      requestType = com.google.showcase.v1beta1.CreateSequenceRequest.class,
      responseType = com.google.showcase.v1beta1.Sequence.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.CreateSequenceRequest,
      com.google.showcase.v1beta1.Sequence> getCreateSequenceMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.CreateSequenceRequest, com.google.showcase.v1beta1.Sequence> getCreateSequenceMethod;
    if ((getCreateSequenceMethod = SequenceServiceGrpc.getCreateSequenceMethod) == null) {
      synchronized (SequenceServiceGrpc.class) {
        if ((getCreateSequenceMethod = SequenceServiceGrpc.getCreateSequenceMethod) == null) {
          SequenceServiceGrpc.getCreateSequenceMethod = getCreateSequenceMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.CreateSequenceRequest, com.google.showcase.v1beta1.Sequence>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateSequence"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.CreateSequenceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.Sequence.getDefaultInstance()))
              .setSchemaDescriptor(new SequenceServiceMethodDescriptorSupplier("CreateSequence"))
              .build();
        }
      }
    }
    return getCreateSequenceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.GetSequenceReportRequest,
      com.google.showcase.v1beta1.SequenceReport> getGetSequenceReportMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSequenceReport",
      requestType = com.google.showcase.v1beta1.GetSequenceReportRequest.class,
      responseType = com.google.showcase.v1beta1.SequenceReport.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.GetSequenceReportRequest,
      com.google.showcase.v1beta1.SequenceReport> getGetSequenceReportMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.GetSequenceReportRequest, com.google.showcase.v1beta1.SequenceReport> getGetSequenceReportMethod;
    if ((getGetSequenceReportMethod = SequenceServiceGrpc.getGetSequenceReportMethod) == null) {
      synchronized (SequenceServiceGrpc.class) {
        if ((getGetSequenceReportMethod = SequenceServiceGrpc.getGetSequenceReportMethod) == null) {
          SequenceServiceGrpc.getGetSequenceReportMethod = getGetSequenceReportMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.GetSequenceReportRequest, com.google.showcase.v1beta1.SequenceReport>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSequenceReport"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.GetSequenceReportRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.SequenceReport.getDefaultInstance()))
              .setSchemaDescriptor(new SequenceServiceMethodDescriptorSupplier("GetSequenceReport"))
              .build();
        }
      }
    }
    return getGetSequenceReportMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.AttemptSequenceRequest,
      com.google.protobuf.Empty> getAttemptSequenceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AttemptSequence",
      requestType = com.google.showcase.v1beta1.AttemptSequenceRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.AttemptSequenceRequest,
      com.google.protobuf.Empty> getAttemptSequenceMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.AttemptSequenceRequest, com.google.protobuf.Empty> getAttemptSequenceMethod;
    if ((getAttemptSequenceMethod = SequenceServiceGrpc.getAttemptSequenceMethod) == null) {
      synchronized (SequenceServiceGrpc.class) {
        if ((getAttemptSequenceMethod = SequenceServiceGrpc.getAttemptSequenceMethod) == null) {
          SequenceServiceGrpc.getAttemptSequenceMethod = getAttemptSequenceMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.AttemptSequenceRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AttemptSequence"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.AttemptSequenceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new SequenceServiceMethodDescriptorSupplier("AttemptSequence"))
              .build();
        }
      }
    }
    return getAttemptSequenceMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SequenceServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SequenceServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SequenceServiceStub>() {
        @java.lang.Override
        public SequenceServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SequenceServiceStub(channel, callOptions);
        }
      };
    return SequenceServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SequenceServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SequenceServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SequenceServiceBlockingStub>() {
        @java.lang.Override
        public SequenceServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SequenceServiceBlockingStub(channel, callOptions);
        }
      };
    return SequenceServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SequenceServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SequenceServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SequenceServiceFutureStub>() {
        @java.lang.Override
        public SequenceServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SequenceServiceFutureStub(channel, callOptions);
        }
      };
    return SequenceServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SequenceServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void createSequence(com.google.showcase.v1beta1.CreateSequenceRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.Sequence> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateSequenceMethod(), responseObserver);
    }

    /**
     */
    public void getSequenceReport(com.google.showcase.v1beta1.GetSequenceReportRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.SequenceReport> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSequenceReportMethod(), responseObserver);
    }

    /**
     */
    public void attemptSequence(com.google.showcase.v1beta1.AttemptSequenceRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAttemptSequenceMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateSequenceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.CreateSequenceRequest,
                com.google.showcase.v1beta1.Sequence>(
                  this, METHODID_CREATE_SEQUENCE)))
          .addMethod(
            getGetSequenceReportMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.GetSequenceReportRequest,
                com.google.showcase.v1beta1.SequenceReport>(
                  this, METHODID_GET_SEQUENCE_REPORT)))
          .addMethod(
            getAttemptSequenceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.AttemptSequenceRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_ATTEMPT_SEQUENCE)))
          .build();
    }
  }

  /**
   */
  public static final class SequenceServiceStub extends io.grpc.stub.AbstractAsyncStub<SequenceServiceStub> {
    private SequenceServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SequenceServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SequenceServiceStub(channel, callOptions);
    }

    /**
     */
    public void createSequence(com.google.showcase.v1beta1.CreateSequenceRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.Sequence> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateSequenceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getSequenceReport(com.google.showcase.v1beta1.GetSequenceReportRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.SequenceReport> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSequenceReportMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void attemptSequence(com.google.showcase.v1beta1.AttemptSequenceRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAttemptSequenceMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SequenceServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SequenceServiceBlockingStub> {
    private SequenceServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SequenceServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SequenceServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.showcase.v1beta1.Sequence createSequence(com.google.showcase.v1beta1.CreateSequenceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateSequenceMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.showcase.v1beta1.SequenceReport getSequenceReport(com.google.showcase.v1beta1.GetSequenceReportRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSequenceReportMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty attemptSequence(com.google.showcase.v1beta1.AttemptSequenceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAttemptSequenceMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SequenceServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SequenceServiceFutureStub> {
    private SequenceServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SequenceServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SequenceServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.Sequence> createSequence(
        com.google.showcase.v1beta1.CreateSequenceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateSequenceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.SequenceReport> getSequenceReport(
        com.google.showcase.v1beta1.GetSequenceReportRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSequenceReportMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> attemptSequence(
        com.google.showcase.v1beta1.AttemptSequenceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAttemptSequenceMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_SEQUENCE = 0;
  private static final int METHODID_GET_SEQUENCE_REPORT = 1;
  private static final int METHODID_ATTEMPT_SEQUENCE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SequenceServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SequenceServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_SEQUENCE:
          serviceImpl.createSequence((com.google.showcase.v1beta1.CreateSequenceRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.Sequence>) responseObserver);
          break;
        case METHODID_GET_SEQUENCE_REPORT:
          serviceImpl.getSequenceReport((com.google.showcase.v1beta1.GetSequenceReportRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.SequenceReport>) responseObserver);
          break;
        case METHODID_ATTEMPT_SEQUENCE:
          serviceImpl.attemptSequence((com.google.showcase.v1beta1.AttemptSequenceRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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

  private static abstract class SequenceServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SequenceServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.google.showcase.v1beta1.SequenceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SequenceService");
    }
  }

  private static final class SequenceServiceFileDescriptorSupplier
      extends SequenceServiceBaseDescriptorSupplier {
    SequenceServiceFileDescriptorSupplier() {}
  }

  private static final class SequenceServiceMethodDescriptorSupplier
      extends SequenceServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SequenceServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SequenceServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SequenceServiceFileDescriptorSupplier())
              .addMethod(getCreateSequenceMethod())
              .addMethod(getGetSequenceReportMethod())
              .addMethod(getAttemptSequenceMethod())
              .build();
        }
      }
    }
    return result;
  }
}
