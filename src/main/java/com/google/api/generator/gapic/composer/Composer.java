// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.gapic.composer;

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.gapic.composer.comment.CommentComposer;
import com.google.api.generator.gapic.composer.grpc.GrpcServiceCallableFactoryClassComposer;
import com.google.api.generator.gapic.composer.grpc.GrpcServiceStubClassComposer;
import com.google.api.generator.gapic.composer.grpc.MockServiceClassComposer;
import com.google.api.generator.gapic.composer.grpc.MockServiceImplClassComposer;
import com.google.api.generator.gapic.composer.grpc.ServiceClientClassComposer;
import com.google.api.generator.gapic.composer.grpc.ServiceClientTestClassComposer;
import com.google.api.generator.gapic.composer.grpc.ServiceSettingsClassComposer;
import com.google.api.generator.gapic.composer.grpc.ServiceStubClassComposer;
import com.google.api.generator.gapic.composer.grpc.ServiceStubSettingsClassComposer;
import com.google.api.generator.gapic.composer.grpcrest.HttpJsonServiceClientTestClassComposer;
import com.google.api.generator.gapic.composer.resourcename.ResourceNameHelperClassComposer;
import com.google.api.generator.gapic.composer.rest.HttpJsonServiceCallableFactoryClassComposer;
import com.google.api.generator.gapic.composer.rest.HttpJsonServiceStubClassComposer;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicPackageInfo;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.model.Transport;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Composer {
  public static List<GapicClass> composeServiceClasses(GapicContext context) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.addAll(generateServiceClasses(context));
    clazzes.addAll(generateMockClasses(context, context.mixinServices()));
    clazzes.addAll(generateResourceNameHelperClasses(context));
    return addApacheLicense(
        prepareExecutableSamples(clazzes, context.gapicMetadata().getProtoPackage()));
  }

  public static GapicPackageInfo composePackageInfo(GapicContext context) {
    return addApacheLicense(ClientLibraryPackageInfoComposer.generatePackageInfo(context));
  }

  public static List<GapicClass> generateServiceClasses(GapicContext context) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.addAll(generateStubClasses(context));
    clazzes.addAll(generateClientSettingsClasses(context));
    clazzes.addAll(generateMockClasses(context, context.services()));
    clazzes.addAll(generateTestClasses(context));
    return clazzes;
  }

  public static List<GapicClass> generateResourceNameHelperClasses(GapicContext context) {
    return context.helperResourceNames().values().stream()
        .distinct()
        .filter(r -> !r.isOnlyWildcard())
        .map(r -> ResourceNameHelperClassComposer.instance().generate(r, context))
        .collect(Collectors.toList());
  }

  public static List<GapicClass> generateStubClasses(GapicContext context) {
    List<GapicClass> clazzes = new ArrayList<>();
    context
        .services()
        .forEach(
            s -> {
              if (context.transport() == Transport.REST) {
                clazzes.add(
                    com.google.api.generator.gapic.composer.rest.ServiceStubClassComposer.instance()
                        .generate(context, s));
                clazzes.add(
                    com.google.api.generator.gapic.composer.rest.ServiceStubSettingsClassComposer
                        .instance()
                        .generate(context, s));
                clazzes.add(
                    HttpJsonServiceCallableFactoryClassComposer.instance().generate(context, s));
                clazzes.add(HttpJsonServiceStubClassComposer.instance().generate(context, s));
              } else if (context.transport() == Transport.GRPC) {
                clazzes.add(ServiceStubClassComposer.instance().generate(context, s));
                clazzes.add(ServiceStubSettingsClassComposer.instance().generate(context, s));
                clazzes.add(
                    GrpcServiceCallableFactoryClassComposer.instance().generate(context, s));
                clazzes.add(GrpcServiceStubClassComposer.instance().generate(context, s));
              } else if (context.transport() == Transport.GRPC_REST) {
                clazzes.add(
                    com.google.api.generator.gapic.composer.grpcrest.ServiceStubClassComposer
                        .instance()
                        .generate(context, s));
                clazzes.add(
                    com.google.api.generator.gapic.composer.grpcrest
                        .ServiceStubSettingsClassComposer.instance()
                        .generate(context, s));
                clazzes.add(
                    GrpcServiceCallableFactoryClassComposer.instance().generate(context, s));
                clazzes.add(GrpcServiceStubClassComposer.instance().generate(context, s));
                clazzes.add(
                    HttpJsonServiceCallableFactoryClassComposer.instance().generate(context, s));
                clazzes.add(
                    com.google.api.generator.gapic.composer.grpcrest
                        .HttpJsonServiceStubClassComposer.instance()
                        .generate(context, s));
              }
            });
    return clazzes;
  }

  public static List<GapicClass> generateClientSettingsClasses(GapicContext context) {
    List<GapicClass> clazzes = new ArrayList<>();
    context
        .services()
        .forEach(
            s -> {
              if (context.transport() == Transport.REST) {
                clazzes.add(
                    com.google.api.generator.gapic.composer.rest.ServiceClientClassComposer
                        .instance()
                        .generate(context, s));
                clazzes.add(
                    com.google.api.generator.gapic.composer.rest.ServiceSettingsClassComposer
                        .instance()
                        .generate(context, s));
              } else if (context.transport() == Transport.GRPC) {
                clazzes.add(ServiceClientClassComposer.instance().generate(context, s));
                clazzes.add(ServiceSettingsClassComposer.instance().generate(context, s));
              } else if (context.transport() == Transport.GRPC_REST) {
                clazzes.add(
                    com.google.api.generator.gapic.composer.grpcrest.ServiceClientClassComposer
                        .instance()
                        .generate(context, s));
                clazzes.add(
                    com.google.api.generator.gapic.composer.grpcrest.ServiceSettingsClassComposer
                        .instance()
                        .generate(context, s));
              }
            });
    return clazzes;
  }

  public static List<GapicClass> generateMockClasses(GapicContext context, List<Service> services) {
    List<GapicClass> clazzes = new ArrayList<>();
    services.forEach(
        s -> {
          if (context.transport() == Transport.REST) {
            // REST transport tests do not use mock services.
          } else if (context.transport() == Transport.GRPC) {
            clazzes.add(MockServiceClassComposer.instance().generate(context, s));
            clazzes.add(MockServiceImplClassComposer.instance().generate(context, s));
          } else if (context.transport() == Transport.GRPC_REST) {
            clazzes.add(MockServiceClassComposer.instance().generate(context, s));
            clazzes.add(MockServiceImplClassComposer.instance().generate(context, s));
          }
        });
    return clazzes;
  }

  public static List<GapicClass> generateTestClasses(GapicContext context) {
    List<GapicClass> clazzes = new ArrayList<>();
    context
        .services()
        .forEach(
            s -> {
              if (context.transport() == Transport.REST) {
                clazzes.add(
                    com.google.api.generator.gapic.composer.rest.ServiceClientTestClassComposer
                        .instance()
                        .generate(context, s));
              } else if (context.transport() == Transport.GRPC) {
                clazzes.add(ServiceClientTestClassComposer.instance().generate(context, s));
              } else if (context.transport() == Transport.GRPC_REST) {
                clazzes.add(ServiceClientTestClassComposer.instance().generate(context, s));
                clazzes.add(HttpJsonServiceClientTestClassComposer.instance().generate(context, s));
              }
            });

    return clazzes;
  }

  @VisibleForTesting
  static List<GapicClass> prepareExecutableSamples(List<GapicClass> clazzes, String protoPackage) {
    //  parse protoPackage for apiVersion
    String[] pakkage = protoPackage.split("\\.");
    String apiVersion;
    //  e.g. v1, v2, v1beta1
    if (pakkage[pakkage.length - 1].matches("v[0-9].*")) {
      apiVersion = pakkage[pakkage.length - 1];
    } else {
      apiVersion = "";
    }
    // Include license header, apiShortName, and apiVersion
    List<GapicClass> clazzesWithSamples = new ArrayList<>();
    clazzes.forEach(
        gapicClass -> {
          List<Sample> samples = new ArrayList<>();
          gapicClass
              .samples()
              .forEach(
                  sample ->
                      samples.add(
                          addRegionTagAndHeaderToSample(
                              sample, parseDefaultHost(gapicClass.defaultHost()), apiVersion)));
          clazzesWithSamples.add(gapicClass.withSamples(samples));
        });
    return clazzesWithSamples;
  }

  // Parse defaultHost for apiShortName for the RegionTag. Need to account for regional default
  // endpoints like
  // "us-east1-pubsub.googleapis.com".
  @VisibleForTesting
  protected static String parseDefaultHost(String defaultHost) {
    // If the defaultHost is of the format "**.googleapis.com", take the name before the first
    // period.
    String apiShortName = Iterables.getFirst(Splitter.on(".").split(defaultHost), defaultHost);
    // If the defaultHost is of the format "**-**-**.googleapis.com", take the section before the
    // first period and after the last dash to follow CSharp's implementation here:
    // https://github.com/googleapis/gapic-generator-csharp/blob/main/Google.Api.Generator/Generation/ServiceDetails.cs#L70
    apiShortName = Iterables.getLast(Splitter.on("-").split(apiShortName), defaultHost);
    // `iam-meta-api` service is an exceptional case and is handled as a one-off
    if (defaultHost.contains("iam-meta-api")) {
      apiShortName = "iam";
    }
    return apiShortName;
  }

  @VisibleForTesting
  protected static Sample addRegionTagAndHeaderToSample(
      Sample sample, String apiShortName, String apiVersion) {
    final List<CommentStatement> header = Arrays.asList(CommentComposer.APACHE_LICENSE_COMMENT);
    return sample
        .withHeader(header)
        .withRegionTag(
            sample.regionTag().withApiVersion(apiVersion).withApiShortName(apiShortName));
  }

  @VisibleForTesting
  protected static List<GapicClass> addApacheLicense(List<GapicClass> gapicClassList) {
    return gapicClassList.stream()
        .map(
            gapicClass -> {
              ClassDefinition classWithHeader =
                  gapicClass
                      .classDefinition()
                      .toBuilder()
                      .setFileHeader(CommentComposer.APACHE_LICENSE_COMMENT)
                      .build();
              return GapicClass.create(gapicClass.kind(), classWithHeader, gapicClass.samples());
            })
        .collect(Collectors.toList());
  }

  private static GapicPackageInfo addApacheLicense(GapicPackageInfo gapicPackageInfo) {
    return GapicPackageInfo.with(
        gapicPackageInfo
            .packageInfo()
            .toBuilder()
            .setFileHeader(CommentComposer.APACHE_LICENSE_COMMENT)
            .build());
  }
}
