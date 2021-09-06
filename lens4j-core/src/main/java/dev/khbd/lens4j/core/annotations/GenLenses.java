package dev.khbd.lens4j.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to generated factory with specified lenses.
 *
 * @author Sergei_Khadanovich
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface GenLenses {

    /**
     * Generated factory name.
     *
     * @return factory name
     */
    String factoryName() default "";

    /**
     * Lenses which should be added to generated factory.
     *
     * @return lenses
     */
    Lens[] lenses() default {};
}
