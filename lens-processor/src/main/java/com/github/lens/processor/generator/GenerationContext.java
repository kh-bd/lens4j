package com.github.lens.processor.generator;

import java.util.List;

/**
 * Context for generators.
 *
 * @author Alexey_Bodyak
 */
public class GenerationContext {
    private final List<FactoryMetadata> factoryMetadata;

    private GenerationContext(List<FactoryMetadata> factoryMetadata) {
        this.factoryMetadata = factoryMetadata;
    }

    /**
     * Create generation context with factory metadata.
     *
     * @param factoryMetadata factory metadata
     * @return generation context
     */
    public static GenerationContext of(List<FactoryMetadata> factoryMetadata) {
        return new GenerationContext(factoryMetadata);
    }

    /**
     * Get factory metadata.
     *
     * @return factory metadata
     */
    public List<FactoryMetadata> getFactoryMetadata() {
        return factoryMetadata;
    }
}
