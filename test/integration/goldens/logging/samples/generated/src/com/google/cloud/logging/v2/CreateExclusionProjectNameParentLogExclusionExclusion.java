/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.logging.v2.samples;

// [START REGION TAG]
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.ProjectName;

public class CreateExclusionProjectNameParentLogExclusionExclusion {

  public static void main(String[] args) throws Exception {
    createExclusionProjectNameParentLogExclusionExclusion();
  }

  public static void createExclusionProjectNameParentLogExclusionExclusion() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      LogExclusion exclusion = LogExclusion.newBuilder().build();
      LogExclusion response = configClient.createExclusion(parent, exclusion);
    }
  }
}
// [END REGION TAG]