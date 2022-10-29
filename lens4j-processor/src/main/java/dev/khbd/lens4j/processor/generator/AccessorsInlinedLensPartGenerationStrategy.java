package dev.khbd.lens4j.processor.generator;

import com.squareup.javapoet.CodeBlock;
import dev.khbd.lens4j.processor.meta.LensPartMeta;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Sergei_Khadanovich
 */
class AccessorsInlinedLensPartGenerationStrategy implements InlinedLensPartGenerationStrategy {

    @Override
    public CodeBlock generateRead(String sourceName, LensPartMeta meta) {
        return CodeBlock.builder()
                .add("$L.get$L()", sourceName, StringUtils.capitalize(meta.getName()))
                .build();
    }

    @Override
    public CodeBlock generateWrite(String sourceName, LensPartMeta meta, String propertyName) {
        return CodeBlock.builder()
                .add("$L.set$L($L)", sourceName, StringUtils.capitalize(meta.getName()), propertyName)
                .build();
    }
}
