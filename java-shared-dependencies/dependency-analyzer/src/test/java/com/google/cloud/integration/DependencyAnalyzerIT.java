package com.google.cloud.integration;

import com.google.cloud.DependencyAnalyzer;
import com.google.cloud.external.DepsDevClient;
import com.google.cloud.model.AnalysisResult;
import com.google.cloud.model.ReportResult;
import java.net.http.HttpClient;
import org.junit.Test;

public class DependencyAnalyzerIT {
  @Test
  public void testAnalyzeFromJavaSharedDependenciesBom() {
    DependencyAnalyzer dependencyAnalyzer = new DependencyAnalyzer(
        new DepsDevClient(HttpClient.newHttpClient()));
    String sharedDependenciesBom = "../pom.xml";
    AnalysisResult analyzeReport = dependencyAnalyzer.analyze(sharedDependenciesBom);
    System.out.println(analyzeReport.toString());
    ReportResult result = analyzeReport.getAnalysisResult();
    System.out.println(result);
    if (result.equals(ReportResult.FAIL)) {
      System.out.println(
          "Please refer to go/cloud-java-rotations#security-advisories-monitoring for further actions");
      System.exit(1);
    }
  }
}
