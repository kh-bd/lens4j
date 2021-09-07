package dev.khbd.lens4j.core;

import java.util.Objects;
import java.util.function.Function;

/**
 * Read-write lens.
 *
 * <p>Read-write lens has the same properties as {@link ReadLens},
 * it can be used to get data from original objects.
 * For example, if we have a lens {@code ReadWriteLens<E, A>}, we can use
 * this lens to get value with type A from object with type E.
 * <pre>{@code
 * ReadWriteLens<E, A> lens = ...;
 * A a = lens.get(e);
 * }</pre>
 * Moreover, read-write lens can be used to set value into source objects.
 * Again, if we have a lens {@code ReadWriteLens<E, A>}, we can set
 * value of type A into object with type E.
 * <pre>{@code
 * ReadWriteLens<E, A> lens = ...;
 * lens.set(e, a);
 * }</pre>
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
     * @see Lenses#combine(ReadLens, ReadWriteLens)
     */
    @Override
    default <O1> ReadWriteLens<O1, P> compose(ReadLens<? super O1, ? extends O> base) {
        return Lenses.combine(base, this);
    }
}
