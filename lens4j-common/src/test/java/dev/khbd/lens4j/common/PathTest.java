package dev.khbd.lens4j.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class PathTest {

    private final PathParser pathParser = new PathParser();

    @Test
    public void getCorrectPrefix_pathIsValid_returnTheSame() {
        Path path = pathParser.parse("pr1.pr2.pr3");

        Path result = path.getCorrectPathPrefix();

        assertThat(result).isEqualTo(path);
    }

    @Test
    public void getCorrectPrefix_pathContainsSeveralPoints_returnPrefix() {
        Path path = pathParser.parse("pr1.pr2...pr3");

        Path result = path.getCorrectPathPrefix();

        assertThat(result).isEqualTo(pathParser.parse("pr1.pr2."));
    }

    @Test
    public void getCorrectPrefix_pathStartsFromPoint_returnEmpty() {
        Path path = pathParser.parse(".pr1.pr2");

        Path result = path.getCorrectPathPrefix();

        assertThat(result).isEqualTo(new Path());
    }

    @Test
    public void getCorrectPrefix_pathEndsWithPoint_returnWholeExceptLastPoint() {
        Path path = pathParser.parse("pr1.pr2.");

        Path result = path.getCorrectPathPrefix();

        assertThat(result).isEqualTo(pathParser.parse("pr1.pr2."));
    }

    // hasCorrectStructure

    @Test
    public void hasCorrectStructure_pathIsEmpty_returnTrue() {
        Path path = pathParser.parse("");

        assertThat(path.hasCorrectStructure()).isTrue();
    }

    @Test
    public void hasCorrectStructure_onlyPoint_returnFalse() {
        Path path = pathParser.parse(".");

        assertThat(path.hasCorrectStructure()).isFalse();
    }

    @Test
    public void hasCorrectStructure_startsFromPoint_returnFalse() {
        Path path = pathParser.parse(".pr");

        assertThat(path.hasCorrectStructure()).isFalse();
    }

    @Test
    public void hasCorrectStructure_severalPointsInTheMiddle_returnFalse() {
        Path path = pathParser.parse("pr1...pr2");

        assertThat(path.hasCorrectStructure()).isFalse();
    }

    @Test
    public void hasCorrectStructure_endsWithPoint_returnTrue() {
        Path path = pathParser.parse("pr1.pr2.");

        assertThat(path.hasCorrectStructure()).isTrue();
    }

    @Test
    public void hasCorrectStructure_severalProperties_returnTrue() {
        Path path = pathParser.parse("pr1.pr2.pr3.pr4");

        assertThat(path.hasCorrectStructure()).isTrue();
    }

    // removeLastPart

    @Test(expectedExceptions = IllegalStateException.class,
            expectedExceptionsMessageRegExp = "Path is empty\\. Cannot remove last part")
    public void removeLastPart_pathIsEmpty_throwError() {
        pathParser.parse("").removeLastPart();
    }

    @Test
    public void removeLastPart_pathIsSingleProperty_returnEmptyPath() {
        Path path = pathParser.parse("pr").removeLastPart();

        assertThat(path).isEqualTo(new Path());
    }

    @Test
    public void removeLastPart_pathHasMoreThanOnePart_returnNewPath() {
        Path path = pathParser.parse("pr.p2.p3");

        Path result = path.removeLastPart();

        assertThat(path).isEqualTo(pathParser.parse("pr.p2.p3"));
        assertThat(result).isEqualTo(pathParser.parse("pr.p2."));
    }
}
