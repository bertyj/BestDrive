package com.conti.share.utils.common;


import android.content.Context;
import android.content.pm.PackageManager;

public class PermissionUtil {

    public static boolean hasPermission(Context context, String... permissions) {
        for (String permission : permissions) {
            if (context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                android.util.Log.w("Permission Check", "lack of permission: "
                        + permission);
                return false;
            }
        }
        return true;
    }

}
