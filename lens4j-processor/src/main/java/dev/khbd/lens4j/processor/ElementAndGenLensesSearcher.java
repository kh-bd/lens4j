package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.core.annotations.GenLenses;
import lombok.RequiredArgsConstructor;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper class to search all {@link GenLenses} in round environment.
 *
 * @author Sergei Khadanovich
 */
@RequiredArgsConstructor
class ElementAndGenLensesSearcher {

    private final Types typeUtils;

    /**
     * Search gen lenses in round environment.
     */
    Set<ElementAndGenLenses> search(RoundEnvironment roundEnv) {
        Set<ElementAndGenLenses> elements = Stream.concat(getFromLenses(roundEnv), getFromMultiLenses(roundEnv))
                .collect(Collectors.toSet());

        for (ElementAndGenLenses elementAndGen : elements) {
            Element root = elementAndGen.getRoot();
            if (!correctRootElementType(root)) {
                throw new LensProcessingException(MessageFactory.incorrectRootType(root));
            }
        }

        return elements;
    }

    private Stream<ElementAndGenLenses> getFromLenses(RoundEnvironment roundEnv) {
        return roundEnv.getElementsAnnotatedWith(GenLenses.class)
                .stream()
                .map(onElement -> {
                    GenLenses annotation = onElement.getAnnotation(GenLenses.class);
                    return new ElementAndGenLenses(getRootElement(onElement, annotation), annotation);
                });
    }

    private Stream<ElementAndGenLenses> getFromMultiLenses(RoundEnvironment roundEnv) {
        return roundEnv.getElementsAnnotatedWith(GenLenses.GenLensesMulti.class)
                .stream()
                .flatMap(onElement -> {
                    GenLenses.GenLensesMulti multi = onElement.getAnnotation(GenLenses.GenLensesMulti.class);
                    return Arrays.stream(multi.value())
                            .map(annotation -> new ElementAndGenLenses(getRootElement(onElement, annotation), annotation));
                });
    }

    private Element getRootElement(Element onElement, GenLenses annotation) throws MirroredTypeException {
        TypeMirror mirror = rootTypeMirror(annotation);
        if (mirror.getKind() == TypeKind.VOID) {
            return onElement;
        }
        return typeUtils.asElement(mirror);
    }

    private TypeMirror rootTypeMirror(GenLenses annotation) {
        try {
            Class<?> root = annotation.root();
            throw new IllegalStateException("Cannot resolve type mirror by class " + root);
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
    }

    private static boolean correctRootElementType(Element element) {
        switch (element.getKind()) {
            case CLASS:
            case ENUM:
            case INTERFACE:
                return true;
            default:
                return false;
        }
    }
}
