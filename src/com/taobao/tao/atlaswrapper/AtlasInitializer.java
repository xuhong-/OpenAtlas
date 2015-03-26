package com.taobao.tao.atlaswrapper;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Properties;

import test.blue.stack.loader.g;

import com.taobao.android.task.Coordinator;
import com.taobao.tao.ClassNotFoundInterceptor;
import com.taobao.tao.Globals;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.taobao.atlas.framework.Atlas;
import android.taobao.atlas.runtime.ClassNotFoundInterceptorCallback;
import android.text.TextUtils;
import android.util.Log;

public class AtlasInitializer {
    private static long a;
    private static boolean g;
    private Application b;
    private String c;
    private c d;
    private MiniPackage e;
    private String f;
    private boolean h;
    private boolean i;

    public AtlasInitializer(Application application, String str) {
        this.f = "bundleinfo";
        this.i = false;
        this.b = application;
        this.c = str;
        if (application.getPackageName().equals(str)) {
            g = true;
        }
    }

    public void injectApplication() {
        try {
            Atlas.getInstance().injectApplication(this.b,
                    this.b.getPackageName());
        } catch (Exception e) {
            throw new RuntimeException("atlas inject mApplication fail"
                    + e.getMessage());
        }
    }

    public void init() {
        a = System.currentTimeMillis();
        Properties properties = new Properties();
        properties.put("android.taobao.atlas.welcome",
                "com.taobao.tao.welcome.Welcome");
        properties.put("android.taobao.atlas.debug.bundles", "true");
        properties.put("android.taobao.atlas.AppDirectory", this.b
                .getFilesDir().getParent());
        this.e = new MiniPackage(this.b);
        this.e.a(properties);
        try {
            Field declaredField = Globals.class
                    .getDeclaredField("sApplication");
            declaredField.setAccessible(true);
            declaredField.set(null, this.b);
            try {
                Atlas.getInstance().init(this.b, properties);
                // "Atlas framework inited " + (System.currentTimeMillis() - Component)
                // + " ms";
                try {
                    declaredField = Globals.class
                            .getDeclaredField("sClassLoader");
                    declaredField.setAccessible(true);
                    declaredField.set(null, Atlas.getInstance()
                            .getDelegateClassLoader());
                    this.d = new c();
                    this.i = c();
                    if (this.i) {
                        File file = new File(this.b.getFilesDir()
                                + File.separator + "bundleBaseline"
                                + File.separator + "baselineInfo");
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                    if (this.b.getPackageName().equals(this.c)) {
                        // if (!(Versions.isDebug() || b() ||
                        // !ApkUtils.isRootSystem())) {
                        // properties.put("android.taobao.atlas.publickey",
                        // SecurityFrameListener.PUBLIC_KEY);
                        // Atlas.getInstance().addFrameworkListener(new SecurityFrameListener());
                        // }
                        // if (this.i || this.d.a()) {
                        // properties.put("osgi.init", "true");
                        // }
                    }
                    // "Atlas framework starting in process " + this.c + " " +
                    // (System.currentTimeMillis() - Component) + " ms";
                    if (!Utils.searchFile(this.b.getFilesDir().getParentFile()
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
        BundlesInstaller a = BundlesInstaller.a();
        OptDexProcess instance = OptDexProcess.getInstance();
        if (this.b.getPackageName().equals(this.c) && (this.i || this.d.a())) {
            a.a(this.b, this.e, this.d, g);
            instance.a(this.b);
        }
        // "Atlas framework begin to start in process " + this.c + " " +
        // (System.currentTimeMillis() - Component) + " ms";
      Atlas.getInstance().setClassNotFoundInterceptorCallback(new ClassNotFoundInterceptor());
        try {
            Atlas.getInstance().startup();
            Coordinator.postTask(new b(this, "AtlasStartup", a, instance));
        } catch (Throwable e) {
            Log.e("AtlasInitializer", "Could not start up atlas framework !!!",
                    e);
            throw new RuntimeException("atlas startUp fail " + e);
        }
    }

    // private void Component() {
    // BundleListing bundleListing = b.instance().getBundleListing();
    // if (bundleListing != null && bundleListing.getBundles() != null) {
    // LinkedList linkedList = new LinkedList();
    // for (com.taobao.lightapk.BundleListing.Component aVar :
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
            PackageInfo packageInfo = this.b.getPackageManager()
                    .getPackageInfo(this.b.getPackageName(), 0);
            SharedPreferences sharedPreferences = this.b.getSharedPreferences(
                    "atlas_configs", 0);
            int i = sharedPreferences.getInt("last_version_code", 0);
            CharSequence string = sharedPreferences.getString(
                    "last_version_name", "");
            SharedPreferences sharedPreferences2 = this.b.getSharedPreferences(
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

    void a(BundlesInstaller dVar, OptDexProcess hVar) {
        // "Atlas framework started in process " + this.c + " " +
        // (System.currentTimeMillis() - Component) + " ms";
        if (this.b.getPackageName().equals(this.c) && (this.i || this.d.a())) {
            if (InstallSolutionConfig.install_when_oncreate) {
                dVar.process();
                hVar.processPackages();
                return;
            }
            System.setProperty("BUNDLES_INSTALLED", "true");
            this.b.sendBroadcast(new Intent(
                    "com.taobao.taobao.action.BUNDLES_INSTALLED"));
            dVar.UpdatePackageVersion();
        } else if (!this.i && this.b.getPackageName().equals(this.c)) {
            System.setProperty("BUNDLES_INSTALLED", "true");
            this.b.sendBroadcast(new Intent(
                    "com.taobao.taobao.action.BUNDLES_INSTALLED"));
        }
    }
}
