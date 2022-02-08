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

// [START 10_10_generated_storageClient_deleteNotification_string]
import com.google.protobuf.Empty;
import com.google.storage.v2.NotificationName;
import com.google.storage.v2.StorageClient;

public class DeleteNotificationString {

  public static void main(String[] args) throws Exception {
    deleteNotificationString();
  }

  public static void deleteNotificationString() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (StorageClient storageClient = StorageClient.create()) {
      String name = NotificationName.of("[PROJECT]", "[BUCKET]", "[NOTIFICATION]").toString();
      storageClient.deleteNotification(name);
    }
  }
}
// [END 10_10_generated_storageClient_deleteNotification_string]