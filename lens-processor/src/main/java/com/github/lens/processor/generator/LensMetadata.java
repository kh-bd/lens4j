/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor.generator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.Element;
import java.util.Deque;

/**
 * Information for generating specific lens.
 *
 * @author Alexey_Bodyak
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class LensMetadata {
    private final String lensName;
    private final boolean onlyRead;
    private final Deque<Element> lensFields;
}
