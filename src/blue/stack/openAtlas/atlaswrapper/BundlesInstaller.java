/**
 *  OpenAtlasForAndroid Project
The MIT License (MIT) Copyright (OpenAtlasForAndroid) 2015 Bunny Blue,achellies

Permission is hereby granted, free of charge, to any person obtaining mApp copy of this software
and associated documentation files (the "Software"), to deal in the Software 
without restriction, including without limitation the rights to use, copy, modify, 
merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies 
or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
@author BunnyBlue
 * **/
package blue.stack.openAtlas.atlaswrapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Bundle;

import com.openAtlas.framework.Atlas;
import com.openAtlas.runtime.RuntimeVariables;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;


public class BundlesInstaller {
    private static boolean isAppPkg;
    private static BundlesInstaller mBundlesInstaller;
    AwbDebug a;
    private Application mApplication;
    private MiniPackage miniPackage;
    private PackageInfo mPackageInfo;
    private boolean isInited;
    private boolean f;

    BundlesInstaller() {
    }

    void init(Application application, MiniPackage miniPackage, AwbDebug cVar, boolean isAppPkg) {
        this.mApplication = application;
        this.miniPackage = miniPackage;
        this.a = cVar;
        isAppPkg = isAppPkg;
        this.mPackageInfo = Utils.getPackageInfo(application);
        this.isInited = true;
    }

    static synchronized BundlesInstaller getInstance() {
   
        synchronized (BundlesInstaller.class) {
            if (mBundlesInstaller == null) {
                mBundlesInstaller = new BundlesInstaller();
            }
           
        }
        return mBundlesInstaller;
    }

    public synchronized void process() {
        ZipFile zipFile;
        Throwable e;
        if (!this.isInited) {
            Log.e("BundlesInstaller",
                    "Bundle Installer not initialized yet, process abort!");
        } else if (this.f) {
            Log.i("BundlesInstaller",
                    "Bundle install already executed, just return");
        } else {
            try {
                zipFile = new ZipFile(this.mApplication.getApplicationInfo().sourceDir);
                List mFileList = getFileList(zipFile, "lib/armeabi/libcom_", ".so");
                if (mFileList != null && mFileList.size() > 0
                        && getSpace() < (((mFileList.size() * 2) * 1024) * 1024)) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(
                                    RuntimeVariables.androidApplication,
                                    "检测到手机存储空间不足，为不影响您的使用请清理！",
                                    1).show();

                        }
                    });
                }
                install(zipFile, mFileList, this.mApplication);
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
        if (this.isInited) {
            SharedPreferences sharedPreferences = this.mApplication.getSharedPreferences(
                    "atlas_configs", 0);
            this.miniPackage.a(sharedPreferences, this.mPackageInfo);
            Editor edit = sharedPreferences.edit();
            edit.putInt("last_version_code", this.mPackageInfo.versionCode);
            edit.putString("last_version_name", this.mPackageInfo.versionName);
            edit.putString(this.mPackageInfo.versionName, "dexopt");
            edit.commit();
            return;
        }
        Log.e("BundlesInstaller",
                "Bundle Installer not initialized yet, process abort!");
    }

    private List<String> getFileList(ZipFile zipFile, String mPref, String mSuffix) {
        List<String> arrayList = new ArrayList();
        try {
            Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                String name = ((ZipEntry) entries.nextElement()).getName();
                if (name.startsWith(mPref) && name.endsWith(mSuffix)) {
                    arrayList.add(name);
                }
            }
        } catch (Throwable e) {
            Log.e("BundlesInstaller",
                    "Exception while get bundles in assets or lib", e);
        }
        return arrayList;
    }

    private long getSpace() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getAvailableBlocks())
                * ((long) statFs.getBlockSize());
    }

    private void install(ZipFile zipFile, List<String> list, Application application) {
        int i = 0;
        for (String replace : Utils.DELAY) {
            String replace2 = isContains(list, replace.replace(".", "_"));
            if (replace2 != null && replace2.length() > 0) {
                install(zipFile, replace2, application);
                list.remove(replace2);
            }
        }
        for (String a : list) {
            install(zipFile, a, application);
        }
        if (isAppPkg) {
            String[] strArr = Utils.AUTO;
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

    private String isContains(List<String> list, String str) {
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

    private boolean install(ZipFile zipFile, String str, Application application) {
        // "processLibsBundle entryName " + str;
        this.a.installBundle(str);
        String fileNameFromEntryName = Utils.getFileNameFromEntryName(str);
        String packageNameFromEntryName = Utils.getPackageNameFromEntryName(str);
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
