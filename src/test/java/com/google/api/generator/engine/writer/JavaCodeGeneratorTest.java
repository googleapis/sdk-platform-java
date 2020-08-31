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

import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.CastExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.EnumRefExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ForStatement;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.ReferenceConstructorExpr;
import com.google.api.generator.engine.ast.ReturnExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TernaryExpr;
import com.google.api.generator.engine.ast.ThisObjectValue;
import com.google.api.generator.engine.ast.ThrowExpr;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import org.junit.Test;

public class JavaCodeGeneratorTest {
  @Test
  public void validJavaClass() {
    // Create references.
    VaporReference stubRef =
        VaporReference.builder().setPakkage("com.google.gax.grpc").setName("Stub").build();
    VaporReference bookKindRef =
        VaporReference.builder()
            .setPakkage("com.google.exmaple.library.v1")
            .setName("BookKind")
            .build();
    VaporReference libraryServiceRef =
        VaporReference.builder()
            .setPakkage("com.google.exmaple.library")
            .setName("LibraryService")
            .build();
    VaporReference shelfClassRef =
        VaporReference.builder()
            .setName("Shelf")
            .setPakkage("com.google.example.library.core")
            .build();
    VaporReference bookClassRef =
        VaporReference.builder()
            .setName("Book")
            .setPakkage("com.google.example.library.core")
            .build();
    VaporReference novelClassRef =
        VaporReference.builder()
            .setName("Novel")
            .setPakkage("com.google.example.library.core")
            .build();
    // TODO: (xiaozhenliu)import static jaav.lang.Integer.*;
    ConcreteReference nonexistentShelfExceptionRef =
        ConcreteReference.builder().setClazz(Exception.class).build();
    ConcreteReference integerUtilRef =
        ConcreteReference.builder().setClazz(Integer.class).setIsStaticImport(true).build();
    ConcreteReference shelfListRef =
        ConcreteReference.builder()
            .setClazz(List.class)
            .setGenerics(Arrays.asList(shelfClassRef))
            .build();
    ConcreteReference stringRef = ConcreteReference.withClazz(String.class);
    ConcreteReference shelfMapRef =
        ConcreteReference.builder()
            .setClazz(HashMap.class)
            .setGenerics(Arrays.asList(stringRef, shelfClassRef))
            .build();
    ConcreteReference stringBuilderRef =
        ConcreteReference.builder().setClazz(StringBuilder.class).build();
    ConcreteReference fileWriterRef =
        ConcreteReference.builder().setClazz(FileWriter.class).build();
    // Create outer class variableDels.
    // [code] private static final String serviceName = "LibraryServiceStub";
    VariableExpr serviceName =
        VariableExpr.builder()
            .setIsDecl(true)
            .setIsFinal(true)
            .setIsStatic(true)
            .setScope(ScopeNode.PRIVATE)
            .setVariable(createVar(stringRef, "serviceName"))
            .build();
    AssignmentExpr serviceNameDel =
        AssignmentExpr.builder()
            .setValueExpr(ValueExpr.withValue(StringObjectValue.withValue("LibraryServiceStub")))
            .setVariableExpr(serviceName)
            .build();
    // [code] protected List<Shelf> shelfList
    Variable shelfList = createVar(shelfListRef, "shelfList");
    VariableExpr shelfListExpr =
        VariableExpr.builder()
            .setIsDecl(true)
            .setScope(ScopeNode.PROTECTED)
            .setVariable(shelfList)
            .build();
    // [code] public static HashMap<String, Shelf> shelfMap;
    Variable shelfMap = createVar(shelfMapRef, "shelfMap");
    VariableExpr shelfMapExpr =
        VariableExpr.builder()
            .setIsDecl(true)
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setVariable(shelfMap)
            .build();
    // Create the LibraryServiceStub constructor
    NewObjectExpr arrayList =
        NewObjectExpr.builder()
            .setType(TypeNode.withReference(ConcreteReference.withClazz(ArrayList.class)))
            .setIsGeneric(true)
            .build();
    NewObjectExpr hashMap =
        NewObjectExpr.builder()
            .setType(TypeNode.withReference(ConcreteReference.withClazz(HashMap.class)))
            .setIsGeneric(true)
            .build();
    VaporReference outerClassRef =
        VaporReference.builder()
            .setName("LibraryServiceStub")
            .setPakkage("com.google.example.library.core")
            .build();
    ReferenceConstructorExpr superExpr =
        ReferenceConstructorExpr.superBuilder()
            .setType(TypeNode.withReference(outerClassRef))
            .build();
    ValueExpr thisValueExpr =
        ValueExpr.withValue(ThisObjectValue.withType(TypeNode.withReference(outerClassRef)));
    VariableExpr thisVariableExpr =
        VariableExpr.builder().setVariable(shelfList).setExprReferenceExpr(thisValueExpr).build();
    AssignmentExpr shelfListAssignmentExpr =
        AssignmentExpr.builder().setVariableExpr(thisVariableExpr).setValueExpr(arrayList).build();
    AssignmentExpr shelfMapAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(VariableExpr.withVariable(shelfMap))
            .setValueExpr(hashMap)
            .build();
    MethodDefinition libraryServiceCtor =
        MethodDefinition.constructorBuilder()
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(superExpr),
                    ExprStatement.withExpr(shelfListAssignmentExpr),
                    ExprStatement.withExpr(shelfMapAssignmentExpr)))
            .setReturnType(TypeNode.withReference(libraryServiceRef))
            .setScope(ScopeNode.PUBLIC)
            .build();
    // Create nested class Shelf.
    Variable shelfNameVar =
        Variable.builder().setName("shelfName").setType(TypeNode.STRING).build();
    Variable seriesNumVar = Variable.builder().setName("seriesNum").setType(TypeNode.INT).build();
    Variable shelfServiceNameVar =
        Variable.builder().setName("shelfServiceName").setType(TypeNode.STRING).build();

    VariableExpr shelfNameDel =
        VariableExpr.builder()
            .setIsDecl(true)
            .setScope(ScopeNode.PUBLIC)
            .setVariable(shelfNameVar)
            .build();
    VariableExpr seriesNumDel =
        VariableExpr.builder()
            .setIsDecl(true)
            .setScope(ScopeNode.PUBLIC)
            .setVariable(seriesNumVar)
            .build();

    VariableExpr shelfServiceNameDel =
        VariableExpr.builder()
            .setIsDecl(true)
            .setScope(ScopeNode.PUBLIC)
            .setVariable(shelfServiceNameVar)
            .build();
    AssignmentExpr shelfServiceNameAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(shelfServiceNameDel)
            .setValueExpr(
                VariableExpr.withVariable(
                    Variable.builder().setName("serviceName").setType(TypeNode.STRING).build()))
            .build();
    VariableExpr thisShelfNameVariableExpr =
        VariableExpr.builder()
            .setVariable(shelfNameVar)
            .setExprReferenceExpr(
                ValueExpr.withValue(
                    ThisObjectValue.withType(TypeNode.withReference(shelfClassRef))))
            .build();
    VariableExpr thisSeriesNumVariableExpr =
        VariableExpr.builder()
            .setVariable(seriesNumVar)
            .setExprReferenceExpr(
                ValueExpr.withValue(
                    ThisObjectValue.withType(TypeNode.withReference(shelfClassRef))))
            .build();
    AssignmentExpr thisShelfNameAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(thisShelfNameVariableExpr)
            .setValueExpr(VariableExpr.withVariable(shelfNameVar))
            .build();
    AssignmentExpr thisSeriesNumAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(thisSeriesNumVariableExpr)
            .setValueExpr(VariableExpr.withVariable(seriesNumVar))
            .build();
    MethodDefinition nestedShelfClassCtor =
        MethodDefinition.constructorBuilder()
            .setArguments(
                Arrays.asList(
                    VariableExpr.builder().setIsDecl(true).setVariable(shelfNameVar).build(),
                    VariableExpr.builder().setIsDecl(true).setVariable(seriesNumVar).build()))
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(thisShelfNameAssignExpr),
                    ExprStatement.withExpr(thisSeriesNumAssignExpr)))
            .setReturnType(TypeNode.withReference(shelfClassRef))
            .setScope(ScopeNode.PUBLIC)
            .build();
    ClassDefinition nestedClassShelf =
        ClassDefinition.builder()
            .setIsNested(true)
            .setMethods(Arrays.asList(nestedShelfClassCtor))
            .setStatements(
                Arrays.asList(
                    ExprStatement.withExpr(shelfNameDel),
                    ExprStatement.withExpr(seriesNumDel),
                    ExprStatement.withExpr(shelfServiceNameAssignmentExpr)))
            .setName("Shelf")
            .setScope(ScopeNode.PUBLIC)
            .build();
    // Create nested abstract class Book.
    Variable bookKindVar =
        Variable.builder().setName("bookKind").setType(TypeNode.withReference(bookKindRef)).build();
    VariableExpr bookKindDel =
        VariableExpr.builder()
            .setVariable(bookKindVar)
            .setScope(ScopeNode.PUBLIC)
            .setIsDecl(true)
            .build();
    MethodDefinition createBookMethod =
        MethodDefinition.builder()
            .setIsAbstract(true)
            .setArguments(
                Arrays.asList(
                    VariableExpr.builder().setIsDecl(true).setVariable(seriesNumVar).build(),
                    VariableExpr.builder().setIsDecl(true).setVariable(bookKindVar).build()))
            .setReturnType(TypeNode.VOID)
            .setName("createBook")
            .setScope(ScopeNode.PUBLIC)
            .build();
    ClassDefinition nestedClassBook =
        ClassDefinition.builder()
            .setHeaderCommentStatements(
                Arrays.asList(
                    createPreMethodLineComment("Test nested abstract class and abstract method.")))
            .setMethods(Arrays.asList(createBookMethod))
            .setStatements(
                Arrays.asList(
                    ExprStatement.withExpr(bookKindDel), ExprStatement.withExpr(seriesNumDel)))
            .setName("Book")
            .setScope(ScopeNode.PUBLIC)
            .setIsNested(true)
            .setIsAbstract(true)
            .build();
    // Create nested class Novel.
    VariableExpr thisBookKindVariableExpr =
        VariableExpr.builder()
            .setVariable(bookKindVar)
            .setExprReferenceExpr(
                ValueExpr.withValue(
                    ThisObjectValue.withType(TypeNode.withReference(novelClassRef))))
            .build();
    EnumRefExpr bookKindNovelEnumExpr =
        EnumRefExpr.builder().setName("NOVEL").setType(TypeNode.withReference(bookKindRef)).build();
    AssignmentExpr thisBookKindAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(thisBookKindVariableExpr)
            .setValueExpr(bookKindNovelEnumExpr)
            .build();
    MethodDefinition overrideCreateBook =
        MethodDefinition.builder()
            .setAnnotations(Arrays.asList(AnnotationNode.OVERRIDE))
            .setArguments(
                Arrays.asList(
                    VariableExpr.builder().setIsDecl(true).setVariable(seriesNumVar).build(),
                    VariableExpr.builder().setIsDecl(true).setVariable(bookKindVar).build()))
            .setReturnType(TypeNode.VOID)
            .setName("createBook")
            .setScope(ScopeNode.PUBLIC)
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(thisSeriesNumAssignExpr),
                    ExprStatement.withExpr(thisBookKindAssignmentExpr)))
            .build();
    ClassDefinition nestedClassNovel =
        ClassDefinition.builder()
            .setName("Novel")
            .setScope(ScopeNode.PUBLIC)
            .setIsNested(true)
            .setExtendsType(TypeNode.withReference(bookClassRef))
            .setMethods(Arrays.asList(overrideCreateBook))
            .build();
    // Create method `addShelf`
    Variable nameVar = Variable.builder().setName("name").setType(TypeNode.STRING).build();
    Variable seriesDoubleNumVar =
        Variable.builder().setName("seriesDoubleNum").setType(TypeNode.DOUBLE).build();
    Variable maxValueVar = createVar(integerUtilRef, "MAX_VALUE");
    CastExpr seriesNumDoubleToIntExpr =
        CastExpr.builder()
            .setExpr(VariableExpr.withVariable(seriesDoubleNumVar))
            .setType(TypeNode.INT)
            .build();
    AssignmentExpr castSeriesNumExpr =
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.builder().setIsDecl(true).setVariable(seriesNumVar).build())
            .setValueExpr(seriesNumDoubleToIntExpr)
            .build();
    ReturnExpr returnExpr =
        ReturnExpr.withExpr(ValueExpr.withValue(StringObjectValue.withValue("")));
    // TODO: update the condition from `condition` to `seriesNum == MAX_VALUE`
    IfStatement maxValueCheck =
        IfStatement.builder()
            .setConditionExpr(
                VariableExpr.withVariable(
                    Variable.builder().setName("condition").setType(TypeNode.BOOLEAN).build()))
            .setBody(Arrays.asList(ExprStatement.withExpr(returnExpr)))
            .build();
    NewObjectExpr newShelfExpr =
        NewObjectExpr.builder()
            .setType(TypeNode.withReference(shelfClassRef))
            .setArguments(
                Arrays.asList(
                    VariableExpr.withVariable(nameVar), VariableExpr.withVariable(seriesNumVar)))
            .build();
    MethodInvocationExpr addShelfToList =
        MethodInvocationExpr.builder()
            .setMethodName("add")
            .setExprReferenceExpr(VariableExpr.withVariable(shelfList))
            .setArguments(Arrays.asList(newShelfExpr))
            .build();
    MethodInvocationExpr putShelfToMapExpr =
        putShelfToMap(shelfMap, Arrays.asList(VariableExpr.withVariable(nameVar), newShelfExpr));
    MethodInvocationExpr mapContainsKey =
        shelfMapContainsKey(shelfMap, Arrays.asList(VariableExpr.withVariable(nameVar)));

    IfStatement mapContainsKeyCheck =
        IfStatement.builder()
            .setConditionExpr(mapContainsKey)
            .setBody(Arrays.asList(ExprStatement.withExpr(returnExpr)))
            .build();
    MethodDefinition addShelfMethod =
        MethodDefinition.builder()
            .setAnnotations(Arrays.asList(AnnotationNode.OVERRIDE))
            .setName("addShelf")
            .setReturnType(TypeNode.VOID)
            .setScope(ScopeNode.PUBLIC)
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(castSeriesNumExpr),
                    maxValueCheck,
                    ExprStatement.withExpr(addShelfToList),
                    mapContainsKeyCheck,
                    ExprStatement.withExpr(putShelfToMapExpr)))
            .setArguments(
                Arrays.asList(
                    VariableExpr.builder().setVariable(nameVar).setIsDecl(true).build(),
                    VariableExpr.builder().setVariable(seriesDoubleNumVar).setIsDecl(true).build()))
            .build();
    // Create method `updateShelfMap`
    Variable shelfVar =
        Variable.builder()
            .setName("newShelf")
            .setType(TypeNode.withReference(shelfClassRef))
            .build();

    VariableExpr shelfNameFromNewShelfObject =
        fieldFromShelfObject(
            shelfVar, Variable.builder().setType(TypeNode.STRING).setName("shelfName").build());

    mapContainsKey = shelfMapContainsKey(shelfMap, Arrays.asList(shelfNameFromNewShelfObject));
    putShelfToMapExpr =
        putShelfToMap(
            shelfMap,
            Arrays.asList(shelfNameFromNewShelfObject, VariableExpr.withVariable(shelfVar)));
    ThrowExpr throwExpr =
        ThrowExpr.builder()
            .setMessageExpr("Updating shelf is not existing in the map")
            .setType(TypeNode.withReference(nonexistentShelfExceptionRef))
            .build();
    IfStatement updateShelfMapIfElseBlock =
        IfStatement.builder()
            .setConditionExpr(mapContainsKey)
            .setBody(Arrays.asList(ExprStatement.withExpr(putShelfToMapExpr)))
            .setElseBody(Arrays.asList(ExprStatement.withExpr(throwExpr)))
            .build();
    MethodDefinition updateShelfMap =
        MethodDefinition.builder()
            .setName("updateShelfMap")
            .setThrowsExceptions(
                Arrays.asList(TypeNode.withReference(nonexistentShelfExceptionRef)))
            .setReturnType(TypeNode.VOID)
            .setScope(ScopeNode.PUBLIC)
            .setBody(Arrays.asList(updateShelfMapIfElseBlock))
            .setArguments(
                Arrays.asList(VariableExpr.builder().setVariable(shelfVar).setIsDecl(true).build()))
            .build();
    // Creat method `printShelfListToFile`
    Variable stringBuilderVar =
        Variable.builder().setName("sb").setType(TypeNode.withReference(stringBuilderRef)).build();
    Variable fileNameVar = Variable.builder().setName("fileName").setType(TypeNode.STRING).build();
    Variable shelfObject =
        Variable.builder().setName("s").setType(TypeNode.withReference(shelfClassRef)).build();
    VariableExpr shelfNameFromShelfObject = fieldFromShelfObject(shelfObject, shelfNameVar);
    VariableExpr seriesNumFromShelfObject = fieldFromShelfObject(shelfObject, seriesNumVar);

    AssignmentExpr createStringBuilderExpr =
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.builder().setIsDecl(true).setVariable(stringBuilderVar).build())
            .setValueExpr(NewObjectExpr.withType(TypeNode.withReference(stringBuilderRef)))
            .build();
    Variable fileWriterVar =
        Variable.builder()
            .setName("fileWriter")
            .setType(TypeNode.withReference(fileWriterRef))
            .build();
    Variable ioException =
        Variable.builder()
            .setName("e")
            .setType(TypeNode.withExceptionClazz(IOException.class))
            .build();
    AssignmentExpr createFileWriterExpr =
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.builder().setIsDecl(true).setVariable(fileWriterVar).build())
            .setValueExpr(
                NewObjectExpr.builder()
                    .setType(TypeNode.withReference(fileWriterRef))
                    .setArguments(Arrays.asList(VariableExpr.withVariable(fileNameVar)))
                    .build())
            .build();
    MethodInvocationExpr appendShelfName =
        MethodInvocationExpr.builder()
            .setMethodName("append")
            .setExprReferenceExpr(VariableExpr.withVariable(stringBuilderVar))
            .setArguments(shelfNameFromShelfObject)
            .build();
    MethodInvocationExpr appendSeriesNum =
        MethodInvocationExpr.builder()
            .setMethodName("append")
            .setExprReferenceExpr(appendShelfName)
            .setArguments(seriesNumFromShelfObject)
            .build();
    MethodInvocationExpr stringBuilderToString =
        MethodInvocationExpr.builder()
            .setMethodName("toString")
            .setExprReferenceExpr(VariableExpr.withVariable(stringBuilderVar))
            .build();

    MethodInvocationExpr writeToFileWriter =
        MethodInvocationExpr.builder()
            .setMethodName("write")
            .setExprReferenceExpr(VariableExpr.withVariable(fileNameVar))
            .setArguments(stringBuilderToString)
            .build();
    MethodInvocationExpr closeFileWriter =
        MethodInvocationExpr.builder()
            .setMethodName("close")
            .setExprReferenceExpr(VariableExpr.withVariable(fileNameVar))
            .build();
    MethodInvocationExpr printError =
        MethodInvocationExpr.builder()
            .setMethodName("printStackTrace")
            .setExprReferenceExpr(VariableExpr.withVariable(ioException))
            .build();
    ForStatement loopShelfList =
        ForStatement.builder()
            .setLocalVariableExpr(
                VariableExpr.builder().setIsDecl(true).setVariable(shelfObject).build())
            .setCollectionExpr(VariableExpr.withVariable(shelfList))
            .setBody(Arrays.asList(ExprStatement.withExpr(appendSeriesNum)))
            .build();
    TryCatchStatement tryCatchStatement =
        TryCatchStatement.builder()
            .setTryBody(
                Arrays.asList(
                    ExprStatement.withExpr(createFileWriterExpr),
                    loopShelfList,
                    ExprStatement.withExpr(writeToFileWriter),
                    ExprStatement.withExpr(closeFileWriter)))
            .setCatchVariableExpr(
                VariableExpr.builder().setIsDecl(true).setVariable(ioException).build())
            .setCatchBody(Arrays.asList(ExprStatement.withExpr(printError)))
            .build();
    MethodDefinition printShelfListToFile =
        MethodDefinition.builder()
            .setName("printShelfListToFile")
            .setReturnType(TypeNode.VOID)
            .setScope(ScopeNode.PUBLIC)
            .setBody(
                Arrays.asList(ExprStatement.withExpr(createStringBuilderExpr), tryCatchStatement))
            .setArguments(
                Arrays.asList(
                    VariableExpr.builder().setVariable(fileNameVar).setIsDecl(true).build()))
            .build();
    // Create method `addBooksContainsNovel`
    ConcreteReference bookKindStackRef =
        ConcreteReference.builder()
            .setClazz(Stack.class)
            .setGenerics(Arrays.asList(bookKindRef))
            .build();
    Variable bookKindStackVar =
        Variable.builder()
            .setName("stack")
            .setType(TypeNode.withReference(bookKindStackRef))
            .build();
    Variable containsNovelVar =
        Variable.builder().setName("containsNovel").setType(TypeNode.BOOLEAN).build();
    TernaryExpr returnTernaryExpr =
        TernaryExpr.builder()
            .setConditionExpr(VariableExpr.withVariable(containsNovelVar))
            .setThenExpr(ValueExpr.withValue(StringObjectValue.withValue("Added novels")))
            .setElseExpr(ValueExpr.withValue(StringObjectValue.withValue("No novels added")))
            .build();
    AssignmentExpr setContainsNovelToFalse =
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.builder().setIsDecl(true).setVariable(containsNovelVar).build())
            .setValueExpr(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setValue("false").setType(TypeNode.BOOLEAN).build()))
            .build();
    AssignmentExpr setContainsNovelToTrue =
        AssignmentExpr.builder().setVariableExpr(containsNovelVar).setValueExpr().build();
    MethodDefinition addBooksContainsNovel =
        MethodDefinition.builder()
            .setName("addBooksContainsNovel")
            .setReturnType(TypeNode.STRING)
            .setScope(ScopeNode.PUBLIC)
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(containsNovelAssignmentExpr), tryCatchStatement))
            .setReturnExpr(returnTernaryExpr)
            .build();
    // Create outer class LibraryServiceStub
    ClassDefinition libraryServiceStubClass =
        ClassDefinition.builder()
            .setFileHeader(Arrays.asList(createFileHeader()))
            .setHeaderCommentStatements(Arrays.asList(createOuterClassJavaDocComment()))
            .setPackageString("com.google.example.library.core")
            .setAnnotations(
                Arrays.asList(
                    AnnotationNode.DEPRECATED,
                    AnnotationNode.OVERRIDE,
                    AnnotationNode.withSuppressWarnings("all")))
            .setImplementsTypes(Arrays.asList(TypeNode.withReference(libraryServiceRef)))
            .setExtendsType(TypeNode.withReference(stubRef))
            .setScope(ScopeNode.PUBLIC)
            .setStatements(
                Arrays.asList(
                    ExprStatement.withExpr(serviceNameDel),
                    ExprStatement.withExpr(shelfListExpr),
                    ExprStatement.withExpr(shelfMapExpr)))
            .setMethods(
                Arrays.asList(
                    libraryServiceCtor,
                    addShelfMethod,
                    updateShelfMap,
                    printShelfListToFile,
                    addBooksContainsNovel))
            .setNestedClasses(Arrays.asList(nestedClassShelf, nestedClassBook, nestedClassNovel))
            .setName("LibraryServiceStub")
            .build();
    JavaWriterVisitor javaWriterVisitor = new JavaWriterVisitor();
    libraryServiceStubClass.accept(javaWriterVisitor);
    System.out.println(javaWriterVisitor.write());
    assertEquals("", "hello");
  }

  // Private helpers.
  private CommentStatement createFileHeader() {
    String fileheader =
        "Copyright 2020 Gagpic-generator-java\n\n"
            + "Licensed description and license version 2.0 (the \"License\");\n\n"
            + "   https://www.foo.bar/licenses/LICENSE-2.0\n\n"
            + "Software distributed under the License is distributed on an \"AS IS\" BASIS.\n"
            + "See the License for the specific language governing permissions and\n"
            + "limitations under the License.";
    return CommentStatement.withComment(BlockComment.withComment(fileheader));
  }

  private CommentStatement createOuterClassJavaDocComment() {
    return CommentStatement.withComment(
        JavaDocComment.builder()
            .addComment("Service Description: This is a test comment.")
            .addComment("")
            .addSampleCode("LibraryServiceStub libServiceStub = new LibraryServiceStub()")
            .addUnorderedList(
                Arrays.asList(
                    "A \"flattened\" method.",
                    "A \"request object\" method.",
                    "A \"callable\" method."))
            .setDeprecated("This is a deprecated message.")
            .build());
  }

  private CommentStatement createPreMethodLineComment(String commentString) {
    return CommentStatement.withComment(LineComment.withComment(commentString));
  }

  private CommentStatement createPreMethodJavaDocComment() {
    return CommentStatement.withComment(
        JavaDocComment.builder()
            .addComment("Add books to Shelf and check if there is a novel,")
            .addComment("return string message as whether novel books are added to the shelf.")
            .addComment("\n")
            .addParam("shelf", "The Shelf object to which books will put.")
            .addParam("stack", "The Stack of the BookKinds.")
            .build());
  }

  private MethodInvocationExpr shelfMapContainsKey(Variable map, List<Expr> arg) {
    return MethodInvocationExpr.builder()
        .setMethodName("containsKey")
        .setExprReferenceExpr(VariableExpr.withVariable(map))
        .setArguments(arg)
        .setReturnType(TypeNode.BOOLEAN)
        .build();
  }

  private MethodInvocationExpr putShelfToMap(Variable map, List<Expr> arg) {
    return MethodInvocationExpr.builder()
        .setMethodName("put")
        .setExprReferenceExpr(VariableExpr.withVariable(map))
        .setArguments(arg)
        .build();
  }

  private VariableExpr fieldFromShelfObject(Variable shelfVar, Variable field) {
    return VariableExpr.builder()
        .setVariable(field)
        .setExprReferenceExpr(VariableExpr.withVariable(shelfVar))
        .build();
  }

  private Variable createVar(ConcreteReference ref, String name) {
    return Variable.builder().setName(name).setType(TypeNode.withReference(ref)).build();
  }
}
