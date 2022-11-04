package dev.khbd.lens4j.processor.path;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class PathTest {

    @Test
    public void isEmpty_pathHasNoParts_returnTrue() {
        Path path = Path.empty();

        assertThat(path.isEmpty()).isTrue();
    }

    @Test
    public void isEmpty_pathHasSomeParts_returnFalse() {
        Path path = Path.builder().withPart(new Method("name", 0)).build();

        assertThat(path.isEmpty()).isFalse();
    }

}
