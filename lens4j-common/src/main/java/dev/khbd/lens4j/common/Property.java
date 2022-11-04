package dev.khbd.lens4j.common;

import java.util.Objects;

/**
 * Path part which represents property access in lens path.
 *
 * @author Sergei_Khadanovich
 */
@Deprecated(since = "0.2.1", forRemoval = true)
public class Property implements PathPart {

    private final String name;
    private final int start;

    public Property(String name, int start) {
        this.name = name;
        this.start = start;
    }

    @Override
    public void visit(PathVisitor visitor) {
        visitor.visitProperty(this);
    }

    @Override
    public PathPartKind getKind() {
        return PathPartKind.PROPERTY;
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
        Property property = (Property) o;
        return start == property.start && name.equals(property.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, start);
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", start=" + start +
                '}';
    }
}
