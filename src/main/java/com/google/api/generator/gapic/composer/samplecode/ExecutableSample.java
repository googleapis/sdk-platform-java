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
