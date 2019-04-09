package com.shanbay.lps;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class XmlFileScanner {

    public static Set<File> listXmlFiles(File dir) {
        Set<File> set = new HashSet<>();
        scanAndRecord(dir, set);
        return set;
    }

    /**
     * ***************************************************************************************************************
     * <p>
     * ***************************************************************************************************************
     */

    private static void scanAndRecord(File file, Set<File> set) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                scanAndRecord(f, set);
            }
        } else {
            set.add(file);
        }
    }
}
