package com.google.longrunning;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * Manages long-running operations with an API service.
 * When an API method normally takes long time to complete, it can be designed
 * to return [Operation][google.longrunning.Operation] to the client, and the client can use this
 * interface to receive the real response asynchronously by polling the
 * operation resource, or pass the operation resource to another API (such as
 * Google Cloud Pub/Sub API) to receive the response.  Any API service that
 * returns long-running operations should implement the `Operations` interface
 * so developers can have a consistent client experience.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.10.0)",
    comments = "Source: google/longrunning/operations.proto")
public final class OperationsGrpc {

  private OperationsGrpc() {}

  public static final String SERVICE_NAME = "google.longrunning.Operations";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getListOperationsMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.google.longrunning.ListOperationsRequest,
      com.google.longrunning.ListOperationsResponse> METHOD_LIST_OPERATIONS = getListOperationsMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.google.longrunning.ListOperationsRequest,
      com.google.longrunning.ListOperationsResponse> getListOperationsMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.google.longrunning.ListOperationsRequest,
      com.google.longrunning.ListOperationsResponse> getListOperationsMethod() {
    return getListOperationsMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.google.longrunning.ListOperationsRequest,
      com.google.longrunning.ListOperationsResponse> getListOperationsMethodHelper() {
    io.grpc.MethodDescriptor<com.google.longrunning.ListOperationsRequest, com.google.longrunning.ListOperationsResponse> getListOperationsMethod;
    if ((getListOperationsMethod = OperationsGrpc.getListOperationsMethod) == null) {
      synchronized (OperationsGrpc.class) {
        if ((getListOperationsMethod = OperationsGrpc.getListOperationsMethod) == null) {
          OperationsGrpc.getListOperationsMethod = getListOperationsMethod = 
              io.grpc.MethodDescriptor.<com.google.longrunning.ListOperationsRequest, com.google.longrunning.ListOperationsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "google.longrunning.Operations", "ListOperations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.longrunning.ListOperationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.longrunning.ListOperationsResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new OperationsMethodDescriptorSupplier("ListOperations"))
                  .build();
          }
        }
     }
     return getListOperationsMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getGetOperationMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.google.longrunning.GetOperationRequest,
      com.google.longrunning.Operation> METHOD_GET_OPERATION = getGetOperationMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.google.longrunning.GetOperationRequest,
      com.google.longrunning.Operation> getGetOperationMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.google.longrunning.GetOperationRequest,
      com.google.longrunning.Operation> getGetOperationMethod() {
    return getGetOperationMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.google.longrunning.GetOperationRequest,
      com.google.longrunning.Operation> getGetOperationMethodHelper() {
    io.grpc.MethodDescriptor<com.google.longrunning.GetOperationRequest, com.google.longrunning.Operation> getGetOperationMethod;
    if ((getGetOperationMethod = OperationsGrpc.getGetOperationMethod) == null) {
      synchronized (OperationsGrpc.class) {
        if ((getGetOperationMethod = OperationsGrpc.getGetOperationMethod) == null) {
          OperationsGrpc.getGetOperationMethod = getGetOperationMethod = 
              io.grpc.MethodDescriptor.<com.google.longrunning.GetOperationRequest, com.google.longrunning.Operation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "google.longrunning.Operations", "GetOperation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.longrunning.GetOperationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.longrunning.Operation.getDefaultInstance()))
                  .setSchemaDescriptor(new OperationsMethodDescriptorSupplier("GetOperation"))
                  .build();
          }
        }
     }
     return getGetOperationMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getDeleteOperationMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.google.longrunning.DeleteOperationRequest,
      com.google.protobuf.Empty> METHOD_DELETE_OPERATION = getDeleteOperationMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.google.longrunning.DeleteOperationRequest,
      com.google.protobuf.Empty> getDeleteOperationMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.google.longrunning.DeleteOperationRequest,
      com.google.protobuf.Empty> getDeleteOperationMethod() {
    return getDeleteOperationMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.google.longrunning.DeleteOperationRequest,
      com.google.protobuf.Empty> getDeleteOperationMethodHelper() {
    io.grpc.MethodDescriptor<com.google.longrunning.DeleteOperationRequest, com.google.protobuf.Empty> getDeleteOperationMethod;
    if ((getDeleteOperationMethod = OperationsGrpc.getDeleteOperationMethod) == null) {
      synchronized (OperationsGrpc.class) {
        if ((getDeleteOperationMethod = OperationsGrpc.getDeleteOperationMethod) == null) {
          OperationsGrpc.getDeleteOperationMethod = getDeleteOperationMethod = 
              io.grpc.MethodDescriptor.<com.google.longrunning.DeleteOperationRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "google.longrunning.Operations", "DeleteOperation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.longrunning.DeleteOperationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new OperationsMethodDescriptorSupplier("DeleteOperation"))
                  .build();
          }
        }
     }
     return getDeleteOperationMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getCancelOperationMethod()} instead. 
  public static final io.grpc.MethodDescriptor<com.google.longrunning.CancelOperationRequest,
      com.google.protobuf.Empty> METHOD_CANCEL_OPERATION = getCancelOperationMethodHelper();

  private static volatile io.grpc.MethodDescriptor<com.google.longrunning.CancelOperationRequest,
      com.google.protobuf.Empty> getCancelOperationMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.google.longrunning.CancelOperationRequest,
      com.google.protobuf.Empty> getCancelOperationMethod() {
    return getCancelOperationMethodHelper();
  }

  private static io.grpc.MethodDescriptor<com.google.longrunning.CancelOperationRequest,
      com.google.protobuf.Empty> getCancelOperationMethodHelper() {
    io.grpc.MethodDescriptor<com.google.longrunning.CancelOperationRequest, com.google.protobuf.Empty> getCancelOperationMethod;
    if ((getCancelOperationMethod = OperationsGrpc.getCancelOperationMethod) == null) {
      synchronized (OperationsGrpc.class) {
        if ((getCancelOperationMethod = OperationsGrpc.getCancelOperationMethod) == null) {
          OperationsGrpc.getCancelOperationMethod = getCancelOperationMethod = 
              io.grpc.MethodDescriptor.<com.google.longrunning.CancelOperationRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "google.longrunning.Operations", "CancelOperation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.longrunning.CancelOperationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new OperationsMethodDescriptorSupplier("CancelOperation"))
                  .build();
          }
        }
     }
     return getCancelOperationMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OperationsStub newStub(io.grpc.Channel channel) {
    return new OperationsStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OperationsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new OperationsBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OperationsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new OperationsFutureStub(channel);
  }

  /**
   * <pre>
   * Manages long-running operations with an API service.
   * When an API method normally takes long time to complete, it can be designed
   * to return [Operation][google.longrunning.Operation] to the client, and the client can use this
   * interface to receive the real response asynchronously by polling the
   * operation resource, or pass the operation resource to another API (such as
   * Google Cloud Pub/Sub API) to receive the response.  Any API service that
   * returns long-running operations should implement the `Operations` interface
   * so developers can have a consistent client experience.
   * </pre>
   */
  public static abstract class OperationsImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Lists operations that match the specified filter in the request. If the
     * server doesn't support this method, it returns `UNIMPLEMENTED`.
     * NOTE: the `name` binding below allows API services to override the binding
     * to use different resource name schemes, such as `users/&#42;&#47;operations`.
     * </pre>
     */
    public void listOperations(com.google.longrunning.ListOperationsRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.ListOperationsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListOperationsMethodHelper(), responseObserver);
    }

    /**
     * <pre>
     * Gets the latest state of a long-running operation.  Clients can use this
     * method to poll the operation result at intervals as recommended by the API
     * service.
     * </pre>
     */
    public void getOperation(com.google.longrunning.GetOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnimplementedUnaryCall(getGetOperationMethodHelper(), responseObserver);
    }

    /**
     * <pre>
     * Deletes a long-running operation. This method indicates that the client is
     * no longer interested in the operation result. It does not cancel the
     * operation. If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.
     * </pre>
     */
    public void deleteOperation(com.google.longrunning.DeleteOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteOperationMethodHelper(), responseObserver);
    }

    /**
     * <pre>
     * Starts asynchronous cancellation on a long-running operation.  The server
     * makes a best effort to cancel the operation, but success is not
     * guaranteed.  If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.  Clients can use
     * [Operations.GetOperation][google.longrunning.Operations.GetOperation] or
     * other methods to check whether the cancellation succeeded or whether the
     * operation completed despite cancellation. On successful cancellation,
     * the operation is not deleted; instead, it becomes an operation with
     * an [Operation.error][google.longrunning.Operation.error] value with a [google.rpc.Status.code][google.rpc.Status.code] of 1,
     * corresponding to `Code.CANCELLED`.
     * </pre>
     */
    public void cancelOperation(com.google.longrunning.CancelOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getCancelOperationMethodHelper(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListOperationsMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.longrunning.ListOperationsRequest,
                com.google.longrunning.ListOperationsResponse>(
                  this, METHODID_LIST_OPERATIONS)))
          .addMethod(
            getGetOperationMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.longrunning.GetOperationRequest,
                com.google.longrunning.Operation>(
                  this, METHODID_GET_OPERATION)))
          .addMethod(
            getDeleteOperationMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.longrunning.DeleteOperationRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_DELETE_OPERATION)))
          .addMethod(
            getCancelOperationMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.longrunning.CancelOperationRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_CANCEL_OPERATION)))
          .build();
    }
  }

  /**
   * <pre>
   * Manages long-running operations with an API service.
   * When an API method normally takes long time to complete, it can be designed
   * to return [Operation][google.longrunning.Operation] to the client, and the client can use this
   * interface to receive the real response asynchronously by polling the
   * operation resource, or pass the operation resource to another API (such as
   * Google Cloud Pub/Sub API) to receive the response.  Any API service that
   * returns long-running operations should implement the `Operations` interface
   * so developers can have a consistent client experience.
   * </pre>
   */
  public static final class OperationsStub extends io.grpc.stub.AbstractStub<OperationsStub> {
    private OperationsStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OperationsStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OperationsStub(channel, callOptions);
    }

    /**
     * <pre>
     * Lists operations that match the specified filter in the request. If the
     * server doesn't support this method, it returns `UNIMPLEMENTED`.
     * NOTE: the `name` binding below allows API services to override the binding
     * to use different resource name schemes, such as `users/&#42;&#47;operations`.
     * </pre>
     */
    public void listOperations(com.google.longrunning.ListOperationsRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.ListOperationsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListOperationsMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Gets the latest state of a long-running operation.  Clients can use this
     * method to poll the operation result at intervals as recommended by the API
     * service.
     * </pre>
     */
    public void getOperation(com.google.longrunning.GetOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetOperationMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Deletes a long-running operation. This method indicates that the client is
     * no longer interested in the operation result. It does not cancel the
     * operation. If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.
     * </pre>
     */
    public void deleteOperation(com.google.longrunning.DeleteOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteOperationMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Starts asynchronous cancellation on a long-running operation.  The server
     * makes a best effort to cancel the operation, but success is not
     * guaranteed.  If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.  Clients can use
     * [Operations.GetOperation][google.longrunning.Operations.GetOperation] or
     * other methods to check whether the cancellation succeeded or whether the
     * operation completed despite cancellation. On successful cancellation,
     * the operation is not deleted; instead, it becomes an operation with
     * an [Operation.error][google.longrunning.Operation.error] value with a [google.rpc.Status.code][google.rpc.Status.code] of 1,
     * corresponding to `Code.CANCELLED`.
     * </pre>
     */
    public void cancelOperation(com.google.longrunning.CancelOperationRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCancelOperationMethodHelper(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Manages long-running operations with an API service.
   * When an API method normally takes long time to complete, it can be designed
   * to return [Operation][google.longrunning.Operation] to the client, and the client can use this
   * interface to receive the real response asynchronously by polling the
   * operation resource, or pass the operation resource to another API (such as
   * Google Cloud Pub/Sub API) to receive the response.  Any API service that
   * returns long-running operations should implement the `Operations` interface
   * so developers can have a consistent client experience.
   * </pre>
   */
  public static final class OperationsBlockingStub extends io.grpc.stub.AbstractStub<OperationsBlockingStub> {
    private OperationsBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OperationsBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OperationsBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Lists operations that match the specified filter in the request. If the
     * server doesn't support this method, it returns `UNIMPLEMENTED`.
     * NOTE: the `name` binding below allows API services to override the binding
     * to use different resource name schemes, such as `users/&#42;&#47;operations`.
     * </pre>
     */
    public com.google.longrunning.ListOperationsResponse listOperations(com.google.longrunning.ListOperationsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListOperationsMethodHelper(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Gets the latest state of a long-running operation.  Clients can use this
     * method to poll the operation result at intervals as recommended by the API
     * service.
     * </pre>
     */
    public com.google.longrunning.Operation getOperation(com.google.longrunning.GetOperationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetOperationMethodHelper(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Deletes a long-running operation. This method indicates that the client is
     * no longer interested in the operation result. It does not cancel the
     * operation. If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.
     * </pre>
     */
    public com.google.protobuf.Empty deleteOperation(com.google.longrunning.DeleteOperationRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteOperationMethodHelper(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Starts asynchronous cancellation on a long-running operation.  The server
     * makes a best effort to cancel the operation, but success is not
     * guaranteed.  If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.  Clients can use
     * [Operations.GetOperation][google.longrunning.Operations.GetOperation] or
     * other methods to check whether the cancellation succeeded or whether the
     * operation completed despite cancellation. On successful cancellation,
     * the operation is not deleted; instead, it becomes an operation with
     * an [Operation.error][google.longrunning.Operation.error] value with a [google.rpc.Status.code][google.rpc.Status.code] of 1,
     * corresponding to `Code.CANCELLED`.
     * </pre>
     */
    public com.google.protobuf.Empty cancelOperation(com.google.longrunning.CancelOperationRequest request) {
      return blockingUnaryCall(
          getChannel(), getCancelOperationMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Manages long-running operations with an API service.
   * When an API method normally takes long time to complete, it can be designed
   * to return [Operation][google.longrunning.Operation] to the client, and the client can use this
   * interface to receive the real response asynchronously by polling the
   * operation resource, or pass the operation resource to another API (such as
   * Google Cloud Pub/Sub API) to receive the response.  Any API service that
   * returns long-running operations should implement the `Operations` interface
   * so developers can have a consistent client experience.
   * </pre>
   */
  public static final class OperationsFutureStub extends io.grpc.stub.AbstractStub<OperationsFutureStub> {
    private OperationsFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OperationsFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OperationsFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OperationsFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Lists operations that match the specified filter in the request. If the
     * server doesn't support this method, it returns `UNIMPLEMENTED`.
     * NOTE: the `name` binding below allows API services to override the binding
     * to use different resource name schemes, such as `users/&#42;&#47;operations`.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.ListOperationsResponse> listOperations(
        com.google.longrunning.ListOperationsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListOperationsMethodHelper(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Gets the latest state of a long-running operation.  Clients can use this
     * method to poll the operation result at intervals as recommended by the API
     * service.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> getOperation(
        com.google.longrunning.GetOperationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetOperationMethodHelper(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Deletes a long-running operation. This method indicates that the client is
     * no longer interested in the operation result. It does not cancel the
     * operation. If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deleteOperation(
        com.google.longrunning.DeleteOperationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteOperationMethodHelper(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Starts asynchronous cancellation on a long-running operation.  The server
     * makes a best effort to cancel the operation, but success is not
     * guaranteed.  If the server doesn't support this method, it returns
     * `google.rpc.Code.UNIMPLEMENTED`.  Clients can use
     * [Operations.GetOperation][google.longrunning.Operations.GetOperation] or
     * other methods to check whether the cancellation succeeded or whether the
     * operation completed despite cancellation. On successful cancellation,
     * the operation is not deleted; instead, it becomes an operation with
     * an [Operation.error][google.longrunning.Operation.error] value with a [google.rpc.Status.code][google.rpc.Status.code] of 1,
     * corresponding to `Code.CANCELLED`.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> cancelOperation(
        com.google.longrunning.CancelOperationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCancelOperationMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_OPERATIONS = 0;
  private static final int METHODID_GET_OPERATION = 1;
  private static final int METHODID_DELETE_OPERATION = 2;
  private static final int METHODID_CANCEL_OPERATION = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final OperationsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(OperationsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST_OPERATIONS:
          serviceImpl.listOperations((com.google.longrunning.ListOperationsRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.ListOperationsResponse>) responseObserver);
          break;
        case METHODID_GET_OPERATION:
          serviceImpl.getOperation((com.google.longrunning.GetOperationRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
          break;
        case METHODID_DELETE_OPERATION:
          serviceImpl.deleteOperation((com.google.longrunning.DeleteOperationRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_CANCEL_OPERATION:
          serviceImpl.cancelOperation((com.google.longrunning.CancelOperationRequest) request,
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

  private static abstract class OperationsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    OperationsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.google.longrunning.OperationsProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Operations");
    }
  }

  private static final class OperationsFileDescriptorSupplier
      extends OperationsBaseDescriptorSupplier {
    OperationsFileDescriptorSupplier() {}
  }

  private static final class OperationsMethodDescriptorSupplier
      extends OperationsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    OperationsMethodDescriptorSupplier(String methodName) {
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
      synchronized (OperationsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OperationsFileDescriptorSupplier())
              .addMethod(getListOperationsMethodHelper())
              .addMethod(getGetOperationMethodHelper())
              .addMethod(getDeleteOperationMethodHelper())
              .addMethod(getCancelOperationMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
