package by.zagart.android.penumbra.network.requests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import by.zagart.android.penumbra.constants.ExceptionConstants;
import by.zagart.android.penumbra.interfaces.IHttpClient;
import by.zagart.android.penumbra.utils.IOUtil;

/**
 * Request for downloading bytes at specified URL address.
 *
 * @author zagart
 * @see by.zagart.android.penumbra.interfaces.IHttpClient.IRequest
 */

public class DownloadBytesRequest implements IHttpClient.IRequest<ByteArrayOutputStream> {

    private final String mUrl;

    public DownloadBytesRequest(final String pUrl) {
        mUrl = pUrl;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public IHttpClient.Method getMethodType() {
        return IHttpClient.Method.GET;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public void handleRequestConnection(final HttpURLConnection pConnection) {
        //no special handling required
    }

    @Override
    public ByteArrayOutputStream onErrorStream(
            final HttpURLConnection pConnection,
            final InputStream pErrorStream) throws IOException {
        throw new RuntimeException(ExceptionConstants.SERVER_RESPONSE_ERROR);
    }

    @Override
    public ByteArrayOutputStream onStandardStream(final InputStream pInputStream) {
        return IOUtil.readInputIntoByteArray(pInputStream);
    }

    @Override
    public void onTimeoutException(final String... pParameters) {
        throw new RuntimeException(ExceptionConstants.TIMEOUT_EXCEPTION);
    }

    @Override
    public void onIOException(final String... pParameters) {
        throw new RuntimeException(ExceptionConstants.IO_EXCEPTION);
    }
}
