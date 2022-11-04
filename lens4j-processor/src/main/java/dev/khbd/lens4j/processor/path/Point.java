package dev.khbd.lens4j.processor.path;

import java.util.Objects;

/**
 * Path part which represents point in lens path.
 *
 * @author Sergei_Khadanovich
 */
public class Point implements PathPart {

    private final int position;

    public Point(int position) {
        this.position = position;
    }

    @Override
    public void visit(PathVisitor visitor) {
        visitor.visitPoint(this);
    }

    @Override
    public PathPartKind getKind() {
        return PathPartKind.POINT;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return position == point.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "Point{" +
                "position=" + position +
                '}';
    }
}
