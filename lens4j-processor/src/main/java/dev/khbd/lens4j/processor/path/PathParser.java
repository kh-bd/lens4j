package dev.khbd.lens4j.processor.path;

/**
 * Path parser.
 *
 * @author Sergei_Khadanovich
 */
public final class PathParser {

    private static final PathParser INSTANCE = new PathParser();

    /**
     * Parse string into {@link Path} instance.
     *
     * @param pathStr path as string
     * @return parsed path
     */
    public Path parse(String pathStr) {
        if (pathStr.isEmpty()) {
            return Path.empty();
        }

        PathBuilder builder = Path.builder();

        int start = 0;
        StringBuilder buffer = new StringBuilder();

        char[] chars = pathStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];
            if (current == '.') {
                if (buffer.length() != 0) {
                    builder.withPart(createNamedPart(buffer.toString(), start));
                    buffer = new StringBuilder();
                }
                builder.withPart(new Point(i));
            } else {
                if (buffer.length() == 0) { // start new property
                    start = i;
                }
                buffer.append(current);
            }
        }

        if (buffer.length() != 0) {
            builder.withPart(createNamedPart(buffer.toString(), start));
        }

        return builder.build();
    }

    private PathPart createNamedPart(String name, int start) {
        if (name.endsWith("()")) {
            return new Method(name.substring(0, name.length() - 2), start);
        } else {
            return new Property(name, start);
        }
    }

    /**
     * Get parser singleton instance.
     *
     * @return parser instance
     */
    public static PathParser getInstance() {
        return INSTANCE;
    }
}
