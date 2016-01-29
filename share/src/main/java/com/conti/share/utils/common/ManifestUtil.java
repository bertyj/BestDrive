package com.conti.share.utils.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class ManifestUtil {

    public static String getMetaValue(Context context, String packageName,
                                      String attrName) {
        String value = null;
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(
                    packageName, PackageManager.GET_META_DATA);
            Object obj = (applicationInfo.metaData != null ? applicationInfo.metaData
                    .get(attrName) : null);
            if (obj != null) {
                value = String.valueOf(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
