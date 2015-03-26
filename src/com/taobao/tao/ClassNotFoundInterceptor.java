package com.taobao.tao;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.taobao.atlas.bundleInfo.BundleInfoList;
import android.taobao.atlas.runtime.ClassNotFoundInterceptorCallback;

import com.taobao.lightapk.BundleListing.Component;

/* compiled from: ClassNotFoundInterceptor.java */
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

    public Intent returnIntent(Intent intent) {
        Object obj = 1;
        Object obj2 = null;
        String className = intent.getComponent().getClassName();
        CharSequence dataString = intent.getDataString();
        if (className == null || !className.equals("com.taobao.tao.welcome.Welcome")) {
            String bundleForComponet = BundleInfoList.getInstance().getBundleForComponet(className);
         //   Atlas.getInstance().getBundle(intent.get)
//            if (b.sInternalBundles == null) {
//                b.instance().resolveInternalBundles();
//            }
//            if (b.sInternalBundles != null) {
//                if (b.sInternalBundles.contains(bundleForComponet) || Atlas.getInstance().getBundle(bundleForComponet) != null) {
//                    obj = null;
//                }
//                obj2 = obj;
//            } else if (Globals.isMiniPackage() || bundleForComponet.equalsIgnoreCase("com.duanqu.qupai.recorder")) {
//                obj2 = 1;
//            }
            if (obj2 != null) {
//                Component findBundleByActivity =Component();// b.instance().findBundleByActivity(className);
//                if (!(findBundleByActivity == null || Atlas.getInstance().getBundle(findBundleByActivity.getPkgName()) != null || GO_H5_BUNDLES_IF_NOT_EXISTS.contains(findBundleByActivity.getPkgName()))) {
//                    new Handler(Looper.getMainLooper()).post(new k(this, intent, className, findBundleByActivity));
//                }
            }
//            if (!TextUtils.isEmpty(dataString)) {
//                Nav.from(Globals.getApplication()).withCategory(c.BROWSER_ONLY_CATEGORY).withExtras(intent.getExtras()).toUri(intent.getData());
//            }
        }
        return intent;
    }
    public static final String KEY_ACTIVITY = "lightapk_activity";
    public static final String KEY_BUNDLE_PKG = "lightapk_pkg";
    class k implements Runnable {
        final /* synthetic */ Intent a;
        final /* synthetic */ String b;
        final /* synthetic */ Component c;
        final /* synthetic */ ClassNotFoundInterceptor d;

        k(ClassNotFoundInterceptor jVar, Intent intent, String str, Component aVar) {
            this.d = jVar;
            this.a = intent;
            this.b = str;
            this.c = aVar;
        }

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