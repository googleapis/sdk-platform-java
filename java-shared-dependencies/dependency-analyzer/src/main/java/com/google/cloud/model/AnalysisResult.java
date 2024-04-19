package com.google.cloud.model;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AnalysisResult {

  private final VersionKey root;
  private final Map<VersionKey, List<Advisory>> advisories;
  private final Map<VersionKey, List<String>> nonCompliantLicenses;
  private final ImmutableSet<LicenseCategory> compliantCategories = ImmutableSet.of(
      LicenseCategory.NOTICE);

  private final static Logger LOGGER = Logger.getLogger(AnalysisResult.class.getName());

  public AnalysisResult(VersionKey root, List<PackageInfo> result) {
    this.root = root;
    advisories = getAdvisories(result);
    nonCompliantLicenses = getNonCompliantLicenses(result);
  }

  public ReportResult generateReport() {
    if (!advisories.isEmpty()) {
      formatLog(root, advisories, "New security vulnerability found in dependencies:");
    }

    if (!nonCompliantLicenses.isEmpty()) {
      formatLog(root, nonCompliantLicenses, "Non-compliant license found in dependencies:");
    }

    if (!advisories.isEmpty() || !nonCompliantLicenses.isEmpty()) {
      LOGGER.log(Level.SEVERE, String.format("Found dependency risk in %s", root));
      return ReportResult.FAIL;
    }

    LOGGER.log(Level.INFO,
        String.format("%s have no known vulnerabilities and non compliant licenses", root));
    return ReportResult.PASS;
  }

  private Map<VersionKey, List<Advisory>> getAdvisories(List<PackageInfo> result) {
    Map<VersionKey, List<Advisory>> advisories = new HashMap<>();
    result.forEach(packageInfo -> {
      List<Advisory> adv = packageInfo.advisories();
      if (!adv.isEmpty()) {
        advisories.put(packageInfo.versionKey(), packageInfo.advisories());
      }
    });
    return advisories;
  }

  private Map<VersionKey, List<String>> getNonCompliantLicenses(List<PackageInfo> result) {
    Map<VersionKey, List<String>> licenses = new HashMap<>();

    result.forEach(packageInfo -> {
      List<String> nonCompliantLicenses = new ArrayList<>();
      for (String licenseStr : packageInfo.licenses()) {
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
        licenses.put(packageInfo.versionKey(), nonCompliantLicenses);
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
