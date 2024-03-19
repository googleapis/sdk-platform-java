/*
 * Copyright 2016 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.gax.rpc.HeaderProvider;
import com.google.auth.Credentials;
import com.google.auth.http.HttpTransportFactory;
import com.google.cloud.BaseService;
import com.google.cloud.NoCredentials;
import com.google.cloud.Service;
import com.google.cloud.ServiceDefaults;
import com.google.cloud.ServiceFactory;
import com.google.cloud.ServiceOptions;
import com.google.cloud.ServiceRpc;
import com.google.cloud.TransportOptions;
import com.google.cloud.http.HttpTransportOptions.DefaultHttpTransportFactory;
import com.google.cloud.spi.ServiceRpcFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;
import org.easymock.EasyMock;
import org.junit.Test;

public class HttpTransportOptionsTest {

  private static final HttpTransportFactory MOCK_HTTP_TRANSPORT_FACTORY =
      EasyMock.createMock(HttpTransportFactory.class);
  private static final HttpTransportOptions OPTIONS =
      HttpTransportOptions.newBuilder()
          .setConnectTimeout(1234)
          .setHttpTransportFactory(MOCK_HTTP_TRANSPORT_FACTORY)
          .setReadTimeout(5678)
          .build();
  private static final HttpTransportOptions DEFAULT_OPTIONS =
      HttpTransportOptions.newBuilder().build();
  private static final HttpTransportOptions OPTIONS_COPY = OPTIONS.toBuilder().build();

  interface TestService extends Service<TestServiceOptions> {}

  private static class TestServiceImpl extends BaseService<TestServiceOptions>
      implements TestService {
    private TestServiceImpl(TestServiceOptions options) {
      super(options);
    }
  }

  public interface TestServiceFactory extends ServiceFactory<TestService, TestServiceOptions> {}

  private static class DefaultTestServiceFactory implements TestServiceFactory {
    private static final TestServiceFactory INSTANCE = new DefaultTestServiceFactory();

    @Override
    public TestService create(TestServiceOptions options) {
      return new TestServiceImpl(options);
    }
  }

  public interface TestServiceRpcFactory extends ServiceRpcFactory<TestServiceOptions> {}

  private static class DefaultTestServiceRpcFactory implements TestServiceRpcFactory {
    private static final TestServiceRpcFactory INSTANCE = new DefaultTestServiceRpcFactory();

    @Override
    public TestServiceRpc create(TestServiceOptions options) {
      return new DefaultTestServiceRpc(options);
    }
  }

  private interface TestServiceRpc extends ServiceRpc {}

  private static class DefaultTestServiceRpc implements TestServiceRpc {
    DefaultTestServiceRpc(TestServiceOptions options) {}
  }

  static class TestServiceOptions extends ServiceOptions<TestService, TestServiceOptions> {
    private static class Builder
        extends ServiceOptions.Builder<TestService, TestServiceOptions, Builder> {
      private Builder() {}

      private Builder(TestServiceOptions options) {
        super(options);
      }

      @Override
      protected TestServiceOptions build() {
        return new TestServiceOptions(this);
      }
    }

    private TestServiceOptions(Builder builder) {
      super(
          TestServiceFactory.class,
          TestServiceRpcFactory.class,
          builder,
          new TestServiceDefaults());
    }

    private static class TestServiceDefaults
        implements ServiceDefaults<TestService, TestServiceOptions> {

      @Override
      public TestServiceFactory getDefaultServiceFactory() {
        return DefaultTestServiceFactory.INSTANCE;
      }

      @Override
      public TestServiceRpcFactory getDefaultRpcFactory() {
        return DefaultTestServiceRpcFactory.INSTANCE;
      }

      @Override
      public TransportOptions getDefaultTransportOptions() {
        return new TransportOptions() {};
      }
    }

    @Override
    protected Set<String> getScopes() {
      return null;
    }

    @Override
    public Builder toBuilder() {
      return new Builder(this);
    }

    private static Builder newBuilder() {
      return new Builder();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof TestServiceOptions && baseEquals((TestServiceOptions) obj);
    }

    @Override
    public int hashCode() {
      return baseHashCode();
    }
  }

  @Test
  public void testBuilder() {
    assertEquals(1234, OPTIONS.getConnectTimeout());
    assertSame(MOCK_HTTP_TRANSPORT_FACTORY, OPTIONS.getHttpTransportFactory());
    assertEquals(5678, OPTIONS.getReadTimeout());
    assertEquals(-1, DEFAULT_OPTIONS.getConnectTimeout());
    assertTrue(DEFAULT_OPTIONS.getHttpTransportFactory() instanceof DefaultHttpTransportFactory);
    assertEquals(-1, DEFAULT_OPTIONS.getReadTimeout());
  }

  @Test
  public void testBaseEquals() {
    assertEquals(OPTIONS, OPTIONS_COPY);
    assertNotEquals(DEFAULT_OPTIONS, OPTIONS);
  }

  @Test
  public void testBaseHashCode() {
    assertEquals(OPTIONS.hashCode(), OPTIONS_COPY.hashCode());
    assertNotEquals(DEFAULT_OPTIONS.hashCode(), OPTIONS.hashCode());
  }

  @Test
  public void testHeader() {
    String expectedHeaderPattern = "^gl-java/.+ gccl/.* gax/.+";
    ServiceOptions<?, ?> serviceOptions = EasyMock.createMock(ServiceOptions.class);
    HeaderProvider headerProvider =
        OPTIONS.getInternalHeaderProviderBuilder(serviceOptions).build();

    assertEquals(1, headerProvider.getHeaders().size());
    assertTrue(
        Pattern.compile(expectedHeaderPattern)
            .matcher(headerProvider.getHeaders().values().iterator().next())
            .find());
  }

  @Test
  public void testHttpRequestInitializer_initializeHasValidUniverseDomain() throws IOException {
    Credentials credentials = EasyMock.createMock(Credentials.class);
    EasyMock.expect(credentials.getUniverseDomain()).andReturn(Credentials.GOOGLE_DEFAULT_UNIVERSE);
    EasyMock.expect(credentials.hasRequestMetadata()).andReturn(false);
    HeaderProvider headerProvider = EasyMock.createMock(HeaderProvider.class);
    EasyMock.expect(headerProvider.getHeaders()).andReturn(new HashMap<>());
    EasyMock.replay(credentials, headerProvider);

    HttpTransport mockHttpTransport =
        new MockHttpTransport() {
          @Override
          public LowLevelHttpRequest buildRequest(String method, String url) {
            return new MockLowLevelHttpRequest() {
              @Override
              public LowLevelHttpResponse execute() {
                return new MockLowLevelHttpResponse();
              }
            };
          }
        };
    HttpRequest httpRequest =
        mockHttpTransport.createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
    TestServiceOptions testServiceOptions =
        TestServiceOptions.newBuilder()
            .setCredentials(credentials)
            .setHeaderProvider(headerProvider)
            .setQuotaProjectId("testing")
            .setProjectId("testing")
            .build();
    HttpRequestInitializer httpRequestInitializer =
        DEFAULT_OPTIONS.getHttpRequestInitializer(testServiceOptions);
    httpRequestInitializer.initialize(httpRequest);
  }

  @Test
  public void
      testHttpRequestInitializer_initializeHasInvalidUniverseDomain_throwsIllegalStateException()
          throws IOException {
    Credentials credentials = EasyMock.createMock(Credentials.class);
    EasyMock.expect(credentials.getUniverseDomain()).andReturn("random.com");
    EasyMock.expect(credentials.hasRequestMetadata()).andReturn(false);
    HeaderProvider headerProvider = EasyMock.createMock(HeaderProvider.class);
    EasyMock.expect(headerProvider.getHeaders()).andReturn(new HashMap<>());
    EasyMock.replay(credentials, headerProvider);

    HttpTransport mockHttpTransport =
        new MockHttpTransport() {
          @Override
          public LowLevelHttpRequest buildRequest(String method, String url) {
            return new MockLowLevelHttpRequest() {
              @Override
              public LowLevelHttpResponse execute() {
                return new MockLowLevelHttpResponse();
              }
            };
          }
        };
    HttpRequest httpRequest =
        mockHttpTransport.createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
    TestServiceOptions testServiceOptions =
        TestServiceOptions.newBuilder()
            .setCredentials(credentials)
            .setHeaderProvider(headerProvider)
            .setQuotaProjectId("testing")
            .setProjectId("testing")
            .build();
    HttpRequestInitializer httpRequestInitializer =
        DEFAULT_OPTIONS.getHttpRequestInitializer(testServiceOptions);
    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class, () -> httpRequestInitializer.initialize(httpRequest));
    assertEquals(
        "The configured universe domain (googleapis.com) does not match the universe domain found in the credentials (random.com). If you haven't configured the universe domain explicitly, `googleapis.com` is the default.",
        exception.getMessage());
  }

  @Test
  public void testHttpRequestInitializer_initializeNoCredentials_noThrow() throws IOException {
    NoCredentials credentials = NoCredentials.getInstance();
    HeaderProvider headerProvider = EasyMock.createMock(HeaderProvider.class);
    EasyMock.expect(headerProvider.getHeaders()).andReturn(new HashMap<>());
    EasyMock.replay(headerProvider);

    HttpTransport mockHttpTransport =
        new MockHttpTransport() {
          @Override
          public LowLevelHttpRequest buildRequest(String method, String url) {
            return new MockLowLevelHttpRequest() {
              @Override
              public LowLevelHttpResponse execute() {
                return new MockLowLevelHttpResponse();
              }
            };
          }
        };
    HttpRequest httpRequest =
        mockHttpTransport.createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL);
    TestServiceOptions testServiceOptions =
        TestServiceOptions.newBuilder()
            .setCredentials(credentials)
            .setHeaderProvider(headerProvider)
            .setQuotaProjectId("testing")
            .setProjectId("testing")
            .build();
    HttpRequestInitializer httpRequestInitializer =
        DEFAULT_OPTIONS.getHttpRequestInitializer(testServiceOptions);
    httpRequestInitializer.initialize(httpRequest);
  }
}
