package com.google.api.servicemanagement.v1;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * [Google Service Management API](/service-management/overview)
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.2.0)",
    comments = "Source: google/api/servicemanagement/v1/servicemanager.proto")
public final class ServiceManagerGrpc {

  private ServiceManagerGrpc() {}

  public static final String SERVICE_NAME = "google.api.servicemanagement.v1.ServiceManager";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.ListServicesRequest,
      com.google.api.servicemanagement.v1.ListServicesResponse> METHOD_LIST_SERVICES =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "ListServices"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.ListServicesRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.ListServicesResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.GetServiceRequest,
      com.google.api.servicemanagement.v1.ManagedService> METHOD_GET_SERVICE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "GetService"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.GetServiceRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.ManagedService.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.CreateServiceRequest,
      com.google.longrunning.Operation> METHOD_CREATE_SERVICE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "CreateService"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.CreateServiceRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.longrunning.Operation.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.DeleteServiceRequest,
      com.google.longrunning.Operation> METHOD_DELETE_SERVICE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "DeleteService"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.DeleteServiceRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.longrunning.Operation.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.UndeleteServiceRequest,
      com.google.longrunning.Operation> METHOD_UNDELETE_SERVICE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "UndeleteService"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.UndeleteServiceRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.longrunning.Operation.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.ListServiceConfigsRequest,
      com.google.api.servicemanagement.v1.ListServiceConfigsResponse> METHOD_LIST_SERVICE_CONFIGS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "ListServiceConfigs"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.ListServiceConfigsRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.ListServiceConfigsResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.GetServiceConfigRequest,
      com.google.api.Service> METHOD_GET_SERVICE_CONFIG =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "GetServiceConfig"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.GetServiceConfigRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.Service.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.CreateServiceConfigRequest,
      com.google.api.Service> METHOD_CREATE_SERVICE_CONFIG =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "CreateServiceConfig"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.CreateServiceConfigRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.Service.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.SubmitConfigSourceRequest,
      com.google.longrunning.Operation> METHOD_SUBMIT_CONFIG_SOURCE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "SubmitConfigSource"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.SubmitConfigSourceRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.longrunning.Operation.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.ListServiceRolloutsRequest,
      com.google.api.servicemanagement.v1.ListServiceRolloutsResponse> METHOD_LIST_SERVICE_ROLLOUTS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "ListServiceRollouts"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.ListServiceRolloutsRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.ListServiceRolloutsResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.GetServiceRolloutRequest,
      com.google.api.servicemanagement.v1.Rollout> METHOD_GET_SERVICE_ROLLOUT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "GetServiceRollout"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.GetServiceRolloutRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.Rollout.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.CreateServiceRolloutRequest,
      com.google.longrunning.Operation> METHOD_CREATE_SERVICE_ROLLOUT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "CreateServiceRollout"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.CreateServiceRolloutRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.longrunning.Operation.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.GenerateConfigReportRequest,
      com.google.api.servicemanagement.v1.GenerateConfigReportResponse> METHOD_GENERATE_CONFIG_REPORT =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "GenerateConfigReport"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.GenerateConfigReportRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.GenerateConfigReportResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.EnableServiceRequest,
      com.google.longrunning.Operation> METHOD_ENABLE_SERVICE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "EnableService"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.EnableServiceRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.longrunning.Operation.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.google.api.servicemanagement.v1.DisableServiceRequest,
      com.google.longrunning.Operation> METHOD_DISABLE_SERVICE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "google.api.servicemanagement.v1.ServiceManager", "DisableService"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.api.servicemanagement.v1.DisableServiceRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.google.longrunning.Operation.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServiceManagerStub newStub(io.grpc.Channel channel) {
    return new ServiceManagerStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServiceManagerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ServiceManagerBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static ServiceManagerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ServiceManagerFutureStub(channel);
  }

