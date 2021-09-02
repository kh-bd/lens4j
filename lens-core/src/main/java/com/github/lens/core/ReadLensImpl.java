package com.github.lens.core;

import java.util.Objects;
import java.util.function.Function;

/**
 * Simple accessor based read lens implementation.
 *
 * @param <O> object type
 * @param <P> property type
 */
class ReadLensImpl<O, P> implements ReadLens<O, P> {

    private final Function<? super O, ? extends P> getter;

    ReadLensImpl(Function<? super O, ? extends P> getter) {
        this.getter = getter;
    }

    @Override
    public P get(O object) {
        if (Objects.isNull(object)) {
            return null;
        }
        return getter.apply(object);
    }
}
