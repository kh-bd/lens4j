package dev.khbd.lens4j.processor.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * @author Sergei Khadanovich
 */
public class StringUtilsTest {

    @Test
    public void isBlank_stringIsNull_returnTrue() {
        assertThat(StringUtils.isBlank(null)).isTrue();
    }

    @Test
    public void isBlank_stringIsEmpty_returnTrue() {
        assertThat(StringUtils.isBlank("")).isTrue();
    }

    @Test
    public void isBlank_allCharsAreWhitespace_returnTrue() {
        assertThat(StringUtils.isBlank("   ")).isTrue();
    }

    @Test
    public void isBlank_hasNoneWhitespaceChar_returnFalse() {
        assertThat(StringUtils.isBlank(" H ")).isFalse();
    }

    @Test
    public void capitalize_stringIsNull_returnNull() {
        assertThat(StringUtils.capitalize(null)).isNull();
    }

    @Test
    public void capitalize_stringIsEmpty_returnEmpty() {
        assertThat(StringUtils.capitalize("")).isEmpty();
    }

    @Test
    public void capitalize_stringIsCapitalized_returnArgument() {
        assertThat(StringUtils.capitalize("Hello")).isEqualTo("Hello");
    }

    @Test
    public void capitalize_stringIsNotCapitalized_returnCapitalizedVersion() {
        assertThat(StringUtils.capitalize("hello")).isEqualTo("Hello");
    }
}