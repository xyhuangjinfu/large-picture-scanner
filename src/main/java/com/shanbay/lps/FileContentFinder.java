package com.shanbay.lps;

import org.gradle.api.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class FileContentFinder {

    public static boolean find(File file, String content) {
        byte[] fileContent = readFile(file);

        if (fileContent == null) {
            return false;
        }

        StringBuilder sb = new StringBuilder(new String(fileContent));

        return sb.indexOf(content) != -1;
    }

    /**
     * ***************************************************************************************************************
     * <p>
     * ***************************************************************************************************************
     */

    @Nullable
    private static byte[] readFile(File file) {
        ByteArrayOutputStream baos = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            byte[] readBuffer = new byte[1024];
            int readLength;
            while (true) {
                readLength = fis.read(readBuffer);
                if (readLength == -1) {
                    break;
                }
                baos.write(readBuffer, 0, readLength);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

}
