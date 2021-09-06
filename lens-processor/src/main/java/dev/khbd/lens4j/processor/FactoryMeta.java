package dev.khbd.lens4j.processor;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory metadata.
 *
 * @author Alexey_Bodyak
 */
public class FactoryMeta {

    private final String packageName;
    private final String factoryName;
    private final List<LensMeta> lenses = new ArrayList<>();

    public FactoryMeta(String packageName, String factoryName) {
        this.packageName = packageName;
        this.factoryName = factoryName;
    }

    /**
     * Add lens meta to factory.
     *
     * @param lensMeta lens meta
     */
    public void addLens(LensMeta lensMeta) {
        this.lenses.add(lensMeta);
    }

    /**
     * Get package name.
     *
     * @return package name
     */
    public String getPackageName() {
        return packageName;
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
     * Get all lenses in factory.
     *
     * @return lenses
     */
    public List<LensMeta> getLenses() {
        return lenses;
    }
}
