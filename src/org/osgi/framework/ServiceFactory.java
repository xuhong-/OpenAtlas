package org.osgi.framework;

public interface ServiceFactory {
    Object getService(Bundle bundle, ServiceRegistration serviceRegistration);

    void ungetService(Bundle bundle, ServiceRegistration serviceRegistration, Object obj);
}
