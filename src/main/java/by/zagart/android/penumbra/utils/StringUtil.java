package by.zagart.android.penumbra.utils;

/**
 * Utility class to work with strings.
 *
 * @author zagart
 */
@SuppressWarnings("unused")
public class StringUtil {

    public static String add(final String... pStrings) {
        final StringBuilder builder = new StringBuilder();
        for (final String string : pStrings) {
            builder.append(string);
        }
        return builder.toString();
    }
}
