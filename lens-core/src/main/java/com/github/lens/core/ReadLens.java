package com.github.lens.core;

/**
 * Read lens.
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
 * <p>To write the same code more correctly, wrap method reference to {@link ReadLens} manually.
 * <pre>{@code
 * ReadLens<Entity, Property1> lens = Lenses.readLens(Entity::getProperty1);
 * ReadLens<Entity, DeepProperty2> lens2 =
 *   Lenses.combine(lens, Lenses.readLens(Property1::getDeepProperty2));
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
