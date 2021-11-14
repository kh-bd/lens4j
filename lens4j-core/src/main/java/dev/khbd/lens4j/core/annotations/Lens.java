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
     * If lensName is empty, it will be derived from {@link Lens#path}.
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

    /**
     * What access level should be used for lens instance.
     *
     * @return generated lens access level
     */
    AccessLevel accessLevel() default AccessLevel.PUBLIC;

    /**
     * Lens instance access level.
     */
    enum AccessLevel {
        PUBLIC,
        PACKAGE
    }
}
