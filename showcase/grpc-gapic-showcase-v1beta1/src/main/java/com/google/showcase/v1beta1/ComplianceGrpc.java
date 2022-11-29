package com.google.showcase.v1beta1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: schema/google/showcase/v1beta1/compliance.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ComplianceGrpc {

  private ComplianceGrpc() {}

  public static final String SERVICE_NAME = "google.showcase.v1beta1.Compliance";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RepeatDataBody",
      requestType = com.google.showcase.v1beta1.RepeatRequest.class,
      responseType = com.google.showcase.v1beta1.RepeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyMethod;
    if ((getRepeatDataBodyMethod = ComplianceGrpc.getRepeatDataBodyMethod) == null) {
      synchronized (ComplianceGrpc.class) {
        if ((getRepeatDataBodyMethod = ComplianceGrpc.getRepeatDataBodyMethod) == null) {
          ComplianceGrpc.getRepeatDataBodyMethod = getRepeatDataBodyMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RepeatDataBody"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ComplianceMethodDescriptorSupplier("RepeatDataBody"))
              .build();
        }
      }
    }
    return getRepeatDataBodyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RepeatDataBodyInfo",
      requestType = com.google.showcase.v1beta1.RepeatRequest.class,
      responseType = com.google.showcase.v1beta1.RepeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyInfoMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyInfoMethod;
    if ((getRepeatDataBodyInfoMethod = ComplianceGrpc.getRepeatDataBodyInfoMethod) == null) {
      synchronized (ComplianceGrpc.class) {
        if ((getRepeatDataBodyInfoMethod = ComplianceGrpc.getRepeatDataBodyInfoMethod) == null) {
          ComplianceGrpc.getRepeatDataBodyInfoMethod = getRepeatDataBodyInfoMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RepeatDataBodyInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ComplianceMethodDescriptorSupplier("RepeatDataBodyInfo"))
              .build();
        }
      }
    }
    return getRepeatDataBodyInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataQueryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RepeatDataQuery",
      requestType = com.google.showcase.v1beta1.RepeatRequest.class,
      responseType = com.google.showcase.v1beta1.RepeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataQueryMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse> getRepeatDataQueryMethod;
    if ((getRepeatDataQueryMethod = ComplianceGrpc.getRepeatDataQueryMethod) == null) {
      synchronized (ComplianceGrpc.class) {
        if ((getRepeatDataQueryMethod = ComplianceGrpc.getRepeatDataQueryMethod) == null) {
          ComplianceGrpc.getRepeatDataQueryMethod = getRepeatDataQueryMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RepeatDataQuery"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ComplianceMethodDescriptorSupplier("RepeatDataQuery"))
              .build();
        }
      }
    }
    return getRepeatDataQueryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataSimplePathMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RepeatDataSimplePath",
      requestType = com.google.showcase.v1beta1.RepeatRequest.class,
      responseType = com.google.showcase.v1beta1.RepeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataSimplePathMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse> getRepeatDataSimplePathMethod;
    if ((getRepeatDataSimplePathMethod = ComplianceGrpc.getRepeatDataSimplePathMethod) == null) {
      synchronized (ComplianceGrpc.class) {
        if ((getRepeatDataSimplePathMethod = ComplianceGrpc.getRepeatDataSimplePathMethod) == null) {
          ComplianceGrpc.getRepeatDataSimplePathMethod = getRepeatDataSimplePathMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RepeatDataSimplePath"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ComplianceMethodDescriptorSupplier("RepeatDataSimplePath"))
              .build();
        }
      }
    }
    return getRepeatDataSimplePathMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataPathResourceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RepeatDataPathResource",
      requestType = com.google.showcase.v1beta1.RepeatRequest.class,
      responseType = com.google.showcase.v1beta1.RepeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataPathResourceMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse> getRepeatDataPathResourceMethod;
    if ((getRepeatDataPathResourceMethod = ComplianceGrpc.getRepeatDataPathResourceMethod) == null) {
      synchronized (ComplianceGrpc.class) {
        if ((getRepeatDataPathResourceMethod = ComplianceGrpc.getRepeatDataPathResourceMethod) == null) {
          ComplianceGrpc.getRepeatDataPathResourceMethod = getRepeatDataPathResourceMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RepeatDataPathResource"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ComplianceMethodDescriptorSupplier("RepeatDataPathResource"))
              .build();
        }
      }
    }
    return getRepeatDataPathResourceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataPathTrailingResourceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RepeatDataPathTrailingResource",
      requestType = com.google.showcase.v1beta1.RepeatRequest.class,
      responseType = com.google.showcase.v1beta1.RepeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataPathTrailingResourceMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse> getRepeatDataPathTrailingResourceMethod;
    if ((getRepeatDataPathTrailingResourceMethod = ComplianceGrpc.getRepeatDataPathTrailingResourceMethod) == null) {
      synchronized (ComplianceGrpc.class) {
        if ((getRepeatDataPathTrailingResourceMethod = ComplianceGrpc.getRepeatDataPathTrailingResourceMethod) == null) {
          ComplianceGrpc.getRepeatDataPathTrailingResourceMethod = getRepeatDataPathTrailingResourceMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RepeatDataPathTrailingResource"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ComplianceMethodDescriptorSupplier("RepeatDataPathTrailingResource"))
              .build();
        }
      }
    }
    return getRepeatDataPathTrailingResourceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyPutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RepeatDataBodyPut",
      requestType = com.google.showcase.v1beta1.RepeatRequest.class,
      responseType = com.google.showcase.v1beta1.RepeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyPutMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyPutMethod;
    if ((getRepeatDataBodyPutMethod = ComplianceGrpc.getRepeatDataBodyPutMethod) == null) {
      synchronized (ComplianceGrpc.class) {
        if ((getRepeatDataBodyPutMethod = ComplianceGrpc.getRepeatDataBodyPutMethod) == null) {
          ComplianceGrpc.getRepeatDataBodyPutMethod = getRepeatDataBodyPutMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RepeatDataBodyPut"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ComplianceMethodDescriptorSupplier("RepeatDataBodyPut"))
              .build();
        }
      }
    }
    return getRepeatDataBodyPutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyPatchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RepeatDataBodyPatch",
      requestType = com.google.showcase.v1beta1.RepeatRequest.class,
      responseType = com.google.showcase.v1beta1.RepeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest,
      com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyPatchMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse> getRepeatDataBodyPatchMethod;
    if ((getRepeatDataBodyPatchMethod = ComplianceGrpc.getRepeatDataBodyPatchMethod) == null) {
      synchronized (ComplianceGrpc.class) {
        if ((getRepeatDataBodyPatchMethod = ComplianceGrpc.getRepeatDataBodyPatchMethod) == null) {
          ComplianceGrpc.getRepeatDataBodyPatchMethod = getRepeatDataBodyPatchMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.RepeatRequest, com.google.showcase.v1beta1.RepeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RepeatDataBodyPatch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.RepeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ComplianceMethodDescriptorSupplier("RepeatDataBodyPatch"))
              .build();
        }
      }
    }
    return getRepeatDataBodyPatchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EnumRequest,
      com.google.showcase.v1beta1.EnumResponse> getGetEnumMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetEnum",
      requestType = com.google.showcase.v1beta1.EnumRequest.class,
      responseType = com.google.showcase.v1beta1.EnumResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EnumRequest,
      com.google.showcase.v1beta1.EnumResponse> getGetEnumMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EnumRequest, com.google.showcase.v1beta1.EnumResponse> getGetEnumMethod;
    if ((getGetEnumMethod = ComplianceGrpc.getGetEnumMethod) == null) {
      synchronized (ComplianceGrpc.class) {
        if ((getGetEnumMethod = ComplianceGrpc.getGetEnumMethod) == null) {
          ComplianceGrpc.getGetEnumMethod = getGetEnumMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.EnumRequest, com.google.showcase.v1beta1.EnumResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetEnum"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EnumRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EnumResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ComplianceMethodDescriptorSupplier("GetEnum"))
              .build();
        }
      }
    }
    return getGetEnumMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EnumResponse,
      com.google.showcase.v1beta1.EnumResponse> getVerifyEnumMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "VerifyEnum",
      requestType = com.google.showcase.v1beta1.EnumResponse.class,
      responseType = com.google.showcase.v1beta1.EnumResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EnumResponse,
      com.google.showcase.v1beta1.EnumResponse> getVerifyEnumMethod() {
    io.grpc.MethodDescriptor<com.google.showcase.v1beta1.EnumResponse, com.google.showcase.v1beta1.EnumResponse> getVerifyEnumMethod;
    if ((getVerifyEnumMethod = ComplianceGrpc.getVerifyEnumMethod) == null) {
      synchronized (ComplianceGrpc.class) {
        if ((getVerifyEnumMethod = ComplianceGrpc.getVerifyEnumMethod) == null) {
          ComplianceGrpc.getVerifyEnumMethod = getVerifyEnumMethod =
              io.grpc.MethodDescriptor.<com.google.showcase.v1beta1.EnumResponse, com.google.showcase.v1beta1.EnumResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "VerifyEnum"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EnumResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.showcase.v1beta1.EnumResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ComplianceMethodDescriptorSupplier("VerifyEnum"))
              .build();
        }
      }
    }
    return getVerifyEnumMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ComplianceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ComplianceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ComplianceStub>() {
        @java.lang.Override
        public ComplianceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ComplianceStub(channel, callOptions);
        }
      };
    return ComplianceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ComplianceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ComplianceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ComplianceBlockingStub>() {
        @java.lang.Override
        public ComplianceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ComplianceBlockingStub(channel, callOptions);
        }
      };
    return ComplianceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ComplianceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ComplianceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ComplianceFutureStub>() {
        @java.lang.Override
        public ComplianceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ComplianceFutureStub(channel, callOptions);
        }
      };
    return ComplianceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ComplianceImplBase implements io.grpc.BindableService {

    /**
     */
    public void repeatDataBody(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRepeatDataBodyMethod(), responseObserver);
    }

    /**
     */
    public void repeatDataBodyInfo(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRepeatDataBodyInfoMethod(), responseObserver);
    }

    /**
     */
    public void repeatDataQuery(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRepeatDataQueryMethod(), responseObserver);
    }

    /**
     */
    public void repeatDataSimplePath(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRepeatDataSimplePathMethod(), responseObserver);
    }

    /**
     */
    public void repeatDataPathResource(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRepeatDataPathResourceMethod(), responseObserver);
    }

    /**
     */
    public void repeatDataPathTrailingResource(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRepeatDataPathTrailingResourceMethod(), responseObserver);
    }

    /**
     */
    public void repeatDataBodyPut(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRepeatDataBodyPutMethod(), responseObserver);
    }

    /**
     */
    public void repeatDataBodyPatch(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRepeatDataBodyPatchMethod(), responseObserver);
    }

    /**
     */
    public void getEnum(com.google.showcase.v1beta1.EnumRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EnumResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEnumMethod(), responseObserver);
    }

    /**
     */
    public void verifyEnum(com.google.showcase.v1beta1.EnumResponse request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EnumResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getVerifyEnumMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRepeatDataBodyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.RepeatRequest,
                com.google.showcase.v1beta1.RepeatResponse>(
                  this, METHODID_REPEAT_DATA_BODY)))
          .addMethod(
            getRepeatDataBodyInfoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.RepeatRequest,
                com.google.showcase.v1beta1.RepeatResponse>(
                  this, METHODID_REPEAT_DATA_BODY_INFO)))
          .addMethod(
            getRepeatDataQueryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.RepeatRequest,
                com.google.showcase.v1beta1.RepeatResponse>(
                  this, METHODID_REPEAT_DATA_QUERY)))
          .addMethod(
            getRepeatDataSimplePathMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.RepeatRequest,
                com.google.showcase.v1beta1.RepeatResponse>(
                  this, METHODID_REPEAT_DATA_SIMPLE_PATH)))
          .addMethod(
            getRepeatDataPathResourceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.RepeatRequest,
                com.google.showcase.v1beta1.RepeatResponse>(
                  this, METHODID_REPEAT_DATA_PATH_RESOURCE)))
          .addMethod(
            getRepeatDataPathTrailingResourceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.RepeatRequest,
                com.google.showcase.v1beta1.RepeatResponse>(
                  this, METHODID_REPEAT_DATA_PATH_TRAILING_RESOURCE)))
          .addMethod(
            getRepeatDataBodyPutMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.RepeatRequest,
                com.google.showcase.v1beta1.RepeatResponse>(
                  this, METHODID_REPEAT_DATA_BODY_PUT)))
          .addMethod(
            getRepeatDataBodyPatchMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.RepeatRequest,
                com.google.showcase.v1beta1.RepeatResponse>(
                  this, METHODID_REPEAT_DATA_BODY_PATCH)))
          .addMethod(
            getGetEnumMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.EnumRequest,
                com.google.showcase.v1beta1.EnumResponse>(
                  this, METHODID_GET_ENUM)))
          .addMethod(
            getVerifyEnumMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.showcase.v1beta1.EnumResponse,
                com.google.showcase.v1beta1.EnumResponse>(
                  this, METHODID_VERIFY_ENUM)))
          .build();
    }
  }

  /**
   */
  public static final class ComplianceStub extends io.grpc.stub.AbstractAsyncStub<ComplianceStub> {
    private ComplianceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ComplianceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ComplianceStub(channel, callOptions);
    }

    /**
     */
    public void repeatDataBody(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRepeatDataBodyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void repeatDataBodyInfo(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRepeatDataBodyInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void repeatDataQuery(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRepeatDataQueryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void repeatDataSimplePath(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRepeatDataSimplePathMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void repeatDataPathResource(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRepeatDataPathResourceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void repeatDataPathTrailingResource(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRepeatDataPathTrailingResourceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void repeatDataBodyPut(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRepeatDataBodyPutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void repeatDataBodyPatch(com.google.showcase.v1beta1.RepeatRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRepeatDataBodyPatchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getEnum(com.google.showcase.v1beta1.EnumRequest request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EnumResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetEnumMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void verifyEnum(com.google.showcase.v1beta1.EnumResponse request,
        io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EnumResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getVerifyEnumMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ComplianceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ComplianceBlockingStub> {
    private ComplianceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ComplianceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ComplianceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.showcase.v1beta1.RepeatResponse repeatDataBody(com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRepeatDataBodyMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.showcase.v1beta1.RepeatResponse repeatDataBodyInfo(com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRepeatDataBodyInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.showcase.v1beta1.RepeatResponse repeatDataQuery(com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRepeatDataQueryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.showcase.v1beta1.RepeatResponse repeatDataSimplePath(com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRepeatDataSimplePathMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.showcase.v1beta1.RepeatResponse repeatDataPathResource(com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRepeatDataPathResourceMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.showcase.v1beta1.RepeatResponse repeatDataPathTrailingResource(com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRepeatDataPathTrailingResourceMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.showcase.v1beta1.RepeatResponse repeatDataBodyPut(com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRepeatDataBodyPutMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.showcase.v1beta1.RepeatResponse repeatDataBodyPatch(com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRepeatDataBodyPatchMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.showcase.v1beta1.EnumResponse getEnum(com.google.showcase.v1beta1.EnumRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetEnumMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.showcase.v1beta1.EnumResponse verifyEnum(com.google.showcase.v1beta1.EnumResponse request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getVerifyEnumMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ComplianceFutureStub extends io.grpc.stub.AbstractFutureStub<ComplianceFutureStub> {
    private ComplianceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ComplianceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ComplianceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.RepeatResponse> repeatDataBody(
        com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRepeatDataBodyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.RepeatResponse> repeatDataBodyInfo(
        com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRepeatDataBodyInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.RepeatResponse> repeatDataQuery(
        com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRepeatDataQueryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.RepeatResponse> repeatDataSimplePath(
        com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRepeatDataSimplePathMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.RepeatResponse> repeatDataPathResource(
        com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRepeatDataPathResourceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.RepeatResponse> repeatDataPathTrailingResource(
        com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRepeatDataPathTrailingResourceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.RepeatResponse> repeatDataBodyPut(
        com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRepeatDataBodyPutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.RepeatResponse> repeatDataBodyPatch(
        com.google.showcase.v1beta1.RepeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRepeatDataBodyPatchMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.EnumResponse> getEnum(
        com.google.showcase.v1beta1.EnumRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetEnumMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.showcase.v1beta1.EnumResponse> verifyEnum(
        com.google.showcase.v1beta1.EnumResponse request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getVerifyEnumMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REPEAT_DATA_BODY = 0;
  private static final int METHODID_REPEAT_DATA_BODY_INFO = 1;
  private static final int METHODID_REPEAT_DATA_QUERY = 2;
  private static final int METHODID_REPEAT_DATA_SIMPLE_PATH = 3;
  private static final int METHODID_REPEAT_DATA_PATH_RESOURCE = 4;
  private static final int METHODID_REPEAT_DATA_PATH_TRAILING_RESOURCE = 5;
  private static final int METHODID_REPEAT_DATA_BODY_PUT = 6;
  private static final int METHODID_REPEAT_DATA_BODY_PATCH = 7;
  private static final int METHODID_GET_ENUM = 8;
  private static final int METHODID_VERIFY_ENUM = 9;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ComplianceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ComplianceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REPEAT_DATA_BODY:
          serviceImpl.repeatDataBody((com.google.showcase.v1beta1.RepeatRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse>) responseObserver);
          break;
        case METHODID_REPEAT_DATA_BODY_INFO:
          serviceImpl.repeatDataBodyInfo((com.google.showcase.v1beta1.RepeatRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse>) responseObserver);
          break;
        case METHODID_REPEAT_DATA_QUERY:
          serviceImpl.repeatDataQuery((com.google.showcase.v1beta1.RepeatRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse>) responseObserver);
          break;
        case METHODID_REPEAT_DATA_SIMPLE_PATH:
          serviceImpl.repeatDataSimplePath((com.google.showcase.v1beta1.RepeatRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse>) responseObserver);
          break;
        case METHODID_REPEAT_DATA_PATH_RESOURCE:
          serviceImpl.repeatDataPathResource((com.google.showcase.v1beta1.RepeatRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse>) responseObserver);
          break;
        case METHODID_REPEAT_DATA_PATH_TRAILING_RESOURCE:
          serviceImpl.repeatDataPathTrailingResource((com.google.showcase.v1beta1.RepeatRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse>) responseObserver);
          break;
        case METHODID_REPEAT_DATA_BODY_PUT:
          serviceImpl.repeatDataBodyPut((com.google.showcase.v1beta1.RepeatRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse>) responseObserver);
          break;
        case METHODID_REPEAT_DATA_BODY_PATCH:
          serviceImpl.repeatDataBodyPatch((com.google.showcase.v1beta1.RepeatRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.RepeatResponse>) responseObserver);
          break;
        case METHODID_GET_ENUM:
          serviceImpl.getEnum((com.google.showcase.v1beta1.EnumRequest) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EnumResponse>) responseObserver);
          break;
        case METHODID_VERIFY_ENUM:
          serviceImpl.verifyEnum((com.google.showcase.v1beta1.EnumResponse) request,
              (io.grpc.stub.StreamObserver<com.google.showcase.v1beta1.EnumResponse>) responseObserver);
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

  private static abstract class ComplianceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ComplianceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.google.showcase.v1beta1.ComplianceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Compliance");
    }
  }

  private static final class ComplianceFileDescriptorSupplier
      extends ComplianceBaseDescriptorSupplier {
    ComplianceFileDescriptorSupplier() {}
  }

  private static final class ComplianceMethodDescriptorSupplier
      extends ComplianceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ComplianceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ComplianceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ComplianceFileDescriptorSupplier())
              .addMethod(getRepeatDataBodyMethod())
              .addMethod(getRepeatDataBodyInfoMethod())
              .addMethod(getRepeatDataQueryMethod())
              .addMethod(getRepeatDataSimplePathMethod())
              .addMethod(getRepeatDataPathResourceMethod())
              .addMethod(getRepeatDataPathTrailingResourceMethod())
              .addMethod(getRepeatDataBodyPutMethod())
              .addMethod(getRepeatDataBodyPatchMethod())
              .addMethod(getGetEnumMethod())
              .addMethod(getVerifyEnumMethod())
              .build();
        }
      }
    }
    return result;
  }
}
