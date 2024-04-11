package com.google.cloud.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class VersionKeyTest {

  @Test
  public void testVersionKeyWithIncorrectNameThrowsException() {
    assertThrows("artifact is an incorrect package name in MAVEN package management system", IllegalArgumentException.class,
        () -> new VersionKey("maven", "artifact", "1.2.3"));
  }

  @Test
  public void testVersionKeyWithCorrectNameSucceeds() throws IllegalArgumentException {
    VersionKey versionKey = new VersionKey("maven", "group:artifact", "1.2.3");
    assertEquals(PkgManagement.MAVEN, versionKey.getSystem());
    assertEquals("group:artifact", versionKey.getName());
    assertEquals("1.2.3", versionKey.getVersion());
  }
}
