package com.github.lens.core;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Accessors based lens implementations.
 *
 * @param <O> object type
 * @param <P> property type
 * @author Sergei_Khadanovich
 */
class AccessorsLens<O, P> implements Lens<O, P> {

    private final Function<? super O, ? extends P> getter;
    private final BiConsumer<? super O, ? super P> setter;

    AccessorsLens(Function<? super O, ? extends P> getter,
                  BiConsumer<? super O, ? super P> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public P get(O object) {
        if (Objects.isNull(object)) {
            return null;
        }
        return getter.apply(object);
    }

    @Override
    public void set(O object, P property) {
        if (Objects.nonNull(object)) {
            setter.accept(object, property);
        }
    }
}
