package by.zagart.android.penumbra.utils;

import android.net.Uri;

import java.util.Locale;

import by.zagart.android.penumbra.constants.ApplicationConstants;
import by.zagart.android.penumbra.constants.URIConstants;

/**
 * Utility class with methods to work with URI.
 *
 * @author zagart
 */
@SuppressWarnings("unused")
public final class URIUtil {

    public static String getClearUriPath(final Uri pUri) {
        final String path = pUri.getPath();
        return path.replace(URIConstants.URI_SEPARATOR, ApplicationConstants.EMPTY_STRING);
    }

    private static String uriBuilder(final String pAuthority,
                                     final String pPath,
                                     final String pId) {
        return String.format(
                Locale.getDefault(),
                URIConstants.URI_FORMAT,
                URIConstants.CONTENT,
                pAuthority,
                pPath,
                pId
        );
    }
}
