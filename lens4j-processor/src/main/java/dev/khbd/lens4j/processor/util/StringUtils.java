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

        if (chars[0] == Character.toUpperCase(chars[0])) {
            return original;
        }

        char[] capitalizeChars = new char[original.length()];
        capitalizeChars[0] = Character.toUpperCase(chars[0]);

        System.arraycopy(chars, 1, capitalizeChars, 1, capitalizeChars.length - 1);

        return new String(capitalizeChars);
    }

    /**
     * Convert camel case string to snake-upper case.
     *
     * @param original original string
     * @return converted string
     */
    public static String toSnakeCase(String original) {
        if (original == null || original.isEmpty()) {
            return original;
        }

        StringBuilder builder = new StringBuilder();

        char[] chars = original.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            builder.append(Character.toUpperCase(chars[i]));
            if (Character.isLowerCase(chars[i]) && Character.isUpperCase(chars[i + 1])) {
                builder.append("_");
            }
        }
        builder.append(Character.toUpperCase(chars[chars.length - 1]));

        return builder.toString();
    }
}
