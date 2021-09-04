package com.github.lens.processor.generator;

import com.github.lens.core.annotations.LensType;

import javax.lang.model.element.Element;
import java.util.Deque;

/**
 * Information for generating specific lens.
 *
 * @author Alexey_Bodyak
 */
public class LensMetadata {
    private final String lensName;
    private final LensType lensType;
    private final Deque<Element> fields;

    private LensMetadata(String lensName, LensType lensType,
                         Deque<Element> fields) {
        this.lensName = lensName;
        this.lensType = lensType;
        this.fields = fields;
    }

    /**
     * Create lens metadata.
     *
     * @param lensName lens name
     * @param lensType lens type
     * @param fields   lens fields
     * @return lens metadata
     */
    public static LensMetadata of(String lensName, LensType lensType, Deque<Element> fields) {
        return new LensMetadata(lensName, lensType, fields);
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
     * Get lens type.
     *
     * @return lens type
     */
    public LensType getLensType() {
        return lensType;
    }

    /**
     * Get lens fields.
     *
     * @return lens fields
     */
    public Deque<Element> getFields() {
        return fields;
    }
}
