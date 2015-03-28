/**
 *  OpenAtlasForAndroid Project
The MIT License (MIT) Copyright (OpenAtlasForAndroid) 2015 Bunny Blue,achellies

Permission is hereby granted, free of charge, to any person obtaining mApp copy of this software
and associated documentation files (the "Software"), to deal in the Software 
without restriction, including without limitation the rights to use, copy, modify, 
merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies 
or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
@author BunnyBlue
 * **/
package android.taobao.atlas.runtime;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.osgi.framework.BundleException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.app.Fragment;
import android.app.Instrumentation;
import android.app.UiAutomation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.taobao.atlas.framework.BundleClassLoader;
import android.taobao.atlas.framework.Framework;
import android.taobao.atlas.hack.AtlasHacks;
import android.taobao.atlas.hack.Hack;
import android.taobao.atlas.hack.Hack.HackDeclaration.HackAssertionException;
import android.taobao.atlas.hack.Hack.HackedClass;
import android.taobao.atlas.hack.Hack.HackedMethod;
import android.taobao.atlas.log.Logger;
import android.taobao.atlas.log.LoggerFactory;
import android.taobao.atlas.util.StringUtils;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class InstrumentationHook extends Instrumentation {
    static final Logger log;
    private Context context;
    private Instrumentation mBase;
    private HackedClass<Object> mInstrumentationInvoke;
    private HackedMethod mExecStartActivity1;
    private HackedMethod mExecStartActivity2;

    private static interface ExecStartActivityCallback {
        ActivityResult execStartActivity();
    }

    class AnonymousClass_1 implements ExecStartActivityCallback {
        final IBinder contextThread;
        final Intent intent;
        final int requestCode;
        final Activity target;
        final IBinder token;
        final Context who;

        AnonymousClass_1(Context context, IBinder iBinder, IBinder iBinder2,
                         Activity activity, Intent intent, int i) {
            this.who = context;
            this.contextThread = iBinder;
            this.token = iBinder2;
            this.target = activity;
            this.intent = intent;
            this.requestCode = i;
        }

        @Override
		public ActivityResult execStartActivity() {
            if (mExecStartActivity1 == null) {
                throw new NullPointerException("could not hook Instrumentation!");
            }

            try {
                return (ActivityResult) mExecStartActivity1.invoke(mBase, this.who, this.contextThread,
                        this.token, this.target, this.intent,
                        this.requestCode, null);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return null;
//            return InstrumentationInvoke.execStartActivity(mBase, this.who,
//                    this.contextThread, this.token, this.target,
//                    this.intent, this.requestCode);
            // return
            // InstrumentationHook.this.mBase.execStartActivity(this.who,
            // this.contextThread, this.token, this.target,
            // this.intent, this.requestCode);
        }
    }

    class AnonymousClass_2 implements ExecStartActivityCallback {
        final IBinder contextThread;
        final Intent intent;
        final Bundle options;
        final int requestCode;
        final Activity target;
        final IBinder token;
        final Context who;

        AnonymousClass_2(Context context, IBinder iBinder, IBinder iBinder2,
                         Activity activity, Intent intent, int i, Bundle bundle) {
            this.who = context;
            this.contextThread = iBinder;
            this.token = iBinder2;
            this.target = activity;
            this.intent = intent;
            this.requestCode = i;
            this.options = bundle;
        }

        @Override
		public ActivityResult execStartActivity() {
            if (mExecStartActivity1 == null) {
                throw new NullPointerException("could not hook Instrumentation!");
            }
            try {
                return (ActivityResult) mExecStartActivity1.invoke(mBase, this.who, this.contextThread,
                        this.token, this.target, this.intent,
                        this.requestCode, this.options);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return null;
//            return InstrumentationInvoke.execStartActivity(mBase, this.who,
//                    this.contextThread, this.token, this.target,
//                    this.intent, this.requestCode, this.options);
            // return
            // InstrumentationHook.this.mBase.execStartActivity(this.who,
            // this.contextThread, this.token, this.target,
            // this.intent, this.requestCode, this.options);
        }
    }

    class AnonymousClass_3 implements ExecStartActivityCallback {
        final IBinder contextThread;
        final Intent intent;
        final int requestCode;
        final Fragment target;
        final IBinder token;
        final Context who;

        AnonymousClass_3(Context context, IBinder iBinder, IBinder iBinder2,
                         Fragment fragment, Intent intent, int i) {
            this.who = context;
            this.contextThread = iBinder;
            this.token = iBinder2;
            this.target = fragment;
            this.intent = intent;
            this.requestCode = i;
        }

        @Override
		public ActivityResult execStartActivity() {
            if (mExecStartActivity2 == null) {
                throw new NullPointerException("could not hook Instrumentation!");
            }
            try {
                return (ActivityResult) mExecStartActivity2.invoke(mBase, this.who, this.contextThread,
                        this.token, this.target, this.intent,
                        this.requestCode, null);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return null;
//            return InstrumentationInvoke.execStartActivity(mBase, this.who,
//                    this.contextThread, this.token, this.target,
//                    this.intent, this.requestCode);
            // return
            // InstrumentationHook.this.mBase.execStartActivity(this.who,
            // this.contextThread, this.token, this.target,
            // this.intent, this.requestCode, this.options);

            // return
            // InstrumentationHook.this.mBase.execStartActivity(this.who,
            // this.contextThread, this.token, this.target,
            // this.intent, this.requestCode);
        }
    }

    class AnonymousClass_4 implements ExecStartActivityCallback {
        final IBinder contextThread;
        final Intent intent;
        final Bundle options;
        final int requestCode;
        final Fragment target;
        final IBinder token;
        final Context who;

        AnonymousClass_4(Context context, IBinder iBinder, IBinder iBinder2,
                         Fragment fragment, Intent intent, int i, Bundle bundle) {
            this.who = context;
            this.contextThread = iBinder;
            this.token = iBinder2;
            this.target = fragment;
            this.intent = intent;
            this.requestCode = i;
            this.options = bundle;
        }

        @Override
		public ActivityResult execStartActivity() {
            if (mExecStartActivity2 == null) {
                throw new NullPointerException("could not hook Instrumentation!");
            }

            try {
                return (ActivityResult) mExecStartActivity2.invoke(mBase, this.who, this.contextThread,
                        this.token, this.target, this.intent,
                        this.requestCode, this.options);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            return null;
//            return InstrumentationInvoke.execStartActivity(mBase, this.who,
//                    this.contextThread, this.token, this.target,
//                    this.intent, this.requestCode, this.options);
            // return
            // InstrumentationHook.this.mBase.execStartActivity(this.who,
            // this.contextThread, this.token, this.target,
            // this.intent, this.requestCode, this.options);
        }
    }

    static {
        log = LoggerFactory.getInstance("InstrumentationHook");
    }

    public InstrumentationHook(Instrumentation instrumentation, Context context) {
        this.context = context;
        this.mBase = instrumentation;

        try {
            mInstrumentationInvoke = Hack
                    .into("android.app.Instrumentation");
            mExecStartActivity1 = mInstrumentationInvoke.method(
                    "execStartActivity", new Class[]{Context.class,
                            IBinder.class, IBinder.class, Activity.class,
                            Intent.class, int.class, Bundle.class});
            mExecStartActivity2 = mInstrumentationInvoke.method(
                    "execStartActivity", new Class[]{Context.class,
                            IBinder.class, IBinder.class, Fragment.class,
                            Intent.class, int.class, Bundle.class});
        } catch (HackAssertionException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public ActivityResult execStartActivity(Context context, IBinder iBinder,
                                            IBinder iBinder2, Activity activity, Intent intent, int i) {
        return execStartActivityInternal(this.context, intent,
                new AnonymousClass_1(context, iBinder, iBinder2, activity,
                        intent, i));
    }

    @TargetApi(16)
    public ActivityResult execStartActivity(Context context, IBinder iBinder,
                                            IBinder iBinder2, Activity activity, Intent intent, int i,
                                            Bundle bundle) {
        return execStartActivityInternal(this.context, intent,
                new AnonymousClass_2(context, iBinder, iBinder2, activity,
                        intent, i, bundle));
    }

    @TargetApi(14)
    public ActivityResult execStartActivity(Context context, IBinder iBinder,
                                            IBinder iBinder2, Fragment fragment, Intent intent, int i) {
        return execStartActivityInternal(this.context, intent,
                new AnonymousClass_3(context, iBinder, iBinder2, fragment,
                        intent, i));
    }

    @TargetApi(16)
    public ActivityResult execStartActivity(Context context, IBinder iBinder,
                                            IBinder iBinder2, Fragment fragment, Intent intent, int i,
                                            Bundle bundle) {
        return execStartActivityInternal(this.context, intent,
                new AnonymousClass_4(context, iBinder, iBinder2, fragment,
                        intent, i, bundle));
    }

    private ActivityResult execStartActivityInternal(Context context,
                                                     Intent intent, ExecStartActivityCallback execStartActivityCallback) {
        String packageName;
        String tmpString = context.getPackageName();
        String className;
        if (intent.getComponent() != null) {
            packageName = intent.getComponent().getPackageName();
            className = intent.getComponent().getClassName();
        } else {
            ResolveInfo resolveActivity = context.getPackageManager()
                    .resolveActivity(intent, 0);
            if (resolveActivity == null || resolveActivity.activityInfo == null) {
                className = null;
                packageName = null;
            } else {
                packageName = resolveActivity.activityInfo.packageName;
                className = resolveActivity.activityInfo.name;
            }
        }
        if (!StringUtils.equals(context.getPackageName(), packageName)) {
            return execStartActivityCallback.execStartActivity();
        }
        if (DelegateComponent.locateComponent(className) != null) {
            return execStartActivityCallback.execStartActivity();
        }
        try {
            if (ClassLoadFromBundle.loadFromUninstalledBundles(className) != null) {
                return execStartActivityCallback.execStartActivity();
            }
        } catch (ClassNotFoundException e) {
            log.info("Can't find class " + className + " in all bundles.");
        }
        try {
            if (Framework.getSystemClassLoader().loadClass(className) != null) {
                return execStartActivityCallback.execStartActivity();
            }
            return null;
        } catch (ClassNotFoundException e2) {
            log.error("Can't find class " + className);
            if (Framework.getClassNotFoundCallback() == null) {
                return null;
            }
            if (intent.getComponent() == null && !TextUtils.isEmpty(className)) {
                intent.setClassName(context, className);
            }
            if (intent.getComponent() == null) {
                return null;
            }
            Framework.getClassNotFoundCallback().returnIntent(intent);
            return null;
        }
    }

    @Override
	public Activity newActivity(Class<?> cls, Context context, IBinder iBinder,
                                Application application, Intent intent, ActivityInfo activityInfo,
                                CharSequence charSequence, Activity activity, String str, Object obj)
            throws InstantiationException, IllegalAccessException {
        Activity newActivity = this.mBase.newActivity(cls, context, iBinder,
                application, intent, activityInfo, charSequence, activity, str,
                obj);
        if (RuntimeVariables.androidApplication.getPackageName().equals(
                activityInfo.packageName)
                && AtlasHacks.ContextThemeWrapper_mResources != null) {
            AtlasHacks.ContextThemeWrapper_mResources.set(newActivity,
                    RuntimeVariables.getDelegateResources());
        }
        return newActivity;
    }

    @Override
	public Activity newActivity(ClassLoader classLoader, String str,
                                Intent intent) throws InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        Activity newActivity;
        String str2 = null;
        try {
            newActivity = this.mBase.newActivity(classLoader, str, intent);
        } catch (ClassNotFoundException e) {
            ClassNotFoundException classNotFoundException = e;
            CharSequence property = Framework.getProperty(
                    "android.taobao.atlas.welcome",
                    "com.taobao.tao.welcome.Welcome");
            if (TextUtils.isEmpty(property)) {
                str2 = "com.taobao.tao.welcome.Welcome";
            } else {
                CharSequence charSequence = property;
            }
            if (TextUtils.isEmpty(str2)) {
                throw classNotFoundException;
            }
            List runningTasks = ((ActivityManager) this.context
                    .getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1);
            if (runningTasks != null
                    && runningTasks.size() > 0
                    && ((RunningTaskInfo) runningTasks.get(0)).numActivities > 1
                    && Framework.getClassNotFoundCallback() != null) {
                if (intent.getComponent() == null) {
                    intent.setClassName(this.context, str);
                }
                Framework.getClassNotFoundCallback().returnIntent(intent);
            }
            log.warn("Could not find activity class: " + str);
            log.warn("Redirect to welcome activity: " + str2);
            newActivity = this.mBase.newActivity(classLoader, str2, intent);
        }
        if ((classLoader instanceof DelegateClassLoader)
                && AtlasHacks.ContextThemeWrapper_mResources != null) {
            AtlasHacks.ContextThemeWrapper_mResources.set(newActivity,
                    RuntimeVariables.getDelegateResources());
        }
        return newActivity;
    }

    @Override
	public void callActivityOnCreate(Activity activity, Bundle bundle) {
        if (RuntimeVariables.androidApplication.getPackageName().equals(
                activity.getPackageName())) {
            ContextImplHook contextImplHook = new ContextImplHook(
                    activity.getBaseContext(), activity.getClass()
                    .getClassLoader());
            if (!(AtlasHacks.ContextThemeWrapper_mBase == null || AtlasHacks.ContextThemeWrapper_mBase
                    .getField() == null)) {
                AtlasHacks.ContextThemeWrapper_mBase.set(activity,
                        contextImplHook);
            }
            AtlasHacks.ContextWrapper_mBase.set(activity, contextImplHook);
            if (activity.getClass().getClassLoader() instanceof BundleClassLoader) {
                try {
                    ((BundleClassLoader) activity.getClass().getClassLoader())
                            .getBundle().startBundle();
                } catch (BundleException e) {
                    log.error(e.getMessage() + " Caused by: ",
                            e.getNestedException());
                }
            }
            String property = Framework.getProperty(
                    "android.taobao.atlas.welcome",
                    "com.taobao.tao.welcome.Welcome");
            if (TextUtils.isEmpty(property)) {
                property = "com.taobao.tao.welcome.Welcome";
            }
            if (activity.getClass().getName().equals(property)) {
                this.mBase.callActivityOnCreate(activity, null);
                return;
            } else {
                this.mBase.callActivityOnCreate(activity, bundle);
                return;
            }
        }
        this.mBase.callActivityOnCreate(activity, bundle);
    }

    @Override
	@TargetApi(18)
    public UiAutomation getUiAutomation() {
        return this.mBase.getUiAutomation();
    }

    @Override
	public void onCreate(Bundle bundle) {
        this.mBase.onCreate(bundle);
    }

    @Override
	public void start() {
        this.mBase.start();
    }

    @Override
	public void onStart() {
        this.mBase.onStart();
    }

    @Override
	public boolean onException(Object obj, Throwable th) {
        return this.mBase.onException(obj, th);
    }

    @Override
	public void sendStatus(int i, Bundle bundle) {
        this.mBase.sendStatus(i, bundle);
    }

    @Override
	public void finish(int i, Bundle bundle) {
        this.mBase.finish(i, bundle);
    }

    @Override
	public void setAutomaticPerformanceSnapshots() {
        this.mBase.setAutomaticPerformanceSnapshots();
    }

    @Override
	public void startPerformanceSnapshot() {
        this.mBase.startPerformanceSnapshot();
    }

    @Override
	public void endPerformanceSnapshot() {
        this.mBase.endPerformanceSnapshot();
    }

    @Override
	public void onDestroy() {
        this.mBase.onDestroy();
    }

    @Override
	public Context getContext() {
        return this.mBase.getContext();
    }

    @Override
	public ComponentName getComponentName() {
        return this.mBase.getComponentName();
    }

    @Override
	public Context getTargetContext() {
        return this.mBase.getTargetContext();
    }

    @Override
	public boolean isProfiling() {
        return this.mBase.isProfiling();
    }

    @Override
	public void startProfiling() {
        this.mBase.startProfiling();
    }

    @Override
	public void stopProfiling() {
        this.mBase.stopProfiling();
    }

    @Override
	public void setInTouchMode(boolean z) {
        this.mBase.setInTouchMode(z);
    }

    @Override
	public void waitForIdle(Runnable runnable) {
        this.mBase.waitForIdle(runnable);
    }

    @Override
	public void waitForIdleSync() {
        this.mBase.waitForIdleSync();
    }

    @Override
	public void runOnMainSync(Runnable runnable) {
        this.mBase.runOnMainSync(runnable);
    }

    @Override
	public Activity startActivitySync(Intent intent) {
        return this.mBase.startActivitySync(intent);
    }

    @Override
	public void addMonitor(ActivityMonitor activityMonitor) {
        this.mBase.addMonitor(activityMonitor);
    }

    @Override
	public ActivityMonitor addMonitor(IntentFilter intentFilter,
                                      ActivityResult activityResult, boolean z) {
        return this.mBase.addMonitor(intentFilter, activityResult, z);
    }

    @Override
	public ActivityMonitor addMonitor(String str,
                                      ActivityResult activityResult, boolean z) {
        return this.mBase.addMonitor(str, activityResult, z);
    }

    @Override
	public boolean checkMonitorHit(ActivityMonitor activityMonitor, int i) {
        return this.mBase.checkMonitorHit(activityMonitor, i);
    }

    @Override
	public Activity waitForMonitor(ActivityMonitor activityMonitor) {
        return this.mBase.waitForMonitor(activityMonitor);
    }

    @Override
	public Activity waitForMonitorWithTimeout(ActivityMonitor activityMonitor,
                                              long j) {
        return this.mBase.waitForMonitorWithTimeout(activityMonitor, j);
    }

    @Override
	public void removeMonitor(ActivityMonitor activityMonitor) {
        this.mBase.removeMonitor(activityMonitor);
    }

    @Override
	public boolean invokeMenuActionSync(Activity activity, int i, int i2) {
        return this.mBase.invokeMenuActionSync(activity, i, i2);
    }

    @Override
	public boolean invokeContextMenuAction(Activity activity, int i, int i2) {
        return this.mBase.invokeContextMenuAction(activity, i, i2);
    }

    @Override
	public void sendStringSync(String str) {
        this.mBase.sendStringSync(str);
    }

    @Override
	public void sendKeySync(KeyEvent keyEvent) {
        this.mBase.sendKeySync(keyEvent);
    }

    @Override
	public void sendKeyDownUpSync(int i) {
        this.mBase.sendKeyDownUpSync(i);
    }

    @Override
	public void sendCharacterSync(int i) {
        this.mBase.sendCharacterSync(i);
    }

    @Override
	public void sendPointerSync(MotionEvent motionEvent) {
        this.mBase.sendPointerSync(motionEvent);
    }

    @Override
	public void sendTrackballEventSync(MotionEvent motionEvent) {
        this.mBase.sendTrackballEventSync(motionEvent);
    }

    @Override
	public Application newApplication(ClassLoader classLoader, String str,
                                      Context context) throws InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        return this.mBase.newApplication(classLoader, str, context);
    }

    @Override
	public void callApplicationOnCreate(Application application) {
        this.mBase.callApplicationOnCreate(application);
    }

    @Override
	public void callActivityOnDestroy(Activity activity) {
        this.mBase.callActivityOnDestroy(activity);
    }

    @Override
	public void callActivityOnRestoreInstanceState(Activity activity,
                                                   Bundle bundle) {
        this.mBase.callActivityOnRestoreInstanceState(activity, bundle);
    }

    @Override
	public void callActivityOnPostCreate(Activity activity, Bundle bundle) {
        this.mBase.callActivityOnPostCreate(activity, bundle);
    }

    @Override
	public void callActivityOnNewIntent(Activity activity, Intent intent) {
        this.mBase.callActivityOnNewIntent(activity, intent);
    }

    @Override
	public void callActivityOnStart(Activity activity) {
        this.mBase.callActivityOnStart(activity);
    }

    @Override
	public void callActivityOnRestart(Activity activity) {
        this.mBase.callActivityOnRestart(activity);
    }

    @Override
	public void callActivityOnResume(Activity activity) {
        this.mBase.callActivityOnResume(activity);
    }

    @Override
	public void callActivityOnStop(Activity activity) {
        this.mBase.callActivityOnStop(activity);
    }

    @Override
	public void callActivityOnSaveInstanceState(Activity activity, Bundle bundle) {
        this.mBase.callActivityOnSaveInstanceState(activity, bundle);
    }

    @Override
	public void callActivityOnPause(Activity activity) {
        this.mBase.callActivityOnPause(activity);
    }

    @Override
	public void callActivityOnUserLeaving(Activity activity) {
        this.mBase.callActivityOnUserLeaving(activity);
    }

    @Override
	public void startAllocCounting() {
        this.mBase.startAllocCounting();
    }

    @Override
	public void stopAllocCounting() {
        this.mBase.stopAllocCounting();
    }

    @Override
	public Bundle getAllocCounts() {
        return this.mBase.getAllocCounts();
    }

    @Override
	public Bundle getBinderCounts() {
        return this.mBase.getBinderCounts();
    }
}
