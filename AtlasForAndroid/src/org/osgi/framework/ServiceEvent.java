package org.osgi.framework;

import java.util.EventObject;

public class ServiceEvent extends EventObject {
    public static final int MODIFIED = 2;
    public static final int REGISTERED = 1;
    public static final int UNREGISTERING = 4;
    private static final long serialVersionUID = 8792901483909409299L;
    private final transient ServiceReference reference;
    private final transient int type;

    public ServiceEvent(int i, ServiceReference serviceReference) {
        super(serviceReference);
        this.reference = serviceReference;
        this.type = i;
    }

    public final ServiceReference getServiceReference() {
        return this.reference;
    }

    public final int getType() {
        return this.type;
    }
}
