package dev.khbd.lens4j.processor.meta;

import dev.khbd.lens4j.core.annotations.LensType;

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
public class LensMeta {

    private final String name;
    private final LensType type;
    private final LinkedList<LensPartMeta> parts = new LinkedList<>();
    private final Set<Modifier> modifiers;

    public LensMeta(String name, LensType type, Set<Modifier> modifiers) {
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
    }

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
     * Is single part or not.
     *
     * @return {@code true} if single part {@code false} otherwise
     */
    public boolean isSinglePart() {
        return parts.size() == 1;
    }

    public String getName() {
        return name;
    }

    public LensType getType() {
        return type;
    }

    public List<LensPartMeta> getParts() {
        return parts;
    }

    public Set<Modifier> getModifiers() {
        return modifiers;
    }
}
