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

  private final Map<MavenCoordinate, List<Advisory>> advisories;
  private final Map<MavenCoordinate, List<String>> nonCompliantLicenses;
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

  private Map<MavenCoordinate, List<Advisory>> getAdvisories(List<PackageInfo> result) {
    Map<MavenCoordinate, List<Advisory>> advisories = new HashMap<>();
    result.forEach(packageInfo -> {
      List<Advisory> adv = packageInfo.getAdvisories();
      if (!adv.isEmpty()) {
        advisories.put(packageInfo.getMavenCoordinate(), packageInfo.getAdvisories());
      }
    });
    return advisories;
  }

  private Map<MavenCoordinate, List<String>> getNonCompliantLicenses(List<PackageInfo> result) {
    Map<MavenCoordinate, List<String>> licenses = new HashMap<>();

    result.forEach(packageInfo -> {
      List<String> nonCompliantLicenses = new ArrayList<>();
      for (String licenseStr : packageInfo.getLicenses()) {
        License license = License.valueOf(licenseStr);
        for (LicenseCategory nonCompliantCategory : nonCompliantCategories) {
          if (license.getCategories().contains(nonCompliantCategory)) {
            nonCompliantLicenses.add(licenseStr);
            break;
          }
        }
      }
      if (!nonCompliantLicenses.isEmpty()) {
        licenses.put(packageInfo.getMavenCoordinate(), nonCompliantLicenses);
      }
    });
    return licenses;
  }

  private <T> void formatLog(Map<MavenCoordinate, List<T>> map, String message) {
    LOGGER.log(Level.SEVERE, message);
    map.forEach((mavenCoordinate, list) -> {
      LOGGER.log(Level.SEVERE, separator(mavenCoordinate));
      list.forEach(item -> LOGGER.log(Level.SEVERE, item.toString()));
      LOGGER.log(Level.SEVERE, separator(mavenCoordinate));
    });
  }

  private String separator(MavenCoordinate mavenCoordinate) {
    return String.format("====================== %s ======================",
        mavenCoordinate.toString());
  }
}
