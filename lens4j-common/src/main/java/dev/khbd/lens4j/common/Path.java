package dev.khbd.lens4j.common;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author Sergei_Khadanovich
 */
public class Path {

    private final List<PathPart> parts;

    public Path(List<PathPart> parts) {
        this.parts = new ArrayList<>(parts);
    }

    public Path() {
        this.parts = new ArrayList<>();
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
     * Add part to the end of the path.
     *
     * <p>Be careful, this method mutates current instance.
     *
     * @param part part
     * @return self for chaining
     */
    public Path addPart(PathPart part) {
        parts.add(part);
        return this;
    }

    /**
     * Add parts to path.
     *
     * <p>Be careful, this method mutates current instance.
     *
     * @param parts parts
     * @return self for chaining
     */
    public Path addParts(PathPart... parts) {
        this.parts.addAll(List.of(parts));
        return this;
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

    /**
     * Get last part of the path.
     *
     * @return last part if exists
     */
    public PathPart getLastPart() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return parts.get(parts.size() - 1);
    }

    /**
     * Get first part of the path.
     *
     * @return first part if exists
     */
    public PathPart getFirstPart() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return parts.get(0);
    }

    /**
     * Create new path without last part.
     *
     * @return new path without last part
     */
    public Path removeLastPart() {
        if (isEmpty()) {
            throw new IllegalStateException("Path is empty. Cannot remove last part");
        }
        List<PathPart> newParts = new ArrayList<>(parts);
        newParts.remove(newParts.size() - 1);
        return new Path(newParts);
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
     * Make path copy.
     *
     * @return copied path instance
     */
    public Path copy() {
        return new Path(new ArrayList<>(parts));
    }

    /**
     * Get correct path prefix.
     *
     * <p>Correct path has formal structure:
     * path = property [point property]* [point]
     *
     * @return correct path prefix
     */
    public Path getCorrectPathPrefix() {
        CorrectPathPrefixCollector collector = new CorrectPathPrefixCollector();
        visit(collector);
        return collector.getPathPrefix();
    }

    /**
     * Check path has correct structure or not.
     *
     * <p>Correct path has formal structure:
     * path = property [point property]* [point]
     *
     * @return {@literal true} if path structure is correct and {@literal false} otherwise
     */
    public boolean hasCorrectStructure() {
        return this.equals(getCorrectPathPrefix());
    }

    private static final class CorrectPathPrefixCollector implements PathVisitor {

        private final Path result = new Path();
        private boolean fail;

        @Override
        public void visitPoint(Point point) {
            tryCollect(point);
        }

        @Override
        public void visitProperty(Property property) {
            tryCollect(property);
        }

        public Path getPathPrefix() {
            return result;
        }

        private void tryCollect(PathPart nextPart) {
            if (fail) {
                return;
            }
            if (result.isEmpty()) {
                collectFirst(nextPart);
            } else {
                PathPart lastPart = result.getLastPart();
                if (lastPart.hasTheSameKindWith(nextPart)) {
                    fail();
                } else {
                    result.addPart(nextPart);
                }
            }
        }

        private void collectFirst(PathPart nextPart) {
            if (nextPart.getKind() == PathPartKind.POINT) {
                fail();
                return;
            }
            result.addPart(nextPart);
        }

        private void fail() {
            fail = true;
        }
    }
}
