// Copyright 2021 Google LLC
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

package com.google.api.generator.gapic.composer.samplecode;

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.Statement;

import java.util.List;

public class ExecutableSample {
    final String samplePackageName;
    final String sampleMethodName;
    final List<AssignmentExpr> sampleVariableAssignments;
    final List<Statement> sampleBody;

    public ExecutableSample(String samplePackageName, String sampleMethodName,
                            List<AssignmentExpr> sampleVariableAssignments, List<Statement> sampleBody) {
        this.samplePackageName = samplePackageName;
        this.sampleMethodName = sampleMethodName;
        this.sampleVariableAssignments = sampleVariableAssignments;
        this.sampleBody = sampleBody;
    }
}
