package org.osgi.framework;

import java.util.EventObject;

public class BundleEvent extends EventObject {
    public static final int INSTALLED = 1;
    public static final int STARTED = 2;
    public static final int STOPPED = 4;
    public static final int UNINSTALLED = 16;
    public static final int UPDATED = 8;
    private transient Bundle bundle;
    private transient int type;

    public BundleEvent(int i, Bundle bundle) {
        super(bundle);
        this.bundle = bundle;
        this.type = i;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public int getType() {
        return this.type;
    }
}
