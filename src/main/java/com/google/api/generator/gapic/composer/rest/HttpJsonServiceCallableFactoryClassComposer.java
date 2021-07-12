// Copyright 2021 Google LLC
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

package com.google.api.generator.gapic.composer.rest;

import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.httpjson.ApiMessage;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.gapic.composer.common.AbstractServiceCallableFactoryClassComposer;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.api.generator.gapic.model.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpJsonServiceCallableFactoryClassComposer
    extends AbstractServiceCallableFactoryClassComposer {
  private static final HttpJsonServiceCallableFactoryClassComposer INSTANCE =
      new HttpJsonServiceCallableFactoryClassComposer();

  private static final TypeNode MESSAGE_TYPE =
      TypeNode.withReference(ConcreteReference.withClazz(ApiMessage.class));
  private static final TypeNode BACKGROUND_RESOURCE_TYPE =
      TypeNode.withReference(ConcreteReference.withClazz(BackgroundResource.class));

  private HttpJsonServiceCallableFactoryClassComposer() {
    super(RestContext.instance());
  }

  public static HttpJsonServiceCallableFactoryClassComposer instance() {
    return INSTANCE;
  }

  @Override
  protected List<AnnotationNode> createClassAnnotations(Service service, TypeStore typeStore) {
    List<AnnotationNode> annotations = super.createClassAnnotations(service, typeStore);
    // Always add @BetaApi annotation to the generated CallableFactory for now. It is a public class
    // for technical reasons, end users are not expected to interact with it, but it may change
    // when we add LRO support, that is why making it @BetaApi for now.
    //
    // Remove the @BetaApi annotation once the LRO feature is fully implemented and stabilized.
    if (annotations.stream().noneMatch(a -> a.type().equals(typeStore.get("BetaApi")))) {
      annotations.add(AnnotationNode.withType(typeStore.get("BetaApi")));
    }
    return annotations;
  }

  @Override
  protected List<TypeNode> createClassImplements(TypeStore typeStore) {
    return Arrays.asList(
        TypeNode.withReference(
            getTransportContext()
                .stubCallableFactoryType()
                .reference()
                .copyAndSetGenerics(
                    Arrays.asList(
                        MESSAGE_TYPE.reference(), BACKGROUND_RESOURCE_TYPE.reference()))));
  }

  @Override
  protected MethodDefinition createOperationCallableMethod(TypeStore typeStore) {
    String methodVariantName = "Operation";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames =
        Arrays.asList(requestTemplateName, responseTemplateName, "MetadataT");

    // Always add @BetaApi annotation to the generated createOperationCallable() method for now,
    // until LRO is fully implemented.
    //
    // Remove the @BetaApi annotation once the LRO feature is fully implemented and stabilized.
    AnnotationNode betaAnnotation =
        AnnotationNode.withTypeAndDescription(
            typeStore.get("BetaApi"),
            "The surface for long-running operations is not stable yet and may change in the"
                + " future.");

    MethodDefinition method =
        createGenericCallableMethod(
            typeStore,
            /*methodTemplateNames=*/ methodTemplateNames,
            /*returnCallableKindName=*/ methodVariantName,
            /*returnCallableTemplateNames=*/ methodTemplateNames,
            /*methodVariantName=*/ methodVariantName,
            /*httpJsonCallSettingsTemplateObjects=*/ Arrays.asList(
                requestTemplateName, MESSAGE_TYPE),
            /*callSettingsVariantName=*/ methodVariantName,
            /*callSettingsTemplateObjects=*/ methodTemplateNames.stream()
                .map(n -> (Object) n)
                .collect(Collectors.toList()),
            Arrays.asList(betaAnnotation));
    return method.toBuilder().setReturnExpr(ValueExpr.createNullExpr()).build();
  }
}
