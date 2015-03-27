package com.taobao.android.compat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.ArrayList;

public class ApplicationCompat extends Application {
    private final ArrayList<ActivityLifecycleCallbacksCompat> mActivityLifecycleCallbacks;

    public static interface ActivityLifecycleCallbacksCompat {
        void onActivityCreated(Activity activity,  Bundle bundle);

        void onActivityDestroyed(Activity activity);

        void onActivityPaused(Activity activity);

        void onActivityResumed(Activity activity);

        void onActivitySaveInstanceState(Activity activity, Bundle bundle);

        void onActivityStarted(Activity activity);

        void onActivityStopped(Activity activity);
    }

    public static class AbstractActivityLifecycleCallbacks implements ActivityLifecycleCallbacksCompat {
        public void onActivityCreated(Activity activity,  Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }
    }

    public ApplicationCompat() {
        this.mActivityLifecycleCallbacks = new ArrayList();
    }

    @TargetApi(14)
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat activityLifecycleCallbacksCompat) {
        if (VERSION.SDK_INT >= 14) {
            super.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksImpl(activityLifecycleCallbacksCompat));
            return;
        }
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.add(activityLifecycleCallbacksCompat);
        }
    }

    @TargetApi(14)
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat activityLifecycleCallbacksCompat) {
        if (VERSION.SDK_INT >= 14) {
            super.unregisterActivityLifecycleCallbacks(new ActivityLifecycleCallbacksImpl(activityLifecycleCallbacksCompat));
            return;
        }
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.remove(activityLifecycleCallbacksCompat);
        }
    }

    void dispatchActivityCreatedCompat(Activity activity, Bundle bundle) {
        ActivityLifecycleCallbacksCompat[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (ActivityLifecycleCallbacksCompat onActivityCreated : collectActivityLifecycleCallbacks) {
                onActivityCreated.onActivityCreated(activity, bundle);
            }
        }
    }

    void dispatchActivityStartedCompat(Activity activity) {
        ActivityLifecycleCallbacksCompat[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (ActivityLifecycleCallbacksCompat onActivityStarted : collectActivityLifecycleCallbacks) {
                onActivityStarted.onActivityStarted(activity);
            }
        }
    }

    void dispatchActivityResumedCompat(Activity activity) {
        ActivityLifecycleCallbacksCompat[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (ActivityLifecycleCallbacksCompat onActivityResumed : collectActivityLifecycleCallbacks) {
                onActivityResumed.onActivityResumed(activity);
            }
        }
    }

    void dispatchActivityPausedCompat(Activity activity) {
        ActivityLifecycleCallbacksCompat[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (ActivityLifecycleCallbacksCompat onActivityPaused : collectActivityLifecycleCallbacks) {
                onActivityPaused.onActivityPaused(activity);
            }
        }
    }

    void dispatchActivityStoppedCompat(Activity activity) {
        ActivityLifecycleCallbacksCompat[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (ActivityLifecycleCallbacksCompat onActivityStopped : collectActivityLifecycleCallbacks) {
                onActivityStopped.onActivityStopped(activity);
            }
        }
    }

    void dispatchActivitySaveInstanceStateCompat(Activity activity, Bundle bundle) {
        ActivityLifecycleCallbacksCompat[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (ActivityLifecycleCallbacksCompat onActivitySaveInstanceState : collectActivityLifecycleCallbacks) {
                onActivitySaveInstanceState.onActivitySaveInstanceState(activity, bundle);
            }
        }
    }

    void dispatchActivityDestroyedCompat(Activity activity) {
        ActivityLifecycleCallbacksCompat[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (ActivityLifecycleCallbacksCompat onActivityDestroyed : collectActivityLifecycleCallbacks) {
                onActivityDestroyed.onActivityDestroyed(activity);
            }
        }
    }


    private ActivityLifecycleCallbacksCompat[] collectActivityLifecycleCallbacks() {
        ActivityLifecycleCallbacksCompat[] activityLifecycleCallbacksCompatArr = null;
        synchronized (this.mActivityLifecycleCallbacks) {
            if (this.mActivityLifecycleCallbacks.size() > 0) {
                activityLifecycleCallbacksCompatArr = (ActivityLifecycleCallbacksCompat[]) this.mActivityLifecycleCallbacks.toArray(new ActivityLifecycleCallbacksCompat[this.mActivityLifecycleCallbacks.size()]);
            }
        }
        return activityLifecycleCallbacksCompatArr;
    }
    
    

  
    class ActivityLifecycleCallbacksImpl implements ActivityLifecycleCallbacks {
        private final ActivityLifecycleCallbacksCompat mActivityLifecycleCallbacksCompat;

        public void onActivityCreated(Activity activity, Bundle bundle) {
            this.mActivityLifecycleCallbacksCompat.onActivityCreated(activity, bundle);
        }

        public void onActivityStarted(Activity activity) {
            this.mActivityLifecycleCallbacksCompat.onActivityStarted(activity);
        }

        public void onActivityResumed(Activity activity) {
            this.mActivityLifecycleCallbacksCompat.onActivityResumed(activity);
        }

        public void onActivityPaused(Activity activity) {
            this.mActivityLifecycleCallbacksCompat.onActivityPaused(activity);
        }

        public void onActivityStopped(Activity activity) {
            this.mActivityLifecycleCallbacksCompat.onActivityStopped(activity);
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            this.mActivityLifecycleCallbacksCompat.onActivitySaveInstanceState(activity, bundle);
        }

        public void onActivityDestroyed(Activity activity) {
            this.mActivityLifecycleCallbacksCompat.onActivityDestroyed(activity);
        }

        public int hashCode() {
            return this.mActivityLifecycleCallbacksCompat.hashCode();
        }

        public boolean equals( Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ActivityLifecycleCallbacksImpl) {
                return this.mActivityLifecycleCallbacksCompat.equals(((ActivityLifecycleCallbacksImpl) obj).mActivityLifecycleCallbacksCompat);
            }
            return false;
        }

        ActivityLifecycleCallbacksImpl(ActivityLifecycleCallbacksCompat activityLifecycleCallbacksCompat) {
            this.mActivityLifecycleCallbacksCompat = activityLifecycleCallbacksCompat;
        }
    }
}