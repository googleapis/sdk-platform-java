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

package com.google.api.generator.gapic.protowriter;

import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.protobuf.ByteString;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class Writer {
  static class GapicWriterException extends RuntimeException {
    public GapicWriterException(String errorMessage) {
      super(errorMessage);
    }
  }

  public static CodeGeneratorResponse writeCode(List<GapicClass> clazzes, String outputFilePath) {
    ByteString.Output output = ByteString.newOutput();
    JavaWriterVisitor codeWriter = new JavaWriterVisitor();
    JarOutputStream jos = null;
    try {
      jos = new JarOutputStream(output);
    } catch (IOException e) {
      throw new GapicWriterException(e.getMessage());
    }

    for (GapicClass gapicClazz : clazzes) {
      ClassDefinition clazz = gapicClazz.classDefinition();

      clazz.accept(codeWriter);
      String code = codeWriter.write();
      codeWriter.clear();

      String path = getPath(clazz.packageString(), clazz.classIdentifier().name());
      String className = clazz.classIdentifier().name();
      JarEntry jarEntry = new JarEntry(String.format("%s/%s.java", path, className));
      try {
        jos.putNextEntry(jarEntry);
        jos.write(code.getBytes());
      } catch (IOException e) {
        throw new GapicWriterException(
            String.format(
                "Could not write code for class %s.%s: %s",
                clazz.packageString(), clazz.classIdentifier().name(), e.getMessage()));
      }
    }

    try {
      jos.finish();
      jos.flush();
    } catch (IOException e) {
      throw new GapicWriterException(e.getMessage());
    }

    CodeGeneratorResponse.Builder response = CodeGeneratorResponse.newBuilder();
    response
        .setSupportedFeatures(CodeGeneratorResponse.Feature.FEATURE_PROTO3_OPTIONAL_VALUE)
        .addFileBuilder()
        .setName(outputFilePath)
        .setContentBytes(output.toByteString());
    return response.build();
  }

  private static String getPath(String pakkage, String className) {
    String path = pakkage.replaceAll("\\.", "/");
    if (className.startsWith("Mock") || className.endsWith("Test")) {
      path = "test/" + path;
    }
    // Resource name helpers go into the protobuf package.
    if (className.endsWith("Name")) {
      path = "proto/" + path;
    }
    return path;
  }
}
