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
package blue.stack.atlaswrapper;

import org.osgi.framework.Bundle;

import com.openAtlas.framework.Atlas;
import com.openAtlas.framework.BundleImpl;
import com.openAtlas.framework.bundlestorage.BundleArchiveRevision.DexLoadException;

import android.app.Application;
import android.content.Intent;
import android.util.Log;


public class OptDexProcess {
    private static OptDexProcess optDexProcess;
    private Application mApplication;
    private boolean isInited;
    private boolean isExecuted;

    OptDexProcess() {
    }

    public static synchronized OptDexProcess getInstance() {
  
        synchronized (OptDexProcess.class) {
            if (optDexProcess == null) {
                optDexProcess = new OptDexProcess();
            }
           
        }
        return optDexProcess;
    }

    void init(Application application) {
        this.mApplication = application;
        this.isInited = true;
    }

    public synchronized void processPackages() {
        if (!this.isInited) {
            Log.e("OptDexProcess",
                    "Bundle Installer not initialized yet, process abort!");
        } else if (this.isExecuted) {
            Log.i("OptDexProcess",
                    "Bundle install already executed, just return");
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            install();
            // .. "Install bundles not delayed cost time = " +
            // (System.currentTimeMillis() - currentTimeMillis) + " ms";
            Utils.saveAtlasInfoBySharedPreferences(this.mApplication);
            System.setProperty("BUNDLES_INSTALLED", "true");
            notifyInstalled();
            currentTimeMillis = System.currentTimeMillis();
            getInstance().installDely();
            // "Install delayed bundles cost time = " +
            // (System.currentTimeMillis() - currentTimeMillis) + " ms";
            this.isExecuted = true;
        }
    }

    private void install() {
        for (Bundle bundle : Atlas.getInstance().getBundles()) {
            if (!(bundle == null || isContanins(Utils.STORE, bundle.getLocation()))) {
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

    private void notifyInstalled() {
        this.mApplication.sendBroadcast(new Intent(
                "com.taobao.taobao.action.BUNDLES_INSTALLED"));
    }

    private void installDely() {
        for (String bundle : Utils.STORE) {
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

    private boolean isContanins(String[] strArr, String str) {
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
