// Copyright 2021 Google LLC
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

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackageChecker {
  private PackageChecker() {}

  /**
   * Identifies whether a Java package is GA API. Assumes that the version appears in the last
   * component of the package, e.g. com.google.cloud.dataproc.v1beta1.
   */
  public static boolean isGaApi(String pakkage) {
    String[] packageComponents = pakkage.split("\\.");
    Preconditions.checkState(
        packageComponents.length > 0, "No subcomponents found in Java package %s", pakkage);
    String versionComponent = packageComponents[packageComponents.length - 1];
    Matcher matcher = Pattern.compile("^v[0-9]+").matcher(versionComponent);
    Preconditions.checkState(
        matcher.find(),
        "No version component found in last subpackage %s of %s",
        versionComponent,
        pakkage);
    String versionSubstr = versionComponent.replace(matcher.group(), "");
    return Strings.isNullOrEmpty(versionSubstr)
        || (!versionSubstr.contains("alpha") && !versionSubstr.contains("beta"));
  }
}
