package android.taobao.atlas.runtime;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

public class RuntimeVariables {
    public static Application androidApplication;
    public static DelegateClassLoader delegateClassLoader;
    private static Resources delegateResources;

    public static Resources getDelegateResources() {
        return delegateResources;
    }

    public static void setDelegateResources(Resources delegateResources) {
        Thread.currentThread().dumpStack();
        Log.e("bunny", "setDelegateResources" + delegateResources.toString());
        RuntimeVariables.delegateResources = delegateResources;
    }
}
