package com.google.api.generator.gapic.protoparser;

public class NoServicesFoundException extends IllegalStateException {

  public NoServicesFoundException() {}
  public NoServicesFoundException(String message) {
    super(message);
  }
}
