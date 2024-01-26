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
public class CombinedLensFactoryGenerator implements LensFactoryGenerator {

    private static final String UNSUPPORTED_METHOD_MSG = "Can not create instance of factory class";

    private final TypeNameBuilder typeNameBuilder;
    private final Map<LensPartMeta.Shape, LensPartGenerationStrategy> partGenerationStrategies;

    public CombinedLensFactoryGenerator(Types typeUtils) {
        typeNameBuilder = new TypeNameBuilder(typeUtils);

        partGenerationStrategies = new EnumMap<>(LensPartMeta.Shape.class);
        partGenerationStrategies.put(LensPartMeta.Shape.ACCESSORS, new AccessorsLensPartGenerationStrategy(typeNameBuilder));
        partGenerationStrategies.put(LensPartMeta.Shape.METHOD, new MethodLensPartGenerationStrategy(typeNameBuilder));
        partGenerationStrategies.put(LensPartMeta.Shape.FIELD, new FieldLensPartGenerationStrategy(typeNameBuilder));
    }

    @Override
    public JavaFile generate(FactoryMeta factoryMeta) {
        return JavaFile.builder(factoryMeta.getId().getPackageName(), makeType(factoryMeta)).build();
    }

    private TypeSpec makeType(FactoryMeta factoryMeta) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(factoryMeta.getId().getFactoryName());
        builder.addModifiers(factoryMeta.getModifiers().toArray(new Modifier[0]));
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
        return FieldSpec.builder(makeLensType(lensMeta), lensMeta.getName())
                .addModifiers(lensMeta.getModifiers().toArray(Modifier[]::new))
                .initializer(makeExpression(lensMeta))
                .build();
    }

    private CodeBlock makeExpression(LensMeta lensMeta) {
        CodeBlock.Builder builder = CodeBlock.builder();

        if (lensMeta.isSinglePart()) {
            builder.add(makeLensCodeBlock(lensMeta.getFirstPart(), lensMeta.getType()));
            return builder.build();
        }

        builder.add(makeLensCodeBlock(lensMeta.getFirstPart(), LensType.READ));
        for (LensPartMeta part : lensMeta.getPartsWithoutEnds()) {
            builder.add(".andThen($L)", makeLensCodeBlock(part, LensType.READ));
        }
        builder.add(".andThen($L)", makeLensCodeBlock(lensMeta.getLastPart(), lensMeta.getType()));
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
        if (lensMeta.getType() == LensType.READ) {
            return makeLensReadType(lensMeta);
        }
        return makeLensReadWriteType(lensMeta);
    }

    private TypeName makeLensReadType(LensMeta lensMeta) {
        return ParameterizedTypeName.get(
                ClassName.get(ReadLens.class),
                typeNameBuilder.buildTypeName(lensMeta.getFirstPart().getSourceType()),
                typeNameBuilder.buildTypeName(lensMeta.getLastPart().getTargetType())
        );
    }

    private TypeName makeLensReadWriteType(LensMeta lensMeta) {
        return ParameterizedTypeName.get(
                ClassName.get(ReadWriteLens.class),
                typeNameBuilder.buildTypeName(lensMeta.getFirstPart().getSourceType()),
                typeNameBuilder.buildTypeName(lensMeta.getLastPart().getTargetType())
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
