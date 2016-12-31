package by.zagart.android.penumbra.utils;

import android.database.Cursor;

/**
 * Utility class with methods for work with {@link Cursor}.
 *
 * @author zagart
 */
@SuppressWarnings("unused")
public final class CursorUtil {

    public static Long getLong(final Cursor pCursor, final String pColumnName) {
        return pCursor.getLong(pCursor.getColumnIndex(pColumnName));
    }

    public static String getString(final Cursor pCursor, final String pColumnName) {
        return pCursor.getString(pCursor.getColumnIndex(pColumnName));
    }
}
