package dev.khbd.lens4j.core.annotations;

import java.lang.annotation.Documented;

/**
 * Annotation to supply lens parameters.
 *
 * @author Sergei_Khadanovich
 */
@Documented
public @interface Lens {

    String path();

    /**
     * If lensName is empty, it will be generated from {@link Lens#path}.
     *
     * @return name of lens
     */
    String lensName() default "";

    /**
     * What type of lens should be generated.
     *
     * @return type of lens
     */
    LensType type() default LensType.READ;
}
