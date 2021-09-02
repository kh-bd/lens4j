/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor.generator;

import com.github.lens.core.Lenses;
import com.github.lens.core.ReadLens;
import com.github.lens.processor.LensProcessor;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.Generated;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Alexey_Bodyak
 */
public class BaseLensGenerator implements LensGenerator {

    private static final String TAB = "    ";
    private static final String SEPARATOR = System.getProperty("line.separator");

    @Override
    public List<JavaFile> generate(GenerationContext context) {
        return context.getFactoryMetadata().stream()
                .map(it -> JavaFile.builder("com.github.lens", makeType(it)).build())
                .collect(Collectors.toList());
    }

    private TypeSpec makeType(FactoryMetadata factoryMetadata) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(factoryMetadata.getFactoryName());
        builder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        builder.addAnnotation(makeAnnotation());
        builder.addMethod(makeConstructor());
        for (ElementMetadata elementMetadata : factoryMetadata.getElementMetadataList()) {
            for (LensMetadata lensMetadata : elementMetadata.getLensMetadataList()) {
                builder.addField(makeLens(elementMetadata.getElement(), lensMetadata));
            }
        }
        return builder.build();
    }

    private AnnotationSpec makeAnnotation() {
        return AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", LensProcessor.class.getCanonicalName())
                .build();
    }

    private FieldSpec makeLens(Element root, LensMetadata lensMetadata) {
        Deque<Element> fields = lensMetadata.getLensFields();
        Element lastElement = fields.getLast();
        return FieldSpec.builder(makeLensType(root.asType(), lastElement), lensMetadata.getLensName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .initializer(makeExpression(root.asType(), fields, lensMetadata.isOnlyRead()))
                .build();
    }

    private CodeBlock makeExpression(TypeMirror sourceType, Deque<Element> fields, boolean isOnlyRead) {
        CodeBlock.Builder builder = CodeBlock.builder().add(
                "$T.readLens($T::get$L)",
                ClassName.get(Lenses.class),
                ClassName.get(sourceType),
                StringUtils.capitalize(fields.remove().getSimpleName().toString())
        );
        Element lastElement = fields.removeLast();
        for (Element element : fields) {
            TypeMirror baseType = element.getEnclosingElement().asType();
            String name = element.getSimpleName().toString();
            builder.add("$L$L$L$L", SEPARATOR, TAB, TAB, TAB);
            builder.add(
                    ".andThen($T.readLens($T::get$L))",
                    ClassName.get(Lenses.class),
                    ClassName.get(baseType),
                    StringUtils.capitalize(name)
            );
        }

        TypeMirror baseType = lastElement.getEnclosingElement().asType();
        String name = lastElement.getSimpleName().toString();
        builder.add("$L$L$L$L", SEPARATOR, TAB, TAB, TAB);
        Map<String, Object> params = Map.of(
                "lenses", ClassName.get(Lenses.class),
                "baseType", ClassName.get(baseType),
                "fieldName", StringUtils.capitalize(name)
        );
        builder.addNamed(getLastElementTemplate(isOnlyRead), params);
        return builder.build();
    }

    private String getLastElementTemplate(boolean isOnlyRead) {
        return isOnlyRead ? ".andThen($lenses:T.readLens($baseType:T::get$fieldName:L))"
                : ".andThen($lenses:T.readWriteLens($baseType:T::get$fieldName:L, $baseType:T::set$fieldName:L))";
    }

    private TypeName makeLensType(TypeMirror sourceType, Element lastFields) {
        return ParameterizedTypeName.get(
                ClassName.get(ReadLens.class),
                TypeName.get(sourceType),
                TypeName.get(lastFields.asType())
        );
    }

    private MethodSpec makeConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build();
    }
}
