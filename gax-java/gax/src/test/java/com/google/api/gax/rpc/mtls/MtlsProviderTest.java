/*
 * Copyright 2021 Google LLC
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google LLC nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.google.api.gax.rpc.mtls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.List;
import org.junit.jupiter.api.Test;

class MtlsProviderTest {

  private static class TestCertProviderCommandProcess extends Process {

    private boolean runForever;
    private int exitValue;

    public TestCertProviderCommandProcess(int exitValue, boolean runForever) {
      this.runForever = runForever;
      this.exitValue = exitValue;
    }

    @Override
    public OutputStream getOutputStream() {
      return null;
    }

    @Override
    public InputStream getInputStream() {
      return null;
    }

    @Override
    public InputStream getErrorStream() {
      return null;
    }

    @Override
    public int waitFor() throws InterruptedException {
      return 0;
    }

    @Override
    public int exitValue() {
      if (runForever) {
        throw new IllegalThreadStateException();
      }
      return exitValue;
    }

    @Override
    public void destroy() {}
  }

  static class TestProcessProvider implements MtlsProvider.ProcessProvider {

    private int exitCode;

    public TestProcessProvider(int exitCode) {
      this.exitCode = exitCode;
    }

    @Override
    public Process createProcess(InputStream metadata) throws IOException {
      return new TestCertProviderCommandProcess(exitCode, false);
    }
  }

  @Test
  void testUseMtlsEndpointAlways() {
    MtlsProvider mtlsProvider =
        new MtlsProvider(
            name -> name.equals("GOOGLE_API_USE_MTLS_ENDPOINT") ? "always" : "false",
            new TestProcessProvider(0),
            "/path/to/missing/file");
    assertEquals(
        MtlsProvider.MtlsEndpointUsagePolicy.ALWAYS, mtlsProvider.getMtlsEndpointUsagePolicy());
  }

  @Test
  void testUseMtlsEndpointAuto() {
    MtlsProvider mtlsProvider =
        new MtlsProvider(
            name -> name.equals("GOOGLE_API_USE_MTLS_ENDPOINT") ? "auto" : "false",
            new TestProcessProvider(0),
            "/path/to/missing/file");
    assertEquals(
        MtlsProvider.MtlsEndpointUsagePolicy.AUTO, mtlsProvider.getMtlsEndpointUsagePolicy());
  }

  @Test
  void testUseMtlsEndpointNever() {
    MtlsProvider mtlsProvider =
        new MtlsProvider(
            name -> name.equals("GOOGLE_API_USE_MTLS_ENDPOINT") ? "never" : "false",
            new TestProcessProvider(0),
            "/path/to/missing/file");
    assertEquals(
        MtlsProvider.MtlsEndpointUsagePolicy.NEVER, mtlsProvider.getMtlsEndpointUsagePolicy());
  }

  @Test
  void testUseMtlsClientCertificateTrue() {
    MtlsProvider mtlsProvider =
        new MtlsProvider(
            name -> name.equals("GOOGLE_API_USE_MTLS_ENDPOINT") ? "auto" : "true",
            new TestProcessProvider(0),
            "/path/to/missing/file");
    assertTrue(mtlsProvider.useMtlsClientCertificate());
  }

  @Test
  void testUseMtlsClientCertificateFalse() {
    MtlsProvider mtlsProvider =
        new MtlsProvider(
            name -> name.equals("GOOGLE_API_USE_MTLS_ENDPOINT") ? "auto" : "false",
            new TestProcessProvider(0),
            "/path/to/missing/file");
    assertFalse(mtlsProvider.useMtlsClientCertificate());
  }

  @Test
  void testGetKeyStore() throws IOException {
    MtlsProvider mtlsProvider =
        new MtlsProvider(
            name -> name.equals("GOOGLE_API_USE_MTLS_ENDPOINT") ? "always" : "false",
            new TestProcessProvider(0),
            "/path/to/missing/file");
    assertNull(mtlsProvider.getKeyStore());
  }

  @Test
  void testGetKeyStoreNonZeroExitCode()
      throws IOException, InterruptedException, GeneralSecurityException {
    InputStream metadata =
        this.getClass()
            .getClassLoader()
            .getResourceAsStream("com/google/api/gax/rpc/mtls/mtlsCertAndKey.pem");
    IOException actual =
        assertThrows(
            IOException.class,
            () -> MtlsProvider.getKeyStore(metadata, new TestProcessProvider(1)));
    assertTrue(
        actual.getMessage().contains("Cert provider command failed with exit code: 1"),
        "expected to fail with nonzero exit code");
  }

  @Test
  void testExtractCertificateProviderCommand() throws IOException {
    InputStream inputStream =
        this.getClass()
            .getClassLoader()
            .getResourceAsStream("com/google/api/gax/rpc/mtls/mtls_context_aware_metadata.json");
    List<String> command = MtlsProvider.extractCertificateProviderCommand(inputStream);
    assertEquals(2, command.size());
    assertEquals("some_binary", command.get(0));
    assertEquals("some_argument", command.get(1));
  }

  @Test
  void testRunCertificateProviderCommandSuccess() throws IOException, InterruptedException {
    Process certCommandProcess = new TestCertProviderCommandProcess(0, false);
    int exitValue = MtlsProvider.runCertificateProviderCommand(certCommandProcess, 100);
    assertEquals(0, exitValue);
  }

  @Test
  void testRunCertificateProviderCommandTimeout() throws InterruptedException {
    Process certCommandProcess = new TestCertProviderCommandProcess(0, true);
    IOException actual =
        assertThrows(
            IOException.class,
            () -> MtlsProvider.runCertificateProviderCommand(certCommandProcess, 100));
    assertTrue(
        actual.getMessage().contains("cert provider command timed out"),
        "expected to fail with timeout");
  }
}
