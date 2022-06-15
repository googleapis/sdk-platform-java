package com.google.api.generator.gapic.composer.common;

import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.RelationalOperationExpr;
import com.google.api.generator.engine.ast.ReturnExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.common.collect.ImmutableList;

public class CodelabComposer {

  // public static int length(String word) {
  //  return word.length();
  // }
  public static MethodDefinition createdALengthMethod() {
    //word
    Variable variable = Variable.builder()
        .setName("word")
        .setType(TypeNode.STRING)
        .build();

    //word.length()
    ReturnExpr returnExpr = ReturnExpr.withExpr(MethodInvocationExpr.builder()
        .setExprReferenceExpr(VariableExpr.withVariable(variable))
        .setMethodName("length")
        .setReturnType(TypeNode.INT)
        .build());

    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setIsStatic(true)
        .setReturnType(TypeNode.INT)
        .setName("length")
        //String word
        .setArguments(
            ImmutableList.of(VariableExpr.builder().setVariable(variable).setIsDecl(true).build()))
        .setReturnExpr(returnExpr)
        .build();
  }

































  // protected static final boolean isFun(String word) {
  //  return word.equals("fun");
  // }
  public static MethodDefinition createdAFunMethod() {
    //word
    Variable variable = Variable.builder()
        .setName("word")
        .setType(TypeNode.STRING)
        .build();

    //word.equals("fun")
    ReturnExpr returnExpr = ReturnExpr.withExpr(MethodInvocationExpr.builder()
        .setExprReferenceExpr(VariableExpr.withVariable(variable))
        .setMethodName("equals")
        .setArguments(ValueExpr.withValue(StringObjectValue.withValue("fun")))
        .setReturnType(TypeNode.BOOLEAN)
        .build());

    return MethodDefinition.builder()
        .setScope(ScopeNode.PROTECTED)
        .setIsStatic(true)
        .setIsFinal(true)
        .setReturnType(TypeNode.BOOLEAN)
        .setName("isFun")
        //String word
        .setArguments(
            ImmutableList.of(VariableExpr.builder().setVariable(variable).setIsDecl(true).build()))
        .setReturnExpr(returnExpr)
        .build();
  }

  // protected static final boolean isFun(String word) {
  //  if (word == null) {
  //   return false;
  //  }
  //  return word.equals("fun");
  // }
  public static MethodDefinition createdARobustFunMethod() {
    //word
    Variable variable = Variable.builder()
        .setName("word")
        .setType(TypeNode.STRING)
        .build();

    Expr leftExpr = VariableExpr.withVariable(variable);
    Expr rightExpr = ValueExpr.createNullExpr();

    //return false;
    ReturnExpr nestedReturnExpr = ReturnExpr.withExpr(ValueExpr.withValue(
        PrimitiveValue.builder()
            .setType(TypeNode.BOOLEAN)
            .setValue("false")
            .build()));

    IfStatement ifStatement = IfStatement.builder()
        //if (word == null)
        .setConditionExpr(RelationalOperationExpr.equalToWithExprs(leftExpr, rightExpr))
        .setBody(ImmutableList.of(ExprStatement.withExpr(nestedReturnExpr)))
        .build();

    //word.equals("fun")
    ReturnExpr returnExpr = ReturnExpr.withExpr(MethodInvocationExpr.builder()
        .setExprReferenceExpr(VariableExpr.withVariable(variable))
        .setMethodName("equals")
        .setArguments(ValueExpr.withValue(StringObjectValue.withValue("fun")))
        .setReturnType(TypeNode.BOOLEAN)
        .build());
    return MethodDefinition.builder()
        .setScope(ScopeNode.PROTECTED)
        .setIsStatic(true)
        .setIsFinal(true)
        .setReturnType(TypeNode.BOOLEAN)
        .setName("isFun")
        //String word
        .setArguments(
            ImmutableList.of(VariableExpr.builder().setVariable(variable).setIsDecl(true).build()))
        .setBody(ImmutableList.of(ifStatement))
        .setReturnExpr(returnExpr)
        .build();
  }
}
