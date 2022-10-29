package dev.khbd.lens4j.processor.generator;

import com.squareup.javapoet.CodeBlock;
import dev.khbd.lens4j.processor.meta.LensPartMeta;

/**
 * @author Sergei_Khadanovich
 */
class MethodInlinedLensPartGenerationStrategy implements InlinedLensPartGenerationStrategy {

    @Override
    public CodeBlock generateRead(String sourceName, LensPartMeta meta) {
        return CodeBlock.builder()
                .add("$L.$L()", sourceName, meta.getName())
                .build();
    }

    @Override
    public CodeBlock generateWrite(String sourceName, LensPartMeta meta, String propertyName) {
        throw new IllegalArgumentException("Method-based generation strategy can not be used in write position");
    }
}
