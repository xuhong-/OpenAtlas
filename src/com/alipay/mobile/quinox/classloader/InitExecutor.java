package com.alipay.mobile.quinox.classloader;

import android.os.Build.VERSION;
import android.taobao.atlas.log.Logger;
import android.taobao.atlas.log.LoggerFactory;

public class InitExecutor {
    static final Logger log;
    private static boolean sDexOptLoaded;

    private static native void dexopt(String str, String str2, String str3);

    static {
        log = LoggerFactory.getInstance("InitExecutor");
        sDexOptLoaded = false;
        try {
            System.loadLibrary("dexopt");
            sDexOptLoaded = true;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public static boolean optDexFile(String str, String str2) {
        try {
            if (sDexOptLoaded && VERSION.SDK_INT <= 18) {
                dexopt(str, str2, "v=n,o=v");
                return true;
            }
        } catch (Throwable e) {
            log.error("Exception while try to call native dexopt >>>", e);
        }
        return false;
    }
}
