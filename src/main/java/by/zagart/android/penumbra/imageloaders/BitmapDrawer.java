package by.zagart.android.penumbra.imageloaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import by.zagart.android.penumbra.constants.ExceptionConstants;
import by.zagart.android.penumbra.interfaces.IAction;
import by.zagart.android.penumbra.interfaces.ICallback;
import by.zagart.android.penumbra.interfaces.IDrawable;
import by.zagart.android.penumbra.interfaces.IHttpClient;
import by.zagart.android.penumbra.managers.ThreadManager;
import by.zagart.android.penumbra.network.HttpFactory;
import by.zagart.android.penumbra.network.requests.DownloadBytesRequest;
import by.zagart.android.penumbra.utils.ImageUtil;

/**
 * Implementation of interface IDrawable.
 * Canvas - ImageView. Image - Bitmap.
 *
 * @author zagart
 */
@SuppressWarnings("unused")
public class BitmapDrawer implements IDrawable<ImageView, String> {

    private static final float MEMORY_USE_COEFFICIENT = 0.125f; //12.5%
    private final LruCache<String, Bitmap> mCache;
    private final ThreadManager mThreadManager;
    private boolean mResize;

    public BitmapDrawer(@NonNull final ThreadManager pThreadManager) {
        mThreadManager = pThreadManager;
        final int mMaxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int mCacheSize = (int) (mMaxMemory * MEMORY_USE_COEFFICIENT);
        mCache = new LruCache<String, Bitmap>(mCacheSize) {

            @Override
            protected int sizeOf(final String pKey, final Bitmap pImage) {
                return pImage.getByteCount() / 1024;
            }
        };
    }

    /**
     * Method draws on ImageView canvas specified as URL bitmap.
     * If there is no persisted in cache bitmap with such
     * URL as key, then it will be try to download it in background
     * thread.
     *
     * @param pImageView Canvas
     * @param pUrl       URL of Bitmap image
     */
    @Override
    public void draw(final ImageView pImageView, final String pUrl, final boolean... pResize) {
        if (pResize != null && pResize.length > 0) {
            mResize = pResize[0];
        }
        final boolean isImageSet = setImageBitmap(pImageView, pUrl);
        if (!isImageSet) {
            mThreadManager.executeAction(
                    new BitmapDownloadAction(),
                    new BitmapDownloadCallback(pImageView),
                    pUrl);
        }
    }

    private Bitmap getFromCache(final String pUrl) {
        synchronized (mCache) {
            return mCache.get(pUrl);
        }
    }

    private boolean setImageBitmap(final ImageView pImageView, final String pUrl) {
        final Bitmap bitmap = getFromCache(pUrl);
        if (bitmap != null) {
            try {
                mThreadManager.post(new Runnable() {

                    @Override
                    public void run() {
                        if (mResize) {
                            pImageView.setImageBitmap(ImageUtil.getResizedBitmap(
                                    bitmap,
                                    pImageView.getWidth(),
                                    pImageView.getHeight()));
                        } else {
                            pImageView.setImageBitmap(bitmap);
                        }
                    }
                });
            } catch (final Exception pEx) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * IAction interface implementation for downloading Bitmap image.
     * Calls {@link BitmapDownloadCallback} methods. Required to
     * execute downloading in background thread {@link ThreadManager}.
     */
    private class BitmapDownloadAction implements IAction<String, Void, ByteArrayOutputStream> {

        @Override
        public ByteArrayOutputStream process(
                final ICallback<Void, ByteArrayOutputStream> pCallback,
                final String... pParam
        ) throws InterruptedException {
            final String pUrl = pParam[0];
            pCallback.onStart(pUrl);
            pCallback.onComplete(pUrl, null);
            return null;
        }
    }

    /**
     * ICallback implementation with callbacks for Bitmap image downloading.
     * Use {@link IHttpClient} as HTTP-client for downloading bytes from
     * specified URL. On successful downloading convert them into Bitmap
     * and try to set downloaded image with resize if necessary.
     */
    private class BitmapDownloadCallback implements ICallback<Void, ByteArrayOutputStream> {

        private static final int BITMAP_DECODE_START_INDEX = 0;
        private final IHttpClient mHttpClient = HttpFactory.getDefaultClient();
        private final WeakReference<ImageView> mImageView;
        private byte[] downloaded;

        BitmapDownloadCallback(final ImageView pImageView) {
            mImageView = new WeakReference<>(pImageView);
        }

        @Override
        public void onComplete(final String pParam, final ByteArrayOutputStream pBytesStream) {
            final Bitmap bitmap = BitmapFactory.decodeByteArray(
                    downloaded,
                    BITMAP_DECODE_START_INDEX,
                    downloaded.length);
            downloaded = null;
            if (pParam != null && bitmap != null) {
                putInCache(pParam, bitmap);
                setImageBitmap(mImageView.get(), pParam);
            }
        }

        @Override
        public void onException(final String pParam, final Exception pEx) {
            throw new RuntimeException(ExceptionConstants.IMAGE_DOWNLOAD_EXCEPTION);
        }

        @Override
        public void onStart(final String pParam) {
            try {
                final ByteArrayOutputStream resultStream = mHttpClient.executeRequest(
                        new DownloadBytesRequest(pParam));
                if (resultStream != null) {
                    downloaded = resultStream.toByteArray();
                }
            } catch (final IOException pEx) {
                onException(pParam, pEx);
            }
        }

        @Override
        public void onUpdate(final String pParam, final Void pVoid) {
            //ignored
        }

        private boolean putInCache(final String pUrl, final Bitmap pBitmap) {
            synchronized (mCache) {
                if (mCache.get(pUrl) == null) {
                    mCache.put(pUrl, pBitmap);
                    return true;
                }
                return false;
            }
        }
    }
}
