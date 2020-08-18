package com.google.api.generator.engine.ast;

import com.google.api.generator.engine.lexicon.OperatorKind;
import javax.annotation.Nullable;

public interface OperationExpr extends Expr {
  Expr firstExpression();

  @Nullable
  Expr secondExpression();

  OperatorKind operatorKind();
}
