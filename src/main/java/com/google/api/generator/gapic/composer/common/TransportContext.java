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

package com.google.api.generator.gapic.composer.common;

import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.composer.utils.ClassNames;
import com.google.api.generator.gapic.model.Transport;
import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

@AutoValue
public abstract class TransportContext {

  public abstract ClassNames classNames();

  // For AbstractServiceStubClassComposer
  public abstract Transport transport();

  public abstract String transportName();

  public abstract Class<?> callSettingsClass();

  public abstract TypeNode stubCallableFactoryType();

  public abstract Class<?> methodDescriptorClass();

  @Nullable
  public abstract TypeNode transportOperationsStubType();

  // For AbstractServiceSettingsClassComposer
  public abstract Class<?> instantiatingChannelProviderClass();

  public abstract String defaultTransportProviderBuilderName();

  // For AbstractServiceStubSettingsClassComposer
  public abstract TypeNode transportChannelType();

  public abstract String transportGetterName();

  // For AbstractServiceCallableFactoryClassComposer
  public abstract TypeNode transportCallSettingsType();

  public abstract TypeNode transportCallableFactoryType();

  public abstract TypeNode operationsStubType();

  public abstract String transportCallSettingsName();

  // For RetrySettingsComposer
  public abstract TypeNode operationResponseTransformerType();

  public abstract TypeNode operationMetadataTransformerType();

  public abstract TypeNode operationsClientType();

  protected static TypeNode classToType(Class<?> clazz) {
    return TypeNode.withReference(ConcreteReference.withClazz(clazz));
  }

  public static TransportContext.Builder builder() {
    return new AutoValue_TransportContext.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setClassNames(ClassNames value);

    public abstract Builder setTransport(Transport transport);

    public abstract Builder setTransportName(String value);

    public abstract Builder setCallSettingsClass(Class<?> callSettingsClass);

    public abstract Builder setStubCallableFactoryType(TypeNode stubCallableFactoryType);

    public abstract Builder setMethodDescriptorClass(Class<?> methodDescriptorClass);

    public abstract Builder setInstantiatingChannelProviderClass(
        Class<?> instantiatingChannelProviderClass);

    public abstract Builder setDefaultTransportProviderBuilderName(
        String defaultTransportProviderBuilderName);

    public abstract Builder setTransportChannelType(TypeNode transportChannelType);

    public abstract Builder setTransportGetterName(String transportGetterName);

    public abstract Builder setTransportCallSettingsType(TypeNode transportCallSettingsType);

    public abstract Builder setTransportCallableFactoryType(TypeNode transportCallableFactoryType);

    public abstract Builder setTransportCallSettingsName(String transportCallSettingsName);

    public abstract Builder setTransportOperationsStubType(TypeNode transportOperationsStubType);

    public abstract Builder setOperationsStubType(TypeNode operationsStubType);

    public abstract Builder setOperationResponseTransformerType(TypeNode operationResponseTransformerType);

    public abstract Builder setOperationMetadataTransformerType(TypeNode operationMetadataTransformerType);

    public abstract Builder setOperationsClientType(TypeNode operationsClientType);

    public abstract TransportContext build();
  }
}
