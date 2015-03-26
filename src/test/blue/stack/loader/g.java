package test.blue.stack.loader;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.taobao.atlas.runtime.ClassNotFoundInterceptorCallback;

/* compiled from: ClassNotFoundInterceptor.java */
public class g implements ClassNotFoundInterceptorCallback {
    public static final List<String> GO_H5_BUNDLES_IF_NOT_EXISTS;
    public final String TAG;

    public g() {
        this.TAG = "ClassNotFundInterceptor";
    }

    static {
        GO_H5_BUNDLES_IF_NOT_EXISTS = new ArrayList();
    }

    public static void addGoH5BundlesIfNotExists(String str) {
        if (!GO_H5_BUNDLES_IF_NOT_EXISTS.contains(str)) {
            GO_H5_BUNDLES_IF_NOT_EXISTS.add(str);
        }
    }

    public static void resetGoH5BundlesIfNotExists() {
        GO_H5_BUNDLES_IF_NOT_EXISTS.clear();
    }

    public Intent returnIntent(Intent intent) {
        String className = intent.getComponent().getClassName();
        CharSequence dataString = intent.getDataString();
        if (className == null
                || !className.equals("com.taobao.tao.welcome.Welcome")) {
            // if (Globals.isMiniPackage()) {
            // a findBundleByActivity =
            // BundlesInstaller.instance().findBundleByActivity(className);
            // if (!(findBundleByActivity == null ||
            // Atlas.getInstance().getBundle(findBundleByActivity.getPkgName())
            // != null ||
            // GO_H5_BUNDLES_IF_NOT_EXISTS.contains(findBundleByActivity.getPkgName())))
            // {
            // new Handler(Looper.getMainLooper()).post(new OptDexProcess(this,
            // intent, className, findBundleByActivity));
            // }
            // }
            // if (!TextUtils.isEmpty(dataString)) {
            // Nav.from(Globals.getApplication()).withCategory(ShopUrlFilter.BROWSER_ONLY_CATEGORY).withExtras(intent.getExtras()).toUri(intent.getData());
            // }
        }
        return intent;
    }
}
