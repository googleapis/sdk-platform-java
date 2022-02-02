package com.google.cloud.iam.credentials.v1.samples;

import com.google.cloud.iam.credentials.v1.GenerateIdTokenResponse;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import java.util.ArrayList;
import java.util.List;

public class GenerateIdTokenStringNameListStringDelegatesStringAudienceBooleanIncludeEmail {

  public static void main(String[] args) throws Exception {
    generateIdTokenStringNameListStringDelegatesStringAudienceBooleanIncludeEmail();
  }

  public static void generateIdTokenStringNameListStringDelegatesStringAudienceBooleanIncludeEmail()
      throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      String name = ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]").toString();
      List<String> delegates = new ArrayList<>();
      String audience = "audience975628804";
      boolean includeEmail = true;
      GenerateIdTokenResponse response =
          iamCredentialsClient.generateIdToken(name, delegates, audience, includeEmail);
    }
  }
}
