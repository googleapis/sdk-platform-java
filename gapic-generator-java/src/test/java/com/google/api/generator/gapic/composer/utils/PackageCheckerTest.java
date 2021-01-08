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

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class PackageCheckerTest {
  @Test
  public void isGaApi_normalExpectedPackageStructure() {
    assertTrue(PackageChecker.isGaApi("com.google.cloud.dataproc.v1"));
    assertTrue(PackageChecker.isGaApi("com.google.cloud.dataproc.v999"));
    assertTrue(PackageChecker.isGaApi("com.google.cloud.dataproc.v12345a"));
    assertTrue(PackageChecker.isGaApi("com.google.cloud.dataproc.v999asdf"));
    assertFalse(PackageChecker.isGaApi("com.google.cloud.dataproc.v1beta1"));
    assertFalse(PackageChecker.isGaApi("com.google.cloud.dataproc.v1alpha1"));
    assertFalse(PackageChecker.isGaApi("com.google.cloud.dataproc.v1beta"));
    assertFalse(PackageChecker.isGaApi("com.google.cloud.dataproc.v1alpha"));
    assertFalse(PackageChecker.isGaApi("com.google.cloud.dataproc.v1p2alpha1"));
    assertFalse(PackageChecker.isGaApi("com.google.cloud.dataproc.v1p4beta"));
    assertFalse(PackageChecker.isGaApi("com.google.cloud.asset.v1beta1"));
  }

  @Test
  public void isGaApi_invalidPackageStructure() {
    assertThrows(
        IllegalStateException.class,
        () -> PackageChecker.isGaApi("com.google.cloud.dataproc.v1.foobar"));
    assertThrows(IllegalStateException.class, () -> PackageChecker.isGaApi(""));
  }
}
