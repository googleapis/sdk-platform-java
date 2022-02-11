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

// [START storage_v2_generated_storageclient_deletenotification_callablefuturecalldeletenotificationrequest]
import com.google.api.core.ApiFuture;
import com.google.protobuf.Empty;
import com.google.storage.v2.DeleteNotificationRequest;
import com.google.storage.v2.NotificationName;
import com.google.storage.v2.StorageClient;

public class DeleteNotificationCallableFutureCallDeleteNotificationRequest {

  public static void main(String[] args) throws Exception {
    deleteNotificationCallableFutureCallDeleteNotificationRequest();
  }

  public static void deleteNotificationCallableFutureCallDeleteNotificationRequest()
      throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (StorageClient storageClient = StorageClient.create()) {
      DeleteNotificationRequest request =
          DeleteNotificationRequest.newBuilder()
              .setName(NotificationName.of("[PROJECT]", "[BUCKET]", "[NOTIFICATION]").toString())
              .build();
      ApiFuture<Empty> future = storageClient.deleteNotificationCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
// [END storage_v2_generated_storageclient_deletenotification_callablefuturecalldeletenotificationrequest]