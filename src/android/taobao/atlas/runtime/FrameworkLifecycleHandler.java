package android.taobao.atlas.runtime;

import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;

import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.taobao.atlas.framework.Framework;
import android.taobao.atlas.log.Logger;
import android.taobao.atlas.log.LoggerFactory;
import android.taobao.atlas.util.StringUtils;

public class FrameworkLifecycleHandler implements FrameworkListener {
    static final Logger log;

    static {
        log = LoggerFactory.getInstance("FrameworkLifecycleHandler");
    }

    public void frameworkEvent(FrameworkEvent frameworkEvent) {
        switch (frameworkEvent.getType()) {
            case 0 /*0*/:
                starting();
            case 1 /*1*/:
                started();
            default:
        }
    }

    private void starting() {
        Bundle bundle;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            bundle = RuntimeVariables.androidApplication.getPackageManager().getApplicationInfo(RuntimeVariables.androidApplication.getPackageName(), 128).metaData;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            bundle = null;
        }
        if (bundle != null) {
            String string = bundle.getString("application");
            if (StringUtils.isNotEmpty(string)) {
                if (log.isDebugEnabled()) {
                    log.debug("Found extra application: " + string);
                }
                String[] split = StringUtils.split(string, ",");
                if (split == null || split.length == 0) {
                    split = new String[]{string};
                }
                for (String str : split) {
                    try {
                        Application newApplication = BundleLifecycleHandler.newApplication(str, Framework.getSystemClassLoader());
                        newApplication.onCreate();
                        DelegateComponent.apkApplications.put("system:" + str, newApplication);
                    } catch (Throwable e2) {
                        log.error("Error to start application", e2);
                    }
                }
            }
        }
        log.info("starting() spend " + (System.currentTimeMillis() - currentTimeMillis) + " milliseconds");
    }

    private void started() {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            DelegateResources.newDelegateResources(RuntimeVariables.androidApplication, RuntimeVariables.getDelegateResources());
        } catch (Throwable e) {
            log.error("Failed to newDelegateResources", e);
        }
        log.info("started() spend " + (System.currentTimeMillis() - currentTimeMillis) + " milliseconds");
    }
}
