package dev.khbd.lens4j.processor.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Factory metadata.
 *
 * @author Alexey_Bodyak
 */
@Getter
@RequiredArgsConstructor
public class FactoryMeta {

    private final FactoryId id;
    private final Set<Modifier> modifiers;
    private final List<LensMeta> lenses = new ArrayList<>();

    /**
     * Add lens meta to factory.
     *
     * @param lensMeta lens meta
     */
    public void addLens(LensMeta lensMeta) {
        this.lenses.add(lensMeta);
    }

    /**
     * Two factories can be merged into single one
     * if they point to the same file with same modifiers.
     */
    public boolean canBeMergedWith(FactoryMeta other) {
        return id.equals(other.id) && modifiers.equals(other.modifiers);
    }

    /**
     * Merge to factories into single one.
     */
    public FactoryMeta merge(FactoryMeta other) {
        FactoryMeta result = new FactoryMeta(id, modifiers);
        result.lenses.addAll(lenses);
        result.lenses.addAll(other.lenses);
        return result;
    }
}
