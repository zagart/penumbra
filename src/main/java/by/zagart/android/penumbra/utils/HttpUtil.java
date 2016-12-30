package by.zagart.android.penumbra.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

import by.zagart.android.penumbra.constants.ExceptionConstants;
import by.zagart.android.penumbra.constants.NetworkConstants;

/**
 * Utility class for HTTP-requests and responses.
 *
 * @author zagart
 */

public class HttpUtil {

    public static void setRequestBody(final HttpURLConnection pConnection,
                                      final Map<String, String> pBody) {
        OutputStream output = null;
        final JSONObject jsonObject = new JSONObject();
        for (final Map.Entry<String, String> entry : pBody.entrySet()) {
            try {
                output = pConnection.getOutputStream();
                jsonObject.put(entry.getKey(), entry.getValue());
                byte[] outputInBytes = jsonObject.toString().getBytes(NetworkConstants.UTF_8);
                for (byte byt3 : outputInBytes) {
                    output.write(byt3);
                }
            } catch (JSONException | IOException pEx) {
                throw new RuntimeException(ExceptionConstants.SETTING_REQUEST_BODY_EXCEPTION);
            } finally {
                Log.i(HttpUtil.class.getSimpleName(), jsonObject.toString());
                IOUtil.close(output);
            }
        }
    }
}
