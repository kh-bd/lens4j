package dev.khbd.lens4j.processor.util;

import lombok.experimental.UtilityClass;

/**
 * String utils.
 *
 * @author Sergei Khadanovich
 */
@UtilityClass
public class StringUtils {

    /**
     * Check is string is blank or not.
     *
     * @param cs char sequence
     * @return {@literal true} if it's blank and {@literal false} otherwise
     */
    public static boolean isBlank(CharSequence cs) {
        if (cs == null || cs.isEmpty()) {
            return true;
        }
        return cs.chars().allMatch(Character::isWhitespace);
    }

    /**
     * Capitalize string.
     *
     * @param original original string
     * @return capitalize string
     */
    public static String capitalize(String original) {
        if (original == null || original.isEmpty()) {
            return original;
        }

        char[] chars = original.toCharArray();

        if (chars[0] == Character.toTitleCase(chars[0])) {
            return original;
        }

        char[] capitalizeChars = new char[original.length()];
        capitalizeChars[0] = Character.toTitleCase(chars[0]);

        System.arraycopy(chars, 1, capitalizeChars, 1, capitalizeChars.length - 1);

        return new String(capitalizeChars);
    }
}
