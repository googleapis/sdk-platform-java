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

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.Patch;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Differ {
  public static List<String> diff(Path goldenFilePath, String codegen) {
    List<String> revised = Arrays.asList(codegen.split("\\r?\\n"));
    List<String> original = null;
    try {
      original = Files.readAllLines(goldenFilePath);
    } catch (IOException e) {
      throw new GoldenFileReadException(
          String.format("Error occurs when reading golden file %s", goldenFilePath));
    }
    return diffTwoStringLists(original, revised);
  }

  public static List<String> diff(String expectedStr, String actualStr) {
    List<String> revised = Arrays.asList(actualStr.split("\\r?\\n"));
    List<String> original = Arrays.asList(expectedStr.split("\\r?\\n"));
    return diffTwoStringLists(original, revised);
  }

  private static List<String> diffTwoStringLists(List<String> original, List<String> revised) {
    Patch<String> diff = null;
    try {
      diff = DiffUtils.diff(original, revised);
    } catch (DiffException e) {
      throw new ComputeDiffException("Could not compute the differences.");
    }
    List<String> unifiedDiff =
        UnifiedDiffUtils.generateUnifiedDiff("golden", "codegen", original, diff, 2);
    return unifiedDiff;
  }

  private static class GoldenFileReadException extends RuntimeException {
    public GoldenFileReadException(String errorMessage) {
      super(errorMessage);
    }
  }

  private static class ComputeDiffException extends RuntimeException {
    public ComputeDiffException(String errorMessage) {
      super(errorMessage);
    }
  }
}
