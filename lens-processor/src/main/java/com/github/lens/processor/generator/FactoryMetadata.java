package com.github.lens.processor.generator;

import javax.lang.model.type.TypeMirror;
import java.util.List;

/**
 * Factory metadata.
 *
 * @author Alexey_Bodyak
 */
public class FactoryMetadata {
    private final String factoryName;
    private final String factoryPackage;
    private final TypeMirror rootType;
    private final List<LensMetadata> lensMetadata;

    private FactoryMetadata(String factoryName, String factoryPackage,
                            TypeMirror rootType, List<LensMetadata> lensMetadata) {
        this.factoryName = factoryName;
        this.factoryPackage = factoryPackage;
        this.rootType = rootType;
        this.lensMetadata = lensMetadata;
    }

    /**
     * Create factory metadata.
     *
     * @param factoryName    factory name
     * @param factoryPackage factory package
     * @param rootType       root type
     * @param lensMetadata   lens metadata
     * @return factory metadata
     */
    public static FactoryMetadata of(String factoryName, String factoryPackage,
                                     TypeMirror rootType, List<LensMetadata> lensMetadata) {
        return new FactoryMetadata(factoryName, factoryPackage, rootType, lensMetadata);
    }

    /**
     * Get factory name.
     *
     * @return factory name
     */
    public String getFactoryName() {
        return factoryName;
    }

    /**
     * Get factory package.
     *
     * @return factory package
     */
    public String getFactoryPackage() {
        return factoryPackage;
    }

    /**
     * Get root type.
     *
     * @return root type
     */
    public TypeMirror getRootType() {
        return rootType;
    }

    /**
     * Get lens metadata.
     *
     * @return lens metadata
     */
    public List<LensMetadata> getLensMetadata() {
        return lensMetadata;
    }
}
