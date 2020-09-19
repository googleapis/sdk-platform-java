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

import java.nio.file.Path;
import java.util.List;
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
}
