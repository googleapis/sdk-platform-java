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

package com.google.api.generator.engine.writer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.google.api.generator.engine.ast.AnonymousClassExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.AstNode;
import com.google.api.generator.engine.ast.CastExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.EnumRefExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.InstanceofExpr;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.ThrowExpr;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.common.base.Function;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ImportWriterVisitorTest {
  private static final String CURRENT_PACKAGE = "com.google.api.generator.engine.foobar";
  private ImportWriterVisitor writerVisitor;

  @Before
  public void setUp() {
    writerVisitor = new ImportWriterVisitor(CURRENT_PACKAGE);
  }

  @Test
  public void writeAssignmentExprImports() {
    Variable variable =
        Variable.builder().setName("clazz").setType(createType(AstNode.class)).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    TypeNode someType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("SomeClass")
                .setPakkage("com.google.api.some.pakkage")
                .build());

    MethodInvocationExpr valueExpr =
        MethodInvocationExpr.builder()
            .setMethodName("createClass")
            .setStaticReferenceType(someType)
            .setReturnType(createType(ClassDefinition.class))
            .build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();

    assignExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(3),
            "import com.google.api.generator.engine.ast.AstNode;\n",
            "import com.google.api.generator.engine.ast.ClassDefinition;\n",
            "import com.google.api.some.pakkage.SomeClass;\n\n"));
  }

  @Test
  public void writeAssignmentExprImports_concreteAndNestedGenerics() {
    List<Reference> nestedSubGenerics =
        Arrays.asList(
            ConcreteReference.withClazz(ClassDefinition.class),
            ConcreteReference.withClazz(AstNode.class));
    Reference nestedGenericRef =
        ConcreteReference.builder()
            .setClazz(Map.Entry.class)
            .setIsStaticImport(true)
            .setGenerics(nestedSubGenerics)
            .build();

    List<Reference> subGenerics =
        Arrays.asList(ConcreteReference.withClazz(AssignmentExpr.class), nestedGenericRef);
    Reference genericRef =
        ConcreteReference.builder()
            .setClazz(Map.Entry.class)
            .setIsStaticImport(false)
            .setGenerics(subGenerics)
            .build();
    Reference reference =
        ConcreteReference.builder()
            .setClazz(List.class)
            .setGenerics(Arrays.asList(genericRef))
            .build();
    TypeNode type = TypeNode.withReference(reference);
    Variable variable = Variable.builder().setName("clazz").setType(type).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Reference returnReference =
        ConcreteReference.builder()
            .setClazz(ArrayList.class)
            .setGenerics(Arrays.asList(genericRef))
            .build();
    MethodInvocationExpr valueExpr =
        MethodInvocationExpr.builder()
            .setMethodName("doSomething")
            .setReturnType(TypeNode.withReference(returnReference))
            .build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();

    assignExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(7),
            "import static java.util.Map.Entry;\n\n",
            "import com.google.api.generator.engine.ast.AssignmentExpr;\n",
            "import com.google.api.generator.engine.ast.AstNode;\n",
            "import com.google.api.generator.engine.ast.ClassDefinition;\n",
            "import java.util.ArrayList;\n",
            "import java.util.List;\n",
            "import java.util.Map;\n\n"));
  }

  @Test
  public void writeAssignmentExprImports_static() {
    List<Reference> generics =
        Arrays.asList(
            ConcreteReference.withClazz(AssignmentExpr.class),
            ConcreteReference.withClazz(AstNode.class));
    Reference reference =
        ConcreteReference.builder()
            .setClazz(Map.Entry.class)
            .setIsStaticImport(true)
            .setGenerics(generics)
            .build();
    assertTrue(reference.isStaticImport());

    TypeNode type = TypeNode.withReference(reference);
    Variable variable = Variable.builder().setName("clazz").setType(type).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    MethodInvocationExpr valueExpr =
        MethodInvocationExpr.builder()
            .setMethodName("doSomething")
            .setReturnType(TypeNode.withReference(reference))
            .build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();

    assignExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(3),
            "import static java.util.Map.Entry;\n\n",
            "import com.google.api.generator.engine.ast.AssignmentExpr;\n",
            "import com.google.api.generator.engine.ast.AstNode;\n\n"));
  }

  @Test
  public void writeAssignmentExprImports_notStatic() {
    List<Reference> generics =
        Arrays.asList(
            ConcreteReference.withClazz(AssignmentExpr.class),
            ConcreteReference.withClazz(AstNode.class));
    Reference reference =
        ConcreteReference.builder()
            .setClazz(Map.Entry.class)
            .setIsStaticImport(false)
            .setGenerics(generics)
            .build();
    assertFalse(reference.isStaticImport());

    TypeNode type = TypeNode.withReference(reference);
    Variable variable = Variable.builder().setName("clazz").setType(type).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    MethodInvocationExpr valueExpr =
        MethodInvocationExpr.builder()
            .setMethodName("doSomething")
            .setReturnType(TypeNode.withReference(reference))
            .build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();

    assignExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(3),
            "import com.google.api.generator.engine.ast.AssignmentExpr;\n",
            "import com.google.api.generator.engine.ast.AstNode;\n",
            "import java.util.Map;\n\n"));
  }

  @Test
  public void writeCastExprImports() {
    TypeNode type = TypeNode.withReference(ConcreteReference.withClazz(AssignmentExpr.class));
    Variable variable = Variable.builder().setName("expr").setType(type).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    CastExpr castExpr =
        CastExpr.builder()
            .setType(TypeNode.withReference(ConcreteReference.withClazz(Expr.class)))
            .setExpr(variableExpr)
            .build();
    castExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(2),
            "import com.google.api.generator.engine.ast.AssignmentExpr;\n",
            "import com.google.api.generator.engine.ast.Expr;\n\n"));
  }

  @Test
  public void importFromVaporAndConcreteReferences() {
    Reference mapReference =
        ConcreteReference.builder()
            .setClazz(HashMap.class)
            .setGenerics(
                Arrays.asList(
                    VaporReference.builder().setName("String").setPakkage("java.lang").build(),
                    ConcreteReference.withClazz(MethodDefinition.class)))
            .build();
    Reference outerMapReference =
        VaporReference.builder()
            .setName("HashMap")
            .setPakkage("java.util")
            .setGenerics(Arrays.asList(mapReference, mapReference))
            .build();
    Reference listReference =
        VaporReference.builder()
            .setName("List")
            .setPakkage("java.util")
            .setGenerics(Arrays.asList(outerMapReference))
            .build();
    assertEquals(
        listReference.name(),
        "List<HashMap<HashMap<String, MethodDefinition>, HashMap<String, MethodDefinition>>>");

    TypeNode type = TypeNode.withReference(listReference);
    VariableExpr varExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setName("foobar").setType(type).build())
            .setIsDecl(true)
            .build();

    varExpr.accept(writerVisitor);

    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(3),
            "import com.google.api.generator.engine.ast.MethodDefinition;\n",
            "import java.util.HashMap;\n",
            "import java.util.List;\n\n"));
  }

  @Test
  public void writeVariableExprImports_basic() {
    Variable variable =
        Variable.builder()
            .setName("expr")
            .setType(TypeNode.withReference(ConcreteReference.withClazz(Expr.class)))
            .build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    variableExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(createLines(1), "import com.google.api.generator.engine.ast.Expr;\n\n"));
  }

  @Test
  public void writeVariableExprImports_reference() {
    Variable variable =
        Variable.builder()
            .setName("expr")
            .setType(TypeNode.withReference(ConcreteReference.withClazz(Expr.class)))
            .build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    Variable subVariable =
        Variable.builder()
            .setName("assignExpr")
            .setType(TypeNode.withReference(ConcreteReference.withClazz(AssignmentExpr.class)))
            .build();
    variableExpr =
        VariableExpr.builder().setVariable(subVariable).setExprReferenceExpr(variableExpr).build();
    variableExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(2),
            "import com.google.api.generator.engine.ast.AssignmentExpr;\n",
            "import com.google.api.generator.engine.ast.Expr;\n\n"));
  }

  @Test
  public void writeVariableExprImports_nestedReference() {
    Variable variable =
        Variable.builder()
            .setName("expr")
            .setType(TypeNode.withReference(ConcreteReference.withClazz(Expr.class)))
            .build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    Variable subVariable =
        Variable.builder()
            .setName("assignExpr")
            .setType(TypeNode.withReference(ConcreteReference.withClazz(AssignmentExpr.class)))
            .build();
    variableExpr =
        VariableExpr.builder().setVariable(subVariable).setExprReferenceExpr(variableExpr).build();
    subVariable =
        Variable.builder()
            .setName("anotherExpr")
            .setType(TypeNode.withReference(ConcreteReference.withClazz(VariableExpr.class)))
            .build();
    variableExpr =
        VariableExpr.builder().setVariable(subVariable).setExprReferenceExpr(variableExpr).build();

    variableExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(3),
            "import com.google.api.generator.engine.ast.AssignmentExpr;\n",
            "import com.google.api.generator.engine.ast.Expr;\n",
            "import com.google.api.generator.engine.ast.VariableExpr;\n\n"));
  }

  @Test
  public void writeAnonymousClassExprImports() {
    // [Constructing] Function<List<IOException>, MethodDefinition>
    ConcreteReference exceptionListRef =
        ConcreteReference.builder()
            .setClazz(List.class)
            .setGenerics(Arrays.asList(ConcreteReference.withClazz(IOException.class)))
            .build();
    ConcreteReference methodDefinitionRef = ConcreteReference.withClazz(MethodDefinition.class);
    ConcreteReference ref =
        ConcreteReference.builder()
            .setClazz(Function.class)
            .setGenerics(Arrays.asList(exceptionListRef, methodDefinitionRef))
            .build();
    TypeNode type = TypeNode.withReference(ref);
    // [Constructing] HashMap<String, Integer> map;
    ConcreteReference mapRef =
        ConcreteReference.builder()
            .setClazz(HashMap.class)
            .setGenerics(
                Arrays.asList(
                    ConcreteReference.withClazz(String.class),
                    ConcreteReference.withClazz(Integer.class)))
            .build();
    VariableExpr mapExpr =
        VariableExpr.builder()
            .setVariable(
                Variable.builder().setName("map").setType(TypeNode.withReference(mapRef)).build())
            .setIsDecl(true)
            .build();
    ExprStatement exprStatement = ExprStatement.withExpr(mapExpr);
    // [Constructing] an input argument whose type is `List<IOException>`
    VariableExpr arg =
        VariableExpr.builder()
            .setVariable(
                Variable.builder()
                    .setName("arg")
                    .setType(TypeNode.withReference(exceptionListRef))
                    .build())
            .setIsDecl(true)
            .build();
    // [Constructing] a return variable expression whose type is `MethodDefinition`
    VariableExpr returnArg =
        VariableExpr.builder()
            .setVariable(
                Variable.builder()
                    .setName("returnArg")
                    .setType(TypeNode.withReference(methodDefinitionRef))
                    .build())
            .build();
    MethodDefinition method =
        MethodDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.withReference(methodDefinitionRef))
            .setArguments(Arrays.asList(arg))
            .setReturnExpr(returnArg)
            .setName("apply")
            .build();
    AnonymousClassExpr anonymousClassExpr =
        AnonymousClassExpr.builder()
            .setType(type)
            .setMethods(Arrays.asList(method))
            .setStatements(Arrays.asList(exprStatement))
            .build();
    anonymousClassExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(5),
            "import com.google.api.generator.engine.ast.MethodDefinition;\n",
            "import com.google.common.base.Function;\n",
            "import java.io.IOException;\n",
            "import java.util.HashMap;\n",
            "import java.util.List;\n\n"));
  }

  @Test
  public void writeThrowExprImports_basic() {
    TypeNode exceptionTypes =
        TypeNode.withReference(ConcreteReference.withClazz(IOException.class));
    String message = "Some message asdf";
    ThrowExpr throwExpr = ThrowExpr.builder().setType(exceptionTypes).setMessage(message).build();
    throwExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "import java.io.IOException;\n\n");
  }

  @Test
  public void writeInstanceofExprImports_basic() {
    TypeNode exprType = TypeNode.withReference(ConcreteReference.withClazz(Expr.class));
    TypeNode assignExprType =
        TypeNode.withReference(ConcreteReference.withClazz(AssignmentExpr.class));

    Variable variable = Variable.builder().setName("x").setType(assignExprType).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    InstanceofExpr expr =
        InstanceofExpr.builder().setExpr(variableExpr).setCheckType(exprType).build();
    expr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(2),
            "import com.google.api.generator.engine.ast.AssignmentExpr;\n",
            "import com.google.api.generator.engine.ast.Expr;\n\n"));
  }

  @Test
  public void writeEnumRefExprImports_basic() {
    TypeNode enumType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(TypeNode.TypeKind.class)
                .setIsStaticImport(true)
                .build());
    EnumRefExpr enumRefExpr = EnumRefExpr.builder().setName("VOID").setType(enumType).build();

    enumRefExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        "import static com.google.api.generator.engine.ast.TypeNode.TypeKind;\n\n");
  }

  @Test
  public void writeEnumRefExprImports_nested() {
    TypeNode enumType =
        TypeNode.withReference(ConcreteReference.withClazz(TypeNode.TypeKind.class));
    EnumRefExpr enumRefExpr = EnumRefExpr.builder().setName("VOID").setType(enumType).build();
    enumRefExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "import com.google.api.generator.engine.ast.TypeNode;\n\n");
  }

  @Test
  public void writeMethodDefinitionImports_templatedMixedNamesAndTypes() {
    Reference mapRef = ConcreteReference.withClazz(Map.class);
    List<VariableExpr> arguments =
        Arrays.asList(
            VariableExpr.builder()
                .setVariable(createVariable("x", TypeNode.withReference(mapRef)))
                .setIsDecl(true)
                .setTemplateObjects(
                    Arrays.asList(
                        "K",
                        TypeNode.withReference(ConcreteReference.withClazz(AssignmentExpr.class))))
                .build(),
            VariableExpr.builder()
                .setVariable(createVariable("y", TypeNode.withReference(mapRef)))
                .setIsDecl(true)
                .setTemplateObjects(Arrays.asList("T", "V"))
                .build());

    TypeNode returnType = TypeNode.withReference(mapRef);
    MethodDefinition methodDefinition =
        MethodDefinition.builder()
            .setName("close")
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(returnType)
            .setTemplateNames(Arrays.asList("T", "K", "V"))
            .setReturnTemplateNames(Arrays.asList("K", "V"))
            .setArguments(arguments)
            .setReturnExpr(
                MethodInvocationExpr.builder()
                    .setMethodName("foobar")
                    .setReturnType(returnType)
                    .build())
            .build();
    methodDefinition.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(2),
            "import com.google.api.generator.engine.ast.AssignmentExpr;\n",
            "import java.util.Map;\n\n"));
  }

  private static TypeNode createType(Class clazz) {
    return TypeNode.withReference(ConcreteReference.withClazz(clazz));
  }

  private static Variable createVariable(String variableName, TypeNode type) {
    return Variable.builder().setName(variableName).setType(type).build();
  }

  private static String createLines(int numLines) {
    return new String(new char[numLines]).replace("\0", "%s");
  }
}
