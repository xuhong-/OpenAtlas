package org.osgi.framework;

import java.io.File;
import java.io.InputStream;
import java.util.Dictionary;

public interface BundleContext {
    void addBundleListener(BundleListener bundleListener);

    void addFrameworkListener(FrameworkListener frameworkListener);

    void addServiceListener(ServiceListener serviceListener);

    void addServiceListener(ServiceListener serviceListener, String str) throws InvalidSyntaxException;

    Filter createFilter(String str) throws InvalidSyntaxException;

    Bundle getBundle();

    Bundle getBundle(long j);

    Bundle[] getBundles();

    File getDataFile(String str);

    String getProperty(String str);

    Object getService(ServiceReference serviceReference);

    ServiceReference getServiceReference(String str);

    ServiceReference[] getServiceReferences(String str, String str2) throws InvalidSyntaxException;

    Bundle installBundle(String str) throws BundleException;

    Bundle installBundle(String str, InputStream inputStream) throws BundleException;

    ServiceRegistration registerService(String str, Object obj, Dictionary<String, ?> dictionary);

    ServiceRegistration registerService(String[] strArr, Object obj, Dictionary<String, ?> dictionary);

    void removeBundleListener(BundleListener bundleListener);

    void removeFrameworkListener(FrameworkListener frameworkListener);

    void removeServiceListener(ServiceListener serviceListener);

    boolean ungetService(ServiceReference serviceReference);
}
