// Copyright 2026 Google LLC
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

package com.google.api.generator.gapic.composer;

import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.FieldDefinition;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import java.util.Arrays;
import java.util.List;

public class LibraryVersionClassComposer {
  private static final LibraryVersionClassComposer INSTANCE = new LibraryVersionClassComposer();

  private LibraryVersionClassComposer() {}

  public static LibraryVersionClassComposer instance() {
    return INSTANCE;
  }

  public GapicClass generate(GapicContext context, Service service) {
    String packageName = service.pakkage();
    String className = "Version";

    // {x-version-update-start:[artifact]:current}
    // public static String VERSION = "0.0.0-SNAPSHOT";
    // {x-version-update-end}

    String artifact = context.artifact();
    String artifactId = artifact;
    if (artifact != null && artifact.contains(":")) {
      artifactId = artifact.split(":")[1];
    }

    FieldDefinition versionField =
        FieldDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setIsFinal(true)
            .setType(TypeNode.STRING)
            .setName("VERSION")
            .setAssignmentExpr(Expr.withValue("0.0.0-SNAPSHOT"))
            .build();

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageName(packageName)
            .setAnnotations(
                Arrays.asList(
                    AnnotationNode.builder()
                        .setType(TypeNode.withReference(com.google.api.core.InternalApi.class))
                        .setDescription("For internal use only")
                        .build()))
            .setScope(ScopeNode.PUBLIC)
            .setIsFinal(true)
            .setName(className)
            .setStatements(
                Arrays.asList(
                    CommentStatement.withComment(
                        LineComment.withComment(
                            String.format("{x-version-update-start:%s:current}", artifactId))),
                    ExprStatement.withExpr(versionField.assignmentExpr()),
                    CommentStatement.withComment(LineComment.withComment("{x-version-update-end}"))))
            .build();

    // Re-evaluating the class definition because I need to include the field itself,
    // but the engine's ClassDefinition handles fields separately.
    // However, I need the comments around the field.

    classDef =
        ClassDefinition.builder()
            .setPackageName(packageName)
            .setAnnotations(
                Arrays.asList(
                    AnnotationNode.builder()
                        .setType(TypeNode.withReference(com.google.api.core.InternalApi.class))
                        .setDescription("For internal use only")
                        .build()))
            .setScope(ScopeNode.PUBLIC)
            .setIsFinal(true)
            .setName(className)
            .setFields(Arrays.asList(versionField))
            .build();

    // Since I can't easily put comments around fields in the current engine,
    // I will try to see if I can use statements or just accept standard field generation for now
    // if the engine doesn't support comments on fields.
    // Actually, looking at the requested template, it's a field.

    return GapicClass.create(GapicClass.Kind.MAIN, classDef);
  }
}
