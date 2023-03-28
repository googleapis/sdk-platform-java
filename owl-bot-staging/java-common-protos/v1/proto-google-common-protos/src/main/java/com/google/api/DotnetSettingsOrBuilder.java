// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/client.proto

package com.google.api;

public interface DotnetSettingsOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.api.DotnetSettings)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   * @return Whether the common field is set.
   */
  boolean hasCommon();
  /**
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   * @return The common.
   */
  com.google.api.CommonLanguageSettings getCommon();
  /**
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   */
  com.google.api.CommonLanguageSettingsOrBuilder getCommonOrBuilder();

  /**
   * <pre>
   * Map from original service names to renamed versions.
   * This is used when the default generated types
   * would cause a naming conflict. (Neither name is
   * fully-qualified.)
   * Example: Subscriber to SubscriberServiceApi.
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_services = 2;</code>
   */
  int getRenamedServicesCount();
  /**
   * <pre>
   * Map from original service names to renamed versions.
   * This is used when the default generated types
   * would cause a naming conflict. (Neither name is
   * fully-qualified.)
   * Example: Subscriber to SubscriberServiceApi.
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_services = 2;</code>
   */
  boolean containsRenamedServices(
      java.lang.String key);
  /**
   * Use {@link #getRenamedServicesMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getRenamedServices();
  /**
   * <pre>
   * Map from original service names to renamed versions.
   * This is used when the default generated types
   * would cause a naming conflict. (Neither name is
   * fully-qualified.)
   * Example: Subscriber to SubscriberServiceApi.
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_services = 2;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getRenamedServicesMap();
  /**
   * <pre>
   * Map from original service names to renamed versions.
   * This is used when the default generated types
   * would cause a naming conflict. (Neither name is
   * fully-qualified.)
   * Example: Subscriber to SubscriberServiceApi.
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_services = 2;</code>
   */
  /* nullable */
java.lang.String getRenamedServicesOrDefault(
      java.lang.String key,
      /* nullable */
java.lang.String defaultValue);
  /**
   * <pre>
   * Map from original service names to renamed versions.
   * This is used when the default generated types
   * would cause a naming conflict. (Neither name is
   * fully-qualified.)
   * Example: Subscriber to SubscriberServiceApi.
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_services = 2;</code>
   */
  java.lang.String getRenamedServicesOrThrow(
      java.lang.String key);

  /**
   * <pre>
   * Map from full resource types to the effective short name
   * for the resource. This is used when otherwise resource
   * named from different services would cause naming collisions.
   * Example entry:
   * "datalabeling.googleapis.com/Dataset": "DataLabelingDataset"
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_resources = 3;</code>
   */
  int getRenamedResourcesCount();
  /**
   * <pre>
   * Map from full resource types to the effective short name
   * for the resource. This is used when otherwise resource
   * named from different services would cause naming collisions.
   * Example entry:
   * "datalabeling.googleapis.com/Dataset": "DataLabelingDataset"
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_resources = 3;</code>
   */
  boolean containsRenamedResources(
      java.lang.String key);
  /**
   * Use {@link #getRenamedResourcesMap()} instead.
   */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String>
  getRenamedResources();
  /**
   * <pre>
   * Map from full resource types to the effective short name
   * for the resource. This is used when otherwise resource
   * named from different services would cause naming collisions.
   * Example entry:
   * "datalabeling.googleapis.com/Dataset": "DataLabelingDataset"
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_resources = 3;</code>
   */
  java.util.Map<java.lang.String, java.lang.String>
  getRenamedResourcesMap();
  /**
   * <pre>
   * Map from full resource types to the effective short name
   * for the resource. This is used when otherwise resource
   * named from different services would cause naming collisions.
   * Example entry:
   * "datalabeling.googleapis.com/Dataset": "DataLabelingDataset"
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_resources = 3;</code>
   */
  /* nullable */
