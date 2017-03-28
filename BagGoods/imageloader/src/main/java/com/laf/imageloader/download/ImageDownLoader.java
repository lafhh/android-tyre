package com.laf.imageloader.download;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import com.laf.imageloader.decode.ImageDecodingInfo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by apple on 2017/3/20.
 */

public class ImageDownLoader {

    private static final String TAG = "ImageDownLoader";

    /**
     * http connect timeout, milliseconds
     * {@value}
     */
    private static final int HTTP_CONNECT_TIMEOUT = 60 * 1000;

    /**
     * http read timeout, milliseconds
     * {@value}
     */
    private static final int HTTP_READ_TIMEOUT = 60 * 1000;

    /**
     * {@value}
     */
    private static final int BUFFER_SIZE = 32 * 1024; // 32kb


    /**
     * load a scaled down version into memory
     * <p>
     * <a href="https://developer.android.com/topic/performance/graphics/load-bitmap.html"></a><br/>
     * 官网练习：Loading Large Bitmaps Efficiently
     * <p>
     * 可参考 Android-Universal-Image-Loader<br>
     * ImageSizeUtils#computeImageSampleSize()
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeFromResource(Resources res,
                                     int resId,
                                     int reqWidth,
                                     int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //先获取原图的尺寸，根据目标尺寸计算出inSampleSize的值
        BitmapFactory.decodeResource(res, resId, options);
        final int imageHeight = options.outHeight;
        final int imageWidth = options.outWidth;
        int inSampleSize = 1;

        if (imageHeight > reqHeight || imageWidth > reqWidth) {
            final int halfHeight = imageHeight / 2;
            final int halfWidth = imageWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
        Log.d(TAG, "bitmap.width = " + options.outWidth +
                " bitmap.height = " + options.outHeight);

        return bitmap;
    }

    public Bitmap decodeFromStream(ImageDecodingInfo decodingInfo,
                                   Context context) throws IOException {

        InputStream in = getImageStreamFromNetwork(decodingInfo.getImgUri());
        Log.d(TAG, "after network");
        if (in == null) {
            Log.d(TAG, "no image input stream");
            return null;
        }

        int targetWidth = decodingInfo.getTargetWidth();

        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        int inSampleSize = 1;
        Log.d(TAG, "original image width = " + srcWidth);
        Log.d(TAG, "original image height = " + srcHeight);

//        decodingInfo.setSrcWidth(srcWidth);
//        decodingInfo.setSrcHeight(srcHeight);

        //目前只有获取首页图片的处理逻辑, 宽==屏幕宽，高按比例缩放,
        //所以inSampleSize只需参照宽度的比例
        if (srcWidth > targetWidth) {
            inSampleSize = Math.round(srcWidth / targetWidth);
            Log.d(TAG, "decodeFromStream==imSampleSize: " + inSampleSize);
        }

        InputStream imgStream = resetStream(in, decodingInfo);
        Options opts = new Options();
        int density = decodingInfo.getInDensity();
        if (density == TypedValue.DENSITY_DEFAULT) {
            opts.inDensity = DisplayMetrics.DENSITY_DEFAULT;
        } else if (density != TypedValue.DENSITY_NONE) {
            opts.inDensity = density;
        }
        opts.inSampleSize = inSampleSize;
        opts.inJustDecodeBounds = false;
        opts.inTargetDensity = decodingInfo.getTargetDensity();
        opts.inScaled = decodingInfo.isInScaled();
        Bitmap decodedBitmap = BitmapFactory.decodeStream(imgStream, null, opts);
        Log.d(TAG, "decodestream end===================");
        Log.d(TAG, "options.width = " + options.outWidth);
        Log.d(TAG, "options.height = " + options.outHeight);
        Log.d(TAG, "decodestream end===================");

        if (decodedBitmap != null) {
            decodedBitmap = exactScaleForHomeImage(decodedBitmap, decodingInfo);
        } else {
            Log.d(TAG, "bitmap from decodeStream() is null");
        }

        return decodedBitmap;
    }

    /**
     * exactly scaled for the home ImageView
     *
     * @param decodedBitmap
     * @return
     */
    protected Bitmap exactScaleForHomeImage(Bitmap decodedBitmap,
                                            ImageDecodingInfo decodingInfo) {
        Matrix m = new Matrix();
        float w = decodedBitmap.getWidth();
        float h = decodedBitmap.getHeight();
        float scale = h / w;
        int targetHeight = Math.round(
                decodingInfo.getTargetWidth() * scale);
        Log.d(TAG, "before exactScale ========================");
        Log.d(TAG, "decodedbitmap.getwidth() = " + w);
        Log.d(TAG, "decodedbitmap.getHeight() = " + h);
        Log.d(TAG, "scale = " + h / w);
        Log.d(TAG, "targetwidth = " + decodingInfo.getTargetWidth());
        Log.d(TAG, "targetHeight = " + targetHeight);
        Log.d(TAG, "before exactScale ========================");

//        if (Double.compare(scale, 1d) != 0) {
//            m.setScale(scale, scale);
//        }

        Bitmap finalBitmap = Bitmap.createBitmap(
                decodedBitmap,
                0,
                0,
                decodingInfo.getTargetWidth(),
                targetHeight,
                m,
                true);
        Log.d(TAG, "finalBitmap======================");
        Log.d(TAG, "width = " + finalBitmap.getWidth());
        Log.d(TAG, "height = " + finalBitmap.getHeight());
        Log.d(TAG, "finalBitmap======================");
        if (finalBitmap != decodedBitmap) {
            decodedBitmap.recycle();
        }

        return finalBitmap;
    }

