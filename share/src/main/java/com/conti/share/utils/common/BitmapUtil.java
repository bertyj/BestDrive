package com.conti.share.utils.common;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitmapUtil {

    public static byte[] toByte(Bitmap bitmap, CompressFormat format,
                                int quality) {
        byte[] bitmapBytes = null;
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(format, quality, stream);
            bitmapBytes = stream.toByteArray();
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
            }
        }
        return bitmapBytes;
    }

    /**
     * 函数名: decodeByte<br>
     * 功能: <br>
     *
     * @param bitmapByte
     * @param isCompress     是否需要压缩
     * @param minSideLength  图片侧边的最小值。不需要可填-1
     * @param maxNumOfPixels 图片的最大的像素。不需要可填-1 例如：320*480
     * @return
     */
    public static Bitmap decodeByte(byte[] bitmapByte, boolean isCompress,
                                    int minSideLength, int maxNumPixels) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (isCompress) {
            // 设置为不分配内存。
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length,
                    options);
            options.inSampleSize = computeSampleSize(options, minSideLength,
                    maxNumPixels);
            try {
                // inNativeAlloc 属性设置为true，可以不把使用的内存算到VM里
                BitmapFactory.Options.class.getField("inNativeAlloc")
                        .setBoolean(options, true);
            } catch (Exception e) {
            }
            // 计算最终缩放比例：
            double originalSize = Math.sqrt(options.outWidth
                    * options.outHeight / (Math.pow(options.inSampleSize, 2)));
            double targetSize = Math.sqrt(maxNumPixels);

            if (options.inSampleSize > 1 || originalSize > targetSize) {
                options.inDensity = (int) originalSize;
                options.inTargetDensity = (int) targetSize;
                options.inScaled = true;
            }
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inJustDecodeBounds = false;
            ByteArrayInputStream is = null;
            try {
                is = new ByteArrayInputStream(bitmapByte);
                bitmap = BitmapFactory.decodeStream(is, null, options);
            } catch (OutOfMemoryError e) {
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        } else {
            options.inPurgeable = true;
            options.inInputShareable = true;
            bitmap = BitmapFactory.decodeByteArray(bitmapByte, 0,
                    bitmapByte.length, options);
        }
        return bitmap;
    }

    /**
     * 用于计算动态SampleSize的算法
     *
     * @param options
     * @param minSideLength  图片侧边的最小值。
     * @param maxNumOfPixels 图片的最大的像素
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double width = options.outWidth;
        double height = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.floor(Math
                .sqrt(width * height / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(width / minSideLength),
                Math.floor(height / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

}
