package org.osgi.framework;

import java.util.Dictionary;

public interface ServiceRegistration {
    ServiceReference getReference();

    void setProperties(Dictionary<String, ?> dictionary);

    void unregister();
}
