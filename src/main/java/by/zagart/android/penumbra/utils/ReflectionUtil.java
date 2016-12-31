package by.zagart.android.penumbra.utils;

import android.annotation.TargetApi;
import android.os.Build;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

/**
 * Utility class with methods which use mechanism of reflection.
 *
 * @author zagart
 */
@SuppressWarnings("unused")
final class ReflectionUtil {

    private static final int API_19 = 19;

    public static <O> O getGenericObject(final Object pTarget, final int pParameterPosition) {
        final ParameterizedType parameterizedType = (ParameterizedType) pTarget.getClass()
                .getGenericSuperclass();
        final Class<?> clazz = (Class<?>) parameterizedType
                .getActualTypeArguments()[pParameterPosition];
        final Constructor<?> constructor = clazz.getConstructors()[0];
        final O object;
        if (Build.VERSION.SDK_INT >= API_19) {
            object = getObjectApi19(constructor);
        } else {
            object = getObject(constructor);
        }
        return object;
    }

    @TargetApi(19)
    private static <O> O getObjectApi19(final Constructor<?> pConstructor) {
        final O object;
        try {
            object = (O) pConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException pEx) {
            throw new RuntimeException(pEx.getMessage());
        }
        return object;
    }

    private static <O> O getObject(final Constructor<?> pConstructor) {
        final O object;
        try {
            object = (O) pConstructor.newInstance();
        } catch (final Exception pEx) {
            throw new RuntimeException(pEx.getMessage());
        }
        return object;
    }
}
