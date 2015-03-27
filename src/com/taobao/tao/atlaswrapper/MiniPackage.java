package com.taobao.tao.atlaswrapper;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import com.taobao.tao.Globals;
import java.util.Properties;

/* compiled from: MiniPackage.java ClassNotFoundInterceptorCallbackImpl*/
public class MiniPackage {
    Application a;

    MiniPackage(Application application) {
        this.a = application;
    }

    void a(Properties properties) {
        if (!Globals.isMiniPackage()) {
        }
    }

    void a(SharedPreferences sharedPreferences, PackageInfo packageInfo) {
        if (!Globals.isMiniPackage()) {
        }
    }
}
