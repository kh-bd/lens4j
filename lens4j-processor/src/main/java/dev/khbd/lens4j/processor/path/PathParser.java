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
        Path path = new Path();

        if (pathStr.isEmpty()) {
            return path;
        }

        int start = 0;
        StringBuilder buffer = new StringBuilder();

        char[] chars = pathStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];
            if (current == '.') {
                if (buffer.length() != 0) {
                    path.addPart(createNamedPart(buffer.toString(), start));
                    buffer = new StringBuilder();
                }
                path.addPart(new Point(i));
            } else {
                if (buffer.length() == 0) { // start new property
                    start = i;
                }
                buffer.append(current);
            }
        }

        if (buffer.length() != 0) {
            path.addPart(createNamedPart(buffer.toString(), start));
        }

        return path;
    }

    private PathPart createNamedPart(String name, int start) {
        if (name.endsWith("()")) {
            return new Method(name.substring(0, name.length() - 2), start);
        } else {
            return new Property(name, start);
        }
    }

    public static PathParser getInstance() {
        return INSTANCE;
    }
}
