package dev.khbd.lens4j.processor.meta;

import dev.khbd.lens4j.core.annotations.LensType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Information for generating specific lens.
 *
 * @author Alexey_Bodyak
 */
@Getter
@RequiredArgsConstructor
public class LensMeta {

    private final String name;
    private final LensType type;
    private final Set<Modifier> modifiers;
    private final LinkedList<LensPartMeta> parts = new LinkedList<>();

    /**
     * Add lens part to the end.
     *
     * @param part lens part
     */
    public void addPart(LensPartMeta part) {
        parts.add(part);
    }

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
