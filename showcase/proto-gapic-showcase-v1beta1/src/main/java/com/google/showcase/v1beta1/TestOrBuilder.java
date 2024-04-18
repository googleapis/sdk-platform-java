// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/testing.proto

// Protobuf Java Version: 3.25.3
package com.google.showcase.v1beta1;

public interface TestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.Test)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The name of the test.
   * The tests/&#42; portion of the names are hard-coded, and do not change
   * from session to session.
   * </pre>
   *
   * <code>string name = 1;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <pre>
   * The name of the test.
   * The tests/&#42; portion of the names are hard-coded, and do not change
   * from session to session.
   * </pre>
   *
   * <code>string name = 1;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   * The expectation level for this test.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Test.ExpectationLevel expectation_level = 2;</code>
   * @return The enum numeric value on the wire for expectationLevel.
   */
  int getExpectationLevelValue();
  /**
   * <pre>
   * The expectation level for this test.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Test.ExpectationLevel expectation_level = 2;</code>
   * @return The expectationLevel.
   */
  com.google.showcase.v1beta1.Test.ExpectationLevel getExpectationLevel();

  /**
   * <pre>
   * A description of the test.
   * </pre>
   *
   * <code>string description = 3;</code>
   * @return The description.
   */
  java.lang.String getDescription();
  /**
   * <pre>
   * A description of the test.
   * </pre>
   *
   * <code>string description = 3;</code>
   * @return The bytes for description.
   */
  com.google.protobuf.ByteString
      getDescriptionBytes();

  /**
   * <pre>
   * The blueprints that will satisfy this test. There may be multiple blueprints
   * that can signal to the server that this test case is being exercised. Although
   * multiple blueprints are specified, only a single blueprint needs to be run to
   * signal that the test case was exercised.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Test.Blueprint blueprints = 4;</code>
   */
  java.util.List<com.google.showcase.v1beta1.Test.Blueprint> 
      getBlueprintsList();
  /**
   * <pre>
   * The blueprints that will satisfy this test. There may be multiple blueprints
   * that can signal to the server that this test case is being exercised. Although
   * multiple blueprints are specified, only a single blueprint needs to be run to
   * signal that the test case was exercised.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Test.Blueprint blueprints = 4;</code>
   */
  com.google.showcase.v1beta1.Test.Blueprint getBlueprints(int index);
  /**
   * <pre>
   * The blueprints that will satisfy this test. There may be multiple blueprints
   * that can signal to the server that this test case is being exercised. Although
   * multiple blueprints are specified, only a single blueprint needs to be run to
   * signal that the test case was exercised.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Test.Blueprint blueprints = 4;</code>
   */
  int getBlueprintsCount();
  /**
   * <pre>
   * The blueprints that will satisfy this test. There may be multiple blueprints
   * that can signal to the server that this test case is being exercised. Although
   * multiple blueprints are specified, only a single blueprint needs to be run to
   * signal that the test case was exercised.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Test.Blueprint blueprints = 4;</code>
   */
  java.util.List<? extends com.google.showcase.v1beta1.Test.BlueprintOrBuilder> 
      getBlueprintsOrBuilderList();
  /**
   * <pre>
   * The blueprints that will satisfy this test. There may be multiple blueprints
   * that can signal to the server that this test case is being exercised. Although
   * multiple blueprints are specified, only a single blueprint needs to be run to
   * signal that the test case was exercised.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Test.Blueprint blueprints = 4;</code>
   */
  com.google.showcase.v1beta1.Test.BlueprintOrBuilder getBlueprintsOrBuilder(
      int index);
}
