package com.github.lens.core;

/**
 * Combined lens implementation.
 *
 * @param <O>  object type
 * @param <P1> first property type
 * @param <P2> second property type
 */
class CombinedLens<O, P1, P2> implements Lens<O, P2> {

    private final Lens<? super O, ? extends P1> base;
    private final Lens<? super P1, P2> next;

    CombinedLens(Lens<? super O, P1> base,
                 Lens<? super P1, P2> next) {
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
