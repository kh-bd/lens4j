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

    private final String lensName;
    private final LensType lensType;
    private final LinkedList<LensPartMeta> lensParts = new LinkedList<>();
    private final Set<Modifier> modifiers;

    public LensMeta(String lensName, LensType lensType, Set<Modifier> modifiers) {
        this.lensName = lensName;
        this.lensType = lensType;
        this.modifiers = modifiers;
    }

    /**
     * Add lens part to the end.
     *
     * @param part lens part
     */
    public void addLensPart(LensPartMeta part) {
        lensParts.add(part);
    }

    /**
     * Get last part.
     *
     * @return last part in lens chain
     */
    public LensPartMeta getLastLensPart() {
        return lensParts.getLast();
    }

    /**
     * Get first part.
     *
     * @return first part in lens chain
     */
    public LensPartMeta getFirstLensPart() {
        return lensParts.getFirst();
    }

    /**
     * Get lens parts without first and last element.
     *
     * @return lens parts
     */
    public List<LensPartMeta> getLensPartsWithoutEnds() {
        return lensParts.stream()
                .skip(1)
                .limit(lensParts.size() - 2)
                .collect(Collectors.toList());
    }

    /**
     * Is single part or not.
     *
     * @return {@code true} if single part {@code false} otherwise
     */
    public boolean isSinglePart() {
        return lensParts.size() == 1;
    }

    public String getLensName() {
        return lensName;
    }

    public LensType getLensType() {
        return lensType;
    }

    public List<LensPartMeta> getLensParts() {
        return lensParts;
    }

    public Set<Modifier> getModifiers() {
        return modifiers;
    }
}
