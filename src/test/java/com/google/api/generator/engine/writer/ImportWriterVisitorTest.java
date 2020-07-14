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

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.AstNode;
import com.google.api.generator.engine.ast.CastExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ThrowExpr;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
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
  public void writeThrowExprImports_basic() {
    TypeNode exceptionTypes =
        TypeNode.withReference(ConcreteReference.withClazz(IOException.class));
    String message = "Some message asdf";
    ThrowExpr throwExpr = ThrowExpr.builder().setType(exceptionTypes).setMessage(message).build();
    throwExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "import java.io.IOException;\n\n");
  }

  private static TypeNode createType(Class clazz) {
    return TypeNode.withReference(ConcreteReference.withClazz(clazz));
  }

  private static String createLines(int numLines) {
    return new String(new char[numLines]).replace("\0", "%s");
  }
}
