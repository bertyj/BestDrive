package com.conti.share.utils.common;

import java.util.Random;

public class StringUtil {

    /**
     * APK文件后缀
     */
    public static final String APK_FILE_SUFFIX = ".apk";

    static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9'};
    static final char[] LOWER_CHAR = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'};
    static final char[] UPPER_CHAR = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'};

    public static String getRandomString(int length, boolean repeatAllowed) {
        return getRandomString(length, repeatAllowed, NUMBERS, LOWER_CHAR,
                UPPER_CHAR);
    }

    /**
     * 获得一个指定长度的随机字符串
     */
    private static String getRandomString(int length, boolean repeatable,
                                          char[]... arrays) {
        if (length > 0 && arrays.length > 0) {
            StringBuilder builder = new StringBuilder();
            Random random = new Random();
            if (!repeatable) {
                // 如果请求的字符串长度超过数据源数组的总长度，则修改请求的字符串长度
                int totalLength = 0;
                for (char[] array : arrays) {
                    totalLength += array.length;
                }
                if (length > totalLength) {
                    length = totalLength;
                }
            }
            while (builder.length() < length) {
                int sourceIndex = random.nextInt(arrays.length);
                int dataIndex = random.nextInt(arrays[sourceIndex].length);
                char value = arrays[sourceIndex][dataIndex];
                if (repeatable) {
                    builder.append(value);
                } else {
                    if (builder.indexOf(value + "") == -1) {
                        builder.append(value);
                    }
                }
            }
            return builder.toString();
        }
        return null;
    }
}
