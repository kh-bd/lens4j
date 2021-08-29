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
     * Combine current lens with supplied one.
     *
     * @param next next lens
     * @param <P2> deep property type
     * @return combined lens
     */
    default <P2> Lens<O, P2> andThen(Lens<? super P, P2> next) {
        return Lens.combine(this, next);
    }

    /**
     * Combine specified lens with current one.
     *
     * @param base base lens
     * @param <O1> new object type
     * @return combined lens
     */
    default <O1> Lens<O1, P> compose(Lens<? super O1, ? extends O> base) {
        return Lens.combine(base, this);
    }

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

    /**
     * Create a combined lens from two lenses.
     *
     * @param base base lens
     * @param next next lens
     * @param <O>  object type
     * @param <P1> first property type
     * @param <P2> second property type
     * @return combined lens
     */
    static <O, P1, P2> Lens<O, P2> combine(Lens<? super O, ? extends P1> base,
                                           Lens<? super P1, P2> next) {
        return new CombinedLens<>(base, next);
    }
}
