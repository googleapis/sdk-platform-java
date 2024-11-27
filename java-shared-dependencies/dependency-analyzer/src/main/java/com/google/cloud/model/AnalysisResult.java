package com.google.cloud.model;

import static com.google.cloud.external.DepsDevClient.QUERY_URL_BASE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalysisResult {

  private final List<PackageInfo> packageInfos;
  private final Map<VersionKey, List<Advisory>> advisories;
  private final Map<VersionKey, List<License>> nonCompliantLicenses;

  private AnalysisResult(List<PackageInfo> result) {
    this.packageInfos = result;
    this.advisories = getAdvisories(result);
    this.nonCompliantLicenses = getNonCompliantLicenses(result);
  }

  public static AnalysisResult of(List<PackageInfo> result) {
    return new AnalysisResult(result);
  }

  public ReportResult getAnalysisResult() {
    if (advisories.isEmpty() && nonCompliantLicenses.isEmpty()) {
      return ReportResult.PASS;
    }

    return ReportResult.FAIL;
  }

  @Override
  public String toString() {
    return packageInfoReport();
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

  private Map<VersionKey, List<License>> getNonCompliantLicenses(List<PackageInfo> result) {
    Map<VersionKey, List<License>> licenses = new HashMap<>();
    Set<LicenseCategory> compliantCategories = LicenseCategory.compliantCategories();

    result.forEach(packageInfo -> {
      List<License> nonCompliantLicenses = new ArrayList<>();
      for (License license : packageInfo.licenses()) {
        // fiter out all compliant categories, the remaining ones are non-compliant.
        List<LicenseCategory> nonCompliantCategories = license
            .getCategories()
            .stream()
            .filter(category -> !compliantCategories.contains(category))
            .toList();
        if (!nonCompliantCategories.isEmpty()) {
          nonCompliantLicenses.add(license);
        }
      }
      if (!nonCompliantLicenses.isEmpty()) {
        licenses.put(packageInfo.versionKey(), nonCompliantLicenses);
      }
    });
    return licenses;
  }

  private String packageInfoReport() {
    StringBuilder builder = new StringBuilder();
    PackageInfo root = packageInfos.get(0);
    String title = String.format("""
        ## Package information of %s
        %s
        """, root.versionKey(), packageInfoSection(root));
    builder.append(title);

    builder.append("## Dependencies\n");
    if (packageInfos.size() == 1) {
      builder.append(String.format("%s has no dependency.", root.versionKey()));
    } else {
      builder.append("==========Non-compliant licenses==========\n");
      for (int i = 1; i < packageInfos.size(); i++) {
        PackageInfo info = packageInfos.get(i);
        boolean hasNonComplaintLicenses = false;
        for (License license : info.licenses()) {
          if (!license.isCompliant()) {
            hasNonComplaintLicenses = true;
            break;
          }
        }
        if (hasNonComplaintLicenses) {
          builder.append(String.format("%s: %s\n", info.versionKey(), info.licenses()));
        }
      }

      builder.append("==========Security vulnerabilities==========\n");
      for (int i = 1; i < packageInfos.size(); i++) {
        PackageInfo info = packageInfos.get(i);
        for (Advisory advisory : info.advisories()) {
          builder.append(String.format("%s: %s\n", info.versionKey(), advisory.url()));
        }
      }
    }
    builder.append("\n");

    return builder.toString();
  }

  private String packageInfoSection(PackageInfo packageInfo) {
    VersionKey versionKey = packageInfo.versionKey();
    // generate the report using Markdown format.
    String packageInfoReport = """
        Licenses: %s
        Vulnerabilities: %s.
        Checked in [%s (%s)](%s)
        """;
    return String.format(packageInfoReport,
        packageInfo.licenses(),
        packageInfo.advisories(),
        versionKey.name(),
        versionKey.version(),
        getQueryUrl(
            versionKey.pkgManagement().toString(),
            versionKey.name(),
            versionKey.version()));
  }

  private String getQueryUrl(String system, String name, String version) {
    return String.format(QUERY_URL_BASE, system, name, version);
  }
}
