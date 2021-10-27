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
class MethodLensPartGenerationStrategy implements LensPartGenerationStrategy {

    private static final String TEMPLATE = "$lenses:T.readLens($sourceType:T::$methodName:L)";

    private final TypeNameBuilder typeNameBuilder;

    MethodLensPartGenerationStrategy(TypeNameBuilder typeNameBuilder) {
        this.typeNameBuilder = typeNameBuilder;
    }

    @Override
    public CodeBlock generate(ResolvedParametrizedTypeMirror sourceType, String name, LensType lensType) {
        verifyReadOnly(lensType);

        Map<String, Object> params = Map.of(
                "lenses", ClassName.get(Lenses.class),
                "sourceType", typeNameBuilder.buildTypeName(sourceType),
                "methodName", name
        );

        return CodeBlock.builder()
                .addNamed(TEMPLATE, params)
                .build();
    }

    private void verifyReadOnly(LensType lensType) {
        if (lensType == LensType.READ_WRITE) {
            throw new IllegalArgumentException("Method-based generation strategy can not be used in write position");
        }
    }
}
