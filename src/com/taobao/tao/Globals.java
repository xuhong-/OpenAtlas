package com.taobao.tao;

import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.taobao.taobao.R;

public class Globals {
    private static Application sApplication;
    private static ClassLoader sClassLoader;
    private static String sInstalledVersionName;

    public static synchronized Application getApplication() {
        Application application;
        synchronized (Globals.class) {
            if (sApplication == null) {
                sApplication = getSystemApp();
            }
            application = sApplication;
        }
        return application;
    }

    public static synchronized ClassLoader getClassLoader() {
        ClassLoader classLoader;
        synchronized (Globals.class) {
            if (sClassLoader == null) {
                classLoader = getApplication().getClassLoader();
            } else {
                classLoader = sClassLoader;
            }
        }
        return classLoader;
    }

    private static Application getSystemApp() {
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod(
                    "currentActivityThread", new Class[0]);
            Field declaredField = cls.getDeclaredField("mInitialApplication");
            declaredField.setAccessible(true);
            return (Application) declaredField.get(declaredMethod.invoke(null,
                    new Object[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getVersionName() {
        try {
            return getApplication().getPackageManager().getPackageInfo(
                    getApplication().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "5.0.0";
        }
    }

    public static String getInstalledVersionName() {
        return sInstalledVersionName;
    }

    public static int getVersionCode() {
        int i = 0;
        try {
            return getApplication().getPackageManager().getPackageInfo(
                    getApplication().getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return i;
        }
    }

    public static final String TRACE_TYPE_BUY = "3";
    public static final String TRACE_TYPE_CART = "2";
    public static final String TRACE_TYPE_FAV = "1";
    public static final String TRACE_TYPE_FAV_SHOP = "4";

    public static boolean isMiniPackage() {
        try {
            String string = getApplication().getString(R.string.isMiniPackage);
            if (string == null) {
                return false;
            }
            return TRACE_TYPE_FAV.equals(string.trim());
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean isMiniPackage(Application application) {
        try {
            String string = application.getString(R.string.isMiniPackage);
            if (TextUtils.isEmpty(string)) {
                return false;
            }
            return TRACE_TYPE_FAV.equals(string.trim());
        } catch (Throwable th) {
            return false;
        }
    }
}
