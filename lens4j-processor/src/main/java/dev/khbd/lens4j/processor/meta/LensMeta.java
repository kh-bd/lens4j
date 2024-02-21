package dev.khbd.lens4j.processor.meta;

import dev.khbd.lens4j.core.annotations.LensType;
import lombok.Value;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Information for generating specific lens.
 *
 * @author Alexey_Bodyak
 */
@Value
public class LensMeta {

    String name;
    LensType type;
    Set<Modifier> modifiers;
    List<LensPartMeta> parts;

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
     * Create new lens meta builder.
     *
     * @return new builder
     */
    public static LensMetaBuilder builder() {
        return new LensMetaBuilder();
    }

    /**
     * Lens meta builder class.
     */
    public static class LensMetaBuilder {

        private String name;
        private LensType type;
        private Set<Modifier> modifiers = new HashSet<>();
        private final List<LensPartMeta> parts = new ArrayList<>();

        public LensMetaBuilder name(String name) {
            this.name = name;
            return this;
        }

        public LensMetaBuilder type(LensType type) {
            this.type = type;
            return this;
        }

        public LensMetaBuilder modifiers(Set<Modifier> modifiers) {
            this.modifiers = new HashSet<>(modifiers);
            return this;
        }

        public LensMetaBuilder part(LensPartMeta part) {
            parts.add(part);
            return this;
        }

        /**
         * Build lens meta.
         */
        public LensMeta build() {
            return new LensMeta(name, type, modifiers, parts);
        }
    }
}
