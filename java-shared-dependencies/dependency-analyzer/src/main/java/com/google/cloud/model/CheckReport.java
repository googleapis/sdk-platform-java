package com.google.cloud.model;

import com.google.cloud.exception.HasVulnerabilityException;
import com.google.cloud.exception.NonCompliantLicenseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckReport {

  private final Map<VersionKey, List<Advisory>> advisories;
  private final Map<VersionKey, List<String>> nonCompliantLicenses;
  private final List<LicenseCategory> nonCompliantCategories = List.of(LicenseCategory.Restricted);

  private final static Logger LOGGER = Logger.getLogger(CheckReport.class.getName());

  public CheckReport(List<PackageInfo> result) {
    advisories = getAdvisories(result);
    nonCompliantLicenses = getNonCompliantLicenses(result);
  }

  public void generateReport() throws HasVulnerabilityException, NonCompliantLicenseException {
    if (!advisories.isEmpty()) {
      formatLog(advisories, "Known vulnerabilities in dependencies:");
      throw new HasVulnerabilityException("Found vulnerabilities in check report.");
    }

    if (!nonCompliantLicenses.isEmpty()) {
      formatLog(nonCompliantLicenses, "Known non compliant licenses in dependencies:");
      throw new NonCompliantLicenseException("Found non compliant licenses in check report.");
    }

    LOGGER.log(Level.INFO, "Dependencies have no known vulnerabilities and non compliant licenses");
  }

  private Map<VersionKey, List<Advisory>> getAdvisories(List<PackageInfo> result) {
    Map<VersionKey, List<Advisory>> advisories = new HashMap<>();
    result.forEach(packageInfo -> {
      List<Advisory> adv = packageInfo.getAdvisories();
      if (!adv.isEmpty()) {
        advisories.put(packageInfo.getVersionKey(), packageInfo.getAdvisories());
      }
    });
    return advisories;
  }

  private Map<VersionKey, List<String>> getNonCompliantLicenses(List<PackageInfo> result) {
    Map<VersionKey, List<String>> licenses = new HashMap<>();

    result.forEach(packageInfo -> {
      List<String> nonCompliantLicenses = new ArrayList<>();
      for (String licenseStr : packageInfo.getLicenses()) {
        License license = License.toLicense(licenseStr);
        for (LicenseCategory nonCompliantCategory : nonCompliantCategories) {
          if (license.getCategories().contains(nonCompliantCategory)) {
            nonCompliantLicenses.add(licenseStr);
            break;
          }
        }
      }
      if (!nonCompliantLicenses.isEmpty()) {
        licenses.put(packageInfo.getVersionKey(), nonCompliantLicenses);
      }
    });
    return licenses;
  }

  private <T> void formatLog(Map<VersionKey, List<T>> map, String message) {
    LOGGER.log(Level.SEVERE, message);
    map.forEach((versionKey, list) -> {
      LOGGER.log(Level.SEVERE, separator(versionKey));
      list.forEach(item -> LOGGER.log(Level.SEVERE, item.toString()));
      LOGGER.log(Level.SEVERE, separator(versionKey));
    });
  }

  private String separator(VersionKey versionKey) {
    return String.format("====================== %s ======================",
        versionKey.toString());
  }
}
