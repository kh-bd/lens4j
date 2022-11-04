package dev.khbd.lens4j.common;

import java.util.Objects;

/**
 * Path part which represents method access in lens path.
 *
 * @author Sergei_Khadanovich
 */
@Deprecated(since = "0.2.1", forRemoval = true)
public class Method implements PathPart {

    private final String name;
    private final int start;

    public Method(String name, int start) {
        this.name = name;
        this.start = start;
    }

    @Override
    public void visit(PathVisitor visitor) {
        visitor.visitMethod(this);
    }

    @Override
    public PathPartKind getKind() {
        return PathPartKind.METHOD;
    }

    public String getName() {
        return name;
    }

    public int getStart() {
        return start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method property = (Method) o;
        return start == property.start && name.equals(property.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, start);
    }

    @Override
    public String toString() {
        return "Method{" +
                "name='" + name + '\'' +
                ", start=" + start +
                '}';
    }
}
