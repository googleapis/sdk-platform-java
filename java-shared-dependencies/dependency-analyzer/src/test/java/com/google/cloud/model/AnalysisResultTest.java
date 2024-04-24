package com.google.cloud.model;


import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

public class AnalysisResultTest {

  @Test
  public void testGenerateReportWithAdvisoriesThrowsException()
      throws IllegalArgumentException {
    VersionKey root = VersionKey.from("maven", "com.example:artifact", "1.2.3");
    List<PackageInfo> results = List.of(
        new PackageInfo(
            root,
            List.of(),
            List.of(new Advisory(
                new AdvisoryKey("GHSA-2qrg-x229-3v8q"),
                "https://osv.dev/vulnerability/GHSA-2qrg-x229-3v8q",
                "Deserialization of Untrusted Data in Log4j",
                new String[]{"CVE-2019-17571"},
                9.8,
                "CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H"
            ))
        )
    );
    ReportResult result = AnalysisResult.of(root, results).getAnalysisResult();
    assertEquals(ReportResult.FAIL, result);
  }

  @Test
  public void testGenerateReportWithNonCompliantLicenseThrowsException()
      throws IllegalArgumentException {
    VersionKey root = VersionKey.from("maven", "com.example:artifact", "1.2.3");
    List<PackageInfo> results = List.of(
        new PackageInfo(
            root,
            List.of("BCL"),
            List.of()
        )
    );
    ReportResult result = AnalysisResult.of(root, results).getAnalysisResult();
    assertEquals(ReportResult.FAIL, result);
  }

  @Test
  public void testGenerateReportWithoutRiskSucceeds()
      throws IllegalArgumentException {
    VersionKey root = VersionKey.from("maven", "com.example:artifact", "1.2.3");
    List<PackageInfo> results = List.of(
        new PackageInfo(
            root,
            List.of("Apache-2.0"),
            List.of()
        )
    );
    ReportResult result = AnalysisResult.of(root, results).getAnalysisResult();
    assertEquals(ReportResult.PASS, result);
  }

  @Test
  public void testToStringReturnsCorrectContents() {
    VersionKey root = VersionKey.from("maven", "com.example:artifact", "1.2.3");
    List<PackageInfo> results = List.of(
        new PackageInfo(
            root,
            List.of("Apache-2.0"),
            List.of()
        ),
        new PackageInfo(
            VersionKey.from("maven", "com.example:dependency", "4.5.6"),
            List.of("Apache-2.0", "MIT"),
            List.of()
        ),
        new PackageInfo(
            VersionKey.from("maven", "com.example:nested-dependency", "2.3.1"),
            List.of("Apache-2.0", "MIT"),
            List.of()
        )
    );
    assertEquals("""
        Please copy and paste the package information below to your ticket.
                
        ## Package information of com.example:artifact:1.2.3
        Licenses: [Apache-2.0]
        Vulnerabilities: [].
        Checked in [com.example:artifact (1.2.3)](https://api.deps.dev/v3/query?versionKey.system=MAVEN&versionKey.name=com.example:artifact&versionKey.version=1.2.3)
                
        ## Dependencies:
        ### Package information of com.example:dependency:4.5.6
        Licenses: [Apache-2.0, MIT]
        Vulnerabilities: [].
        Checked in [com.example:dependency (4.5.6)](https://api.deps.dev/v3/query?versionKey.system=MAVEN&versionKey.name=com.example:dependency&versionKey.version=4.5.6)
                
        ### Package information of com.example:nested-dependency:2.3.1
        Licenses: [Apache-2.0, MIT]
        Vulnerabilities: [].
        Checked in [com.example:nested-dependency (2.3.1)](https://api.deps.dev/v3/query?versionKey.system=MAVEN&versionKey.name=com.example:nested-dependency&versionKey.version=2.3.1)
                
                
        """, AnalysisResult.of(root, results).toString());
  }
}
