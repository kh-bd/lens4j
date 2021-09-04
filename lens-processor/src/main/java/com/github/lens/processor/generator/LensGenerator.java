package com.github.lens.processor.generator;

import com.github.lens.core.Lenses;
import com.github.lens.core.ReadLens;
import com.github.lens.core.annotations.LensType;
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
 * Lens generator.
 *
 * @author Alexey_Bodyak
 */
public class LensGenerator {

    private static final String TAB = "    ";
    private static final String SEPARATOR = System.getProperty("line.separator");

    public List<JavaFile> generate(GenerationContext context) {
        return context.getFactoryMetadata().stream()
                .map(it -> JavaFile.builder(it.getFactoryPackage(), makeType(it)).build())
                .collect(Collectors.toList());
    }

    private TypeSpec makeType(FactoryMetadata factoryMetadata) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(factoryMetadata.getFactoryName());
        builder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        builder.addAnnotation(makeAnnotation());
        builder.addMethod(makeConstructor());
        for (LensMetadata lensMetadata : factoryMetadata.getLensMetadata()) {
            builder.addField(makeLens(factoryMetadata.getRootType(), lensMetadata));
        }
        return builder.build();
    }

    private AnnotationSpec makeAnnotation() {
        return AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", LensProcessor.class.getCanonicalName())
                .build();
    }

    private FieldSpec makeLens(TypeMirror rootType, LensMetadata lensMetadata) {
        Deque<Element> fields = lensMetadata.getFields();
        Element lastElement = fields.getLast();
        return FieldSpec.builder(makeLensType(rootType, lastElement), lensMetadata.getLensName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .initializer(makeExpression(rootType, fields, lensMetadata.getLensType()))
                .build();
    }

    private CodeBlock makeExpression(TypeMirror sourceType, Deque<Element> fields, LensType lensType) {
        Element firstElement = fields.removeFirst();
        Element lastElement = fields.removeLast();
        CodeBlock.Builder builder = CodeBlock.builder().add(
                "$T.readLens($T::get$L)",
                ClassName.get(Lenses.class),
                ClassName.get(sourceType),
                StringUtils.capitalize(firstElement.toString())
        );
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
        builder.addNamed(getLastElementTemplate(lensType), params);
        return builder.build();
    }

    private String getLastElementTemplate(LensType lensType) {
        return lensType == LensType.READ ? ".andThen($lenses:T.readLens($baseType:T::get$fieldName:L))"
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
