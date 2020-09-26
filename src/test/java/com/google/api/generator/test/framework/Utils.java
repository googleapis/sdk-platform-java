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

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
  /**
   * Save the generated code from JUnit test to a file for updating goldens. These files will be
   * saved as a zip file, then unzipped to overwrite goldens files. The relative path
   * `com/google/..` which is identical with the location of goldens files which will help us easily
   * replace the original goldens. For example:
   * `src/test/java/com/google/api/generator/gapic/composer/ComposerTest.java` will save the
   * generated code into a file called `ComposerTest.golden` at
   * `$TEST_OUTPUT_HOME/com/google/api/generator/gapic/composer/goldens/ComposerTest.golden`.
   *
   * @param clazz the test class.
   * @param fileName the name of saved file, usually it is test method name with suffix `.golden`.
   * @param codegen the generated code from JUnit test.
   */
  public static void saveCodegenToFile(Class clazz, String fileName, String codegen) {
    // This system environment variable `TEST_OUTPUT_HOME` is used to specify a folder
    // which contains generated output from JUnit test.
    // It will be set when running `bazel run testTarget_update` command.
    String testOutputHome = System.getenv("TEST_OUTPUT_HOME");
    String relativeGoldenDir = getTestoutGoldenDir(clazz);
    Path testOutputDir = Paths.get(testOutputHome, relativeGoldenDir);
    testOutputDir.toFile().mkdirs();
    try (FileWriter myWriter =
        new FileWriter(Paths.get(testOutputHome, relativeGoldenDir, fileName).toFile())) {
      myWriter.write(codegen);
    } catch (IOException e) {
      throw new SaveCodegenToFileException(
          String.format(
              "Error occured when saving codegen to file %s/%s", relativeGoldenDir, fileName));
    }
  }

  private static String getTestoutGoldenDir(Class clazz) {
    return clazz.getPackage().getName().replace(".", "/") + "/goldens/";
  }

  public static String getGoldenDir(Class clazz) {
    return "src/test/java/" + clazz.getPackage().getName().replace(".", "/") + "/goldens/";
  }

  public static String getClassName(Class clazz) {
    return clazz.getSimpleName();
  }

  public static class SaveCodegenToFileException extends RuntimeException {
    public SaveCodegenToFileException(String errorMessage) {
      super(errorMessage);
    }

    public SaveCodegenToFileException(String errorMessage, Throwable cause) {
      super(errorMessage, cause);
    }
  }
}
