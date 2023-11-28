package dev.khbd.lens4j.processor.path;

/**
 * Path visitor.
 *
 * @author Sergei_Khadanovich
 */
public interface PathVisitor {

    /**
     * Visit begin of the path.
     */
    default void start() {
    }

    /**
     * Visit point part.
     *
     * @param point point part
     */
    default void visitPoint(Point point) {
    }

    /**
     * Visit property part.
     *
     * @param property property part
     */
    default void visitProperty(Property property) {
    }

    /**
     * Visit method part.
     *
     * @param method method part
     */
    default void visitMethod(Method method) {
    }

    /**
     * Visit end of the path.
     */
    default void finish() {
    }
}
