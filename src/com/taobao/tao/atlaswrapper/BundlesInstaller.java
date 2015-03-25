package com.taobao.tao.atlaswrapper;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.taobao.atlas.framework.Atlas;
import android.taobao.atlas.runtime.RuntimeVariables;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Bundle;

/* compiled from: BundlesInstaller BundlesInstaller.java d*/
public class BundlesInstaller {
    private static boolean g;
    private static BundlesInstaller h;
    c a;
    private Application b;
    private MiniPackage c;
    private PackageInfo d;
    private boolean e;
    private boolean f;

    BundlesInstaller() {
    }

    void a(Application application, MiniPackage gVar, c cVar, boolean z) {
        this.b = application;
        this.c = gVar;
        this.a = cVar;
        g = z;
        this.d = k.getPackageInfo(application);
        this.e = true;
    }

    static synchronized BundlesInstaller a() {
        BundlesInstaller dVar;
        synchronized (BundlesInstaller.class) {
            if (h == null) {
                h = new BundlesInstaller();
            }
            dVar = h;
        }
        return dVar;
    }

    public synchronized void process() {
        ZipFile zipFile;
        Throwable e;
        if (!this.e) {
            Log.e("BundlesInstaller",
                    "Bundle Installer not initialized yet, process abort!");
        } else if (this.f) {
            Log.i("BundlesInstaller",
                    "Bundle install already executed, just return");
        } else {
            try {
                zipFile = new ZipFile(this.b.getApplicationInfo().sourceDir);
                List a = a(zipFile, "lib/armeabi/libcom_", ".so");
                if (a != null && a.size() > 0
                        && b() < ((long) (((a.size() * 2) * 1024) * 1024))) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(
                                    RuntimeVariables.androidApplication,
                                    "\u68c0\u6d4b\u5230\u624b\u673a\u5b58\u50a8\u7a7a\u95f4\u4e0d\u8db3\uff0c\u4e3a\u4e0d\u5f71\u54cd\u60a8\u7684\u4f7f\u7528\u8bf7\u6e05\u7406\uff01",
                                    1).show();

                        }
                    });
                }
                a(zipFile, a, this.b);
                UpdatePackageVersion();
                if (zipFile != null) {
                    try {
                        zipFile.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (IOException e5) {
                e = e5;
                zipFile = null;
                Log.e("BundlesInstaller",
                        "IOException while processLibsBundles >>>", e);
                this.f = true;
            } catch (Throwable th2) {
                e = th2;
                zipFile = null;
            }
            this.f = true;
        }
    }

    public void UpdatePackageVersion() {
        if (this.e) {
            SharedPreferences sharedPreferences = this.b.getSharedPreferences(
                    "atlas_configs", 0);
            this.c.a(sharedPreferences, this.d);
            Editor edit = sharedPreferences.edit();
            edit.putInt("last_version_code", this.d.versionCode);
            edit.putString("last_version_name", this.d.versionName);
            edit.putString(this.d.versionName, "dexopt");
            edit.commit();
            return;
        }
        Log.e("BundlesInstaller",
                "Bundle Installer not initialized yet, process abort!");
    }

    private List<String> a(ZipFile zipFile, String str, String str2) {
        List<String> arrayList = new ArrayList();
        try {
            Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                String name = ((ZipEntry) entries.nextElement()).getName();
                if (name.startsWith(str) && name.endsWith(str2)) {
                    arrayList.add(name);
                }
            }
        } catch (Throwable e) {
            Log.e("BundlesInstaller",
                    "Exception while get bundles in assets or lib", e);
        }
        return arrayList;
    }

    private long b() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getAvailableBlocks())
                * ((long) statFs.getBlockSize());
    }

    private void a(ZipFile zipFile, List<String> list, Application application) {
        int i = 0;
        for (String replace : k.a) {
            String replace2 = a(list, replace.replace(".", "_"));
            if (replace2 != null && replace2.length() > 0) {
                a(zipFile, replace2, application);
                list.remove(replace2);
            }
        }
        for (String a : list) {
            a(zipFile, a, application);
        }
        if (g) {
            String[] strArr = k.b;
            int length = strArr.length;
            while (i < length) {
                Bundle bundle = Atlas.getInstance().getBundle(strArr[i]);
                if (bundle != null) {
                    try {
                        bundle.start();
                    } catch (Throwable e) {
                        Log.e("BundlesInstaller",
                                "Could not auto start bundle: "
                                        + bundle.getLocation(), e);
                    }
                }
                i++;
            }
        }
    }

    private String a(List<String> list, String str) {
        if (list == null || str == null) {
            return null;
        }
        for (String str2 : list) {
            if (str2.contains(str)) {
                return str2;
            }
        }
        return null;
    }

    private boolean a(ZipFile zipFile, String str, Application application) {
        // "processLibsBundle entryName " + str;
        this.a.a(str);
        String fileNameFromEntryName = k.getFileNameFromEntryName(str);
        String packageNameFromEntryName = k.getPackageNameFromEntryName(str);
        if (packageNameFromEntryName == null
                || packageNameFromEntryName.length() <= 0) {
            return false;
        }
        File file = new File(new File(
                application.getFilesDir().getParentFile(), "lib"),
                fileNameFromEntryName);
        if (Atlas.getInstance().getBundle(packageNameFromEntryName) != null) {
            return false;
        }
        try {
            if (file.exists()) {
                Atlas.getInstance().installBundle(packageNameFromEntryName,
                        file);
            } else {
                Atlas.getInstance().installBundle(packageNameFromEntryName,
                        zipFile.getInputStream(zipFile.getEntry(str)));
            }
            // "Succeed to install bundle " + packageNameFromEntryName;
            return true;
        } catch (Throwable e) {
            Log.e("BundlesInstaller", "Could not install bundle.", e);
            return false;
        }
    }
}