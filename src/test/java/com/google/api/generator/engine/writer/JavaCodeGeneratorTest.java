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
import com.google.api.generator.engine.ast.AnonymousClassExpr;
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
import com.google.api.generator.engine.ast.InstanceofExpr;
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
import com.google.api.generator.engine.ast.WhileStatement;
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
    VaporReference libraryServiceStubRef =
        VaporReference.builder()
            .setPakkage("com.google.exmaple.library.core")
            .setName("LibraryServiceStub")
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
            .setReturnType(TypeNode.withReference(libraryServiceStubRef))
            .setScope(ScopeNode.PUBLIC)
            .build();
    // Create nested class Shelf.
    Variable shelfNameVar = createPrimitiveVar(TypeNode.STRING, "shelfName");
    Variable seriesNumVar = createPrimitiveVar(TypeNode.INT, "seriesNum");
    Variable shelfServiceNameVar = createPrimitiveVar(TypeNode.STRING, "shelfServiceName");

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
    Variable bookKindVar = createVar(bookKindRef, "bookKind");
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
    EnumRefExpr bookKindNovelEnumExpr =
        EnumRefExpr.builder().setName("NOVEL").setType(bookKindVar.type()).build();
    ClassDefinition nestedClassNovel =
        ClassDefinition.builder()
            .setName("Novel")
            .setScope(ScopeNode.PUBLIC)
            .setIsNested(true)
            .setExtendsType(TypeNode.withReference(bookClassRef))
            .setMethods(
                Arrays.asList(
                    overrideCreateBookMethod(
                        novelClassRef, seriesNumVar, bookKindVar, bookKindNovelEnumExpr)))
            .build();
    // Create method `addShelf`
    Variable nameVar = createPrimitiveVar(TypeNode.STRING, "name");
    Variable seriesDoubleNumVar = createPrimitiveVar(TypeNode.DOUBLE, "seriesDoubleNum");
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
    Variable shelfVar = createVar(shelfClassRef, "newShelf");

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
    Variable stringBuilderVar = createVar(stringBuilderRef, "sb");
    Variable fileNameVar = createPrimitiveVar(TypeNode.STRING, "fileName");
    Variable shelfObject = createVar(shelfClassRef, "s");
    VariableExpr shelfNameFromShelfObject = fieldFromShelfObject(shelfObject, shelfNameVar);
    VariableExpr seriesNumFromShelfObject = fieldFromShelfObject(shelfObject, seriesNumVar);

    AssignmentExpr createStringBuilderExpr =
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.builder().setIsDecl(true).setVariable(stringBuilderVar).build())
            .setValueExpr(NewObjectExpr.withType(TypeNode.withReference(stringBuilderRef)))
            .build();
    Variable fileWriterVar = createVar(fileWriterRef, "fileWriter");
    Variable ioException = createVar(ConcreteReference.withClazz(IOException.class), "e");
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
    Variable bookKindStackVar = createVar(bookKindStackRef, "stack");
    Variable containsNovelVar = createPrimitiveVar(TypeNode.BOOLEAN, "containsNovel");
    shelfVar = createVar(shelfClassRef, "shelf");
    Variable bookVar = createVar(bookClassRef, "addedBook");
    TernaryExpr ternaryExpr =
        TernaryExpr.builder()
            .setConditionExpr(VariableExpr.withVariable(containsNovelVar))
            .setThenExpr(ValueExpr.withValue(StringObjectValue.withValue("Added novels")))
            .setElseExpr(ValueExpr.withValue(StringObjectValue.withValue("No novels added")))
            .build();
    AssignmentExpr setContainsNovelToFalse =
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.builder().setIsDecl(true).setVariable(containsNovelVar).build())
            .setValueExpr(ValueExpr.withValue(createBooleanValue("false")))
            .build();
    MethodInvocationExpr stackIsEmpty =
        MethodInvocationExpr.builder()
            .setMethodName("isEmpty")
            .setExprReferenceExpr(VariableExpr.withVariable(bookKindStackVar))
            .setReturnType(TypeNode.BOOLEAN)
            .build();
    MethodInvocationExpr stackPop =
        MethodInvocationExpr.builder()
            .setMethodName("pop")
            .setExprReferenceExpr(VariableExpr.withVariable(bookKindStackVar))
            .build();
    MethodInvocationExpr addBookToShelfMethod =
        MethodInvocationExpr.builder()
            .setMethodName("addBookToShelf")
            .setArguments(stackPop, VariableExpr.withVariable(shelfVar))
            .setReturnType(TypeNode.withReference(bookClassRef))
            .build();
    AssignmentExpr createNewAddedBook =
        AssignmentExpr.builder()
            .setVariableExpr(VariableExpr.builder().setIsDecl(true).setVariable(bookVar).build())
            .setValueExpr(addBookToShelfMethod)
            .build();
    InstanceofExpr addedBookIsNovelInstance =
        InstanceofExpr.builder()
            .setExpr(VariableExpr.withVariable(bookVar))
            .setCheckType(TypeNode.withReference(novelClassRef))
            .build();
    AssignmentExpr setContainsNovelToTrue =
        AssignmentExpr.builder()
            .setVariableExpr(VariableExpr.withVariable(containsNovelVar))
            .setValueExpr(ValueExpr.withValue(createBooleanValue("true")))
            .build();
    IfStatement ifStatement =
        IfStatement.builder()
            .setConditionExpr(addedBookIsNovelInstance)
            .setBody(Arrays.asList(ExprStatement.withExpr(setContainsNovelToTrue)))
            .build();
    // TODO: update the conditionExpr from `stack.isEmpty()` to `!stack.isEmpty()`
    WhileStatement whileStatement =
        WhileStatement.builder()
            .setConditionExpr(stackIsEmpty)
            .setBody(Arrays.asList(ExprStatement.withExpr(createNewAddedBook), ifStatement))
            .build();
    MethodDefinition addBooksContainsNovel =
        MethodDefinition.builder()
            .setHeaderCommentStatements(createPreMethodJavaDocComment())
            .setArguments(
                Arrays.asList(
                    VariableExpr.builder().setIsDecl(true).setVariable(shelfVar).build(),
                    VariableExpr.builder().setIsDecl(true).setVariable(bookKindStackVar).build()))
            .setName("addBooksContainsNovel")
            .setReturnType(TypeNode.STRING)
            .setScope(ScopeNode.PUBLIC)
            .setBody(Arrays.asList(ExprStatement.withExpr(setContainsNovelToFalse), whileStatement))
            .setReturnExpr(ternaryExpr)
            .build();
    // Create private method `addBookToShelf`
    bookVar = createVar(bookClassRef, "book");
    AnonymousClassExpr anonymousBookClassExpr =
        AnonymousClassExpr.builder()
            .setType(TypeNode.withReference(bookClassRef))
            .setMethods(
                Arrays.asList(
                    overrideCreateBookMethod(
                        bookClassRef,
                        seriesNumVar,
                        bookKindVar,
                        VariableExpr.withVariable(bookKindVar))))
            .build();
    AssignmentExpr createNewBook =
        AssignmentExpr.builder()
            .setVariableExpr(VariableExpr.builder().setIsDecl(true).setVariable(bookVar).build())
            .setValueExpr(anonymousBookClassExpr)
            .build();
    MethodDefinition addBookToShelf =
        MethodDefinition.builder()
            .setHeaderCommentStatements(
                Arrays.asList(createPreMethodLineComment("Private helper.")))
            .setName("addBookToShelf")
            .setReturnType(TypeNode.withReference(bookClassRef))
            .setArguments(
                Arrays.asList(
                    VariableExpr.builder().setIsDecl(true).setVariable(bookKindVar).build(),
                    VariableExpr.builder().setIsDecl(true).setVariable(shelfVar).build()))
            .setScope(ScopeNode.PRIVATE)
            .setBody(Arrays.asList(ExprStatement.withExpr(createNewBook)))
            .setReturnExpr(VariableExpr.withVariable(bookVar))
            .build();
    // Create outer class LibraryServiceStub
    ClassDefinition libraryServiceStubClass =
        ClassDefinition.builder()
            .setFileHeader(Arrays.asList(createFileHeader()))
            .setHeaderCommentStatements(Arrays.asList(createOuterClassJavaDocComment()))
            .setPackageString("com.google.example.library.core")
            .setAnnotations(
                Arrays.asList(
                    AnnotationNode.withSuppressWarnings("all"),
                    AnnotationNode.DEPRECATED,
                    AnnotationNode.OVERRIDE))
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
                    addBooksContainsNovel,
                    addBookToShelf))
            .setNestedClasses(Arrays.asList(nestedClassShelf, nestedClassBook, nestedClassNovel))
            .setName("LibraryServiceStub")
            .build();
    JavaWriterVisitor javaWriterVisitor = new JavaWriterVisitor();
    libraryServiceStubClass.accept(javaWriterVisitor);
    // System.out.println(javaWriterVisitor.write());
    assertEquals(javaWriterVisitor.write(), EXPECTED_CLASS_STRING);
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
            .addOrderedList(
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

  private List<CommentStatement> createPreMethodJavaDocComment() {
    return Arrays.asList(
        CommentStatement.withComment(
            JavaDocComment.builder()
                .addComment("Add books to Shelf and check if there is a novel,")
                .addComment("return string message as whether novel books are added to the shelf.")
                .addComment("")
                .addParam("shelf", "The Shelf object to which books will put.")
                .addParam("stack", "The Stack of the BookKinds.")
                .build()));
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

  private Variable createVar(VaporReference ref, String name) {
    return Variable.builder().setName(name).setType(TypeNode.withReference(ref)).build();
  }

  private Variable createPrimitiveVar(TypeNode ref, String name) {
    return Variable.builder().setName(name).setType(ref).build();
  }

  private PrimitiveValue createBooleanValue(String booleanValue) {
    return PrimitiveValue.builder().setValue(booleanValue).setType(TypeNode.BOOLEAN).build();
  }

  private MethodDefinition overrideCreateBookMethod(
      VaporReference classRef,
      Variable seriesNumVar,
      Variable bookKindVar,
      Expr bookKindDefaultExpr) {

    VariableExpr thisBookKindVariableExpr =
        VariableExpr.builder()
            .setVariable(bookKindVar)
            .setExprReferenceExpr(
                ValueExpr.withValue(ThisObjectValue.withType(TypeNode.withReference(classRef))))
            .build();
    VariableExpr thisSeriesNumVariableExpr =
        VariableExpr.builder()
            .setVariable(seriesNumVar)
            .setExprReferenceExpr(
                ValueExpr.withValue(ThisObjectValue.withType(TypeNode.withReference(classRef))))
            .build();
    AssignmentExpr thisSeriesNumAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(thisSeriesNumVariableExpr)
            .setValueExpr(VariableExpr.withVariable(seriesNumVar))
            .build();
    AssignmentExpr thisBookKindAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(thisBookKindVariableExpr)
            .setValueExpr(bookKindDefaultExpr)
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
    return overrideCreateBook;
  }

  private static final String EXPECTED_CLASS_STRING =
      "/*\n"
          + " * Copyright 2020 Gagpic-generator-java\n"
          + " *\n"
          + " * Licensed description and license version 2.0 (the \"License\");\n"
          + " *\n"
          + " *    https://www.foo.bar/licenses/LICENSE-2.0\n"
          + " *\n"
          + " * Software distributed under the License is distributed on an \"AS IS\" BASIS.\n"
          + " * See the License for the specific language governing permissions and\n"
          + " * limitations under the License.\n"
          + " */\n"
          + "\n"
          + "package com.google.example.library.core;\n"
          + "\n"
          + "import com.google.exmaple.library.LibraryService;\n"
          + "import com.google.exmaple.library.core.LibraryServiceStub;\n"
          + "import com.google.exmaple.library.v1.BookKind;\n"
          + "import com.google.gax.grpc.Stub;\n"
          + "import java.io.FileWriter;\n"
          + "import java.io.IOException;\n"
          + "import java.util.ArrayList;\n"
          + "import java.util.HashMap;\n"
          + "import java.util.List;\n"
          + "import java.util.Stack;\n"
          + "\n"
          + "/**\n"
          + " * Service Description: This is a test comment.\n"
          + " *\n"
          + " * <pre><code>\n"
          + " * LibraryServiceStub libServiceStub = new LibraryServiceStub()\n"
          + " * </code></pre>\n"
          + " *\n"
          + " * <ol>\n"
          + " *   <li>A \"flattened\" method.\n"
          + " *   <li>A \"request object\" method.\n"
          + " *   <li>A \"callable\" method.\n"
          + " * </ol>\n"
          + " *\n"
          + " * @deprecated This is a deprecated message.\n"
          + " */\n"
          + "@SuppressWarnings(\"all\")\n"
          + "@Deprecated\n"
          + "@Override\n"
          + "public class LibraryServiceStub extends Stub implements LibraryService {\n"
          + "  private static final String serviceName = \"LibraryServiceStub\";\n"
          + "  protected List<Shelf> shelfList;\n"
          + "  public static HashMap<String, Shelf> shelfMap;\n"
          + "\n"
          + "  public LibraryServiceStub() {\n"
          + "    super();\n"
          + "    this.shelfList = new ArrayList<>();\n"
          + "    shelfMap = new HashMap<>();\n"
          + "  }\n"
          + "\n"
          + "  @Override\n"
          + "  public void addShelf(String name, double seriesDoubleNum) {\n"
          + "    int seriesNum = ((int) seriesDoubleNum);\n"
          + "    if (condition) {\n"
          + "      return \"\";\n"
          + "    }\n"
          + "    shelfList.add(new Shelf(name, seriesNum));\n"
          + "    if (shelfMap.containsKey(name)) {\n"
          + "      return \"\";\n"
          + "    }\n"
          + "    shelfMap.put(name, new Shelf(name, seriesNum));\n"
          + "  }\n"
          + "\n"
          + "  public void updateShelfMap(Shelf newShelf) throws Exception {\n"
          + "    if (shelfMap.containsKey(newShelf.shelfName)) {\n"
          + "      shelfMap.put(newShelf.shelfName, newShelf);\n"
          + "    } else {\n"
          + "      throw new Exception(\"Updating shelf is not existing in the map\");\n"
          + "    }\n"
          + "  }\n"
          + "\n"
          + "  public void printShelfListToFile(String fileName) {\n"
          + "    StringBuilder sb = new StringBuilder();\n"
          + "    try {\n"
          + "      FileWriter fileWriter = new FileWriter(fileName);\n"
          + "      for (Shelf s : shelfList) {\n"
          + "        sb.append(s.shelfName).append(s.seriesNum);\n"
          + "      }\n"
          + "      fileName.write(sb.toString());\n"
          + "      fileName.close();\n"
          + "    } catch (IOException e) {\n"
          + "      e.printStackTrace();\n"
          + "    }\n"
          + "  }\n"
          + "  /**\n"
          + "   * Add books to Shelf and check if there is a novel, return string message as whether novel books\n"
          + "   * are added to the shelf.\n"
          + "   *\n"
          + "   * @param shelf The Shelf object to which books will put.\n"
          + "   * @param stack The Stack of the BookKinds.\n"
          + "   */\n"
          + "  public String addBooksContainsNovel(Shelf shelf, Stack<BookKind> stack) {\n"
          + "    boolean containsNovel = false;\n"
          + "    while (stack.isEmpty()) {\n"
          + "      Book addedBook = addBookToShelf(stack.pop(), shelf);\n"
          + "      if (addedBook instanceof Novel) {\n"
          + "        containsNovel = true;\n"
          + "      }\n"
          + "    }\n"
          + "    return containsNovel ? \"Added novels\" : \"No novels added\";\n"
          + "  }\n"
          + "  // Private helper.\n"
          + "  private Book addBookToShelf(BookKind bookKind, Shelf shelf) {\n"
          + "    Book book =\n"
          + "        new Book() {\n"
          + "          @Override\n"
          + "          public void createBook(int seriesNum, BookKind bookKind) {\n"
          + "            this.seriesNum = seriesNum;\n"
          + "            this.bookKind = bookKind;\n"
          + "          }\n"
          + "        };\n"
          + "    return book;\n"
          + "  }\n"
          + "\n"
          + "  public class Shelf {\n"
          + "    public String shelfName;\n"
          + "    public int seriesNum;\n"
          + "    public String shelfServiceName = serviceName;\n"
          + "\n"
          + "    public Shelf(String shelfName, int seriesNum) {\n"
          + "      this.shelfName = shelfName;\n"
          + "      this.seriesNum = seriesNum;\n"
          + "    }\n"
          + "  }\n"
          + "  // Test nested abstract class and abstract method.\n"
          + "  public abstract class Book {\n"
          + "    public BookKind bookKind;\n"
          + "    public int seriesNum;\n"
          + "\n"
          + "    public abstract void createBook(int seriesNum, BookKind bookKind);\n"
          + "  }\n"
          + "\n"
          + "  public class Novel extends Book {\n"
          + "    @Override\n"
          + "    public void createBook(int seriesNum, BookKind bookKind) {\n"
          + "      this.seriesNum = seriesNum;\n"
          + "      this.bookKind = BookKind.NOVEL;\n"
          + "    }\n"
          + "  }\n"
          + "}\n";
}
