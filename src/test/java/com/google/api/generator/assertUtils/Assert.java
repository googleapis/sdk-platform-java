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

package com.google.api.generator.assertUtils;

import com.google.api.generator.diffUtils.FileDiffUtils;
import java.nio.file.Path;
import java.util.List;
import junit.framework.AssertionFailedError;

public class Assert {
  public static void assertCodeEquals(Path goldenPath, String codegen) {
    List<String> diffList = FileDiffUtils.diffFileAndString(goldenPath, codegen);
    if (!diffList.isEmpty()) {
      throw new AssertionFailedError("Differences found: \n" + String.join("\n", diffList));
    }
  }

  public static void assertCodeEquals(String expected, String codegen) {
    List<String> diffList = FileDiffUtils.diffTwoStrings(expected, codegen);
    if (!diffList.isEmpty()) {
      throw new AssertionFailedError("Differences found: \n" + String.join("\n", diffList));
    }
  }
}