java.lang.String getRenamedResourcesOrDefault(
      java.lang.String key,
      /* nullable */
java.lang.String defaultValue);
  /**
   * <pre>
   * Map from full resource types to the effective short name
   * for the resource. This is used when otherwise resource
   * named from different services would cause naming collisions.
   * Example entry:
   * "datalabeling.googleapis.com/Dataset": "DataLabelingDataset"
   * </pre>
   *
   * <code>map&lt;string, string&gt; renamed_resources = 3;</code>
   */
  java.lang.String getRenamedResourcesOrThrow(
      java.lang.String key);

  /**
   * <pre>
   * List of full resource types to ignore during generation.
   * This is typically used for API-specific Location resources,
   * which should be handled by the generator as if they were actually
   * the common Location resources.
   * Example entry: "documentai.googleapis.com/Location"
   * </pre>
   *
   * <code>repeated string ignored_resources = 4;</code>
   * @return A list containing the ignoredResources.
   */
  java.util.List<java.lang.String>
      getIgnoredResourcesList();
  /**
   * <pre>
   * List of full resource types to ignore during generation.
   * This is typically used for API-specific Location resources,
   * which should be handled by the generator as if they were actually
   * the common Location resources.
   * Example entry: "documentai.googleapis.com/Location"
   * </pre>
   *
   * <code>repeated string ignored_resources = 4;</code>
   * @return The count of ignoredResources.
   */
  int getIgnoredResourcesCount();
  /**
   * <pre>
   * List of full resource types to ignore during generation.
   * This is typically used for API-specific Location resources,
   * which should be handled by the generator as if they were actually
   * the common Location resources.
   * Example entry: "documentai.googleapis.com/Location"
   * </pre>
   *
   * <code>repeated string ignored_resources = 4;</code>
   * @param index The index of the element to return.
   * @return The ignoredResources at the given index.
   */
  java.lang.String getIgnoredResources(int index);
  /**
   * <pre>
   * List of full resource types to ignore during generation.
   * This is typically used for API-specific Location resources,
   * which should be handled by the generator as if they were actually
   * the common Location resources.
   * Example entry: "documentai.googleapis.com/Location"
   * </pre>
   *
   * <code>repeated string ignored_resources = 4;</code>
   * @param index The index of the value to return.
   * @return The bytes of the ignoredResources at the given index.
   */
  com.google.protobuf.ByteString
      getIgnoredResourcesBytes(int index);

  /**
   * <pre>
   * Namespaces which must be aliased in snippets due to
   * a known (but non-generator-predictable) naming collision
   * </pre>
   *
   * <code>repeated string forced_namespace_aliases = 5;</code>
   * @return A list containing the forcedNamespaceAliases.
   */
  java.util.List<java.lang.String>
      getForcedNamespaceAliasesList();
  /**
   * <pre>
   * Namespaces which must be aliased in snippets due to
   * a known (but non-generator-predictable) naming collision
   * </pre>
   *
   * <code>repeated string forced_namespace_aliases = 5;</code>
   * @return The count of forcedNamespaceAliases.
   */
  int getForcedNamespaceAliasesCount();
  /**
   * <pre>
   * Namespaces which must be aliased in snippets due to
   * a known (but non-generator-predictable) naming collision
   * </pre>
   *
   * <code>repeated string forced_namespace_aliases = 5;</code>
   * @param index The index of the element to return.
   * @return The forcedNamespaceAliases at the given index.
   */
  java.lang.String getForcedNamespaceAliases(int index);
  /**
   * <pre>
   * Namespaces which must be aliased in snippets due to
   * a known (but non-generator-predictable) naming collision
   * </pre>
   *
   * <code>repeated string forced_namespace_aliases = 5;</code>
   * @param index The index of the value to return.
   * @return The bytes of the forcedNamespaceAliases at the given index.
   */
  com.google.protobuf.ByteString
      getForcedNamespaceAliasesBytes(int index);

  /**
   * <pre>
   * Method signatures (in the form "service.method(signature)")
   * which are provided separately, so shouldn't be generated.
   * Snippets *calling* these methods are still generated, however.
   * </pre>
   *
   * <code>repeated string handwritten_signatures = 6;</code>
   * @return A list containing the handwrittenSignatures.
   */
  java.util.List<java.lang.String>
      getHandwrittenSignaturesList();
  /**
   * <pre>
   * Method signatures (in the form "service.method(signature)")
   * which are provided separately, so shouldn't be generated.
   * Snippets *calling* these methods are still generated, however.
   * </pre>
   *
   * <code>repeated string handwritten_signatures = 6;</code>
   * @return The count of handwrittenSignatures.
   */
  int getHandwrittenSignaturesCount();
  /**
   * <pre>
   * Method signatures (in the form "service.method(signature)")
   * which are provided separately, so shouldn't be generated.
   * Snippets *calling* these methods are still generated, however.
   * </pre>
   *
   * <code>repeated string handwritten_signatures = 6;</code>
   * @param index The index of the element to return.
   * @return The handwrittenSignatures at the given index.
   */
  java.lang.String getHandwrittenSignatures(int index);
  /**
   * <pre>
   * Method signatures (in the form "service.method(signature)")
   * which are provided separately, so shouldn't be generated.
   * Snippets *calling* these methods are still generated, however.
   * </pre>
   *
   * <code>repeated string handwritten_signatures = 6;</code>
   * @param index The index of the value to return.
   * @return The bytes of the handwrittenSignatures at the given index.
   */
  com.google.protobuf.ByteString
      getHandwrittenSignaturesBytes(int index);
}
