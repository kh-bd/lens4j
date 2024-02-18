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

    @Test
    public void toSnakeCase_stringIsNull_returnNull() {
        assertThat(StringUtils.toSnakeCase(null)).isNull();
    }

    @Test
    public void toSnakeCase_stringIsEmpty_returnEmpty() {
        assertThat(StringUtils.toSnakeCase("")).isEmpty();
    }

    @Test
    public void toSnakeCase_simpleWorld_returnUppercaseVersion() {
        String result = StringUtils.toSnakeCase("lens");

        assertThat(result).isEqualTo("LENS");
    }

    @Test
    public void toSnakeCase_simpleUppercase_returnTheSame() {
        String result = StringUtils.toSnakeCase("LENS_NAME");

        assertThat(result).isEqualTo("LENS_NAME");
    }

    @Test
    public void toSnakeCase_stringInCamelCase_returnUppercaseVersion() {
        String result = StringUtils.toSnakeCase("lensName");

        assertThat(result).isEqualTo("LENS_NAME");
    }
}