package com.github.lens.core;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Simple accessor-based read-write lens implementation.
 *
 * @param <O> object type
 * @param <P> property type
 */
class ReadWriteLensImpl<O, P> extends ReadLensImpl<O, P> implements ReadWriteLens<O, P> {

    private final BiConsumer<? super O, ? super P> setter;

    ReadWriteLensImpl(Function<? super O, ? extends P> getter,
                      BiConsumer<? super O, ? super P> setter) {
        super(getter);
        this.setter = setter;
    }

    @Override
    public void set(O object, P property) {
        if (Objects.nonNull(object)) {
            setter.accept(object, property);
        }
    }
}
