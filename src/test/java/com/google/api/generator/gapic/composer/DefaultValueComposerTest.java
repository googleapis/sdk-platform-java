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

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import com.google.testgapic.v1beta1.LockerProto;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class DefaultValueComposerTest {
  private JavaWriterVisitor writerVisitor;

  @Before
  public void setUp() {
    writerVisitor = new JavaWriterVisitor();
  }

  @Test
  public void defaultValue_mapField() {
    // Incorrect and will never happen in real usage, but proves that map detection is based on
    // isMap rather than type().
    Field field =
        Field.builder()
            .setName("foobar")
            .setType(TypeNode.STRING)
            .setIsMap(true)
            .setIsRepeated(true)
            .build();
    Expr expr = DefaultValueComposer.createDefaultValue(field);
    expr.accept(writerVisitor);
    assertEquals("new HashMap<>()", writerVisitor.write());

    writerVisitor.clear();

    // isMap() and isRepeated() will be set by protoc simulataneoulsy, but we check this edge case
    // for completeness.
    field = Field.builder().setName("foobar").setType(TypeNode.STRING).setIsMap(true).build();
    expr = DefaultValueComposer.createDefaultValue(field);
    expr.accept(writerVisitor);
    assertEquals("\"foobar-1268878963\"", writerVisitor.write());
  }

  @Test
  public void defaultValue_listField() {
    // Incorrect and will never happen in real usage, but proves that list detection is based on
    // isRepeated rather than type().
    Field field =
        Field.builder().setName("foobar").setType(TypeNode.STRING).setIsRepeated(true).build();
    Expr expr = DefaultValueComposer.createDefaultValue(field);
    expr.accept(writerVisitor);
    assertEquals("new ArrayList<>()", writerVisitor.write());
  }

  @Test
  public void defaultValue_enumField() {
    // Incorrect and will never happen in real usage, but proves that enum detection is based on
    // isEnum() rather than type().
    Field field =
        Field.builder().setName("foobar").setType(TypeNode.STRING).setIsEnum(true).build();
    Expr expr = DefaultValueComposer.createDefaultValue(field);
    expr.accept(writerVisitor);
    assertEquals("String.forNumber(0)", writerVisitor.write());
  }

  @Test
  public void defaultValue_messageField() {
    // Incorrect and will never happen in real usage, but proves that message detection is based on
    // isMessage() rather than type().
    Field field =
        Field.builder().setName("foobar").setType(TypeNode.STRING).setIsMessage(true).build();
    Expr expr = DefaultValueComposer.createDefaultValue(field);
    expr.accept(writerVisitor);
    assertEquals("String.newBuilder().build()", writerVisitor.write());
  }

  @Test
  public void defaultValue_stringField() {
    Field field = Field.builder().setName("foobar").setType(TypeNode.STRING).build();
    Expr expr = DefaultValueComposer.createDefaultValue(field);
    expr.accept(writerVisitor);
    assertEquals("\"foobar-1268878963\"", writerVisitor.write());
  }

  @Test
  public void defaultValue_numericField() {
    Field field = Field.builder().setName("foobar").setType(TypeNode.INT).build();
    Expr expr = DefaultValueComposer.createDefaultValue(field);
    expr.accept(writerVisitor);
    assertEquals("-1268878963", writerVisitor.write());

    writerVisitor.clear();
    field = Field.builder().setName("foobar").setType(TypeNode.DOUBLE).build();
    expr = DefaultValueComposer.createDefaultValue(field);
    expr.accept(writerVisitor);
    assertEquals("-1268878963", writerVisitor.write());
  }

  @Test
  public void defaultValue_booleanField() {
    Field field = Field.builder().setName("foobar").setType(TypeNode.BOOLEAN).build();
    Expr expr = DefaultValueComposer.createDefaultValue(field);
    expr.accept(writerVisitor);
    assertEquals("true", writerVisitor.write());
  }

  @Test
  public void defaultValue_resourceNameWithOnePattern() {
    FileDescriptor lockerServiceFileDescriptor = LockerProto.getDescriptor();
    Map<String, ResourceName> typeStringsToResourceNames =
        Parser.parseResourceNames(lockerServiceFileDescriptor);
    ResourceName resourceName =
        typeStringsToResourceNames.get("cloudbilling.googleapis.com/BillingAccount");
    Expr expr =
        DefaultValueComposer.createDefaultValue(
            resourceName,
            typeStringsToResourceNames.values().stream().collect(Collectors.toList()));
    expr.accept(writerVisitor);
    assertEquals("BillingAccountName.of(\"[BILLING_ACCOUNT]\")", writerVisitor.write());
  }

  @Test
  public void defaultValue_resourceNameWithMultiplePatterns() {
    FileDescriptor lockerServiceFileDescriptor = LockerProto.getDescriptor();
    Map<String, ResourceName> typeStringsToResourceNames =
        Parser.parseResourceNames(lockerServiceFileDescriptor);
    ResourceName resourceName =
        typeStringsToResourceNames.get("cloudresourcemanager.googleapis.com/Folder");
    Expr expr =
        DefaultValueComposer.createDefaultValue(
            resourceName,
            typeStringsToResourceNames.values().stream().collect(Collectors.toList()));
    expr.accept(writerVisitor);
    assertEquals(
        "FolderName.ofProjectFolderName(\"[PROJECT]\", \"[FOLDER]\")", writerVisitor.write());
  }

  @Test
  public void defaultValue_resourceNameWithWildcardPattern() {
    FileDescriptor lockerServiceFileDescriptor = LockerProto.getDescriptor();
    Map<String, ResourceName> typeStringsToResourceNames =
        Parser.parseResourceNames(lockerServiceFileDescriptor);
    ResourceName resourceName =
        typeStringsToResourceNames.get("cloudresourcemanager.googleapis.com/Anything");
    Expr expr =
        DefaultValueComposer.createDefaultValue(
            resourceName,
            typeStringsToResourceNames.values().stream().collect(Collectors.toList()));
    expr.accept(writerVisitor);
    assertEquals("DocumentName.ofDocumentName(\"[DOCUMENT]\")", writerVisitor.write());
  }

  @Test
  public void invalidDefaultValue_wildcardResourceNameWithOnlyDeletedTopic() {
    // Edge case that should never happen in practice.
    // Wildcard, but the resource names map has only other names that contain only the deleted-topic
    // constant.
    FileDescriptor lockerServiceFileDescriptor = LockerProto.getDescriptor();
    Map<String, ResourceName> typeStringsToResourceNames =
        Parser.parseResourceNames(lockerServiceFileDescriptor);
    ResourceName resourceName =
        typeStringsToResourceNames.get("cloudresourcemanager.googleapis.com/Anything");
    ResourceName topicResourceName = typeStringsToResourceNames.get("pubsub.googleapis.com/Topic");
    typeStringsToResourceNames.clear();
    typeStringsToResourceNames.put(topicResourceName.resourceTypeString(), topicResourceName);
    Expr expr =
        DefaultValueComposer.createDefaultValue(
            resourceName,
            typeStringsToResourceNames.values().stream().collect(Collectors.toList()));
    expr.accept(writerVisitor);
    assertEquals("TopicName.ofDeletedTopic()", writerVisitor.write());
  }

  @Test
  public void invalidDefaultValue_resourceNameWithOnlyWildcards() {
    // Edge case that should never happen in practice.
    // Wildcard, but the resource names map has only other names that contain only the deleted-topic
    // constant.
    FileDescriptor lockerServiceFileDescriptor = LockerProto.getDescriptor();
    Map<String, ResourceName> typeStringsToResourceNames =
        Parser.parseResourceNames(lockerServiceFileDescriptor);
    ResourceName resourceName =
        typeStringsToResourceNames.get("cloudresourcemanager.googleapis.com/Anything");
    assertThrows(
        IllegalStateException.class,
        () -> DefaultValueComposer.createDefaultValue(resourceName, Collections.emptyList()));
  }

  @Test
  public void createSimpleMessage_basicPrimitivesOnly() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> typeStringsToResourceNames =
        Parser.parseResourceNames(echoFileDescriptor);
    Message message = messageTypes.get("Foobar");
    Expr expr =
        DefaultValueComposer.createSimpleMessageBuilderExpr(
            message, typeStringsToResourceNames, messageTypes);
    expr.accept(writerVisitor);
    assertEquals(
        "Foobar.newBuilder().setName(FoobarName.ofProjectFoobarName(\"[PROJECT]\", \"[FOOBAR]\")"
            + ".toString()).setInfo(\"info3237038\").build()",
        writerVisitor.write());
  }

  @Test
  public void createSimpleMessage_containsMessagesEnumsAndResourceName() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> typeStringsToResourceNames =
        Parser.parseResourceNames(echoFileDescriptor);
    Message message = messageTypes.get("EchoRequest");
    Expr expr =
        DefaultValueComposer.createSimpleMessageBuilderExpr(
            message, typeStringsToResourceNames, messageTypes);
    expr.accept(writerVisitor);
    assertEquals(
        "EchoRequest.newBuilder().setName("
            + "FoobarName.ofProjectFoobarName(\"[PROJECT]\", \"[FOOBAR]\").toString())"
            + ".setParent(FoobarName.ofProjectFoobarName(\"[PROJECT]\", \"[FOOBAR]\").toString())"
            + ".setFoobar(Foobar.newBuilder().build()).build()",
        writerVisitor.write());
  }

  @Test
  public void createSimpleMessage_containsRepeatedField() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> typeStringsToResourceNames =
        Parser.parseResourceNames(echoFileDescriptor);
    Message message = messageTypes.get("PagedExpandResponse");
    Expr expr =
        DefaultValueComposer.createSimpleMessageBuilderExpr(
            message, typeStringsToResourceNames, messageTypes);
    expr.accept(writerVisitor);
    assertEquals(
        "PagedExpandResponse.newBuilder().addAllResponses(new"
            + " ArrayList<>()).setNextPageToken(\"next_page_token-1530815211\").build()",
        writerVisitor.write());
  }

  @Test
  public void createSimpleMessage_onlyOneofs() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> typeStringsToResourceNames =
        Parser.parseResourceNames(echoFileDescriptor);
    Message message = messageTypes.get("WaitRequest");
    Expr expr =
        DefaultValueComposer.createSimpleMessageBuilderExpr(
            message, typeStringsToResourceNames, messageTypes);
    expr.accept(writerVisitor);
    assertEquals("WaitRequest.newBuilder().build()", writerVisitor.write());
  }
}
