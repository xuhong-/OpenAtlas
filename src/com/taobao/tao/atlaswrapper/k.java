package com.taobao.tao.atlaswrapper;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.util.Log;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: Utils.java */
public class k {
    static final String[] a;
    static final String[] b;
    static final String[] c;

    static {
        a = new String[]{"com.taobao.login4android", "com.taobao.taobao.home", "com.taobao.passivelocation", "com.taobao.mytaobao", "com.taobao.wangxin", "com.taobao.allspark", "com.taobao.search", "com.taobao.android.scancode", "com.taobao.android.trade", "com.taobao.taobao.cashdesk", "com.taobao.weapp", "com.taobao.taobao.alipay"};
        b = new String[]{"com.taobao.login4android", "com.taobao.taobao.home", "com.taobao.mytaobao", "com.taobao.wangxin", "com.taobao.passivelocation", "com.taobao.allspark"};
        c = new String[]{"com.taobao.scan", "com.taobao.taobao.pluginservice", "com.taobao.legacy", "com.ut.share", "com.taobao.taobao.map", "com.taobao.android.gamecenter", "com.taobao.tongxue", "com.taobao.taobao.zxing", "com.taobao.labs", "com.taobao.android.audio", "com.taobao.dressmatch", "com.taobao.crazyanchor", "com.taobao.bala", "com.taobao.coupon", "com.taobao.cainiao", "com.taobao.rushpromotion", "com.taobao.android.gamecenter", "com.taobao.ju.android", "com.taobao.android.big"};
    }

    public static String getFileNameFromEntryName(String str) {
        return str.substring(str.indexOf("lib/armeabi/") + "lib/armeabi/".length());
    }

    public static String getPackageNameFromEntryName(String str) {
        return str.substring(str.indexOf("lib/armeabi/lib") + "lib/armeabi/lib".length(), str.indexOf(".so")).replace( "_", ".");
    }

    public static String getPackageNameFromSoName(String str) {
        return str.substring(str.indexOf("lib") + "lib".length(), str.indexOf(".so")).replace( "_", ".");
    }

    public static String getBaseFileName(String str) {
        int lastIndexOf = str.lastIndexOf(".");
        if (lastIndexOf > 0) {
            return str.substring(0, lastIndexOf);
        }
        return str;
    }

    public static PackageInfo getPackageInfo(Application application) {
        try {
            return application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
        } catch (Throwable e) {
            Log.e("Utils", "Error to get PackageInfo >>>", e);
            return new PackageInfo();
        }
    }

    public static void saveAtlasInfoBySharedPreferences(Application application) {
        Map<String,String>concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put(getPackageInfo(application).versionName, "dexopt");
        SharedPreferences sharedPreferences = application.getSharedPreferences("atlas_configs", 0);
        if (sharedPreferences == null) {
            sharedPreferences = application.getSharedPreferences("atlas_configs", 0);
        }
        Editor edit = sharedPreferences.edit();
        for (String str : concurrentHashMap.keySet()) {
            edit.putString(str, (String) concurrentHashMap.get(str));
        }
        edit.commit();
    }

    public static boolean searchFile(String str, String str2) {
        if (str == null || str2 == null) {
            Log.e("Utils", "error in search File, direcoty or keyword is null");
            return false;
        }
        File file = new File(str);
        if (file == null || !file.exists()) {
            Log.e("Utils", "error in search File, can not open directory " + str);
            return false;
        }
        File[] listFiles = new File(str).listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            return false;
        }
        for (File file2 : listFiles) {
            if (file2.getName().indexOf(str2) >= 0) {
                Log.i("Utils", "the file search success " + file2.getName() + " keyword is " + str2);
                return true;
            }
        }
        Log.i("Utils", "the file search failed directory is " + str + " keyword is " + str2);
        return false;
    }
}