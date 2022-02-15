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

// [START storage_v2_generated_storageclient_createnotification_callablefuturecallcreatenotificationrequest]
import com.google.api.core.ApiFuture;
import com.google.storage.v2.CreateNotificationRequest;
import com.google.storage.v2.Notification;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateNotificationCallableFutureCallCreateNotificationRequest {

  public static void main(String[] args) throws Exception {
    createNotificationCallableFutureCallCreateNotificationRequest();
  }

  public static void createNotificationCallableFutureCallCreateNotificationRequest()
      throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (StorageClient storageClient = StorageClient.create()) {
      CreateNotificationRequest request =
          CreateNotificationRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setNotification(Notification.newBuilder().build())
              .build();
      ApiFuture<Notification> future =
          storageClient.createNotificationCallable().futureCall(request);
      // Do something.
      Notification response = future.get();
    }
  }
}
// [END storage_v2_generated_storageclient_createnotification_callablefuturecallcreatenotificationrequest]