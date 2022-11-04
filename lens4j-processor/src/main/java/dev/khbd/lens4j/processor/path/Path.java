package dev.khbd.lens4j.processor.path;

import java.util.List;
import java.util.Objects;

/**
 * @author Sergei_Khadanovich
 */
public class Path {

    private final List<PathPart> parts;

    Path(List<PathPart> parts) {
        this.parts = parts;
    }

    public boolean isEmpty() {
        return parts.isEmpty();
    }

    /**
     * Path length.
     *
     * @return path elements count
     */
    public int length() {
        return parts.size();
    }

    /**
     * Visit path with specified visitor.
     *
     * @param visitor visitor.
     */
    public void visit(PathVisitor visitor) {
        visitor.start();
        for (PathPart part : parts) {
            part.visit(visitor);
        }
        visitor.finish();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path = (Path) o;
        return parts.equals(path.parts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parts);
    }

    @Override
    public String toString() {
        return "Path{" +
                "parts=" + parts +
                '}';
    }

    /**
     * Create empty path builder.
     *
     * @return builder
     */
    public static PathBuilder builder() {
        return new PathBuilder();
    }

    /**
     * Create empty path.
     *
     * @return empty path
     */
    public static Path empty() {
        return new Path(List.of());
    }

}
