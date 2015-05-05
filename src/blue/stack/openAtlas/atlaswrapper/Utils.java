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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.util.Log;


public class Utils {
    static final String[] DELAY;
    static final String[] AUTO;
    static final String[] STORE;

    static {
        DELAY = new String[] { "com.taobao.login4android",
                "com.taobao.taobao.home", "com.taobao.passivelocation",
                "com.taobao.mytaobao", "com.taobao.wangxin",
                "com.taobao.allspark", "com.taobao.search",
                "blue.stack.openAtlas.android.scancode", "blue.stack.openAtlas.android.trade",
                "com.taobao.taobao.cashdesk", "com.taobao.weapp",
                "com.taobao.taobao.alipay" };
        AUTO = new String[] { "com.taobao.login4android",
                "com.taobao.taobao.home", "com.taobao.mytaobao",
                "com.taobao.wangxin", "com.taobao.passivelocation",
                "com.taobao.allspark" };
        STORE = new String[] { "com.taobao.android.game20x6f","com.taobao.scan",
                "com.taobao.taobao.pluginservice", "com.taobao.legacy",
                "com.ut.share", "com.taobao.taobao.map",
                "blue.stack.openAtlas.android.gamecenter", "com.taobao.tongxue",
                "com.taobao.taobao.zxing", "com.taobao.labs",
                "blue.stack.openAtlas.android.audio", "com.taobao.dressmatch",
                "com.taobao.crazyanchor", "com.taobao.bala",
                "com.taobao.coupon", "com.taobao.cainiao",
                "com.taobao.rushpromotion", "blue.stack.openAtlas.android.gamecenter",
                "com.taobao.ju.android", "blue.stack.openAtlas.android.big" };
    }

    public static String getFileNameFromEntryName(String str) {
        return str.substring(str.indexOf("lib/armeabi/")
                + "lib/armeabi/".length());
    }

    public static String getPackageNameFromEntryName(String str) {
        return str.substring(
                str.indexOf("lib/armeabi/lib") + "lib/armeabi/lib".length(),
                str.indexOf(".so")).replace("_", ".");
    }

    public static String getPackageNameFromSoName(String str) {
        return str.substring(str.indexOf("lib") + "lib".length(),
                str.indexOf(".so")).replace("_", ".");
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
            return application.getPackageManager().getPackageInfo(
                    application.getPackageName(), 0);
        } catch (Throwable e) {
            Log.e("Utils", "Error to get PackageInfo >>>", e);
            return new PackageInfo();
        }
    }

    public static void saveAtlasInfoBySharedPreferences(Application application) {
        Map<String, String> concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap
                .put(getPackageInfo(application).versionName, "dexopt");
        SharedPreferences sharedPreferences = application.getSharedPreferences(
                "atlas_configs", 0);
        if (sharedPreferences == null) {
            sharedPreferences = application.getSharedPreferences(
                    "atlas_configs", 0);
        }
        Editor edit = sharedPreferences.edit();
        for (String str : concurrentHashMap.keySet()) {
            edit.putString(str, concurrentHashMap.get(str));
        }
        edit.commit();
    }

    public static boolean searchFile(String direcoty, String keyword) {
        if (direcoty == null || keyword == null) {
            Log.e("Utils", "error in search File, direcoty or keyword is null");
            return false;
        }
        File file = new File(direcoty);
        if (file == null || !file.exists()) {
            Log.e("Utils", "error in search File, can not open directory "
                    + direcoty);
            return false;
        }
        File[] listFiles = new File(direcoty).listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            return false;
        }
        for (File file2 : listFiles) {
            if (file2.getName().indexOf(keyword) >= 0) {
                Log.i("Utils", "the file search success " + file2.getName()
                        + " keyword is " + keyword);
                return true;
            }
        }
        Log.i("Utils", "the file search failed directory is " + direcoty
                + " keyword is " + keyword);
        return false;
    }
}
