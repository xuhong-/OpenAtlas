/**OpenAtlasForAndroid Project

The MIT License (MIT) 
Copyright (c) 2015 Bunny Blue

Permission is hereby granted, free of charge, to any person obtaining a copy of this software
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
package com.openAtlas.launcher.Atlaswrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.openAtlas.bundleInfo.BundleInfoList;
import com.openAtlas.bundleInfo.BundleInfoList.BundleInfo;
import com.openAtlas.launcher.lightapk.BundleListing;

/**
 * @author BunnyBlue
 *
 */
public class BundleParser {
	public static void parser(Context mContext) {
		InputStream is;
		LinkedList<BundleInfoList.BundleInfo> bundleInfos=new LinkedList<BundleInfoList.BundleInfo>();
	ArrayList<BundleListing.Component> mComponents=new ArrayList<BundleListing.Component>();
	
		try {
			is = mContext.getAssets().open("bundle-info.json");
			int size = is.available();  

			// Read the entire asset into a local byte buffer.  
			byte[] buffer = new byte[size];  
			is.read(buffer);  
			is.close();  
			JSONArray jsonArray=new JSONArray(new String(buffer));
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject tmp=	jsonArray.optJSONObject(i);
				BundleInfo mBundleInfo=new BundleInfo();
				BundleListing.Component mComponent=new BundleListing.Component();
				mBundleInfo.bundleName=tmp.optString("pkgName");
				mBundleInfo.hasSO=tmp.optBoolean("hasSO");
				mComponent.setPkgName(mBundleInfo.bundleName);
				mComponent.setHasSO(mBundleInfo.hasSO);
				mBundleInfo.Components=new ArrayList<String>();
				JSONArray mJsonArray=tmp.optJSONArray("activities");
				for (int j = 0; j < mJsonArray.length(); j++) {
					mBundleInfo.Components.add(mJsonArray.optString(i));
					mComponent.getActivities().add(mJsonArray.optString(i));
				}
				
				JSONArray receivers=tmp.optJSONArray("receivers");
				for (int j = 0; j < mJsonArray.length(); j++) {
					mBundleInfo.Components.add(receivers.optString(i));
					mComponent.getReceivers().add(mJsonArray.optString(i));
				}

				JSONArray services=tmp.optJSONArray("services");
				for (int j = 0; j < mJsonArray.length(); j++) {
					mBundleInfo.Components.add(services.optString(i));
					mComponent.getServices().add(mJsonArray.optString(i));
				}

				JSONArray contentProviders=tmp.optJSONArray("contentProviders");
				for (int j = 0; j < mJsonArray.length(); j++) {
					mBundleInfo.Components.add(contentProviders.optString(i));
					mComponent.getContentProviders().add(mJsonArray.optString(i));
				
				}
				bundleInfos.add(mBundleInfo);
				mComponents.add(mComponent);
				//  BundleInfoList.getInstance().init(;)
			}
			BundleListing.getInstance().setBundles(mComponents);
			//BundleInfoList.getInstance().init(bundleInfos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  



	}

}
