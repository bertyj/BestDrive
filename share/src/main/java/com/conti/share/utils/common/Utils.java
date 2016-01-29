package com.conti.share.utils.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Utils {

    public static String getAndroidId(Context context) {
        String androidId = "";
        try {
            androidId = android.provider.Settings.Secure.getString(
                    context.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidId;
    }

    public static int getScreenMinBorder(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        boolean isPortrait = width < height;
        return isPortrait ? width : height;
    }

    /**
     * 判断sd卡是否存在并可读写
     *
     * @return
     */
    public static boolean SDCardAccessAble() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    public static String getJsonValue(JSONObject object,String key){
        String value = "";
        try {
            value = object.getString(key);
        } catch (JSONException e) {
            return value;
        }
        return value;
    }

    public static Bitmap convertStreamToBitmap(InputStream is)
            throws NullPointerException, IOException {
        Bitmap bmp = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bmp = BitmapFactory.decodeStream(is, null, options);
        int width = options.outWidth;
        int height = options.outHeight;
        return bmp;
    }
}
