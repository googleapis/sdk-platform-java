package com.google.cloud;

import com.google.cloud.tools.opensource.dependencies.MavenRepositoryException;
import org.eclipse.aether.resolution.ArtifactDescriptorException;
import org.junit.Test;

public class UnmanagedDependencyCheckTest {
  @Test
  public void test() throws MavenRepositoryException, ArtifactDescriptorException {
    String unManagedDependencies =
        UnmanagedDependencyCheck.outputUnmanagedDependencies(
            "3.18.0", "../../gapic-generator-java/pom.xml");
    System.out.println(unManagedDependencies);
  }
}
