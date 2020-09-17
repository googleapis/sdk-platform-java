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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

  public static void saveToFile(String fileName, String codegen) {
    String TEST_UNDECLARED_OUTPUTS_DIR = System.getenv("TEST_CLI_HOME");
    // Map<String, String> env = System.getenv();
    // // Java 8
    // //env.forEach((k, v) -> System.out.println(k + ":" + v));

    // // Classic way to loop a map
    // for (Map.Entry<String, String> entry : env.entrySet()) {
    //     System.out.println(entry.getKey() + " : " + entry.getValue());
    // }

    // Path testOutputPath = Paths.get(TEST_UNDECLARED_OUTPUTS_DIR, fileName);
    System.out.println(
        "TEST_UNDECLARED_OUTPUTS_DIR value: it's changing! " + TEST_UNDECLARED_OUTPUTS_DIR);
    Path testOutputPath = Paths.get(TEST_UNDECLARED_OUTPUTS_DIR, fileName);
    System.out.println("testOutputPath: " + testOutputPath.toAbsolutePath());
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
    System.out.println("Saved to file!");
  }

  public static void updateGoldenFile(Path goldenFilePath, String codegen) {
    File goldenFile = goldenFilePath.toFile();
    FileWriter myWriter = null;
    try {
      if (Files.exists(goldenFilePath.toAbsolutePath())) {
        System.out.println("golden file exists !");
        goldenFile.delete();
      }
      goldenFile.createNewFile();
      myWriter = new FileWriter(goldenFile, false);
      myWriter.write(codegen);
      myWriter.flush();
      myWriter.close();
    } catch (IOException e) {
      System.out.println("A File creation error occurred." + e);
    }
    System.out.println("Updated golden successfully! ");
  }
}
