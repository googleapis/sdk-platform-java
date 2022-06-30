// Copyright 2022 Google LLC
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

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.model.Transport;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpringComposerTest {
  private GapicContext context;

  private String EXPECTED_CODE =
      "/*\n"
          + " * Copyright 2022 Google LLC\n"
          + " *\n"
          + " * Licensed under the Apache License, Version 2.0 (the \"License\");\n"
          + " * you may not use this file except in compliance with the License.\n"
          + " * You may obtain a copy of the License at\n"
          + " *\n"
          + " *      https://www.apache.org/licenses/LICENSE-2.0\n"
          + " *\n"
          + " * Unless required by applicable law or agreed to in writing, software\n"
          + " * distributed under the License is distributed on an \"AS IS\" BASIS,\n"
          + " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
          + " * See the License for the specific language governing permissions and\n"
          + " * limitations under the License.\n"
          + " */\n"
          + "\n"
          + SpringAutoConfigClassComposerTest.EXPECTED_CLASS_STRING;

  @Before
  public void setUp() {

    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    ServiceDescriptor echoService = echoFileDescriptor.getServices().get(0);
    Assert.assertEquals(echoService.getName(), "Echo");

    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);

    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();

    List<Service> services =
        Parser.parseService(
            echoFileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    context =
        GapicContext.builder()
            .setMessages(messageTypes)
            .setResourceNames(resourceNames)
            .setServices(services)
            .setHelperResourceNames(outputResourceNames)
            .setTransport(Transport.GRPC)
            .build();
  }

  @Test
  public void spring_composer_test() {

    List<GapicClass> gapicClasses = SpringComposer.composeServiceAutoConfigClasses(context);

    // write to verify result for now
    StringBuilder codeForAllClasses = new StringBuilder();
    JavaWriterVisitor codeWriter = new JavaWriterVisitor();
    for (GapicClass gapicClazz : gapicClasses) {
      ClassDefinition clazz = gapicClazz.classDefinition();

      clazz.accept(codeWriter);
      String code = codeWriter.write();
      codeForAllClasses.append(code);
      codeWriter.clear();
    }

    Assert.assertEquals(codeForAllClasses.toString(), EXPECTED_CODE);
  }
}
