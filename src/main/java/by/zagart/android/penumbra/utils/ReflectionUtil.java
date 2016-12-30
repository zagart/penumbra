package by.zagart.android.penumbra.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

/**
 * Utility class with methods which use mechanism of reflection.
 *
 * @author zagart
 */

public class ReflectionUtil {

    public static <Entity> Entity getGenericObject(final Object pTarget,
                                                   final int pParameterPosition) {
        ParameterizedType parameterizedType = (ParameterizedType) pTarget.getClass()
                .getGenericSuperclass();
        Class<?> clazz = (Class<?>) parameterizedType.getActualTypeArguments()[pParameterPosition];
        Constructor<?> constructor = clazz.getConstructors()[0];
        Object object;
        if (Build.VERSION.SDK_INT >= 19) {
            object = getObjectApi19(constructor);
        } else {
            object = getObject(constructor);
        }
        return (Entity) object;
    }

    @TargetApi(19)
    private static Object getObjectApi19(final Constructor<?> pConstructor) {
        Object object = null;
        try {
            object = pConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException pEx) {
            Log.e(ReflectionUtil.class.getSimpleName(), pEx.getMessage(), pEx);
        }
        return object;
    }

    private static Object getObject(final Constructor<?> pConstructor) {
        Object object = null;
        try {
            object = pConstructor.newInstance();
        } catch (Exception pEx) {
            Log.e(ReflectionUtil.class.getSimpleName(), pEx.getMessage(), pEx);
        }
        return object;
    }

}
