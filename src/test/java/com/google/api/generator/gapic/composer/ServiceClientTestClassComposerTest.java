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

import static com.google.api.generator.test.framework.Assert.assertCodeEquals;
import static junit.framework.Assert.assertEquals;

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.test.framework.Utils;
import com.google.logging.v2.LogEntryProto;
import com.google.logging.v2.LoggingConfigProto;
import com.google.logging.v2.LoggingMetricsProto;
import com.google.logging.v2.LoggingProto;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.pubsub.v1.PubsubProto;
import com.google.showcase.v1beta1.EchoOuterClass;
import google.cloud.CommonResources;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class ServiceClientTestClassComposerTest {
  @Test
  public void generateClientTest_echoClient() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    ServiceDescriptor echoService = echoFileDescriptor.getServices().get(0);
    assertEquals(echoService.getName(), "Echo");

    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(echoFileDescriptor, messageTypes, resourceNames, outputResourceNames);

    Service echoProtoService = services.get(0);
    GapicClass clazz =
        ServiceClientTestClassComposer.instance()
            .generate(echoProtoService, resourceNames, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "EchoClientTest.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "EchoClientTest.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateClientTest_pubSubPublisherClient() {
    FileDescriptor serviceFileDescriptor = PubsubProto.getDescriptor();
    FileDescriptor commonResourcesFileDescriptor = CommonResources.getDescriptor();
    ServiceDescriptor serviceDescriptor = serviceFileDescriptor.getServices().get(0);
    assertEquals("Publisher", serviceDescriptor.getName());

    Map<String, ResourceName> resourceNames = new HashMap<>();
    resourceNames.putAll(Parser.parseResourceNames(serviceFileDescriptor));
    resourceNames.putAll(Parser.parseResourceNames(commonResourcesFileDescriptor));

    Map<String, Message> messageTypes = Parser.parseMessages(serviceFileDescriptor);

    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            serviceFileDescriptor, messageTypes, resourceNames, outputResourceNames);

    Service subscriptionService = services.get(1);
    assertEquals("Subscriber", subscriptionService.name());
    GapicClass clazz =
        ServiceClientTestClassComposer.instance()
            .generate(subscriptionService, resourceNames, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "SubscriberClientTest.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "SubscriberClientTest.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateClientTest_logging() {
    FileDescriptor serviceFileDescriptor = LoggingProto.getDescriptor();
    ServiceDescriptor serviceDescriptor = serviceFileDescriptor.getServices().get(0);
    assertEquals(serviceDescriptor.getName(), "LoggingServiceV2");

    List<FileDescriptor> protoFiles =
        Arrays.asList(
            serviceFileDescriptor,
            LogEntryProto.getDescriptor(),
            LoggingConfigProto.getDescriptor(),
            LoggingMetricsProto.getDescriptor());

    Map<String, ResourceName> resourceNames = new HashMap<>();
    Map<String, Message> messageTypes = new HashMap<>();
    for (FileDescriptor fileDescriptor : protoFiles) {
      resourceNames.putAll(Parser.parseResourceNames(fileDescriptor));
      messageTypes.putAll(Parser.parseMessages(fileDescriptor));
    }

    // Additional resource names.
    FileDescriptor commonResourcesFileDescriptor = CommonResources.getDescriptor();
    resourceNames.putAll(Parser.parseResourceNames(commonResourcesFileDescriptor));

    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            serviceFileDescriptor, messageTypes, resourceNames, outputResourceNames);

    Service loggingService = services.get(0);
    GapicClass clazz =
        ServiceClientTestClassComposer.instance()
            .generate(loggingService, resourceNames, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "LoggingClientTest.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "LoggingClientTest.golden");
    assertCodeEquals(goldenFilePath, visitor.write());
  }
}
