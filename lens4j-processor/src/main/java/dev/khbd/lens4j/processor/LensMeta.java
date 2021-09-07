package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.core.annotations.LensType;

import java.util.LinkedList;
import java.util.List;
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

    public LensMeta(String lensName, LensType lensType) {
        this.lensName = lensName;
        this.lensType = lensType;
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
     * Get all lens parts.
     *
     * @return all lens parts
     */
    public List<LensPartMeta> getLensParts() {
        return lensParts;
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

    /**
     * Get lens name.
     *
     * @return lens name
     */
    public String getLensName() {
        return lensName;
    }

    /**
     * Get lens type.
     *
     * @return lens type
     */
    public LensType getLensType() {
        return lensType;
    }
}
