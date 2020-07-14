// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

@AutoValue
public abstract class MethodDefinition implements AstNode {
  static final Reference RUNTIME_EXCEPTION_REFERENCE =
      ConcreteReference.withClazz(RuntimeException.class);

  // Required.
  public abstract ScopeNode scope();
  // Required.
  public abstract TypeNode returnType();
  // Required.
  public abstract IdentifierNode methodIdentifier();

  // TODO(xiahzhenliu): Add a Nullable JavaDoc here.
  public abstract ImmutableList<AnnotationNode> annotations();

  // Using a list helps with determinism in unit tests.
  public abstract ImmutableList<TypeNode> throwsExceptions();

  public abstract ImmutableList<VariableExpr> arguments();

  public abstract boolean isStatic();

  public abstract boolean isFinal();

  public abstract boolean isAbstract();

  public abstract boolean isConstructor();

  public abstract ImmutableList<Statement> body();

  @Nullable
  public abstract Expr returnExpr();

  abstract boolean isOverride();

  @Nullable
  abstract String name();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static Builder builder() {
    return new AutoValue_MethodDefinition.Builder()
        .setArguments(Collections.emptyList())
        .setIsAbstract(false)
        .setIsFinal(false)
        .setIsStatic(false)
        .setIsConstructor(false)
        .setAnnotations(Collections.emptyList())
        .setThrowsExceptions(Collections.emptyList())
        .setBody(Collections.emptyList())
        .setIsOverride(false);
  }

  public static Builder constructorBuilder() {
    return new AutoValue_MethodDefinition.Builder()
        .setArguments(Collections.emptyList())
        .setIsAbstract(false)
        .setIsFinal(false)
        .setIsStatic(false)
        .setIsConstructor(true)
        .setAnnotations(Collections.emptyList())
        .setThrowsExceptions(Collections.emptyList())
        .setBody(Collections.emptyList())
        .setIsOverride(false);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setScope(ScopeNode scope);

    public abstract Builder setReturnType(TypeNode type);

    public abstract Builder setName(String name);

    public Builder setAnnotations(List<AnnotationNode> annotations) {
      annotationsBuilder().addAll(annotations);
      return this;
    }

    public abstract Builder setIsStatic(boolean isStatic);

    public abstract Builder setIsFinal(boolean isFinal);

    public abstract Builder setIsAbstract(boolean isAbstract);

    public abstract Builder setIsConstructor(boolean isConstructor);

    public abstract Builder setThrowsExceptions(List<TypeNode> exceptionTypes);

    public abstract Builder setArguments(List<VariableExpr> arguments);

    public abstract Builder setBody(List<Statement> body);

    public abstract Builder setReturnExpr(Expr returnExpr);

    public abstract Builder setIsOverride(boolean isOverride);

    // Private accessors.

    abstract ImmutableList.Builder<AnnotationNode> annotationsBuilder();

    abstract String name();

    abstract TypeNode returnType();

    abstract boolean isOverride();

    abstract boolean isAbstract();

    abstract boolean isFinal();

    abstract boolean isStatic();

    abstract ImmutableList<Statement> body();

    abstract boolean isConstructor();

    abstract ScopeNode scope();

    abstract MethodDefinition autoBuild();

    abstract Builder setMethodIdentifier(IdentifierNode methodIdentifier);

    public MethodDefinition build() {
      if (isConstructor()) {
        Preconditions.checkState(
            TypeNode.isReferenceType(returnType()), "Constructor must return an object type.");
        setName(returnType().reference().name());
      } else {
        Preconditions.checkNotNull(name(), "Methods must have a name");
      }

      IdentifierNode methodIdentifier = IdentifierNode.builder().setName(name()).build();
      setMethodIdentifier(methodIdentifier);

      // Abstract and modifier checking.
      if (isAbstract()) {
        Preconditions.checkState(
            !isFinal() && !isStatic() && !scope().equals(ScopeNode.PRIVATE),
            "Abstract methods cannot be static, final, or private");
      }

      // If this method overrides another, ensure that the Override annotaiton is the last one.
      if (isOverride()) {
        annotationsBuilder().add(AnnotationNode.OVERRIDE);
      }

      MethodDefinition method = autoBuild();

      Preconditions.checkState(
          !method.scope().equals(ScopeNode.LOCAL),
          "Method scope must be either public, protected, or private");

      Preconditions.checkState(
          !method.returnType().equals(TypeNode.NULL), "Null is not a valid method return type");

      // Constructor checking.
      if (method.isConstructor()) {
        Preconditions.checkState(
            !method.isFinal() && !method.isStatic(), "Constructors cannot be static or final");
        Preconditions.checkState(!method.isOverride(), "A constructor cannot override another");
        Preconditions.checkState(
            method.returnExpr() == null, "A constructor cannot have a return expression");
        // Reference already checked at method name validation time.
        // TODO(unsupported): Constructors for templated types. This would require changing the
        // Reference API, which would be trivial. However, such constructors don't seem to be needed
        // yet.
        Preconditions.checkState(
            method.returnType().reference().generics().isEmpty(),
            "Constructors for templated classes are not yet supported");
      } else {
        // Return type validation and checking.
        boolean isLastStatementThrowExpr = false;
        Statement lastStatement;
        if (!body().isEmpty()
            && (lastStatement = body().get(body().size() - 1)) instanceof ExprStatement) {
          isLastStatementThrowExpr =
              ((ExprStatement) lastStatement).expression() instanceof ThrowExpr;
        }
        if (!method.returnType().equals(TypeNode.VOID) && !isLastStatementThrowExpr) {
          Preconditions.checkNotNull(
              method.returnExpr(),
              "Method with non-void return type must have a return expression");
        }

        if (!method.returnType().equals(TypeNode.VOID) && !isLastStatementThrowExpr) {
          Preconditions.checkNotNull(
              method.returnExpr(),
              "Method with non-void return type must have a return expression");
        }

        if (method.returnExpr() != null && !isLastStatementThrowExpr) {
          if (method.returnType().isPrimitiveType()) {
            Preconditions.checkState(
                method.returnExpr().type().isPrimitiveType()
                    && method.returnType().equals((method.returnExpr().type())),
                "Method primitive return type does not match the return expression type");

          } else {
            Preconditions.checkState(
                !method.returnExpr().type().isPrimitiveType()
                    && method.returnType().isSupertypeOrEquals(method.returnExpr().type()),
                "Method reference return type is not a subtype of the return expression type");
          }
        }
      }

      for (VariableExpr varExpr : method.arguments()) {
        Preconditions.checkState(
            varExpr.isDecl(),
            String.format(
                "Argument %s must be a variable declaration", varExpr.variable().identifier()));
      }

      for (TypeNode exceptionType : method.throwsExceptions()) {
        Preconditions.checkState(
            TypeNode.isExceptionType(exceptionType),
            String.format("Type %s is not an exception type", exceptionType.reference()));
        Preconditions.checkState(
            !RUNTIME_EXCEPTION_REFERENCE.isAssignableFrom(exceptionType.reference()),
            String.format(
                "RuntimeException type %s does not need to be thrown",
                exceptionType.reference().name()));
      }

      return method;
    }
  }
}
