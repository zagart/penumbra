package by.zagart.android.penumbra.interfaces;

/**
 * Simple result callback interface.
 *
 * @author zagart
 */
public interface IResultCallback<Param, Result> {

    Result onResult(final Param pParam);
}
