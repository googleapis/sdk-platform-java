// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/sequence.proto

// Protobuf Java Version: 3.25.3
package com.google.showcase.v1beta1;

public final class SequenceOuterClass {
  private SequenceOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_Sequence_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_Sequence_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_Sequence_Response_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_Sequence_Response_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_StreamingSequence_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_StreamingSequence_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_StreamingSequence_Response_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_StreamingSequence_Response_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_StreamingSequenceReport_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_StreamingSequenceReport_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_StreamingSequenceReport_Attempt_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_StreamingSequenceReport_Attempt_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_SequenceReport_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_SequenceReport_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_SequenceReport_Attempt_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_SequenceReport_Attempt_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_CreateSequenceRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_CreateSequenceRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_CreateStreamingSequenceRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_CreateStreamingSequenceRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_AttemptSequenceRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_AttemptSequenceRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_AttemptStreamingSequenceRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_AttemptStreamingSequenceRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_AttemptStreamingSequenceResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_AttemptStreamingSequenceResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_GetSequenceReportRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_GetSequenceReportRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_showcase_v1beta1_GetStreamingSequenceReportRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_GetStreamingSequenceReportRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n-schema/google/showcase/v1beta1/sequenc" +
      "e.proto\022\027google.showcase.v1beta1\032\034google" +
      "/api/annotations.proto\032\027google/api/clien" +
      "t.proto\032\037google/api/field_behavior.proto" +
      "\032\031google/api/resource.proto\032\036google/prot" +
      "obuf/duration.proto\032\033google/protobuf/emp" +
      "ty.proto\032\037google/protobuf/timestamp.prot" +
      "o\032\027google/rpc/status.proto\"\363\001\n\010Sequence\022" +
      "\021\n\004name\030\001 \001(\tB\003\340A\003\022=\n\tresponses\030\002 \003(\0132*." +
      "google.showcase.v1beta1.Sequence.Respons" +
      "e\032X\n\010Response\022\"\n\006status\030\001 \001(\0132\022.google.r" +
      "pc.Status\022(\n\005delay\030\002 \001(\0132\031.google.protob" +
      "uf.Duration:;\352A8\n showcase.googleapis.co" +
      "m/Sequence\022\024sequences/{sequence}\"\312\002\n\021Str" +
      "eamingSequence\022\021\n\004name\030\001 \001(\tB\003\340A\003\022\017\n\007con" +
      "tent\030\002 \001(\t\022F\n\tresponses\030\003 \003(\01323.google.s" +
      "howcase.v1beta1.StreamingSequence.Respon" +
      "se\032p\n\010Response\022\"\n\006status\030\001 \001(\0132\022.google." +
      "rpc.Status\022(\n\005delay\030\002 \001(\0132\031.google.proto" +
      "buf.Duration\022\026\n\016response_index\030\003 \001(\005:W\352A" +
      "T\n)showcase.googleapis.com/StreamingSequ" +
      "ence\022\'streamingSequences/{streaming_sequ" +
      "ence}\"\322\003\n\027StreamingSequenceReport\022\021\n\004nam" +
      "e\030\001 \001(\tB\003\340A\003\022J\n\010attempts\030\002 \003(\01328.google." +
      "showcase.v1beta1.StreamingSequenceReport" +
      ".Attempt\032\340\001\n\007Attempt\022\026\n\016attempt_number\030\001" +
      " \001(\005\0224\n\020attempt_deadline\030\002 \001(\0132\032.google." +
      "protobuf.Timestamp\0221\n\rresponse_time\030\003 \001(" +
      "\0132\032.google.protobuf.Timestamp\0220\n\rattempt" +
      "_delay\030\004 \001(\0132\031.google.protobuf.Duration\022" +
      "\"\n\006status\030\005 \001(\0132\022.google.rpc.Status:u\352Ar" +
      "\n/showcase.googleapis.com/StreamingSeque" +
      "nceReport\022?streamingSequences/{streaming" +
      "_sequence}/streamingSequenceReport\"\233\003\n\016S" +
      "equenceReport\022\021\n\004name\030\001 \001(\tB\003\340A\003\022A\n\010atte" +
      "mpts\030\002 \003(\0132/.google.showcase.v1beta1.Seq" +
      "uenceReport.Attempt\032\340\001\n\007Attempt\022\026\n\016attem" +
      "pt_number\030\001 \001(\005\0224\n\020attempt_deadline\030\002 \001(" +
      "\0132\032.google.protobuf.Timestamp\0221\n\rrespons" +
      "e_time\030\003 \001(\0132\032.google.protobuf.Timestamp" +
      "\0220\n\rattempt_delay\030\004 \001(\0132\031.google.protobu" +
      "f.Duration\022\"\n\006status\030\005 \001(\0132\022.google.rpc." +
      "Status:P\352AM\n&showcase.googleapis.com/Seq" +
      "uenceReport\022#sequences/{sequence}/sequen" +
      "ceReport\"L\n\025CreateSequenceRequest\0223\n\010seq" +
      "uence\030\001 \001(\0132!.google.showcase.v1beta1.Se" +
      "quence\"h\n\036CreateStreamingSequenceRequest" +
      "\022F\n\022streaming_sequence\030\001 \001(\0132*.google.sh" +
      "owcase.v1beta1.StreamingSequence\"P\n\026Atte" +
      "mptSequenceRequest\0226\n\004name\030\001 \001(\tB(\340A\002\372A\"" +
      "\n showcase.googleapis.com/Sequence\"\200\001\n\037A" +
      "ttemptStreamingSequenceRequest\022?\n\004name\030\001" +
      " \001(\tB1\340A\002\372A+\n)showcase.googleapis.com/St" +
      "reamingSequence\022\034\n\017last_fail_index\030\002 \001(\005" +
      "B\003\340A\001\"3\n AttemptStreamingSequenceRespons" +
      "e\022\017\n\007content\030\001 \001(\t\"X\n\030GetSequenceReportR" +
      "equest\022<\n\004name\030\001 \001(\tB.\340A\002\372A(\n&showcase.g" +
      "oogleapis.com/SequenceReport\"j\n!GetStrea" +
      "mingSequenceReportRequest\022E\n\004name\030\001 \001(\tB" +
      "7\340A\002\372A1\n/showcase.googleapis.com/Streami" +
      "ngSequenceReport2\360\010\n\017SequenceService\022\224\001\n" +
      "\016CreateSequence\022..google.showcase.v1beta" +
      "1.CreateSequenceRequest\032!.google.showcas" +
      "e.v1beta1.Sequence\"/\332A\010sequence\202\323\344\223\002\036\"\022/" +
      "v1beta1/sequences:\010sequence\022\314\001\n\027CreateSt" +
      "reamingSequence\0227.google.showcase.v1beta" +
      "1.CreateStreamingSequenceRequest\032*.googl" +
      "e.showcase.v1beta1.StreamingSequence\"L\332A" +
      "\022streaming_sequence\202\323\344\223\0021\"\033/v1beta1/stre" +
      "amingSequences:\022streaming_sequence\022\252\001\n\021G" +
      "etSequenceReport\0221.google.showcase.v1bet" +
      "a1.GetSequenceReportRequest\032\'.google.sho" +
      "wcase.v1beta1.SequenceReport\"9\332A\004name\202\323\344" +
      "\223\002,\022*/v1beta1/{name=sequences/*/sequence" +
      "Report}\022\327\001\n\032GetStreamingSequenceReport\022:" +
      ".google.showcase.v1beta1.GetStreamingSeq" +
      "uenceReportRequest\0320.google.showcase.v1b" +
      "eta1.StreamingSequenceReport\"K\332A\004name\202\323\344" +
      "\223\002>\022</v1beta1/{name=streamingSequences/*" +
      "/streamingSequenceReport}\022\211\001\n\017AttemptSeq" +
      "uence\022/.google.showcase.v1beta1.AttemptS" +
      "equenceRequest\032\026.google.protobuf.Empty\"-" +
      "\332A\004name\202\323\344\223\002 \"\033/v1beta1/{name=sequences/" +
      "*}:\001*\022\320\001\n\030AttemptStreamingSequence\0228.goo" +
      "gle.showcase.v1beta1.AttemptStreamingSeq" +
      "uenceRequest\0329.google.showcase.v1beta1.A" +
      "ttemptStreamingSequenceResponse\"=\332A\004name" +
      "\202\323\344\223\0020\"+/v1beta1/{name=streamingSequence" +
      "s/*}:stream:\001*0\001\032\021\312A\016localhost:7469Bq\n\033c" +
      "om.google.showcase.v1beta1P\001Z4github.com" +
      "/googleapis/gapic-showcase/server/genpro" +
      "to\352\002\031Google::Showcase::V1beta1b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.api.AnnotationsProto.getDescriptor(),
          com.google.api.ClientProto.getDescriptor(),
          com.google.api.FieldBehaviorProto.getDescriptor(),
          com.google.api.ResourceProto.getDescriptor(),
          com.google.protobuf.DurationProto.getDescriptor(),
          com.google.protobuf.EmptyProto.getDescriptor(),
          com.google.protobuf.TimestampProto.getDescriptor(),
          com.google.rpc.StatusProto.getDescriptor(),
        });
    internal_static_google_showcase_v1beta1_Sequence_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_google_showcase_v1beta1_Sequence_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_Sequence_descriptor,
        new java.lang.String[] { "Name", "Responses", });
    internal_static_google_showcase_v1beta1_Sequence_Response_descriptor =
      internal_static_google_showcase_v1beta1_Sequence_descriptor.getNestedTypes().get(0);
    internal_static_google_showcase_v1beta1_Sequence_Response_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_Sequence_Response_descriptor,
        new java.lang.String[] { "Status", "Delay", });
    internal_static_google_showcase_v1beta1_StreamingSequence_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_google_showcase_v1beta1_StreamingSequence_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_StreamingSequence_descriptor,
        new java.lang.String[] { "Name", "Content", "Responses", });
    internal_static_google_showcase_v1beta1_StreamingSequence_Response_descriptor =
      internal_static_google_showcase_v1beta1_StreamingSequence_descriptor.getNestedTypes().get(0);
    internal_static_google_showcase_v1beta1_StreamingSequence_Response_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_StreamingSequence_Response_descriptor,
        new java.lang.String[] { "Status", "Delay", "ResponseIndex", });
    internal_static_google_showcase_v1beta1_StreamingSequenceReport_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_google_showcase_v1beta1_StreamingSequenceReport_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_StreamingSequenceReport_descriptor,
        new java.lang.String[] { "Name", "Attempts", });
    internal_static_google_showcase_v1beta1_StreamingSequenceReport_Attempt_descriptor =
      internal_static_google_showcase_v1beta1_StreamingSequenceReport_descriptor.getNestedTypes().get(0);
    internal_static_google_showcase_v1beta1_StreamingSequenceReport_Attempt_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_StreamingSequenceReport_Attempt_descriptor,
        new java.lang.String[] { "AttemptNumber", "AttemptDeadline", "ResponseTime", "AttemptDelay", "Status", });
    internal_static_google_showcase_v1beta1_SequenceReport_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_google_showcase_v1beta1_SequenceReport_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_SequenceReport_descriptor,
        new java.lang.String[] { "Name", "Attempts", });
    internal_static_google_showcase_v1beta1_SequenceReport_Attempt_descriptor =
      internal_static_google_showcase_v1beta1_SequenceReport_descriptor.getNestedTypes().get(0);
    internal_static_google_showcase_v1beta1_SequenceReport_Attempt_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_SequenceReport_Attempt_descriptor,
        new java.lang.String[] { "AttemptNumber", "AttemptDeadline", "ResponseTime", "AttemptDelay", "Status", });
    internal_static_google_showcase_v1beta1_CreateSequenceRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_google_showcase_v1beta1_CreateSequenceRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_CreateSequenceRequest_descriptor,
        new java.lang.String[] { "Sequence", });
    internal_static_google_showcase_v1beta1_CreateStreamingSequenceRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_google_showcase_v1beta1_CreateStreamingSequenceRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_CreateStreamingSequenceRequest_descriptor,
        new java.lang.String[] { "StreamingSequence", });
    internal_static_google_showcase_v1beta1_AttemptSequenceRequest_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_google_showcase_v1beta1_AttemptSequenceRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_AttemptSequenceRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_google_showcase_v1beta1_AttemptStreamingSequenceRequest_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_google_showcase_v1beta1_AttemptStreamingSequenceRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_AttemptStreamingSequenceRequest_descriptor,
        new java.lang.String[] { "Name", "LastFailIndex", });
    internal_static_google_showcase_v1beta1_AttemptStreamingSequenceResponse_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_google_showcase_v1beta1_AttemptStreamingSequenceResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_AttemptStreamingSequenceResponse_descriptor,
        new java.lang.String[] { "Content", });
    internal_static_google_showcase_v1beta1_GetSequenceReportRequest_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_google_showcase_v1beta1_GetSequenceReportRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_GetSequenceReportRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_google_showcase_v1beta1_GetStreamingSequenceReportRequest_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_google_showcase_v1beta1_GetStreamingSequenceReportRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_showcase_v1beta1_GetStreamingSequenceReportRequest_descriptor,
        new java.lang.String[] { "Name", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.google.api.ClientProto.defaultHost);
    registry.add(com.google.api.FieldBehaviorProto.fieldBehavior);
    registry.add(com.google.api.AnnotationsProto.http);
    registry.add(com.google.api.ClientProto.methodSignature);
    registry.add(com.google.api.ResourceProto.resource);
    registry.add(com.google.api.ResourceProto.resourceReference);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    com.google.api.AnnotationsProto.getDescriptor();
    com.google.api.ClientProto.getDescriptor();
    com.google.api.FieldBehaviorProto.getDescriptor();
    com.google.api.ResourceProto.getDescriptor();
    com.google.protobuf.DurationProto.getDescriptor();
    com.google.protobuf.EmptyProto.getDescriptor();
    com.google.protobuf.TimestampProto.getDescriptor();
    com.google.rpc.StatusProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
