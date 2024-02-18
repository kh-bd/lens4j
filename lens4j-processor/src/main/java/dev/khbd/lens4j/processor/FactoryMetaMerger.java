package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.processor.meta.FactoryId;
import dev.khbd.lens4j.processor.meta.FactoryMeta;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Factory meta merger.
 *
 * @author Sergei Khadanovich
 */
class FactoryMetaMerger {

    /**
     * Group factories and merge.
     */
    List<FactoryMeta> merge(List<FactoryMeta> factories) {
        return groupFactories(factories).values()
                .stream()
                .map(this::mergeFactories)
                .collect(Collectors.toList());
    }

    private Map<FactoryId, List<FactoryMeta>> groupFactories(List<FactoryMeta> factories) {
        return factories.stream().collect(Collectors.groupingBy(FactoryMeta::getId));
    }

    private FactoryMeta mergeFactories(List<FactoryMeta> factories) {
        if (factories.size() == 1) {
            return factories.get(0);
        }
        FactoryMeta merged = factories.get(0);
        for (int i = 1; i < factories.size(); i++) {
            FactoryMeta current = factories.get(i);
            if (!merged.canBeMergedWith(current)) {
                throw new LensProcessingException(MessageFactory.factoriesCannotBeMerged(current.getId()));
            }
            merged = merged.merge(current);
        }
        return merged;
    }
}
