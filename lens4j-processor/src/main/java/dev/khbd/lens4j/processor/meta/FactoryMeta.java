package dev.khbd.lens4j.processor.meta;

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
    private final Set<Modifier> modifiers;
    private final List<LensMeta> lenses = new ArrayList<>();

    public FactoryMeta(String packageName,
                       String factoryName,
                       Set<Modifier> modifiers) {
        this.packageName = packageName;
        this.factoryName = factoryName;
        this.modifiers = modifiers;
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
    public Set<Modifier> getModifiers() {
        return modifiers;
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
