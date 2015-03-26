package com.taobao.tao.welcome;

import org.osgi.framework.BundleException;

import test.blue.stack.loader.App;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.taobao.taobao.R;

import android.taobao.atlas.framework.Atlas;
import android.taobao.atlas.framework.BundleImpl;
import android.taobao.atlas.framework.Framework;
import android.taobao.atlas.runtime.RuntimeVariables;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_tesst);

        // Bundle-SymbolicName="com.taobao.scan.bundledemostartactivity1"
        // Bundle-Version="1.0.0"
        // date="2012.11.28"
        // provider-name="插件开发商的名称"
        // provider-url=""
        // Bundle-Activator="com.taobao.scan.SimpleBundle"
        // Bundle-Activity="com.taobao.scan.MainActivity"

        Button btn = (Button) findViewById(R.id.btn);
        // btn.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        //
        // Intent mIntent = null;
        // try {
        // mIntent = new
        // Intent(Welcome.this,RuntimeVariables.delegateClassLoader.loadClass("com.taobao.scan.MainActivity"));
        // } catch (ClassNotFoundException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        //
        // mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // RuntimeVariables.androidApplication.startActivity(mIntent);
        //
        //
        //
        // }
        // });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent mIntent = new Intent(Intent.ACTION_VIEW);
        mIntent.setPackage("com.taobao.taobao");
//        mIntent.setComponent(new ComponentName("com.taobao.taobao",
//                "com.taobao.scan.MainActivity"));

try {
	Class<?> cls=RuntimeVariables.delegateClassLoader.loadClass("com.taobao.scan.MainActivity");
	mIntent.setClass(this, cls);
} catch (ClassNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
 
        // mComponent = {ComponentName@6742}
        // "ComponentInfo{com.taobao.taobao/com.taobao.taobao.scancode.gateway.activityFXXK.ScancodeGatewayActivity}"
        // mClass = {String@6750}
        // "com.taobao.taobao.scancode.gateway.activityFXXK.ScancodeGatewayActivity"
        // mPackage = {String@6751} "com.taobao.taobao"
        // shadow$_klass_ = {Class@700} "class android.content.ComponentName"
        // shadow$_monitor_ = -1442943439
        // mData = {Uri$StringUri@6743} "http://tb.cn/n/scancode"
        // mExtras = {Bundle@6744}
        // "Bundle[{callback_action=null, referrer=http://m.taobao.com/index.htm}]"
        // mPackage = {String@6745} "com.taobao.taobao"

        // try {
        // mIntent = new
        // Intent(this,RuntimeVariables.delegateClassLoader.loadClass("com.taobao.scan.MainActivity"));
        // } catch (ClassNotFoundException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // mIntent.setClassName("com.taobao.scan",
        // "com.taobao.scan.MainActivity");
        startActivity(mIntent);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.loader_tesst, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify Component parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            {

                Intent mIntent = null;
                mIntent = new Intent("com.taobao.scan.MainActivity");

                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // mIntent.setClassName("com.taobao.scan",
                // "com.taobao.scan.MainActivity");
                App.instaceApp.startActivity(mIntent);

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
