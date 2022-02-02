package com.google.cloud.iam.credentials.v1.samples;

import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import com.google.cloud.iam.credentials.v1.SignBlobResponse;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.List;

public class SignBlobServiceAccountNameNameListStringDelegatesByteStringPayload {

  public static void main(String[] args) throws Exception {
    signBlobServiceAccountNameNameListStringDelegatesByteStringPayload();
  }

  public static void signBlobServiceAccountNameNameListStringDelegatesByteStringPayload()
      throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      ServiceAccountName name = ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]");
      List<String> delegates = new ArrayList<>();
      ByteString payload = ByteString.EMPTY;
      SignBlobResponse response = iamCredentialsClient.signBlob(name, delegates, payload);
    }
  }
}
