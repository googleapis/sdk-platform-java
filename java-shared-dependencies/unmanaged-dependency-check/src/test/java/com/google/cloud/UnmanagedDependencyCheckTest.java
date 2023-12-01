package com.google.cloud;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertTrue;

import com.google.cloud.tools.opensource.dependencies.MavenRepositoryException;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.eclipse.aether.resolution.ArtifactDescriptorException;
import org.junit.Test;

public class UnmanagedDependencyCheckTest {
  @Test
  public void getUnmanagedDependencyFromSamePomTest()
      throws MavenRepositoryException, ArtifactDescriptorException {
    List<String> unManagedDependencies =
        UnmanagedDependencyCheck.getUnmanagedDependencies(
            "3.18.0", "src/test/resources/shared-dependency-3.18.0-pom.xml");
    assertTrue(unManagedDependencies.isEmpty());
  }

  @Test
  public void getUnmanagedDependencyFromDifferentPomTest()
      throws MavenRepositoryException, ArtifactDescriptorException {
    List<String> unManagedDependencies =
        UnmanagedDependencyCheck.getUnmanagedDependencies(
            "3.18.0", "src/test/resources/self-dependency-pom.xml");
    assertThat(unManagedDependencies)
        .containsAtLeastElementsIn(ImmutableList.of("org.springframework.boot:spring-boot"));
  }

  @Test
  public void getUnmanagedDependencyFromNestedPomTest()
      throws MavenRepositoryException, ArtifactDescriptorException {
    List<String> unManagedDependencies =
        UnmanagedDependencyCheck.getUnmanagedDependencies(
            "3.18.0", "src/test/resources/transitive-dependency-pom.xml");
    assertThat(unManagedDependencies)
        .containsAtLeastElementsIn(ImmutableList.of("com.h2database:h2"));
  }
}
