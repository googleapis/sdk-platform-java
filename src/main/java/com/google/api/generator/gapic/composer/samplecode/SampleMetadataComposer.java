package com.google.api.generator.gapic.composer.samplecode;

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.SampleMetadata;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SampleMetadataComposer {
  public static String composeClientLibraryName() {
    // TODO
    return "";
  }

  public static List<SampleMetadata.Api> composeApiList() {
    // TODO
    return new ArrayList<>();
  }

  public static List<SampleMetadata.Snippet> composeSnippets(List<GapicClass> gapicClasses) {
    List<SampleMetadata.Snippet> snippets = new ArrayList<>();
    gapicClasses.stream()
        .forEach(
            gapicClass -> {
              //  ex: com.google.cloud.redis.v1
              String pakkage = gapicClass.classDefinition().packageString();
              //  ex: CloudRedisClient
              String clazz = gapicClass.classDefinition().classIdentifier().name();

              snippets.addAll(
                  gapicClass.samples().stream()
                      .map(
                          sample ->
                              new SampleMetadata.Snippet(
                                  // ex: regionTag =
                                  // redis_v1beta1_generated_cloudredisclient_deleteinstance_sync
                                  sample.regionTag().generate(),
                                  // ex: title = SyncDeleteInstance
                                  sample.name(),
                                  // ex: description = Generated snippet for deleteinstance
                                  "Generated snippet for" + sample.regionTag().rpcName(),
                                  // ex: file =
                                  // samples/snippets/generated/com/google/cloud/redis/v1beta1/cloudredisclient/deleteinstance/SyncDeleteInstance.java
                                  // TODO: sample.file is new
                                  composeSampleFile(),
                                  // ex: language = JAVA
                                  SampleMetadata.Language.JAVA,
                                  new SampleMetadata.ClientMethod(
                                      // ex: shortName = DeleteInstance
                                      sample.regionTag().rpcName(),
                                      // ex: fullName =
                                      // com.google.cloud.redis.v1.CloudRedisClient.DeleteInstance
                                      pakkage + "." + clazz + "." + sample.regionTag().rpcName(),
                                      // ex: async: false
                                      sample.regionTag().isAsynchronous(),
                                      // TODO: include sample.argments + composeParameters
                                      composeClientMethodParameters(sample.arguments()),
                                      // TODO: sample.resultType is new + composeResultType
                                      composeClientMethodResultType(sample.resultType()),
                                      new SampleMetadata.ServiceClient(
                                          // ex: shortName = redis
                                          sample.regionTag().apiShortName(),
                                          // TODO: service client fullName
                                          composeServiceClientFullName()),
                                      new SampleMetadata.Method(
                                          sample.regionTag().rpcName(),
                                          // TODO: method fullName
                                          composeMethodFullName(),
                                          new SampleMetadata.Service(
                                              sample.regionTag().serviceName(),
                                              composeServiceFullName()))),
                                  sample.isCanonical(),
                                  SampleMetadata.Snippet.Origin.API_DEFINITION,
                                  composeSnippetSegments()))
                      .collect(Collectors.toList()));
            });
    return snippets;
  }

  static String composeSampleFile() {
    // TODO
    return "";
  }

  static String composeServiceFullName() {
    // TODO
    return "";
  }

  static String composeServiceClientFullName() {
    // TODO
    return "";
  }

  static String composeMethodFullName() {
    // TODO
    return "";
  }

  static List<SampleMetadata.ClientMethod.Parameter> composeClientMethodParameters(
      List<VariableExpr> arguments) {
    return arguments.stream()
        .map(
            arg ->
                new SampleMetadata.ClientMethod.Parameter(
                    arg.variable().type().toString(), arg.variable().identifier().name()))
        .collect(Collectors.toList());
  }

  static String composeClientMethodResultType(TypeNode resultType) {
    return resultType.typeKind().name();
  }

  static List<SampleMetadata.Snippet.Segment> composeSnippetSegments() {
    // TODO

    //    Arrays.asList(
    //            //  sample body - snippet without boilerplate code
    //            new Snippet.Segment("","", Snippet.Segment.SegmentType.SHORT),
    //            //  "executable" sample - full drop in sample
    //            new Snippet.Segment("","", Snippet.Segment.SegmentType.FULL),
    //
    //            new Snippet.Segment("","", Snippet.Segment.SegmentType.REQUEST_INITIALIZATION),
    //            new Snippet.Segment("","", Snippet.Segment.SegmentType.REQUEST_EXECUTION),
    //            new Snippet.Segment("","", Snippet.Segment.SegmentType.RESPONSE_HANDLING),
    //            new Snippet.Segment("","", Snippet.Segment.SegmentType.CLIENT_INITIALIZATION),

    return new ArrayList<>();
  }
}
