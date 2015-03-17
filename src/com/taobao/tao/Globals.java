package com.taobao.tao;

import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Globals {
    private static Application sApplication;
    private static ClassLoader sClassLoader;

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
            Class forName = Class.forName("android.app.ActivityThread");
            Method declaredMethod = forName.getDeclaredMethod("currentActivityThread", new Class[0]);
            Field declaredField = forName.getDeclaredField("mInitialApplication");
            declaredField.setAccessible(true);
            return (Application) declaredField.get(declaredMethod.invoke(null, new Object[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getVersionName() {
//        String version = TaoApplication.getVersion();
//        if (getVersionCode() > a.getInstance().getMainVersionCode()) {
//            return version;
//        }
//        String mainVersionName = a.getInstance().getMainVersionName();
//        if (!StringUtil.isEmpty(mainVersionName) && !version.equalsIgnoreCase(mainVersionName)) {
//            return version;
//        }
//        String baselineVersion = a.getInstance().getBaselineVersion();
//        if (StringUtil.isEmpty(mainVersionName) || StringUtil.isEmpty(baselineVersion)) {
//            return version;
//        }
//        String[] split = mainVersionName.split("\\.");
//        if (split.length < 3) {
//            return version;
//        }
//        split[2] = baselineVersion;
        return "1.0";//TextUtils.join(".", split);
    }

    public static String getBaselineVer() {
//        if (getVersionCode() > a.getInstance().getMainVersionCode()) {
//            return BaseRemoteBusiness.BASE_URL;
//        }
//        String version = TaoApplication.getVersion();
//        String mainVersionName = a.getInstance().getMainVersionName();
        return "1";//(StringUtil.isEmpty(mainVersionName) || version.equalsIgnoreCase(mainVersionName)) ? a.getInstance().getBaselineVersion() : BaseRemoteBusiness.BASE_URL;
    }

    private static int getVersionCode() {
        try {
            return getApplication().getPackageManager().getPackageInfo(getApplication().getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean isMiniPackage() {
return false;
    }

    public static boolean isMiniPackage(Application application) {
    	return false;
        }
}