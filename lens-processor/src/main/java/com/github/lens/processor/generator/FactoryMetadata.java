/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor.generator;

import java.util.List;

/**
 * Factory metadata.
 *
 * @author Alexey_Bodyak
 */
public class FactoryMetadata {
    private final String factoryName;
    private final List<ElementMetadata> elementMetadataList;

    private FactoryMetadata(String factoryName, List<ElementMetadata> elementMetadataList) {
        this.factoryName = factoryName;
        this.elementMetadataList = elementMetadataList;
    }

    /**
     * Create factory metadata.
     *
     * @param factoryName         factory name
     * @param elementMetadataList element metadata
     * @return factory metadata
     */
    public static FactoryMetadata of(String factoryName, List<ElementMetadata> elementMetadataList) {
        return new FactoryMetadata(factoryName, elementMetadataList);
    }

    /**
     * Get factory name.
     *
     * @return factory name
     */
    public String getFactoryName() {
        return factoryName;
    }

    /**
     * Get element metadata.
     *
     * @return element metadata
     */
    public List<ElementMetadata> getElementMetadataList() {
        return elementMetadataList;
    }
}
