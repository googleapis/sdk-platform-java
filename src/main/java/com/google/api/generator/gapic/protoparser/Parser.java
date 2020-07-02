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

import com.google.api.generator.gapic.model.Service;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.ServiceDescriptorProto;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest;
import java.util.ArrayList;
import java.util.List;

public class Parser {
  // TODO(miraleung): Caller should handle empty lists.
  public static List<Service> parseServices(CodeGeneratorRequest request) {
    List<Service> services = new ArrayList<>();
    for (FileDescriptorProto fileDescriptorProto : request.getProtoFileList()) {
      for (ServiceDescriptorProto serviceDescriptor : fileDescriptorProto.getServiceList()) {
        // TODO(miraleung): Parse methods here too.
        Service service = Service.builder().setName(serviceDescriptor.getName()).build();
        services.add(service);
      }
    }

    return services;
  }
}
