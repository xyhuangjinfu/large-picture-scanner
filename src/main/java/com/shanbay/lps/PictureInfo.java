package com.shanbay.lps;

public class PictureInfo {

    public String mAbsolutePath;
    public String mName;
    public int mWidth;
    public int mHeight;

    @Override
    public int hashCode() {
        return mAbsolutePath.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PictureInfo)) {
            return false;
        }

        PictureInfo p = (PictureInfo) obj;

        return mAbsolutePath.equals(p.mAbsolutePath);
    }
}
