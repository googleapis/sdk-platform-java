package com.google.cloud.model;

import static com.google.cloud.model.LicenseCategory.NOTICE;
import static com.google.cloud.model.LicenseCategory.Restricted;

import java.util.Set;


public enum License {
  BCL(Set.of(Restricted, NOTICE)),
  GL2PS(Set.of(Restricted, NOTICE));

  private final Set<LicenseCategory> categories;

  License(Set<LicenseCategory> categories) {
    this.categories = categories;
  }

  public Set<LicenseCategory> getCategories() {
    return categories;
  }
}
