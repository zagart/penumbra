package by.zagart.android.penumbra.network;

import by.zagart.android.penumbra.interfaces.IHttpClient;

/**
 * Factory of HTTP-clients.
 */
public class HttpFactory {

    public static IHttpClient getDefaultClient() {
        return new HttpClient();
    }
}
