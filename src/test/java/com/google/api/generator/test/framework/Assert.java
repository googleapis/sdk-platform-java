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

package com.google.api.generator.test.framework;

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.comment.CommentComposer;
import com.google.api.generator.gapic.composer.samplecode.SampleCodeWriter;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import junit.framework.AssertionFailedError;

public class Assert {
  /**
   * Assert that the generated code is identical with the content in corresponding golden file. The
   * differences will be emitted to the test-logs if any.
   *
   * @param goldenPath the path of the golden file.
   * @param codegen the generated source code.
   */
  public static void assertCodeEquals(Path goldenPath, String codegen) {
    List<String> diffList = Differ.diff(goldenPath, codegen);
    if (!diffList.isEmpty()) {
      throw new AssertionFailedError("Differences found: \n" + String.join("\n", diffList));
    }
  }

  // Assert that two strings are identical, else throw AssertionFailedError and emit the
  // differences to the test-logs.
  public static void assertCodeEquals(String expected, String codegen) {
    List<String> diffList = Differ.diff(expected, codegen);
    if (!diffList.isEmpty()) {
      throw new AssertionFailedError("Differences found: \n" + String.join("\n", diffList));
    }
  }

  public static void assertEmptySamples(List<Sample> samples) {
    if (!samples.isEmpty()) {
      List<String> diffList = samples.stream().map(Sample::name).collect(Collectors.toList());
      throw new AssertionFailedError("Differences found: \n" + String.join("\n", diffList));
    }
  }

  public static void assertSampleFileCount(String goldenDir, List<Sample> samples) {
    File directory = new File(goldenDir);
    if (directory.list().length != samples.size()) {
      List<String> fileNames =
          Arrays.stream(directory.listFiles()).map(File::getName).collect(Collectors.toList());
      List<String> sampleNames =
          samples.stream()
              .map(s -> String.format("%s.golden", JavaStyle.toUpperCamelCase(s.name())))
              .collect(Collectors.toList());
      List<String> diffList = Differ.diffTwoStringLists(fileNames, sampleNames);
      if (!diffList.isEmpty()) {
        String d = "Differences found: \n" + String.join("\n", diffList);
        throw new AssertionFailedError("Differences found: \n" + String.join("\n", diffList));
      }
    }
  }

  public static void assertGoldenClass(Class<?> clazz, GapicClass gapicClass, String fileName) {
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    gapicClass.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(clazz, fileName, visitor.write());
    Path goldenFilePath = Paths.get(Utils.getGoldenDir(clazz), fileName);
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  public static void assertGoldenSamples(List<Sample> samples, String packkage, String goldenDir) {
    for (Sample sample : samples) {
      String fileName = JavaStyle.toUpperCamelCase(sample.name()).concat(".golden");
      Path goldenFilePath = Paths.get(goldenDir, fileName);
      sample =
          sample
              .withHeader(Arrays.asList(CommentComposer.APACHE_LICENSE_COMMENT))
              .withRegionTag(sample.regionTag().withApiShortName("goldenSample"));
      assertCodeEquals(
          goldenFilePath, SampleCodeWriter.writeExecutableSample(sample, packkage + ".samples"));
    }
  }
}
