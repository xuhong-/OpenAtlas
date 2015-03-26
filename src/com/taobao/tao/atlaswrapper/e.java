package com.taobao.tao.atlaswrapper;

import android.taobao.atlas.runtime.RuntimeVariables;
import android.widget.Toast;

public class e implements Runnable {
    final BundlesInstaller a;

    e(BundlesInstaller dVar) {
        this.a = dVar;
    }

    public void run() {
        Toast.makeText(
                RuntimeVariables.androidApplication,
                "检测到手机存储空间不足，为不影响您的使用请清理！",
                1).show();
    }
}
