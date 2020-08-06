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

package com.google.api.generator.gapic.composer;

import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.LineComment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHeader {
  public static final List<CommentStatement> APACHE_LICENSE = createApacheLicense();

  private static List<CommentStatement> createApacheLicense() {
    String[] fileHeadeStrings = {
      "Copyright 2020 Google LLC",
      "",
      "Licensed under the Apache License, Version 2.0 (the \"License\");",
      "you may not use this file except in compliance with the License.",
      "You may obtain a copy of the License at",
      "",
      "     http://www.apache.org/licenses/LICENSE-2.0",
      "",
      "Unless required by applicable law or agreed to in writing, software",
      "distributed under the License is distributed on an \"AS IS\" BASIS,",
      "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.",
      "See the License for the specific language governing permissions and",
      "limitations under the License."
    };

    List<CommentStatement> apacheFileHeader = new ArrayList<>();
    Arrays.stream(fileHeadeStrings)
        .forEach(
            s -> {
              apacheFileHeader.add(CommentStatement.withComment(LineComment.withComment(s)));
            });
    return apacheFileHeader;
  }
}
