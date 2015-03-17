package test.blue.stack.loader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import com.taobao.android.task.Coordinator;
import com.taobao.android.task.Coordinator.TaggedRunnable;
import com.taobao.tao.Globals;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.taobao.atlas.framework.Atlas;
import android.taobao.atlas.hack.AssertionArrayException;
import android.taobao.atlas.runtime.ContextImplHook;
import android.taobao.atlas.runtime.DelegateClassLoader;
import android.taobao.atlas.runtime.RuntimeVariables;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.StatFs;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.taobao.atlas.framework.*;
public class App extends Application {
    static final String[] AUTOSTART_PACKAGES;
    static final String[] DELAYED_PACKAGES;
    static final String[] SORTED_PACKAGES;

    private static long START = 0;
    static final String TAG = "TestApp";
    private final String EXTERNAL_DIR_FOR_DEUBG_AWB="/data/local/tmp/awb";
    private boolean awbDebug;
    private ArrayList<String> awbFilePathForDebug;
    private String processName;
    private boolean resetForOverrideInstall;


    static {
        SORTED_PACKAGES = new String[]{"com.taobao.login4android", "com.taobao.taobao.home", "com.taobao.passivelocation", "com.taobao.mytaobao", "com.taobao.wangxin", "com.taobao.allspark", "com.taobao.search", "com.taobao.android.scancode", "com.taobao.android.trade", "com.taobao.taobao.cashdesk", "com.taobao.weapp", "com.taobao.taobao.alipay"};
        AUTOSTART_PACKAGES = new String[]{ "com.taobao.scan","com.taobao.login4android", "com.taobao.taobao.home", "com.taobao.mytaobao", "com.taobao.wangxin", "com.taobao.passivelocation", "com.taobao.allspark"};
        DELAYED_PACKAGES = new String[]{"com.taobao.fmagazine", "com.taobao.taobao.pluginservice", "com.taobao.legacy", "com.ut.share", "com.taobao.taobao.map", "com.taobao.android.gamecenter", "com.taobao.tongxue", "com.taobao.taobao.zxing", "com.taobao.labs"};
       // START = 0;
    }
    
    class bd implements Runnable {
        final /* synthetic */ AnonymousClass_1 a;

        bd(AnonymousClass_1 anonymousClass_1) {
            this.a = anonymousClass_1;
        }

        public void run() {
            Toast.makeText(RuntimeVariables.androidApplication, "\u68c0\u6d4b\u5230\u624b\u673a\u5b58\u50a8\u7a7a\u95f4\u4e0d\u8db3\uff0c\u4e3a\u4e0d\u5f71\u54cd\u60a8\u7684\u4f7f\u7528\u8bf7\u6e05\u7406\uff01", 1).show();
        }
    }
    class AnonymousClass_1 extends TaggedRunnable {
        final /* synthetic */ PackageInfo val$fpackageInfo;

        AnonymousClass_1(String str, PackageInfo packageInfo) {
        	  super(str);
            this.val$fpackageInfo = packageInfo;
          
        }

