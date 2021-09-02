/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor.generator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Context for generators.
 *
 * @author Alexey_Bodyak
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class GenerationContext {
    private final List<FactoryMetadata> factoryMetadata;
}
