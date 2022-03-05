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

package com.google.cloud.logging.v2.samples;

// [START logging_v2_generated_loggingclient_listlogs_billingaccountnameiterateall]
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.BillingAccountName;

public class ListLogsBillingAccountNameIterateAll {

  public static void main(String[] args) throws Exception {
    listLogsBillingAccountNameIterateAll();
  }

  public static void listLogsBillingAccountNameIterateAll() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (LoggingClient loggingClient = LoggingClient.create()) {
      BillingAccountName parent = BillingAccountName.of("[BILLING_ACCOUNT]");
      for (String element : loggingClient.listLogs(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END logging_v2_generated_loggingclient_listlogs_billingaccountnameiterateall]
