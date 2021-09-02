/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor.generator;

import com.squareup.javapoet.JavaFile;

import java.util.List;

/**
 * Base interface for lens generators.
 *
 * @author Alexey_Bodyak
 */
public interface LensGenerator {

    /**
     * Generate java file with lenses
     *
     * @param context generation context
     * @return java file with lenses
     */
    List<JavaFile> generate(GenerationContext context);
}
