package dev.khbd.lens4j.core;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Utility class to create and combine lenses.
 *
 * <p>To create {@link ReadLens} use {@link Lenses#readLens(Function)} factory method.
 * For example, {@link ReadLens} can be created from method reference:
 * <pre>{@code
 * ReadLens<Entity, Property> lens = Lenses.readLens(Entity::getProperty);
 * }</pre>
 *
 * <p>To create {@link ReadWriteLens} use {@link Lenses#readWriteLens(Function, BiConsumer)} factory method.
 * To create read-write lens for property from last example, you can write code like this:
 * <pre>{@code
 * ReadWriteLens<Entity, Property> lens =
 *   Lenses.readWriteLens(Entity::getProperty, Entity::setProperty);
 * }</pre>
 *
 * <p>Lenses can be combined with each other to create more complicated lenses.
 * To continue we assume there are several classes:
 * <pre>{@code
 * class Account {
 *     Currency currency;
 *     String accountNumber;
 *
 *     ... accessor methods here
 * }
 * class Currency {
 *      String code;
 *
 *      ... accessor methods here
 * }
 * }</pre>
 *
 * <p>To create lens which can read currency code from account instance, we can combine
 * two lenses into bigger one.
 * <pre>{@code
 * ReadLens<Account, Currency> curLens = Lenses.readLens(Account::getCurrency);
 * ReadLens<Currency, String> codeLens = Lenses.readLens(Currency::getCode);
 * ReadLens<Account, String> curCodeLens = Lenses.combine(curLens, codeLens);
 * }</pre>
 *
 * <p>If we want read currency code from account instance and write currency code to account instance,
 * we can combine read lens with read-write lens to create bigger read-write lens.
 * <pre>{@code
 * ReadLens<Account, Currency> curLens = Lenses.readLens(Account::getCurrency);
 * ReadWriteLens<Currency, String> codeLens =
 *   Lenses.readWriteLens(Currency::getCode, Currency::setCode);
 * ReadWriteLens<Account, String> curCodeLens = Lenses.combine(curLens, codeLens);
 * }</pre>
 *
 * <p>Lenses can be combined in different ways. If we have two read lenses with the same source type,
 * we can combine them into bigger one with {@link #both(ReadLens, ReadLens, BiFunction)} combinator.
 * Result lens can extract both values from source instance, but only if
 * both sub-lenses return non-null results.
 * For example,
 * <pre>{@code
 * ReadLens<E, A> lens1 = ...;
 * ReadLens<E, B> lens2 = ...;
 * ReadLens<E, Pair<A, B>> combinedLens = Lenses.both(lens1, lens2, Pair::of);
 * }</pre>
 * Result lens can extract a pair of {@code A} and {@code B}, but only if both values are not null.
 *
 * <p>Read-write lenses can be combined with {@link #both(ReadWriteLens, ReadWriteLens, BiFunction, Function, Function)}
 * combinator too, but with combiner function we have to supply extractor functions.
 * Example above with read-write lenses can be rewritten in such way:
 * <pre>{@code
 * ReadWriteLens<E, A> lens1 = ...;
 * ReadWriteLens<E, B> lens2 = ...;
 * ReadWriteLens<E, Pair<A, B>> combinedLens =
 *   Lenses.both(lens1, lens2, Pair::of, Pair::getLeft, Pair::getRight);
 * }</pre>
 * Such combined lens can get a pair of values {@code A} and {@code B} as read analog.
 * Additionally, it can set a pair of values {@code A} and {@code B} to instance of type {@code E}.
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
