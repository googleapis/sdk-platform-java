package com.google.api.generator.gapic.composer.utils;

import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AnonymousClassExpr;
import com.google.api.generator.engine.ast.ArithmeticOperationExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.AssignmentOperationExpr;
import com.google.api.generator.engine.ast.AstNode;
import com.google.api.generator.engine.ast.AstNodeVisitor;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.BlockStatement;
import com.google.api.generator.engine.ast.BreakStatement;
import com.google.api.generator.engine.ast.CastExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.EmptyLineStatement;
import com.google.api.generator.engine.ast.EnumRefExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ForStatement;
import com.google.api.generator.engine.ast.GeneralForStatement;
import com.google.api.generator.engine.ast.IdentifierNode;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.InstanceofExpr;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.LambdaExpr;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.LogicalOperationExpr;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.PackageInfoDefinition;
import com.google.api.generator.engine.ast.ReferenceConstructorExpr;
import com.google.api.generator.engine.ast.RelationalOperationExpr;
import com.google.api.generator.engine.ast.ReturnExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.SynchronizedStatement;
import com.google.api.generator.engine.ast.TernaryExpr;
import com.google.api.generator.engine.ast.ThrowExpr;
import com.google.api.generator.engine.ast.ThrowExpr.Builder;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.UnaryOperationExpr;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.ast.WhileStatement;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class AstTransformer implements AstNodeVisitor {

  @Nullable private AstNode node = null;

  @Override
  public void visit(IdentifierNode identifier) {
    node = identifier;
  }

  @Override
  public void visit(TypeNode type) {
    node = type;
  }

  @Override
  public void visit(ScopeNode scope) {
    node = scope;
  }

  @Override
  public void visit(AnnotationNode annotation) {
    node = annotation;
  }

  @Override
  public void visit(ConcreteReference reference) {
    node = reference;
  }

  @Override
  public void visit(VaporReference reference) {
    node = reference;
  }

  @Override
  public void visit(ValueExpr valueExpr) {
    node = valueExpr;
  }

  @Override
  public void visit(VariableExpr variableExpr) {
    node = variableExpr;
  }

  @Override
  public void visit(TernaryExpr tenaryExpr) {
    Expr conditionExpr = transform(tenaryExpr.conditionExpr(), Expr.class);
    Expr thenExpr = transform(tenaryExpr.thenExpr(), Expr.class);
    Expr elseExpr = transform(tenaryExpr.elseExpr(), Expr.class);
    node =
        tenaryExpr.toBuilder()
            .setConditionExpr(conditionExpr)
            .setThenExpr(thenExpr)
            .setElseExpr(elseExpr)
            .build();
  }

  @Override
  public void visit(AssignmentExpr assignmentExpr) {
    Expr valueExpr = transform(assignmentExpr.valueExpr(), Expr.class);
    node = assignmentExpr.toBuilder().setValueExpr(valueExpr).build();
  }

  @Override
  public void visit(MethodInvocationExpr methodInvocationExpr) {
    node = methodInvocationExpr;
  }

  @Override
  public void visit(CastExpr castExpr) {
    Expr expr = transform(castExpr.expr(), Expr.class);
    node = castExpr.toBuilder().setExpr(expr).build();
  }

  @Override
  public void visit(AnonymousClassExpr anonymousClassExpr) {
    node = anonymousClassExpr; // TODO: transform to lambda if possible
  }

  @Override
  public void visit(ThrowExpr throwExpr) {
    Builder builder = throwExpr.toBuilder();
    Expr currentThrowExpr = throwExpr.throwExpr();
    Expr currentMessageExpr = throwExpr.messageExpr();
    Expr currentCauseExpr = throwExpr.causeExpr();
    if (currentThrowExpr != null) {
      builder.setThrowExpr(transform(currentThrowExpr, Expr.class));
    }
    if (currentMessageExpr != null) {
      builder.setMessageExpr(transform(currentMessageExpr, Expr.class));
    }
    if (currentCauseExpr != null) {
      builder.setCauseExpr(transform(currentCauseExpr, Expr.class));
    }
    node = builder.build();
  }

  @Override
  public void visit(InstanceofExpr instanceofExpr) {
    Expr expr = transform(instanceofExpr.expr(), Expr.class);
    node = instanceofExpr.toBuilder().setExpr(expr).build();
  }

  @Override
  public void visit(NewObjectExpr newObjectExpr) {
    List<Expr> arguments = transform(newObjectExpr.arguments(), Expr.class);
    node = newObjectExpr.toBuilder().setArguments(arguments).build();
  }

  @Override
  public void visit(EnumRefExpr enumRefExpr) {
    node = enumRefExpr;
  }

  @Override
  public void visit(ReturnExpr returnExpr) {
    Expr expr = transform(returnExpr.expr(), Expr.class);
    node = returnExpr.toBuilder().setExpr(expr).build();
  }

  @Override
  public void visit(ReferenceConstructorExpr referenceConstructorExpr) {
    List<Expr> arguments = transform(referenceConstructorExpr.arguments(), Expr.class);
    node = referenceConstructorExpr.toBuilder().setArguments(arguments).build();
  }

  @Override
  public void visit(ArithmeticOperationExpr arithmeticOperationExpr) {
    Expr lhsExpr = transform(arithmeticOperationExpr.lhsExpr(), Expr.class);
    Expr rhsExpr = transform(arithmeticOperationExpr.rhsExpr(), Expr.class);
    node = arithmeticOperationExpr.toBuilder().setLhsExpr(lhsExpr).setRhsExpr(rhsExpr).build();
  }

  @Override
  public void visit(UnaryOperationExpr unaryOperationExpr) {
    Expr expr = transform(unaryOperationExpr.expr(), Expr.class);
    node = unaryOperationExpr.toBuilder().setExpr(expr).build();
  }

  @Override
  public void visit(RelationalOperationExpr relationalOperationExpr) {
    Expr lhsExpr = transform(relationalOperationExpr.lhsExpr(), Expr.class);
    Expr rhsExpr = transform(relationalOperationExpr.rhsExpr(), Expr.class);
    node = relationalOperationExpr.toBuilder().setLhsExpr(lhsExpr).setRhsExpr(rhsExpr).build();
  }

  @Override
  public void visit(LogicalOperationExpr logicalOperationExpr) {
    Expr lhsExpr = transform(logicalOperationExpr.lhsExpr(), Expr.class);
    Expr rhsExpr = transform(logicalOperationExpr.rhsExpr(), Expr.class);
    node = logicalOperationExpr.toBuilder().setLhsExpr(lhsExpr).setRhsExpr(rhsExpr).build();
  }

  @Override
  public void visit(AssignmentOperationExpr assignmentOperationExpr) {
    Expr valueExpr = transform(assignmentOperationExpr.valueExpr(), Expr.class);
    node = assignmentOperationExpr.toBuilder().setValueExpr(valueExpr).build();
  }

  @Override
  public void visit(LambdaExpr lambdaExpr) {
    List<Statement> body = transform(lambdaExpr.body(), Statement.class);
    ReturnExpr returnExpr = transform(lambdaExpr.returnExpr(), ReturnExpr.class);
    node = lambdaExpr.toBuilder().setBody(body).setReturnExpr(returnExpr).build();
  }

  @Override
  public void visit(LineComment lineComment) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void visit(BlockComment blockComment) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void visit(JavaDocComment javaDocComment) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void visit(ExprStatement exprStatement) {
    Expr expression = transform(exprStatement.expression(), Expr.class);
    node = exprStatement.toBuilder().setExpression(expression).build();
  }

  @Override
  public void visit(BlockStatement blockStatement) {
    List<Statement> body = transform(blockStatement.body(), Statement.class);
    node = blockStatement.toBuilder().setBody(body).build();
  }

  @Override
  public void visit(IfStatement ifStatement) {
    Expr conditionExpr = transform(ifStatement.conditionExpr(), Expr.class);
    List<Statement> body = transform(ifStatement.body(), Statement.class);
    List<Statement> elseBody = transform(ifStatement.elseBody(), Statement.class);
    Map<Expr, List<Statement>> elseIfs =
        ifStatement.elseIfs().entrySet().stream()
            .collect(
                Collectors.toMap(
                    e -> transform(e.getKey(), Expr.class),
                    e -> transform(e.getValue(), Statement.class)));
    node =
        ifStatement.toBuilder()
            .setConditionExpr(conditionExpr)
            .setBody(body)
            .setElseBody(elseBody)
            .setElseIfs(ImmutableMap.copyOf(elseIfs))
            .build();
  }

  @Override
  public void visit(ForStatement forStatement) {
    Expr collectionExpr = transform(forStatement.collectionExpr(), Expr.class);
    List<Statement> body = transform(forStatement.body(), Statement.class);
    node = forStatement.toBuilder().setCollectionExpr(collectionExpr).setBody(body).build();
  }

  @Override
  public void visit(GeneralForStatement generalForStatement) {
    Expr initializationExpr = transform(generalForStatement.initializationExpr(), Expr.class);
    Expr terminationExpr = transform(generalForStatement.terminationExpr(), Expr.class);
    Expr updateExpr = transform(generalForStatement.updateExpr(), Expr.class);
    List<Statement> body = transform(generalForStatement.body(), Statement.class);
    node =
        generalForStatement.toBuilder()
            .setInitializationExpr(initializationExpr)
            .setTerminationExpr(terminationExpr)
            .setUpdateExpr(updateExpr)
            .setBody(body)
            .build();
  }

  @Override
  public void visit(WhileStatement whileStatement) {
    List<Statement> body = transform(whileStatement.body(), Statement.class);
    Expr conditionExpr = transform(whileStatement.conditionExpr(), Expr.class);
    node = whileStatement.toBuilder().setBody(body).setConditionExpr(conditionExpr).build();
  }

  @Override
  public void visit(TryCatchStatement tryCatchStatement) {
    List<Statement> tryBody = transform(tryCatchStatement.tryBody(), Statement.class);
    List<List<Statement>> catchBlocks =
        tryCatchStatement.catchBlocks().stream()
            .map(block -> transform(block, Statement.class))
            .collect(Collectors.toList());
    node = tryCatchStatement.toBuilder().setTryBody(tryBody).setCatchBlocks(catchBlocks).build();
  }

  @Override
  public void visit(SynchronizedStatement synchronizedStatement) {
    List<Statement> body = transform(synchronizedStatement.body(), Statement.class);
    node = synchronizedStatement.toBuilder().setBody(body).build();
  }

  @Override
  public void visit(CommentStatement commentStatement) {
    node = commentStatement;
  }

  @Override
  public void visit(EmptyLineStatement emptyLineStatement) {
    node = emptyLineStatement;
  }

  @Override
  public void visit(BreakStatement breakStatement) {
    node = breakStatement;
  }

  @Override
  public void visit(MethodDefinition methodDefinition) {
    List<Statement> newMethods = transform(methodDefinition.body(), Statement.class);
    node = methodDefinition.toBuilder().setBody(newMethods).build();
  }

  @Override
  public void visit(ClassDefinition classDefinition) {
    List<MethodDefinition> newMethods =
        transform(classDefinition.methods(), MethodDefinition.class);
    node = classDefinition.toBuilder().setMethods(newMethods).build();
  }

  @Override
  public void visit(PackageInfoDefinition packageInfoDefinition) {
    node = packageInfoDefinition;
  }

  private static <T extends AstNode> List<T> transform(
      List<? extends AstNode> nodes, Class<T> clazz) {
    return nodes.stream().map(node -> transform(node, clazz)).collect(Collectors.toList());
  }

  private static <T extends AstNode> List<T> transform(
      ImmutableList<? extends AstNode> nodes, Class<T> clazz) {
    return nodes.stream().map(node -> transform(node, clazz)).collect(Collectors.toList());
  }

  private static <T extends AstNode> T transform(AstNode node, Class<T> clazz) {
    Preconditions.checkNotNull(node);
    AstTransformer transformer = new AstTransformer();
    node.accept(transformer);
    return clazz.cast(transformer.getTransformedAst());
  }

  public AstNode getTransformedAst() {
    Preconditions.checkState(node != null, "visitor has not been called");
    return node;
  }
}
