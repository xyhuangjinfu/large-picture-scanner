package com.shanbay.lps;

import java.io.File;
import java.util.Set;

public class WrongUseDetector {

    public static void detect(File file, Set<PictureInfo> largePictureSet) {
        for (PictureInfo pic : largePictureSet) {
            if (FileContentFinder.find(file, pic.mName)) {
                throw new RuntimeException(getErrorMsg(file, pic));
            }
        }
    }

    private static String getErrorMsg(File file, PictureInfo pic) {
        return "detect wrong use of large picture, picture path : " + pic.mAbsolutePath + " , picture size : (" + pic.mWidth + "," + pic.mHeight + ")\n"
                + "use place : " + file.getAbsolutePath()
                + "\nplease use Glide to prevent oom!";
    }
}
