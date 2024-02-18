package dev.khbd.lens4j.processor.path;

import lombok.Value;

/**
 * Path part which represents method access in lens path.
 *
 * @author Sergei_Khadanovich
 */
@Value
public class Method implements PathPart {

    String name;
    int start;

    @Override
    public void visit(PathVisitor visitor) {
        visitor.visitMethod(this);
    }

    @Override
    public PathPartKind getKind() {
        return PathPartKind.METHOD;
    }
}
