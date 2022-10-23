package com.google.api.generator.spring;

import com.google.api.generator.ProtoRegistry;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    ExtensionRegistry registry = ExtensionRegistry.newInstance();
    ProtoRegistry.registerAllExtensions(registry);
    CodeGeneratorRequest request = CodeGeneratorRequest.parseFrom(System.in, registry);
    CodeGeneratorResponse springResponse = SpringGenerator.generateSpring(request);
    springResponse.writeTo(System.out);
  }
}
