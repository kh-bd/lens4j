package dev.khbd.lens4j.processor.generator;

import com.squareup.javapoet.CodeBlock;
import dev.khbd.lens4j.core.annotations.LensType;
import dev.khbd.lens4j.processor.meta.ResolvedParametrizedTypeMirror;

/**
 * Lens part generation strategy.
 *
 * @author Sergei_Khadanovich
 */
interface LensPartGenerationStrategy {

    /**
     * Generate lens part.
     *
     * @param sourceType source entity type for generated lens
     * @param name       property or method name which was used in {@code Lens#path}
     * @param lensType   lens type
     * @return generated code block for specified lens part
     */
    CodeBlock generate(ResolvedParametrizedTypeMirror sourceType, String name, LensType lensType);
}
