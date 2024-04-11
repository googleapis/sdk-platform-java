package com.google.cloud.model;

import static com.google.cloud.model.LicenseCategory.NOTICE;
import static com.google.cloud.model.LicenseCategory.RESTRICTED;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Third-party licenses associated with an open-source software.
 * <p>
 * For more information, please refer to go/thirdpartylicenses.
 */
public enum License {
  APACHE_2_0(Set.of(NOTICE)),
  BCL(Set.of(RESTRICTED, NOTICE)),
  GL2PS(Set.of(RESTRICTED, NOTICE)),
  MIT(Set.of(NOTICE)),
  NOT_RECOGNIZED(Set.of());

  private final static Logger LOGGER = Logger.getLogger(License.class.getName());

  private final Set<LicenseCategory> categories;

  License(Set<LicenseCategory> categories) {
    this.categories = categories;
  }

  public static License toLicense(String licenseStr) {
    String value = licenseStr
        .toUpperCase()
        .replace('-', '_')
        .replace('.', '_');
    try {
      return License.valueOf(value);
    } catch (IllegalArgumentException exception) {
      LOGGER.log(Level.WARNING, String.format("%s is not recognized as any of the known license.", licenseStr));
      return NOT_RECOGNIZED;
    }
  }

  public Set<LicenseCategory> getCategories() {
    return ImmutableSet.copyOf(categories);
  }
}
