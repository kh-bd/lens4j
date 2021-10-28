package dev.khbd.lens4j.processor.meta;

/**
 * @author Alexey_Bodyak
 */
public class LensPartMeta {

    private final ResolvedParametrizedTypeMirror sourceType;
    private final ResolvedParametrizedTypeMirror targetType;
    private final String name;

    private Shape shape = Shape.ACCESSORS;

    public LensPartMeta(ResolvedParametrizedTypeMirror sourceType,
                        ResolvedParametrizedTypeMirror targetType,
                        String name) {
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.name = name;
    }

    /**
     * Get source type mirror.
     *
     * @return source type mirror
     */
    public ResolvedParametrizedTypeMirror getSourceType() {
        return sourceType;
    }

    /**
     * Get property type mirror.
     *
     * @return property type mirror
     */
    public ResolvedParametrizedTypeMirror getTargetType() {
        return targetType;
    }

    /**
     * Get property name.
     *
     * @return property name
     */
    public String getName() {
        return name;
    }

    /**
     * Get lens part shape.
     *
     * @return part shape
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Change lens part shape.
     *
     * @param shape new shape
     * @return self for chaining
     */
    public LensPartMeta withShape(Shape shape) {
        this.shape = shape;
        return this;
    }

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
