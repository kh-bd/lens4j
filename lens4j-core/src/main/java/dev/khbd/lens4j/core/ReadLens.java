package dev.khbd.lens4j.core;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Read lens.
 *
 * <p>Read lens can be used to get some data from source object.
 * For example, if we have a lens {@code ReadLens<E, A>}, we can use
 * this lens to extract value with type A from entity with type E.
 * <pre>{@code
 * ReadLens<E, A> lens = ...;
 * A a = lens.get(e);
 * }</pre>
 *
 * <p>Be careful with usage of this interface.
 * {@link ReadLens} is functional interface, but not all functional interfaces have
 * null propagation capability.
 *
 * <p>For example,
 * <pre>{@code
 * ReadLens<Entity, Property1> lens = Lenses.readLens(Entity::getProperty1);
 * ReadLens<Entity, DeepProperty2> lens2 =
 *   Lenses.combine(lens, Property1::getDeepProperty2);
 * DeepProperty2 p2 = lens2.get(entity);
 * }</pre>
 * This code can produce {@link java.lang.NullPointerException} if {@code Entity.getProperty1()}
 * will be null. Any similar functional interfaces can be implicitly converted
 * to {@link ReadLens} interface.
 *
 * <p>There are several ways to write the same code more correctly.
 * First, wrap method reference to {@link ReadLens} manually.
 * <pre>{@code
 * ReadLens<Entity, Property1> lens = Lenses.readLens(Entity::getProperty1);
 * ReadLens<Entity, DeepProperty2> lens2 =
 *   Lenses.combine(lens, Lenses.readLens(Property1::getDeepProperty2));
 * DeepProperty2 p2 = lens2.get(entity);
 * }</pre>
 * Second, use 'F' instance methods, which convert supplied functions to lenses internally.
 * For example,
 * <pre>{@code
 * ReadLens<Entity, Property1> lens = Lenses.readLens(Entity::getProperty1);
 * ReadLens<Entity, DeepProperty2> lens2 =
 *   lens1.andThenF(Property1::getDeepProperty2);
 * DeepProperty2 p2 = lens2.get(entity);
 * }</pre>
 * This code works as intended.
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
     * Get property value from object or default value.
     *
     * @param object       object
     * @param defaultValue default value
     * @param <P1>         result type
     * @return property value or default value if property value is {@literal null}
     */
    default <P1 extends P> P getOrElse(O object, P1 defaultValue) {
        P value = get(object);
        if (Objects.nonNull(value)) {
            return value;
        }
        return defaultValue;
    }

    /**
     * Combine current lens with supplied one.
     *
     * @param next next lens
     * @param <P2> deep property type
     * @return combined lens
     * @see Lenses#combine(ReadLens, ReadLens)
     */
    default <P2> ReadLens<O, P2> andThen(ReadLens<? super P, ? extends P2> next) {
        return Lenses.combine(this, next);
    }

    /**
     * Create lens from specified function and combine it with current one.
     *
     * @param getter getter function
     * @param <P2>   deep property type
     * @return combined lens
     * @see #andThen(ReadLens)
     * @see Lenses#combine(ReadLens, ReadLens)
     */
    default <P2> ReadLens<O, P2> andThenF(Function<? super P, ? extends P2> getter) {
        return Lenses.combine(this, Lenses.readLens(getter));
    }

    /**
     * Combine current lens with supplied one.
     *
     * @param next next lens
     * @param <P2> deep property type
     * @return combined lens
     * @see Lenses#combine(ReadLens, ReadWriteLens)
     */
    default <P2> ReadWriteLens<O, P2> andThen(ReadWriteLens<? super P, P2> next) {
        return Lenses.combine(this, next);
    }

    /**
     * Create read-write lens from specified functions and combine it with current one.
     *
     * @param getter getter function
     * @param setter setter function
     * @param <P2>   deep property type
     * @return combined lens
     * @see Lenses#combine(ReadLens, ReadWriteLens)
     * @see #andThen(ReadWriteLens)
     */
    default <P2> ReadWriteLens<O, P2> andThenF(Function<? super P, ? extends P2> getter,
                                               BiConsumer<? super P, ? super P2> setter) {
        return Lenses.combine(this, Lenses.readWriteLens(getter, setter));
    }

    /**
     * Combine specified lens with current one.
     *
     * @param base base lens
     * @param <O1> new object type
     * @return combined lens
     * @see Lenses#combine(ReadLens, ReadLens)
     */
    default <O1> ReadLens<O1, P> compose(ReadLens<? super O1, ? extends O> base) {
        return Lenses.combine(base, this);
    }

    /**
     * Create read-lens from specified function and combine it with current one.
     *
     * @param getter getter function
     * @param <O1>   new object type
     * @return combine lens
     * @see #compose(ReadLens)
     * @see Lenses#combine(ReadLens, ReadLens)
     */
    default <O1> ReadLens<O1, P> composeF(Function<? super O1, ? extends O> getter) {
        return Lenses.combine(Lenses.readLens(getter), this);
    }
}
