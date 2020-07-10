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

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Service;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {
  static class GapicParserException extends RuntimeException {
    public GapicParserException(String errorMessage) {
      super(errorMessage);
    }
  }

  public static List<Service> parseServices(
      CodeGeneratorRequest request, Map<String, Message> messageTypes) {
    Map<String, FileDescriptor> fileDescriptors = getFilesToGenerate(request);
    List<Service> services = new ArrayList<>();
    for (String fileToGenerate : request.getFileToGenerateList()) {
      FileDescriptor fileDescriptor =
          Preconditions.checkNotNull(
              fileDescriptors.get(fileToGenerate),
              "Missing file descriptor for [%s]",
              fileToGenerate);

      String pakkage = TypeParser.getPackage(fileDescriptor);
      for (ServiceDescriptor serviceDescriptor : fileDescriptor.getServices()) {
        List<Method> methods = parseMethods(serviceDescriptor, messageTypes);
        Service service =
            Service.builder()
                .setName(serviceDescriptor.getName())
                .setPakkage(pakkage)
                .setMethods(methods)
                .build();
        services.add(service);
      }
    }

    return services;
  }

  public static Map<String, Message> parseMessages(CodeGeneratorRequest request) {
    Map<String, FileDescriptor> fileDescriptors = getFilesToGenerate(request);
    Map<String, Message> messages = new HashMap<>();
    for (String fileToGenerate : request.getFileToGenerateList()) {
      FileDescriptor fileDescriptor =
          Preconditions.checkNotNull(
              fileDescriptors.get(fileToGenerate),
              "Missing file descriptor for [%s]",
              fileToGenerate);

      String pakkage = TypeParser.getPackage(fileDescriptor);
      for (Descriptor messageDescriptor : fileDescriptor.getMessageTypes()) {
        List<Field> fields = parseFields(messageDescriptor);
        String messageName = messageDescriptor.getName();
        messages.put(
            messageName,
            Message.builder()
                .setType(
                    TypeNode.withReference(
                        VaporReference.builder().setName(messageName).setPakkage(pakkage).build()))
                .setName(messageName)
                .setFields(fields)
                .build());
      }
    }
    return messages;
  }

  private static List<Field> parseFields(Descriptor messageDescriptor) {
    return messageDescriptor.getFields().stream()
        .map(f -> Field.builder().setName(f.getName()).setType(TypeParser.parseType(f)).build())
        .collect(Collectors.toList());
  }

  private static Map<String, FileDescriptor> getFilesToGenerate(CodeGeneratorRequest request) {
    // Build the fileDescriptors map so that we can create the FDs for the filesToGenerate.
    Map<String, FileDescriptor> fileDescriptors = Maps.newHashMap();
    for (FileDescriptorProto fileDescriptorProto : request.getProtoFileList()) {
      // Look up the imported files from previous file descriptors.  It is sufficient to look at
      // only previous file descriptors because CodeGeneratorRequest guarantees that the files
      // are sorted in topological order.
      FileDescriptor[] deps = new FileDescriptor[fileDescriptorProto.getDependencyCount()];
      for (int i = 0; i < fileDescriptorProto.getDependencyCount(); i++) {
        String name = fileDescriptorProto.getDependency(i);
        deps[i] =
            Preconditions.checkNotNull(
                fileDescriptors.get(name), "Missing file descriptor for [%s]", name);
      }

      FileDescriptor fileDescriptor = null;
      try {
        fileDescriptor = FileDescriptor.buildFrom(fileDescriptorProto, deps);
      } catch (DescriptorValidationException e) {
        throw new GapicParserException(e.getMessage());
      }

      fileDescriptors.put(fileDescriptor.getName(), fileDescriptor);
    }
    return fileDescriptors;
  }

  private static List<Method> parseMethods(
      ServiceDescriptor serviceDescriptor, Map<String, Message> messageTypes) {
    List<Method> methods = new ArrayList<>();
    for (MethodDescriptor methodDescriptor : serviceDescriptor.getMethods()) {
      methods.add(
          Method.builder()
              .setName(methodDescriptor.getName())
              .setInputType(TypeParser.parseType(methodDescriptor.getInputType()))
              .setOutputType(TypeParser.parseType(methodDescriptor.getOutputType()))
              .build());
    }
    return methods;
  }
}
