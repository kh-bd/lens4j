package com.github.lens;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Lens interface.
 *
 * <p>To create a simple, accessor based lens use {@link Lens#fromAccessors(Function, BiConsumer)}
 * factory method.
 * <p>For example,
 * <pre> {@code
 * Lens<Person, String> nameLens =
 *   Lens.fromAccessors(Person::getName, Person::setName);
 * }</pre>
 *
 * @param <O> object type
 * @param <P> property type
 * @author Sergei_Khadanovich
 */
public interface Lens<O, P> {

    /**
     * Get property from object.
     *
     * <p>Object value can be {@literal null}. In this case, return value will be {@literal null}.
     *
     * @param object object
     * @return extracted property value
     */
    P get(O object);

    /**
     * Set property into object.
     *
     * <p>Object value can be {@literal null}. In this case, property value will not be set.
     *
     * @param object   object
     * @param property property value
     */
    void set(O object, P property);

    /**
     * Create a lens from accessors methods.
     *
     * @param getter getter accessor
     * @param setter setter accessor
     * @param <O>    object type
     * @param <P>    property type
     * @return lens
     */
    static <O, P> Lens<O, P> fromAccessors(Function<? super O, ? extends P> getter,
                                           BiConsumer<? super O, ? super P> setter) {
        return new AccessorsLens<>(getter, setter);
    }
}
