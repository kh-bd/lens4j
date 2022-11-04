package dev.khbd.lens4j.processor.path;

/**
 * Path part which represents point in lens path.
 *
 * @author Sergei_Khadanovich
 */
public record Point(int position) implements PathPart {

    @Override
    public void visit(PathVisitor visitor) {
        visitor.visitPoint(this);
    }

    @Override
    public PathPartKind getKind() {
        return PathPartKind.POINT;
    }

}