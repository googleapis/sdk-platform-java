package com.google.cloud.model;

import static com.google.cloud.model.LicenseCategory.NOTICE;
import static com.google.cloud.model.LicenseCategory.Restricted;

import java.util.Set;


public enum License {
  APACHE_2_0(Set.of(NOTICE)),
  BCL(Set.of(Restricted, NOTICE)),
  GL2PS(Set.of(Restricted, NOTICE));

  private final Set<LicenseCategory> categories;

  License(Set<LicenseCategory> categories) {
    this.categories = categories;
  }

  public static License toLicense(String licenseStr) {
    String value = licenseStr
        .toUpperCase()
        .replace('-', '_')
        .replace('.', '_');
    return License.valueOf(value);
  }

  public Set<LicenseCategory> getCategories() {
    return categories;
  }
}
