package com.google.cloud.model;

import static com.google.cloud.model.LicenseCategory.NOTICE;
import static com.google.cloud.model.LicenseCategory.PERMISSIVE;
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
  APACHE_2_0("Apache-2.0", Set.of(NOTICE)),
  BCL("BCL", Set.of(RESTRICTED, NOTICE)),
  BSD_2_CLAUSE("BSD-2-Clause", Set.of(NOTICE)),
  BSD_3_CLAUSE("BSD-3-Clause", Set.of(NOTICE)),
  GL2PS("GL2PS", Set.of(RESTRICTED, NOTICE)),
  GPL_2_0_WITH_CLASSPATH_EXCEPTION("GPL-2.0-with-classpath-exception", Set.of(PERMISSIVE)),
  MIT("MIT", Set.of(NOTICE)),
  NOT_RECOGNIZED("Not-Recognized", Set.of()),
  UPL_1_0("UPL-1.0", Set.of(NOTICE));

  private final static Logger LOGGER = Logger.getLogger(License.class.getName());

  private final String licenseStr;

  private final Set<LicenseCategory> categories;

  License(String licenseStr, Set<LicenseCategory> categories) {
    this.licenseStr = licenseStr;
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
      LOGGER.log(Level.WARNING,
          String.format("%s is not recognized as any of the known license.", licenseStr));
      return NOT_RECOGNIZED;
    }
  }

  public boolean isCompliant() {
    if (categories.isEmpty()) {
      return false;
    }
    Set<LicenseCategory> complaintLicenseCategories = LicenseCategory.compliantCategories();
    for (LicenseCategory category : categories) {
      if (!complaintLicenseCategories.contains(category)) {
        return false;
      }
    }

    return true;
  }

  public Set<LicenseCategory> getCategories() {
    return ImmutableSet.copyOf(categories);
  }

  @Override
  public String toString() {
    String nonCompliantPrefix = "%s (Not Google-compliant!)";
    String compliantPrefix = "%s (Google-compliant)";

    return isCompliant()
        ? String.format(compliantPrefix, this.licenseStr)
        : String.format(nonCompliantPrefix, this.licenseStr);
  }
}
