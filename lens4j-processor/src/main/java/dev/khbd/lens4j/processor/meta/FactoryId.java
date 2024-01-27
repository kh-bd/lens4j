package dev.khbd.lens4j.processor.meta;

import lombok.Value;

/**
 * @author Sergei Khadanovich
 */
@Value
public class FactoryId {

    String packageName;
    String factoryName;

    /**
     * Compose fqn.
     */
    public String getFqn() {
        return packageName + "." + factoryName;
    }
}
