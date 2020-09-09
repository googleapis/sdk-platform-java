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

package com.google.api.generator.diff;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.Patch;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class FileDiffUtils {
  public static List<String> diffStringAndFile(String actualContent, String goldenFilePath) {
    List<String> original = Arrays.asList(actualContent.split("\\r?\\n"));
    List<String> revised = null;
    Patch<String> diff = null;
    try {
      revised = Files.readAllLines(new File(goldenFilePath).toPath());
    } catch (IOException e) {
      throw new GoldenFileNotFoundException(
          String.format("Golden File %s is not found.", goldenFilePath));
    }

    try {
      diff = DiffUtils.diff(original, revised);
    } catch (DiffException e) {
      throw new FileDiffException(
          "Could not compute the difference between actual codegen and golden file.");
    }

    List<String> unifiedDiff =
        UnifiedDiffUtils.generateUnifiedDiff("sample", goldenFilePath, original, diff, 2);
    return unifiedDiff;
  }

  private static class GoldenFileNotFoundException extends RuntimeException {
    public GoldenFileNotFoundException(String errorMessage) {
      super(errorMessage);
    }
  }

  private static class FileDiffException extends RuntimeException {
    public FileDiffException(String errorMessage) {
      super(errorMessage);
    }
  }
}
