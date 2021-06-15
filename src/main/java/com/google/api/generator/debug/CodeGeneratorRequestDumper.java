// Copyright 2021 Google LLC
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

package com.google.api.generator.debug;

import com.google.api.generator.ProtoRegistry;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse;
import java.io.IOException;

public class CodeGeneratorRequestDumper {
  public static void main(String[] args) throws IOException {
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    ProtoRegistry.registerAllExtensions(registry);
    CodeGeneratorRequest request = CodeGeneratorRequest.parseFrom(System.in, registry);

    CodeGeneratorResponse.Builder response = CodeGeneratorResponse.newBuilder();
    response
        .setSupportedFeatures(CodeGeneratorResponse.Feature.FEATURE_PROTO3_OPTIONAL_VALUE)
        .addFileBuilder()
        .setName("desc-dump.bin")
        .setContentBytes(request.toByteString());
    response.build().writeTo(System.out);
  }
}
