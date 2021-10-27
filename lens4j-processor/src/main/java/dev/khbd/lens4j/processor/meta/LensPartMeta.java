package dev.khbd.lens4j.processor.meta;

/**
 * @author Alexey_Bodyak
 */
public class LensPartMeta {

    private final ResolvedParametrizedTypeMirror sourceType;
    private final ResolvedParametrizedTypeMirror propertyType;
    private final String propertyName;

    public LensPartMeta(ResolvedParametrizedTypeMirror sourceType,
                        ResolvedParametrizedTypeMirror propertyType,
                        String propertyName) {
        this.sourceType = sourceType;
        this.propertyType = propertyType;
        this.propertyName = propertyName;
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
    public ResolvedParametrizedTypeMirror getPropertyType() {
        return propertyType;
    }

    /**
     * Get property name.
     *
     * @return property name
     */
    public String getPropertyName() {
        return propertyName;
    }
}
