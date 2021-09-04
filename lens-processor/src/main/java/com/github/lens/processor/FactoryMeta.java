package com.github.lens.processor;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Meta to represent factory, which will be generated.
 *
 * @author Sergei_Khadanovich
 */
@Data
@Accessors(chain = true)
class FactoryMeta {
    String packageName;
    String factoryName;

    List<LensMeta> lenses = new ArrayList<>();

    FactoryMeta addLens(LensMeta lensMeta) {
        this.lenses.add(lensMeta);
        return this;
    }
}
