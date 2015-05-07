/**
 *  OpenAtlasForAndroid Project
The MIT License (MIT) Copyright (OpenAtlasForAndroid) 2015 Bunny Blue,achellies

Permission is hereby granted, free of charge, to any person obtaining a copy of this software
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
import java.lang.reflect.Field;
import java.util.Properties;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import android.util.Log;
import blue.stack.openAtlas.ClassNotFoundInterceptor;
import blue.stack.openAtlas.Globals;
import blue.stack.openAtlas.PlatformConfigure;
import blue.stack.openAtlas.android.task.Coordinator;

import com.openAtlas.framework.Atlas;

public class AtlasInitializer {
    private static long a;
    private static boolean isAppPkg;
    private Application mApplication;
    private String mPkgName;
    private AwbDebug awDebug;
    private MiniPackage miniPackage;
    private String f;
    private boolean h;
    private boolean i;

    public AtlasInitializer(Application application, String mPkgName) {
        this.f = "bundleinfo";
        this.i = false;
        this.mApplication = application;
        this.mPkgName = mPkgName;
        if (application.getPackageName().equals(mPkgName)) {
            isAppPkg = true;
        }
    }

    public void injectApplication() {
        try {
            Atlas.getInstance().injectApplication(this.mApplication,
                    this.mApplication.getPackageName());
        } catch (Exception e) {
            throw new RuntimeException("atlas inject mApplication fail"
                    + e.getMessage());
        }
    }

    public void init() {
        a = System.currentTimeMillis();
        Properties properties = new Properties();
        properties.put("com.openAtlas.welcome",
                PlatformConfigure.BOOT_ACTIVITY);
        properties.put("com.openAtlas.debug.bundles", "true");
        properties.put("com.openAtlas.AppDirectory", this.mApplication
                .getFilesDir().getParent());
        this.miniPackage = new MiniPackage(this.mApplication);
        this.miniPackage.init(properties);
        try {
            Field declaredField = Globals.class
                    .getDeclaredField("sApplication");
            declaredField.setAccessible(true);
            declaredField.set(null, this.mApplication);
            try {
                Atlas.getInstance().init(this.mApplication, properties);
                // "Atlas framework inited " + (System.currentTimeMillis() - Component)
                // + " ms";
                try {
                    declaredField = Globals.class
                            .getDeclaredField("sClassLoader");
                    declaredField.setAccessible(true);
                    declaredField.set(null, Atlas.getInstance()
                            .getDelegateClassLoader());
                    this.awDebug = new AwbDebug();
                    this.i = c();
                    if (this.i) {
                        File file = new File(this.mApplication.getFilesDir()
                                + File.separator + "bundleBaseline"
                                + File.separator + "baselineInfo");
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                    if (this.mApplication.getPackageName().equals(this.mPkgName)) {
                        // if (!(Versions.isDebug() || mOptDexProcess() ||
                        // !ApkUtils.isRootSystem())) {
                        // properties.put("com.openAtlas.publickey",
                        // SecurityFrameListener.PUBLIC_KEY);
                        // Atlas.getInstance().addFrameworkListener(new SecurityFrameListener());
                        // }
                        // if (this.i || this.d.a()) {
                        // properties.put("osgi.init", "true");
                        // }
                    }
                    // "Atlas framework starting in process " + this.c + " " +
                    // (System.currentTimeMillis() - Component) + " ms";
                    if (!Utils.searchFile(this.mApplication.getFilesDir().getParentFile()
                            + "/lib", "libcom_taobao")) {
                        InstallSolutionConfig.install_when_oncreate = true;
                    }
                    // if (InstallSolutionConfig.install_when_findclass) {
                    // Component();
                    // }
                } catch (Throwable e) {
                    Log.e("AtlasInitializer",
                            "Could not set  Globals.sClassLoader !!!", e);
                    throw new RuntimeException(
                            "Could not set  Globals.sClassLoader !!!", e);
                }
            } catch (Throwable e2) {
                Log.e("AtlasInitializer", "Could not init atlas framework !!!",
                        e2);
                throw new RuntimeException("atlas initialization fail"
                        + e2.getMessage());
            }
        } catch (Throwable e22) {
            Log.e("AtlasInitializer", "Could not set Globals.sApplication !!!",
                    e22);
            throw new RuntimeException(
                    "Could not set Globals.sApplication !!!", e22);
        }
    }

    public void startUp() {
        BundlesInstaller mBundlesInstaller = BundlesInstaller.getInstance();
        OptDexProcess instance = OptDexProcess.getInstance();
        if (this.mApplication.getPackageName().equals(this.mPkgName) && (this.i || this.awDebug.init())) {
            mBundlesInstaller.init(this.mApplication, this.miniPackage, this.awDebug, isAppPkg);
            instance.init(this.mApplication);
        }
   
      Atlas.getInstance().setClassNotFoundInterceptorCallback(new ClassNotFoundInterceptor());
        try {
            Atlas.getInstance().startup();
            Coordinator.postTask(new StartupRunnable(this, "AtlasStartup", mBundlesInstaller, instance));
        } catch (Throwable e) {
            Log.e("AtlasInitializer", "Could not start up atlas framework !!!",
                    e);
            throw new RuntimeException("atlas startUp fail " + e);
        }
    }

    // private void Component() {
    // BundleListing bundleListing = mOptDexProcess.instance().getBundleListing();
    // if (bundleListing != null && bundleListing.getBundles() != null) {
    // LinkedList linkedList = new LinkedList();
    // for (blue.stack.openAtlas.lightapk.BundleListing.Component aVar :
    // bundleListing.getBundles()) {
    // if (aVar != null) {
    // BundleInfo bundleInfo = new BundleInfo();
    // List arrayList = new ArrayList();
    // if (aVar.getActivities() != null) {
    // arrayList.addAll(aVar.getActivities());
    // }
    // if (aVar.getServices() != null) {
    // arrayList.addAll(aVar.getServices());
    // }
    // if (aVar.getReceivers() != null) {
    // arrayList.addAll(aVar.getReceivers());
    // }
    // if (aVar.getContentProviders() != null) {
    // arrayList.addAll(aVar.getContentProviders());
    // }
    // bundleInfo.hasSO = aVar.isHasSO();
    // bundleInfo.bundleName = aVar.getPkgName();
    // bundleInfo.Components = arrayList;
    // bundleInfo.DependentBundles = aVar.getDependency();
    // linkedList.add(bundleInfo);
    // }
    // }
    // BundleInfoList.getInstance().init(linkedList);
    // }
    // }
    private boolean c() {
        try {
            PackageInfo packageInfo = this.mApplication.getPackageManager()
                    .getPackageInfo(this.mApplication.getPackageName(), 0);
            SharedPreferences sharedPreferences = this.mApplication.getSharedPreferences(
                    "atlas_configs", 0);
            int i = sharedPreferences.getInt("last_version_code", 0);
            CharSequence string = sharedPreferences.getString(
                    "last_version_name", "");
            SharedPreferences sharedPreferences2 = this.mApplication.getSharedPreferences(
                    "atlas_configs", 0);
            CharSequence string2 = sharedPreferences2.getString(
                    "isMiniPackage", "");
            this.h = !String.valueOf(Globals.isMiniPackage()).equals(string2);
            // "resetForOverrideInstall = " + this.h;
            if (TextUtils.isEmpty(string2) || this.h) {
                Editor edit = sharedPreferences2.edit();
                edit.clear();
                edit.putString("isMiniPackage",
                        String.valueOf(Globals.isMiniPackage()));
                edit.commit();
            }
            if (packageInfo.versionCode > i
                    || ((packageInfo.versionCode == i && !TextUtils.equals(
                            Globals.getInstalledVersionName(), string)) || this.h)) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            Log.e("AtlasInitializer", "Error to get PackageInfo >>>", e);
            throw new RuntimeException(e);
        }
    }

    void process(BundlesInstaller dVar, OptDexProcess hVar) {

        if (this.mApplication.getPackageName().equals(this.mPkgName) && (this.i || this.awDebug.init())) {
            if (InstallSolutionConfig.install_when_oncreate) {
                dVar.process();
                hVar.processPackages();
                return;
            }
            System.setProperty("BUNDLES_INSTALLED", "true");
            this.mApplication.sendBroadcast(new Intent(
                    PlatformConfigure.ACTION_BROADCAST_BUNDLES_INSTALLED));
            dVar.UpdatePackageVersion();
        } else if (!this.i && this.mApplication.getPackageName().equals(this.mPkgName)) {
            System.setProperty("BUNDLES_INSTALLED", "true");
            this.mApplication.sendBroadcast(new Intent(
            		PlatformConfigure.ACTION_BROADCAST_BUNDLES_INSTALLED));
        }
    }
}
