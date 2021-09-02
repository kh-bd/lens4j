/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor.generator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.Element;
import java.util.List;

/**
 * Element metadata.
 *
 * @author Alexey_Bodyak
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class ElementMetadata {
    private final Element element;
    private final List<LensMetadata> lensMetadataList;
}
