package by.zagart.android.penumbra.singletons;

import android.content.Context;

/**
 * Singleton for application context.
 *
 * @author zagart
 */
public enum ContextHolder {
    INSTANCE;
    private Context mContext;

    public static Context get() {
        return INSTANCE.mContext;
    }

    public static void set(final Context pContext) {
        INSTANCE.mContext = pContext;
    }
}
