package dev.khbd.lens4j.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Sergei_Khadanovich
 */
public class LexicalScope {

    private final Map<String, Integer> indexMap = new HashMap<>();

    public LexicalScope(String... names) {
        for (String name : names) {
            add(name);
        }
    }

    /**
     * Add variable to scope.
     *
     * @param name variable name
     * @return adjust variable name
     */
    public String add(String name) {
        Integer index = indexMap.get(name);
        if (Objects.nonNull(index)) {
            indexMap.put(name, index + 1);
            return name + (index + 1);
        } else {
            indexMap.put(name, 0);
            return name;
        }
    }

}
