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

package com.google.api.generator.gapic.composer.defaultvalue;

import com.google.api.generator.engine.ast.AnonymousClassExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.lexicon.Keyword;
import com.google.api.generator.gapic.composer.resourcename.ResourceNameTokenizer;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.HttpBindings;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.api.generator.gapic.utils.ResourceNameConstants;
import com.google.api.generator.gapic.utils.ResourceReferenceUtils;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.longrunning.Operation;
import com.google.protobuf.Any;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultValueComposer {
  private static final TypeNode OPERATION_TYPE =
      TypeNode.withReference(ConcreteReference.withClazz(Operation.class));
  private static final TypeNode ANY_TYPE =
      TypeNode.withReference(ConcreteReference.withClazz(Any.class));

  private static final Pattern REPLACER_PATTERN = Pattern.compile("(\\w*)(\\w/|-\\d+/)\\*");

  public static Expr createMethodArgValue(
      MethodArgument methodArg,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes,
      Map<String, String> valuePatterns,
      HttpBindings bindings) {
    if (methodArg.isResourceNameHelper()) {
      Preconditions.checkState(
          methodArg.field().hasResourceReference(),
          String.format(
              "No corresponding resource reference for argument %s found on field %s %s",
              methodArg.name(), methodArg.field().type(), methodArg.field().name()));
      ResourceName resourceName =
          resourceNames.get(methodArg.field().resourceReference().resourceTypeString());
      Preconditions.checkNotNull(
          resourceName,
          String.format(
              "No resource name found for reference %s",
              methodArg.field().resourceReference().resourceTypeString()));
      Expr defValue =
          createResourceHelperValue(
              resourceName,
              methodArg.field().resourceReference().isChildType(),
              new ArrayList<>(resourceNames.values()),
              methodArg.field().name(),
              bindings);

      if (!methodArg.isResourceNameHelper() && methodArg.field().hasResourceReference()) {
        defValue =
            MethodInvocationExpr.builder()
                .setExprReferenceExpr(defValue)
                .setMethodName("toString")
                .setReturnType(TypeNode.STRING)
                .build();
      }
      return defValue;
    }

    if (methodArg.type().equals(methodArg.field().type())) {
      return createValue(
          methodArg.field(), false, resourceNames, messageTypes, valuePatterns, bindings);
    }

    return createValue(Field.builder().setName(methodArg.name()).setType(methodArg.type()).build());
  }

  public static Expr createValue(Field field) {
    return createValue(
        field, false, Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap(), null);
  }

  public static Expr createValue(
      Field field,
      boolean useExplicitInitTypeInGenerics,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes,
      Map<String, String> valuePatterns,
      HttpBindings bindings) {
    if (field.isRepeated()) {
      ConcreteReference.Builder refBuilder =
          ConcreteReference.builder().setClazz(field.isMap() ? HashMap.class : ArrayList.class);
      if (useExplicitInitTypeInGenerics) {
        if (field.isMap()) {
          refBuilder = refBuilder.setGenerics(field.type().reference().generics().subList(0, 2));
        } else {
          refBuilder = refBuilder.setGenerics(field.type().reference().generics().get(0));
        }
      }

      TypeNode newType = TypeNode.withReference(refBuilder.build());
      return NewObjectExpr.builder().setType(newType).setIsGeneric(true).build();
    }

    if (field.isEnum()) {
      return MethodInvocationExpr.builder()
          .setStaticReferenceType(field.type())
          .setMethodName("forNumber")
          .setArguments(
              ValueExpr.withValue(
                  PrimitiveValue.builder().setType(TypeNode.INT).setValue("0").build()))
          .setReturnType(field.type())
          .build();
    }

    if (field.isMessage()) {
      String nestedFieldName = field.name();
      Map<String, String> nestedValuePatterns = new HashMap<>();
      for (Map.Entry<String, String> entry : valuePatterns.entrySet()) {
        String lowerCamelNestedFieldName = JavaStyle.toLowerCamelCase(nestedFieldName);
        lowerCamelNestedFieldName = Keyword.unescapeKeyword(lowerCamelNestedFieldName);
        if (entry.getKey().startsWith(lowerCamelNestedFieldName + '.')) {
          nestedValuePatterns.put(
              entry.getKey().substring(lowerCamelNestedFieldName.length() + 1), entry.getValue());
        }
      }

      if (!nestedValuePatterns.isEmpty()) {
        Message nestedMessage = messageTypes.get(field.type().reference().fullName());
        if (nestedMessage != null) {
          return createSimpleMessageBuilderValue(
              nestedMessage, resourceNames, messageTypes, nestedValuePatterns, bindings);
        }
      }

      MethodInvocationExpr newBuilderExpr =
          MethodInvocationExpr.builder()
              .setStaticReferenceType(field.type())
              .setMethodName("newBuilder")
              .build();
      if (field.type().equals(TypeNode.VALUE)) {
        newBuilderExpr =
            MethodInvocationExpr.builder()
                .setExprReferenceExpr(newBuilderExpr)
                .setMethodName("setBoolValue")
                .setArguments(
                    ValueExpr.withValue(
                        PrimitiveValue.builder()
                            .setType(TypeNode.BOOLEAN)
                            .setValue("true")
                            .build()))
                .build();
      }

      return MethodInvocationExpr.builder()
          .setExprReferenceExpr(newBuilderExpr)
          .setMethodName("build")
          .setReturnType(field.type())
          .build();
    }

    if (field.type().equals(TypeNode.STRING)) {
      String javaFieldName = JavaStyle.toLowerCamelCase(field.name());
      return ValueExpr.withValue(
          StringObjectValue.withValue(
              constructValueMatchingPattern(javaFieldName, valuePatterns.get(javaFieldName))));
    }

    if (TypeNode.isNumericType(field.type())) {
      return ValueExpr.withValue(
          PrimitiveValue.builder()
              .setType(field.type())
              .setValue(String.format("%s", field.name().hashCode()))
              .build());
    }

    if (field.type().equals(TypeNode.BOOLEAN)) {
      return ValueExpr.withValue(
          PrimitiveValue.builder().setType(field.type()).setValue("true").build());
    }

    if (field.type().equals(TypeNode.BYTESTRING)) {
      return VariableExpr.builder()
          .setStaticReferenceType(TypeNode.BYTESTRING)
          .setVariable(Variable.builder().setName("EMPTY").setType(TypeNode.BYTESTRING).build())
          .build();
    }

    throw new UnsupportedOperationException(
        String.format(
            "Default value for field %s with type %s not implemented yet.",
            field.name(), field.type()));
  }

  public static Expr createResourceHelperValue(
      ResourceName resourceName,
      boolean isChildType,
      List<ResourceName> resnames,
      String fieldOrMessageName,
      HttpBindings bindings) {
    return createResourceHelperValue(
        resourceName, isChildType, resnames, fieldOrMessageName, true, bindings);
  }

  private static List<ResourceName> findParentResources(
      ResourceName childResource, List<ResourceName> resourceNames) {
    List<ResourceName> parentResources = new ArrayList<>();
    Map<String, ResourceName> patternToResourceName = new HashMap<>();

    for (ResourceName resourceName : resourceNames) {
      for (String parentPattern : resourceName.patterns()) {
        patternToResourceName.put(parentPattern, resourceName);
      }
    }

    for (String childPattern : childResource.patterns()) {
      Optional<String> parentPattern = ResourceReferenceUtils.parseParentPattern(childPattern);
      if (parentPattern.isPresent() && patternToResourceName.containsKey(parentPattern.get())) {
        parentResources.add(patternToResourceName.get(parentPattern.get()));
      }
    }

    return parentResources;
  }

  @VisibleForTesting
  static Expr createResourceHelperValue(
      ResourceName resourceName,
      boolean isChildType,
      List<ResourceName> resnames,
      String fieldOrMessageName,
      boolean allowAnonResourceNameClass,
      HttpBindings bindings) {
    if (isChildType) {
      List<ResourceName> parentResources = findParentResources(resourceName, resnames);
      if (!parentResources.isEmpty()) {
        resourceName = parentResources.get(0);
      }
    }

    if (resourceName.isOnlyWildcard()) {
      List<ResourceName> unexaminedResnames = new ArrayList<>(resnames);
      for (ResourceName resname : resnames) {
        unexaminedResnames.remove(resname);
        if (!resname.isOnlyWildcard()
            && (bindings == null || resname.getMatchingPattern(bindings) != null)) {
          return createResourceHelperValue(
              resname, false, unexaminedResnames, fieldOrMessageName, bindings);
        }
      }

      return allowAnonResourceNameClass
          ? createAnonymousResourceNameClassValue(fieldOrMessageName, bindings)
          : ValueExpr.withValue(
              StringObjectValue.withValue(
                  String.format("%s%s", fieldOrMessageName, fieldOrMessageName.hashCode())));
    }

    // The cost tradeoffs of new ctors versus distinct() don't really matter here, since this list
    // will usually have a very small number of elements.
    List<String> patterns = new ArrayList<>(new HashSet<>(resourceName.patterns()));
    boolean containsOnlyDeletedTopic =
        patterns.size() == 1 && patterns.get(0).equals(ResourceNameConstants.DELETED_TOPIC_LITERAL);
    String ofMethodName = "of";
    List<String> patternPlaceholderTokens = new ArrayList<>();

    if (containsOnlyDeletedTopic) {
      ofMethodName = "ofDeletedTopic";
    } else {
      String matchingPattern = null;
      if (bindings != null) {
        matchingPattern = resourceName.getMatchingPattern(bindings);
      }
      if (matchingPattern == null) {
        for (String pattern : resourceName.patterns()) {
          if (pattern.equals(ResourceNameConstants.WILDCARD_PATTERN)
              || pattern.equals(ResourceNameConstants.DELETED_TOPIC_LITERAL)) {
            continue;
          }
          matchingPattern = pattern;
          break;
        }
      }
      patternPlaceholderTokens.addAll(
          ResourceNameTokenizer.parseTokenHierarchy(Arrays.asList(matchingPattern)).get(0));
    }

    boolean hasOnePattern = resourceName.patterns().size() == 1;
    if (!hasOnePattern) {
      ofMethodName =
          String.format(
              "of%sName",
              String.join(
                  "",
                  patternPlaceholderTokens.stream()
                      .map(s -> JavaStyle.toUpperCamelCase(s))
                      .collect(Collectors.toList())));
    }

    TypeNode resourceNameJavaType = resourceName.type();
    List<Expr> argExprs =
        patternPlaceholderTokens.stream()
            .map(
                s ->
                    ValueExpr.withValue(
                        StringObjectValue.withValue(String.format("[%s]", s.toUpperCase()))))
            .collect(Collectors.toList());
    return MethodInvocationExpr.builder()
        .setStaticReferenceType(resourceNameJavaType)
        .setMethodName(ofMethodName)
        .setArguments(argExprs)
        .setReturnType(resourceNameJavaType)
        .build();
  }

  public static Expr createSimpleMessageBuilderValue(
      Message message,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes,
      HttpBindings bindings) {
    return createSimpleMessageBuilderValue(
        message, resourceNames, messageTypes, Collections.emptyMap(), bindings);
  }

  public static Expr createSimpleMessageBuilderValue(
      Message message,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes,
      Map<String, String> valuePatterns,
      HttpBindings bindings) {
    MethodInvocationExpr builderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(message.type())
            .setMethodName("newBuilder")
            .build();

    for (Field field : message.fields()) {
      if (field.isContainedInOneof() // Avoid colliding fields.
          || ((field.isMessage()
                  || (field.isEnum()
                      && message.operationResponse() == null)) // Avoid importing unparsed messages.
              && !field.isRepeated()
              && !messageTypes.containsKey(field.type().reference().fullName()))) {
        continue;
      }
      String setterMethodNamePattern = "set%s";
      if (field.isRepeated()) {
        setterMethodNamePattern = field.isMap() ? "putAll%s" : "addAll%s";
      }
      Expr defaultExpr = null;
      List<ResourceName> matchingResources =
          getMatchingResources(field, resourceNames, bindings, valuePatterns);
      if (!matchingResources.isEmpty()) {
        // isChildType is set to false as the matchingResource value already accounts
        // for the possible child resource (accounted for in `getMatchingResource()`).
        defaultExpr =
            createResourceHelperValue(
                matchingResources.get(0),
                false,
                new ArrayList<>(resourceNames.values()),
                message.name(),
                /* allowAnonResourceNameClass = */ false,
                bindings);
        defaultExpr =
            MethodInvocationExpr.builder()
                .setExprReferenceExpr(defaultExpr)
                .setMethodName("toString")
                .setReturnType(TypeNode.STRING)
                .build();
      } else {
        if (message.operationResponse() != null) {
          if (field.name().equals(message.operationResponse().statusFieldName())) {
            String statusTypeName = message.operationResponse().statusFieldTypeName();
            String statusClassName = statusTypeName.substring(statusTypeName.lastIndexOf('.') + 1);

            TypeNode statusType =
                TypeNode.withReference(
                    VaporReference.builder()
                        .setName(statusClassName)
                        .setPakkage(message.type().reference().fullName())
                        .setIsStaticImport(false)
                        .build());
            defaultExpr =
                VariableExpr.builder()
                    .setVariable(Variable.builder().setName("DONE").setType(statusType).build())
                    .setStaticReferenceType(statusType)
                    .build();

          } else if (field.name().equals(message.operationResponse().errorCodeFieldName())) {
            defaultExpr =
                ValueExpr.withValue(
                    PrimitiveValue.builder().setType(field.type()).setValue("0").build());
          }
        }

        if (defaultExpr == null) {
          defaultExpr =
              createValue(field, true, resourceNames, messageTypes, valuePatterns, bindings);
        }
      }
      builderExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(builderExpr)
              .setMethodName(
                  String.format(setterMethodNamePattern, JavaStyle.toUpperCamelCase(field.name())))
              .setArguments(defaultExpr)
              .build();
    }

    return MethodInvocationExpr.builder()
        .setExprReferenceExpr(builderExpr)
        .setMethodName("build")
        .setReturnType(message.type())
        .build();
  }

  /**
   * The logic inside here is similar to {@link #createResourceHelperValue(ResourceName, boolean,
   * List, String, boolean, HttpBindings)} . The reason this is duplicated and extracted out here is
   * so that the resource reference validity check (this method) can occur prior to the attempt to
   * try and generate the default value for the configured resource reference. This allows a
   * fallback option where if this check fails, then the generator can attempt to generate a default
   * value using {@link #createValue(Field, boolean, Map, Map, Map, HttpBindings)}. Additionally,
   * there are references to createResourceHelperValue being used elsewhere (client header samples,
   * sample code gen) which is a much larger effort to change.
   *
   * <p>Rationale for matching the Resource's pattern with HttpBindings: If the field is used in the
   * path, check that the field's resource reference matches the RPC's HttpBindings. There is an
   * edge case where an RPC's HttpBindings defined in the proto may not match a message. This is
   * possible for Mixin RPCs if service teams configures a custom HttpBindings in the service yaml
   * file. For example, the GetIamPolicyRequest's resource field has a resource reference to match
   * anything (`*`). This normally works because the GetIamPolicy RPC will take in any resource
   * (HttpBindings is defined with `{resource=**}`). But if the custom path is expected to take in a
   * resource with a more specific path (i.e. `{resource=service/*}), it won't work unless the
   * resource has a `service/*` pattern. This logic will try to match a resource that has the
   * pattern `service/*`.
   */
  static List<ResourceName> getMatchingResources(
      Field field,
      Map<String, ResourceName> resourceNames,
      HttpBindings bindings,
      Map<String, String> valuePatterns) {
    if (!field.hasResourceReference()) {
      return ImmutableList.of();
    }
    String resourceTypeString = field.resourceReference().resourceTypeString();
    if (!resourceNames.containsKey(resourceTypeString)) {
      return ImmutableList.of();
    }
    ResourceName resourceReference = resourceNames.get(resourceTypeString);

    // Resource reference (google.api.resource_reference) is a string type with a field name of
    // `parent` or `name`. It won't have anything nested in the bindings (i.e. `{name.field=*}`).
    // This check is needed as a message can contain fields with valid resource references that
    // aren't used in the path. Additionally, this flag only impacts HttpJson callables as gRPC
    // does not need to match the request the callable's URL. This method is used to generate
    // default values for gRPC callables as well but gRPC's valuePatterns map will be empty and
    // the logic will be skipped.
    boolean isFieldUsedInPath = bindings != null && valuePatterns.containsKey(field.name());

    // Holds the list of known resources (excluding the wildcard resource).
    List<ResourceName> possibleResources =
        resourceNames.values().stream()
            .filter(x -> !x.isOnlyWildcard())
            .collect(Collectors.toList());

    if (resourceReference.isOnlyWildcard()) {
      if (isFieldUsedInPath) {
        // Only use resources whose pattern match the HttpBindings
        return possibleResources.stream()
            .filter(x -> x.getMatchingPattern(bindings) != null)
            .collect(Collectors.toList());
      }
      // Can with any possible known resource
      return possibleResources;
    }

    // Narrow down the list of possibleResources
    if (field.resourceReference().isChildType()) {
      // Use the parent resource(s) if the field is a `child_type`. This is for certain RPCs like
      // `List` and `Create` which have a resource reference to the parent collection(s). A resource
      // may have multiple parents, so we must check the possibility for each parent resource.
      possibleResources =
          ImmutableList.copyOf(
              findParentResources(resourceReference, new ArrayList<>(resourceNames.values())));
    } else {
      possibleResources = ImmutableList.of(resourceReference);
    }

    if (isFieldUsedInPath) {
      return possibleResources.stream()
          .filter(x -> x.getMatchingPattern(bindings) != null)
          .collect(Collectors.toList());
    }
    return possibleResources;
  }

  public static Expr createSimpleOperationBuilderValue(String name, VariableExpr responseExpr) {
    Expr operationExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(OPERATION_TYPE)
            .setMethodName("newBuilder")
            .build();
    operationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(operationExpr)
            .setMethodName("setName")
            .setArguments(ValueExpr.withValue(StringObjectValue.withValue(name)))
            .build();
    operationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(operationExpr)
            .setMethodName("setDone")
            .setArguments(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setType(TypeNode.BOOLEAN).setValue("true").build()))
            .build();
    operationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(operationExpr)
            .setMethodName("setResponse")
            .setArguments(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(ANY_TYPE)
                    .setMethodName("pack")
                    .setArguments(responseExpr)
                    .build())
            .build();
    return MethodInvocationExpr.builder()
        .setExprReferenceExpr(operationExpr)
        .setMethodName("build")
        .setReturnType(OPERATION_TYPE)
        .build();
  }

  public static Expr createSimplePagedResponseValue(
      TypeNode responseType, String repeatedFieldName, Expr responseElementVarExpr, boolean isMap) {
    // Code for paginated maps:
    // AggregatedMessageList.newBuilder()
    //     .setNextPageToken("")
    //     .putAllItems(Collections.singletonMap("items", responsesElement))
    //     .build();
    //
    // Code for paginated arrays:
    // MessageList expectedResponse =
    //     AddressList.newBuilder()
    //         .setNextPageToken("")
    //         .addAllItems(Arrays.asList(responsesElement))
    //         .build();
    Expr pagedResponseExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(responseType)
            .setMethodName("newBuilder")
            .build();
    pagedResponseExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(pagedResponseExpr)
            .setMethodName("setNextPageToken")
            .setArguments(ValueExpr.withValue(StringObjectValue.withValue("")))
            .build();
    if (isMap) {
      pagedResponseExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(pagedResponseExpr)
              .setMethodName(
                  String.format("putAll%s", JavaStyle.toUpperCamelCase(repeatedFieldName)))
              .setArguments(
                  MethodInvocationExpr.builder()
                      .setStaticReferenceType(
                          TypeNode.withReference(ConcreteReference.withClazz(Collections.class)))
                      .setMethodName("singletonMap")
                      .setArguments(
                          ValueExpr.withValue(
                              StringObjectValue.withValue(
                                  JavaStyle.toLowerCamelCase(repeatedFieldName))),
                          responseElementVarExpr)
                      .build())
              .build();
    } else {
      pagedResponseExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(pagedResponseExpr)
              .setMethodName(
                  String.format("addAll%s", JavaStyle.toUpperCamelCase(repeatedFieldName)))
              .setArguments(
                  MethodInvocationExpr.builder()
                      .setStaticReferenceType(
                          TypeNode.withReference(ConcreteReference.withClazz(Arrays.class)))
                      .setMethodName("asList")
                      .setArguments(responseElementVarExpr)
                      .build())
              .build();
    }
    return MethodInvocationExpr.builder()
        .setExprReferenceExpr(pagedResponseExpr)
        .setMethodName("build")
        .setReturnType(responseType)
        .build();
  }

  @VisibleForTesting
  static AnonymousClassExpr createAnonymousResourceNameClassValue(
      String fieldOrMessageName, HttpBindings matchedBindings) {
    TypeNode stringMapType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(Map.class)
                .setGenerics(
                    Arrays.asList(
                        ConcreteReference.withClazz(String.class),
                        ConcreteReference.withClazz(String.class)))
                .build());

    // Method code:
    // @Override
    // public Map<String, String> getFieldValuesMap() {
    //   Map<String, String> fieldValuesMap = new HashMap<>();
    //   fieldValuesMap.put("resource", "resource-12345");
    //   return fieldValuesMap;
    // }

    String toStringValue = String.format("%s%s", fieldOrMessageName, fieldOrMessageName.hashCode());
    if (matchedBindings != null) {
      Map<String, String> valuePatterns = matchedBindings.getPathParametersValuePatterns();
      toStringValue =
          constructValueMatchingPattern(fieldOrMessageName, valuePatterns.get(fieldOrMessageName));
    }

    VariableExpr fieldValuesMapVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(stringMapType).setName("fieldValuesMap").build());
    StringObjectValue fieldOrMessageStringValue = StringObjectValue.withValue(toStringValue);

    List<Expr> bodyExprs =
        Arrays.asList(
            AssignmentExpr.builder()
                .setVariableExpr(fieldValuesMapVarExpr.toBuilder().setIsDecl(true).build())
                .setValueExpr(
                    NewObjectExpr.builder()
                        .setType(TypeNode.withReference(ConcreteReference.withClazz(HashMap.class)))
                        .setIsGeneric(true)
                        .build())
                .build(),
            MethodInvocationExpr.builder()
                .setExprReferenceExpr(fieldValuesMapVarExpr)
                .setMethodName("put")
                .setArguments(
                    ValueExpr.withValue(StringObjectValue.withValue(fieldOrMessageName)),
                    ValueExpr.withValue(fieldOrMessageStringValue))
                .build());

    MethodDefinition getFieldValuesMapMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(stringMapType)
            .setName("getFieldValuesMap")
            .setBody(
                bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()))
            .setReturnExpr(fieldValuesMapVarExpr)
            .build();

    // Method code:
    // @Override
    // public String getFieldValue(String fieldName) {
    //   return getFieldValuesMap().get(fieldName);
    // }
    VariableExpr fieldNameVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.STRING).setName("fieldName").build());
    MethodDefinition getFieldValueMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.STRING)
            .setName("getFieldValue")
            .setArguments(fieldNameVarExpr.toBuilder().setIsDecl(true).build())
            .setReturnExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(
                        MethodInvocationExpr.builder().setMethodName("getFieldValuesMap").build())
                    .setMethodName("get")
                    .setArguments(fieldNameVarExpr)
                    .setReturnType(TypeNode.STRING)
                    .build())
            .build();

    MethodDefinition toStringMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.STRING)
            .setName("toString")
            .setReturnExpr(ValueExpr.withValue(StringObjectValue.withValue(toStringValue)))
            .build();

    return AnonymousClassExpr.builder()
        .setType(
            TypeNode.withReference(
                ConcreteReference.withClazz(com.google.api.resourcenames.ResourceName.class)))
        .setMethods(Arrays.asList(getFieldValuesMapMethod, getFieldValueMethod, toStringMethod))
        .build();
  }

  public static String constructValueMatchingPattern(String fieldName, String pattern) {
    if (pattern == null || pattern.isEmpty()) {
      return fieldName + fieldName.hashCode();
    }

    final String suffix = "-" + (Math.abs((fieldName + pattern).hashCode()) % 10000);

    String value = pattern.replace("**", "*");

    String prevTempl = null;
    while (!value.equals(prevTempl)) {
      prevTempl = value;
      value = REPLACER_PATTERN.matcher(value).replaceFirst("$1$2$1" + suffix);
    }

    return value.replace("*", fieldName + suffix);
  }
}
