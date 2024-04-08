package com.google.cloud.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LicenseTest {
  @Test
  public void testToLicenseSuccess() {
    String license = "Apache-2.0";
    assertEquals(License.APACHE_2_0, License.toLicense(license));
  }
}
