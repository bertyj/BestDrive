package com.conti.share.utils.common;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class TelephonyUtil {

    private static final String EMULATOR_IMEI = "000000000000000";

    public static String getImei(Context context) {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if(tm != null) {
                imei = tm.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return imei == null ? null : imei.replaceAll("[^\\da-zA-Z]*", "");
    }

    /**
     * 是否是模拟器环境
     */
    public static boolean isEmulator(Context context) {
        boolean isEmulator = false;
        if ((Build.MODEL.equals("sdk")) || (Build.MODEL.equals("google_sdk"))) {
            isEmulator = true;
        } else {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                String imei = tm.getDeviceId();
                if (imei == null || imei.equalsIgnoreCase(EMULATOR_IMEI)) {
                    isEmulator = true;
                }
            }
        }
        return isEmulator;
    }
}
