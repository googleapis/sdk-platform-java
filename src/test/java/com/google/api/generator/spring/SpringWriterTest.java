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

package com.google.api.generator.spring;

import static org.junit.Assert.assertEquals;

import com.google.api.generator.gapic.composer.common.TestProtoLoader;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.spring.SpringWriter.GapicWriterException;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import org.junit.Before;
import org.junit.Test;

public class SpringWriterTest {
  private GapicContext context;
  private JarOutputStream jos;

  @Before
  public void setUp() {
    this.context = TestProtoLoader.instance().parseShowcaseEcho();
    try {
      this.jos = new JarOutputStream(ByteString.newOutput());
    } catch (IOException e) {
      throw new GapicWriterException(e.getMessage(), e);
    }
  }

  @Test
  public void writeAutoConfigRegistrationTest() {
    String result = SpringWriter.writeAutoConfigRegistration(context, jos);
    String expected = "com.google.showcase.v1beta1.spring.EchoSpringAutoConfig";
    assertEquals(expected, result);
  }

  @Test
  public void writeSpringAdditionalMetadataJsonTest() {
    String result = SpringWriter.writeSpringAdditionalMetadataJson(context, jos);
    // TODO(emmwang): replace with json comparison?
    String expected =
        "\n"
            + "{\n"
            + "    \"properties\": [\n"
            + "        {\n"
            + "            \"name\": \"com.google.showcase.v1beta1.spring.auto.echo.enabled\",\n"
            + "            \"type\": \"java.lang.Boolean\",\n"
            + "            \"description\": \"Auto-configure Google Cloud showcase/Echo components.\",\n"
            + "            \"defaultValue\": true\n"
            + "        }\n"
            + "    ]\n"
            + "}";
    assertEquals(expected, result);
  }

  @Test
  public void writePomTest() {
    String result = SpringWriter.writePom(context, jos);
    // TODO(emmwang): replace with file comparison?
    String expected =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "  xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\">\n"
            + "  <modelVersion>4.0.0</modelVersion>\n"
            + "\n"
            + "  <groupId>com.google.cloud</groupId>\n"
            + "  <artifactId>com-google-showcase-v1beta1-spring-starter</artifactId>\n"
            + "  <version>{{starter-version}}</version>\n"
            + "  <name>Spring Boot Starter - showcase</name>\n"
            + "  <description>Spring Boot Starter with AutoConfiguration for showcase</description>\n"
            + "\n"
            + "\n"
            + "  <dependencies>\n"
            + "    <dependency>\n"
            + "      <groupId>{{client-library-group-id}}</groupId>\n"
            + "      <artifactId>{{client-library-artifact-id}}</artifactId>\n"
            + "      <version>{{client-library-version}}</version>\n"
            + "    </dependency>\n"
            + "\n"
            + "    <dependency>\n"
            + "      <groupId>org.springframework.boot</groupId>\n"
            + "      <artifactId>spring-boot-starter</artifactId>\n"
            + "      <version>2.6.3</version>\n"
            + "    </dependency>\n"
            + "\n"
            + "  <dependency>\n"
            + "    <groupId>com.google.cloud</groupId>\n"
            + "    <artifactId>spring-cloud-gcp-core</artifactId>\n"
            + "    <version>3.2.1</version>\n"
            + "  </dependency>\n"
            + "</dependencies>\n"
            + "  <build>\n"
            + "    <pluginManagement>\n"
            + "      <plugins>\n"
            + "        <plugin>\n"
            + "          <groupId>org.apache.maven.plugins</groupId>\n"
            + "          <artifactId>maven-jar-plugin</artifactId>\n"
            + "          <version>3.2.2</version>\n"
            + "        </plugin>\n"
            + "      </plugins>\n"
            + "    </pluginManagement>\n"
            + "\n"
            + "    <plugins>\n"
            + "      <plugin>\n"
            + "        <groupId>org.apache.maven.plugins</groupId>\n"
            + "        <artifactId>maven-jar-plugin</artifactId>\n"
            + "        <configuration>\n"
            + "          <archive>\n"
            + "            <manifest>\n"
            + "              <addDefaultImplementationEntries>true</addDefaultImplementationEntries>\n"
            + "            </manifest>\n"
            + "          </archive>\n"
            + "        </configuration>\n"
            + "      </plugin>\n"
            + "    </plugins>\n"
            + "  </build>\n"
            + "\n"
            + "</project>";

    assertEquals(expected, result);
  }
}
