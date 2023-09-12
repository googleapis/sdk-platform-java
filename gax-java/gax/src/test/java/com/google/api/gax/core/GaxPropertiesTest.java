/*
 * Copyright 2020 Google LLC
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google LLC nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.google.api.gax.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.common.base.Strings;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GaxPropertiesTest {

  @Test
  public void testGaxVersion() {
    String gaxVersion = GaxProperties.getGaxVersion();
    assertTrue(Pattern.compile("^\\d+\\.\\d+\\.\\d+").matcher(gaxVersion).find());
    String[] versionComponents = gaxVersion.split("\\.");
    // This test was added in version 1.56.0, so check that the major and minor numbers are greater
    // than that.
    int major = Integer.parseInt(versionComponents[0]);
    int minor = Integer.parseInt(versionComponents[1]);

    assertTrue(major >= 1);
    if (major == 1) {
      assertTrue(minor >= 56);
    }
  }

  private static String originalJavaVersion = System.getProperty("java.version");
  private static String originalJavaVendor = System.getProperty("java.vendor");
  private static String originalJavaVendorVersion = System.getProperty("java.vendor.version");

  @After
  public void cleanup() {
    if (Strings.isNullOrEmpty(originalJavaVersion)) {
      System.clearProperty("java.version");
    } else {
      System.setProperty("java.version", originalJavaVersion);
    }

    if (Strings.isNullOrEmpty(originalJavaVendor)) {
      System.clearProperty("java.vendor");
    } else {
      System.setProperty("java.vendor", originalJavaVendor);
    }

    if (Strings.isNullOrEmpty(originalJavaVendorVersion)) {
      System.clearProperty("java.vendor.version");
    } else {
      System.setProperty("java.vendor.version", originalJavaVendorVersion);
    }
  }

  @Test
  public void testGetJavaRuntimeInfo_graalVM() {
    // This case is one of major Java vendors
    System.setProperty("java.version", "17.0.3");
    System.setProperty("java.vendor", "GraalVM Community");
    System.setProperty("java.vendor.version", "GraalVM CE 22.1.0");

    String runtimeInfo = GaxProperties.getRuntimeVersion();
    assertEquals("17.0.3__GraalVM-Community__GraalVM-CE-22.1.0", runtimeInfo);
  }

  @Test
  public void testGetJavaRuntimeInfo_temurin() {
    // This case is one of major Java vendors
    System.setProperty("java.version", "11.0.19");
    System.setProperty("java.vendor", "Eclipse Adoptium");
    System.setProperty("java.vendor.version", "Temurin-11.0.19+7");

    String runtimeInfo = GaxProperties.getRuntimeVersion();
    assertEquals("11.0.19__Eclipse-Adoptium__Temurin-11.0.19-7", runtimeInfo);
  }

  @Test
  public void testGetJavaRuntimeInfo_coretto() {
    // This case is one of major Java vendors
    System.setProperty("java.version", "11.0.19");
    System.setProperty("java.vendor", "Amazon.com Inc.");
    System.setProperty("java.vendor.version", "Corretto-11.0.19.7.1");

    String runtimeInfo = GaxProperties.getRuntimeVersion();
    assertEquals("11.0.19__Amazon.com-Inc.__Corretto-11.0.19.7.1", runtimeInfo);
  }

  @Test
  public void testGetJavaRuntimeInfo_specialCharacters() {
    // testing for unsupported characters and spaces
    System.setProperty("java.version", "20%^.&0~.1#45`*");
    System.setProperty("java.vendor", "A^!@#$*B()[]{} C ~%& D-E ?");
    System.setProperty("java.vendor.version", "1!@%$@#.AB!346.9^");

    String runtimeInfo = GaxProperties.getRuntimeVersion();
    assertEquals("20--.-0-.1-45--__A------B-------C-----D-E--__1------.AB-346.9-", runtimeInfo);
  }

  @Test
  public void testGetJavaRuntimeInfo_nullVendorVersion() {
    // testing for null java.vendor.version
    System.setProperty("java.version", "20.0.1");
    System.setProperty("java.vendor", "Oracle");
    System.clearProperty("java.vendor.version");

    String runtimeInfo = GaxProperties.getRuntimeVersion();
    assertEquals("20.0.1__Oracle", runtimeInfo);
  }

  @Test
  public void testGetJavaRuntimeInfo_nullVendorAndVendorVersion() {
    // testing for null java.vendor and java.vendor.version
    System.setProperty("java.version", "20.0.1");
    System.clearProperty("java.vendor");
    System.clearProperty("java.vendor.version");

    String runtimeInfo = GaxProperties.getRuntimeVersion();
    assertEquals("20.0.1", runtimeInfo);
  }

  @Test
  public void testGetJavaRuntimeInfo_nullJavaVersion() {
    // testing for null java.version
    // We don't expect this case to happen, however we don't want the method to fail when it really
    // happens.

    System.clearProperty("java.version");
    System.setProperty("java.vendor", "oracle");
    System.setProperty("java.vendor.version", "20.0.1");

    String runtimeInfo = GaxProperties.getRuntimeVersion();
    assertEquals("null__oracle__20.0.1", runtimeInfo);
  }
}
