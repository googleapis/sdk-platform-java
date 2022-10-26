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
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    JsonObject jsonResult = JsonParser.parseString(result).getAsJsonObject();

    JsonObject innerExpected = new JsonObject();
    innerExpected.addProperty("name", "com.google.showcase.v1beta1.spring.auto.echo.enabled");
    innerExpected.addProperty("type", "java.lang.Boolean");
    innerExpected.addProperty(
        "description", "Auto-configure Google Cloud showcase/Echo components.");
    innerExpected.addProperty("defaultValue", true);
    JsonArray innerExpectedArray = new JsonArray();
    innerExpectedArray.add(innerExpected);
    JsonObject jsonExpected = new JsonObject();
    jsonExpected.add("properties", innerExpectedArray);

    assertEquals(jsonExpected, jsonResult);
  }

  @Test
  public void writePomTest() {
    String result = SpringWriter.writePom(context, jos);
    String fileName = "SpringPackagePom.golden";
    Utils.saveCodegenToFile(this.getClass(), fileName, result);
    Path goldenFilePath = Paths.get(Utils.getGoldenDir(this.getClass()), fileName);
    Assert.assertCodeEquals(goldenFilePath, result);
  }
}
