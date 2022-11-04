package dev.khbd.lens4j.processor.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Sergei_Khadanovich
 */
public class PathBuilder {

    private final List<PathPart> parts = new ArrayList<>();

    PathBuilder() {
    }

    /**
     * Add part to path.
     *
     * @param part path part
     * @return self
     */
    public PathBuilder withPart(PathPart part) {
        Objects.requireNonNull(part);
        parts.add(part);
        return this;
    }

    /**
     * Add parts to path.
     *
     * @param parts path parts
     * @return self
     */
    public PathBuilder withParts(PathPart... parts) {
        for (PathPart part : parts) {
            withPart(part);
        }
        return this;
    }

    /**
     * Build resulted path.
     *
     * @return path
     */
    public Path build() {
        return new Path(parts);
    }
}
