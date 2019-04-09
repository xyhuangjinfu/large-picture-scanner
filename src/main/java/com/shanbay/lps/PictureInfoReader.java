package com.shanbay.lps;

import org.gradle.api.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PictureInfoReader {

    /**
     * <href>https://en.wikipedia.org/wiki/Portable_Network_Graphics</href>
     * @param file
     * @return
     */
    @Nullable
    public static PictureInfo readPng(File file) {
        RandomAccessFile randomAccessFile = null;

        try {
            randomAccessFile = new RandomAccessFile(file, "r");

            int headerLength = 8;
            int chunkLengthLength = 4;
            int chunkTypeLength = 4;

            randomAccessFile.seek(headerLength + chunkLengthLength + chunkTypeLength);

            int width = randomAccessFile.readInt();
            int height = randomAccessFile.readInt();

            return createPictureInfo(file, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                }
            }
        }

        return null;
    }

    /**
     * ***************************************************************************************************************
     * <p>
     * ***************************************************************************************************************
     */

    /**
     * <href>https://wiki.tcl-lang.org/page/Reading+WEBP+image+dimensions</href>
     *
     * @param file
     * @return
     */
    @Nullable
    public static PictureInfo readWebp(File file) {
        RandomAccessFile randomAccessFile = null;

        try {
            randomAccessFile = new RandomAccessFile(file, "r");

            randomAccessFile.seek(12);

            byte[] bytes = new byte[4];
            randomAccessFile.read(bytes);
            String trunk = new String(bytes).trim().toUpperCase();

            if ("VP8".equals(trunk)) {
                return readWebpVp8(file);
            } else if ("VP8L".equals(trunk)) {
                return readWebpVp8l(file);
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                }
            }
        }

        return null;
    }

    @Nullable
    public static PictureInfo readWebpVp8(File file) {
        RandomAccessFile randomAccessFile = null;

        try {
            randomAccessFile = new RandomAccessFile(file, "r");

            randomAccessFile.seek(26);

            byte b1 = randomAccessFile.readByte();
            byte b2 = randomAccessFile.readByte();
            int width = 0;
            width = width | b1 & 0x000000ff;
            width = width | ((b2 & 0x000000ff) << 8);
            width = width & 0x3fffffff;

            byte b3 = randomAccessFile.readByte();
            byte b4 = randomAccessFile.readByte();
            int height = 0;
            height = height | (b3 & 0x000000ff);
            height = height | ((b4 & 0x000000ff) << 8);
            height = height & 0x3fffffff;

            return createPictureInfo(file, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                }
            }
        }

        return null;
    }

    @Nullable
    public static PictureInfo readWebpVp8l(File file) {
        RandomAccessFile randomAccessFile = null;

        try {
            randomAccessFile = new RandomAccessFile(file, "r");

            randomAccessFile.seek(21);

            byte b0 = randomAccessFile.readByte();
            byte b1 = randomAccessFile.readByte();
            byte b2 = randomAccessFile.readByte();
            byte b3 = randomAccessFile.readByte();

            int width = 1 + ((((b1 & 0x000000ff) & 0x3F) << 8) | (b0 & 0x000000ff));
            int height = 1 + ((((b3 & 0x000000ff) & 0xF) << 10) | ((b2 & 0x000000ff) << 2) | (((b1 & 0x000000ff) & 0xC0) >> 6));

            return createPictureInfo(file, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                }
            }
        }

        return null;
    }

    /**
     * ***************************************************************************************************************
     * <p>
     * ***************************************************************************************************************
     */

    private static PictureInfo createPictureInfo(File file, int width, int height) {
        PictureInfo pictureInfo = new PictureInfo();

        String fileName = file.getName();
        pictureInfo.mAbsolutePath = file.getAbsolutePath();
        pictureInfo.mName = fileName.substring(0, fileName.lastIndexOf('.'));
        pictureInfo.mWidth = width;
        pictureInfo.mHeight = height;

        return pictureInfo;
    }

}


