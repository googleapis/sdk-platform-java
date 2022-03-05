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

// [START storage_v2_generated_storageclient_listhmackeys_callablecalllisthmackeysrequest]
import com.google.common.base.Strings;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.HmacKeyMetadata;
import com.google.storage.v2.ListHmacKeysRequest;
import com.google.storage.v2.ListHmacKeysResponse;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListHmacKeysCallableCallListHmacKeysRequest {

  public static void main(String[] args) throws Exception {
    listHmacKeysCallableCallListHmacKeysRequest();
  }

  public static void listHmacKeysCallableCallListHmacKeysRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (StorageClient storageClient = StorageClient.create()) {
      ListHmacKeysRequest request =
          ListHmacKeysRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .setServiceAccountEmail("serviceAccountEmail1825953988")
              .setShowDeletedKeys(true)
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      while (true) {
        ListHmacKeysResponse response = storageClient.listHmacKeysCallable().call(request);
        for (HmacKeyMetadata element : response.getResponsesList()) {
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
// [END storage_v2_generated_storageclient_listhmackeys_callablecalllisthmackeysrequest]
