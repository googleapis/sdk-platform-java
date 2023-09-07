/*
 * Copyright 2017 Google LLC
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

import static org.graalvm.nativeimage.ImageInfo.PROPERTY_IMAGE_CODE_KEY;
import static org.graalvm.nativeimage.ImageInfo.PROPERTY_IMAGE_CODE_VALUE_RUNTIME;

import com.google.api.core.InternalApi;
import com.google.common.base.Strings;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Provides properties of the GAX library. */
@InternalApi
public class GaxProperties {

  private static final String DEFAULT_VERSION = "";
  private static final String GAX_VERSION = getLibraryVersion(GaxProperties.class, "version.gax");
  private static final String JAVA_VERSION = getRuntimeVersion();

  private GaxProperties() {}

  /** Returns the version of the library that the {@code libraryClass} belongs to */
  public static String getLibraryVersion(Class<?> libraryClass) {
    String version = libraryClass.getPackage().getImplementationVersion();
    return version != null ? version : DEFAULT_VERSION;
  }

  /**
   * Returns the version of the library that the {@code libraryClass} belongs to, or a property
   * value in dependencies.properties resource file instead, if the version was not found. The
   * method is doing I/O operations and is potentially inefficient, the values returned by this
   * method are expected to be cached.
   */
  public static String getLibraryVersion(Class<?> libraryClass, String propertyName) {
    String version = null;
    // Always read GaxProperties' version from the properties file.
    if (!libraryClass.equals(GaxProperties.class)) {
      version = getLibraryVersion(libraryClass);
      if (!DEFAULT_VERSION.equals(version)) {
        return version;
      }
    }

    try (InputStream in = libraryClass.getResourceAsStream("/dependencies.properties")) {
      if (in != null) {
        Properties props = new Properties();
        props.load(in);
        version = props.getProperty(propertyName);
      }
    } catch (IOException e) {
      // ignore
    }

    return version != null ? version : DEFAULT_VERSION;
  }

  /** Returns the version of the running JVM */
  public static String getJavaVersion() {
    // When running the application as a native image, append `-graalvm` to the
    // version.
    String imageCode = System.getProperty(PROPERTY_IMAGE_CODE_KEY);
    if (imageCode != null && imageCode.equals(PROPERTY_IMAGE_CODE_VALUE_RUNTIME)) {
      return System.getProperty("java.version") + "-graalvm";
    }
    return JAVA_VERSION;
  }

  /** Returns the current version of GAX. */
  public static String getGaxVersion() {
    return GAX_VERSION;
  }

  /** Returns the current runtime version */
  private static String getRuntimeVersion() {
    String javaRuntimeInformation = System.getProperty("java.version");

    // append the vendor information to the java-version if vendor is present.
    String vendor = System.getProperty("java.vendor");
    if (!Strings.isNullOrEmpty(vendor)) {
      javaRuntimeInformation = String.format("%s__%s", javaRuntimeInformation, vendor);
    }

    // appends the vendor version information to the java-version if vendor version is present.
    String vendorVersion = System.getProperty("java.vendor.version");
    if (!Strings.isNullOrEmpty(vendorVersion)) {
      javaRuntimeInformation = String.format("%s__%s", javaRuntimeInformation, vendorVersion);
    }

    // replacing all characters that are not numbers, letters, underscores, periods, or backslashes
    // with hyphens.
    return javaRuntimeInformation.replaceAll("[^0-9a-zA-Z_\\\\.]", "-");
  }
}
