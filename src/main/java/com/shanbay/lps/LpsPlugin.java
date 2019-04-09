package com.shanbay.lps;

import groovy.lang.Closure;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class LpsPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().create("lpsConfig", LpsConfig.class);

        project.afterEvaluate(new Closure(this) {
            public void doCall() {
                Task preBuild = project.getTasks().findByName("preBuild");
                ScanTask scanTask = project.getTasks().create("largePictureScan", ScanTask.class);
                preBuild.dependsOn(scanTask);
            }
        });
    }
}
