package com.google.cloud;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.cloud.exception.HasVulnerabilityException;
import com.google.cloud.exception.NonCompliantLicenseException;
import com.google.cloud.external.DepsDevClient;
import com.google.cloud.model.Advisory;
import com.google.cloud.model.AdvisoryKey;
import com.google.cloud.model.CheckReport;
import com.google.cloud.model.PackageInfo;
import com.google.cloud.model.QueryResult;
import com.google.cloud.model.Result;
import com.google.cloud.model.Version;
import com.google.cloud.model.VersionKey;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class PackageInfoCheck {

  private final DepsDevClient depsDevClient;

  public PackageInfoCheck(DepsDevClient depsDevClient) {
    this.depsDevClient = depsDevClient;
  }

  public CheckReport check(String system, String dependencyName, String dependencyVersion)
      throws URISyntaxException, IOException, InterruptedException {
    VersionKey initial = new VersionKey(system, dependencyName, dependencyVersion);
    Set<VersionKey> seenDependency = new HashSet<>();
    seenDependency.add(initial);
    Queue<VersionKey> queue = new ArrayDeque<>();
    queue.offer(initial);
    List<VersionKey> dependencies = new ArrayList<>();
    while (!queue.isEmpty()) {
      VersionKey versionKey = queue.poll();
      dependencies.add(versionKey);
      List<VersionKey> directDependencies = depsDevClient.getDirectDependencies(versionKey);
      // only add unseen dependencies to the queue.
      directDependencies
          .stream()
          .filter(seenDependency::add)
          .forEach(queue::offer);
    }

    List<PackageInfo> result = new ArrayList<>();
    for (VersionKey versionKey : dependencies) {
      QueryResult packageInfo = depsDevClient.getQueryResult(versionKey);
      List<String> licenses = new ArrayList<>();
      List<Advisory> advisories = new ArrayList<>();
      for (Result res : packageInfo.getResults()) {
        Version version = res.getVersion();
        licenses.addAll(version.getLicenses());
        for (AdvisoryKey advisoryKey : version.getAdvisoryKeys()) {
          advisories.add(depsDevClient.getAdvisory(advisoryKey.getId()));
        }
      }

      result.add(new PackageInfo(versionKey, licenses, advisories));
    }

    return new CheckReport(result);
  }

  public static void main(String[] args)
      throws URISyntaxException, IOException, InterruptedException {
    checkArgument(args.length == 3,
        "The length of the inputs should be 3.\n" +
            "The 1st input should be the package management system.\n" +
            "The 2nd input should be the dependency name.\n" +
            "The 3rd input should be the version.\n"
    );

    PackageInfoCheck packageInfoCheck = new PackageInfoCheck(
        new DepsDevClient(HttpClient.newHttpClient(), new Gson()));
    CheckReport checkReport = packageInfoCheck.check(args[0], args[1], args[2]);
    try {
      checkReport.generateReport();
    } catch (NonCompliantLicenseException | HasVulnerabilityException ex) {
      System.out.println("Caught exception: " + ex);
      System.exit(1);
    }
  }
}
