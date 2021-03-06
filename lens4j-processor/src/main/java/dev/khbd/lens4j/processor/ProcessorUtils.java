package dev.khbd.lens4j.processor;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return getNestedHierarchy(classElement).getHighest();
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
     * {@code getNestedHierarchy(Inner2) == [Outer -> Inner1 -> Inner2] }
     *
     * @param classElement class to start
     * @return all classes up to top level
     */
    public static LinerHierarchy<TypeElement> getNestedHierarchy(TypeElement classElement) {
        List<TypeElement> classes = new ArrayList<>();
        classes.add(classElement);

        TypeElement current = classElement;

        while (current.getEnclosingElement().getKind() != ElementKind.PACKAGE) {
            current = (TypeElement) current.getEnclosingElement();
            classes.add(current);
        }

        Collections.reverse(classes);

        return new LinerHierarchy<>(classes);
    }

    /**
     * Find non-static field by name in specified class or any super classes.
     *
     * @param classElement class to start search
     * @param fieldName    field name
     * @return found field or empty
     */
    public static Optional<VariableElement> findNonStaticFieldByName(TypeElement classElement,
                                                                     String fieldName) {
        return findNonStaticFieldInClass(classElement, fieldName)
                .or(findNonStaticFieldInSuperClass(classElement, fieldName));
    }

    private static Optional<VariableElement> findNonStaticFieldInClass(TypeElement classElement, String fieldName) {
        return classElement.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.FIELD)
                .filter(e -> !e.getModifiers().contains(Modifier.STATIC))
                .filter(e -> e.getSimpleName().toString().equals(fieldName))
                .map(VariableElement.class::cast)
                .findFirst();
    }

    private static Supplier<Optional<VariableElement>> findNonStaticFieldInSuperClass(TypeElement classElement,
                                                                                      String fieldName) {
        return () -> {
            TypeMirror superType = classElement.getSuperclass();
            if (superType.getKind() == TypeKind.NONE) {
                return Optional.empty();
            }
            DeclaredType declaredType = (DeclaredType) superType;
            return findNonStaticFieldByName((TypeElement) declaredType.asElement(), fieldName);
        };
    }

    /**
     * Find non-static methods by name in specified class or any super classes.
     *
     * @param classElement class to start search
     * @param methodName   method name
     * @return all found methods with specified name
     */
    public static List<ExecutableElement> findNonStaticMethodsByName(TypeElement classElement,
                                                                     String methodName) {
        return getInheritanceHierarchy(classElement)
                .stream()
                .flatMap(ce -> findNonStaticMethodsByNameInClass(ce, methodName))
                .collect(Collectors.toList());
    }

    private static Stream<ExecutableElement> findNonStaticMethodsByNameInClass(TypeElement classElement,
                                                                               String methodName) {
        return classElement.getEnclosedElements().stream()
                .filter(e -> e.getKind().equals(ElementKind.METHOD))
                .filter(e -> !e.getModifiers().contains(Modifier.STATIC))
                .filter(e -> e.getSimpleName().toString().equals(methodName))
                .map(ExecutableElement.class::cast);
    }

    /**
     * Get all super classes up to {@code Object}.
     *
     * <p>For example, suppose we have several classes
     * <pre>{@code
     *  class Parent {
     *  }
     *  class Child extends Parent {
     *  }
     * }</pre>
     * {@code getInheritanceHierarchy(Child) == [Object -> Parent -> Child] }
     *
     * @param classElement class to start
     * @return all classes up to object
     */
    public static LinerHierarchy<TypeElement> getInheritanceHierarchy(TypeElement classElement) {
        List<TypeElement> classes = new ArrayList<>();
        classes.add(classElement);

        TypeElement current = classElement;

        while (current.getSuperclass().getKind() != TypeKind.NONE) {
            DeclaredType superType = (DeclaredType) current.getSuperclass();
            current = (TypeElement) superType.asElement();
            classes.add(current);
        }

        Collections.reverse(classes);

        return new LinerHierarchy<>(classes);
    }
}
