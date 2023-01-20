package dev.khbd.lens4j.processor.path;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class PathTest {

    private final PathParser pathParser = new PathParser();

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
