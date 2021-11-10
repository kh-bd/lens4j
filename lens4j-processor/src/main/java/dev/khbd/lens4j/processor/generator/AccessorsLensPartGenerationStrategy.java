package dev.khbd.lens4j.processor.generator;

import com.google.common.collect.ImmutableMap;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import dev.khbd.lens4j.core.Lenses;
import dev.khbd.lens4j.core.annotations.LensType;
import dev.khbd.lens4j.processor.meta.ResolvedParametrizedTypeMirror;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author Sergei_Khadanovich
 */
class AccessorsLensPartGenerationStrategy implements LensPartGenerationStrategy {

    private static final String READ_LENS_CODE_BLOCK_TEMPLATE =
            "$lenses:T.readLens($sourceType:T::get$fieldName:L)";
    private static final String READ_WRITE_LENS_CODE_BLOCK_TEMPLATE =
            "$lenses:T.readWriteLens($sourceType:T::get$fieldName:L, $sourceType:T::set$fieldName:L)";

    private final TypeNameBuilder typeNameBuilder;

    AccessorsLensPartGenerationStrategy(TypeNameBuilder typeNameBuilder) {
        this.typeNameBuilder = typeNameBuilder;
    }

    @Override
    public CodeBlock generate(ResolvedParametrizedTypeMirror sourceType,
                              ResolvedParametrizedTypeMirror ignore,
                              String name,
                              LensType lensType) {
        Map<String, Object> params = ImmutableMap.of(
                "lenses", ClassName.get(Lenses.class),
                "sourceType", typeNameBuilder.buildTypeName(sourceType),
                "fieldName", StringUtils.capitalize(name)
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
