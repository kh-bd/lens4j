package dev.khbd.lens4j.processor.path;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Sergei_Khadanovich
 */
public class PathTest {

    private final PathParser pathParser = new PathParser();

    @Test
    public void isEmpty_pathHasNoParts_returnTrue() {
        Path path = new Path();

        assertThat(path.isEmpty()).isTrue();
    }

    @Test
    public void isEmpty_pathHasSomeParts_returnFalse() {
        Path path = new Path(List.of(new Method("name", 0)));

        assertThat(path.isEmpty()).isFalse();
    }

}
