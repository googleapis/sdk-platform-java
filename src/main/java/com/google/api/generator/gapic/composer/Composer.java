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
import com.google.api.generator.gapic.model.Service;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class Composer {
  public static List<GapicClass> composeServiceClasses(@Nonnull List<Service> services) {
    List<GapicClass> clazzes = new ArrayList<>();
    for (Service service : services) {
      clazzes.addAll(generateServiceClasses(service));
    }
    return clazzes;
  }

  public static List<GapicClass> generateServiceClasses(@Nonnull Service service) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.addAll(generateStubClasses(service));
    clazzes.addAll(generateClientSettingsClasses(service));
    // TODO(miraleung): Generate test classes.
    return clazzes;
  }

  public static List<GapicClass> generateStubClasses(Service service) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.add(generateStubService(service));
    clazzes.add(generateStubServiceSettings(service));
    clazzes.add(generateStubGrpcServiceCallableFactory(service));
    clazzes.add(generateStubGrpcServiceStub(service));
    return clazzes;
  }

  public static List<GapicClass> generateClientSettingsClasses(Service service) {
    List<GapicClass> clazzes = new ArrayList<>();
    clazzes.add(generateServiceClient(service));
    clazzes.add(generateServiceSettings(service));
    return clazzes;
  }

  /** ====================== STUB CLASSES ==================== */
  private static GapicClass generateStubService(Service service) {
    return generateGenericClass(Kind.STUB, String.format("%sStub", service.name()), service);
  }

  private static GapicClass generateStubServiceSettings(Service service) {
    return generateGenericClass(
        Kind.STUB, String.format("%sStubSettings", service.name()), service);
  }

  private static GapicClass generateStubGrpcServiceCallableFactory(Service service) {
    return generateGenericClass(
        Kind.STUB, String.format("Grpc%sCallableFactory", service.name()), service);
  }

  private static GapicClass generateStubGrpcServiceStub(Service service) {
    return generateGenericClass(Kind.STUB, String.format("Grpc%sStub", service.name()), service);
  }

  /** ====================== MAIN CLASSES ==================== */
  private static GapicClass generateServiceClient(Service service) {
    return ServiceClientClassComposer.instance().generate(service);
  }

  private static GapicClass generateServiceSettings(Service service) {
    return generateGenericClass(Kind.MAIN, String.format("%sSettings", service.name()), service);
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
