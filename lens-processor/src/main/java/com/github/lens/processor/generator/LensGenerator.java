package com.github.lens.processor.generator;

import com.github.lens.core.Lenses;
import com.github.lens.core.ReadLens;
import com.github.lens.core.ReadWriteLens;
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
import javax.lang.model.element.Modifier;
import java.util.Map;

/**
 * Lens generator.
 *
 * @author Alexey_Bodyak
 */
public class LensGenerator {

    private static final String TAB = "    ";
    private static final String SEPARATOR = System.getProperty("line.separator");

    /**
     * Generate factory source file.
     *
     * @param factoryMeta factory meta
     * @return generated java file
     */
    public JavaFile generate(FactoryMeta factoryMeta) {
        return JavaFile.builder(factoryMeta.getPackageName(), makeType(factoryMeta)).build();
    }

    private TypeSpec makeType(FactoryMeta factoryMeta) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(factoryMeta.getFactoryName());
        builder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        builder.addAnnotation(makeGeneratedAnnotation());
        builder.addMethod(makeConstructor());
        for (LensMeta lensMeta : factoryMeta.getLenses()) {
            builder.addField(makeLens(lensMeta));
        }
        return builder.build();
    }

    private AnnotationSpec makeGeneratedAnnotation() {
        return AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", LensProcessor.class.getCanonicalName())
                .build();
    }

    private FieldSpec makeLens(LensMeta lensMeta) {
        return FieldSpec.builder(makeLensType(lensMeta), lensMeta.getLensName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .initializer(makeExpression(lensMeta))
                .build();
    }

    private CodeBlock makeExpression(LensMeta lensMeta) {
        LensPartMeta firstPart = lensMeta.getFirstLensPart();

        CodeBlock.Builder builder = CodeBlock.builder().add(
                "$T.readLens($T::get$L)",
                ClassName.get(Lenses.class),
                ClassName.get(firstPart.getSourceType()),
                StringUtils.capitalize(firstPart.getPropertyName())
        );

        for (int i = 1; i < lensMeta.getLensParts().size() - 1; i++) {
            LensPartMeta part = lensMeta.getLensParts().get(i);

            builder.add("$L$L$L$L", SEPARATOR, TAB, TAB, TAB);
            builder.add(".andThen($T.readLens($T::get$L))",
                    ClassName.get(Lenses.class),
                    ClassName.get(part.getSourceType()),
                    StringUtils.capitalize(part.getPropertyName())
            );
        }

        LensPartMeta lastPart = lensMeta.getLastLensPart();

        builder.add("$L$L$L$L", SEPARATOR, TAB, TAB, TAB);
        Map<String, Object> params = Map.of(
                "lenses", ClassName.get(Lenses.class),
                "baseType", ClassName.get(lastPart.getSourceType()),
                "fieldName", StringUtils.capitalize(lastPart.getPropertyName())
        );
        builder.addNamed(getLastElementTemplate(lensMeta.getLensType()), params);
        return builder.build();
    }

    private String getLastElementTemplate(LensType lensType) {
        return lensType == LensType.READ ? ".andThen($lenses:T.readLens($baseType:T::get$fieldName:L))"
                : ".andThen($lenses:T.readWriteLens($baseType:T::get$fieldName:L, $baseType:T::set$fieldName:L))";
    }

    private TypeName makeLensType(LensMeta lensMeta) {
        if (lensMeta.getLensType() == LensType.READ) {
            return makeLensReadType(lensMeta);
        }
        return makeLensReadWriteType(lensMeta);
    }

    private TypeName makeLensReadType(LensMeta lensMeta) {
        return ParameterizedTypeName.get(
                ClassName.get(ReadLens.class),
                TypeName.get(lensMeta.getFirstLensPart().getSourceType()),
                TypeName.get(lensMeta.getLastLensPart().getPropertyType())
        );
    }

    private TypeName makeLensReadWriteType(LensMeta lensMeta) {
        return ParameterizedTypeName.get(
                ClassName.get(ReadWriteLens.class),
                TypeName.get(lensMeta.getFirstLensPart().getSourceType()),
                TypeName.get(lensMeta.getLastLensPart().getPropertyType())
        );
    }

    private MethodSpec makeConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build();
    }
}
