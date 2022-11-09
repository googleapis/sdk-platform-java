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

package com.google.api.generator.gapic.composer;

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.common.TestProtoLoader;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicPackageInfo;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class ClientLibraryPackageInfoComposerTest {
  private static final String ECHO_SERVICE_DESCRIPTION =
      "This service is used showcase the four main types of rpcs - unary, server\n"
          + " side streaming, client side streaming, and bidirectional streaming. This\n"
          + " service also exposes methods that explicitly implement server delay, and\n"
          + " paginated calls. Set the 'showcase-trailer' metadata key on any method\n"
          + " to have the values echoed in the response trailers.";
  private GapicContext context;

  @Before
  public void setUp() {
    GapicContext echoContext = TestProtoLoader.instance().parseShowcaseEcho();
    // Adds service description for testing purposes, since FileDescriptorProto with SourceCodeInfo
    // from a protoc CodeGeneratorRequest is not available through unit testing resources
    List<Service> services =
        echoContext.services().stream()
            .map(s -> s.toBuilder().setDescription(ECHO_SERVICE_DESCRIPTION).build())
            .collect(Collectors.toList());
    this.context = echoContext.toBuilder().setServices(services).build();
  }

  @Test
  public void composePackageInfo_showcase() {
    GapicPackageInfo packageInfo = ClientLibraryPackageInfoComposer.generatePackageInfo(context);
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    packageInfo.packageInfo().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "ShowcaseWithEchoPackageInfo.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(Utils.getGoldenDir(this.getClass()), "ShowcaseWithEchoPackageInfo.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }
}
