/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor.generator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Factory metadata.
 *
 * @author Alexey_Bodyak
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class FactoryMetadata {
    private final String factoryName;
    private final List<ElementMetadata> elementMetadataList;
}