    /**
     * Retrieves {@link InputStream} of image by URI<br/>
     * <p>
     * 这里抛出了几处异常：1.获取InputStream时抛出IO异常；<br/> 2.返回码非200时，抛出异常<br/>
     * 3.还有getResponseCode()以及is.close()有可能会抛出IO异常<br/>
     * see: Android-Universal-Image-Loader<br/>
     * <a href=
     * https://github.com/nostra13/Android-Universal-Image-Loader/
     * ></a>
     * com.nostra13.universalimageloader.utils.IoUtils<br/>
     * com.nostra13.universalimageloader.core.download.BaseImageDownloader#getStreamFromNetwork()
     *
     * @param imgUri Image URI
     * @return {@link InputStream} of image
     * @throws IOException
     */
    public InputStream getImageStreamFromNetwork(String imgUri) throws IOException {
        HttpURLConnection conn = createConnection(imgUri);
        Log.d(TAG, "createconnection.conn = " + conn); //断网情况下，conn 有返回值，

        int responseCode;
        try {
            responseCode = conn.getResponseCode();
        } catch (IOException e) {
            if (conn != null) conn.disconnect();
            throw e;
        }

//        int redirectCount = 0;

        //conn.getHeaderField("Location")返回什么？
//        while (responseCode / 100 == 3 && redirectCount < MAX_REDIRECT_COUNT) {
//            conn = createConnection(conn.getHeaderField("Location"));
//        redirectCount++;
//        }

        InputStream is;
        try {
            is = conn.getInputStream();
        } catch (IOException e) {
            InputStream errorStream = conn.getErrorStream();
            Log.d(TAG, "responseCode = " + responseCode +
                    " errorStream = " + errorStream);

            final byte[] bytes = new byte[32 * 1024]; //32kb

            try {
                while (errorStream.read(bytes, 0, bytes.length) != -1) ;
            } catch (IOException ignored) {
            } finally {
                try {
                    if (errorStream != null) errorStream.close();
                } catch (IOException ignored) {
                }
            }
            throw e; //代码不会再往下走
        }

        if (responseCode != 200) {
            if (is != null) is.close();
            throw new IOException("Image request failed with response code " + responseCode);
        }

//        return new BufferedInputStream(is, BUFFER_SIZE);
        return is;
    }

    private HttpURLConnection createConnection(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
        conn.setReadTimeout(HTTP_READ_TIMEOUT);
        return conn;
    }

    private InputStream resetStream(InputStream in, ImageDecodingInfo info) throws IOException {
        if (in.markSupported()) {
            try {
                in.reset();
                return in;
            } catch (IOException ignored) {}
        }
        try {
            in.close();
        } catch (IOException ignored) {}

        return getImageStreamFromNetwork(info.getImgUri());
    }
}

