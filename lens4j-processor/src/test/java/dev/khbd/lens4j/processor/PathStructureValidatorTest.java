package dev.khbd.lens4j.processor;

import static org.assertj.core.api.Assertions.assertThat;

import dev.khbd.lens4j.common.Path;
import dev.khbd.lens4j.common.PathParser;
import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class PathStructureValidatorTest {

    private final PathParser pathParser = PathParser.getInstance();

    @Test
    public void validate_pathIsEmpty_returnFalse() {
        Path path = pathParser.parse("");

        assertThat(PathStructureValidator.validate(path)).isFalse();
    }

    @Test
    public void validate_onlyPoint_returnFalse() {
        Path path = pathParser.parse(".");

        assertThat(PathStructureValidator.validate(path)).isFalse();
    }

    @Test
    public void validate_startsFromPoint_returnFalse() {
        Path path = pathParser.parse(".pr");

        assertThat(PathStructureValidator.validate(path)).isFalse();
    }

    @Test
    public void validate_severalPointsInTheMiddle_returnFalse() {
        Path path = pathParser.parse("pr1...pr2");

        assertThat(PathStructureValidator.validate(path)).isFalse();
    }

    @Test
    public void validate_endsWithPoint_returnFalse() {
        Path path = pathParser.parse("pr1.pr2.");

        assertThat(PathStructureValidator.validate(path)).isFalse();
    }

    @Test
    public void validate_severalProperties_returnTrue() {
        Path path = pathParser.parse("pr1.pr2.pr3.pr4");

        assertThat(PathStructureValidator.validate(path)).isTrue();
    }

}
