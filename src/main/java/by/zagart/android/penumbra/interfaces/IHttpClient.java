package by.zagart.android.penumbra.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Implementation of this interface allows to class-implementer
 * use privileges of HTTP-client such as do HTTP-request.
 */
public interface IHttpClient extends IHttpData {

    <Result> Result executeRequest(final IRequest<Result> pRequest) throws IOException;

    enum Method {
        GET, POST
    }

    interface IRequest<Result> {

        @ContentType
        String getContentType();

        Method getMethodType();

        String getUrl();

        void handleRequestConnection(final HttpURLConnection pConnection);

        Result onErrorStream(
                HttpURLConnection pConnection,
                InputStream pInputStream
        ) throws IOException;

        Result onStandardStream(final InputStream pInputStream) throws IOException;

        void onTimeoutException(final String... pParameters);

        void onIOException(final String... pParameters);
    }
}
