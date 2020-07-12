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
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ParserTest {
  private static final String ECHO_PACKAGE = "com.google.showcase.v1beta1";
  // TODO(miraleung): Backfill with more tests (e.g. field, message, methods) for Parser.java.
  private ServiceDescriptor echoService;
  private FileDescriptor echoFileDescriptor;

  @Before
  public void setUp() {
    echoFileDescriptor = EchoOuterClass.getDescriptor();
    echoService = echoFileDescriptor.getServices().get(0);
    assertEquals(echoService.getName(), "Echo");
  }

  @Test
  public void parseMessages_basic() {
    // TODO(miraleung): Add more tests for oneofs and other message-parsing edge cases.
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    String echoResponseName = "EchoResponse";
    Field echoResponseContentField =
        Field.builder().setName("content").setType(TypeNode.STRING).build();
    Field echoResponseSeverityField =
        Field.builder()
            .setName("severity")
            .setType(
                TypeNode.withReference(
                    VaporReference.builder().setName("Severity").setPakkage(ECHO_PACKAGE).build()))
            .build();
    TypeNode echoResponseType =
        TypeNode.withReference(
            VaporReference.builder().setName(echoResponseName).setPakkage(ECHO_PACKAGE).build());

    Message echoResponseMessage =
        Message.builder()
            .setType(echoResponseType)
            .setName(echoResponseName)
            .setFields(Arrays.asList(echoResponseContentField, echoResponseSeverityField))
            .build();
    assertThat(messageTypes.get(echoResponseName)).isEqualTo(echoResponseMessage);
  }

  @Test
  public void parseMethods_basic() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    List<Method> methods = Parser.parseMethods(echoService, messageTypes);
    assertThat(methods.size()).isEqualTo(7);

    // Methods should appear in the same order as in the protobuf file.
    Method echoMethod = methods.get(0);
    assertEquals(echoMethod.name(), "Echo");
    assertEquals(echoMethod.stream(), Method.Stream.NONE);

    Method expandMethod = methods.get(1);
    assertEquals(expandMethod.name(), "Expand");
    assertEquals(
        expandMethod.inputType(),
        TypeNode.withReference(
            VaporReference.builder().setName("ExpandRequest").setPakkage(ECHO_PACKAGE).build()));
    assertEquals(
        expandMethod.outputType(),
        TypeNode.withReference(
            VaporReference.builder().setName("EchoResponse").setPakkage(ECHO_PACKAGE).build()));
    assertEquals(expandMethod.stream(), Method.Stream.SERVER);

    Method collectMethod = methods.get(2);
    assertEquals(collectMethod.name(), "Collect");
    assertEquals(collectMethod.stream(), Method.Stream.CLIENT);

    Method chatMethod = methods.get(3);
    assertEquals(chatMethod.name(), "Chat");
    assertEquals(chatMethod.stream(), Method.Stream.BIDI);
  }

  @Test
  public void parseMethods_basicLro() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    List<Method> methods = Parser.parseMethods(echoService, messageTypes);
    assertThat(methods.size()).isEqualTo(7);

    // Methods should appear in the same order as in the protobuf file.
    Method waitMethod = methods.get(5);
    assertEquals(waitMethod.name(), "Wait");
    assertTrue(waitMethod.hasLro());
    TypeNode waitResponseType = messageTypes.get("WaitResponse").type();
    TypeNode waitMetadataType = messageTypes.get("WaitMetadata").type();
    assertThat(waitMethod.lro().responseType()).isEqualTo(waitResponseType);
    assertThat(waitMethod.lro().metadataType()).isEqualTo(waitMetadataType);
  }

  @Test
  public void parseMethods_lroMissingResponseType() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    MethodDescriptor waitMethodDescriptor = echoService.getMethods().get(5);
    messageTypes.remove("WaitResponse");
    assertThrows(
        NullPointerException.class, () -> Parser.parseLro(waitMethodDescriptor, messageTypes));
  }

  @Test
  public void parseMethods_lroMissingMetadataType() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    MethodDescriptor waitMethodDescriptor = echoService.getMethods().get(5);
    messageTypes.remove("WaitMetadata");
    assertThrows(
        NullPointerException.class, () -> Parser.parseLro(waitMethodDescriptor, messageTypes));
  }
}
