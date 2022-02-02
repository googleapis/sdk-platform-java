package com.google.cloud.redis.v1beta1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.ListInstancesRequest;
import com.google.cloud.redis.v1beta1.LocationName;

public class ListInstancesPagedCallableFutureCallListInstancesRequest {

  public static void main(String[] args) throws Exception {
    listInstancesPagedCallableFutureCallListInstancesRequest();
  }

  public static void listInstancesPagedCallableFutureCallListInstancesRequest() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      ListInstancesRequest request =
          ListInstancesRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<Instance> future =
          cloudRedisClient.listInstancesPagedCallable().futureCall(request);
      // Do something.
      for (Instance element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
