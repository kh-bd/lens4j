package dev.khbd.lens4j.processor.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.annotations.LensType;
import dev.khbd.lens4j.processor.meta.ResolvedParametrizedTypeMirror;

import java.util.Map;

/**
 * @author Sergei_Khadanovich
 */
class FieldLensPartGenerationStrategy implements LensPartGenerationStrategy {

    private static final String READ_LENS_CODE_BLOCK_TEMPLATE =
            "$lenses:T.readLens(($sourceType:T o) -> o.$fieldName:L)";
    private static final String READ_WRITE_LENS_CODE_BLOCK_TEMPLATE =
            "$lenses:T.readWriteLens(($sourceType:T o) -> o.$fieldName:L, ($sourceType:T o, $targetType:T p) -> o.$fieldName:L = p)";

    private final TypeNameBuilder typeNameBuilder;

    FieldLensPartGenerationStrategy(TypeNameBuilder typeNameBuilder) {
        this.typeNameBuilder = typeNameBuilder;
    }

    @Override
    public CodeBlock generate(ResolvedParametrizedTypeMirror sourceType,
                              ResolvedParametrizedTypeMirror targetType,
                              String name,
                              LensType lensType) {
        Map<String, Object> params = Map.of(
                "lenses", ClassName.get(Lenses.class),
                "sourceType", typeNameBuilder.buildTypeName(sourceType),
                "targetType", typeNameBuilder.buildTypeName(targetType),
                "fieldName", name
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
}
