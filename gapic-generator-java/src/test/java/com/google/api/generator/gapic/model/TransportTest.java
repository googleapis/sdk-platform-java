package com.google.api.generator.gapic.model;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TransportTest {

  private final String input;
  private final Transport expected;

  @Parameterized.Parameters
  public static Collection<Object[]> primeNumbers() {
    return Arrays.asList(
        new Object[][] {
          {"grpc", Transport.GRPC},
          {"Grpc", Transport.GRPC},
          {"gRPC", Transport.GRPC},
          {"rest", Transport.REST},
          {"REST", Transport.REST},
          {"rESt", Transport.REST},
          {"grpc+rest", Transport.GRPC_REST},
          {"gRPC+REST", Transport.GRPC_REST},
          {"grPc+rEst", Transport.GRPC_REST}
        });
  }

  public TransportTest(String input, Transport expected) {
    this.input = input;
    this.expected = expected;
  }

  @Test
  public void testParse_returnsValidTransport() {
    assertThat(expected).isEqualTo(Transport.parse(input));
  }

  @Test
  public void testParse_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> Transport.parse("invalid transport"));
    assertThrows(IllegalArgumentException.class, () -> Transport.parse("grHttpc"));
  }
}
