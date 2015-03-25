package com.taobao.tao.atlaswrapper;

import android.app.Application;
import android.content.Intent;
import android.taobao.atlas.framework.Atlas;
import android.taobao.atlas.framework.BundleImpl;
import android.taobao.atlas.framework.bundlestorage.BundleArchiveRevision.DexLoadException;
import android.util.Log;

import org.osgi.framework.Bundle;

/* compiled from: OptDexProcess.java  h */
public class OptDexProcess {
    private static OptDexProcess a;
    private Application b;
    private boolean c;
    private boolean d;

    OptDexProcess() {
    }

    public static synchronized OptDexProcess getInstance() {
        OptDexProcess hVar;
        synchronized (OptDexProcess.class) {
            if (a == null) {
                a = new OptDexProcess();
            }
            hVar = a;
        }
        return hVar;
    }

    void a(Application application) {
        this.b = application;
        this.c = true;
    }

    public synchronized void processPackages() {
        if (!this.c) {
            Log.e("OptDexProcess",
                    "Bundle Installer not initialized yet, process abort!");
        } else if (this.d) {
            Log.i("OptDexProcess",
                    "Bundle install already executed, just return");
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            a();
            // .. "Install bundles not delayed cost time = " +
            // (System.currentTimeMillis() - currentTimeMillis) + " ms";
            k.saveAtlasInfoBySharedPreferences(this.b);
            System.setProperty("BUNDLES_INSTALLED", "true");
            b();
            currentTimeMillis = System.currentTimeMillis();
            getInstance().c();
            // "Install delayed bundles cost time = " +
            // (System.currentTimeMillis() - currentTimeMillis) + " ms";
            this.d = true;
        }
    }

    private void a() {
        for (Bundle bundle : Atlas.getInstance().getBundles()) {
            if (!(bundle == null || a(k.c, bundle.getLocation()))) {
                try {
                    ((BundleImpl) bundle).optDexFile();
                    Atlas.getInstance().enableComponent(bundle.getLocation());
                } catch (Throwable e) {
                    if (e instanceof DexLoadException) {
                        throw ((RuntimeException) e);
                    }
                    Log.e("OptDexProcess", "Error while dexopt >>>", e);
                }
            }
        }
    }

    private void b() {
        this.b.sendBroadcast(new Intent(
                "com.taobao.taobao.action.BUNDLES_INSTALLED"));
    }

    private void c() {
        for (String bundle : k.c) {
            Bundle bundle2 = Atlas.getInstance().getBundle(bundle);
            if (bundle2 != null) {
                try {
                    ((BundleImpl) bundle2).optDexFile();
                    Atlas.getInstance().enableComponent(bundle2.getLocation());
                } catch (Throwable e) {
                    if (e instanceof DexLoadException) {
                        throw ((RuntimeException) e);
                    }
                    Log.e("OptDexProcess", "Error while dexopt >>>", e);
                }
            }
        }
    }

    private boolean a(String[] strArr, String str) {
        if (strArr == null || str == null) {
            return false;
        }
        for (String str2 : strArr) {
            if (str2 != null && str2.equals(str)) {
                return true;
            }
        }
        return false;
    }
}