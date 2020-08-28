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

<<<<<<< HEAD
public interface OperationExpr extends Expr {
=======
import com.google.api.generator.engine.lexicon.OperatorKind;
import javax.annotation.Nullable;

public interface OperationExpr extends Expr {
  Expr firstExpression();

  @Nullable
  Expr secondExpression();
>>>>>>> 7bb0f2b43a3139b8cf31c4f55287d441f597e2aa

  OperatorKind operatorKind();
}
