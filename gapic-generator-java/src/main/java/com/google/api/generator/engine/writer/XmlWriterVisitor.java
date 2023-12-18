package com.google.api.generator.engine.writer;

import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AnonymousClassExpr;
import com.google.api.generator.engine.ast.ArithmeticOperationExpr;
import com.google.api.generator.engine.ast.ArrayExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.AssignmentOperationExpr;
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
import com.google.api.generator.engine.ast.SynchronizedStatement;
import com.google.api.generator.engine.ast.TernaryExpr;
import com.google.api.generator.engine.ast.ThrowExpr;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.UnaryOperationExpr;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.ast.WhileStatement;

public class XmlWriterVisitor implements AstNodeVisitor {

  @Override
  public void visit(IdentifierNode identifier) {

  }

  @Override
  public void visit(TypeNode type) {

  }

  @Override
  public void visit(ScopeNode scope) {

  }

  @Override
  public void visit(AnnotationNode annotation) {

  }

  @Override
  public void visit(ArrayExpr expr) {

  }

  @Override
  public void visit(ConcreteReference reference) {

  }

  @Override
  public void visit(VaporReference reference) {

  }

  @Override
  public void visit(ValueExpr valueExpr) {

  }

  @Override
  public void visit(VariableExpr variableExpr) {

  }

  @Override
  public void visit(TernaryExpr tenaryExpr) {

  }

  @Override
  public void visit(AssignmentExpr assignmentExpr) {

  }

  @Override
  public void visit(MethodInvocationExpr methodInvocationExpr) {

  }

  @Override
  public void visit(CastExpr castExpr) {

  }

  @Override
  public void visit(AnonymousClassExpr anonymousClassExpr) {

  }

  @Override
  public void visit(ThrowExpr throwExpr) {

  }

  @Override
  public void visit(InstanceofExpr instanceofExpr) {

  }

  @Override
  public void visit(NewObjectExpr newObjectExpr) {

  }

  @Override
  public void visit(EnumRefExpr enumRefExpr) {

  }

  @Override
  public void visit(ReturnExpr returnExpr) {

  }

  @Override
  public void visit(ReferenceConstructorExpr referenceConstructorExpr) {

  }

  @Override
  public void visit(ArithmeticOperationExpr arithmeticOperationExpr) {

  }

  @Override
  public void visit(UnaryOperationExpr unaryOperationExpr) {

  }

  @Override
  public void visit(RelationalOperationExpr relationalOperationExpr) {

  }

  @Override
  public void visit(LogicalOperationExpr logicalOperationExpr) {

  }

  @Override
  public void visit(AssignmentOperationExpr assignmentOperationExpr) {

  }

  @Override
  public void visit(LambdaExpr lambdaExpr) {

  }

  @Override
  public void visit(LineComment lineComment) {

  }

  @Override
  public void visit(BlockComment blockComment) {

  }

  @Override
  public void visit(JavaDocComment javaDocComment) {

  }

  @Override
  public void visit(ExprStatement exprStatement) {

  }

  @Override
  public void visit(BlockStatement blockStatement) {

  }

  @Override
  public void visit(IfStatement ifStatement) {

  }

  @Override
  public void visit(ForStatement forStatement) {

  }

  @Override
  public void visit(GeneralForStatement generalForStatement) {

  }

  @Override
  public void visit(WhileStatement whileStatement) {

  }

  @Override
  public void visit(TryCatchStatement tryCatchStatement) {

  }

  @Override
  public void visit(SynchronizedStatement synchronizedStatement) {

  }

  @Override
  public void visit(CommentStatement commentStatement) {

  }

  @Override
  public void visit(EmptyLineStatement emptyLineStatement) {

  }

  @Override
  public void visit(BreakStatement breakStatement) {

  }

  @Override
  public void visit(MethodDefinition methodDefinition) {

  }

  @Override
  public void visit(ClassDefinition classDefinition) {

  }

  @Override
  public void visit(PackageInfoDefinition packageInfoDefinition) {

  }
}
