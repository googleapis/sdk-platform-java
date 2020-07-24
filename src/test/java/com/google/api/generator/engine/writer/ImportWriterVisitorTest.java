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
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.InstanceofExpr;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.ThrowExpr;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.common.base.Function;
import java.io.File;
import java.io.FileOutputStream;
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
  public void writeNewObjectExprImports_basic() {
    // [Constructing] `new ArrayList<>()`
    NewObjectExpr newObjectExpr =
        NewObjectExpr.builder()
            .setIsGeneric(true)
            .setType(TypeNode.withReference(ConcreteReference.withClazz(ArrayList.class)))
            .build();
    newObjectExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "import java.util.ArrayList;\n\n");
  }

  @Test
  public void writeNewObjectExprImports_withArgs() {
    // [Constructing] `new FileOutputStream(File file)` and the argument needs to be imported.
    ConcreteReference fileOutputStreamRef = ConcreteReference.withClazz(FileOutputStream.class);
    ConcreteReference fileRef = ConcreteReference.withClazz(File.class);
    Variable fileVar =
        Variable.builder().setName("file").setType(TypeNode.withReference(fileRef)).build();
    VariableExpr fileExpr = VariableExpr.builder().setVariable(fileVar).build();
    NewObjectExpr newObjectExpr =
        NewObjectExpr.builder()
            .setType(TypeNode.withReference(fileOutputStreamRef))
            .setArguments(Arrays.asList(fileExpr))
            .build();
    newObjectExpr.accept(writerVisitor);
    System.out.println(writerVisitor.write());
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(2), "import java.io.File;\n", "import java.io.FileOutputStream;\n\n"));
  }

  @Test
  public void writeNewObjectExprImports_genericsAndVariableArgs() {
    // [Constructing] `new HashMap<List<String>, Integer>>(int initialCapacity, float loadFactor)`
    ConcreteReference listRef =
        ConcreteReference.builder()
            .setClazz(List.class)
            .setGenerics(Arrays.asList(ConcreteReference.withClazz(String.class)))
            .build();
    ConcreteReference mapRef =
        ConcreteReference.builder()
            .setClazz(HashMap.class)
            .setGenerics(Arrays.asList(listRef, ConcreteReference.withClazz(Integer.class)))
            .build();
    TypeNode type = TypeNode.withReference(mapRef);
    Variable initialCapacity =
        Variable.builder().setName("initialCapacity").setType(TypeNode.INT).build();
    VariableExpr initCapacityExpr = VariableExpr.builder().setVariable(initialCapacity).build();
    Variable loadFactor = Variable.builder().setName("loadFactor").setType(TypeNode.FLOAT).build();
    VariableExpr loadFactorExpr = VariableExpr.builder().setVariable(loadFactor).build();

    NewObjectExpr newObjectExpr =
        NewObjectExpr.builder()
            .setIsGeneric(true)
            .setType(type)
            .setArguments(Arrays.asList(initCapacityExpr, loadFactorExpr))
            .build();
    newObjectExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(createLines(2), "import java.util.HashMap;\n", "import java.util.List;\n\n"));
  }

  @Test
  public void writeNewObjectExprImports_methodExprArg() {
    // [Constructing] `new IOException(message, cause(mapArg))` and `cause(mapArg)` is a method
    // invocation with a `HashMap` argument.
    TypeNode exceptionType = TypeNode.withReference(ConcreteReference.withClazz(IOException.class));
    Variable message = Variable.builder().setName("message").setType(TypeNode.STRING).build();
    TypeNode mapType = TypeNode.withReference(ConcreteReference.withClazz(HashMap.class));
    VariableExpr mapExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setName("mapArg").setType(mapType).build())
            .build();
    VariableExpr msgExpr = VariableExpr.builder().setVariable(message).build();
    MethodInvocationExpr causeExpr =
        MethodInvocationExpr.builder()
            .setMethodName("cause")
            .setArguments(Arrays.asList(mapExpr))
            .setReturnType(TypeNode.withReference(ConcreteReference.withClazz(Throwable.class)))
            .build();
    NewObjectExpr newObjectExpr =
        NewObjectExpr.builder()
            .setType(exceptionType)
            .setArguments(Arrays.asList(msgExpr, causeExpr))
            .build();
    newObjectExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(2), "import java.io.IOException;\n", "import java.util.HashMap;\n\n"));
  }

  @Test
  public void writeAssignmentExprImports() {
    Variable variable =
        Variable.builder().setName("clazz").setType(createType(AstNode.class)).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    MethodInvocationExpr valueExpr =
        MethodInvocationExpr.builder()
            .setMethodName("createClass")
            .setStaticReferenceName(ClassDefinition.class.getSimpleName())
            .setReturnType(createType(ClassDefinition.class))
            .build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();

    assignExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(2),
            "import com.google.api.generator.engine.ast.AstNode;\n",
            "import com.google.api.generator.engine.ast.ClassDefinition;\n\n"));
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

  private static TypeNode createType(Class clazz) {
    return TypeNode.withReference(ConcreteReference.withClazz(clazz));
  }

  private static String createLines(int numLines) {
    return new String(new char[numLines]).replace("\0", "%s");
  }
}
