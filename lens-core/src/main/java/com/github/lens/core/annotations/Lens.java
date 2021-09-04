package com.github.lens.core.annotations;

import java.lang.annotation.Documented;

/**
 * Annotation to supply lens parameters.
 *
 * @author Sergei_Khadanovich
 */
@Documented
public @interface Lens {

    String path();

    String lensName();

    /**
     * What type of lens should be generated.
     *
     * @return type of lens
     */
    LensType type() default LensType.READ;
}
