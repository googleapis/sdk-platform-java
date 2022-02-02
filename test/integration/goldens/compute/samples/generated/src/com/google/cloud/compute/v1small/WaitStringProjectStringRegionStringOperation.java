package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.Operation;
import com.google.cloud.compute.v1small.RegionOperationsClient;

public class WaitStringProjectStringRegionStringOperation {

  public static void main(String[] args) throws Exception {
    waitStringProjectStringRegionStringOperation();
  }

  public static void waitStringProjectStringRegionStringOperation() throws Exception {
    try (RegionOperationsClient regionOperationsClient = RegionOperationsClient.create()) {
      String project = "project-309310695";
      String region = "region-934795532";
      String operation = "operation1662702951";
      Operation response = regionOperationsClient.wait(project, region, operation);
    }
  }
}
