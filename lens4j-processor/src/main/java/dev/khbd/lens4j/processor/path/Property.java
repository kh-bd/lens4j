package dev.khbd.lens4j.processor.path;

import lombok.Value;

/**
 * Path part which represents property access in lens path.
 *
 * @author Sergei_Khadanovich
 */
@Value
public class Property implements PathPart {

    String name;
    int start;

    @Override
    public void visit(PathVisitor visitor) {
        visitor.visitProperty(this);
    }

    @Override
    public PathPartKind getKind() {
        return PathPartKind.PROPERTY;
    }
}
