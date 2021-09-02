package com.github.lens.core;

/**
 * Read lens.
 *
 * @param <O> object type
 * @param <P> property type
 */
public interface ReadLens<O, P> {

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
     * Combine current lens with supplied one.
     *
     * @param next next lens
     * @param <P2> deep property type
     * @return combined lens
     */
    default <P2> ReadLens<O, P2> andThen(ReadLens<? super P, ? extends P2> next) {
        return Lenses.combine(this, next);
    }

    /**
     * Combine current lens with supplied one.
     *
     * @param next next lens
     * @param <P2> deep property type
     * @return combined lens
     */
    default <P2> ReadWriteLens<O, P2> andThen(ReadWriteLens<? super P, P2> next) {
        return Lenses.combine(this, next);
    }

    /**
     * Combine specified lens with current one.
     *
     * @param base base lens
     * @param <O1> new object type
     * @return combined lens
     */
    default <O1> ReadLens<O1, P> compose(ReadLens<? super O1, ? extends O> base) {
        return Lenses.combine(base, this);
    }
}
