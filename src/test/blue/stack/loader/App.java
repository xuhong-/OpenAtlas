package test.blue.stack.loader;

import java.lang.reflect.Field;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.os.Process;
import android.taobao.atlas.runtime.ContextImplHook;
import android.taobao.atlas.runtime.RuntimeVariables;
import android.util.Log;

import com.taobao.android.lifecycle.PanguApplication;
import com.taobao.tao.Globals;
import com.taobao.tao.atlaswrapper.AtlasInitializer;

public class App extends PanguApplication {
	// static final String[] AUTOSTART_PACKAGES;
	// static final String[] DELAYED_PACKAGES;
	// static final String[] SORTED_PACKAGES;


	static final String TAG = "TestApp";

	private String processName;


	private Context mBaseContext;
	AtlasInitializer mAtlasInitializer;
	public static App instaceApp;

	@Override
	protected void attachBaseContext(Context context) {
		super.attachBaseContext(context);
		this.mBaseContext = context;
		try {
			Field declaredField = Globals.class
					.getDeclaredField("sInstalledVersionName");
			declaredField.setAccessible(true);
			declaredField.set(null, this.mBaseContext.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0).versionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int myPid = Process.myPid();
		for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
				.getRunningAppProcesses()) {
			if (runningAppProcessInfo.pid == myPid) {
				this.processName = runningAppProcessInfo.processName;
				break;
			}
		}
		this.mAtlasInitializer = new AtlasInitializer(this, this.processName);
		this.mAtlasInitializer.injectApplication();
		// initCrashHandlerAndSafeMode(this.mBaseContext);
		this.mAtlasInitializer.init();
	}

	@Override
	public void onCreate() {

		super.onCreate();
		instaceApp = this;

		this.mAtlasInitializer.startUp();
		RuntimeVariables.setDelegateResources(getResources());
	
	}

	private PackageInfo getPackageInfo() {
		try {
			return getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (Throwable e) {
			Log.e(TAG, "Error to get PackageInfo >>>", e);
			return new PackageInfo();
		}
	}



	@Override
	public boolean bindService(Intent intent,
			ServiceConnection serviceConnection, int i) {
		return new ContextImplHook(getBaseContext(), null).bindService(intent,
				serviceConnection, i);
	}

	@Override
	public void startActivity(Intent intent) {
		// TODO Auto-generated method stub
		// super.startActivity(intent);
		new ContextImplHook(getBaseContext(), getClassLoader())
		.startActivity(intent);
	}



	@Override
	public ComponentName startService(Intent intent) {
		return new ContextImplHook(getBaseContext(), null).startService(intent);
	}







}
