package dev.khbd.lens4j.processor.path;

/**
 * Base interface for all path parts.
 *
 * @author Sergei_Khadanovich
 */
public sealed interface PathPart permits Method, Point, Property {

    /**
     * Visit part with specified visitor.
     *
     * @param visitor visitor
     */
    void visit(PathVisitor visitor);

    /**
     * Get part kind.
     *
     * @return part kind
     */
    PathPartKind getKind();

    /**
     * Check is part point or not.
     *
     * @return {@literal true} part is point and {@literal false} otherwise
     */
    default boolean isPoint() {
        return getKind() == PathPartKind.POINT;
    }

    /**
     * Check is part property or not.
     *
     * @return {@literal true} part is property and {@literal false} otherwise
     */
    default boolean isProperty() {
        return getKind() == PathPartKind.PROPERTY;
    }

    /**
     * Check is part method or not.
     *
     * @return {@literal true} part is method and {@literal false} otherwise
     */
    default boolean isMethod() {
        return getKind() == PathPartKind.METHOD;
    }
}
