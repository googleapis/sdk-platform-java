package com.google.api.generator.gapic.composer.utils;

import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.List;
import java.util.stream.Collectors;

public class SampleNames {
  public static String createSampleName(String methodName) {
    return JavaStyle.toUpperCamelCase(methodName);
  }

  public static String createSampleName(String methodName, String identifier) {
    return String.format(
        "%s%s", JavaStyle.toUpperCamelCase(methodName), JavaStyle.toUpperCamelCase(identifier));
  }

  public static String createSampleName(String methodName, List<MethodArgument> arguments) {
    String args =
        arguments.stream()
            .map(arg -> JavaStyle.toUpperCamelCase(arg.type().reference().simpleName()))
            .collect(Collectors.joining());

    return String.format("%s%s", JavaStyle.toUpperCamelCase(methodName), args);
  }
}
