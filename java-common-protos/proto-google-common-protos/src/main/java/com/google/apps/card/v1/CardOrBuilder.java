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

public interface CardOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.apps.card.v1.Card)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The header of the card. A header usually contains a leading image and a
   * title. Headers always appear at the top of a card.
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.CardHeader header = 1;</code>
   *
   * @return Whether the header field is set.
   */
  boolean hasHeader();
  /**
   *
   *
   * <pre>
   * The header of the card. A header usually contains a leading image and a
   * title. Headers always appear at the top of a card.
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.CardHeader header = 1;</code>
   *
   * @return The header.
   */
  com.google.apps.card.v1.Card.CardHeader getHeader();
  /**
   *
   *
   * <pre>
   * The header of the card. A header usually contains a leading image and a
   * title. Headers always appear at the top of a card.
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.CardHeader header = 1;</code>
   */
  com.google.apps.card.v1.Card.CardHeaderOrBuilder getHeaderOrBuilder();

  /**
   *
   *
   * <pre>
   * Contains a collection of widgets. Each section has its own, optional
   * header. Sections are visually separated by a line divider. For an example
   * in Google Chat apps, see [Define a section of a
   * card](https://developers.google.com/workspace/chat/design-components-card-dialog#define_a_section_of_a_card).
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Card.Section sections = 2;</code>
   */
  java.util.List<com.google.apps.card.v1.Card.Section> getSectionsList();
  /**
   *
   *
   * <pre>
   * Contains a collection of widgets. Each section has its own, optional
   * header. Sections are visually separated by a line divider. For an example
   * in Google Chat apps, see [Define a section of a
   * card](https://developers.google.com/workspace/chat/design-components-card-dialog#define_a_section_of_a_card).
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Card.Section sections = 2;</code>
   */
  com.google.apps.card.v1.Card.Section getSections(int index);
  /**
   *
   *
   * <pre>
   * Contains a collection of widgets. Each section has its own, optional
   * header. Sections are visually separated by a line divider. For an example
   * in Google Chat apps, see [Define a section of a
   * card](https://developers.google.com/workspace/chat/design-components-card-dialog#define_a_section_of_a_card).
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Card.Section sections = 2;</code>
   */
  int getSectionsCount();
  /**
   *
   *
   * <pre>
   * Contains a collection of widgets. Each section has its own, optional
   * header. Sections are visually separated by a line divider. For an example
   * in Google Chat apps, see [Define a section of a
   * card](https://developers.google.com/workspace/chat/design-components-card-dialog#define_a_section_of_a_card).
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Card.Section sections = 2;</code>
   */
  java.util.List<? extends com.google.apps.card.v1.Card.SectionOrBuilder>
      getSectionsOrBuilderList();
  /**
   *
   *
   * <pre>
   * Contains a collection of widgets. Each section has its own, optional
   * header. Sections are visually separated by a line divider. For an example
   * in Google Chat apps, see [Define a section of a
   * card](https://developers.google.com/workspace/chat/design-components-card-dialog#define_a_section_of_a_card).
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Card.Section sections = 2;</code>
   */
  com.google.apps.card.v1.Card.SectionOrBuilder getSectionsOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * The divider style between sections.
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.DividerStyle section_divider_style = 9;</code>
   *
   * @return The enum numeric value on the wire for sectionDividerStyle.
   */
  int getSectionDividerStyleValue();
  /**
   *
   *
   * <pre>
   * The divider style between sections.
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.DividerStyle section_divider_style = 9;</code>
   *
   * @return The sectionDividerStyle.
   */
  com.google.apps.card.v1.Card.DividerStyle getSectionDividerStyle();

  /**
   *
   *
   * <pre>
   * The card's actions. Actions are added to the card's toolbar menu.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   *
   * For example, the following JSON constructs a card action menu with
   * `Settings` and `Send Feedback` options:
   *
   * ```
   * "card_actions": [
   *   {
   *     "actionLabel": "Settings",
   *     "onClick": {
   *       "action": {
   *         "functionName": "goToView",
   *         "parameters": [
   *           {
   *             "key": "viewType",
   *             "value": "SETTING"
   *          }
   *         ],
   *         "loadIndicator": "LoadIndicator.SPINNER"
   *       }
   *     }
   *   },
   *   {
   *     "actionLabel": "Send Feedback",
   *     "onClick": {
   *       "openLink": {
   *         "url": "https://example.com/feedback"
   *       }
   *     }
   *   }
   * ]
   * ```
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Card.CardAction card_actions = 3;</code>
   */
  java.util.List<com.google.apps.card.v1.Card.CardAction> getCardActionsList();
  /**
   *
   *
   * <pre>
   * The card's actions. Actions are added to the card's toolbar menu.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   *
   * For example, the following JSON constructs a card action menu with
   * `Settings` and `Send Feedback` options:
   *
   * ```
   * "card_actions": [
   *   {
   *     "actionLabel": "Settings",
   *     "onClick": {
   *       "action": {
   *         "functionName": "goToView",
   *         "parameters": [
   *           {
   *             "key": "viewType",
   *             "value": "SETTING"
   *          }
   *         ],
   *         "loadIndicator": "LoadIndicator.SPINNER"
   *       }
   *     }
   *   },
   *   {
   *     "actionLabel": "Send Feedback",
   *     "onClick": {
   *       "openLink": {
   *         "url": "https://example.com/feedback"
   *       }
   *     }
   *   }
   * ]
   * ```
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Card.CardAction card_actions = 3;</code>
   */
  com.google.apps.card.v1.Card.CardAction getCardActions(int index);
  /**
   *
   *
   * <pre>
   * The card's actions. Actions are added to the card's toolbar menu.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   *
   * For example, the following JSON constructs a card action menu with
   * `Settings` and `Send Feedback` options:
   *
   * ```
   * "card_actions": [
   *   {
   *     "actionLabel": "Settings",
   *     "onClick": {
   *       "action": {
   *         "functionName": "goToView",
   *         "parameters": [
   *           {
   *             "key": "viewType",
   *             "value": "SETTING"
   *          }
   *         ],
   *         "loadIndicator": "LoadIndicator.SPINNER"
   *       }
   *     }
   *   },
   *   {
   *     "actionLabel": "Send Feedback",
   *     "onClick": {
   *       "openLink": {
   *         "url": "https://example.com/feedback"
   *       }
   *     }
   *   }
   * ]
   * ```
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Card.CardAction card_actions = 3;</code>
   */
  int getCardActionsCount();
  /**
   *
   *
   * <pre>
   * The card's actions. Actions are added to the card's toolbar menu.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   *
   * For example, the following JSON constructs a card action menu with
   * `Settings` and `Send Feedback` options:
   *
   * ```
   * "card_actions": [
   *   {
   *     "actionLabel": "Settings",
   *     "onClick": {
   *       "action": {
   *         "functionName": "goToView",
   *         "parameters": [
   *           {
   *             "key": "viewType",
   *             "value": "SETTING"
   *          }
   *         ],
   *         "loadIndicator": "LoadIndicator.SPINNER"
   *       }
   *     }
   *   },
   *   {
   *     "actionLabel": "Send Feedback",
   *     "onClick": {
   *       "openLink": {
   *         "url": "https://example.com/feedback"
   *       }
   *     }
   *   }
   * ]
   * ```
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Card.CardAction card_actions = 3;</code>
   */
  java.util.List<? extends com.google.apps.card.v1.Card.CardActionOrBuilder>
      getCardActionsOrBuilderList();
  /**
   *
   *
   * <pre>
   * The card's actions. Actions are added to the card's toolbar menu.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   *
   * For example, the following JSON constructs a card action menu with
   * `Settings` and `Send Feedback` options:
   *
   * ```
   * "card_actions": [
   *   {
   *     "actionLabel": "Settings",
   *     "onClick": {
   *       "action": {
   *         "functionName": "goToView",
   *         "parameters": [
   *           {
   *             "key": "viewType",
   *             "value": "SETTING"
   *          }
   *         ],
   *         "loadIndicator": "LoadIndicator.SPINNER"
   *       }
   *     }
   *   },
   *   {
   *     "actionLabel": "Send Feedback",
   *     "onClick": {
   *       "openLink": {
   *         "url": "https://example.com/feedback"
   *       }
   *     }
   *   }
   * ]
   * ```
   * </pre>
   *
   * <code>repeated .google.apps.card.v1.Card.CardAction card_actions = 3;</code>
   */
  com.google.apps.card.v1.Card.CardActionOrBuilder getCardActionsOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * Name of the card. Used as a card identifier in card navigation.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   * </pre>
   *
   * <code>string name = 4;</code>
   *
   * @return The name.
   */
  java.lang.String getName();
  /**
   *
   *
   * <pre>
   * Name of the card. Used as a card identifier in card navigation.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   * </pre>
   *
   * <code>string name = 4;</code>
   *
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString getNameBytes();

  /**
   *
   *
   * <pre>
   * The fixed footer shown at the bottom of this card.
   *
   * Setting `fixedFooter` without specifying a `primaryButton` or a
   * `secondaryButton` causes an error. For Chat apps, you can use fixed footers
   * in
   * [dialogs](https://developers.google.com/workspace/chat/dialogs), but not
   * [card
   * messages](https://developers.google.com/workspace/chat/create-messages#create).
   *
   * [Google Workspace Add-ons and Chat
   * apps](https://developers.google.com/workspace/extend):
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.CardFixedFooter fixed_footer = 5;</code>
   *
   * @return Whether the fixedFooter field is set.
   */
  boolean hasFixedFooter();
  /**
   *
   *
   * <pre>
   * The fixed footer shown at the bottom of this card.
   *
   * Setting `fixedFooter` without specifying a `primaryButton` or a
   * `secondaryButton` causes an error. For Chat apps, you can use fixed footers
   * in
   * [dialogs](https://developers.google.com/workspace/chat/dialogs), but not
   * [card
   * messages](https://developers.google.com/workspace/chat/create-messages#create).
   *
   * [Google Workspace Add-ons and Chat
   * apps](https://developers.google.com/workspace/extend):
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.CardFixedFooter fixed_footer = 5;</code>
   *
   * @return The fixedFooter.
   */
  com.google.apps.card.v1.Card.CardFixedFooter getFixedFooter();
  /**
   *
   *
   * <pre>
   * The fixed footer shown at the bottom of this card.
   *
   * Setting `fixedFooter` without specifying a `primaryButton` or a
   * `secondaryButton` causes an error. For Chat apps, you can use fixed footers
   * in
   * [dialogs](https://developers.google.com/workspace/chat/dialogs), but not
   * [card
   * messages](https://developers.google.com/workspace/chat/create-messages#create).
   *
   * [Google Workspace Add-ons and Chat
   * apps](https://developers.google.com/workspace/extend):
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.CardFixedFooter fixed_footer = 5;</code>
   */
  com.google.apps.card.v1.Card.CardFixedFooterOrBuilder getFixedFooterOrBuilder();

  /**
   *
   *
   * <pre>
   * In Google Workspace Add-ons, sets the display properties of the
   * `peekCardHeader`.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.DisplayStyle display_style = 6;</code>
   *
   * @return The enum numeric value on the wire for displayStyle.
   */
  int getDisplayStyleValue();
  /**
   *
   *
   * <pre>
   * In Google Workspace Add-ons, sets the display properties of the
   * `peekCardHeader`.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.DisplayStyle display_style = 6;</code>
   *
   * @return The displayStyle.
   */
  com.google.apps.card.v1.Card.DisplayStyle getDisplayStyle();

  /**
   *
   *
   * <pre>
   * When displaying contextual content, the peek card header acts as a
   * placeholder so that the user can navigate forward between the homepage
   * cards and the contextual cards.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.CardHeader peek_card_header = 7;</code>
   *
   * @return Whether the peekCardHeader field is set.
   */
  boolean hasPeekCardHeader();
  /**
   *
   *
   * <pre>
   * When displaying contextual content, the peek card header acts as a
   * placeholder so that the user can navigate forward between the homepage
   * cards and the contextual cards.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.CardHeader peek_card_header = 7;</code>
   *
   * @return The peekCardHeader.
   */
  com.google.apps.card.v1.Card.CardHeader getPeekCardHeader();
  /**
   *
   *
   * <pre>
   * When displaying contextual content, the peek card header acts as a
   * placeholder so that the user can navigate forward between the homepage
   * cards and the contextual cards.
   *
   * [Google Workspace
   * Add-ons](https://developers.google.com/workspace/add-ons):
   * </pre>
   *
   * <code>.google.apps.card.v1.Card.CardHeader peek_card_header = 7;</code>
   */
  com.google.apps.card.v1.Card.CardHeaderOrBuilder getPeekCardHeaderOrBuilder();
}
