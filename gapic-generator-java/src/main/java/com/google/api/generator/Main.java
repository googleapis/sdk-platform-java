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

package com.google.api.generator;

import com.google.api.generator.gapic.Generator;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.protobuf.ByteString;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse;
import java.io.IOException;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class Main {
  public static void main(String[] args)
      throws IOException, InterruptedException, DescriptorValidationException {
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    CodeGeneratorRequest request = CodeGeneratorRequest.parseFrom(System.in, registry);

    Generator.generateGapic(request);

    CodeGeneratorResponse response = doPlaceholderStuff(request);
    response.writeTo(System.out);
  }

  private static CodeGeneratorResponse doPlaceholderStuff(CodeGeneratorRequest request)
      throws IOException, DescriptorValidationException {
    ByteString.Output output = ByteString.newOutput();
    JarOutputStream jos = new JarOutputStream(output);
    generateCode(request, jos);
    jos.flush();

    CodeGeneratorResponse.Builder response = CodeGeneratorResponse.newBuilder();
    response
        .setSupportedFeatures(CodeGeneratorResponse.Feature.FEATURE_PROTO3_OPTIONAL_VALUE)
        .addFileBuilder()
        .setName(request.getParameter() + "temp-gen.srcjar")
        .setContentBytes(output.toByteString());

    return response.build();
  }

  private static void generateCode(CodeGeneratorRequest request, JarOutputStream jos)
      throws DescriptorValidationException, IOException {
    Preconditions.checkArgument(
        request.getFileToGenerateCount() >= 1, "Expected: at least one proto file input");
    Map<String, String> scopeToJavaPackageMap = Maps.newHashMap();

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

      FileDescriptor fileDescriptor = FileDescriptor.buildFrom(fileDescriptorProto, deps);
      fileDescriptors.put(fileDescriptor.getName(), fileDescriptor);
    }

    for (FileDescriptor value : fileDescriptors.values()) {
      String javaPkgName = getPackage(value);
      for (Descriptors.Descriptor messageType : value.getMessageTypes()) {
        String name = messageType.getName();
        scopeToJavaPackageMap.put(value.getPackage() + "." + name, getJavaClassName(messageType));
      }
    }

    for (String fileToGenerate : request.getFileToGenerateList()) {
      FileDescriptor fileDescriptor =
          Preconditions.checkNotNull(
              fileDescriptors.get(fileToGenerate),
              "Missing file descriptor for [%s]",
              fileToGenerate);
      for (Descriptors.ServiceDescriptor service : fileDescriptor.getServices()) {
        String path = getPackage(fileDescriptor) + ".";
        path = path.replaceAll("\\.", "/");
        String className = service.getName() + "AsyncClient";
        JarEntry jarEntry = new JarEntry(path + className + ".java");
        jos.putNextEntry(jarEntry);
        // TODO(miraleung): Code generation here.
        String asyncClientCode =
            "package com.google.code;\n public class Foo { public int getFoo() { return 1; } }";
        jos.write(asyncClientCode.getBytes());
        className = service.getName() + "ClientModule";
        jarEntry = new JarEntry(path + className + ".java");
        jos.putNextEntry(jarEntry);
        // TODO(miraleung): Code generation here.
        String moduleCode =
            "package com.google.code;\npublic class Boo { public int getBoo() { return 1; } }";
        jos.write(moduleCode.getBytes());
      }
    }
    jos.finish();
  }

  private static String getPackage(Descriptors.FileDescriptor fileDescriptor) {
    String javaPackage = fileDescriptor.getOptions().getJavaPackage();
    if (javaPackage == null || javaPackage.equals("")) {
      String genericPackage = fileDescriptor.getPackage();
      if (genericPackage == null || genericPackage.equals("")) {
        javaPackage = "com.google.protos";
      } else {
        javaPackage = "com.google.protos." + fileDescriptor.getPackage();
      }
    }
    return javaPackage;
  }

  private static String getJavaClassName(Descriptors.Descriptor messageType) {
    String className = getPackage(messageType.getFile());
    String outerClass = messageType.getFile().getOptions().getJavaOuterClassname();
    if (messageType.getFile().getOptions().getJavaMultipleFiles()) {
      outerClass = ""; // Multiple files overrides outer classname.
    }

    if (!"".equals(outerClass)) {
      className += "." + outerClass;
    }
    className += "." + messageType.getName();
    return className;
  }
}
