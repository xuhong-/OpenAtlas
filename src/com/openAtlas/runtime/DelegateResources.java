/**
 *  OpenAtlasForAndroid Project
The MIT License (MIT) Copyright (OpenAtlasForAndroid) 2015 Bunny Blue,achellies

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
package com.openAtlas.runtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;

import com.openAtlas.framework.BundleImpl;
import com.openAtlas.framework.Framework;
import com.openAtlas.hack.AndroidHack;
import com.openAtlas.hack.AtlasHacks;
import com.openAtlas.log.Logger;
import com.openAtlas.log.LoggerFactory;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.DisplayMetrics;

public class DelegateResources extends Resources {
    static final Logger log;
    private Map<String, Integer> resIdentifierMap;

    static {
        log = LoggerFactory.getInstance("DelegateResources");
    }

    public DelegateResources(AssetManager assetManager, Resources resources) {
        super(assetManager, resources.getDisplayMetrics(), resources
                .getConfiguration());
        this.resIdentifierMap = new ConcurrentHashMap();
    }

    public static void newDelegateResources(Application application,
            Resources resources) throws Exception {
        List<Bundle> bundles = Framework.getBundles();
        if (bundles != null && !bundles.isEmpty()) {
            Resources delegateResources;
            List<String> arrayList = new ArrayList();
            arrayList.add(application.getApplicationInfo().sourceDir);
            for (Bundle bundle : bundles) {
                arrayList.add(((BundleImpl) bundle).getArchive()
                        .getArchiveFile().getAbsolutePath());
            }
            AssetManager assetManager = AssetManager.class
                    .newInstance();
            for (String str : arrayList) {
                AtlasHacks.AssetManager_addAssetPath.invoke(assetManager, str);
            }
            if (resources == null
                    || !resources.getClass().getName()
                            .equals("android.content.res.MiuiResources")) {
                delegateResources = new DelegateResources(assetManager,
                        resources);
            } else {
                Constructor declaredConstructor = Class.forName(
                        "android.content.res.MiuiResources")
                        .getDeclaredConstructor(
                                new Class[] { AssetManager.class,
                                        DisplayMetrics.class,
                                        Configuration.class });
                declaredConstructor.setAccessible(true);
                delegateResources = (Resources) declaredConstructor
                        .newInstance(new Object[] { assetManager,
                                resources.getDisplayMetrics(),
                                resources.getConfiguration() });
            }
            RuntimeVariables.setDelegateResources(delegateResources);
            AndroidHack.injectResources(application, delegateResources);
            if (log.isDebugEnabled()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("newDelegateResources [");
                for (int i = 0; i < arrayList.size(); i++) {
                    if (i > 0) {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append(arrayList.get(i));
                }
                stringBuffer.append("]");
                log.debug(stringBuffer.toString());
            }
        }
    }

    @Override
    public XmlResourceParser getLayout(int id) throws NotFoundException {
        // TODO Auto-generated method stub
        return super.getLayout(id);
    }

    @Override
    public int getIdentifier(String str, String str2, String str3) {
        int identifier = super.getIdentifier(str, str2, str3);
        if (identifier != 0) {
            return identifier;
        }
        if (str2 == null && str3 == null) {
            str = str.substring(str.indexOf("/") + 1);
            str2 = str.substring(str.indexOf(":") + 1, str.indexOf("/"));
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return 0;
        }
        List bundles = Framework.getBundles();
        if (!(bundles == null || bundles.isEmpty())) {
            for (Bundle bundle : Framework.getBundles()) {
                String location = bundle.getLocation();
                String str4 = location + ":" + str;
                if (!this.resIdentifierMap.isEmpty()
                        && this.resIdentifierMap.containsKey(str4)) {
                    int intValue = this.resIdentifierMap.get(str4)
                            .intValue();
                    if (intValue != 0) {
                        return intValue;
                    }
                }
                BundleImpl bundleImpl = (BundleImpl) bundle;
                if (bundleImpl.getArchive().isDexOpted()) {
                    ClassLoader classLoader = bundleImpl.getClassLoader();
                    if (classLoader != null) {
                        try {
                            StringBuilder stringBuilder = new StringBuilder(
                                    location);
                            stringBuilder.append(".R$");
                            stringBuilder.append(str2);
                            identifier = getFieldValueOfR(
                                    classLoader.loadClass(stringBuilder
                                            .toString()), str);
                            if (identifier != 0) {
                                this.resIdentifierMap.put(str4,
                                        Integer.valueOf(identifier));
                                return identifier;
                            }
                        } catch (ClassNotFoundException e) {
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return 0;
    }

    @Override
	public String getString(int i) throws NotFoundException {
        if (i == 33816578 || i == 262146) {
            return "Web View";
        }
        return super.getString(i);
    }

    private static int getFieldValueOfR(Class<?> cls, String str) {
        if (cls != null) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                if (declaredField != null) {
                    if (!declaredField.isAccessible()) {
                        declaredField.setAccessible(true);
                    }
                    return ((Integer) declaredField.get(null)).intValue();
                }
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e2) {
            } catch (IllegalArgumentException e3) {
            }
        }
        return 0;
    }
}
