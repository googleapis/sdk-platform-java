package com.google.cloud.model;

import static com.google.cloud.external.DepsDevClient.QUERY_URL_BASE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalysisResult {

  private final VersionKey root;
  private final List<PackageInfo> packageInfos;
  private final Map<VersionKey, List<Advisory>> advisories;
  private final Map<VersionKey, List<String>> nonCompliantLicenses;

  private final static Logger LOGGER = Logger.getLogger(AnalysisResult.class.getName());

  private AnalysisResult(VersionKey root, List<PackageInfo> result) {
    this.root = root;
    this.packageInfos = result;
    this.advisories = getAdvisories(result);
    this.nonCompliantLicenses = getNonCompliantLicenses(result);
  }

  public static AnalysisResult of(VersionKey root, List<PackageInfo> result) {
    return new AnalysisResult(root, result);
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
    LOGGER.log(Level.INFO, "Generate package information report...");
    System.out.println(packageInfoReport());

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
    Set<LicenseCategory> compliantCategories = LicenseCategory.compliantCategories();

    result.forEach(packageInfo -> {
      List<String> nonCompliantLicenses = new ArrayList<>();
      for (String licenseStr : packageInfo.licenses()) {
        License license = License.toLicense(licenseStr);
        // fiter out all compliant categories, the remaining ones are non-compliant.
        List<LicenseCategory> nonCompliantCategories = license
            .getCategories()
            .stream()
            .filter(category -> !compliantCategories.contains(category))
            .toList();
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
    String relation = versionKey.equals(root) ? "self" : "dependency";
    return String.format("====================== %s, %s of %s ======================",
        versionKey, relation, root);
  }

  private String endSeparator() {
    return "===========================================================";
  }

  private String packageInfoReport() {
    StringBuilder builder = new StringBuilder();
    builder.append("Please copy and paste the package information below to your ticket.\n");
    builder.append("\n\n");
    appendToReport(builder, packageInfos.get(0));

    builder.append("## Dependencies:\n");
    if (packageInfos.size() <= 1) {
      builder.append("None");
    } else {
      for (int i = 1; i < packageInfos.size(); i++) {
        appendToReport(builder, packageInfos.get(i));
      }
    }
    builder.append("\n\n");

    return builder.toString();
  }

  private void appendToReport(StringBuilder builder, PackageInfo packageInfo) {
    VersionKey versionKey = packageInfo.versionKey();
    // generate the report using Markdown format.
    builder.append(String.format("### %s\n", versionKey));
    builder.append(String.format("Licenses: %s\n", packageInfo.licenses()));
    builder.append(
        String.format("Vulnerabilities: None\n. Checked in [deps.dev query](%s)\n",
            getQueryUrl(
                versionKey.pkgManagement().toString(),
                versionKey.name(),
                versionKey.version())));
    builder.append("\n");
  }

  private String getQueryUrl(String system, String name, String version) {
    return String.format(QUERY_URL_BASE, system, name, version);
  }
}
