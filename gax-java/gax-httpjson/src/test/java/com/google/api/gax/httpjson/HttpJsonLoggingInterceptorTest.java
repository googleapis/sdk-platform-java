package com.google.api.gax.httpjson;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.api.gax.httpjson.ApiMethodDescriptor.MethodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class HttpJsonLoggingInterceptorTest {

  @Mock private HttpJsonChannel channel;

  @Mock private HttpJsonClientCall<String, Integer> call;

  private static final ApiMethodDescriptor<String, Integer> method =
      ApiMethodDescriptor.newBuilder()
          .setType(MethodType.UNARY)
          .setRequestFormatter(mock(HttpRequestFormatter.class))
          .setRequestFormatter(mock(HttpRequestFormatter.class))
          .setFullMethodName("FakeClient/fake-method")
          .build();

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(channel.newCall(
            Mockito.<ApiMethodDescriptor<String, Integer>>any(), any(HttpJsonCallOptions.class)))
        .thenReturn(call);
  }

  @Test
  void testInterceptor_basic() {

    HttpJsonLoggingInterceptor interceptor = new HttpJsonLoggingInterceptor();
    // HttpJsonChannel intercepted = HttpJsonClientInterceptor.intercept()
  }
}
