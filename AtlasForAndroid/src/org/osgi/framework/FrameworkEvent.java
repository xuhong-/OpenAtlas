package org.osgi.framework;

import java.util.EventObject;

public class FrameworkEvent extends EventObject {
    public static final int ERROR = 2;
    public static final int PACKAGES_REFRESHED = 4;
    public static final int STARTED = 1;
    public static final int STARTLEVEL_CHANGED = 8;
    private static final long serialVersionUID = 207051004521261705L;
    private final transient Bundle bundle;
    private final transient Throwable throwable;
    private final transient int type;

    public FrameworkEvent(int i, Object obj) {
        super(obj);
        this.type = i;
        this.bundle = null;
        this.throwable = null;
    }

    public FrameworkEvent(int i, Bundle bundle, Throwable th) {
        super(bundle);
        this.type = i;
        this.bundle = bundle;
        this.throwable = th;
    }

    public final Throwable getThrowable() {
        return this.throwable;
    }

    public final Bundle getBundle() {
        return this.bundle;
    }

    public final int getType() {
        return this.type;
    }
}
