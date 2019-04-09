package com.shanbay.lps;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.AndroidSourceSet;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.Set;

public class ScanTask extends DefaultTask {

    private LargePictureFinder mLargePictureFinder;

    @TaskAction
    public void scan() {
        mLargePictureFinder = new LargePictureFinder(getProject());

        AppExtension appExtension = getProject().getExtensions().findByType(AppExtension.class);

        for (AndroidSourceSet sourceSet : appExtension.getSourceSets()) {
            Set<File> resSourceDirs = sourceSet.getRes().getSrcDirs();

            if (resSourceDirs.isEmpty()) {
                continue;
            }

            for (File resSourceDir : resSourceDirs) {
                Set<PictureInfo> largePictures = mLargePictureFinder.getLargePictures(resSourceDir);

                if (largePictures.isEmpty()) {
                    continue;
                }

                Set<File> xmlFileSet = XmlFileScanner.listXmlFiles(resSourceDir);

                for (File xml : xmlFileSet) {
                    WrongUseDetector.detect(xml, largePictures);
                }
            }

        }

    }
}
