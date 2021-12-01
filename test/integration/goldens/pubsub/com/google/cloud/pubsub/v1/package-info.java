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

/**
 * A client to Cloud Pub/Sub API
 *
 * <p>The interfaces provided are listed below, along with usage samples.
 *
 * <p>======================= TopicAdminClient =======================
 *
 * <p>Service Description: The service that an application uses to manipulate topics, and to send
 * messages to a topic.
 *
 * <p>Sample for TopicAdminClient:
 *
 * <pre>{@code
 * package com.google.example;
 *
 * import com.google.cloud.pubsub.v1.TopicAdminClient;
 * import com.google.pubsub.v1.Topic;
 * import com.google.pubsub.v1.TopicName;
 *
 * public class TopicAdminClientCreateTopic {
 *
 *   public static void main(String[] args) throws Exception {
 *     topicAdminClientCreateTopic();
 *   }
 *
 *   public static void topicAdminClientCreateTopic() throws Exception {
 *     try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
 *       TopicName name = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]");
 *       Topic response = topicAdminClient.createTopic(name);
 *     }
 *   }
 * }
 * }</pre>
 *
 * <p>======================= SubscriptionAdminClient =======================
 *
 * <p>Service Description: The service that an application uses to manipulate subscriptions and to
 * consume messages from a subscription via the `Pull` method or by establishing a bi-directional
 * stream using the `StreamingPull` method.
 *
 * <p>Sample for SubscriptionAdminClient:
 *
 * <pre>{@code
 * package com.google.example;
 *
 * import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
 * import com.google.pubsub.v1.PushConfig;
 * import com.google.pubsub.v1.Subscription;
 * import com.google.pubsub.v1.SubscriptionName;
 * import com.google.pubsub.v1.TopicName;
 *
 * public class SubscriptionAdminClientCreateSubscription {
 *
 *   public static void main(String[] args) throws Exception {
 *     subscriptionAdminClientCreateSubscription();
 *   }
 *
 *   public static void subscriptionAdminClientCreateSubscription() throws Exception {
 *     try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
 *       SubscriptionName name = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]");
 *       TopicName topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]");
 *       PushConfig pushConfig = PushConfig.newBuilder().build();
 *       int ackDeadlineSeconds = 2135351438;
 *       Subscription response =
 *           subscriptionAdminClient.createSubscription(name, topic, pushConfig, ackDeadlineSeconds);
 *     }
 *   }
 * }
 * }</pre>
 *
 * <p>======================= SchemaServiceClient =======================
 *
 * <p>Service Description: Service for doing schema-related operations.
 *
 * <p>Sample for SchemaServiceClient:
 *
 * <pre>{@code
 * package com.google.example;
 *
 * import com.google.cloud.pubsub.v1.SchemaServiceClient;
 * import com.google.pubsub.v1.ProjectName;
 * import com.google.pubsub.v1.Schema;
 *
 * public class SchemaServiceClientCreateSchema {
 *
 *   public static void main(String[] args) throws Exception {
 *     schemaServiceClientCreateSchema();
 *   }
 *
 *   public static void schemaServiceClientCreateSchema() throws Exception {
 *     try (SchemaServiceClient schemaServiceClient = SchemaServiceClient.create()) {
 *       ProjectName parent = ProjectName.of("[PROJECT]");
 *       Schema schema = Schema.newBuilder().build();
 *       String schemaId = "schemaId-697673060";
 *       Schema response = schemaServiceClient.createSchema(parent, schema, schemaId);
 *     }
 *   }
 * }
 * }</pre>
 */
@Generated("by gapic-generator-java")
package com.google.cloud.pubsub.v1;

import javax.annotation.Generated;
