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

package com.google.api.generator.gapic.composer.httpjson;

import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.httpjson.ApiMessage;
import com.google.api.gax.httpjson.HttpJsonCallSettings;
import com.google.api.gax.httpjson.HttpJsonCallableFactory;
import com.google.api.gax.httpjson.HttpJsonStubCallableFactory;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.gapic.composer.common.AbstractServiceCallableFactoryClassComposer;
import com.google.api.generator.gapic.composer.comment.StubCommentComposer;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.api.generator.gapic.composer.utils.ClassNames;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpJsonServiceCallableFactoryClassComposer
    extends AbstractServiceCallableFactoryClassComposer {
  private static final HttpJsonServiceCallableFactoryClassComposer INSTANCE =
      new HttpJsonServiceCallableFactoryClassComposer();

  private HttpJsonServiceCallableFactoryClassComposer() {
    super(
        createTypes(),
        new StubCommentComposer("REST"),
        new ClassNames("HttpJson"),
        HttpJsonCallSettings.class,
        HttpJsonCallableFactory.class,
        BackgroundResource.class,
        "httpJsonCallSettings");
  }

  public static HttpJsonServiceCallableFactoryClassComposer instance() {
    return INSTANCE;
  }

  private static TypeStore createTypes() {
    TypeStore typeStore = createCommonTypes();
    typeStore.putAll(
        Arrays.asList(
            ApiMessage.class,
            BackgroundResource.class,
            HttpJsonCallSettings.class,
            HttpJsonCallableFactory.class,
            HttpJsonStubCallableFactory.class));
    return typeStore;
  }

  @Override
  protected List<TypeNode> createClassImplements(TypeStore typeStore) {
    return Arrays.asList(
        TypeNode.withReference(
            typeStore
                .get("HttpJsonStubCallableFactory")
                .reference()
                .copyAndSetGenerics(
                    Arrays.asList(
                        typeStore.get("ApiMessage").reference(),
                        typeStore.get("BackgroundResource").reference()))));
  }

  @Override
  protected MethodDefinition createOperationCallableMethod(TypeStore typeStore) {
    String methodVariantName = "Operation";
    String requestTemplateName = "RequestT";
    String responseTemplateName = "ResponseT";
    List<String> methodTemplateNames =
        Arrays.asList(requestTemplateName, responseTemplateName, "MetadataT");
    MethodDefinition method =
        createGenericCallableMethod(
            typeStore,
            /*methodTemplateNames=*/ methodTemplateNames,
            /*returnCallableKindName=*/ methodVariantName,
            /*returnCallableTemplateNames=*/ methodTemplateNames,
            /*methodVariantName=*/ methodVariantName,
            /*httpJsonCallSettingsTemplateObjects=*/ Arrays.asList(
                requestTemplateName, typeStore.get("ApiMessage")),
            /*callSettingsVariantName=*/ methodVariantName,
            /*callSettingsTemplateObjects=*/ methodTemplateNames.stream()
                .map(n -> (Object) n)
                .collect(Collectors.toList()));
    return method.toBuilder().setReturnExpr(ValueExpr.createNullExpr()).build();
  }
}
