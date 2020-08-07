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

import static com.google.common.truth.Truth.assertThat;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.ResourceNameConstants;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.testgapic.v1beta1.BadMessageResnameDefProto;
import com.google.testgapic.v1beta1.LockerProto;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class ResourceNameParserTest {
  private static final String MAIN_PACKAGE = "com.google.testgapic.v1beta1";

  private ServiceDescriptor lockerService;
  private FileDescriptor lockerServiceFileDescriptor;

  @Before
  public void setUp() {
    lockerServiceFileDescriptor = LockerProto.getDescriptor();
    lockerService = lockerServiceFileDescriptor.getServices().get(0);
  }

  @Test
  public void parseResourceNames_basicOnePattern() {
    Map<String, ResourceName> typeStringsToResourceNames =
        ResourceNameParser.parseResourceNamesFromFile(lockerServiceFileDescriptor);
    assertEquals(3, typeStringsToResourceNames.size());

    ResourceName resourceName =
        typeStringsToResourceNames.get("cloudbilling.googleapis.com/BillingAccount");
    assertEquals(1, resourceName.patterns().size());
    assertEquals("billingAccounts/{billing_account}", resourceName.patterns().get(0));
    assertEquals("billing_account", resourceName.variableName());
    assertEquals("cloudbilling.googleapis.com/BillingAccount", resourceName.resourceTypeString());
    assertEquals(MAIN_PACKAGE, resourceName.pakkage());
    assertFalse(resourceName.hasParentMessageName());
    assertThat(resourceName.parentMessageName()).isNull();
  }

  @Test
  public void parseResourceNames_basicTwoPatterns() {
    Map<String, ResourceName> typeStringsToResourceNames =
        ResourceNameParser.parseResourceNamesFromFile(lockerServiceFileDescriptor);
    assertEquals(3, typeStringsToResourceNames.size());

    ResourceName resourceName =
        typeStringsToResourceNames.get("cloudresourcemanager.googleapis.com/Folder");
    assertEquals(2, resourceName.patterns().size());
    assertThat(resourceName.patterns()).contains("projects/{project}/folders/{folder}");
    assertThat(resourceName.patterns()).contains("folders/{folder}");
    assertEquals("folder", resourceName.variableName());
    assertEquals("cloudresourcemanager.googleapis.com/Folder", resourceName.resourceTypeString());
    assertEquals(MAIN_PACKAGE, resourceName.pakkage());
    assertFalse(resourceName.hasParentMessageName());
  }

  @Test
  public void parseResourceNames_deletedTopic() {
    Map<String, ResourceName> typeStringsToResourceNames =
        ResourceNameParser.parseResourceNamesFromFile(lockerServiceFileDescriptor);
    assertEquals(3, typeStringsToResourceNames.size());

    ResourceName resourceName = typeStringsToResourceNames.get("pubsub.googleapis.com/Topic");
    assertEquals(1, resourceName.patterns().size());
    assertEquals(ResourceNameConstants.DELETED_TOPIC_LITERAL, resourceName.patterns().get(0));
    assertEquals(ResourceNameConstants.DELETED_TOPIC_LITERAL, resourceName.variableName());
    assertEquals("pubsub.googleapis.com/Topic", resourceName.resourceTypeString());
    assertEquals(MAIN_PACKAGE, resourceName.pakkage());
    assertFalse(resourceName.hasParentMessageName());
  }

  @Test
  public void parseResourceNames_messageResourceDefinition() {
    String pakkage = TypeParser.getPackage(lockerServiceFileDescriptor);
    List<Descriptor> messageDescriptors = lockerServiceFileDescriptor.getMessageTypes();
    Map<String, ResourceName> typeStringsToResourceNames =
        ResourceNameParser.parseResourceNamesFromMessages(messageDescriptors, pakkage);
    assertEquals(1, typeStringsToResourceNames.size());

    ResourceName resourceName = typeStringsToResourceNames.get("testgapic.googleapis.com/Document");
    assertEquals(2, resourceName.patterns().size());
    assertThat(resourceName.patterns()).contains("folders/{folder}/documents/{document}");
    assertThat(resourceName.patterns()).contains("documents/{document}");
    assertEquals("document", resourceName.variableName());
    assertEquals("testgapic.googleapis.com/Document", resourceName.resourceTypeString());
    assertEquals(MAIN_PACKAGE, resourceName.pakkage());

    assertTrue(resourceName.hasParentMessageName());
    assertEquals("Document", resourceName.parentMessageName());
  }

  @Test
  public void parseResourceNames_messageWithoutResourceDefinition() {
    String pakkage = TypeParser.getPackage(lockerServiceFileDescriptor);
    List<Descriptor> messageDescriptors = lockerServiceFileDescriptor.getMessageTypes();
    Descriptor folderMessageDescriptor = messageDescriptors.get(1);
    assertEquals("Folder", folderMessageDescriptor.getName());
    Optional<ResourceName> resourceNameOpt =
        ResourceNameParser.parseResourceNameFromMessageType(folderMessageDescriptor, pakkage);
    assertFalse(resourceNameOpt.isPresent());
  }

  @Test
  public void parseResourceNames_badMessageResourceNameDefinitionMissingNameField() {
    FileDescriptor protoFileDescriptor = BadMessageResnameDefProto.getDescriptor();
    List<Descriptor> messageDescriptors = protoFileDescriptor.getMessageTypes();
    Descriptor messageDescriptor = messageDescriptors.get(0);
    String pakkage = TypeParser.getPackage(protoFileDescriptor);

    assertThrows(
        NullPointerException.class,
        () -> ResourceNameParser.parseResourceNameFromMessageType(messageDescriptor, pakkage));
  }

  @Test
  public void getVariableName_basicPattern() {
    Optional<String> nameOpt = ResourceNameParser.getVariableNameFromPattern("projects/{project}");
    assertTrue(nameOpt.isPresent());
    assertEquals("project", nameOpt.get());
  }

  @Test
  public void getVariableName_basicPatternLonger() {
    Optional<String> nameOpt =
        ResourceNameParser.getVariableNameFromPattern(
            "projects/{project}/billingAccounts/{billing_account}");
    assertTrue(nameOpt.isPresent());
    assertEquals("billing_account", nameOpt.get());
  }

  @Test
  public void getVariableName_differentCasedName() {
    Optional<String> nameOpt =
        ResourceNameParser.getVariableNameFromPattern(
            "projects/{project}/billingAccounts/{billingAccOunt}");
    assertTrue(nameOpt.isPresent());
    assertEquals("billingAccOunt", nameOpt.get());
  }

  @Test
  public void getVariableName_badEndingLiteral() {
    assertThrows(
        IllegalStateException.class,
        () -> ResourceNameParser.getVariableNameFromPattern("projects/{project}/badLiteral"));
  }

  @Test
  public void getVariableName_onlyLiterals() {
    assertThrows(
        IllegalStateException.class,
        () -> ResourceNameParser.getVariableNameFromPattern("projects/project/locations/location"));
  }

  @Test
  public void getVariableName_deletedTopic() {
    Optional<String> nameOpt =
        ResourceNameParser.getVariableNameFromPattern(ResourceNameConstants.DELETED_TOPIC_LITERAL);
    assertTrue(nameOpt.isPresent());
    assertEquals(ResourceNameConstants.DELETED_TOPIC_LITERAL, nameOpt.get());
  }

  @Test
  public void getVariableName_wildcard() {
    Optional<String> nameOpt =
        ResourceNameParser.getVariableNameFromPattern(ResourceNameConstants.WILDCARD_PATTERN);
    assertFalse(nameOpt.isPresent());
  }
}
