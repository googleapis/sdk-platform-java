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

  public static void saveToFile(String dirName, String fileName, String codegen) {
    System.out.println("dirname: " + dirName);

    String outputDir = System.getenv("TEST_CLI_HOME");
    Path testOutputPath = Paths.get(outputDir, dirName);
    testOutputPath.toFile().mkdirs();
    testOutputPath = Paths.get(outputDir, dirName, fileName);

    System.out.println("testOutputPath: " + testOutputPath.toString());
    try {
      File createdFile = testOutputPath.toFile();
      if (createdFile.createNewFile()) {
        System.out.println("File created: " + createdFile.getName());
      } else {
        System.out.println("File did not get created :(");
      }
      FileWriter myWriter = new FileWriter(createdFile);
      myWriter.write(codegen);
      myWriter.flush();
      myWriter.close();
    } catch (IOException e) {
      System.out.println("Error occured when saving codegen to file" + fileName);
    }
    System.out.println("Saved to file! ");
  }
}
