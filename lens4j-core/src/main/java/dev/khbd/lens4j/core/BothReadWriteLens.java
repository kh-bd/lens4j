package dev.khbd.lens4j.core;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 'Both' read-write lens.
 *
 * <p>This lens produces result if both sub lenses produce non-null results.
 *
 * @param <O>  source object type
 * @param <P1> first property type
 * @param <P2> second property type
 * @param <R>  result type
 * @author Sergei_Khadanovich
 */
class BothReadWriteLens<O, P1, P2, R> implements ReadWriteLens<O, R> {

    private final ReadWriteLens<? super O, P1> lens1;
    private final ReadWriteLens<? super O, P2> lens2;
    private final BiFunction<? super P1, ? super P2, ? extends R> combineF;
    private final Function<? super R, ? extends P1> extractorF1;
    private final Function<? super R, ? extends P2> extractorF2;

    BothReadWriteLens(ReadWriteLens<? super O, P1> lens1,
                      ReadWriteLens<? super O, P2> lens2,
                      BiFunction<? super P1, ? super P2, ? extends R> combineF,
                      Function<? super R, ? extends P1> extractorF1,
                      Function<? super R, ? extends P2> extractorF2) {
        this.lens1 = lens1;
        this.lens2 = lens2;
        this.combineF = combineF;
        this.extractorF1 = extractorF1;
        this.extractorF2 = extractorF2;
    }

    @Override
    public R get(O object) {
        P1 p1 = lens1.get(object);
        P2 p2 = lens2.get(object);
        if (Objects.nonNull(p1) && Objects.nonNull(p2)) {
            return combineF.apply(p1, p2);
        }
        return null;
    }

    @Override
    public void set(O object, R property) {
        if (Objects.nonNull(property)) {
            lens1.set(object, extractorF1.apply(property));
            lens2.set(object, extractorF2.apply(property));
        }
    }
}
