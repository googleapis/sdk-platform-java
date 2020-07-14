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

import com.google.api.AnnotationsProto;
import com.google.api.ClientProto;
import com.google.api.FieldBehaviorProto;
import com.google.api.ResourceProto;
import com.google.api.generator.gapic.Generator;
import com.google.longrunning.OperationsProto;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse;
import java.io.IOException;

public class Main {
  public static void main(String[] args)
      throws IOException, InterruptedException, DescriptorValidationException {
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    registerAllExtensions(registry);
    CodeGeneratorRequest request = CodeGeneratorRequest.parseFrom(System.in, registry);
    String outputFilePath = String.format("%stemp-gen.srcjar", request.getParameter());
    CodeGeneratorResponse response = Generator.generateGapic(request, outputFilePath);
    response.writeTo(System.out);
  }

  /** Register all extensions needed to process API protofiles. */
  private static void registerAllExtensions(ExtensionRegistry extensionRegistry) {
    OperationsProto.registerAllExtensions(extensionRegistry);
    AnnotationsProto.registerAllExtensions(extensionRegistry);
    ClientProto.registerAllExtensions(extensionRegistry);
    ResourceProto.registerAllExtensions(extensionRegistry);
    FieldBehaviorProto.registerAllExtensions(extensionRegistry);
  }
}
