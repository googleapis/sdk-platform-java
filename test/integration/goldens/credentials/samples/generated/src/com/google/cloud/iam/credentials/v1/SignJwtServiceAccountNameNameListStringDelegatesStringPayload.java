package com.google.cloud.iam.credentials.v1.samples;

import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import com.google.cloud.iam.credentials.v1.SignJwtResponse;
import java.util.ArrayList;
import java.util.List;

public class SignJwtServiceAccountNameNameListStringDelegatesStringPayload {

  public static void main(String[] args) throws Exception {
    signJwtServiceAccountNameNameListStringDelegatesStringPayload();
  }

  public static void signJwtServiceAccountNameNameListStringDelegatesStringPayload()
      throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      ServiceAccountName name = ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]");
      List<String> delegates = new ArrayList<>();
      String payload = "payload-786701938";
      SignJwtResponse response = iamCredentialsClient.signJwt(name, delegates, payload);
    }
  }
}
