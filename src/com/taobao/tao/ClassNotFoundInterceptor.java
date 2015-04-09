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
package com.taobao.tao;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;

import com.openAtlas.bundleInfo.BundleInfoList;
import com.openAtlas.runtime.ClassNotFoundInterceptorCallback;
import com.taobao.lightapk.BundleListing.Component;

public class ClassNotFoundInterceptor implements ClassNotFoundInterceptorCallback {
    public static final List<String> GO_H5_BUNDLES_IF_NOT_EXISTS;
    public final String TAG;

    public ClassNotFoundInterceptor() {
        this.TAG = "ClassNotFundInterceptor";
    }

    static {
        GO_H5_BUNDLES_IF_NOT_EXISTS = new ArrayList();
    }

    public static void addGoH5BundlesIfNotExists(String str) {
        if (!GO_H5_BUNDLES_IF_NOT_EXISTS.contains(str)) {
            GO_H5_BUNDLES_IF_NOT_EXISTS.add(str);
        }
    }

    public static void resetGoH5BundlesIfNotExists() {
        GO_H5_BUNDLES_IF_NOT_EXISTS.clear();
    }

    @Override
	public Intent returnIntent(Intent intent) {
        Object obj = 1;
        Object obj2 = null;
        String className = intent.getComponent().getClassName();
        CharSequence dataString = intent.getDataString();
        if (className == null || !className.equals("com.taobao.tao.welcome.Welcome")) {
            String bundleForComponet = BundleInfoList.getInstance().getBundleForComponet(className);
         //   Atlas.getInstance().getBundle(intent.get)
//            if (mOptDexProcess.sInternalBundles == null) {
//                mOptDexProcess.instance().resolveInternalBundles();
//            }
//            if (mOptDexProcess.sInternalBundles != null) {
//                if (mOptDexProcess.sInternalBundles.contains(bundleForComponet) || Atlas.getInstance().getBundle(bundleForComponet) != null) {
//                    obj = null;
//                }
//                obj2 = obj;
//            } else if (Globals.isMiniPackage() || bundleForComponet.equalsIgnoreCase("com.duanqu.qupai.recorder")) {
//                obj2 = 1;
//            }
            if (obj2 != null) {
//                Component findBundleByActivity =Component();// mOptDexProcess.instance().findBundleByActivity(className);
//                if (!(findBundleByActivity == null || Atlas.getInstance().getBundle(findBundleByActivity.getPkgName()) != null || GO_H5_BUNDLES_IF_NOT_EXISTS.contains(findBundleByActivity.getPkgName()))) {
//                    new Handler(Looper.getMainLooper()).post(new BootRunnable(this, intent, className, findBundleByActivity));
//                }
            }
//            if (!TextUtils.isEmpty(dataString)) {
//                Nav.from(Globals.getApplication()).withCategory(AwbDebug.BROWSER_ONLY_CATEGORY).withExtras(intent.getExtras()).toUri(intent.getData());
//            }
        }
        return intent;
    }
    public static final String KEY_ACTIVITY = "lightapk_activity";
    public static final String KEY_BUNDLE_PKG = "lightapk_pkg";
    class BootRunnable implements Runnable {
        final  Intent a;
        final  String b;
        final  Component c;
        final  ClassNotFoundInterceptor d;

        BootRunnable(ClassNotFoundInterceptor jVar, Intent intent, String str, Component aVar) {
            this.d = jVar;
            this.a = intent;
            this.b = str;
            this.c = aVar;
        }

        @Override
		public void run() {
            Intent intent = new Intent();
            if (this.a.getExtras() != null) {
                intent.putExtras(this.a.getExtras());
            }
            intent.putExtra(KEY_ACTIVITY, this.b);
            intent.putExtra(KEY_BUNDLE_PKG, this.c.getPkgName());
            intent.setData(this.a.getData());
            intent.setFlags(268435456);
            intent.setClass(Globals.getApplication(), BundleNotFoundActivity.class);
            Globals.getApplication().startActivity(intent);
        }
    }
}