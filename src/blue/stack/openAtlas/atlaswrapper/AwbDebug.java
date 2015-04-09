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
package blue.stack.openAtlas.atlaswrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.openAtlas.framework.Atlas;

import android.os.Environment;
import android.util.Log;


class AwbDebug {
    boolean a;
    private boolean b;
    private ArrayList<String> mFileList;
    private final String pluginPath;

    public AwbDebug() {
        this.b = false;
        this.a = false;
        this.mFileList = new ArrayList<String>();
        this.pluginPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/awb-debug";
        this.b = true;
    }

    public boolean init() {
        int i = 0;
        if (!this.b) {
            return false;
        }
        File file = new File(this.pluginPath);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            int length = listFiles.length;
            while (i < length) {
                File file2 = listFiles[i];
                if (file2.isFile() && file2.getName().endsWith(".so")) {
                    this.mFileList.add(file2.getAbsolutePath());
                    // "found external awb " + file2.getAbsolutePath();
                    this.a = true;
                }
                i++;
            }
        }
        return this.a;
    }

    public boolean installBundle(String str) {
        if (!this.b || this.mFileList.size() <= 0) {
            return false;
        }
        Iterator it = this.mFileList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
         
            if (str2.contains(Utils.getFileNameFromEntryName(str).substring(3))) {
                File file = new File(str2);
                String replace = Utils.getBaseFileName(file.getName()).replace("_",
                        ".");
                if (Atlas.getInstance().getBundle(replace) == null) {
                    try {
                        Atlas.getInstance().installBundle(replace, file);
                    } catch (Throwable th) {
                        Log.e("AwbDebug", "Could not install external bundle.",
                                th);
                    }
                    // "Succeed to install external bundle " + replace;
                }
                file.delete();
                return true;
            }
        }
        return false;
    }
}
