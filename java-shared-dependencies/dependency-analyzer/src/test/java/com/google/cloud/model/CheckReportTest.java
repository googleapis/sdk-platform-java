package com.google.cloud.model;


import static org.junit.Assert.assertThrows;

import com.google.cloud.exception.DependencyRiskException;
import java.util.List;
import org.junit.Test;

public class CheckReportTest {

  @Test
  public void testGenerateReportWithAdvisoriesThrowsException()
      throws IllegalArgumentException {
    VersionKey root = new VersionKey("maven", "com.example:artifact", "1.2.3");
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
    CheckReport report = new CheckReport(root, results);
    assertThrows("Found vulnerabilities in check report.", DependencyRiskException.class, report::generateReport);
  }

  @Test
  public void testGenerateReportWithNonCompliantLicenseThrowsException()
      throws IllegalArgumentException {
    VersionKey root = new VersionKey("maven", "com.example:artifact", "1.2.3");
    List<PackageInfo> results = List.of(
        new PackageInfo(
            root,
            List.of("BCL"),
            List.of()
        )
    );
    CheckReport report = new CheckReport(root, results);
    assertThrows("Found non compliant licenses in check report.", DependencyRiskException.class, report::generateReport);
  }
}