  /**
   * <pre>
   * [Google Service Management API](/service-management/overview)
   * </pre>
   */
  public static abstract class ServiceManagerImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Lists all managed services.
     * </pre>
     */
    public void listServices(com.google.api.servicemanagement.v1.ListServicesRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ListServicesResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LIST_SERVICES, responseObserver);
    }

    /**
     * <pre>
     * Gets a managed service.
     * </pre>
     */
    public void getService(com.google.api.servicemanagement.v1.GetServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ManagedService> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SERVICE, responseObserver);
    }

    /**
     * <pre>
     * Creates a new managed service.
     * Please note one producer project can own no more than 20 services.
     * Operation&lt;response: ManagedService&gt;
     * </pre>
     */
    public void createService(com.google.api.servicemanagement.v1.CreateServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_SERVICE, responseObserver);
    }

    /**
     * <pre>
     * Deletes a managed service. This method will change the serivce in the
     * `Soft-Delete` state for 30 days. Within this period, service producers may
     * call [UndeleteService][google.api.servicemanagement.v1.ServiceManager.UndeleteService] to restore the service.
     * After 30 days, the service will be permanently deleted.
     * Operation&lt;response: google.protobuf.Empty&gt;
     * </pre>
     */
    public void deleteService(com.google.api.servicemanagement.v1.DeleteServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DELETE_SERVICE, responseObserver);
    }

    /**
     * <pre>
     * Revives a previously deleted managed service. The method restores the
     * service using the configuration at the time the service was deleted.
     * The target service must exist and must have been deleted within the
     * last 30 days.
     * Operation&lt;response: UndeleteServiceResponse&gt;
     * </pre>
     */
    public void undeleteService(com.google.api.servicemanagement.v1.UndeleteServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UNDELETE_SERVICE, responseObserver);
    }

    /**
     * <pre>
     * Lists the history of the service configuration for a managed service,
     * from the newest to the oldest.
     * </pre>
     */
    public void listServiceConfigs(com.google.api.servicemanagement.v1.ListServiceConfigsRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ListServiceConfigsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LIST_SERVICE_CONFIGS, responseObserver);
    }

    /**
     * <pre>
     * Gets a service configuration (version) for a managed service.
     * </pre>
     */
    public void getServiceConfig(com.google.api.servicemanagement.v1.GetServiceConfigRequest request,
        io.grpc.stub.StreamObserver<com.google.api.Service> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SERVICE_CONFIG, responseObserver);
    }

    /**
     * <pre>
     * Creates a new service configuration (version) for a managed service.
     * This method only stores the service configuration. To roll out the service
     * configuration to backend systems please call
     * [CreateServiceRollout][google.api.servicemanagement.v1.ServiceManager.CreateServiceRollout].
     * </pre>
     */
    public void createServiceConfig(com.google.api.servicemanagement.v1.CreateServiceConfigRequest request,
        io.grpc.stub.StreamObserver<com.google.api.Service> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_SERVICE_CONFIG, responseObserver);
    }

    /**
     * <pre>
     * Creates a new service configuration (version) for a managed service based
     * on
     * user-supplied configuration source files (for example: OpenAPI
     * Specification). This method stores the source configurations as well as the
     * generated service configuration. To rollout the service configuration to
     * other services,
     * please call [CreateServiceRollout][google.api.servicemanagement.v1.ServiceManager.CreateServiceRollout].
     * Operation&lt;response: SubmitConfigSourceResponse&gt;
     * </pre>
     */
    public void submitConfigSource(com.google.api.servicemanagement.v1.SubmitConfigSourceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SUBMIT_CONFIG_SOURCE, responseObserver);
    }

    /**
     * <pre>
     * Lists the history of the service configuration rollouts for a managed
     * service, from the newest to the oldest.
     * </pre>
     */
    public void listServiceRollouts(com.google.api.servicemanagement.v1.ListServiceRolloutsRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ListServiceRolloutsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LIST_SERVICE_ROLLOUTS, responseObserver);
    }

    /**
     * <pre>
     * Gets a service configuration [rollout][google.api.servicemanagement.v1.Rollout].
     * </pre>
     */
    public void getServiceRollout(com.google.api.servicemanagement.v1.GetServiceRolloutRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.Rollout> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SERVICE_ROLLOUT, responseObserver);
    }

    /**
     * <pre>
     * Creates a new service configuration rollout. Based on rollout, the
     * Google Service Management will roll out the service configurations to
     * different backend services. For example, the logging configuration will be
     * pushed to Google Cloud Logging.
     * Please note that any previous pending and running Rollouts and associated
     * Operations will be automatically cancelled so that the latest Rollout will
     * not be blocked by previous Rollouts.
     * Operation&lt;response: Rollout&gt;
     * </pre>
     */
    public void createServiceRollout(com.google.api.servicemanagement.v1.CreateServiceRolloutRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_SERVICE_ROLLOUT, responseObserver);
    }

    /**
     * <pre>
     * Generates and returns a report (errors, warnings and changes from
     * existing configurations) associated with
     * GenerateConfigReportRequest.new_value
     * If GenerateConfigReportRequest.old_value is specified,
     * GenerateConfigReportRequest will contain a single ChangeReport based on the
     * comparison between GenerateConfigReportRequest.new_value and
     * GenerateConfigReportRequest.old_value.
     * If GenerateConfigReportRequest.old_value is not specified, this method
     * will compare GenerateConfigReportRequest.new_value with the last pushed
     * service configuration.
     * </pre>
     */
    public void generateConfigReport(com.google.api.servicemanagement.v1.GenerateConfigReportRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.GenerateConfigReportResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GENERATE_CONFIG_REPORT, responseObserver);
    }

    /**
     * <pre>
     * Enable a managed service for a project with default setting.
     * Operation&lt;response: EnableServiceResponse&gt;
     * [google.rpc.Status][google.rpc.Status] errors may contain a
     * [google.rpc.PreconditionFailure][] error detail.
     * </pre>
     */
    public void enableService(com.google.api.servicemanagement.v1.EnableServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ENABLE_SERVICE, responseObserver);
    }

    /**
     * <pre>
     * Disable a managed service for a project.
     * Operation&lt;response: DisableServiceResponse&gt;
     * </pre>
     */
    public void disableService(com.google.api.servicemanagement.v1.DisableServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DISABLE_SERVICE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_LIST_SERVICES,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.ListServicesRequest,
                com.google.api.servicemanagement.v1.ListServicesResponse>(
                  this, METHODID_LIST_SERVICES)))
          .addMethod(
            METHOD_GET_SERVICE,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.GetServiceRequest,
                com.google.api.servicemanagement.v1.ManagedService>(
                  this, METHODID_GET_SERVICE)))
          .addMethod(
            METHOD_CREATE_SERVICE,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.CreateServiceRequest,
                com.google.longrunning.Operation>(
                  this, METHODID_CREATE_SERVICE)))
          .addMethod(
            METHOD_DELETE_SERVICE,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.DeleteServiceRequest,
                com.google.longrunning.Operation>(
                  this, METHODID_DELETE_SERVICE)))
          .addMethod(
            METHOD_UNDELETE_SERVICE,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.UndeleteServiceRequest,
                com.google.longrunning.Operation>(
                  this, METHODID_UNDELETE_SERVICE)))
          .addMethod(
            METHOD_LIST_SERVICE_CONFIGS,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.ListServiceConfigsRequest,
                com.google.api.servicemanagement.v1.ListServiceConfigsResponse>(
                  this, METHODID_LIST_SERVICE_CONFIGS)))
          .addMethod(
            METHOD_GET_SERVICE_CONFIG,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.GetServiceConfigRequest,
                com.google.api.Service>(
                  this, METHODID_GET_SERVICE_CONFIG)))
          .addMethod(
            METHOD_CREATE_SERVICE_CONFIG,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.CreateServiceConfigRequest,
                com.google.api.Service>(
                  this, METHODID_CREATE_SERVICE_CONFIG)))
          .addMethod(
            METHOD_SUBMIT_CONFIG_SOURCE,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.SubmitConfigSourceRequest,
                com.google.longrunning.Operation>(
                  this, METHODID_SUBMIT_CONFIG_SOURCE)))
          .addMethod(
            METHOD_LIST_SERVICE_ROLLOUTS,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.ListServiceRolloutsRequest,
                com.google.api.servicemanagement.v1.ListServiceRolloutsResponse>(
                  this, METHODID_LIST_SERVICE_ROLLOUTS)))
          .addMethod(
            METHOD_GET_SERVICE_ROLLOUT,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.GetServiceRolloutRequest,
                com.google.api.servicemanagement.v1.Rollout>(
                  this, METHODID_GET_SERVICE_ROLLOUT)))
          .addMethod(
            METHOD_CREATE_SERVICE_ROLLOUT,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.CreateServiceRolloutRequest,
                com.google.longrunning.Operation>(
                  this, METHODID_CREATE_SERVICE_ROLLOUT)))
          .addMethod(
            METHOD_GENERATE_CONFIG_REPORT,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.GenerateConfigReportRequest,
                com.google.api.servicemanagement.v1.GenerateConfigReportResponse>(
                  this, METHODID_GENERATE_CONFIG_REPORT)))
          .addMethod(
            METHOD_ENABLE_SERVICE,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.EnableServiceRequest,
                com.google.longrunning.Operation>(
                  this, METHODID_ENABLE_SERVICE)))
          .addMethod(
            METHOD_DISABLE_SERVICE,
            asyncUnaryCall(
              new MethodHandlers<
                com.google.api.servicemanagement.v1.DisableServiceRequest,
                com.google.longrunning.Operation>(
                  this, METHODID_DISABLE_SERVICE)))
          .build();
    }
  }

  /**
   * <pre>
   * [Google Service Management API](/service-management/overview)
   * </pre>
   */
  public static final class ServiceManagerStub extends io.grpc.stub.AbstractStub<ServiceManagerStub> {
    private ServiceManagerStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServiceManagerStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceManagerStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServiceManagerStub(channel, callOptions);
    }

    /**
     * <pre>
     * Lists all managed services.
     * </pre>
     */
    public void listServices(com.google.api.servicemanagement.v1.ListServicesRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ListServicesResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LIST_SERVICES, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Gets a managed service.
     * </pre>
     */
    public void getService(com.google.api.servicemanagement.v1.GetServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ManagedService> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SERVICE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Creates a new managed service.
     * Please note one producer project can own no more than 20 services.
     * Operation&lt;response: ManagedService&gt;
     * </pre>
     */
    public void createService(com.google.api.servicemanagement.v1.CreateServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_SERVICE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Deletes a managed service. This method will change the serivce in the
     * `Soft-Delete` state for 30 days. Within this period, service producers may
     * call [UndeleteService][google.api.servicemanagement.v1.ServiceManager.UndeleteService] to restore the service.
     * After 30 days, the service will be permanently deleted.
     * Operation&lt;response: google.protobuf.Empty&gt;
     * </pre>
     */
    public void deleteService(com.google.api.servicemanagement.v1.DeleteServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DELETE_SERVICE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Revives a previously deleted managed service. The method restores the
     * service using the configuration at the time the service was deleted.
     * The target service must exist and must have been deleted within the
     * last 30 days.
     * Operation&lt;response: UndeleteServiceResponse&gt;
     * </pre>
     */
    public void undeleteService(com.google.api.servicemanagement.v1.UndeleteServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UNDELETE_SERVICE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Lists the history of the service configuration for a managed service,
     * from the newest to the oldest.
     * </pre>
     */
    public void listServiceConfigs(com.google.api.servicemanagement.v1.ListServiceConfigsRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ListServiceConfigsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LIST_SERVICE_CONFIGS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Gets a service configuration (version) for a managed service.
     * </pre>
     */
    public void getServiceConfig(com.google.api.servicemanagement.v1.GetServiceConfigRequest request,
        io.grpc.stub.StreamObserver<com.google.api.Service> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SERVICE_CONFIG, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Creates a new service configuration (version) for a managed service.
     * This method only stores the service configuration. To roll out the service
     * configuration to backend systems please call
     * [CreateServiceRollout][google.api.servicemanagement.v1.ServiceManager.CreateServiceRollout].
     * </pre>
     */
    public void createServiceConfig(com.google.api.servicemanagement.v1.CreateServiceConfigRequest request,
        io.grpc.stub.StreamObserver<com.google.api.Service> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_SERVICE_CONFIG, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Creates a new service configuration (version) for a managed service based
     * on
     * user-supplied configuration source files (for example: OpenAPI
     * Specification). This method stores the source configurations as well as the
     * generated service configuration. To rollout the service configuration to
     * other services,
     * please call [CreateServiceRollout][google.api.servicemanagement.v1.ServiceManager.CreateServiceRollout].
     * Operation&lt;response: SubmitConfigSourceResponse&gt;
     * </pre>
     */
    public void submitConfigSource(com.google.api.servicemanagement.v1.SubmitConfigSourceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SUBMIT_CONFIG_SOURCE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Lists the history of the service configuration rollouts for a managed
     * service, from the newest to the oldest.
     * </pre>
     */
    public void listServiceRollouts(com.google.api.servicemanagement.v1.ListServiceRolloutsRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ListServiceRolloutsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LIST_SERVICE_ROLLOUTS, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Gets a service configuration [rollout][google.api.servicemanagement.v1.Rollout].
     * </pre>
     */
    public void getServiceRollout(com.google.api.servicemanagement.v1.GetServiceRolloutRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.Rollout> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SERVICE_ROLLOUT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Creates a new service configuration rollout. Based on rollout, the
     * Google Service Management will roll out the service configurations to
     * different backend services. For example, the logging configuration will be
     * pushed to Google Cloud Logging.
     * Please note that any previous pending and running Rollouts and associated
     * Operations will be automatically cancelled so that the latest Rollout will
     * not be blocked by previous Rollouts.
     * Operation&lt;response: Rollout&gt;
     * </pre>
     */
    public void createServiceRollout(com.google.api.servicemanagement.v1.CreateServiceRolloutRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_SERVICE_ROLLOUT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Generates and returns a report (errors, warnings and changes from
     * existing configurations) associated with
     * GenerateConfigReportRequest.new_value
     * If GenerateConfigReportRequest.old_value is specified,
     * GenerateConfigReportRequest will contain a single ChangeReport based on the
     * comparison between GenerateConfigReportRequest.new_value and
     * GenerateConfigReportRequest.old_value.
     * If GenerateConfigReportRequest.old_value is not specified, this method
     * will compare GenerateConfigReportRequest.new_value with the last pushed
     * service configuration.
     * </pre>
     */
    public void generateConfigReport(com.google.api.servicemanagement.v1.GenerateConfigReportRequest request,
        io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.GenerateConfigReportResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GENERATE_CONFIG_REPORT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Enable a managed service for a project with default setting.
     * Operation&lt;response: EnableServiceResponse&gt;
     * [google.rpc.Status][google.rpc.Status] errors may contain a
     * [google.rpc.PreconditionFailure][] error detail.
     * </pre>
     */
    public void enableService(com.google.api.servicemanagement.v1.EnableServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ENABLE_SERVICE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Disable a managed service for a project.
     * Operation&lt;response: DisableServiceResponse&gt;
     * </pre>
     */
    public void disableService(com.google.api.servicemanagement.v1.DisableServiceRequest request,
        io.grpc.stub.StreamObserver<com.google.longrunning.Operation> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DISABLE_SERVICE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * [Google Service Management API](/service-management/overview)
   * </pre>
   */
  public static final class ServiceManagerBlockingStub extends io.grpc.stub.AbstractStub<ServiceManagerBlockingStub> {
    private ServiceManagerBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServiceManagerBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceManagerBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServiceManagerBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Lists all managed services.
     * </pre>
     */
    public com.google.api.servicemanagement.v1.ListServicesResponse listServices(com.google.api.servicemanagement.v1.ListServicesRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LIST_SERVICES, getCallOptions(), request);
    }

    /**
     * <pre>
     * Gets a managed service.
     * </pre>
     */
    public com.google.api.servicemanagement.v1.ManagedService getService(com.google.api.servicemanagement.v1.GetServiceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SERVICE, getCallOptions(), request);
    }

    /**
     * <pre>
     * Creates a new managed service.
     * Please note one producer project can own no more than 20 services.
     * Operation&lt;response: ManagedService&gt;
     * </pre>
     */
    public com.google.longrunning.Operation createService(com.google.api.servicemanagement.v1.CreateServiceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_SERVICE, getCallOptions(), request);
    }

    /**
     * <pre>
     * Deletes a managed service. This method will change the serivce in the
     * `Soft-Delete` state for 30 days. Within this period, service producers may
     * call [UndeleteService][google.api.servicemanagement.v1.ServiceManager.UndeleteService] to restore the service.
     * After 30 days, the service will be permanently deleted.
     * Operation&lt;response: google.protobuf.Empty&gt;
     * </pre>
     */
    public com.google.longrunning.Operation deleteService(com.google.api.servicemanagement.v1.DeleteServiceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DELETE_SERVICE, getCallOptions(), request);
    }

    /**
     * <pre>
     * Revives a previously deleted managed service. The method restores the
     * service using the configuration at the time the service was deleted.
     * The target service must exist and must have been deleted within the
     * last 30 days.
     * Operation&lt;response: UndeleteServiceResponse&gt;
     * </pre>
     */
    public com.google.longrunning.Operation undeleteService(com.google.api.servicemanagement.v1.UndeleteServiceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UNDELETE_SERVICE, getCallOptions(), request);
    }

    /**
     * <pre>
     * Lists the history of the service configuration for a managed service,
     * from the newest to the oldest.
     * </pre>
     */
    public com.google.api.servicemanagement.v1.ListServiceConfigsResponse listServiceConfigs(com.google.api.servicemanagement.v1.ListServiceConfigsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LIST_SERVICE_CONFIGS, getCallOptions(), request);
    }

    /**
     * <pre>
     * Gets a service configuration (version) for a managed service.
     * </pre>
     */
    public com.google.api.Service getServiceConfig(com.google.api.servicemanagement.v1.GetServiceConfigRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SERVICE_CONFIG, getCallOptions(), request);
    }

    /**
     * <pre>
     * Creates a new service configuration (version) for a managed service.
     * This method only stores the service configuration. To roll out the service
     * configuration to backend systems please call
     * [CreateServiceRollout][google.api.servicemanagement.v1.ServiceManager.CreateServiceRollout].
     * </pre>
     */
    public com.google.api.Service createServiceConfig(com.google.api.servicemanagement.v1.CreateServiceConfigRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_SERVICE_CONFIG, getCallOptions(), request);
    }

    /**
     * <pre>
     * Creates a new service configuration (version) for a managed service based
     * on
     * user-supplied configuration source files (for example: OpenAPI
     * Specification). This method stores the source configurations as well as the
     * generated service configuration. To rollout the service configuration to
     * other services,
     * please call [CreateServiceRollout][google.api.servicemanagement.v1.ServiceManager.CreateServiceRollout].
     * Operation&lt;response: SubmitConfigSourceResponse&gt;
     * </pre>
     */
    public com.google.longrunning.Operation submitConfigSource(com.google.api.servicemanagement.v1.SubmitConfigSourceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SUBMIT_CONFIG_SOURCE, getCallOptions(), request);
    }

    /**
     * <pre>
     * Lists the history of the service configuration rollouts for a managed
     * service, from the newest to the oldest.
     * </pre>
     */
    public com.google.api.servicemanagement.v1.ListServiceRolloutsResponse listServiceRollouts(com.google.api.servicemanagement.v1.ListServiceRolloutsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LIST_SERVICE_ROLLOUTS, getCallOptions(), request);
    }

    /**
     * <pre>
     * Gets a service configuration [rollout][google.api.servicemanagement.v1.Rollout].
     * </pre>
     */
    public com.google.api.servicemanagement.v1.Rollout getServiceRollout(com.google.api.servicemanagement.v1.GetServiceRolloutRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SERVICE_ROLLOUT, getCallOptions(), request);
    }

    /**
     * <pre>
     * Creates a new service configuration rollout. Based on rollout, the
     * Google Service Management will roll out the service configurations to
     * different backend services. For example, the logging configuration will be
     * pushed to Google Cloud Logging.
     * Please note that any previous pending and running Rollouts and associated
     * Operations will be automatically cancelled so that the latest Rollout will
     * not be blocked by previous Rollouts.
     * Operation&lt;response: Rollout&gt;
     * </pre>
     */
    public com.google.longrunning.Operation createServiceRollout(com.google.api.servicemanagement.v1.CreateServiceRolloutRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_SERVICE_ROLLOUT, getCallOptions(), request);
    }

    /**
     * <pre>
     * Generates and returns a report (errors, warnings and changes from
     * existing configurations) associated with
     * GenerateConfigReportRequest.new_value
     * If GenerateConfigReportRequest.old_value is specified,
     * GenerateConfigReportRequest will contain a single ChangeReport based on the
     * comparison between GenerateConfigReportRequest.new_value and
     * GenerateConfigReportRequest.old_value.
     * If GenerateConfigReportRequest.old_value is not specified, this method
     * will compare GenerateConfigReportRequest.new_value with the last pushed
     * service configuration.
     * </pre>
     */
    public com.google.api.servicemanagement.v1.GenerateConfigReportResponse generateConfigReport(com.google.api.servicemanagement.v1.GenerateConfigReportRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GENERATE_CONFIG_REPORT, getCallOptions(), request);
    }

    /**
     * <pre>
     * Enable a managed service for a project with default setting.
     * Operation&lt;response: EnableServiceResponse&gt;
     * [google.rpc.Status][google.rpc.Status] errors may contain a
     * [google.rpc.PreconditionFailure][] error detail.
     * </pre>
     */
    public com.google.longrunning.Operation enableService(com.google.api.servicemanagement.v1.EnableServiceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ENABLE_SERVICE, getCallOptions(), request);
    }

    /**
     * <pre>
     * Disable a managed service for a project.
     * Operation&lt;response: DisableServiceResponse&gt;
     * </pre>
     */
    public com.google.longrunning.Operation disableService(com.google.api.servicemanagement.v1.DisableServiceRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DISABLE_SERVICE, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * [Google Service Management API](/service-management/overview)
   * </pre>
   */
  public static final class ServiceManagerFutureStub extends io.grpc.stub.AbstractStub<ServiceManagerFutureStub> {
    private ServiceManagerFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ServiceManagerFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceManagerFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ServiceManagerFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Lists all managed services.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.api.servicemanagement.v1.ListServicesResponse> listServices(
        com.google.api.servicemanagement.v1.ListServicesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LIST_SERVICES, getCallOptions()), request);
    }

    /**
     * <pre>
     * Gets a managed service.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.api.servicemanagement.v1.ManagedService> getService(
        com.google.api.servicemanagement.v1.GetServiceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SERVICE, getCallOptions()), request);
    }

    /**
     * <pre>
     * Creates a new managed service.
     * Please note one producer project can own no more than 20 services.
     * Operation&lt;response: ManagedService&gt;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> createService(
        com.google.api.servicemanagement.v1.CreateServiceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_SERVICE, getCallOptions()), request);
    }

    /**
     * <pre>
     * Deletes a managed service. This method will change the serivce in the
     * `Soft-Delete` state for 30 days. Within this period, service producers may
     * call [UndeleteService][google.api.servicemanagement.v1.ServiceManager.UndeleteService] to restore the service.
     * After 30 days, the service will be permanently deleted.
     * Operation&lt;response: google.protobuf.Empty&gt;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> deleteService(
        com.google.api.servicemanagement.v1.DeleteServiceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DELETE_SERVICE, getCallOptions()), request);
    }

    /**
     * <pre>
     * Revives a previously deleted managed service. The method restores the
     * service using the configuration at the time the service was deleted.
     * The target service must exist and must have been deleted within the
     * last 30 days.
     * Operation&lt;response: UndeleteServiceResponse&gt;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> undeleteService(
        com.google.api.servicemanagement.v1.UndeleteServiceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UNDELETE_SERVICE, getCallOptions()), request);
    }

    /**
     * <pre>
     * Lists the history of the service configuration for a managed service,
     * from the newest to the oldest.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.api.servicemanagement.v1.ListServiceConfigsResponse> listServiceConfigs(
        com.google.api.servicemanagement.v1.ListServiceConfigsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LIST_SERVICE_CONFIGS, getCallOptions()), request);
    }

    /**
     * <pre>
     * Gets a service configuration (version) for a managed service.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.api.Service> getServiceConfig(
        com.google.api.servicemanagement.v1.GetServiceConfigRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SERVICE_CONFIG, getCallOptions()), request);
    }

    /**
     * <pre>
     * Creates a new service configuration (version) for a managed service.
     * This method only stores the service configuration. To roll out the service
     * configuration to backend systems please call
     * [CreateServiceRollout][google.api.servicemanagement.v1.ServiceManager.CreateServiceRollout].
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.api.Service> createServiceConfig(
        com.google.api.servicemanagement.v1.CreateServiceConfigRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_SERVICE_CONFIG, getCallOptions()), request);
    }

    /**
     * <pre>
     * Creates a new service configuration (version) for a managed service based
     * on
     * user-supplied configuration source files (for example: OpenAPI
     * Specification). This method stores the source configurations as well as the
     * generated service configuration. To rollout the service configuration to
     * other services,
     * please call [CreateServiceRollout][google.api.servicemanagement.v1.ServiceManager.CreateServiceRollout].
     * Operation&lt;response: SubmitConfigSourceResponse&gt;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> submitConfigSource(
        com.google.api.servicemanagement.v1.SubmitConfigSourceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SUBMIT_CONFIG_SOURCE, getCallOptions()), request);
    }

    /**
     * <pre>
     * Lists the history of the service configuration rollouts for a managed
     * service, from the newest to the oldest.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.api.servicemanagement.v1.ListServiceRolloutsResponse> listServiceRollouts(
        com.google.api.servicemanagement.v1.ListServiceRolloutsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LIST_SERVICE_ROLLOUTS, getCallOptions()), request);
    }

    /**
     * <pre>
     * Gets a service configuration [rollout][google.api.servicemanagement.v1.Rollout].
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.api.servicemanagement.v1.Rollout> getServiceRollout(
        com.google.api.servicemanagement.v1.GetServiceRolloutRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SERVICE_ROLLOUT, getCallOptions()), request);
    }

    /**
     * <pre>
     * Creates a new service configuration rollout. Based on rollout, the
     * Google Service Management will roll out the service configurations to
     * different backend services. For example, the logging configuration will be
     * pushed to Google Cloud Logging.
     * Please note that any previous pending and running Rollouts and associated
     * Operations will be automatically cancelled so that the latest Rollout will
     * not be blocked by previous Rollouts.
     * Operation&lt;response: Rollout&gt;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> createServiceRollout(
        com.google.api.servicemanagement.v1.CreateServiceRolloutRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_SERVICE_ROLLOUT, getCallOptions()), request);
    }

    /**
     * <pre>
     * Generates and returns a report (errors, warnings and changes from
     * existing configurations) associated with
     * GenerateConfigReportRequest.new_value
     * If GenerateConfigReportRequest.old_value is specified,
     * GenerateConfigReportRequest will contain a single ChangeReport based on the
     * comparison between GenerateConfigReportRequest.new_value and
     * GenerateConfigReportRequest.old_value.
     * If GenerateConfigReportRequest.old_value is not specified, this method
     * will compare GenerateConfigReportRequest.new_value with the last pushed
     * service configuration.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.api.servicemanagement.v1.GenerateConfigReportResponse> generateConfigReport(
        com.google.api.servicemanagement.v1.GenerateConfigReportRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GENERATE_CONFIG_REPORT, getCallOptions()), request);
    }

    /**
     * <pre>
     * Enable a managed service for a project with default setting.
     * Operation&lt;response: EnableServiceResponse&gt;
     * [google.rpc.Status][google.rpc.Status] errors may contain a
     * [google.rpc.PreconditionFailure][] error detail.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> enableService(
        com.google.api.servicemanagement.v1.EnableServiceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ENABLE_SERVICE, getCallOptions()), request);
    }

    /**
     * <pre>
     * Disable a managed service for a project.
     * Operation&lt;response: DisableServiceResponse&gt;
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.longrunning.Operation> disableService(
        com.google.api.servicemanagement.v1.DisableServiceRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DISABLE_SERVICE, getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_SERVICES = 0;
  private static final int METHODID_GET_SERVICE = 1;
  private static final int METHODID_CREATE_SERVICE = 2;
  private static final int METHODID_DELETE_SERVICE = 3;
  private static final int METHODID_UNDELETE_SERVICE = 4;
  private static final int METHODID_LIST_SERVICE_CONFIGS = 5;
  private static final int METHODID_GET_SERVICE_CONFIG = 6;
  private static final int METHODID_CREATE_SERVICE_CONFIG = 7;
  private static final int METHODID_SUBMIT_CONFIG_SOURCE = 8;
  private static final int METHODID_LIST_SERVICE_ROLLOUTS = 9;
  private static final int METHODID_GET_SERVICE_ROLLOUT = 10;
  private static final int METHODID_CREATE_SERVICE_ROLLOUT = 11;
  private static final int METHODID_GENERATE_CONFIG_REPORT = 12;
  private static final int METHODID_ENABLE_SERVICE = 13;
  private static final int METHODID_DISABLE_SERVICE = 14;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServiceManagerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServiceManagerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIST_SERVICES:
          serviceImpl.listServices((com.google.api.servicemanagement.v1.ListServicesRequest) request,
              (io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ListServicesResponse>) responseObserver);
          break;
        case METHODID_GET_SERVICE:
          serviceImpl.getService((com.google.api.servicemanagement.v1.GetServiceRequest) request,
              (io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ManagedService>) responseObserver);
          break;
        case METHODID_CREATE_SERVICE:
          serviceImpl.createService((com.google.api.servicemanagement.v1.CreateServiceRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
          break;
        case METHODID_DELETE_SERVICE:
          serviceImpl.deleteService((com.google.api.servicemanagement.v1.DeleteServiceRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
          break;
        case METHODID_UNDELETE_SERVICE:
          serviceImpl.undeleteService((com.google.api.servicemanagement.v1.UndeleteServiceRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
          break;
        case METHODID_LIST_SERVICE_CONFIGS:
          serviceImpl.listServiceConfigs((com.google.api.servicemanagement.v1.ListServiceConfigsRequest) request,
              (io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ListServiceConfigsResponse>) responseObserver);
          break;
        case METHODID_GET_SERVICE_CONFIG:
          serviceImpl.getServiceConfig((com.google.api.servicemanagement.v1.GetServiceConfigRequest) request,
              (io.grpc.stub.StreamObserver<com.google.api.Service>) responseObserver);
          break;
        case METHODID_CREATE_SERVICE_CONFIG:
          serviceImpl.createServiceConfig((com.google.api.servicemanagement.v1.CreateServiceConfigRequest) request,
              (io.grpc.stub.StreamObserver<com.google.api.Service>) responseObserver);
          break;
        case METHODID_SUBMIT_CONFIG_SOURCE:
          serviceImpl.submitConfigSource((com.google.api.servicemanagement.v1.SubmitConfigSourceRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
          break;
        case METHODID_LIST_SERVICE_ROLLOUTS:
          serviceImpl.listServiceRollouts((com.google.api.servicemanagement.v1.ListServiceRolloutsRequest) request,
              (io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.ListServiceRolloutsResponse>) responseObserver);
          break;
        case METHODID_GET_SERVICE_ROLLOUT:
          serviceImpl.getServiceRollout((com.google.api.servicemanagement.v1.GetServiceRolloutRequest) request,
              (io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.Rollout>) responseObserver);
          break;
        case METHODID_CREATE_SERVICE_ROLLOUT:
          serviceImpl.createServiceRollout((com.google.api.servicemanagement.v1.CreateServiceRolloutRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
          break;
        case METHODID_GENERATE_CONFIG_REPORT:
          serviceImpl.generateConfigReport((com.google.api.servicemanagement.v1.GenerateConfigReportRequest) request,
              (io.grpc.stub.StreamObserver<com.google.api.servicemanagement.v1.GenerateConfigReportResponse>) responseObserver);
          break;
        case METHODID_ENABLE_SERVICE:
          serviceImpl.enableService((com.google.api.servicemanagement.v1.EnableServiceRequest) request,
              (io.grpc.stub.StreamObserver<com.google.longrunning.Operation>) responseObserver);
          break;
        case METHODID_DISABLE_SERVICE:
          serviceImpl.disableService((com.google.api.servicemanagement.v1.DisableServiceRequest) request,
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

  private static final class ServiceManagerDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.google.api.servicemanagement.v1.ServiceManagerProto.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ServiceManagerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServiceManagerDescriptorSupplier())
              .addMethod(METHOD_LIST_SERVICES)
              .addMethod(METHOD_GET_SERVICE)
              .addMethod(METHOD_CREATE_SERVICE)
              .addMethod(METHOD_DELETE_SERVICE)
              .addMethod(METHOD_UNDELETE_SERVICE)
              .addMethod(METHOD_LIST_SERVICE_CONFIGS)
              .addMethod(METHOD_GET_SERVICE_CONFIG)
              .addMethod(METHOD_CREATE_SERVICE_CONFIG)
              .addMethod(METHOD_SUBMIT_CONFIG_SOURCE)
              .addMethod(METHOD_LIST_SERVICE_ROLLOUTS)
              .addMethod(METHOD_GET_SERVICE_ROLLOUT)
              .addMethod(METHOD_CREATE_SERVICE_ROLLOUT)
              .addMethod(METHOD_GENERATE_CONFIG_REPORT)
              .addMethod(METHOD_ENABLE_SERVICE)
              .addMethod(METHOD_DISABLE_SERVICE)
              .build();
        }
      }
    }
    return result;
  }
}
