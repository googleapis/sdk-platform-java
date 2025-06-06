/*
 * Copyright 2025 Google LLC
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
// source: google/shopping/type/types.proto

// Protobuf Java Version: 3.25.8
package com.google.shopping.type;

public interface PriceOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.shopping.type.Price)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The price represented as a number in micros (1 million micros is an
   * equivalent to one's currency standard unit, for example, 1 USD = 1000000
   * micros).
   * </pre>
   *
   * <code>optional int64 amount_micros = 1;</code>
   *
   * @return Whether the amountMicros field is set.
   */
  boolean hasAmountMicros();

  /**
   *
   *
   * <pre>
   * The price represented as a number in micros (1 million micros is an
   * equivalent to one's currency standard unit, for example, 1 USD = 1000000
   * micros).
   * </pre>
   *
   * <code>optional int64 amount_micros = 1;</code>
   *
   * @return The amountMicros.
   */
  long getAmountMicros();

  /**
   *
   *
   * <pre>
   * The currency of the price using three-letter acronyms according to [ISO
   * 4217](http://en.wikipedia.org/wiki/ISO_4217).
   * </pre>
   *
   * <code>optional string currency_code = 2;</code>
   *
   * @return Whether the currencyCode field is set.
   */
  boolean hasCurrencyCode();

  /**
   *
   *
   * <pre>
   * The currency of the price using three-letter acronyms according to [ISO
   * 4217](http://en.wikipedia.org/wiki/ISO_4217).
   * </pre>
   *
   * <code>optional string currency_code = 2;</code>
   *
   * @return The currencyCode.
   */
  java.lang.String getCurrencyCode();

  /**
   *
   *
   * <pre>
   * The currency of the price using three-letter acronyms according to [ISO
   * 4217](http://en.wikipedia.org/wiki/ISO_4217).
   * </pre>
   *
   * <code>optional string currency_code = 2;</code>
   *
   * @return The bytes for currencyCode.
   */
  com.google.protobuf.ByteString getCurrencyCodeBytes();
}
