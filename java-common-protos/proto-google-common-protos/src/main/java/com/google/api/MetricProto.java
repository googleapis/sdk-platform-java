/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: google/api/metric.proto
// Protobuf Java Version: 4.27.1

package com.google.api;

public final class MetricProto {
  private MetricProto() {}

  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 27,
        /* patch= */ 1,
        /* suffix= */ "",
        MetricProto.class.getName());
  }

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_MetricDescriptor_descriptor;
  static final com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_google_api_MetricDescriptor_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_MetricDescriptor_MetricDescriptorMetadata_descriptor;
  static final com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_google_api_MetricDescriptor_MetricDescriptorMetadata_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_Metric_descriptor;
  static final com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_google_api_Metric_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_Metric_LabelsEntry_descriptor;
  static final com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_google_api_Metric_LabelsEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\027google/api/metric.proto\022\ngoogle.api\032\026g"
          + "oogle/api/label.proto\032\035google/api/launch"
          + "_stage.proto\032\036google/protobuf/duration.p"
          + "roto\"\237\006\n\020MetricDescriptor\022\014\n\004name\030\001 \001(\t\022"
          + "\014\n\004type\030\010 \001(\t\022+\n\006labels\030\002 \003(\0132\033.google.a"
          + "pi.LabelDescriptor\022<\n\013metric_kind\030\003 \001(\0162"
          + "\'.google.api.MetricDescriptor.MetricKind"
          + "\022:\n\nvalue_type\030\004 \001(\0162&.google.api.Metric"
          + "Descriptor.ValueType\022\014\n\004unit\030\005 \001(\t\022\023\n\013de"
          + "scription\030\006 \001(\t\022\024\n\014display_name\030\007 \001(\t\022G\n"
          + "\010metadata\030\n \001(\01325.google.api.MetricDescr"
          + "iptor.MetricDescriptorMetadata\022-\n\014launch"
          + "_stage\030\014 \001(\0162\027.google.api.LaunchStage\022 \n"
          + "\030monitored_resource_types\030\r \003(\t\032\260\001\n\030Metr"
          + "icDescriptorMetadata\0221\n\014launch_stage\030\001 \001"
          + "(\0162\027.google.api.LaunchStageB\002\030\001\0220\n\rsampl"
          + "e_period\030\002 \001(\0132\031.google.protobuf.Duratio"
          + "n\022/\n\014ingest_delay\030\003 \001(\0132\031.google.protobu"
          + "f.Duration\"O\n\nMetricKind\022\033\n\027METRIC_KIND_"
          + "UNSPECIFIED\020\000\022\t\n\005GAUGE\020\001\022\t\n\005DELTA\020\002\022\016\n\nC"
          + "UMULATIVE\020\003\"q\n\tValueType\022\032\n\026VALUE_TYPE_U"
          + "NSPECIFIED\020\000\022\010\n\004BOOL\020\001\022\t\n\005INT64\020\002\022\n\n\006DOU"
          + "BLE\020\003\022\n\n\006STRING\020\004\022\020\n\014DISTRIBUTION\020\005\022\t\n\005M"
          + "ONEY\020\006\"u\n\006Metric\022\014\n\004type\030\003 \001(\t\022.\n\006labels"
          + "\030\002 \003(\0132\036.google.api.Metric.LabelsEntry\032-"
          + "\n\013LabelsEntry\022\013\n\003key\030\001 \001(\t\022\r\n\005value\030\002 \001("
          + "\t:\0028\001B_\n\016com.google.apiB\013MetricProtoP\001Z7"
          + "google.golang.org/genproto/googleapis/ap"
          + "i/metric;metric\242\002\004GAPIb\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.api.LabelProto.getDescriptor(),
              com.google.api.LaunchStageProto.getDescriptor(),
              com.google.protobuf.DurationProto.getDescriptor(),
            });
    internal_static_google_api_MetricDescriptor_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_google_api_MetricDescriptor_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessage.FieldAccessorTable(
            internal_static_google_api_MetricDescriptor_descriptor,
            new java.lang.String[] {
              "Name",
              "Type",
              "Labels",
              "MetricKind",
              "ValueType",
              "Unit",
              "Description",
              "DisplayName",
              "Metadata",
              "LaunchStage",
              "MonitoredResourceTypes",
            });
    internal_static_google_api_MetricDescriptor_MetricDescriptorMetadata_descriptor =
        internal_static_google_api_MetricDescriptor_descriptor.getNestedTypes().get(0);
    internal_static_google_api_MetricDescriptor_MetricDescriptorMetadata_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessage.FieldAccessorTable(
            internal_static_google_api_MetricDescriptor_MetricDescriptorMetadata_descriptor,
            new java.lang.String[] {
              "LaunchStage", "SamplePeriod", "IngestDelay",
            });
    internal_static_google_api_Metric_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_google_api_Metric_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessage.FieldAccessorTable(
            internal_static_google_api_Metric_descriptor,
            new java.lang.String[] {
              "Type", "Labels",
            });
    internal_static_google_api_Metric_LabelsEntry_descriptor =
        internal_static_google_api_Metric_descriptor.getNestedTypes().get(0);
    internal_static_google_api_Metric_LabelsEntry_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessage.FieldAccessorTable(
            internal_static_google_api_Metric_LabelsEntry_descriptor,
            new java.lang.String[] {
              "Key", "Value",
            });
    descriptor.resolveAllFeaturesImmutable();
    com.google.api.LabelProto.getDescriptor();
    com.google.api.LaunchStageProto.getDescriptor();
    com.google.protobuf.DurationProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
