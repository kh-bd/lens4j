package dev.khbd.lens4j.processor.path;

import dev.khbd.lens4j.common.Method;
import dev.khbd.lens4j.common.Path;
import dev.khbd.lens4j.common.PathPart;
import dev.khbd.lens4j.common.PathVisitor;
import dev.khbd.lens4j.common.Point;
import dev.khbd.lens4j.common.Property;

import java.util.Objects;

/**
 * @author Sergei_Khadanovich
 */
public class PathStructureValidator implements PathVisitor {

    private PathPart previous;
    private boolean fail;

    @Override
    public void visitPoint(Point point) {
        if (fail) {
            return;
        }
        if (Objects.isNull(previous) || previous.isPoint()) {
            fail = true;
        }
        previous = point;
    }

    @Override
    public void visitProperty(Property property) {
        visitNamed(property);
    }

    @Override
    public void visitMethod(Method method) {
        visitNamed(method);
    }

    private void visitNamed(PathPart part) {
        if (fail) {
            return;
        }
        if (Objects.nonNull(previous) && !previous.isPoint()) {
            fail = true;
        }
        previous = part;
    }

    @Override
    public void finish() {
        if (fail) {
            return;
        }
        if (Objects.isNull(previous) || previous.isPoint()) {
            fail = true;
        }
    }

    public static boolean validate(Path path) {
        PathStructureValidator validator = new PathStructureValidator();
        path.visit(validator);
        return !validator.fail;
    }
}
