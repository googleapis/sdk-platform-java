package com.google.cloud.iam.credentials.v1.samples;

import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import com.google.cloud.iam.credentials.v1.SignBlobResponse;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.List;

public class SignBlobStringNameListStringDelegatesByteStringPayload {

  public static void main(String[] args) throws Exception {
    signBlobStringNameListStringDelegatesByteStringPayload();
  }

  public static void signBlobStringNameListStringDelegatesByteStringPayload() throws Exception {
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      String name = ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]").toString();
      List<String> delegates = new ArrayList<>();
      ByteString payload = ByteString.EMPTY;
      SignBlobResponse response = iamCredentialsClient.signBlob(name, delegates, payload);
    }
  }
}
