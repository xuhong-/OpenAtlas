package com.taobao.android.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import com.taobao.android.compat.ApplicationCompat;


import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class PanguApplication extends ApplicationCompat {
    private static final Handler mAppHandler;
    private final AtomicInteger mCreationCount;
    private final List<CrossActivityLifecycleCallback> mCrossActivityLifecycleCallbacks;
    private final AtomicInteger mStartCount;
    private WeakReference<Activity> mWeakActivity;

    public static interface CrossActivityLifecycleCallback {
        void onCreated(Activity activity);

        void onDestroyed(Activity activity);

        void onStarted(Activity activity);

        void onStopped(Activity activity);
    }

    class CallbackRunable implements Runnable {
        final  PanguApplication mApplication;
        private CrossActivityLifecycleCallback mCrossActivityLifecycleCallback;
        private String name;

        public CallbackRunable(PanguApplication panguApplication, CrossActivityLifecycleCallback crossActivityLifecycleCallback, String str) {
            this.mApplication = panguApplication;
            this.mCrossActivityLifecycleCallback = crossActivityLifecycleCallback;
            this.name = str;
        }

        public void run() {
            if (this.mApplication.mWeakActivity != null) {
                Activity activity = (Activity) this.mApplication.mWeakActivity.get();
                if (!(activity == null || this.mCrossActivityLifecycleCallback == null)) {
                    if ("onCreated".equals(this.name)) {
                        this.mCrossActivityLifecycleCallback.onCreated(activity);
                    } else if ("onStarted".equals(this.name)) {
                        this.mCrossActivityLifecycleCallback.onStarted(activity);
                    }
                }
            }
            this.mCrossActivityLifecycleCallback = null;
            this.name = null;
        }
    }

    class ActivityLifecycleCallbacksCompatImpl implements ActivityLifecycleCallbacksCompat {
        final  PanguApplication mApplication;

        ActivityLifecycleCallbacksCompatImpl(PanguApplication panguApplication) {
            this.mApplication = panguApplication;
        }

        public void onActivityCreated(Activity activity,  Bundle bundle) {
            this.mApplication.mWeakActivity = new WeakReference(activity);
            if (this.mApplication.mCreationCount.getAndIncrement() == 0 && !this.mApplication.mCrossActivityLifecycleCallbacks.isEmpty()) {
                for (CrossActivityLifecycleCallback onCreated : this.mApplication.mCrossActivityLifecycleCallbacks) {
                    onCreated.onCreated(activity);
                }
            }
        }

        public void onActivityStarted(Activity activity) {
            if (this.mApplication.mStartCount.getAndIncrement() == 0 && !this.mApplication.mCrossActivityLifecycleCallbacks.isEmpty()) {
                for (CrossActivityLifecycleCallback onStarted : this.mApplication.mCrossActivityLifecycleCallbacks) {
                    onStarted.onStarted(activity);
                }
            }
        }

        public void onActivityStopped(Activity activity) {
            if (this.mApplication.mStartCount.decrementAndGet() == 0 && !this.mApplication.mCrossActivityLifecycleCallbacks.isEmpty()) {
                for (CrossActivityLifecycleCallback onStopped : this.mApplication.mCrossActivityLifecycleCallbacks) {
                    onStopped.onStopped(activity);
                }
            }
        }

        public void onActivityDestroyed(Activity activity) {
            if (this.mApplication.mCreationCount.decrementAndGet() == 0 && !this.mApplication.mCrossActivityLifecycleCallbacks.isEmpty()) {
                for (CrossActivityLifecycleCallback onDestroyed : this.mApplication.mCrossActivityLifecycleCallbacks) {
                    onDestroyed.onDestroyed(activity);
                }
            }
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }
    }

    public PanguApplication() {
        this.mCrossActivityLifecycleCallbacks = new CopyOnWriteArrayList();
        this.mCreationCount = new AtomicInteger();
        this.mStartCount = new AtomicInteger();
    }

    public void registerCrossActivityLifecycleCallback(CrossActivityLifecycleCallback crossActivityLifecycleCallback) {
        if (crossActivityLifecycleCallback == null) {
            new RuntimeException("registerCrossActivityLifecycleCallback must not be null").fillInStackTrace();
          
            return;
        }
        this.mCrossActivityLifecycleCallbacks.add(crossActivityLifecycleCallback);
        if (this.mCreationCount.get() > 0) {
            mAppHandler.post(new CallbackRunable(this, crossActivityLifecycleCallback, "onCreated"));
        }
        if (this.mStartCount.get() > 0) {
            mAppHandler.post(new CallbackRunable(this, crossActivityLifecycleCallback, "onStarted"));
        }
    }

    public void unregisterCrossActivityLifecycleCallback(CrossActivityLifecycleCallback crossActivityLifecycleCallback) {
        this.mCrossActivityLifecycleCallbacks.remove(crossActivityLifecycleCallback);
    }

    public static void runOnUiThread(Runnable runnable) {
        mAppHandler.post(runnable);
    }

    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksCompatImpl(this));
     
    }

    static {
        mAppHandler = new Handler();
    }
}