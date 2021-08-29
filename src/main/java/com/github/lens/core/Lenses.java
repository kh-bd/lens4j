package com.github.lens.core;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Utility class to create and combine lenses.
 *
 * @author Sergei_Khadanovich
 */
public class Lenses {

    private Lenses() {
    }

    /**
     * Create a read lens from getter method.
     *
     * @param getter getter function
     * @param <O>    object type
     * @param <P>    property type
     * @return read lens
     */
    static <O, P> ReadLens<O, P> readLens(Function<? super O, ? extends P> getter) {
        return new ReadWriteLensImpl<>(getter, null);
    }

    /**
     * Create a read-write lens from accessors methods.
     *
     * @param getter getter accessor
     * @param setter setter accessor
     * @param <O>    object type
     * @param <P>    property type
     * @return read-write lens
     */
    static <O, P> ReadWriteLens<O, P> readWriteLens(Function<? super O, ? extends P> getter,
                                                    BiConsumer<? super O, ? super P> setter) {
        return new ReadWriteLensImpl<>(getter, setter);
    }

    /**
     * Create a combined read lens from two read lenses.
     *
     * @param base base read lens
     * @param next next read lens
     * @param <O>  object type
     * @param <P1> first property type
     * @param <P2> second property type
     * @return combined read lens
     */
    static <O, P1, P2> ReadLens<O, P2> combine(ReadLens<? super O, ? extends P1> base,
                                               ReadLens<? super P1, ? extends P2> next) {
        return new CombinedReadLensImpl<>(base, next);
    }

    /**
     * Create a combined read-write lens from two lenses.
     *
     * @param base base read lens
     * @param next next read lens
     * @param <O>  object type
     * @param <P1> first property type
     * @param <P2> second property type
     * @return combined read lens
     */
    static <O, P1, P2> ReadWriteLens<O, P2> combine(ReadLens<? super O, ? extends P1> base,
                                                    ReadWriteLens<? super P1, P2> next) {
        return new CombinedReadWriteLensImpl<>(base, next);
    }
}
