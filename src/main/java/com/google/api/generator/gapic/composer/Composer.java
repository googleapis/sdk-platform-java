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
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.gapic.composer.comment.CommentComposer;
import com.google.api.generator.gapic.composer.common.ServiceClientClassComposer;
import com.google.api.generator.gapic.composer.common.ServiceStubClassComposer;
import com.google.api.generator.gapic.composer.grpc.GrpcServiceCallableFactoryClassComposer;
import com.google.api.generator.gapic.composer.grpc.GrpcServiceStubClassComposer;
import com.google.api.generator.gapic.composer.grpc.MockServiceClassComposer;
import com.google.api.generator.gapic.composer.grpc.MockServiceImplClassComposer;
import com.google.api.generator.gapic.composer.grpc.ServiceClientTestClassComposer;
import com.google.api.generator.gapic.composer.grpc.ServiceSettingsClassComposer;
import com.google.api.generator.gapic.composer.grpc.ServiceStubSettingsClassComposer;
import com.google.api.generator.gapic.composer.resourcename.ResourceNameHelperClassComposer;
import com.google.api.generator.gapic.composer.rest.HttpJsonServiceCallableFactoryClassComposer;
import com.google.api.generator.gapic.composer.rest.HttpJsonServiceStubClassComposer;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicPackageInfo;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.model.Transport;
import com.google.common.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Composer {
  public static List<GapicClass> composeServiceClasses(GapicContext context) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.addAll(generateServiceClasses(context));
    clazzes.addAll(generateMockClasses(context, context.mixinServices()));
    clazzes.addAll(generateResourceNameHelperClasses(context));
    return addApacheLicense(clazzes);
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
              clazzes.add(ServiceStubClassComposer.instance().generate(context, s));
              if (context.transport() == Transport.REST) {
                clazzes.add(
                    com.google.api.generator.gapic.composer.rest.ServiceStubSettingsClassComposer
                        .instance()
                        .generate(context, s));
                clazzes.add(
                    HttpJsonServiceCallableFactoryClassComposer.instance().generate(context, s));
                clazzes.add(HttpJsonServiceStubClassComposer.instance().generate(context, s));
              } else {
                clazzes.add(ServiceStubSettingsClassComposer.instance().generate(context, s));
                clazzes.add(
                    GrpcServiceCallableFactoryClassComposer.instance().generate(context, s));
                clazzes.add(GrpcServiceStubClassComposer.instance().generate(context, s));
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
              clazzes.add(ServiceClientClassComposer.instance().generate(context, s));
              if (context.transport() == Transport.REST) {
                clazzes.add(
                    com.google.api.generator.gapic.composer.rest.ServiceSettingsClassComposer
                        .instance()
                        .generate(context, s));
              } else {
                clazzes.add(ServiceSettingsClassComposer.instance().generate(context, s));
              }
            });
    return clazzes;
  }

  public static List<GapicClass> generateMockClasses(GapicContext context, List<Service> services) {
    List<GapicClass> clazzes = new ArrayList<>();
    services.forEach(
        s -> {
          if (context.transport() == Transport.REST) {
            // REST transport tests donot not use mock services.
          } else {
            clazzes.add(MockServiceClassComposer.instance().generate(context, s));
            clazzes.add(MockServiceImplClassComposer.instance().generate(context, s));
          }
        });
    return clazzes;
  }

  public static List<GapicClass> generateTestClasses(GapicContext context) {
    return context.services().stream()
        .map(
            s -> {
              if (context.transport() == Transport.REST) {
                return com.google.api.generator.gapic.composer.rest.ServiceClientTestClassComposer
                    .instance()
                    .generate(context, s);
              } else {
                return ServiceClientTestClassComposer.instance().generate(context, s);
              }
            })
        .collect(Collectors.toList());
  }

  /** ====================== HELPERS ==================== */
  // TODO(miraleung): Add method list.
  private static GapicClass generateGenericClass(Kind kind, String name, Service service) {
    String pakkage = service.pakkage();
    if (kind.equals(Kind.STUB)) {
      pakkage += ".stub";
    }

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setName(name)
            .setScope(ScopeNode.PUBLIC)
            .build();
    return GapicClass.create(kind, classDef);
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
              return GapicClass.create(gapicClass.kind(), classWithHeader);
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
