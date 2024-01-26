package dev.khbd.lens4j.processor.meta;

import lombok.Builder;
import lombok.Value;

/**
 * @author Alexey_Bodyak
 */
@Value
@Builder
public class LensPartMeta {

    ResolvedParametrizedTypeMirror sourceType;
    ResolvedParametrizedTypeMirror targetType;
    String name;

    @Builder.Default
    Shape shape = Shape.ACCESSORS;

    /**
     * Lens part shape.
     *
     * <p>Shape means which way will be used to generated lens parts.
     */
    public enum Shape {

        /**
         * Accessors-based lambda will be used.
         *
         * <p>For example, if we have lens part for type {@code O} and {@code name == "property"},
         * accessors-based read lens will have such structure: <br>
         * {@code Lenses.readLens(O::getProperty)}
         */
        ACCESSORS,

        /**
         * Method-based lambda will be used.
         *
         * <p>For example, if we have lens part for type {@code O} and {@code name == "method"},
         * method-based read lens will have such structure: <br>
         * {@code Lenses.readLens(O::method)}
         */
        METHOD,

        /**
         * Field-based lambda will be used.
         *
         * <p>For example, if we have lens part for type {@code O} and {@code name = "field"},
         * field-based read lens will have such structure: <br>
         * {@code Lenses.readLens((O o) -> o.field)}
         */
        FIELD
    }
}
