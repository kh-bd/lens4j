package com.github.lens.processor;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.lang.model.type.TypeMirror;

/**
 * Meta to represent lens which will be generated.
 *
 * @author Sergei_Khadanovich
 */
@Data
@Accessors(chain = true)
class LensMeta {

    /**
     * User-defined lens name.
     */
    String lensName;
    TypeMirror sourceType;
    TypeMirror propertyType;
    boolean read;
    String propertyName;

    /**
     * Can be null. It means current lens was the last in chain.
     */
    LensMeta nextLens;
}
