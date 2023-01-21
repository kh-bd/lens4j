package dev.khbd.lens4j.processor;

import java.util.HashMap;
import java.util.Map;
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

    /**
     * Create map from specified keys and values.
     *
     * @return map
     */
    public static Map<String, Object> map(String key1, Object value1,
                                          String key2, Object value2,
                                          String key3, Object value3) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(key1, value1);
        result.put(key2, value2);
        result.put(key3, value3);
        return result;
    }

    /**
     * Create map from specified keys and values.
     *
     * @return map
     */
    public static Map<String, Object> map(String key1, Object value1,
                                          String key2, Object value2,
                                          String key3, Object value3,
                                          String key4, Object value4) {
        HashMap<String, Object> result = new HashMap<>();
        result.put(key1, value1);
        result.put(key2, value2);
        result.put(key3, value3);
        result.put(key4, value4);
        return result;
    }
}
