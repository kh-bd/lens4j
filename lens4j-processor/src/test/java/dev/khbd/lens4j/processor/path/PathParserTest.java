package dev.khbd.lens4j.processor.path;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class PathParserTest {

    private final PathParser pathParser = new PathParser();

    @Test
    public void parse_pathIsEmpty_returnEmpty() {
        Path path = pathParser.parse("");

        assertThat(path).isEqualTo(Path.empty());
    }

    @Test
    public void parse_pathWithoutProperties_returnPathWithPoints() {
        Path path = pathParser.parse("...");

        assertThat(path).isEqualTo(
                Path.builder()
                        .withPart(new Point(0))
                        .withPart(new Point(1))
                        .withPart(new Point(2))
                        .build()
        );
    }

    @Test
    public void parse_pathContainsDoublePoints_returnPath() {
        Path path = pathParser.parse("pr1..pr2.pr3");

        assertThat(path).isEqualTo(
                Path.builder()
                        .withPart(new Property("pr1", 0))
                        .withParts(new Point(3), new Point(4))
                        .withPart(new Property("pr2", 5))
                        .withPart(new Point(8))
                        .withPart(new Property("pr3", 9))
                        .build()
        );
    }

    @Test
    public void parse_validPathWithMethods_returnPath() {
        Path path = pathParser.parse("pr1().pr2.pr3()");

        assertThat(path).isEqualTo(
                Path.builder()
                        .withPart(new Method("pr1", 0))
                        .withParts(new Point(5))
                        .withPart(new Property("pr2", 6))
                        .withPart(new Point(9))
                        .withPart(new Method("pr3", 10))
                        .build()
        );
    }

    @Test
    public void parse_pathWithSeveralPointsAtTheEnd_returnPath() {
        Path path = pathParser.parse("pr1..");

        assertThat(path).isEqualTo(
                Path.builder()
                        .withPart(new Property("pr1", 0))
                        .withParts(new Point(3), new Point(4))
                        .build()
        );
    }
}
