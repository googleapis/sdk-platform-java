package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.ListInstancesRequest;
import com.google.cloud.redis.v1beta1.LocationName;

public class ListInstancesListInstancesRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listInstancesListInstancesRequestIterateAll();
  }

  public static void listInstancesListInstancesRequestIterateAll() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      ListInstancesRequest request =
          ListInstancesRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (Instance element : cloudRedisClient.listInstances(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
