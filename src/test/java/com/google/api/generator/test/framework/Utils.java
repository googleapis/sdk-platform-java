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

public class Utils {

  public static boolean getProperty(String key) {
    String property = System.getProperty(key);
    if (property != null) {
      return property.equals("true");
    }
    return false;
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
