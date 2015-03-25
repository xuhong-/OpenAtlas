package com.taobao.tao;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;

import android.text.Layout.Alignment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.taobao.android.lifecycle.PanguActivity;
import com.taobao.securityjni.StaticDataStore;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class BaseActivity extends PanguActivity {
    private final String TAG;
    private boolean disablefinishAnimation;
    private boolean isNaviActivity;
    private View mPanelTopView;

    private long resumeUptime;
    private List<Integer> sMenuItemId;

    public BaseActivity() {
        this.TAG = "BaseActivity";
        this.mPanelTopView = null;
        this.disablefinishAnimation = false;
        this.isNaviActivity = false;
    }

    @TargetApi(14)
    protected void onCreate(Bundle bundle) {
    }

    protected String getUTClassName() {
        return getClass().getName();
    }

    public void disableFinishAnimationOnce() {
        this.disablefinishAnimation = true;
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public final Activity getActivity() {
        return this;
    }

    public SharedPreferences getSharedPreferences(String str, int i) {
        return getApplicationContext().getSharedPreferences(str, i);
    }

    public File getFilesDir() {
        return getApplicationContext().getFilesDir();
    }

    public File getCacheDir() {
        return getApplicationContext().getCacheDir();
    }

    public File getDir(String str, int i) {
        return getApplicationContext().getDir(str, i);
    }

    public File getDatabasePath(String str) {
        return getApplicationContext().getDatabasePath(str);
    }

    public View getTopView() {
        return this.mPanelTopView;
    }

    protected boolean onPanelKeyDown(int i, KeyEvent keyEvent) {
        return false;
    }

}