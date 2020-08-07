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

package com.google.api.generator.gapic.protoparser;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.ResourceNameConstants;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.testgapic.v1beta1.LockerProto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class ResourceReferenceParserTest {
  private static final String MAIN_PACKAGE = "com.google.testgapic.v1beta1";

  private ServiceDescriptor lockerService;
  private FileDescriptor lockerServiceFileDescriptor;

  @Before
  public void setUp() {
    lockerServiceFileDescriptor = LockerProto.getDescriptor();
    lockerService = lockerServiceFileDescriptor.getServices().get(0);
  }

  @Test
  public void parseParentResourceName_createFromPattern() {
    String resourceNamePackage = String.format("%s.common", MAIN_PACKAGE);
    String domainName = "cloudbilling.googleapis.com";
    String resourceTypeString = String.format("%s/BillingAccount", domainName);
    String parentResourceTypeString = String.format("%s/Project", domainName);
    Map<String, ResourceName> patternsToResourceNames = new HashMap<>();

    String parentPattern = "projects/{project}";
    Optional<ResourceName> parentResourceNameOpt =
        ResourceReferenceParser.parseParentResourceName(
            String.format("%s/billingAccounts/{billing_account}", parentPattern),
            MAIN_PACKAGE,
            resourceNamePackage,
            resourceTypeString,
            patternsToResourceNames);
    assertTrue(parentResourceNameOpt.isPresent());

    ResourceName parentResourceName = parentResourceNameOpt.get();
    assertEquals("project", parentResourceName.variableName());
    assertEquals(Arrays.asList(parentPattern), parentResourceName.patterns());
    assertEquals(parentResourceTypeString, parentResourceName.resourceTypeString());
    assertEquals(resourceNamePackage, parentResourceName.pakkage());
    assertEquals(
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ProjectName")
                .setPakkage(resourceNamePackage)
                .build()),
        parentResourceName.type());
    assertEquals(patternsToResourceNames.get(parentPattern), parentResourceName);
  }

  @Test
  public void parseParentResourceName_parentResourceNameExists() {
    Map<String, ResourceName> typeStringsToResourceNames =
        ResourceNameParser.parseResourceNamesFromFile(lockerServiceFileDescriptor);

    Map<String, ResourceName> patternsToResourceNames = new HashMap<>();
    for (ResourceName resourceName : typeStringsToResourceNames.values()) {
      for (String pattern : resourceName.patterns()) {
        patternsToResourceNames.put(pattern, resourceName);
      }
    }

    Optional<ResourceName> parentResourceNameOpt =
        ResourceReferenceParser.parseParentResourceName(
            "projects/{project}/folders/{folder}/documents/{document}",
            MAIN_PACKAGE,
            MAIN_PACKAGE,
            "cloudresourcemanager.googleapis.com/Document",
            patternsToResourceNames);

    assertTrue(parentResourceNameOpt.isPresent());
    assertEquals(
        typeStringsToResourceNames.get("cloudresourcemanager.googleapis.com/Folder"),
        parentResourceNameOpt.get());
  }

  @Test
  public void parseParentResourceName_badPattern() {
    Optional<ResourceName> parentResourceNameOpt =
        ResourceReferenceParser.parseParentResourceName(
            "projects/{project}/billingAccounts",
            MAIN_PACKAGE,
            MAIN_PACKAGE,
            "cloudbilling.googleapis.com/Feature",
            new HashMap<String, ResourceName>());
    assertFalse(parentResourceNameOpt.isPresent());
  }

  @Test
  public void parseParentPattern_basic() {
    String parentPattern = "projects/{project}";
    String pattern = String.format("%s/folders/{folder}", parentPattern);
    assertEquals(parentPattern, ResourceReferenceParser.parseParentPattern(pattern).get());
  }

  @Test
  public void parseParentPattern_wildcard() {
    Optional<String> parentPatternOpt =
        ResourceReferenceParser.parseParentPattern(ResourceNameConstants.WILDCARD_PATTERN);
    assertFalse(parentPatternOpt.isPresent());
  }

  @Test
  public void parseParentPattern_deletedTopicLiteral() {
    Optional<String> parentPatternOpt =
        ResourceReferenceParser.parseParentPattern(ResourceNameConstants.DELETED_TOPIC_LITERAL);
    assertFalse(parentPatternOpt.isPresent());
  }

  @Test
  public void parseParentPattern_noParents() {
    Optional<String> parentPatternOpt =
        ResourceReferenceParser.parseParentPattern("projects/{project}");
    assertFalse(parentPatternOpt.isPresent());
  }

  @Test
  public void parseParentPattern_insufficientPathComponents() {
    Optional<String> parentPatternOpt =
        ResourceReferenceParser.parseParentPattern("projects/foobars/{foobar}");
    assertFalse(parentPatternOpt.isPresent());
  }

  @Test
  public void parseParentPattern_lastComponentIsNotAVariable() {
    Optional<String> parentPatternOpt =
        ResourceReferenceParser.parseParentPattern("projects/{project}/foobars");
    assertFalse(parentPatternOpt.isPresent());
  }

  @Test
  public void resolvePackages_resourcePackageIsSubpackageOfService() {
    String resourcePackage = "com.google.testgapic.v1beta1.common";
    assertEquals(
        resourcePackage, ResourceReferenceParser.resolvePackages(resourcePackage, MAIN_PACKAGE));
  }

  @Test
  public void resolvePackages_resourcePackageIsSameAsService() {
    String resourcePackage = "com.google.testgapic.v1beta1.common";
    assertEquals(MAIN_PACKAGE, ResourceReferenceParser.resolvePackages(MAIN_PACKAGE, MAIN_PACKAGE));
  }

  @Test
  public void resolvePackages_resourcePackageIsNotSubpackageOfService() {
    assertEquals(
        MAIN_PACKAGE, ResourceReferenceParser.resolvePackages("com.google.cloud", MAIN_PACKAGE));
  }
}
