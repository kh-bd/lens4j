package com.github.lens.core;

/**
 * Combined read-write lens implementation.
 *
 * @param <O>  object type
 * @param <P1> first property type
 * @param <P2> second property type
 */
class CombinedReadWriteLensImpl<O, P1, P2> implements ReadWriteLens<O, P2> {

    private final ReadLens<? super O, ? extends P1> base;
    private final ReadWriteLens<? super P1, P2> next;

    CombinedReadWriteLensImpl(ReadLens<? super O, ? extends P1> base,
                              ReadWriteLens<? super P1, P2> next) {
        this.base = base;
        this.next = next;
    }

    @Override
    public P2 get(O object) {
        P1 p1 = base.get(object);
        return next.get(p1);
    }

    @Override
    public void set(O object, P2 property) {
        P1 p1 = base.get(object);
        next.set(p1, property);
    }
}
