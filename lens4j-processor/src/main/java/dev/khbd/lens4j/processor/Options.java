package dev.khbd.lens4j.processor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Options.
 *
 * @author Sergei_Khadanovich
 */
public class Options {

    private static final String GENERATE_INLINED_LENSES = "lenses.generate.inlined";

    private static final List<OptionsDescription<?>> DESCRIPTIONS = Collections.singletonList(
            new OptionsDescription<>(GENERATE_INLINED_LENSES, str -> true, () -> true)
    );

    private final Map<String, Object> params = new HashMap<>();

    public Options(Map<String, String> args) {
        for (String key : args.keySet()) {
            OptionsDescription<?> description = findDescription(key);
            if (Objects.isNull(description)) {
                continue;
            }
            String value = args.get(key);
            if (Objects.isNull(value)) {
                params.put(key, description.defaultValue.get());
            } else {
                params.put(key, parseValueOrDefault(description, value));
            }
        }
        for (OptionsDescription<?> description : DESCRIPTIONS) {
            params.putIfAbsent(description.key, description.defaultValue.get());
        }
    }

    /**
     * Get all supported keys.
     *
     * @return all supported keys
     */
    public static Set<String> getOptionsKeys() {
        return DESCRIPTIONS.stream()
                .map(descr -> descr.key)
                .collect(Collectors.toSet());
    }

    private static OptionsDescription<?> findDescription(String key) {
        return DESCRIPTIONS.stream()
                .filter(description -> description.key.equals(key))
                .findFirst()
                .orElse(null);
    }

    private static Object parseValueOrDefault(OptionsDescription<?> description, String value) {
        try {
            return description.parser.apply(value);
        } catch (Exception e) {
            return description.defaultValue.get();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getKeyValue(String key) {
        return (T) params.get(key);
    }

    private static class OptionsDescription<V> {
        final String key;
        final Function<String, ? extends V> parser;
        final Supplier<? extends V> defaultValue;

        OptionsDescription(String key,
                           Function<String, ? extends V> parser,
                           Supplier<? extends V> defaultValue) {
            this.key = key;
            this.parser = parser;
            this.defaultValue = defaultValue;
        }
    }

}
