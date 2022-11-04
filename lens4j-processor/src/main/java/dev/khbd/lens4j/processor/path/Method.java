package dev.khbd.lens4j.processor.path;

/**
 * Path part which represents method access in lens path.
 *
 * @author Sergei_Khadanovich
 */
public record Method(String name, int start) implements PathPart {

    @Override
    public void visit(PathVisitor visitor) {
        visitor.visitMethod(this);
    }

    @Override
    public PathPartKind getKind() {
        return PathPartKind.METHOD;
    }
}
