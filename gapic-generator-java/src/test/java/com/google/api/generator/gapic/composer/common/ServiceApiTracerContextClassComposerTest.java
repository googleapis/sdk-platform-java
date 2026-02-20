/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.api.generator.gapic.composer.common;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.protoloader.TestProtoLoader;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceApiTracerContextClassComposerTest {
  private ServiceApiTracerContextClassComposer composer;
  private JavaWriterVisitor writerVisitor;

  @BeforeEach
  void setUp() {
    composer = ServiceApiTracerContextClassComposer.instance();
    writerVisitor = new JavaWriterVisitor();
  }

  @Test
  void generate_basic() {
    GapicContext context = TestProtoLoader.instance().parseShowcaseEcho();
    Service service = context.services().get(0);
    GapicClass gapicClass = composer.generate(context, service);

    gapicClass.classDefinition().accept(writerVisitor);
    String result = writerVisitor.write();

    assertThat(result).contains("public class EchoApiTracerContext extends ApiTracerContext");
    assertThat(result).contains("private final String serverAddress;");
    assertThat(result).contains("private EchoApiTracerContext(String serverAddress)");
    assertThat(result).contains("this.serverAddress = serverAddress;");
    assertThat(result).contains("public static EchoApiTracerContext create(String serverAddress)");
    assertThat(result).contains("return new EchoApiTracerContext(serverAddress);");
    assertThat(result).contains("public String getServerAddress()");
    assertThat(result).contains("return serverAddress;");
    assertThat(result).contains("public String getRepo()");
    assertThat(result).contains("return null;");
  }

  @Test
  void generate_withRepo() {
    String repo = "googleapis/sdk-platform-java";
    GapicContext context =
        TestProtoLoader.instance().parseShowcaseEcho().toBuilder()
            .setRepo(Optional.of(repo))
            .build();
    Service service = context.services().get(0);
    GapicClass gapicClass = composer.generate(context, service);

    gapicClass.classDefinition().accept(writerVisitor);
    String result = writerVisitor.write();

    assertThat(result).contains("public String getRepo()");
    assertThat(result).contains("return \"" + repo + "\";");
  }
}
