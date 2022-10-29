package dev.khbd.lens4j.processor.generator;

import com.squareup.javapoet.JavaFile;
import dev.khbd.lens4j.processor.meta.FactoryMeta;

/**
 * Base interface for all factory generation variants.
 *
 * @author Sergei_Khadanovich
 */
public interface LensFactoryGenerator {


    /**
     * Generate factory source file.
     *
     * @param factoryMeta factory meta
     * @return generated java file
     */
    JavaFile generate(FactoryMeta factoryMeta);
}
