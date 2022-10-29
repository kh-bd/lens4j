package dev.khbd.lens4j.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Sergei_Khadanovich
 */
public class Options {

    private static final OptionsKey<Boolean> GENERATE_INLINED_LENSES = new OptionsKey<>("lenses.generate.inlined");

    private static final List<OptionsDescription<?>> DESCRIPTIONS = List.of(
            new OptionsDescription<>(GENERATE_INLINED_LENSES, Boolean::parseBoolean, () -> false)
    );

    private final Map<OptionsKey<?>, Object> params = new HashMap<>();

    public Options(Map<String, String> args) {
        for (String key : args.keySet()) {
            OptionsKey<?> optionsKey = new OptionsKey<>(key);
            OptionsDescription<?> description = findDescription(optionsKey);
            if (Objects.isNull(description)) {
                continue;
            }
            String value = args.get(key);
            if (Objects.isNull(value)) {
                params.put(optionsKey, description.defaultValue.get());
            } else {
                params.put(optionsKey, parseValueOrDefault(description, value));
            }
        }
        for (OptionsDescription<?> description : DESCRIPTIONS) {
            params.putIfAbsent(description.key(), description.defaultValue().get());
        }
    }

    /**
     * Is inlined generation enabled or not.
     *
     * @return {@literal true} if enabled
     */
    public boolean inlinedGenerationEnabled() {
        return getKeyValue(GENERATE_INLINED_LENSES);
    }

    /**
     * Get all supported keys.
     *
     * @return all supported keys
     */
    public static Set<String> getOptionsKeys() {
        return DESCRIPTIONS.stream()
                .map(descr -> descr.key().name())
                .collect(Collectors.toSet());
    }

    private static OptionsDescription<?> findDescription(OptionsKey<?> key) {
        return DESCRIPTIONS.stream()
                .filter(description -> description.key().equals(key))
                .findFirst()
                .orElse(null);
    }

    private static Object parseValueOrDefault(OptionsDescription<?> description, String value) {
        try {
            return description.parser().apply(value);
        } catch (Exception e) {
            return description.defaultValue().get();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getKeyValue(OptionsKey<T> key) {
        return (T) params.get(key);
    }

    private record OptionsKey<V>(String name) {
    }

    private record OptionsDescription<V>(
            OptionsKey<V> key,
            Function<String, ? extends V> parser,
            Supplier<? extends V> defaultValue) {
    }

}
