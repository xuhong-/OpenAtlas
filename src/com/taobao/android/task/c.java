package com.taobao.android.task;

import java.io.File;
import java.io.FilenameFilter;

import java.io.File;
import java.io.FileFilter;

/* compiled from: SaturativeExecutor.java */
final class c implements FileFilter {
    c() {
    }

    public boolean accept(File file) {
        return SaturativeExecutor.PATTERN_CPU_ENTRIES.matcher(file.getName())
                .matches();
    }
}