        public void run() {
            Throwable e;
            Map concurrentHashMap;
            Bundle bundle;
            long currentTimeMillis = System.currentTimeMillis();
            ZipFile zipFile = null;
            try {
                zipFile = new ZipFile(App.this.getApplicationInfo().sourceDir);
                try {
                    List access$000 = App.this.getBundleEntryNames(zipFile, "lib/armeabi/libcom_", ".so");
                    if (access$000 != null && access$000.size() > 0 && App.this.getAvailableInternalMemorySize() < ((long) (((access$000.size() * 2) * 1024) * 1024))) {
                        new Handler(Looper.getMainLooper()).post(new bd(this));
                    }
                    App.this.processLibsBundles(zipFile, access$000);
                    Editor edit = App.this.getSharedPreferences("atlas_configs", 0).edit();
                    edit.putInt("last_version_code", App.this.getPackageInfo().versionCode);
                    edit.putString("last_version_name", App.this.getPackageInfo().versionName);
                    edit.commit();
                    if (zipFile != null) {
                     
                            zipFile.close();
                  
                    }
                } catch (IOException e3) {
                    e = e3;
                    try {
                        Log.e(TAG, "IOException while processLibsBundles >>>", e);
                        if (zipFile != null) {
                            try {
                                zipFile.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                    } catch (Throwable th) {
                        e = th;
                        if (zipFile != null) {
                            try {
                                zipFile.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw e;
                    }
                    for (Bundle bundle2 : Atlas.getInstance().getBundles()) {
                        try {
                            ((BundleImpl) bundle2).optDexFile();
                            Atlas.getInstance().enableComponent(bundle2.getLocation());
                        } catch (Exception e5) {
                            try {
                                ((BundleImpl) bundle2).optDexFile();
                                Atlas.getInstance().enableComponent(bundle2.getLocation());
                            } catch (Throwable e6) {
                                Log.e(TAG, "Error while dexopt >>>", e6);
                            }
                        }
                    }
                   System.out.println( "Install bundles in process " + App.this.processName + " " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
                    concurrentHashMap = new ConcurrentHashMap();
                    concurrentHashMap.put(this.val$fpackageInfo.versionName, "dexopt");
                    App.this.saveAtlasInfoBySharedPreferences(concurrentHashMap);
                    System.setProperty("BUNDLES_INSTALLED", "true");
                    App.this.notifyBundleInstalled();
                    System.out.println("Install & dexopt bundles in process " + App.this.processName + " " + (System.currentTimeMillis() - currentTimeMillis) + " ms");;
                    currentTimeMillis = System.currentTimeMillis();
                    for (String str : DELAYED_PACKAGES) {
                        bundle = Atlas.getInstance().getBundle(str);
                        if (bundle == null) {
                            try {
                                ((BundleImpl) bundle).optDexFile();
                                Atlas.getInstance().enableComponent(bundle.getLocation());
                            } catch (Exception e7) {
                                try {
                                    ((BundleImpl) bundle).optDexFile();
                                    Atlas.getInstance().enableComponent(bundle.getLocation());
                                } catch (Throwable e62) {
                                    Log.e(TAG, "Error while dexopt >>>", e62);
                                }
                            }
                        }
                    }
                    System.out.println("DexOpt delayed bundles in " + (System.currentTimeMillis() - currentTimeMillis) + " ms");;
                }
            } catch (IOException e8) {
                
             
                Log.e(TAG, "IOException while processLibsBundles >>>", e8);
                if (zipFile != null) {
                    try {
						zipFile.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
                zipFile = null;
                for (Bundle bundle22 : Atlas.getInstance().getBundles()) {
                    ((BundleImpl) bundle22).optDexFile();
                    Atlas.getInstance().enableComponent(bundle22.getLocation());
                }
               System.out.println( "Install bundles in process " + App.this.processName + " " + (System.currentTimeMillis() - currentTimeMillis) + " ms");;
                concurrentHashMap = new ConcurrentHashMap();
                concurrentHashMap.put(this.val$fpackageInfo.versionName, "dexopt");
                App.this.saveAtlasInfoBySharedPreferences(concurrentHashMap);
                System.setProperty("BUNDLES_INSTALLED", "true");
                App.this.notifyBundleInstalled();
                System.out.println("Install & dexopt bundles in process " + App.this.processName + " " + (System.currentTimeMillis() - currentTimeMillis) + " ms");;
                currentTimeMillis = System.currentTimeMillis();
             //   while (r3 < r8) {
//                    bundle = Atlas.getInstance().getBundle(str);
//                    if (bundle == null) {
//                        ((BundleImpl) bundle).optDexFile();
//                        Atlas.getInstance().enableComponent(bundle.getLocation());
//                    }
//             //   }
               System.out.println( "DexOpt delayed bundles in " + (System.currentTimeMillis() - currentTimeMillis) + " ms");;
            } catch (Throwable th2) {
              //  e62 = th2;
                zipFile = null;
              
            }
            for (Bundle bundle222 : Atlas.getInstance().getBundles()) {
                if (!(bundle222 == null || App.this.contains(DELAYED_PACKAGES, bundle222.getLocation()))) {
                    ((BundleImpl) bundle222).optDexFile();
                    Atlas.getInstance().enableComponent(bundle222.getLocation());
                }
            }
           System.err.println( "Install bundles in process " + App.this.processName + " " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
            concurrentHashMap = new ConcurrentHashMap();
            concurrentHashMap.put(this.val$fpackageInfo.versionName, "dexopt");
            App.this.saveAtlasInfoBySharedPreferences(concurrentHashMap);
            System.setProperty("BUNDLES_INSTALLED", "true");
            App.this.notifyBundleInstalled();
           System.err.println( "Install & dexopt bundles in process " + App.this.processName + " " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
            currentTimeMillis = System.currentTimeMillis();
//            while (r3 < r8) {
//                bundle = Atlas.getInstance().getBundle(str);
//                if (bundle == null) {
//                    ((BundleImpl) bundle).optDexFile();
//                    Atlas.getInstance().enableComponent(bundle.getLocation());
//                }
//            }
            System.out.println("DexOpt delayed bundles in " + (System.currentTimeMillis() - currentTimeMillis) + " ms");;
        }
    }
    
    public void onCreate() {
        boolean z;
        int i = 0;
        super.onCreate();
    	RuntimeVariables.androidApplication=this;
    	RuntimeVariables.delegateResources=getResources();
    	RuntimeVariables.delegateClassLoader=new DelegateClassLoader(getClassLoader());
     long   START = System.currentTimeMillis();
        int myUid = Process.myUid();
        int myPid = Process.myPid();
        ArrayList arrayList = new ArrayList();
        for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                this.processName = runningAppProcessInfo.processName;
            }
            if (runningAppProcessInfo.uid == myUid && runningAppProcessInfo.pid != myPid) {
                if (runningAppProcessInfo.processName.equals(getPackageName() + ":safemode")) {
                    Process.killProcess(myPid);
                    return;
                }
                arrayList.add(Integer.valueOf(runningAppProcessInfo.pid));
            }
        }
        if (this.processName == null) {
            this.processName = "";
        }
        if (this.processName.equals(getPackageName() + ":safemode")) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Process.killProcess(((Integer) it.next()).intValue());
            }
            z = true;
        } else {
            z = false;
        }

        if (!z && !this.processName.contains(":watchdog")) {
           System.out.println( "Atlas safemode inited " + (System.currentTimeMillis() - START) + " ms");
            this.awbDebug = true;
        
            try {
                int i2;
                int i3;
                SharedPreferences sharedPreferences;
                CharSequence string;
                Editor edit;
                PackageInfo packageInfo;
                CharSequence string2;
                PackageInfo packageInfo2;
                Field declaredField = Globals.class.getDeclaredField("sApplication");
                declaredField.setAccessible(true);
                declaredField.set(null, this);
                declaredField = Globals.class.getDeclaredField("sClassLoader");
                declaredField.setAccessible(true);
                declaredField.set(null, Atlas.getInstance().getDelegateClassLoader());
                Properties properties = new Properties();
                properties.put("android.taobao.atlas.welcome", "com.taobao.tao.welcome.Welcome");
                properties.put("android.taobao.atlas.debug.bundles","true");
               properties.put( "osgi.auto.install.1", "com.taobao.android.scancode");
               try {
                   Atlas.getInstance().init(this, properties);
               } catch (Throwable e2) {
                   Log.e(TAG, "Could not init atlas framework !!!", e2);
               }
              System.out.println( "Atlas framework inited " + (System.currentTimeMillis() - START) + " ms");
                awbDebug=true;
                if (this.awbDebug) {
                    File file = new File(this.EXTERNAL_DIR_FOR_DEUBG_AWB);
                    file.mkdirs();
                    if (true) {//file.isDirectory()
                        i2 = 0;
//                        for (File file2 : file.listFiles()) {
//                            if (file2.isFile() && file2.getName().endsWith(".so")) {
//                                this.awbFilePathForDebug.add(file2.getAbsolutePath());
//                                System.out.println("found external awb " + file2.getAbsolutePath());
//                                i2 = 1;
//                            }
//                        }
                        if (getPackageName().equals(this.processName)) {
                            sharedPreferences = getSharedPreferences("atlas_configs", 0);
                            string = sharedPreferences.getString("isMiniPackage", "");
                            this.resetForOverrideInstall = String.valueOf(Globals.isMiniPackage()).equals(string);
                          System.out.println(  "resetForOverrideInstall = " + this.resetForOverrideInstall);
                            if (TextUtils.isEmpty(string) || this.resetForOverrideInstall) {
                                edit = sharedPreferences.edit();
                                edit.clear();
                                edit.putString("isMiniPackage", String.valueOf(Globals.isMiniPackage()));
                                edit.commit();
                            }
//                            if (!(Versions.isDebug() || isLowDevice() || !ApkUtils.isRootSystem())) {
//                                properties.put("android.taobao.atlas.publickey", "30819f300d06092a864886f70d010101050003818d00308189028181008406125f369fde2720f7264923a63dc48e1243c1d9783ed44d8c276602d2d570073d92c155b81d5899e9a8a97e06353ac4b044d07ca3e2333677d199e0969c96489f6323ed5368e1760731704402d0112c002ccd09a06d27946269a438fe4b0216b718b658eed9d165023f24c6ddaec0af6f47ada8306ad0c4f0fcd80d9b69110203010001");
//                                Atlas.getInstance().addFrameworkListener(new ah());
//                            }
                            try {
                                packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            } catch (Throwable e3) {
                                Log.e(TAG, "Error to get PackageInfo >>>", e3);
                                packageInfo = new PackageInfo();
                            }
                            sharedPreferences = getSharedPreferences("atlas_configs", 0);
                            i3 = sharedPreferences.getInt("last_version_code", 0);
                            string2 = sharedPreferences.getString("last_version_name", "");
                            
                            if (i2 != 0 || packageInfo.versionCode > i3 || ((packageInfo.versionCode == i3 && !TextUtils.equals(packageInfo.versionName, string2)) || this.resetForOverrideInstall)) {
                                properties.put("osgi.init", "true");
                                i = 1;
                            }
                        }
                  System.out.println(      "Atlas framework starting in process " + this.processName + " " + (System.currentTimeMillis() - START) + " ms");
                        Atlas.getInstance().startup();
                       System.out.println( "Atlas framework started in process " + this.processName + " " + (System.currentTimeMillis() - START) + " ms");;
                        packageInfo2 = getPackageInfo();
                        if (getPackageName().equals(this.processName) && i != 0) {
                            Coordinator.postTask(new AnonymousClass_1("ProcessBundles", packageInfo2));
                            return;
                        } else if (i == 0 && getPackageName().equals(this.processName)) {
                            Coordinator.postTask(new be(this, "ProcessBundles", packageInfo2));
                            return;
                        }
                    }
                }
                i2 = 0;
                if (getPackageName().equals(this.processName)) {
                    sharedPreferences = getSharedPreferences("atlas_configs", 0);
                    string = sharedPreferences.getString("isMiniPackage", "");
                    if (String.valueOf(Globals.isMiniPackage()).equals(string)) {
                    }
                    this.resetForOverrideInstall = String.valueOf(Globals.isMiniPackage()).equals(string);
                   System.out.println( "resetForOverrideInstall = " + this.resetForOverrideInstall);
                    edit = sharedPreferences.edit();
                    edit.clear();
                    edit.putString("isMiniPackage", String.valueOf(Globals.isMiniPackage()));
                    edit.commit();
                    properties.put("android.taobao.atlas.publickey", "30819f300d06092a864886f70d010101050003818d00308189028181008406125f369fde2720f7264923a63dc48e1243c1d9783ed44d8c276602d2d570073d92c155b81d5899e9a8a97e06353ac4b044d07ca3e2333677d199e0969c96489f6323ed5368e1760731704402d0112c002ccd09a06d27946269a438fe4b0216b718b658eed9d165023f24c6ddaec0af6f47ada8306ad0c4f0fcd80d9b69110203010001");
                 //   Atlas.getInstance().addFrameworkListener(new ah());
                    packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    sharedPreferences = getSharedPreferences("atlas_configs", 0);
                    i3 = sharedPreferences.getInt("last_version_code", 0);
                    string2 = sharedPreferences.getString("last_version_name", "");
                    properties.put("osgi.init", "true");
                    i = 1;
                }
               System.out.println( "Atlas framework starting in process " + this.processName + " " + (System.currentTimeMillis() - START) + " ms");
                try {
                    Atlas.getInstance().startup();
                } catch (Throwable e22) {
                    Log.e(TAG, "Could not start up atlas framework !!!", e22);
                }
                System.out.println("Atlas framework started in process " + this.processName + " " + (System.currentTimeMillis() - START) + " ms");
                packageInfo2 = getPackageInfo();
                if (!getPackageName().equals(this.processName)) {
                }
                if (i == 0) {
                }
            } catch (Throwable e222) {
            	e222.printStackTrace();
                Log.e(TAG, "Could not set Globals.sApplication & Globals.sClassLoader !!!", e222);
                throw new RuntimeException("Could not set Globals.sApplication & Globals.sClassLoader !!!", e222);
            }
        }
    }

    private PackageInfo getPackageInfo() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (Throwable e) {
            Log.e(TAG, "Error to get PackageInfo >>>", e);
            return new PackageInfo();
        }
    }
    private long getAvailableInternalMemorySize() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }

    public void onCreatxxxe() {
    	// TODO Auto-generated method stub
    	super.onCreate();
    	RuntimeVariables.androidApplication=this;
    	RuntimeVariables.delegateResources=getResources();
    	RuntimeVariables.delegateClassLoader=new DelegateClassLoader(getClassLoader());
//        try {
//			Atlas.getInstance().init(TestApp.this);
//		} catch (AssertionArrayException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        
     //   Atlas.getInstance().setClassNotFoundInterceptorCallback(new f());
//        Properties properties=new Properties();
//        File file = new File(getFilesDir(), "storage"  + File.separatorChar);
//        properties.put("android.taobao.atlas.storage", file.getAbsolutePath());
//        try {
//			Atlas.getInstance().startup(properties);
//		} catch (BundleException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
        long currentTimeMillis = System.currentTimeMillis();
        ZipFile zipFile = null;
  
            ZipFile zipFile2;
			try {
				zipFile2 = new ZipFile(App.this.getApplicationInfo().sourceDir);
				  List access$000 = App.this.getBundleEntryNames(zipFile2, "lib/armeabi/libcom_", ".so");
//                if (access$000 != null && access$000.size() > 0 && TestApp.this.getAvailableInternalMemorySize() < ((long) (((access$000.size() * 2) * 1024) * 1024))) {
//                    new Handler(Looper.getMainLooper()).post(new bi(this));
//                }
                App.this.processLibsBundles(zipFile2, access$000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
              
    }

    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return new ContextImplHook(getBaseContext(), null).bindService(intent, serviceConnection, i);
    }

    private List<String> getBundleEntryNames(ZipFile zipFile, String str, String str2) {
        List<String> arrayList = new ArrayList();
        try {
    //    	ZipEntry z;
            Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                String name = ((ZipEntry) entries.nextElement()).getName();
                if (name.startsWith(str) && name.endsWith(str2)) {
                    arrayList.add(name);
                }
            }
        } catch (Throwable e) {
            Log.e(TAG, "Exception while get bundles in assets or lib", e);
        }
        return arrayList;
    }

    public ComponentName startService(Intent intent) {
        return new ContextImplHook(getBaseContext(), null).startService(intent);
    }

    
    private String filterEntryName(List<String> list, String str) {
        if (list == null || str == null) {
            return null;
        }
        for (String str2 : list) {
            if (str2.contains(str)) {
                return str2;
            }
        }
        return null;
    }
    private void processLibsBundles(ZipFile zipFile, List<String> list) {
        int i = 0;
        for (int i2 = 0; i2 < SORTED_PACKAGES.length; i2++) {
            String filterEntryName = filterEntryName(list, SORTED_PACKAGES[i2].replace(".", "_"));
            if (filterEntryName != null) {
                processLibsBundle(zipFile, filterEntryName);
                list.remove(filterEntryName);
            }
        }
        for (String str : list) {
            processLibsBundle(zipFile, str);
        }
        String[] strArr = AUTOSTART_PACKAGES;
        int length = strArr.length;
        while (i < length) {
            Bundle bundle = Atlas.getInstance().getBundle(strArr[i]);
            if (bundle != null) {
                try {
                    bundle.start();
                } catch (Throwable e) {
                    Log.e(TAG, "Could not auto start bundle: " + bundle.getLocation(), e);
                }
            }
            i++;
        }
    }

    private String getBaseFileName(String str) {
        int lastIndexOf = str.lastIndexOf(".");
        return lastIndexOf > 0 ? str.substring(0, lastIndexOf) : str;
    }

    private String getFileNameFromEntryName(String str) {
        return str.substring(str.indexOf("lib/armeabi/") + "lib/armeabi/".length());
    }

    private String getPackageNameFromEntryName(String str) {
        return str.substring(str.indexOf("lib/armeabi/lib") + "lib/armeabi/lib".length(), str.indexOf(".so")).replace("_", ".");
    }
    private boolean processLibsBundle(ZipFile zipFile, String str) {
        String str2;
 
        str2 = getFileNameFromEntryName(str);
        String packageNameFromEntryName = getPackageNameFromEntryName(str);
        File file2 = new File(new File(getFilesDir().getParentFile(), "lib"), str2);
        if (Atlas.getInstance().getBundle(packageNameFromEntryName) == null) {
            try {
                if (file2.exists()) {
                    Atlas.getInstance().installBundle(packageNameFromEntryName, file2);
                } else {
                    Atlas.getInstance().installBundle(packageNameFromEntryName, zipFile.getInputStream(zipFile.getEntry(str)));
                }
         
                return true;
            } catch (Throwable th2) {
                Log.e(TAG, "Could not install bundle.", th2);
            }
        }
        return false;
    }
    

    private void saveAtlasInfoBySharedPreferences(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            SharedPreferences sharedPreferences = getSharedPreferences("atlas_configs", 0);
            if (sharedPreferences == null) {
                sharedPreferences = getSharedPreferences("atlas_configs", 0);
            }
            Editor edit = sharedPreferences.edit();
            for (String str : map.keySet()) {
                edit.putString(str, (String) map.get(str));
            }
            edit.commit();
        }
    }
    private void notifyBundleInstalled() {
        sendBroadcast(new Intent("com.taobao.taobao.action.BUNDLES_INSTALLED"));
        Atlas.getInstance().setClassNotFoundInterceptorCallback(new g());
    }
    class be extends TaggedRunnable {
        final /* synthetic */ PackageInfo a;
        final /* synthetic */ App b;

        be(App taobaoApplication, String str, PackageInfo packageInfo) {
        	super(str);
        
            this.b = taobaoApplication;
            this.a = packageInfo;
         
        }

        public void run() {
            long currentTimeMillis = System.currentTimeMillis();
            for (Bundle bundle : Atlas.getInstance().getBundles()) {
                BundleImpl bundleImpl = (BundleImpl) bundle;
                if (!bundleImpl.getArchive().isDexOpted()) {
                    try {
                        bundleImpl.optDexFile();
                    } catch (Exception e) {
                        try {
                            bundleImpl.optDexFile();
                        } catch (Throwable e2) {
                            Log.e("TaobaoApplication", "Error while dexopt >>>", e2);
                        }
                    }
                }
            }
    System.out.println(  "DexOpt delayed bundles in " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
            Map<String,String> concurrentHashMap = new ConcurrentHashMap();
            concurrentHashMap.put(this.a.versionName, "dexopt");
            App.this.saveAtlasInfoBySharedPreferences(concurrentHashMap);
            System.setProperty("BUNDLES_INSTALLED","true");
            b.notifyBundleInstalled();
        }
        private void saveAtlasInfoBySharedPreferences(Map<String, String> map) {
            if (map != null && !map.isEmpty()) {
                SharedPreferences sharedPreferences = getSharedPreferences("atlas_configs", 0);
                if (sharedPreferences == null) {
                    sharedPreferences = getSharedPreferences("atlas_configs", 0);
                }
                Editor edit = sharedPreferences.edit();
                for (String str : map.keySet()) {
                    edit.putString(str, (String) map.get(str));
                }
                edit.commit();
            }
        }
}
    

    private boolean contains(String[] strArr, String str) {
        if (strArr == null || str == null) {
            return false;
        }
        for (String str2 : strArr) {
            if (str2 != null && str2.equals(str)) {
                return true;
            }
        }
        return false;
    }
    }
