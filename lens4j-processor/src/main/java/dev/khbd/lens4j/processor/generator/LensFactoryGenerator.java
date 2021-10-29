package dev.khbd.lens4j.processor.generator;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import dev.khbd.lens4j.core.annotations.LensType;
import dev.khbd.lens4j.processor.LensProcessor;
import dev.khbd.lens4j.processor.meta.FactoryMeta;
import dev.khbd.lens4j.processor.meta.LensMeta;
import dev.khbd.lens4j.processor.meta.LensPartMeta;

import javax.annotation.processing.Generated;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Types;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Lens generator.
 *
 * @author Alexey_Bodyak
 */
public class LensFactoryGenerator {

    private static final String UNSUPPORTED_METHOD_MSG = "Can not create instance of factory class";

    private final TypeNameBuilder typeNameBuilder;
    private final Map<LensPartMeta.Shape, LensPartGenerationStrategy> partGenerationStrategies;

    public LensFactoryGenerator(Types typeUtils) {
        typeNameBuilder = new TypeNameBuilder(typeUtils);

        partGenerationStrategies = new EnumMap<>(LensPartMeta.Shape.class);
        partGenerationStrategies.put(LensPartMeta.Shape.ACCESSORS, new AccessorsLensPartGenerationStrategy(typeNameBuilder));
        partGenerationStrategies.put(LensPartMeta.Shape.METHOD, new MethodLensPartGenerationStrategy(typeNameBuilder));
        partGenerationStrategies.put(LensPartMeta.Shape.FIELD, new FieldLensPartGenerationStrategy(typeNameBuilder));
    }

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
        builder.addModifiers(factoryMeta.getFactoryModifiers().toArray(new Modifier[0]));
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
                .addModifiers(lensMeta.getModifiers().toArray(Modifier[]::new))
                .initializer(makeExpression(lensMeta))
                .build();
    }

    private CodeBlock makeExpression(LensMeta lensMeta) {
        CodeBlock.Builder builder = CodeBlock.builder();

        if (lensMeta.isSinglePart()) {
            builder.add(makeLensCodeBlock(lensMeta.getFirstLensPart(), lensMeta.getLensType()));
            return builder.build();
        }

        builder.add(makeLensCodeBlock(lensMeta.getFirstLensPart(), LensType.READ));
        for (LensPartMeta part : lensMeta.getLensPartsWithoutEnds()) {
            builder.add(".andThen($L)", makeLensCodeBlock(part, LensType.READ));
        }
        builder.add(".andThen($L)", makeLensCodeBlock(lensMeta.getLastLensPart(), lensMeta.getLensType()));
        return builder.build();
    }

    private CodeBlock makeLensCodeBlock(LensPartMeta lensPartMeta, LensType lensType) {
        LensPartMeta.Shape shape = lensPartMeta.getShape();
        LensPartGenerationStrategy strategy = partGenerationStrategies.get(shape);
        if (Objects.isNull(strategy)) {
            throw new RuntimeException(String.format("Lens part generation strategy was not found for shape = %s", shape));
        }
        return strategy.generate(lensPartMeta.getSourceType(), lensPartMeta.getTargetType(), lensPartMeta.getName(), lensType);
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
                typeNameBuilder.buildTypeName(lensMeta.getFirstLensPart().getSourceType()),
                typeNameBuilder.buildTypeName(lensMeta.getLastLensPart().getTargetType())
        );
    }

    private TypeName makeLensReadWriteType(LensMeta lensMeta) {
        return ParameterizedTypeName.get(
                ClassName.get(ReadWriteLens.class),
                typeNameBuilder.buildTypeName(lensMeta.getFirstLensPart().getSourceType()),
                typeNameBuilder.buildTypeName(lensMeta.getLastLensPart().getTargetType())
        );
    }

    private MethodSpec makeConstructor() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addStatement("throw new $T($S)",
                        ClassName.get(UnsupportedOperationException.class),
                        UNSUPPORTED_METHOD_MSG
                )
                .build();
    }
}
