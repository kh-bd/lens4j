package dev.khbd.lens4j.processor.path;

/**
 * Path part which represents property access in lens path.
 *
 * @author Sergei_Khadanovich
 */
public record Property(String name, int start) implements PathPart {

    @Override
    public void visit(PathVisitor visitor) {
        visitor.visitProperty(this);
    }

    @Override
    public PathPartKind getKind() {
        return PathPartKind.PROPERTY;
    }
}
