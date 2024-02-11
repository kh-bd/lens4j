package dev.khbd.lens4j.processor.path;

import lombok.Value;

/**
 * Path part which represents point in lens path.
 *
 * @author Sergei_Khadanovich
 */
@Value
public class Point implements PathPart {

    int position;

    @Override
    public void visit(PathVisitor visitor) {
        visitor.visitPoint(this);
    }

    @Override
    public PathPartKind getKind() {
        return PathPartKind.POINT;
    }
}
