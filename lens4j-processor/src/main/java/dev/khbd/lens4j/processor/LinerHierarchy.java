package dev.khbd.lens4j.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Liner hierarchy.
 *
 * @param <E> element type
 * @author Sergei_Khadanovich
 */
public class LinerHierarchy<E> {

    private final List<E> elements;

    public LinerHierarchy(List<E> elements) {
        this.elements = new ArrayList<>(elements);
    }

    /**
     * Get highest element in hierarchy.
     *
     * @return highest element
     */
    public E getHighest() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Hierarchy is empty");
        }
        return elements.get(0);
    }

    /**
     * Get stream of all elements in hierarchy.
     *
     * @return stream of all elements
     */
    public Stream<E> stream() {
        return elements.stream();
    }
}
