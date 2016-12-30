package by.zagart.android.penumbra.interfaces;

/**
 * Parent for all Action interfaces.
 *
 * @author zagart
 */
public interface IAction<Param, Progress, Result> {

    Result process(
            final ICallback<Progress, Result> pCallback,
            final Param... pParam) throws InterruptedException;
}
