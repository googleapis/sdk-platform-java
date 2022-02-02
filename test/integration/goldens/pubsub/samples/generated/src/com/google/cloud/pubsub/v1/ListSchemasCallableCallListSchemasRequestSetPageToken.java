package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SchemaServiceClient;
import com.google.common.base.Strings;
import com.google.pubsub.v1.ListSchemasRequest;
import com.google.pubsub.v1.ListSchemasResponse;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Schema;
import com.google.pubsub.v1.SchemaView;

public class ListSchemasCallableCallListSchemasRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listSchemasCallableCallListSchemasRequestSetPageToken();
  }

  public static void listSchemasCallableCallListSchemasRequestSetPageToken() throws Exception {
    try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
      ListSchemasRequest request =
          ListSchemasRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setView(SchemaView.forNumber(0))
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListSchemasResponse response = schemaServiceClient.listSchemasCallable().call(request);
        for (Schema element : response.getResponsesList()) {
          // doThingsWith(element);
        }
        String nextPageToken = response.getNextPageToken();
        if (!Strings.isNullOrEmpty(nextPageToken)) {
          request = request.toBuilder().setPageToken(nextPageToken).build();
        } else {
          break;
        }
      }
    }
  }
}
