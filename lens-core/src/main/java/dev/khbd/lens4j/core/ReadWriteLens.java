package dev.khbd.lens4j.core;

import java.util.Objects;
import java.util.function.Function;

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
     * Modify property value with specified function.
     *
     * <p>Note: function {@code f} will not be invoked if property value is null.
     *
     * @param object object
     * @param f      property modification function
     */
    default void modify(O object, Function<? super P, ? extends P> f) {
        P value = get(object);
        if (Objects.nonNull(value)) {
            P newValue = f.apply(value);
            set(object, newValue);
        }
    }

    /**
     * Combine specified lens with current one.
     *
     * @param base base lens
     * @param <O1> new object type
     * @return combined lens
     */
    @Override
    default <O1> ReadWriteLens<O1, P> compose(ReadLens<? super O1, ? extends O> base) {
        return Lenses.combine(base, this);
    }
}
