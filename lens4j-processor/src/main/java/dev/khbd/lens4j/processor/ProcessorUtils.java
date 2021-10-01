package dev.khbd.lens4j.processor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergei_Khadanovich
 */
public final class ProcessorUtils {

    private ProcessorUtils() {
        throw new UnsupportedOperationException("Can not create instance of utility class");
    }

    /**
     * Get top level class for specified class.
     *
     * <p>if specified class is top level, original class will be return.
     *
     * @param classElement class element
     * @return top level class for specified one
     */
    public static TypeElement getTopLevelClass(TypeElement classElement) {
        List<TypeElement> classes = getAllEnclosingClasses(classElement);
        return classes.get(0);
    }

    /**
     * Get all classes up to top level.
     *
     * <p>For example, suppose we have several classes
     * <pre>{@code
     *  class Outer {
     *      class Inner1 {
     *          class Inner2 {}
     *      }
     *  }
     * }</pre>
     * {@code getAllEnclosingClasses(Inner2) == [Outer, Inner1, Inner2] }
     *
     * @param classElement class to start
     * @return all classes up to top level
     */
    public static List<TypeElement> getAllEnclosingClasses(TypeElement classElement) {
        List<TypeElement> classes = new ArrayList<>();
        classes.add(classElement);

        TypeElement current = classElement;

        while (current.getEnclosingElement().getKind() != ElementKind.PACKAGE) {
            current = (TypeElement) current.getEnclosingElement();
            classes.add(current);
        }

        Collections.reverse(classes);

        return classes;
    }

    /**
     * Find non-static field by name in specified class or any super classes.
     *
     * @param classElement class to start search
     * @param fieldName    field name
     * @return found field or empty
     */
    public static Optional<Element> findNonStaticFieldByName(TypeElement classElement,
                                                             String fieldName) {
        Optional<Element> fieldOpt =
                classElement.getEnclosedElements().stream()
                        .filter(e -> e.getKind() == ElementKind.FIELD)
                        .filter(e -> !e.getModifiers().contains(Modifier.STATIC))
                        .filter(e -> e.getSimpleName().toString().equals(fieldName))
                        .map(Element.class::cast)
                        .findFirst();
        return fieldOpt.or(() -> {
            TypeMirror superType = classElement.getSuperclass();
            if (superType.getKind() == TypeKind.NONE) {
                return Optional.empty();
            }
            DeclaredType declaredType = (DeclaredType) superType;
            return findNonStaticFieldByName((TypeElement) declaredType.asElement(), fieldName);
        });
    }
}
