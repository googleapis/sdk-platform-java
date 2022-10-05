package com.google.api.generator.spring.utils;

import com.google.api.generator.engine.ast.ArithmeticOperationExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LoggerUtils {

  public static Statement getLoggerDeclarationExpr(String className, Map<String, TypeNode> types) {

    Variable loggerVar = Variable.builder().setName("LOGGER").setType(types.get("Log")).build();
    VariableExpr loggerExpr =
        VariableExpr.builder()
            .setVariable(loggerVar)
            .setScope(ScopeNode.PRIVATE)
            .setIsStatic(true)
            .setIsFinal(true)
            .setIsDecl(true)
            .build();

    MethodInvocationExpr loggerValueExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(types.get("LogFactory"))
            .setMethodName("getLog")
            .setArguments(
                VariableExpr.builder()
                    .setVariable(
                        Variable.builder().setType(TypeNode.CLASS_OBJECT).setName("class").build())
                    .setStaticReferenceType(types.get(className))
                    .build())
            .setReturnType(types.get("Log"))
            .build();

    AssignmentExpr loggerAssignmentExpr =
        AssignmentExpr.builder().setVariableExpr(loggerExpr).setValueExpr(loggerValueExpr).build();

    return ExprStatement.withExpr(loggerAssignmentExpr);
  }

  public static ExprStatement createLoggerStatement(Expr value, Map<String, TypeNode> types) {
    Variable loggerVariable =
        Variable.builder().setName("LOGGER").setType(types.get("Log")).build();
    MethodInvocationExpr loggerCallExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(VariableExpr.withVariable(loggerVariable))
            .setMethodName("info")
            .setArguments(value)
            .build();
    return ExprStatement.withExpr(loggerCallExpr);
  }

  public static TypeNode getLoggerType() {
    return createType("Log", "org.apache.commons.logging");
  }

  public static TypeNode getLoggerFactoryType() {
    return createType("LogFactory", "org.apache.commons.logging");
  }

  private static TypeNode createType(String className, String pakkage) {
    TypeNode loggerType =
        TypeNode.withReference(
            VaporReference.builder().setName(className).setPakkage(pakkage).build());
    return loggerType;
  }

  public static Expr concatManyWithExprs(Expr... exprs) {
    List<Expr> exprList = Arrays.asList(exprs);
    return concatManyWithExprsHelper(Optional.empty(), exprList);
  }

  private static Expr concatManyWithExprsHelper(Optional<Expr> current, List<Expr> exprs) {
    if (!current.isPresent()) {
      return concatManyWithExprsHelper(Optional.of(exprs.get(0)), exprs.subList(1, exprs.size()));
    }
    if (exprs.size() == 1) {
      return ArithmeticOperationExpr.concatWithExprs(current.get(), exprs.get(0));
    }
    return ArithmeticOperationExpr.concatWithExprs(
        current.get(),
        concatManyWithExprsHelper(Optional.of(exprs.get(0)), exprs.subList(1, exprs.size())));
  }
}
