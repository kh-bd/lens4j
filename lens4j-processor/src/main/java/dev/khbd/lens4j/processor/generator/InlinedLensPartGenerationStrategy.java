package dev.khbd.lens4j.processor.generator;

import com.squareup.javapoet.CodeBlock;
import dev.khbd.lens4j.processor.meta.LensPartMeta;

/**
 * Lens part generation strategy.
 *
 * @author Sergei_Khadanovich
 */
interface InlinedLensPartGenerationStrategy {

    /**
     * Generate code to read property on source instance.
     *
     * @param sourceName source variable name
     * @param meta       lens part meta data
     * @return code block to read property
     */
    CodeBlock generateRead(String sourceName, LensPartMeta meta);

    /**
     * Generate code to write property into source instance.
     *
     * @param sourceName   source variable name
     * @param meta         lens part meta data
     * @param propertyName property variable name
     * @return code block to write property
     */
    CodeBlock generateWrite(String sourceName, LensPartMeta meta, String propertyName);
}
