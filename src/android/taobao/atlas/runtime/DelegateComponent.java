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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Application;
import android.taobao.atlas.log.Logger;
import android.taobao.atlas.log.LoggerFactory;

public class DelegateComponent {
    static Map<String, Application> apkApplications;
    private static Map<String, PackageLite> apkPackages;
    static final Logger log;

    static {
        log = LoggerFactory.getInstance("DelegateComponent");
        apkPackages = new ConcurrentHashMap();
        apkApplications = new HashMap();
    }

    public static PackageLite getPackage(String str) {
        return apkPackages.get(str);
    }

    public static void putPackage(String str, PackageLite packageLite) {
        apkPackages.put(str, packageLite);
    }

    public static void removePackage(String str) {
        apkPackages.remove(str);
    }

    public static String locateComponent(String str) {
        for (Entry entry : apkPackages.entrySet()) {
            if (((PackageLite) entry.getValue()).components.contains(str)) {
                return (String) entry.getKey();
            }
        }
        return null;
    }
}
