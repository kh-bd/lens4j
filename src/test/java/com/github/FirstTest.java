package com.github;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author Sergei_Khadanovich
 */
public class FirstTest {

    @Test
    public void firstTest() {
        boolean expr = true;

        assertThat(expr).isTrue();
    }
}
