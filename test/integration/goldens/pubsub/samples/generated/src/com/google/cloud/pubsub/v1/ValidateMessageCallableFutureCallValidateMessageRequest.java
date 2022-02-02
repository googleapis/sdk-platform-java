package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.Encoding;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.ValidateMessageRequest;
import com.google.pubsub.v1.ValidateMessageResponse;

public class ValidateMessageCallableFutureCallValidateMessageRequest {

  public static void main(String[] args) throws Exception {
    validateMessageCallableFutureCallValidateMessageRequest();
  }

  public static void validateMessageCallableFutureCallValidateMessageRequest() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      ValidateMessageRequest request =
          ValidateMessageRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setMessage(ByteString.EMPTY)
              .setEncoding(Encoding.forNumber(0))
              .build();
      ApiFuture<ValidateMessageResponse> future =
          schemaServiceClient.validateMessageCallable().futureCall(request);
      // Do something.
      ValidateMessageResponse response = future.get();
    }
  }
}
