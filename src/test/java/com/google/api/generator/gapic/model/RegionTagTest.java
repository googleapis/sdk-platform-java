// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.gapic.model;

import org.junit.Assert;
import org.junit.Test;

public class RegionTagTest {
  private final String serviceName = "serviceName";
  private final String apiVersion = "1";
  private final String apiShortName = "shortName";
  private final String rpcName = "rpcName";
  private final String disambiguation = "disambiguation";

  @Test
  public void regionTagNoRpcName() {
    Assert.assertThrows(
        IllegalStateException.class,
        () ->
            RegionTag.builder()
                .setApiVersion(apiVersion)
                .setApiShortName(apiShortName)
                .setServiceName(serviceName)
                .setOverloadDisambiguation(disambiguation)
                .build());
  }

  @Test
  public void regionTagNoServiceName() {
    Assert.assertThrows(
        IllegalStateException.class,
        () ->
            RegionTag.builder()
                .setApiVersion(apiVersion)
                .setApiShortName(apiShortName)
                .setRpcName(rpcName)
                .setOverloadDisambiguation(disambiguation)
                .build());
  }

  @Test
  public void regionTagValidMissingFields() {
    RegionTag regionTag =
        RegionTag.builder().setServiceName(serviceName).setRpcName(rpcName).build();

    Assert.assertEquals("", regionTag.apiShortName());
    Assert.assertEquals("", regionTag.apiVersion());
    Assert.assertEquals("", regionTag.overloadDisambiguation());
  }

  @Test
  public void regionTagSanitizeAttributes() {
    String apiVersion = "1.4.0-<version!>";
    String serviceName = "service_Na@m*.e!<String>{}";
    String rpcName = "rpc _Nam^#,e   [String]10";
    RegionTag regionTag =
        RegionTag.builder()
            .setApiVersion(apiVersion)
            .setServiceName(serviceName)
            .setRpcName(rpcName)
            .build();

    Assert.assertEquals("1.4.0Version", regionTag.apiVersion());
    Assert.assertEquals("serviceNameString", regionTag.serviceName());
    Assert.assertEquals("rpcNameString10", regionTag.rpcName());
  }
}
