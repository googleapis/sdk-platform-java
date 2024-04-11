package com.google.cloud.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LicenseTest {
  @Test
  public void testToLicenseSucceeds() {
    String license = "Apache-2.0";
    assertEquals(License.APACHE_2_0, License.toLicense(license));
  }

  @Test
  public void testToLicenseReturnsNonRecognizedLicense() {
    String license = "Non-existent-license";
    assertEquals(License.NOT_RECOGNIZED, License.toLicense(license));
  }
}
