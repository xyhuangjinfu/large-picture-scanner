package com.shanbay.lps;

import org.gradle.api.Project;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class LargePictureFinder {

    private Set<PictureInfo> mLargePictureSet = new HashSet<>();

    private LpsConfig mLpsConfig;

    public LargePictureFinder(Project project) {
        mLpsConfig = project.getExtensions().findByType(LpsConfig.class);
        if (mLpsConfig == null) {
            mLpsConfig = new LpsConfig();
        }
    }

    public Set<PictureInfo> getLargePictures(File dir) {
        mLargePictureSet.clear();

        traverseDir(dir);

        return new HashSet<>(mLargePictureSet);
    }

    /**
     * ***************************************************************************************************************
     * <p>
     * ***************************************************************************************************************
     */

    private void traverseDir(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                traverseDir(f);
            }
        } else {
            if (file.getName().endsWith(".png")) {
                PictureInfo pictureInfo = PictureInfoReader.readPng(file);
                handlePictureInfo(pictureInfo);
            }
            if (file.getName().endsWith(".webp")) {
                PictureInfo pictureInfo = PictureInfoReader.readWebp(file);
                handlePictureInfo(pictureInfo);
            }
        }
    }

    private void handlePictureInfo(PictureInfo pictureInfo) {
        if (pictureInfo == null) {
            return;
        }

        long dimension = pictureInfo.mWidth * pictureInfo.mHeight;
        if (dimension >= mLpsConfig.getDimensionLimit()) {
            mLargePictureSet.add(pictureInfo);
        }
    }
}
