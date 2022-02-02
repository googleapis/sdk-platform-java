package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisClient;
import com.google.cloud.redis.v1beta1.Instance;
import com.google.cloud.redis.v1beta1.LocationName;

public class ListInstancesLocationNameIterateAll {

  public static void main(String[] args) throws Exception {
    listInstancesLocationNameIterateAll();
  }

  public static void listInstancesLocationNameIterateAll() throws Exception {
    try (CloudRedisClient cloudRedisClient = CloudRedisClient.create()) {
      LocationName parent = LocationName.of("[PROJECT]", "[LOCATION]");
      for (Instance element : cloudRedisClient.listInstances(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
