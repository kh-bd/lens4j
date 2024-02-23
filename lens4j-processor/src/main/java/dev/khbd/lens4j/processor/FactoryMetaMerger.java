package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.processor.meta.FactoryId;
import dev.khbd.lens4j.processor.meta.FactoryMeta;
import dev.khbd.result4j.core.Either;

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
    Either<Message, List<FactoryMeta>> merge(List<FactoryMeta> factories) {
        List<Either<Message, FactoryMeta>> merged =
                groupFactories(factories).values()
                        .stream()
                        .map(this::mergeFactories)
                        .collect(Collectors.toList());
        return Either.sequence(merged);
    }

    private Map<FactoryId, List<FactoryMeta>> groupFactories(List<FactoryMeta> factories) {
        return factories.stream().collect(Collectors.groupingBy(FactoryMeta::getId));
    }

    private Either<Message, FactoryMeta> mergeFactories(List<FactoryMeta> factories) {
        if (factories.size() == 1) {
            return Either.right(factories.getFirst());
        }
        FactoryMeta merged = factories.getFirst();
        for (int i = 1; i < factories.size(); i++) {
            FactoryMeta current = factories.get(i);
            if (!merged.canBeMergedWith(current)) {
                return Either.left(MessageFactory.factoriesCannotBeMerged(current.getId()));
            }
            merged = merged.merge(current);
        }
        return Either.right(merged);
    }
}
