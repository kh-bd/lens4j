package com.github.lens.core;

/**
 * Read-write lens.
 *
 * @param <O> object type
 * @param <P> property type
 */
public interface ReadWriteLens<O, P> extends ReadLens<O, P> {

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
     * Combine specified lens with current one.
     *
     * @param base base lens
     * @param <O1> new object type
     * @return combined lens
     */
    default <O1> ReadWriteLens<O1, P> compose(ReadLens<? super O1, ? extends O> base) {
        return Lenses.combine(base, this);
    }
}
