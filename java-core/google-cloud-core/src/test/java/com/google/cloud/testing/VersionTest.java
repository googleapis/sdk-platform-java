/*
 * Copyright 2016 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VersionTest {

  @Test
  void testFromString() {
    Version version = Version.fromString("2016.01.13");
    assertEquals(2016, version.getMajor());
    assertEquals(1, version.getMinor());
    assertEquals(13, version.getPatch());
    version = Version.fromString("1.2.0");
    assertEquals(1, version.getMajor());
    assertEquals(2, version.getMinor());
    assertEquals(0, version.getPatch());
  }

  @Test
  void testFromStringWithAlphas() {
    try {
      Version.fromString("2016.01.hello");
      Assertions.fail();
    } catch (IllegalArgumentException ex) {
      assertNotNull(ex.getMessage());
    }
  }

  @Test
  void testFromStringMissingPatch() {
    try {
      Version.fromString("2016.01");
      Assertions.fail();
    } catch (IllegalArgumentException ex) {
      assertNotNull(ex.getMessage());
    }
  }

  @Test
  void testFromStringMissingMinor() {
    try {
      Version.fromString("2016");
      Assertions.fail();
    } catch (IllegalArgumentException ex) {
      assertNotNull(ex.getMessage());
    }
  }

  @Test
  void testFromStringEmpty() {
    try {
      Version.fromString("");
      Assertions.fail();
    } catch (IllegalArgumentException ex) {
      assertNotNull(ex.getMessage());
    }
  }

  @Test
  void testFromStringNull() {
    try {
      Version.fromString(null);
      Assertions.fail();
    } catch (NullPointerException ex) {
      assertNull(ex.getMessage());
    }
  }

  @Test
  void testCompare() {
    Version version = Version.fromString("2016.01.13");
    Version sameVersion = Version.fromString("2016.01.13");
    Version olderVersion = Version.fromString("2015.12.01");
    Version newerVersion = Version.fromString("2016.08.12");
    assertEquals(0, version.compareTo(sameVersion));
    assertTrue(version.compareTo(olderVersion) > 0);
    assertTrue(version.compareTo(newerVersion) < 0);
    Version otherVersion = Version.fromString("1.2.0");
    assertTrue(version.compareTo(otherVersion) > 0);
  }
}
