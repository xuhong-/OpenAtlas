//package com.taobao.tao.atlaswrapper;
//
//import android.annotation.SuppressLint;
//import android.content.SharedPreferences.Editor;
//import android.os.AsyncTask;
//import android.os.Build.VERSION;
//import android.os.Handler;
//import android.os.Message;
//import android.os.Process;
//import android.taobao.atlas.framework.Atlas;
//import android.taobao.atlas.runtime.RuntimeVariables;
//import android.taobao.atlas.util.ApkUtils;
//import android.taobao.atlas.util.StringUtils;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.io.File;
//import java.util.List;
//
//import org.osgi.framework.Bundle;
//import org.osgi.framework.FrameworkEvent;
//import org.osgi.framework.FrameworkListener;
//
///* compiled from: SecurityFrameListener.java */
//public class j implements FrameworkListener {
//    public static final String PUBLIC_KEY = "30819f300d06092a864886f70d010101050003818d00308189028181008406125f369fde2720f7264923a63dc48e1243c1d9783ed44d8c276602d2d570073d92c155b81d5899e9a8a97e06353ac4b044d07ca3e2333677d199e0969c96489f6323ed5368e1760731704402d0112c002ccd09a06d27946269a438fe4b0216b718b658eed9d165023f24c6ddaec0af6f47ada8306ad0c4f0fcd80d9b69110203010001";
//    b a;
//
//    /* compiled from: SecurityFrameListener.java */
//    private class a extends AsyncTask<String, Void, Boolean> {
//        final /* synthetic */ j a;
//
//        private a(j jVar) {
//            this.a = jVar;
//        }
//
//        protected /* synthetic */ Object doInBackground(Object[] objArr) {
//            return a((String[]) objArr);
//        }
//
//        protected /* synthetic */ void onPostExecute(Object obj) {
//            a((Boolean) obj);
//        }
//
//        protected Boolean a(String... strArr) {
//            if (j.PUBLIC_KEY == null || j.PUBLIC_KEY.isEmpty()) {
//                return Boolean.valueOf(true);
//            }
//            try {
//                Thread.sleep(AgooSettings.SYS_ERROR_CONNECT_TEST_INTERVAL);
//            } catch (InterruptedException e) {
//            }
//            List<Bundle> bundles = Atlas.getInstance().getBundles();
//            if (bundles != null) {
//                for (Bundle bundle : bundles) {
//                    File bundleFile = Atlas.getInstance().getBundleFile(bundle.getLocation());
//                    if (!this.a.b(bundleFile.getAbsolutePath())) {
//                        return Boolean.valueOf(false);
//                    }
//                    String[] apkPublicKey = ApkUtils.getApkPublicKey(bundleFile.getAbsolutePath());
//                    if (StringUtils.contains(apkPublicKey, j.PUBLIC_KEY)) {
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e2) {
//                        }
//                    } else {
//                        Log.e("SecurityFrameListener", "Security check failed. " + bundle.getLocation());
//                        if (apkPublicKey == null || apkPublicKey.length == 0) {
//                            this.a.a(bundle.getLocation() + ": NULL");
//                        } else {
//                            this.a.a(bundle.getLocation() + ": " + apkPublicKey[0]);
//                        }
//                        return Boolean.valueOf(false);
//                    }
//                }
//            }
//            return Boolean.valueOf(true);
//        }
//
//        protected void a(Boolean bool) {
//            if (bool != null && !bool.booleanValue()) {
//                Toast.makeText(RuntimeVariables.androidApplication, "\u68c0\u6d4b\u5230\u5b89\u88c5\u6587\u4ef6\u88ab\u635f\u574f\uff0c\u8bf7\u5378\u8f7d\u540e\u91cd\u65b0\u5b89\u88c5\uff01", 1).show();
//                this.a.a.sendEmptyMessageDelayed(0, AgooSettings.SCREEN_ON_CONNECT_TEST_INTERVAL);
//            }
//        }
//    }
//
//    /* compiled from: SecurityFrameListener.java */
//    public static class b extends Handler {
//        public void handleMessage(Message message) {
//            Process.killProcess(Process.myPid());
//        }
//    }
//
//    public j() {
//        this.a = new b();
//    }
//
//    @SuppressLint({"NewApi"})
//    public void frameworkEvent(FrameworkEvent frameworkEvent) {
//        switch (frameworkEvent.getType()) {
//            case OpenBase.OAUTH_CREATE /*1*/:
//                if (VERSION.SDK_INT >= 11) {
//                    new a().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
//                } else {
//                    new a().execute(new String[0]);
//                }
//            default:
//        }
//    }
//
//    private void a(String str) {
//        Editor edit = RuntimeVariables.androidApplication.getSharedPreferences("atlas_configs", 0).edit();
//        edit.putString("BadSignature", str);
//        edit.commit();
//    }
//
//    private boolean b(String str) {
//        SecurityGuardManager instance = SecurityGuardManager.getInstance(RuntimeVariables.androidApplication);
//        if (instance != null) {
//            IPkgValidityCheckComponent packageValidityCheckComp = instance.getPackageValidityCheckComp();
//            if (packageValidityCheckComp != null) {
//                return packageValidityCheckComp.isPackageValid(str);
//            }
//        }
//        return false;
//    }
// }