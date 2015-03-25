package com.taobao.tao.atlaswrapper;

import com.taobao.android.task.Coordinator.TaggedRunnable;

class b extends TaggedRunnable {
    final/* synthetic */BundlesInstaller a;
    final/* synthetic */OptDexProcess b;
    final/* synthetic */AtlasInitializer c;

    b(AtlasInitializer aVar, String str, BundlesInstaller dVar,
            OptDexProcess hVar) {
        super(str);
        this.c = aVar;
        this.a = dVar;
        this.b = hVar;

    }

    public void run() {
        this.c.a(this.a, this.b);
    }
}