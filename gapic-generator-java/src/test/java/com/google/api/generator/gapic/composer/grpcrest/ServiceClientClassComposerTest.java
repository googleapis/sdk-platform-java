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

package com.google.api.generator.gapic.composer.grpcrest;

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.GoldenFileWriter;
import com.google.api.generator.test.protoloader.GrpcRestTestProtoLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ServiceClientClassComposerTest {
  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
          {"EchoClient", GrpcRestTestProtoLoader.instance().parseShowcaseEcho(), 0},
          {"EchoEmpty", GrpcRestTestProtoLoader.instance().parseShowcaseEcho(), 1},
          {"WickedClient", GrpcRestTestProtoLoader.instance().parseShowcaseWicked(), 0},
          {"ApiVersionClient", GrpcRestTestProtoLoader.instance().parseApiVersionTesting(), 0},
        });
  }

  @Parameterized.Parameter public String name;

  @Parameterized.Parameter(1)
  public GapicContext context;

  @Parameterized.Parameter(2)
  public int serviceIndex;

  @Test
  public void generateServiceClasses() {
    Service echoProtoService = context.services().get(serviceIndex);
    GapicClass clazz = ServiceClientClassComposer.instance().generate(context, echoProtoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    GoldenFileWriter.saveCodegenToFile(this.getClass(), name + ".golden", visitor.write());
    Path goldenFilePath =
        Paths.get(GoldenFileWriter.getGoldenDir(this.getClass()), name + ".golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }
}
