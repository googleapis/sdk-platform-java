// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/type/date.proto

package com.google.type;

public final class DateProto {
  private DateProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_type_Date_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_type_Date_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\026google/type/date.proto\022\013google.type\"0\n" +
      "\004Date\022\014\n\004year\030\001 \001(\005\022\r\n\005month\030\002 \001(\005\022\013\n\003da" +
      "y\030\003 \001(\005B]\n\017com.google.typeB\tDateProtoP\001Z" +
      "4google.golang.org/genproto/googleapis/t" +
      "ype/date;date\370\001\001\242\002\003GTPb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_google_type_Date_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_google_type_Date_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_type_Date_descriptor,
        new java.lang.String[] { "Year", "Month", "Day", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
