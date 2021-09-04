/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor.generator;

import javax.lang.model.element.Element;
import java.util.List;

/**
 * Element metadata.
 *
 * @author Alexey_Bodyak
 */
public class ElementMetadata {
    private final Element element;
    private final List<LensMetadata> lensMetadataList;

    private ElementMetadata(Element element, List<LensMetadata> lensMetadataList) {
        this.element = element;
        this.lensMetadataList = lensMetadataList;
    }

    /**
     * Create element metadata.
     *
     * @param element          element
     * @param lensMetadataList lens metadata
     * @return element metadata
     */
    public static ElementMetadata of(Element element, List<LensMetadata> lensMetadataList) {
        return new ElementMetadata(element, lensMetadataList);
    }

    /**
     * Get element.
     *
     * @return element
     */
    public Element getElement() {
        return element;
    }

    /**
     * Get lens metadata.
     *
     * @return lens metadata
     */
    public List<LensMetadata> getLensMetadataList() {
        return lensMetadataList;
    }
}
