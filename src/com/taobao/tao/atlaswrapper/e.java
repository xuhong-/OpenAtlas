package com.taobao.tao.atlaswrapper;

import android.taobao.atlas.runtime.RuntimeVariables;
import android.widget.Toast;

public class e implements Runnable {
    final/* synthetic */BundlesInstaller a;

    e(BundlesInstaller dVar) {
        this.a = dVar;
    }

    public void run() {
        Toast.makeText(
                RuntimeVariables.androidApplication,
                "\u68c0\u6d4b\u5230\u624b\u673a\u5b58\u50a8\u7a7a\u95f4\u4e0d\u8db3\uff0c\u4e3a\u4e0d\u5f71\u54cd\u60a8\u7684\u4f7f\u7528\u8bf7\u6e05\u7406\uff01",
                1).show();
    }
}