package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.LocationName;

public class ListInstancesStringIterateAll {

  public static void main(String[] args) throws Exception {
    listInstancesStringIterateAll();
  }

  public static void listInstancesStringIterateAll() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      String parent = LocationName.of("[PROJECT]", "[LOCATION]").toString();
      for (Instance element : cloudRedisClient.listInstances(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
