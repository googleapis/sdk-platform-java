/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/apps/card/v1/card.proto

// Protobuf Java Version: 3.25.5
package com.google.apps.card.v1;

public interface ActionOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.apps.card.v1.Action)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * A custom function to invoke when the containing element is
   * clicked or othrwise activated.
   *
   * For example usage, see [Read form
   * data](https://developers.google.com/workspace/chat/read-form-data).
   * </pre>
   *
   * <code>string function = 1;</code>
   *
   * @return The function.
   */
  java.lang.String getFunction();

  /**
   *
   *
   * <pre>
   * A custom function to invoke when the containing element is
   * clicked or othrwise activated.
   *
   * For example usage, see [Read form
   * data](https://developers.google.com/workspace/chat/read-form-data).
   * </pre>
   *
   * <code>string function = 1;</code>
   *
   * @return The bytes for function.
   */
  com.google.protobuf.ByteString getFunctionBytes();

  /**
   *
   *
   * <pre>
   * List of action parameters.
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Action.ActionParameter parameters = 2;</code>
   */
  java.util.List<com.google.apps.card.v1.Action.ActionParameter> getParametersList();

  /**
   *
   *
   * <pre>
   * List of action parameters.
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Action.ActionParameter parameters = 2;</code>
   */
  com.google.apps.card.v1.Action.ActionParameter getParameters(int index);

  /**
   *
   *
   * <pre>
   * List of action parameters.
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Action.ActionParameter parameters = 2;</code>
   */
  int getParametersCount();

  /**
   *
   *
   * <pre>
   * List of action parameters.
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Action.ActionParameter parameters = 2;</code>
   */
  java.util.List<? extends com.google.apps.card.v1.Action.ActionParameterOrBuilder>
      getParametersOrBuilderList();

  /**
   *
   *
   * <pre>
   * List of action parameters.
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Action.ActionParameter parameters = 2;</code>
   */
  com.google.apps.card.v1.Action.ActionParameterOrBuilder getParametersOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * Specifies the loading indicator that the action displays while
   * making the call to the action.
   * </pre>
   *
   * <code>.google.apps.card.v1.Action.LoadIndicator load_indicator = 3;</code>
   *
   * @return The enum numeric value on the wire for loadIndicator.
   */
  int getLoadIndicatorValue();

  /**
   *
   *
   * <pre>
   * Specifies the loading indicator that the action displays while
   * making the call to the action.
   * </pre>
   *
   * <code>.google.apps.card.v1.Action.LoadIndicator load_indicator = 3;</code>
   *
   * @return The loadIndicator.
   */
  com.google.apps.card.v1.Action.LoadIndicator getLoadIndicator();

  /**
   *
   *
   * <pre>
   * Indicates whether form values persist after the action. The default value
   * is `false`.
   *
   * If `true`, form values remain after the action is triggered. To let the
   * user make changes while the action is being processed, set
   * [`LoadIndicator`](https://developers.google.com/workspace/add-ons/reference/rpc/google.apps.card.v1#loadindicator)
   * to `NONE`. For [card
   * messages](https://developers.google.com/workspace/chat/api/guides/v1/messages/create#create)
   * in Chat apps, you must also set the action's
   * [`ResponseType`](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.messages#responsetype)
   * to `UPDATE_MESSAGE` and use the same
   * [`card_id`](https://developers.google.com/workspace/chat/api/reference/rest/v1/spaces.messages#CardWithId)
   * from the card that contained the action.
   *
   * If `false`, the form values are cleared when the action is triggered.
   * To prevent the user from making changes while the action is being
   * processed, set
   * [`LoadIndicator`](https://developers.google.com/workspace/add-ons/reference/rpc/google.apps.card.v1#loadindicator)
   * to `SPINNER`.
   * </pre>
   *
   * <code>bool persist_values = 4;</code>
   *
   * @return The persistValues.
   */
  boolean getPersistValues();

  /**
   *
   *
   * <pre>
   * Optional. Required when opening a
   * [dialog](https://developers.google.com/workspace/chat/dialogs).
   *
   * What to do in response to an interaction with a user, such as a user
   * clicking a button in a card message.
   *
   * If unspecified, the app responds by executing an `action`—like opening a
   * link or running a function—as normal.
   *
   * By specifying an `interaction`, the app can respond in special interactive
   * ways. For example, by setting `interaction` to `OPEN_DIALOG`, the app can
   * open a [dialog](https://developers.google.com/workspace/chat/dialogs). When
   * specified, a loading indicator isn't shown. If specified for
   * an add-on, the entire card is stripped and nothing is shown in the client.
   *
   * [Google Chat apps](https://developers.google.com/workspace/chat):
   * </pre>
   *
   * <code>.google.apps.card.v1.Action.Interaction interaction = 5;</code>
   *
   * @return The enum numeric value on the wire for interaction.
   */
  int getInteractionValue();

  /**
   *
   *
   * <pre>
   * Optional. Required when opening a
   * [dialog](https://developers.google.com/workspace/chat/dialogs).
   *
   * What to do in response to an interaction with a user, such as a user
   * clicking a button in a card message.
   *
   * If unspecified, the app responds by executing an `action`—like opening a
   * link or running a function—as normal.
   *
   * By specifying an `interaction`, the app can respond in special interactive
   * ways. For example, by setting `interaction` to `OPEN_DIALOG`, the app can
   * open a [dialog](https://developers.google.com/workspace/chat/dialogs). When
   * specified, a loading indicator isn't shown. If specified for
   * an add-on, the entire card is stripped and nothing is shown in the client.
   *
   * [Google Chat apps](https://developers.google.com/workspace/chat):
   * </pre>
   *
   * <code>.google.apps.card.v1.Action.Interaction interaction = 5;</code>
   *
   * @return The interaction.
   */
  com.google.apps.card.v1.Action.Interaction getInteraction();
}
