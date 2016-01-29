package com.conti.share.utils.common;


import android.Manifest.permission;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkUtil {
    private static final int NETWORK_TYPE_WIFI = 1;
    private static final int NETWORK_TYPE_MOBILE = 0;

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    private static NetworkInfo getNetworkStatus(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;
        for (NetworkInfo info : conMan.getAllNetworkInfo()) {
            if (info.isConnected()) {
                networkInfo = info;
                break;
            }
        }
        return networkInfo;
    }

    public static boolean isWifi(Context context) {
        boolean isWifi = false;
        NetworkInfo info = getNetworkStatus(context);
        if (info != null) {
            isWifi = (info.getType() == ConnectivityManager.TYPE_WIFI);
        }
        return isWifi;
    }

    static int getNetworkType(Context context) {
        int networkType = NETWORK_TYPE_MOBILE;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null
                && connectivityManager.getActiveNetworkInfo() != null) {
            if (connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
                networkType = NETWORK_TYPE_WIFI;
            }
        }
        return networkType;
    }

    /**
     * 获取设备MAC地址
     */
    public static String getLocalMacAddress(Context context) {
        String mac = null;
        if (PermissionUtil.hasPermission(context, permission.ACCESS_WIFI_STATE)) {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();
            mac = info.getMacAddress();
            mac = (mac == null ? null : mac.replaceAll("[^\\da-zA-Z]*", ""));
        }
        return mac;
    }

    static String getWifiBssid(Context context) {
        StringBuilder sb = new StringBuilder();
        if (PermissionUtil.hasPermission(context, permission.ACCESS_WIFI_STATE)) {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            if (wifiManager.isWifiEnabled()) {
                WifiInfo info = wifiManager.getConnectionInfo();
                String connectedBssid = null;
                if (info != null && info.getBSSID() != null) {
                    connectedBssid = info.getBSSID();
                    sb.append(connectedBssid).append('/')
                            .append(info.getRssi()).append(";");
                }
            }
        }
        return sb.toString();
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return intToIp(wifiInfo.getIpAddress());
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

}
