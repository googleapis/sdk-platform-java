package com.google.cloud;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.cloud.exception.HasVulnerabilityException;
import com.google.cloud.exception.NonCompliantLicenseException;
import com.google.cloud.external.DepsDevClient;
import com.google.cloud.model.Advisory;
import com.google.cloud.model.AdvisoryKey;
import com.google.cloud.model.CheckReport;
import com.google.cloud.model.MavenCoordinate;
import com.google.cloud.model.PackageInfo;
import com.google.cloud.model.QueryResult;
import com.google.cloud.model.Result;
import com.google.cloud.model.Version;
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

  public CheckReport check(String groupId, String artifactId, String artifactVersion)
      throws URISyntaxException, IOException, InterruptedException {
    MavenCoordinate initial = new MavenCoordinate(groupId, artifactId, artifactVersion);
    Set<MavenCoordinate> seenCoordinate = new HashSet<>();
    seenCoordinate.add(initial);
    Queue<MavenCoordinate> queue = new ArrayDeque<>();
    queue.offer(initial);
    List<MavenCoordinate> dependencies = new ArrayList<>();
    while (!queue.isEmpty()) {
      MavenCoordinate coordinate = queue.poll();
      dependencies.add(coordinate);
      List<MavenCoordinate> directDependencies = depsDevClient.getDirectDependencies(coordinate);
      // only add unseen dependencies to the queue.
      directDependencies
          .stream()
          .filter(seenCoordinate::add)
          .forEach(queue::offer);
    }

    List<PackageInfo> result = new ArrayList<>();
    for (MavenCoordinate coordinate : dependencies) {
      QueryResult packageInfo = depsDevClient.getQueryResult(coordinate);
      List<String> licenses = new ArrayList<>();
      List<Advisory> advisories = new ArrayList<>();
      for (Result res : packageInfo.getResults()) {
        Version version = res.getVersion();
        licenses.addAll(version.getLicenses());
        for (AdvisoryKey advisoryKey : version.getAdvisoryKeys()) {
          advisories.add(depsDevClient.getAdvisory(advisoryKey.getId()));
        }
      }

      result.add(new PackageInfo(coordinate, licenses, advisories));
    }

    return new CheckReport(result);
  }

  public static void main(String[] args)
      throws URISyntaxException, IOException, InterruptedException {
    checkArgument(args.length == 3,
        "The length of the inputs should be 3.\n" +
                   "The 1st input should be the group ID.\n" +
                   "The 2nd input should be the artifact ID.\n" +
                   "The 3rd input should be the version.\n"
    );
    String groupId = args[0];
    String artifactId = args[1];
    String version = args[2];
    PackageInfoCheck packageInfoCheck = new PackageInfoCheck(
        new DepsDevClient(HttpClient.newHttpClient(), new Gson()));
    CheckReport checkReport = packageInfoCheck.check(groupId, artifactId, version);
    try {
      checkReport.generateReport();
    } catch (NonCompliantLicenseException | HasVulnerabilityException ex) {
      System.out.println("Caught exception: " + ex);
      System.exit(1);
    }
  }
}
