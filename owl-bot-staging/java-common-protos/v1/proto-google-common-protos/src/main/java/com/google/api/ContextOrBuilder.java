// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/context.proto

// Protobuf Java Version: 3.25.2
package com.google.api;

public interface ContextOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.api.Context)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * A list of RPC context rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.ContextRule rules = 1;</code>
   */
  java.util.List<com.google.api.ContextRule> 
      getRulesList();
  /**
   * <pre>
   * A list of RPC context rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.ContextRule rules = 1;</code>
   */
  com.google.api.ContextRule getRules(int index);
  /**
   * <pre>
   * A list of RPC context rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.ContextRule rules = 1;</code>
   */
  int getRulesCount();
  /**
   * <pre>
   * A list of RPC context rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.ContextRule rules = 1;</code>
   */
  java.util.List<? extends com.google.api.ContextRuleOrBuilder> 
      getRulesOrBuilderList();
  /**
   * <pre>
   * A list of RPC context rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.ContextRule rules = 1;</code>
   */
  com.google.api.ContextRuleOrBuilder getRulesOrBuilder(
      int index);
}