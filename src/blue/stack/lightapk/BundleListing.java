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
package blue.stack.lightapk;


import java.util.List;

import android.text.TextUtils;

public class BundleListing  {
    public static final int CLASS_TYPE_ACTIVITY = 1;
    public static final int CLASS_TYPE_SERVICE = 2;
    private List<Component> bundles;

    public static class Component {
        private String name;
        private String pkgName;
        private long size;
        private String version;
        private String desc;
        private String mUrl;
        private String md5;
        private List<String> mdependency;
        private List<String> mActivitiyList;
        private List<String> mServiceList;
        private List<String> msetReceiverList;
        private List<String> mProviderList;
        private boolean m;

        public List<String> getReceivers() {
            return this.msetReceiverList;
        }

        public void setReceivers(List<String> list) {
            this.msetReceiverList = list;
        }

        public List<String> getContentProviders() {
            return this.mProviderList;
        }

        public void setContentProviders(List<String> list) {
            this.mProviderList = list;
        }

        public boolean isHasSO() {
            return this.m;
        }

        public void setHasSO(boolean z) {
            this.m = z;
        }

        public String getMd5() {
            return this.md5;
        }

        public void setMd5(String str) {
            this.md5 = str;
        }

        public String getUrl() {
            return this.mUrl;
        }

        public void setUrl(String str) {
     
            this.mUrl = str;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String str) {
            this.desc = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getPkgName() {
            return this.pkgName;
        }

        public void setPkgName(String str) {
            this.pkgName = str;
        }

        public long getSize() {
            return this.size;
        }

        public void setSize(long j) {
            this.size = j;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String str) {
            this.version = str;
        }

        public List<String> getDependency() {
            return this.mdependency;
        }

        public void setDependency(List<String> list) {
            if (list != null && list.size() > 0) {
                int i = 0;
                while (i < list.size()) {
                    if (TextUtils.isEmpty(list.get(i))) {
                        list.remove(i);
                    } else {
                        i += BundleListing.CLASS_TYPE_ACTIVITY;
                    }
                }
            }
            this.mdependency = list;
        }

        public List<String> getActivities() {
            return this.mActivitiyList;
        }

        public void setActivities(List<String> list) {
            this.mActivitiyList = list;
        }

        public List<String> getServices() {
            return this.mServiceList;
        }

        public void setServices(List<String> list) {
            this.mServiceList = list;
        }

        public boolean contains(String str, int i) {
            if (str == null) {
                return false;
            }
            if (i == BundleListing.CLASS_TYPE_ACTIVITY && this.mActivitiyList != null) {
                for (String str2 : this.mActivitiyList) {
                    if (str2 != null && str2.equals(str)) {
                        return true;
                    }
                }
            } else if (i == BundleListing.CLASS_TYPE_SERVICE && this.mServiceList != null) {
                for (String str22 : this.mServiceList) {
                    if (str22 != null && str22.equals(str)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<Component> getBundles() {
        return this.bundles;
    }

    public void setBundles(List<Component> list) {
        this.bundles = list;
    }

    public Component resolveBundle(String str, int i) {
        if (str == null) {
            return null;
        }
        if (this.bundles != null) {
            for (Component aVar : this.bundles) {
                if (aVar != null && aVar.contains(str, i)) {
                    return aVar;
                }
            }
        }
        return null;
    }
}