package dev.khbd.lens4j.processor.generator;

import com.squareup.javapoet.CodeBlock;
import dev.khbd.lens4j.core.annotations.LensType;
import dev.khbd.lens4j.processor.meta.LensPartMeta;

/**
 * @author Sergei_Khadanovich
 */
class FieldInlinedLensPartGenerationStrategy implements InlinedLensPartGenerationStrategy {

    @Override
    public CodeBlock generateRead(String sourceName, LensPartMeta meta) {
        return CodeBlock.builder()
                .add("$L.$L", sourceName, meta.getName())
                .build();
    }

    @Override
    public CodeBlock generateWrite(String sourceName, LensPartMeta meta, String propertyName) {
        return CodeBlock.builder()
                .add("$L.$L = $L", sourceName, meta.getName(), propertyName)
                .build();
    }
}
