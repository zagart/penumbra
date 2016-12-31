package by.zagart.android.penumbra.managers;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringDef;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import by.zagart.android.penumbra.constants.ExceptionConstants;
import by.zagart.android.penumbra.interfaces.IAction;
import by.zagart.android.penumbra.interfaces.ICallback;

/**
 * Thread manager class. Provides simplified management of multiple
 * threads.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ThreadManager {

    private static final int COUNT_CORE = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_THREADS_NUMBER = 3;
    private static final int MAX_THREADS_NUMBER = Math.max(COUNT_CORE, DEFAULT_THREADS_NUMBER);
    private final ExecutorService mPool;
    private final Handler mHandler;

    public ThreadManager(@Execution final String pExecution) {
        switch (pExecution) {
            case Execution.PARALLEL:
                mPool = Executors.newFixedThreadPool(MAX_THREADS_NUMBER);
                break;
            case Execution.SINGLE:
                mPool = Executors.newSingleThreadExecutor();
                break;
            default:
                throw new IllegalArgumentException(
                        ExceptionConstants.BAD_EXECUTION_ANNOTATION_PARAMETER
                );
        }
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Executes {@link Runnable} implementation on background thread.
     *
     * @param pRunnable Object to run
     */
    public void execute(final Runnable pRunnable) {
        mPool.execute(pRunnable);
    }

    /**
     * Runs IAction using ICallback. Useful for downloading images.
     *
     * @param pAction   Action to execute
     * @param pCallback Callback to use
     * @param pParam    Additional string parameter
     * @see {@link IAction}
     * @see {@link ICallback}
     */
    public void executeAction(
            final IAction<String, Void, ByteArrayOutputStream> pAction,
            final ICallback<Void, ByteArrayOutputStream> pCallback,
            final String pParam) {
        mPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    final ByteArrayOutputStream result = pAction.process(pCallback, pParam);
                    if (result != null) {
                        pCallback.onComplete(pParam, result);
                    }
                } catch (final InterruptedException pEx) {
                    pCallback.onException(ThreadManager.class.getSimpleName(), pEx);
                }
            }
        });
    }

    /**
     * Executes {@link Runnable} implementation on UI-thread.
     *
     * @param pRunnable Object to run
     */
    public void post(final Runnable pRunnable) {
        mHandler.post(pRunnable);
    }

    @StringDef({
            Execution.PARALLEL,
            Execution.SINGLE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Execution {

        String PARALLEL = "Parallel";
        String SINGLE = "Single";
    }
}
