package dev.khbd.lens4j.processor.path;

/**
 * Path visitor.
 *
 * @author Sergei_Khadanovich
 */
public interface PathVisitor {

    default void start() {
    }

    default void visitPoint(Point point) {
    }

    default void visitProperty(Property property) {
    }

    default void visitMethod(Method method) {
    }

    default void finish() {
    }
}
