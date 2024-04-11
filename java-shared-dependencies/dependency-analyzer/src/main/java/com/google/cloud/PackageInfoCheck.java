package com.google.cloud;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.cloud.exception.DependencyRiskException;
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

  public CheckReport check(String system, String packageName, String packageVersion)
      throws URISyntaxException, IOException, InterruptedException, IllegalArgumentException {
    VersionKey root = new VersionKey(system, packageName, packageVersion);
    Set<VersionKey> seenPackage = new HashSet<>();
    seenPackage.add(root);
    Queue<VersionKey> queue = new ArrayDeque<>();
    queue.offer(root);
    List<VersionKey> dependencies = new ArrayList<>();
    while (!queue.isEmpty()) {
      VersionKey versionKey = queue.poll();
      dependencies.add(versionKey);
      List<VersionKey> directDependencies = depsDevClient.getDirectDependencies(versionKey);
      // only add unseen dependencies to the queue.
      directDependencies
          .stream()
          .filter(seenPackage::add)
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

    return new CheckReport(root, result);
  }

  /**
   * For a given package, checks the package information via <a href="https://deps.dev/">deps.dev</a>
   * and reports error if risk information is found.
   * <p>
   * The types of risk checked by the dependency analyzer are:
   * <p>1. Non-compliant licenses</p>
   * <p>2. Security vulnerability</p>
   * The analyzer will report all types of risk before existing with error.
   * @param args a package. A string array with three elements.
   * <p>The 1st element is the package management system, e.g., maven, npm, etc.</p>
   * <p>The 2nd element is the package name.</p>
   * <p>The 3rd element is the package version.</p>
   * @throws IllegalArgumentException if the format of package name is incorrect according to the
   * package management system.
   * @throws DependencyRiskException if any risk information is found affecting the given package.
   */
  public static void main(String[] args) throws IllegalArgumentException, DependencyRiskException {
    checkArgument(args.length == 3,
        "The length of the inputs should be 3.\n" +
            "The 1st input should be the package management system.\n" +
            "The 2nd input should be the package name.\n" +
            "The 3rd input should be the package version.\n"
    );

    PackageInfoCheck packageInfoCheck = new PackageInfoCheck(
        new DepsDevClient(HttpClient.newHttpClient(), new Gson()));
    CheckReport checkReport = null;
    try {
      checkReport = packageInfoCheck.check(args[0], args[1], args[2]);
    } catch (URISyntaxException | IOException | InterruptedException ex) {
      System.out.println("Caught exception when fetching package information from deps.dev: " + ex);
      System.exit(1);
    }

    checkReport.generateReport();
  }
}
