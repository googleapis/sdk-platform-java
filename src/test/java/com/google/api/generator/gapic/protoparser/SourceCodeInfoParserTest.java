// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this protoFile except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.gapic.protoparser;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

import com.google.api.generator.gapic.model.SourceCodeInfoLocation;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SourceCodeInfoParserTest {
  private static final String BASIC_PROTO = "basic.proto";
  private static final String PROTO_DESCRIPTOR_SET = "test-proto.descriptorset";

  private SourceCodeInfoParser parser;
  private FileDescriptor protoFile;

  @Before
  public void setUp() throws Exception {
    parser = new SourceCodeInfoParser();
    protoFile = buildFileDescriptor();
  }

  @Test
  public void getServiceInfo() {
    SourceCodeInfoLocation location = parser.getLocation(protoFile.findServiceByName("FooService"));
    assertEquals(
        "This is a service description.\n It takes up multiple lines, like so.",
        location.getLeadingComments());

    location = parser.getLocation(protoFile.findServiceByName("BarService"));
    assertEquals("This is another service description.", location.getLeadingComments());
  }

  @Test
  public void getMethodInfo() {
    ServiceDescriptor service = protoFile.findServiceByName("FooService");
    SourceCodeInfoLocation location = parser.getLocation(service.findMethodByName("FooMethod"));
    assertEquals(
        "FooMethod does something.\n This comment also takes up multiple lines.",
        location.getLeadingComments());

    service = protoFile.findServiceByName("BarService");
    location = parser.getLocation(service.findMethodByName("BarMethod"));
    assertEquals("BarMethod does another thing.", location.getLeadingComments());
  }

  @Test
  public void getOuterMessageInfo() {
    Descriptor message = protoFile.findMessageTypeByName("FooMessage");
    SourceCodeInfoLocation location = parser.getLocation(message);
    assertEquals(
        "This is a message descxription.\n Lorum ipsum dolor sit amet consectetur adipiscing elit.",
        location.getLeadingComments());

    // Fields.
    location = parser.getLocation(message.findFieldByName("field_one"));
    assertEquals(
        "This is a field description for field_one.\n And here is the second line of that"
            + " description.",
        location.getLeadingComments());
    assertEquals("A field trailing comment.", location.getTrailingComments());

    location = parser.getLocation(message.findFieldByName("field_two"));
    assertEquals("This is another field description.", location.getLeadingComments());
    assertEquals("Another field trailing comment.", location.getTrailingComments());
  }

  @Test
  public void getInnerMessageInfo() {
    Descriptor message = protoFile.findMessageTypeByName("FooMessage");
    assertThat(message).isNotNull();
    message = message.findNestedTypeByName("BarMessage");

    SourceCodeInfoLocation location = parser.getLocation(message);
    assertEquals(
        "This is an inner message description for BarMessage.", location.getLeadingComments());

    // Fields.
    location = parser.getLocation(message.findFieldByName("field_three"));
    assertEquals("A third leading comment for field_three.", location.getLeadingComments());

    location = parser.getLocation(message.findFieldByName("field_two"));
    assertEquals("This is a block comment for field_two.", location.getLeadingComments());
  }

  @Test
  public void getOuterEnumInfo() {
    EnumDescriptor protoEnum = protoFile.findEnumTypeByName("OuterEnum");
    SourceCodeInfoLocation location = parser.getLocation(protoEnum);
    assertEquals("This is an outer enum.", location.getLeadingComments());

    // Enum fields.
    location = parser.getLocation(protoEnum.findValueByName("VALUE_UNSPECIFIED"));
    assertEquals("Another unspecified value.", location.getLeadingComments());
  }

  @Test
  public void getInnerEnumInfo() {
    Descriptor message = protoFile.findMessageTypeByName("FooMessage");
    EnumDescriptor protoEnum = message.findEnumTypeByName("FoodEnum");
    SourceCodeInfoLocation location = parser.getLocation(protoEnum);
    assertEquals("An inner enum.", location.getLeadingComments());

    // Enum fields.
    location = parser.getLocation(protoEnum.findValueByName("RICE"));
    assertEquals("😋 🍚.", location.getLeadingComments());
    location = parser.getLocation(protoEnum.findValueByName("CHOCOLATE"));
    assertEquals("🤤 🍫.", location.getLeadingComments());
  }

  @Test
  public void getOnoeofInfo() {
    Descriptor message = protoFile.findMessageTypeByName("FooMessage");
    OneofDescriptor protoOneof = message.getOneofs().get(0);
    SourceCodeInfoLocation location = parser.getLocation(protoOneof);
    assertEquals("An inner oneof.", location.getLeadingComments());

    location = parser.getLocation(protoOneof.getField(0));
    assertEquals("An InnerOneof comment for its field.", location.getLeadingComments());
  }

  /**
   * Parses a {@link FileDescriptorSet} from the descriptor of {@code basic.proto} and converts the
   * protos to {@link FileDescriptor} wrappers.
   *
   * @return the top level target protoFile descriptor
   */
  private static FileDescriptor buildFileDescriptor() throws Exception {
    InputStream testProto =
        SourceCodeInfoParserTest.class.getClassLoader().getResourceAsStream(PROTO_DESCRIPTOR_SET);
    if (testProto == null) { // TODO: only for Bazel build. Remove when we don't build with Bazel.
      testProto = new FileInputStream(PROTO_DESCRIPTOR_SET);
    }
    try (InputStream in = testProto) {
      List<FileDescriptorProto> protoFileList = FileDescriptorSet.parseFrom(in).getFileList();
      List<FileDescriptor> deps = new ArrayList<>();
      for (FileDescriptorProto proto : protoFileList) {
        FileDescriptor descriptor =
            FileDescriptor.buildFrom(proto, deps.toArray(new FileDescriptor[0]));
        if (descriptor.getName().endsWith(BASIC_PROTO)) {
          return descriptor;
        }
        deps.add(descriptor);
      }
    }
    return null;
  }
}
