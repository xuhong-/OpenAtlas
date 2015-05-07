///**
// *  OpenAtlasForAndroid Project
//The MIT License (MIT) Copyright (OpenAtlasForAndroid) 2015 Bunny Blue,achellies
//
//Permission is hereby granted, free of charge, to any person obtaining a copy of this software
//and associated documentation files (the "Software"), to deal in the Software 
//without restriction, including without limitation the rights to use, copy, modify, 
//merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
//permit persons to whom the Software is furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all copies 
//or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
//INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
//PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
//FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
//ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//@author BunnyBlue
// * **/
//package blue.stack.openAtlas.welcome;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.widget.Button;
//import blue.stack.openAtlas.android.lifecycle.PanguActivity;
//import blue.stack.openAtlas.loader.App;
//
//import com.openAtlas.sdk.R;
//
//public class Welcome extends PanguActivity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_loader_tesst);
//
//		// Bundle-SymbolicName="com.taobao.scan.bundledemostartactivity1"
//		// Bundle-Version="1.0.0"
//		// date="2012.11.28"
//		// provider-name="插件开发商的名称"
//		// provider-url=""
//		// Bundle-Activator="com.taobao.scan.SimpleBundle"
//		// Bundle-Activity="com.taobao.scan.MainActivity"
//
//		Button btn = (Button) findViewById(R.id.btn);
//		// btn.setOnClickListener(new OnClickListener() {
//		//
//		// @Override
//		// public void onClick(View v) {
//		//
//		// Intent mIntent = null;
//		// try {
//		// mIntent = new
//		// Intent(Welcome.this,RuntimeVariables.delegateClassLoader.loadClass("com.taobao.scan.MainActivity"));
//		// } catch (ClassNotFoundException e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//		//
//		// mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		// RuntimeVariables.androidApplication.startActivity(mIntent);
//		//
//		//
//		//
//		// }
//		// });
//
//	}
//	boolean  bootApp=true;
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		if (event.getAction()==MotionEvent.ACTION_UP) {
//			if (bootApp) {
//				Intent mIntent = new Intent(Intent.ACTION_VIEW);
//				// mIntent.setPackage("com.taobao.taobao");
//				//    mIntent.setComponent(new ComponentName("com.taobao.taobao",
//				//            "com.taobao.scan.MainActivity"));
//				mIntent.setClassName(this, "com.taobao.android.gamecenter.main.GcContainerActivity");
//		
//				mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				// mIntent.setClassName("com.taobao.scan",
//				// "com.taobao.scan.MainActivity");
//				startActivity(mIntent);
//				bootApp=false;
//			}else {
//				Intent mIntent = new Intent(Intent.ACTION_VIEW);
//				// mIntent.setPackage("com.taobao.taobao");
//				//    mIntent.setComponent(new ComponentName("com.taobao.taobao",
//				//            "com.taobao.scan.MainActivity"));
//				mIntent.setClassName(this, "com.nostra13.universalimageloader.sample.activity.HomeActivity");
//		
//				mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				// mIntent.setClassName("com.taobao.scan",
//				// "com.taobao.scan.MainActivity");
//				startActivity(mIntent);
//				bootApp=true;
//			}
//		
//
//		}
//		return super.onTouchEvent(event);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.loader_tesst, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify Component parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//
//			{
//
//				Intent mIntent = null;
//				mIntent = new Intent("com.taobao.scan.MainActivity");
//
//				mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				// mIntent.setClassName("com.taobao.scan",
//				// "com.taobao.scan.MainActivity");
//				App.instaceApp.startActivity(mIntent);
//
//			}
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		//libcom_taobao_scan.so
//
//	}
//}
