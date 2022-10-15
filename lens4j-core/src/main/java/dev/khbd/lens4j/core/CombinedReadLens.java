package dev.khbd.lens4j.core;

/**
 * Combined read lens implementation.
 *
 * @param <O>  object type
 * @param <P1> first property type
 * @param <P2> second property type
 */
class CombinedReadLens<O, P1, P2> implements ReadLens<O, P2> {

    private final ReadLens<? super O, ? extends P1> base;
    private final ReadLens<? super P1, ? extends P2> next;

    CombinedReadLens(ReadLens<? super O, ? extends P1> base,
                     ReadLens<? super P1, ? extends P2> next) {
        this.base = base;
        this.next = next;
    }

    @Override
    public P2 get(O object) {
        P1 p1 = base.get(object);
        return next.get(p1);
    }
}
