package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class ReferenceConstructorExpr implements Expr {
  private static final String PKG_JAVA_LANG = "java.lang";
  public enum KeywordKind {
    THIS,
    SUPER
  }

  public abstract TypeNode type();

  public abstract KeywordKind keywordKind();

  public abstract ImmutableList<Expr> arguments();

  public static Builder thisBuild() {
    return AutoValue_ReferenceConstructExpr
        .Builder
        .setArguments(Collections.emptyList())
        .setKeywordKind(KeywordKind.THIS);
  }

  @Override
  public void accept(AstNodeVisitor visitor) {

  }

  @AutoValue.Builder
  public abstract class Builder {
    public abstract Builder setType(TypeNode node);

    public abstract Builder setArguments(List<Expr> arguments);

    abstract Builder setKeyword(KeywordKind keywordKind);

    abstract ReferenceConstructorExpr autoBuild();

    public ReferenceConstructorExpr build() {
      ReferenceConstructorExpr referenceConstructorExpr = autoBuild();
      Preconditions.checkState(referenceConstructorExpr.type().isPrimitiveType(),
          "ReferenceConstructExpr type must be reference type. ");
      Preconditions.checkState(referenceConstructorExpr.type().reference().isFromPackage(PKG_JAVA_LANG),
          "ReferenceConstructExpr can only refer to custom object. ");
      return referenceConstructorExpr;
    }

  }
}
