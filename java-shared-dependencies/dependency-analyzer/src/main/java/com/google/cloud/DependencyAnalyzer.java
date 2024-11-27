package com.google.cloud;

import com.google.cloud.external.DepsDevClient;
import com.google.cloud.external.GitHubClient;
import com.google.cloud.model.Advisory;
import com.google.cloud.model.AdvisoryKey;
import com.google.cloud.model.AnalysisResult;
import com.google.cloud.model.License;
import com.google.cloud.model.PackageInfo;
import com.google.cloud.model.ProjectKey;
import com.google.cloud.model.PullRequestStatistics;
import com.google.cloud.model.QueryResult;
import com.google.cloud.model.RelatedProject;
import com.google.cloud.model.ReportResult;
import com.google.cloud.model.Result;
import com.google.cloud.model.Version;
import com.google.cloud.model.VersionKey;
import com.google.cloud.tools.opensource.classpath.ClassPathBuilder;
import com.google.cloud.tools.opensource.classpath.DependencyMediation;
import com.google.cloud.tools.opensource.dependencies.Bom;
import com.google.cloud.tools.opensource.dependencies.MavenRepositoryException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.version.InvalidVersionSpecificationException;

public class DependencyAnalyzer {

  private final DepsDevClient depsDevClient;
  private final GitHubClient gitHubClient;

  public DependencyAnalyzer(DepsDevClient depsDevClient, GitHubClient gitHubClient) {
    this.depsDevClient = depsDevClient;
    this.gitHubClient = gitHubClient;
  }

  public List<AnalysisResult> analyze(String bomPath)
      throws URISyntaxException, IOException, InterruptedException {
    List<AnalysisResult> analysisResults = new ArrayList<>();
    try {
      Set<VersionKey> roots = getManagedDependenciesFromBom(Bom.readBom(Paths.get(bomPath)));
      for (VersionKey versionKey : roots) {
        if (versionKey.isSnapshot()) {
          continue;
        }
        analysisResults.add(AnalysisResult.of(getPackageInfoFrom(versionKey)));
      }

    } catch (MavenRepositoryException | InvalidVersionSpecificationException ex) {
      System.out.printf("Caught exception when resolving dependencies from %s.", bomPath);
      ex.printStackTrace();
      System.exit(1);
    }

    return analysisResults;
  }

  private static Set<VersionKey> getManagedDependenciesFromBom(Bom bom)
      throws InvalidVersionSpecificationException {
    Set<VersionKey> res = new HashSet<>();
    new ClassPathBuilder()
        .resolve(bom.getManagedDependencies(), false, DependencyMediation.MAVEN)
        .getClassPath()
        .forEach(
            classPath -> {
              Artifact artifact = classPath.getArtifact();
              String pkg = String.format("%s:%s", artifact.getGroupId(), artifact.getArtifactId());
              res.add(VersionKey.from("MAVEN", pkg, artifact.getVersion()));
            });

    return res;
  }

  private List<PackageInfo> getPackageInfoFrom(VersionKey root)
      throws URISyntaxException, IOException, InterruptedException {
    Set<VersionKey> seenPackage = new HashSet<>();
    seenPackage.add(root);
    Queue<VersionKey> queue = new ArrayDeque<>();
    queue.offer(root);
    List<VersionKey> dependencies = new ArrayList<>();
    while (!queue.isEmpty()) {
      VersionKey versionKey = queue.poll();
      dependencies.add(versionKey);
      if (versionKey.toString().equals("org.graalvm.sdk:nativeimage:24.1.1")) {
        continue;
      }
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
      List<License> licenses = new ArrayList<>();
      List<Advisory> advisories = new ArrayList<>();
      Optional<PullRequestStatistics> statistics = Optional.empty();
      for (Result res : packageInfo.results()) {
        Version version = res.version();
        for (String license : version.licenses()) {
          licenses.add(License.toLicense(license));
        }
        for (AdvisoryKey advisoryKey : version.advisoryKeys()) {
          advisories.add(depsDevClient.getAdvisory(advisoryKey.id()));
        }

        for (RelatedProject project : version.relatedProjects()) {
          ProjectKey projectKey = project.projectKey();
          if (!projectKey.isGitHubProject()) {
            continue;
          }
          statistics = Optional.of(gitHubClient.listMonthlyPullRequestStatusOf(
              projectKey.organization(), projectKey.repo()));
        }
      }
      result.add(new PackageInfo(versionKey, licenses, advisories, statistics));
    }

    return result;
  }

  /**
   * For a given package, checks the package information via <a
   * href="https://deps.dev/">deps.dev</a> and reports error if risk information is found.
   * <p>
   * The types of risk checked by the dependency analyzer are:
   * <p>1. Non-compliant licenses</p>
   * <p>2. Security vulnerability</p>
   * The analyzer will report all types of risk before existing with error.
   *
   * @param args a package. A string array with three elements.
   * <p>The 1st element is the package management system, e.g., maven, npm, etc.</p>
   * <p>The 2nd element is the package name.</p>
   * <p>The 3rd element is the package version.</p>
   * @throws IllegalArgumentException if the format of package name is incorrect according to the
   * package management system.
   */
  public static void main(String[] args) throws IllegalArgumentException {
    DependencyAnalyzer dependencyAnalyzer = new DependencyAnalyzer(
        new DepsDevClient(HttpClient.newHttpClient()),
        new GitHubClient(HttpClient.newHttpClient()));
    List<AnalysisResult> analysisResults = null;
    try {
      analysisResults = dependencyAnalyzer.analyze("../pom.xml");
    } catch (URISyntaxException | IOException | InterruptedException ex) {
      System.out.println(
          "Caught exception when fetching package information from https://deps.dev/");
      ex.printStackTrace();
      System.exit(1);
    }

    System.out.println("Please copy and paste the package information below to your ticket.\n");
    analysisResults.forEach(analysisResult -> {
      System.out.println(analysisResult.toString());
      System.out.println(analysisResult.getAnalysisResult());
    });
  }
}
