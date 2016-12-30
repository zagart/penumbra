package by.zagart.android.penumbra.constants;

/**
 * Constants which describe messages for raising exceptions.
 *
 * @author zagart
 */

public interface ExceptionConstants {

    String NULL_CONTEXT = "Failed to get context from application service";
    String NOTIFY_MESSAGE = "Notify";
    String IMAGE_UPLOAD_EXCEPTION = "Failed to upload image";
    String IMAGE_DOWNLOAD_EXCEPTION = "Failed to download image";
    String CLOSABLE_CLOSING_EXCEPTION = "Failed to execute closing";
    String INPUT_STREAM_READING_EXCEPTION = "Failed to read input stream";
    String SETTING_REQUEST_BODY_EXCEPTION = "Failed to set request body";
    String REQUEST_EXCEPTION = "Error rise when handling request";
    String BAD_EXECUTION_ANNOTATION_PARAMETER = "Must be a an Execution value";
    String TIMEOUT_EXCEPTION = "No response";
    String IO_EXCEPTION = "IO exception raised";
    String SERVER_RESPONSE_ERROR = "Server returned error code";
}
