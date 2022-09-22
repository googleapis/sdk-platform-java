package com.google.api.generator.gapic.model;

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.model.HttpBindings.HttpBinding;
import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;

public class HttpBindingsTest {

  public Field.Builder fieldBuilder;
  public HttpBinding.Builder httpBindingBuilder;

  @Before
  public void setUp() throws Exception {
    fieldBuilder = Field.builder().setName("doesNotMatter").setType(TypeNode.OBJECT);
    httpBindingBuilder = HttpBinding.builder().setName("doesNotMatter");
  }

  @Test
  public void isOptional_shouldReturnFalseIfFieldIsNull() {
    HttpBinding httpBinding = httpBindingBuilder.build();
    Truth.assertThat(httpBinding.isOptional()).isFalse();
  }

  @Test
  public void isOptional_shouldReturnFalseIfFieldExistAndIsOptionalIsFalse() {
    HttpBinding httpBinding =
        httpBindingBuilder.setField(fieldBuilder.setIsProto3Optional(false).build()).build();

    Truth.assertThat(httpBinding.isOptional()).isFalse();
  }

  @Test
  public void isOptional_shouldReturnTrueIfFieldExistAndIsOptionalIsTue() {
    HttpBinding httpBinding =
        httpBindingBuilder.setField(fieldBuilder.setIsProto3Optional(true).build()).build();

    Truth.assertThat(httpBinding.isOptional()).isTrue();
  }

  @Test
  public void isRepeated_shouldReturnFalseIfFieldIsNull() {
    HttpBinding httpBinding = httpBindingBuilder.build();
    Truth.assertThat(httpBinding.isRepeated()).isFalse();
  }

  @Test
  public void isRepeated_shouldReturnFalseIfFieldExistAndIsRepeatedIsFalse() {
    HttpBinding httpBinding =
        httpBindingBuilder.setField(fieldBuilder.setIsRepeated(false).build()).build();

    Truth.assertThat(httpBinding.isRepeated()).isFalse();
  }

  @Test
  public void isRepeated_shouldReturnTrueIfFieldExistAndIsRepeatedIsTue() {
    HttpBinding httpBinding =
        httpBindingBuilder.setField(fieldBuilder.setIsRepeated(true).build()).build();

    Truth.assertThat(httpBinding.isRepeated()).isTrue();
  }

  @Test
  public void isEnum_shouldReturnFalseIfFieldIsNull() {
    HttpBinding httpBinding = httpBindingBuilder.build();
    Truth.assertThat(httpBinding.isEnum()).isFalse();
  }

  @Test
  public void isEnum_shouldReturnFalseIfFieldExistAndIsEnumIsFalse() {
    HttpBinding httpBinding =
        httpBindingBuilder.setField(fieldBuilder.setIsEnum(false).build()).build();

    Truth.assertThat(httpBinding.isEnum()).isFalse();
  }

  @Test
  public void isEnum_shouldReturnTrueIfFieldExistAndIsEnumIsTue() {
    HttpBinding httpBinding =
        httpBindingBuilder.setField(fieldBuilder.setIsEnum(true).build()).build();

    Truth.assertThat(httpBinding.isEnum()).isTrue();
  }
}
