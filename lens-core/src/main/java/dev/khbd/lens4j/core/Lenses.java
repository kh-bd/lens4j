package dev.khbd.lens4j.core;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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
    public static <O, P> ReadLens<O, P> readLens(Function<? super O, ? extends P> getter) {
        return new ReadLensImpl<>(getter);
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
    public static <O, P> ReadWriteLens<O, P> readWriteLens(Function<? super O, ? extends P> getter,
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
    public static <O, P1, P2> ReadLens<O, P2> combine(ReadLens<? super O, ? extends P1> base,
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
    public static <O, P1, P2> ReadWriteLens<O, P2> combine(ReadLens<? super O, ? extends P1> base,
                                                           ReadWriteLens<? super P1, P2> next) {
        return new CombinedReadWriteLensImpl<>(base, next);
    }

    /**
     * Create a 'both' lens from two lenses.
     *
     * <p>If both sub lenses produce non-null results, 'both' lens will produce
     * result of function application to those results.
     *
     * @param lens1    first lens
     * @param lens2    second lens
     * @param combineF combinator function
     * @param <O>      source entity type
     * @param <P1>     first property type
     * @param <P2>     second property type
     * @param <R>      result type
     * @return 'both' lens
     */
    public static <O, P1, P2, R> ReadLens<O, R> both(ReadLens<? super O, ? extends P1> lens1,
                                                     ReadLens<? super O, ? extends P2> lens2,
                                                     BiFunction<? super P1, ? super P2, ? extends R> combineF) {
        return new BothReadLensImpl<>(lens1, lens2, combineF);
    }

    /**
     * Create a 'both' lens from two lenses.
     *
     * <p>If both sub lenses produce non-null results, 'both' lens will produce
     * result of function application to those results.
     *
     * @param lens1       first lens
     * @param lens2       second lens
     * @param combineF    combinator function
     * @param extractorF1 first property extractor function
     * @param extractorF2 second property extractor function
     * @param <O>         source entity type
     * @param <P1>        first property type
     * @param <P2>        second property type
     * @param <R>         result type
     * @return 'both' lens
     */
    public static <O, P1, P2, R> ReadWriteLens<O, R> both(ReadWriteLens<? super O, P1> lens1,
                                                          ReadWriteLens<? super O, P2> lens2,
                                                          BiFunction<? super P1, ? super P2, ? extends R> combineF,
                                                          Function<? super R, ? extends P1> extractorF1,
                                                          Function<? super R, ? extends P2> extractorF2) {
        return new BothReadWriteLensImpl<>(lens1, lens2, combineF, extractorF1, extractorF2);
    }
}
