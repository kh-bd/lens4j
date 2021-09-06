package dev.khbd.lens4j.core;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * 'Both' read lens.
 *
 * <p>This lens produces result if both sub lenses produce non-null results.
 *
 * @param <O>  source object type
 * @param <P1> first property type
 * @param <P2> second property type
 * @param <R>  result type
 * @author Sergei_Khadanovich
 */
class BothReadLensImpl<O, P1, P2, R> implements ReadLens<O, R> {

    private final ReadLens<? super O, ? extends P1> lens1;
    private final ReadLens<? super O, ? extends P2> lens2;
    private final BiFunction<? super P1, ? super P2, ? extends R> combineF;

    BothReadLensImpl(ReadLens<? super O, ? extends P1> lens1,
                     ReadLens<? super O, ? extends P2> lens2,
                     BiFunction<? super P1, ? super P2, ? extends R> combineF) {
        this.lens1 = lens1;
        this.lens2 = lens2;
        this.combineF = combineF;
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
}
