package com.google.cloud.iam.credentials.v1.samples;

import com.google.cloud.iam.credentials.v1.GenerateIdTokenResponse;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import java.util.ArrayList;
import java.util.List;

public
class GenerateIdTokenServiceAccountNameNameListStringDelegatesStringAudienceBooleanIncludeEmail {

  public static void main(String[] args) throws Exception {
    generateIdTokenServiceAccountNameNameListStringDelegatesStringAudienceBooleanIncludeEmail();
  }

  public static void
      generateIdTokenServiceAccountNameNameListStringDelegatesStringAudienceBooleanIncludeEmail()
          throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      ServiceAccountName name = ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]");
      List<String> delegates = new ArrayList<>();
      String audience = "audience975628804";
      boolean includeEmail = true;
      GenerateIdTokenResponse response =
          iamCredentialsClient.generateIdToken(name, delegates, audience, includeEmail);
    }
  }
}
