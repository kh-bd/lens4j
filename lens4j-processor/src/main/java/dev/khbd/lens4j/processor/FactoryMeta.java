package dev.khbd.lens4j.processor;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Factory metadata.
 *
 * @author Alexey_Bodyak
 */
public class FactoryMeta {

    private final String packageName;
    private final String factoryName;
    private final Set<Modifier> factoryModifiers;
    private final List<LensMeta> lenses = new ArrayList<>();

    public FactoryMeta(String packageName, String factoryName,
                       Set<Modifier> factoryModifiers) {
        this.packageName = packageName;
        this.factoryName = factoryName;
        this.factoryModifiers = factoryModifiers;
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
     * Get factory modifiers.
     *
     * @return factory modifiers
     */
    public Set<Modifier> getFactoryModifiers() {
        return factoryModifiers;
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
