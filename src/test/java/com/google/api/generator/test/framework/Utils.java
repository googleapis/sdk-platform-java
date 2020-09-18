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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

  public static void saveCodegenToFile(Class clazz, String fileName, String codegen) {
    // This system environment variable `TEST_OUTPUT_HOME` is used to specify a folder
    // which contains generated output from JUnit test.
    // It will be set when running `bazel run testTarget.update` command.
    String testOutputHome = System.getenv("TEST_OUTPUT_HOME");
    // For example: com/google/api/generator/gapic/dummy/goldens/
    String relativeGoldenDir = getTestoutGoldenDir(clazz);
    Path testOutputDir = Paths.get(testOutputHome, relativeGoldenDir);
    testOutputDir.toFile().mkdirs();
    try {
      File testOutputFile = Paths.get(testOutputHome, relativeGoldenDir, fileName).toFile();
      FileWriter myWriter = new FileWriter(testOutputFile);
      myWriter.write(codegen);
      myWriter.flush();
      myWriter.close();
    } catch (IOException e) {
      throw new SaveCodegenToFileException(
          String.format(
              "Error occured when saving codegen to file %s/%s", relativeGoldenDir, fileName));
    }
  }

  private static String getTestoutGoldenDir(Class clazz) {
    return clazz.getPackage().getName().replace(".", "/") + "/goldens/";
  }

  private static class SaveCodegenToFileException extends RuntimeException {
    public SaveCodegenToFileException(String errorMessage) {
      super(errorMessage);
    }
  }
}
