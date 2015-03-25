package com.taobao.tao.atlaswrapper;

import android.os.Environment;
import android.taobao.atlas.framework.Atlas;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: AwbDebug.java */
class c {
    boolean a;
    private boolean b;
    private ArrayList<String> c;
    private final String d;

    public c() {
        this.b = false;
        this.a = false;
        this.c = new ArrayList();
        this.d = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/awb-debug";
        this.b = true;
    }

    public boolean a() {
        int i = 0;
        if (!this.b) {
            return false;
        }
        File file = new File(this.d);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            int length = listFiles.length;
            while (i < length) {
                File file2 = listFiles[i];
                if (file2.isFile() && file2.getName().endsWith(".so")) {
                    this.c.add(file2.getAbsolutePath());
                    // "found external awb " + file2.getAbsolutePath();
                    this.a = true;
                }
                i++;
            }
        }
        return this.a;
    }

    public boolean a(String str) {
        if (!this.b || this.c.size() <= 0) {
            return false;
        }
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            // "processLibsBundle filePath " + str2;
            if (str2.contains(k.getFileNameFromEntryName(str).substring(3))) {
                File file = new File(str2);
                String replace = k.getBaseFileName(file.getName()).replace("_",
                        ".");
                if (Atlas.getInstance().getBundle(replace) == null) {
                    try {
                        Atlas.getInstance().installBundle(replace, file);
                    } catch (Throwable th) {
                        Log.e("AwbDebug", "Could not install external bundle.",
                                th);
                    }
                    // "Succeed to install external bundle " + replace;
                }
                file.delete();
                return true;
            }
        }
        return false;
    }
}