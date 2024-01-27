package dev.khbd.lens4j.processor.meta;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Set;

/**
 * Factory metadata.
 *
 * @author Alexey_Bodyak
 */
@Data
@Builder
public class FactoryMeta {

    private final FactoryId id;
    @Builder.Default
    private final Set<Modifier> modifiers = Set.of();
    @Singular("lens")
    private final List<LensMeta> lenses;

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
        return FactoryMeta.builder()
                .id(id)
                .modifiers(modifiers)
                .lenses(lenses)
                .lenses(other.lenses)
                .build();
    }
}
