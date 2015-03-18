package test.blue.stack.loader;

import org.osgi.framework.BundleException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.taobao.atlas.R;
import android.taobao.atlas.R.id;
import android.taobao.atlas.R.layout;
import android.taobao.atlas.R.menu;
import android.taobao.atlas.framework.Atlas;
import android.taobao.atlas.framework.BundleImpl;
import android.taobao.atlas.framework.Framework;
import android.taobao.atlas.runtime.RuntimeVariables;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoaderTesstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loader_tesst);

//	Bundle-SymbolicName="com.taobao.scan.bundledemostartactivity1"	
//	Bundle-Version="1.0.0"
//	date="2012.11.28"
//	provider-name="插件开发商的名称" 
//	provider-url="" 
//	Bundle-Activator="com.taobao.scan.SimpleBundle"
//	Bundle-Activity="com.taobao.scan.MainActivity"

	Button btn=(Button) findViewById(R.id.btn);
//	btn.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//
//			Intent mIntent = null;
//			try {
//				mIntent = new Intent(LoaderTesstActivity.this,RuntimeVariables.delegateClassLoader.loadClass("com.taobao.scan.MainActivity"));
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			RuntimeVariables.androidApplication.startActivity(mIntent);
//			
//		
//			
//		}
//	});
	

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
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			{
				
				
				Intent mIntent = null;
				try {
					mIntent = new Intent(LoaderTesstActivity.this,RuntimeVariables.delegateClassLoader.loadClass("com.taobao.scan.MainActivity"));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				RuntimeVariables.androidApplication.startActivity(mIntent);
				
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
