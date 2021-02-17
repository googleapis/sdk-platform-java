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
import com.google.api.generator.gapic.composer.resourcename.ResourceNameHelperClassComposer;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicPackageInfo;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.common.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

public class Composer {
  public static List<GapicClass> composeServiceClasses(GapicContext context) {
    List<GapicClass> clazzes = new ArrayList<>();
    Map<String, ResourceName> availableResourceNames = new HashMap<>();
    for (ResourceName resourceName : context.helperResourceNames()) {
      availableResourceNames.put(resourceName.resourceTypeString(), resourceName);
    }
    for (Service service : context.services()) {
      clazzes.addAll(generateServiceClasses(service, context, availableResourceNames));
    }
    for (Service mixinService : context.mixinServices()) {
      clazzes.addAll(generateMockClasses(mixinService, availableResourceNames, context.messages()));
    }
    clazzes.addAll(generateResourceNameHelperClasses(context.helperResourceNames()));
    return addApacheLicense(clazzes);
  }

  public static GapicPackageInfo composePackageInfo(GapicContext context) {
    return addApacheLicense(ClientLibraryPackageInfoComposer.generatePackageInfo(context));
  }

  public static List<GapicClass> generateServiceClasses(
      @Nonnull Service service,
      GapicContext context,
      @Nonnull Map<String, ResourceName> resourceNames) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.addAll(
        generateStubClasses(service, context.serviceConfig(), context.messages(), resourceNames));
    clazzes.addAll(generateClientSettingsClasses(service, context.messages(), resourceNames));
    clazzes.addAll(generateMockClasses(service, resourceNames, context.messages()));
    clazzes.addAll(generateTestClasses(service, context, resourceNames));
    // TODO(miraleung): Generate test classes.
    return clazzes;
  }

  public static List<GapicClass> generateResourceNameHelperClasses(
      Set<ResourceName> resourceNames) {
    return resourceNames.stream()
        .filter(r -> !r.isOnlyWildcard())
        .map(r -> ResourceNameHelperClassComposer.instance().generate(r))
        .collect(Collectors.toList());
  }

  public static List<GapicClass> generateStubClasses(
      Service service,
      GapicServiceConfig serviceConfig,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.add(ServiceStubClassComposer.instance().generate(service, messageTypes));
    clazzes.add(
        ServiceStubSettingsClassComposer.instance().generate(service, serviceConfig, messageTypes));
    clazzes.add(GrpcServiceCallableFactoryClassComposer.instance().generate(service, messageTypes));
    clazzes.add(GrpcServiceStubClassComposer.instance().generate(service, messageTypes));
    return clazzes;
  }

  public static List<GapicClass> generateClientSettingsClasses(
      Service service, GapicContext context, Map<String, ResourceName> resourceNames) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.add(ServiceClientClassComposer.instance().generate(service, context, resourceNames));
    clazzes.add(ServiceSettingsClassComposer.instance().generate(service, context.messages()));
    return clazzes;
  }

  public static List<GapicClass> generateMockClasses(
      Service service, Map<String, ResourceName> resourceNames, Map<String, Message> messageTypes) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.add(MockServiceClassComposer.instance().generate(service, messageTypes));
    clazzes.add(MockServiceImplClassComposer.instance().generate(service, messageTypes));
    return clazzes;
  }

  public static List<GapicClass> generateTestClasses(
      Service service, GapicContext context, Map<String, ResourceName> resourceNames) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.add(
        ServiceClientTestClassComposer.instance().generate(service, context, resourceNames));
    return clazzes;
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
