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

import com.google.api.generator.engine.ast.Reference;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import com.google.testgapic.v1beta1.NestedMessageProto;
import org.junit.Test;

public class TypeParserTest {
  private static final String ECHO_PACKAGE = "com.google.showcase.v1beta1";
  // TODO(miraleung): Backfill with more tests (e.g. field, message, methods) for Parser.java.
  @Test
  public void parseMessageType_basic() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    ServiceDescriptor echoService = echoFileDescriptor.getServices().get(0);
    assertEquals("Echo", echoService.getName());

    MethodDescriptor echoMethodDescriptor = echoService.getMethods().get(0);
    Reference reference = TypeParser.parseMessageReference(echoMethodDescriptor.getInputType());
    assertEquals("com.google.showcase.v1beta1.EchoRequest", reference.fullName());
  }

  @Test
  public void parseMessageType_nested() {
    FileDescriptor fileDescriptor = NestedMessageProto.getDescriptor();
    Descriptor messageDescriptor = fileDescriptor.getMessageTypes().get(0);
    assertEquals("Outer", messageDescriptor.getName());
    messageDescriptor = messageDescriptor.getNestedTypes().get(0);
    assertEquals("Middle", messageDescriptor.getName());
    messageDescriptor = messageDescriptor.getNestedTypes().get(0);
    assertEquals("Inner", messageDescriptor.getName());

    Reference reference = TypeParser.parseMessageReference(messageDescriptor);
    assertEquals("com.google.testgapic.v1beta1.Outer.Middle.Inner", reference.fullName());
  }
}
