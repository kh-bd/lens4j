package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.core.annotations.GenLenses;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper class to search all {@link GenLenses} in round environment.
 *
 * @author Sergei Khadanovich
 */
class ElementAndGenLensesSearcher {

    /**
     * Search gen lenses in round environment.
     */
    Set<ElementAndGenLenses> search(RoundEnvironment roundEnv) {
        Set<ElementAndGenLenses> elements = Stream.concat(getFromLenses(roundEnv), getFromMultiLenses(roundEnv))
                .collect(Collectors.toSet());

        for (ElementAndGenLenses elementAndGen: elements) {
            Element element = elementAndGen.element();
            if (!(element.getKind() == ElementKind.CLASS || element.getKind() == ElementKind.RECORD)) {
                throw new LensProcessingException(MessageFactory.genLensNotAllowedHere(element));
            }
        }

        return elements;
    }

    private Stream<ElementAndGenLenses> getFromLenses(RoundEnvironment roundEnv) {
        return roundEnv.getElementsAnnotatedWith(GenLenses.class)
                .stream()
                .map(element -> new ElementAndGenLenses(element, element.getAnnotation(GenLenses.class)));
    }

    private Stream<ElementAndGenLenses> getFromMultiLenses(RoundEnvironment roundEnv) {
        return roundEnv.getElementsAnnotatedWith(GenLenses.GenLensesMulti.class)
                .stream()
                .flatMap(element -> {
                    GenLenses.GenLensesMulti multi = element.getAnnotation(GenLenses.GenLensesMulti.class);
                    return Arrays.stream(multi.value())
                            .map(annotation -> new ElementAndGenLenses(element, annotation));
                });
    }
}
