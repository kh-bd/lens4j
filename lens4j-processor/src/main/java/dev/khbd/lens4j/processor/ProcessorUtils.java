package dev.khbd.lens4j.processor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public static Element getTopLevelClass(Element classElement) {
        List<Element> classes = getAllClasses(classElement);
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
     * {@code getAllClasses(Inner2) == [Outer, Inner1, Inner2] }
     *
     * @param classElement class to start
     * @return all classes up to top level
     */
    public static List<Element> getAllClasses(Element classElement) {
        List<Element> classes = new ArrayList<>();
        classes.add(classElement);

        Element current = classElement;

        while (current.getEnclosingElement().getKind() != ElementKind.PACKAGE) {
            current = current.getEnclosingElement();
            classes.add(current);
        }

        Collections.reverse(classes);

        return classes;
    }
}
