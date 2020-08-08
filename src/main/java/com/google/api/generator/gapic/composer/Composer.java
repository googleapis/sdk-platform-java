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
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

public class Composer {
  public static List<GapicClass> composeServiceClasses(GapicContext context) {
    List<GapicClass> clazzes = new ArrayList<>();
    for (Service service : context.services()) {
      clazzes.addAll(generateServiceClasses(service, context.messages()));
    }
    clazzes.addAll(generateResourceNameHelperClasses(context.helperResourceNames()));
    return clazzes;
  }

  public static List<GapicClass> generateServiceClasses(
      @Nonnull Service service, @Nonnull Map<String, Message> messageTypes) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.addAll(generateStubClasses(service, messageTypes));
    clazzes.addAll(generateClientSettingsClasses(service, messageTypes));
    clazzes.addAll(generateMocksAndTestClasses(service, messageTypes));
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
      Service service, Map<String, Message> messageTypes) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.add(ServiceStubClassComposer.instance().generate(service, messageTypes));
    clazzes.add(generateStubServiceSettings(service));
    clazzes.add(GrpcServiceCallableFactoryClassComposer.instance().generate(service, messageTypes));
    clazzes.add(GrpcServiceStubClassComposer.instance().generate(service, messageTypes));
    return clazzes;
  }

  public static List<GapicClass> generateClientSettingsClasses(
      Service service, Map<String, Message> messageTypes) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.add(ServiceClientClassComposer.instance().generate(service, messageTypes));
    clazzes.add(ServiceSettingsClassComposer.instance().generate(service, messageTypes));
    return clazzes;
  }

  public static List<GapicClass> generateMocksAndTestClasses(
      Service service, Map<String, Message> messageTypes) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.add(MockServiceClassComposer.instance().generate(service, messageTypes));
    clazzes.add(MockServiceImplClassComposer.instance().generate(service, messageTypes));
    clazzes.add(ServiceClientTestClassComposer.instance().generate(service, messageTypes));
    return clazzes;
  }

  /** ====================== STUB CLASSES ==================== */
  private static GapicClass generateStubServiceSettings(Service service) {
    return generateGenericClass(
        Kind.STUB, String.format("%sStubSettings", service.name()), service);
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
}
