package dev.khbd.lens4j.processor;

import javax.lang.model.type.TypeMirror;

/**
 * @author Alexey_Bodyak
 */
public class LensPartMeta {

    private final TypeMirror sourceType;
    private final TypeMirror propertyType;
    private final String propertyName;


    public LensPartMeta(TypeMirror sourceType, TypeMirror propertyType, String propertyName) {
        this.sourceType = sourceType;
        this.propertyType = propertyType;
        this.propertyName = propertyName;
    }

    /**
     * Get source type mirror.
     *
     * @return source type mirror
     */
    public TypeMirror getSourceType() {
        return sourceType;
    }

    /**
     * Get property type mirror.
     *
     * @return property type mirror
     */
    public TypeMirror getPropertyType() {
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
