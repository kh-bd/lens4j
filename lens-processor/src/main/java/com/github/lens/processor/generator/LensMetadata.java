/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2021 VTB Group. All rights reserved.
 */

package com.github.lens.processor.generator;

import javax.lang.model.element.Element;
import java.util.Deque;

/**
 * Information for generating specific lens.
 *
 * @author Alexey_Bodyak
 */
public class LensMetadata {
    private final String lensName;
    private final boolean onlyRead;
    private final Deque<Element> lensFields;

    private LensMetadata(String lensName, boolean onlyRead,
                         Deque<Element> lensFields) {
        this.lensName = lensName;
        this.onlyRead = onlyRead;
        this.lensFields = lensFields;
    }

    /**
     * Create lens metadata.
     *
     * @param lensName   lens name
     * @param onlyRead   {@code true} if need create only read lens {@code false} otherwise
     * @param lensFields lens fields
     * @return lens metadata
     */
    public static LensMetadata of(String lensName, boolean onlyRead,
                                  Deque<Element> lensFields) {
        return new LensMetadata(lensName, onlyRead, lensFields);
    }

    /**
     * Get lens name.
     *
     * @return lens name
     */
    public String getLensName() {
        return lensName;
    }

    /**
     * Is only read.
     *
     * @return {@code true} if need create only read lens {@code false} otherwise
     */
    public boolean isOnlyRead() {
        return onlyRead;
    }

    /**
     * Get lens fields.
     *
     * @return lens fields
     */
    public Deque<Element> getLensFields() {
        return lensFields;
    }
}
