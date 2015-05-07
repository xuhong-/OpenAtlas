package org.osgi.framework;

import java.util.EventListener;

public interface ServiceListener extends EventListener {
    void serviceChanged(ServiceEvent serviceEvent);
}
