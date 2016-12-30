package by.zagart.android.penumbra.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import by.zagart.android.penumbra.interfaces.IHttpClient;
import by.zagart.android.penumbra.utils.IOUtil;

/**
 * Implementation of IHttpClient interface.
 */
class HttpClient implements IHttpClient {

    private static final int DEFAULT_TIMEOUT = 3000;

    @Override
    public <Result> Result executeRequest(final IHttpClient.IRequest<Result> pRequest) throws
            IOException {
        final Result result;
        final HttpURLConnection connection;
        InputStream errorStream = null;
        InputStream standardStream = null;
        final URL reqUrl = new URL(pRequest.getUrl());
        connection = ((HttpURLConnection) reqUrl.openConnection());
        connection.setConnectTimeout(DEFAULT_TIMEOUT);
        connection.setRequestMethod(pRequest.getMethodType().name());
        connection.setRequestProperty(Header.CONTENT_TYPE, pRequest.getContentType());
        try {
            pRequest.handleRequestConnection(connection);
            errorStream = connection.getErrorStream();
            if (errorStream == null) {
                standardStream = connection.getInputStream();
                result = pRequest.onStandardStream(standardStream);
            } else {
                result = pRequest.onErrorStream(connection, errorStream);
            }
            return result;
        } catch (SocketTimeoutException pEx) {
            pRequest.onTimeoutException();
            return null;
        } catch (IOException pEx) {
            pRequest.onIOException();
            return null;
        } finally {
            IOUtil.close(standardStream);
            IOUtil.close(errorStream);
            connection.disconnect();
        }
    }
}
