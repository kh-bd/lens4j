package dev.khbd.lens4j.processor.meta;

import dev.khbd.lens4j.core.annotations.LensType;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Information for generating specific lens.
 *
 * @author Alexey_Bodyak
 */
@Data
@Builder
public class LensMeta {

    private final String name;
    private final LensType type;
    @Builder.Default
    private final Set<Modifier> modifiers = new HashSet<>();
    @Singular("part")
    private final List<LensPartMeta> parts;

    /**
     * Get last part.
     *
     * @return last part in lens chain
     */
    public LensPartMeta getLastPart() {
        return parts.getLast();
    }

    /**
     * Get first part.
     *
     * @return first part in lens chain
     */
    public LensPartMeta getFirstPart() {
        return parts.getFirst();
    }

    /**
     * Get lens parts without first and last element.
     *
     * @return lens parts
     */
    public List<LensPartMeta> getPartsWithoutEnds() {
        return parts.stream()
                .skip(1)
                .limit(parts.size() - 2)
                .collect(Collectors.toList());
    }

    /**
     * Get lens parts without first element.
     *
     * @return lens parts
     */
    public List<LensPartMeta> getPartsWithoutLast() {
        return parts.stream()
                .limit(parts.size() - 1)
                .collect(Collectors.toList());
    }

    /**
     * Is single part or not.
     *
     * @return {@code true} if single part {@code false} otherwise
     */
    public boolean isSinglePart() {
        return parts.size() == 1;
    }
}
