package dev.khbd.lens4j.processor.util;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Sergei_Khadanovich
 */
public class StdUtils {

    /**
     * Recover optional value with specified supplier.
     *
     * <p>This is replacement for standard `or` method from newer java versions.
     *
     * @param opt      optional value
     * @param defaultF fallback optional value
     * @param <V>      value type
     */
    public static <V> Optional<V> recover(Optional<V> opt, Supplier<Optional<V>> defaultF) {
        if (opt.isPresent()) {
            return opt;
        }
        return defaultF.get();
    }
}
