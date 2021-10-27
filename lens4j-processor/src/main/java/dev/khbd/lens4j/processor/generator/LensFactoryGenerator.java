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
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.ReadLens;
import dev.khbd.lens4j.core.ReadWriteLens;
import dev.khbd.lens4j.core.annotations.LensType;
import dev.khbd.lens4j.processor.LensProcessor;
import dev.khbd.lens4j.processor.meta.FactoryMeta;
import dev.khbd.lens4j.processor.meta.LensMeta;
import dev.khbd.lens4j.processor.meta.LensPartMeta;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.Generated;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Types;
import java.util.Map;

/**
 * Lens generator.
 *
 * @author Alexey_Bodyak
 */
public class LensFactoryGenerator {

    private static final String UNSUPPORTED_METHOD_MSG = "Can not create instance of factory class";

    private static final String READ_LENS_CODE_BLOCK_TEMPLATE =
            "$lenses:T.readLens($sourceType:T::get$fieldName:L)";
    private static final String READ_WRITE_LENS_CODE_BLOCK_TEMPLATE =
            "$lenses:T.readWriteLens($sourceType:T::get$fieldName:L, $sourceType:T::set$fieldName:L)";

    private final TypeNameBuilder typeNameBuilder;

    public LensFactoryGenerator(Types typeUtils) {
        this.typeNameBuilder = new TypeNameBuilder(typeUtils);
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
        Map<String, Object> params = Map.of(
                "lenses", ClassName.get(Lenses.class),
                "sourceType", typeNameBuilder.buildTypeName(lensPartMeta.getSourceType()),
                "fieldName", StringUtils.capitalize(lensPartMeta.getPropertyName())
        );
        return CodeBlock.builder()
                .addNamed(getLensCodeBlockTemplate(lensType), params)
                .build();
    }

    private String getLensCodeBlockTemplate(LensType lensType) {
        return lensType == LensType.READ
                ? READ_LENS_CODE_BLOCK_TEMPLATE
                : READ_WRITE_LENS_CODE_BLOCK_TEMPLATE;
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
                typeNameBuilder.buildTypeName(lensMeta.getLastLensPart().getPropertyType())
        );
    }

    private TypeName makeLensReadWriteType(LensMeta lensMeta) {
        return ParameterizedTypeName.get(
                ClassName.get(ReadWriteLens.class),
                typeNameBuilder.buildTypeName(lensMeta.getFirstLensPart().getSourceType()),
                typeNameBuilder.buildTypeName(lensMeta.getLastLensPart().getPropertyType())
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
