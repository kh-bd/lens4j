package dev.khbd.lens4j.common;

/**
 * Base interface for all path parts.
 *
 * @author Sergei_Khadanovich
 */
public interface PathPart {

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
     * Compare kinds of current part and supplied one.
     *
     * @param other part to compare
     * @return {@literal true} if both parts have the same kind and {@literal false} otherwise
     */
    default boolean hasTheSameKindWith(PathPart other) {
        return getKind() == other.getKind();
    }

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
}
