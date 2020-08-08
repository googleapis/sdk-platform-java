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

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class ComposerTest {
  private ServiceDescriptor echoService;
  private FileDescriptor echoFileDescriptor;

  @Before
  public void setUp() {
    echoFileDescriptor = EchoOuterClass.getDescriptor();
    echoService = echoFileDescriptor.getServices().get(0);
    assertEquals(echoService.getName(), "Echo");
  }

  @Test
  public void generateStubServiceSettings() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();

    List<Service> services =
        Parser.parseService(echoFileDescriptor, messageTypes, resourceNames, outputResourceNames);
    Service echoProtoService = services.get(0);
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    Composer.generateStubServiceSettings(echoProtoService).classDefinition().accept(visitor);
    System.out.println(visitor.write());
    System.out.println(EXPECTED_CLASS_STRING);

    assertEquals(visitor.write(), EXPECTED_CLASS_STRING);
  }

  private static final String EXPECTED_CLASS_STRING =
      "/*\n"
          + " * Copyright 2020 Google LLC\n"
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
          + " */\n\n"
          + "package com.google.showcase.v1beta1.stub;\n\n"
          + "public class EchoStubSettings {}\n";
}
