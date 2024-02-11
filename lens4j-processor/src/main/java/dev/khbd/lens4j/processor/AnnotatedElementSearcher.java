package dev.khbd.lens4j.processor;

import dev.khbd.lens4j.core.annotations.GenLenses;
import lombok.RequiredArgsConstructor;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
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
class AnnotatedElementSearcher {

    private final Types typeUtils;

    /**
     * Search gen lenses in round environment.
     */
    Set<AnnotatedElement> search(RoundEnvironment roundEnv) {
        return Stream.concat(
                getFromLenses(roundEnv),
                getFromMultiLenses(roundEnv)
        ).collect(Collectors.toSet());
    }

    private Stream<AnnotatedElement> getFromLenses(RoundEnvironment roundEnv) {
        return roundEnv.getElementsAnnotatedWith(GenLenses.class)
                .stream()
                .map(annotated -> {
                    GenLenses annotation = annotated.getAnnotation(GenLenses.class);
                    return new AnnotatedElement(annotated, getRootElement(annotated, annotation), annotation);
                });
    }

    private Stream<AnnotatedElement> getFromMultiLenses(RoundEnvironment roundEnv) {
        return roundEnv.getElementsAnnotatedWith(GenLenses.GenLensesMulti.class)
                .stream()
                .flatMap(annotated -> {
                    GenLenses.GenLensesMulti multi = annotated.getAnnotation(GenLenses.GenLensesMulti.class);
                    return Arrays.stream(multi.value())
                            .map(annotation -> new AnnotatedElement(annotated, getRootElement(annotated, annotation), annotation));
                });
    }

    private TypeElement getRootElement(Element annotated, GenLenses annotation) {
        TypeMirror mirror = rootTypeMirror(annotation);

        if (mirror.getKind() != TypeKind.VOID) {
            // correct cast because root can be class only
            return (TypeElement) typeUtils.asElement(mirror);
        }

        if (annotated.getKind() == ElementKind.PACKAGE) {
            throw new LensProcessingException(MessageFactory.explicitRootIsRequired(annotated));
        }

        // root is void and type is annotated, so annotated element is root
        if (correctRootElementType(annotated)) {
            return (TypeElement) annotated;
        }

        throw new LensProcessingException(MessageFactory.incorrectRootType(annotated));
    }

    private TypeMirror rootTypeMirror(GenLenses annotation) {
        try {
            Class<?> root = annotation.root();
            throw new IllegalStateException("Cannot resolve type mirror by class " + root);
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
    }

    private static boolean correctRootElementType(Element root) {
        return switch (root.getKind()) {
            case ElementKind.RECORD, ElementKind.CLASS, ElementKind.INTERFACE, ElementKind.ENUM -> true;
            default -> false;
        };
    }
}
