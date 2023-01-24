package dev.khbd.lens4j.processor.path;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class PathParserTest {

    private final PathParser pathParser = PathParser.getInstance();

    @Test
    public void parse_pathIsEmpty_returnEmpty() {
        Path path = pathParser.parse("");

        assertThat(path).isEqualTo(new Path());
    }

    @Test
    public void parse_pathWithoutProperties_returnPathWithPoints() {
        Path path = pathParser.parse("...");

        assertThat(path).isEqualTo(
                new Path()
                        .addPart(new Point(0))
                        .addPart(new Point(1))
                        .addPart(new Point(2))
        );
    }

    @Test
    public void parse_pathContainsDoublePoints_returnPath() {
        Path path = pathParser.parse("pr1..pr2.pr3");

        assertThat(path).isEqualTo(
                new Path()
                        .addPart(new Property("pr1", 0))
                        .addParts(new Point(3), new Point(4))
                        .addPart(new Property("pr2", 5))
                        .addPart(new Point(8))
                        .addPart(new Property("pr3", 9))
        );
    }

    @Test
    public void parse_validPathWithMethods_returnPath() {
        Path path = pathParser.parse("pr1().pr2.pr3()");

        assertThat(path).isEqualTo(
                new Path()
                        .addPart(new Method("pr1", 0))
                        .addParts(new Point(5))
                        .addPart(new Property("pr2", 6))
                        .addPart(new Point(9))
                        .addPart(new Method("pr3", 10))
        );
    }

    @Test
    public void parse_pathWithSeveralPointsAtTheEnd_returnPath() {
        Path path = pathParser.parse("pr1..");

        assertThat(path).isEqualTo(
                new Path()
                        .addPart(new Property("pr1", 0))
                        .addParts(new Point(3), new Point(4))
        );
    }
}
