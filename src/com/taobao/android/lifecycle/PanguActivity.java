package com.taobao.android.lifecycle;


import android.app.Activity;
import android.os.Bundle;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class PanguActivity extends Activity {
    private final List<IndividualActivityLifecycleCallback> mIndividualActivityLifecycleCallbacks;

    public static interface IndividualActivityLifecycleCallback {
        void onCreated(Activity activity);

        void onDestroyed(Activity activity);

        void onPaused(Activity activity);

        void onResumed(Activity activity);

        void onStarted(Activity activity);

        void onStopped(Activity activity);
    }

    public PanguActivity() {
        this.mIndividualActivityLifecycleCallbacks = new CopyOnWriteArrayList();
    }

    protected void onCreate( Bundle bundle) {
        super.onCreate(bundle);
        if (!this.mIndividualActivityLifecycleCallbacks.isEmpty()) {
            for (IndividualActivityLifecycleCallback individualActivityLifecycleCallback : this.mIndividualActivityLifecycleCallbacks) {
                individualActivityLifecycleCallback.onCreated(this);
            }
        }
    }

    protected void onStart() {
        super.onStart();
        if (!this.mIndividualActivityLifecycleCallbacks.isEmpty()) {
            for (IndividualActivityLifecycleCallback individualActivityLifecycleCallback : this.mIndividualActivityLifecycleCallbacks) {
                individualActivityLifecycleCallback.onStarted(this);
            }
        }
    }

    protected void onResume() {
        super.onResume();
        if (!this.mIndividualActivityLifecycleCallbacks.isEmpty()) {
            for (IndividualActivityLifecycleCallback individualActivityLifecycleCallback : this.mIndividualActivityLifecycleCallbacks) {
                individualActivityLifecycleCallback.onResumed(this);
            }
        }
    }

    protected void onPause() {
        if (!this.mIndividualActivityLifecycleCallbacks.isEmpty()) {
            for (IndividualActivityLifecycleCallback individualActivityLifecycleCallback : this.mIndividualActivityLifecycleCallbacks) {
                individualActivityLifecycleCallback.onPaused(this);
            }
        }
        super.onPause();
    }

    protected void onStop() {
        if (!this.mIndividualActivityLifecycleCallbacks.isEmpty()) {
            for (IndividualActivityLifecycleCallback individualActivityLifecycleCallback : this.mIndividualActivityLifecycleCallbacks) {
                individualActivityLifecycleCallback.onStopped(this);
            }
        }
        super.onStop();
    }

    protected void onDestroy() {
        if (!this.mIndividualActivityLifecycleCallbacks.isEmpty()) {
            for (IndividualActivityLifecycleCallback individualActivityLifecycleCallback : this.mIndividualActivityLifecycleCallbacks) {
                individualActivityLifecycleCallback.onDestroyed(this);
            }
        }
        super.onDestroy();
    }

    public void registerIndividualActivityLifecycleCallback(IndividualActivityLifecycleCallback individualActivityLifecycleCallback) {
        this.mIndividualActivityLifecycleCallbacks.add(individualActivityLifecycleCallback);
    }

    public void unregisterIndividualActivityLifecycleCallback(IndividualActivityLifecycleCallback individualActivityLifecycleCallback) {
        this.mIndividualActivityLifecycleCallbacks.remove(individualActivityLifecycleCallback);
    }


}