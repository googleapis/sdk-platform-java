package com.google.api.generator.gapic.composer.samplecode;

import com.google.api.generator.engine.ast.*;

public class SampleUtil {
    static MethodInvocationExpr systemOutPrint(String content){
        VaporReference out = VaporReference.builder()
                .setSupertypeReference(ConcreteReference.withClazz(System.class))
                .setEnclosingClassNames("System").setName("out").setPakkage("java.lang").build();
        return MethodInvocationExpr.builder()
                .setStaticReferenceType(TypeNode.withReference(out))
                .setMethodName("println")
                .setArguments(ValueExpr.withValue(StringObjectValue.withValue(content)))
                .build();
    }
    static MethodInvocationExpr systemOutPrint(VariableExpr variableExpr){
        VaporReference out = VaporReference.builder()
                .setSupertypeReference(ConcreteReference.withClazz(System.class))
                .setEnclosingClassNames("System").setName("out").setPakkage("java.lang").build();
        return MethodInvocationExpr.builder()
                .setStaticReferenceType(TypeNode.withReference(out))
                .setMethodName("println")
                .setArguments(variableExpr.toBuilder().setIsDecl(false).build())
                .build();
    }
}
