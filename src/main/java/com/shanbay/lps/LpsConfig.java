package com.shanbay.lps;

public class LpsConfig {

    /**
     * 以ARGB_8888加载后只需要4M，应该都能扛得住吧
     */
    private long mDimensionLimit = 1080 * 960;

    public long getDimensionLimit() {
        return mDimensionLimit;
    }

    public void setDimensionLimit(long dimensionLimit) {
        mDimensionLimit = dimensionLimit;
    }
}
