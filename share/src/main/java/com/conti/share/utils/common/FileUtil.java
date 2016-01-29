package com.conti.share.utils.common;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    /**
     * ���sd�����Ƿ�����ļ�
     *
     * @param file
     * @return
     */
    public static boolean isExistFile(String file) {
        return false;
    }

    /**
     * �����ļ���Assets�ļ���
     *
     * @param context
     * @param alias
     * @param source
     * @return
     */
    public static String copyTextFromJarIntoAssetDir(Context context,
                                                     String alias, String source) {
        try {
            InputStream in = context.getAssets().open(source);
            File writeFile = new File(context.getFilesDir(), alias);
            return writeToDisk(in, writeFile);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * @param in
     * @param writeFile
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String writeToDisk(InputStream in, File writeFile)
            throws IllegalStateException, IOException {
        byte buff[] = new byte[1024];
        FileOutputStream out = null;

        if (in != null) {
            out = new FileOutputStream(writeFile);
            do {
                int numread = in.read(buff);
                if (numread <= 0)
                    break;
                out.write(buff, 0, numread);
            } while (true);
            in.close();
        } else {
            throw new IOException("NULL input stream in writeToDisk");
        }
        if (out != null) {
            out.flush();
            out.close();
        }
        return writeFile.getAbsolutePath();
    }

    /**
     * @param data
     * @param writeFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String writeToDisk(String data, File writeFile)
            throws FileNotFoundException, IOException {
        if (data != null && data.length() > 0) {
            FileOutputStream out = new FileOutputStream(writeFile);
            out.write(data.getBytes());
            if (out != null) {
                out.flush();
                out.close();
            }
        } else {
            throw new IOException("NULL input stream in writeToDisk");
        }
        return writeFile.getAbsolutePath();
    }

    /**
     * @param data
     * @param writeFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String writeToDisk(byte[] data, File writeFile)
            throws FileNotFoundException, IOException {

        //yzw
        boolean is = writeFile.exists();

        if (data != null && data.length > 0) {
            FileOutputStream out = new FileOutputStream(writeFile);
            out.write(data);
            if (out != null) {
                out.flush();
                out.close();
            }
        } else {
            throw new IOException("NULL input stream in writeToDisk");
        }
        return writeFile.getAbsolutePath();
    }
}
