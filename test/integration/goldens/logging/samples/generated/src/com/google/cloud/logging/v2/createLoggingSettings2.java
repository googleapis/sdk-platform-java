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

// [START v2_logging_generated_loggingclient_create_loggingsettings2]
import com.google.cloud.logging.v2.LoggingClient;
import com.google.cloud.logging.v2.LoggingSettings;
import com.google.cloud.logging.v2.myEndpoint;

public class CreateLoggingSettings2 {

  public static void main(String[] args) throws Exception {
    createLoggingSettings2();
  }

  public static void createLoggingSettings2() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    LoggingSettings loggingSettings = LoggingSettings.newBuilder().setEndpoint(myEndpoint).build();
    LoggingClient loggingClient = LoggingClient.create(loggingSettings);
  }
}
// [END v2_logging_generated_loggingclient_create_loggingsettings2]