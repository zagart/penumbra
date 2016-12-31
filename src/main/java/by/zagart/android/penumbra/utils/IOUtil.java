package by.zagart.android.penumbra.utils;

import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import by.zagart.android.penumbra.constants.ExceptionConstants;

/**
 * Utility class for input/output related methods.
 *
 * @author zagart
 */
@SuppressWarnings("unused")
public final class IOUtil {

    private static final short READ_BUFFER_SIZE = 4096;
    private static final byte EOF = -1;

    public static void close(final Closeable pCloseable) {
        if (pCloseable != null) {
            try {
                pCloseable.close();
            } catch (final IOException pEx) {
                throw new RuntimeException(ExceptionConstants.CLOSABLE_CLOSING_EXCEPTION);
            }
        }
    }

    @Nullable
    public static ByteArrayOutputStream readInputIntoByteArray(final InputStream pInputStream) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final byte[] buffer = new byte[IOUtil.READ_BUFFER_SIZE];
        int bytesRead;
        try {
            while ((bytesRead = pInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (final IOException pEx) {
            throw new RuntimeException(ExceptionConstants.INPUT_STREAM_READING_EXCEPTION);
        }
        return outputStream;
    }

    public static String readStreamIntoString(final InputStream pInputStream) throws IOException {
        final Reader reader = new InputStreamReader(pInputStream, Charset.defaultCharset());
        final StringBuilder result = new StringBuilder();
        final char[] buffer = new char[READ_BUFFER_SIZE];
        try {
            int bytes;
            while ((bytes = reader.read(buffer)) != EOF) {
                result.append(buffer, 0, bytes);
            }
        } finally {
            IOUtil.close(reader);
        }
        return result.toString();
    }
}
