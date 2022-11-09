// Copyright 2022 Google LLC
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

package com.google.api.generator.gapic.composer.utils;

import static org.junit.Assert.assertEquals;

import com.google.api.generator.testutils.LineFormatter;
import org.junit.Test;

public class CommentFormatterTest {
  private static final String SERVICE_DESCRIPTION_HEADER_PATTERN = "Service Description: %s";

  @Test
  public void parseCommentWithItemizedList() {
    String protobufComment =
        LineFormatter.lines(
            " Service Name\n\n",
            " One line initial description. Here is a list of items:\n",
            " * This is item one\n",
            " * This is item two\n\n",
            " Here is another list, in a new paragraph:\n\n",
            " * This is item one");
    String result =
        CommentFormatter.formatAsJavaDocComment(
            protobufComment, SERVICE_DESCRIPTION_HEADER_PATTERN);
    String expectedJavaDocComment =
        LineFormatter.lines(
            "<p> Service Description:  Service Name\n",
            "<p>  One line initial description. Here is a list of items:\n",
            "<ul>\n",
            "<li>  This is item one\n",
            "<li>  This is item two\n",
            "</ul>\n",
            "<p>  Here is another list, in a new paragraph:\n",
            "<ul>\n",
            "<li>  This is item one\n",
            "</ul>");

    assertEquals(expectedJavaDocComment, result);
  }
}
