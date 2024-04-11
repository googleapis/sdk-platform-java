package com.google.cloud.model;

import com.google.cloud.exception.DependencyRiskException;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CheckReport {

  private final VersionKey root;
  private final Map<VersionKey, List<Advisory>> advisories;
  private final Map<VersionKey, List<String>> nonCompliantLicenses;
  private final ImmutableSet<LicenseCategory> compliantCategories = ImmutableSet.of(LicenseCategory.NOTICE);

  private final static Logger LOGGER = Logger.getLogger(CheckReport.class.getName());

  public CheckReport(VersionKey root, List<PackageInfo> result) {
    this.root = root;
    advisories = getAdvisories(result);
    nonCompliantLicenses = getNonCompliantLicenses(result);
  }

  public void generateReport()
      throws DependencyRiskException {
    if (!advisories.isEmpty()) {
      formatLog(root, advisories, "New security vulnerability found in dependencies:");
    }

    if (!nonCompliantLicenses.isEmpty()) {
      formatLog(root, nonCompliantLicenses, "Non-compliant license changed in dependencies:");
    }

    if (!advisories.isEmpty() || !nonCompliantLicenses.isEmpty()) {
      throw new DependencyRiskException(String.format("Found dependency risk in %s", root));
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
        // fiter out all compliant categories, the remaining ones are non-compliant.
        List<LicenseCategory> nonCompliantCategories = license
            .getCategories()
            .stream()
            .filter(category -> !compliantCategories.contains(category))
            .collect(Collectors.toList());
        if (!nonCompliantCategories.isEmpty()) {
          nonCompliantLicenses.add(licenseStr);
        }
      }
      if (!nonCompliantLicenses.isEmpty()) {
        licenses.put(packageInfo.getVersionKey(), nonCompliantLicenses);
      }
    });
    return licenses;
  }

  private <T> void formatLog(VersionKey root, Map<VersionKey, List<T>> map, String message) {
    LOGGER.log(Level.SEVERE, message);
    map.forEach((versionKey, list) -> {
      LOGGER.log(Level.SEVERE, beginSeparator(versionKey, root));
      list.forEach(item -> LOGGER.log(Level.SEVERE, item.toString()));
      LOGGER.log(Level.SEVERE, endSeparator());
    });
  }

  private String beginSeparator(VersionKey versionKey, VersionKey root) {
    return String.format("====================== %s, dependency of %s ======================",
        versionKey.toString(), root.toString());
  }

  private String endSeparator() {
    return "===========================================================";
  }
}
