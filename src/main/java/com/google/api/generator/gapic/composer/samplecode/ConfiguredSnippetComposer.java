// Copyright 2022 Google LLC
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
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.Comment;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.comment.CommentComposer;
import com.google.api.generator.gapic.model.ConfiguredSnippet;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicSnippetConfig;
import com.google.api.generator.gapic.model.RegionTag;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.SnippetConfigParser;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.cloud.tools.snippetgen.configlanguage.v1.SnippetConfig;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConfiguredSnippetComposer {

    static List<CommentStatement> fileHeader = Arrays.asList(CommentComposer.APACHE_LICENSE_COMMENT);

    // Use this to add swap fileHeader easily
    public static ClassDefinition addFileHeader(GapicSnippetConfig snippetConfig, List<CommentStatement> fileHeader){
        return ClassDefinition.builder().build();
    }

    private static List<Statement> composeMainBody(
            List<AssignmentExpr> sampleVariableAssignments, Statement invokeMethod) {
        List<ExprStatement> setVariables =
                sampleVariableAssignments.stream()
                        .map(var -> ExprStatement.withExpr(var))
                        .collect(Collectors.toList());
        List<Statement> body = new ArrayList<>(setVariables);
        body.add(invokeMethod);
        return body;
    }

    public static List<VariableExpr> composeSampleMethodArgs(
            List<AssignmentExpr> sampleVariableAssignments) {
        return sampleVariableAssignments.stream()
                .map(v -> v.variableExpr().toBuilder().setIsDecl(true).build())
                .collect(Collectors.toList());
    }

    // Compose main method
    private static MethodDefinition composeMainMethod(List<Statement> mainBody) {
        return MethodDefinition.builder()
                .setScope(ScopeNode.PUBLIC)
                .setIsStatic(true)
                .setReturnType(TypeNode.VOID)
                .setName("main")
                .setArguments(
                        VariableExpr.builder()
                                .setVariable(
                                        Variable.builder().setType(TypeNode.STRING_ARRAY).setName("args").build())
                                .setIsDecl(true)
                                .build())
                .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(Exception.class)))
                .setBody(mainBody)
                .build();
    }

    // Compose sample method
    private static MethodDefinition composeSampleMethod(
            String sampleMethodName,
            List<VariableExpr> sampleMethodArgs,
            List<Statement> sampleMethodBody) {
        return MethodDefinition.builder()
                .setScope(ScopeNode.PUBLIC)
                .setIsStatic(true)
                .setReturnType(TypeNode.VOID)
                .setName(sampleMethodName)
                .setArguments(sampleMethodArgs)
                .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(Exception.class)))
                .setBody(sampleMethodBody)
                .build();
    }

    public static ClassDefinition composeConfiguredSnippetClass(GapicSnippetConfig snippetConfig) {
        LineComment lineComment = LineComment.withComment("AUTO-GENERATED DOCUMENTATION AND CLASS");
        JavaDocComment javaDocComment =
                JavaDocComment.builder()
                        .addComment(GapicSnippetConfig.getConfiguredSnippetSnippetName(snippetConfig))
                        .addParagraph(GapicSnippetConfig.getConfiguredSnippetSnippetDescription(snippetConfig))
                        // for scratch stuff
                        .addComment(GapicSnippetConfig.getConfiguredSnippetCallType(snippetConfig))
//        JavaDocComment.builder()
//                .addParam(paramName1, paramDescription1)
                        .build();
        List<Statement> sampleBody = Arrays.asList(ExprStatement.withExpr(
                VariableExpr.builder()
                        .setVariable(Variable.builder().setType(TypeNode.OBJECT).setName("thing").build())
                        .setIsDecl(true)
                        .build()));
        MethodDefinition mainMethod = composeMainMethod(sampleBody);
            return ClassDefinition.builder()
                    // Test fileheader separately
//                    .setFileHeader(fileHeaderList)
                    .setHeaderCommentStatements(
                            Arrays.asList(
                                    CommentStatement.withComment(lineComment),
                                    CommentStatement.withComment(javaDocComment)))
                    .setPackageString(GapicSnippetConfig.getConfiguredSnippetPackageString(snippetConfig))
                    .setName(GapicSnippetConfig.getConfiguredSnippetRpcName(snippetConfig))
                    .setRegionTag(GapicSnippetConfig.getConfiguredSnippetRegionTag(snippetConfig))
                    .setScope(ScopeNode.PUBLIC)
                    .setMethods(ImmutableList.of(mainMethod))
                    .build();
    }

    public static ClassDefinition composeSnippetTest(GapicSnippetConfig snippetConfig) {
        return ClassDefinition.builder()
            .setName(GapicSnippetConfig.getConfiguredSnippetSnippetName(snippetConfig))
            .setIsNested(true)
            .setScope(ScopeNode.PUBLIC)
            .build();
}

}

