/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.storage.v2.samples;

// [START 1.0_10_generated_storageclient_listobjects_callablecalllistobjectsrequest]
import com.google.common.base.Strings;
import com.google.protobuf.FieldMask;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.ListObjectsRequest;
import com.google.storage.v2.ListObjectsResponse;
import com.google.storage.v2.Object;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListObjectsCallableCallListObjectsRequest {

  public static void main(String[] args) throws Exception {
    listObjectsCallableCallListObjectsRequest();
  }

  public static void listObjectsCallableCallListObjectsRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (StorageClient storageClient = StorageClient.create()) {
      ListObjectsRequest request =
          ListObjectsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .setDelimiter("delimiter-250518009")
              .setIncludeTrailingDelimiter(true)
              .setPrefix("prefix-980110702")
              .setVersions(true)
              .setReadMask(FieldMask.newBuilder().build())
              .setLexicographicStart("lexicographicStart-2093413008")
              .setLexicographicEnd("lexicographicEnd1646968169")
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      while (true) {
        ListObjectsResponse response = storageClient.listObjectsCallable().call(request);
        for (Object element : response.getResponsesList()) {
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
// [END 1.0_10_generated_storageclient_listobjects_callablecalllistobjectsrequest]