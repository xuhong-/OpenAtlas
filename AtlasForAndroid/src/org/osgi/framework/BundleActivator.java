package org.osgi.framework;

public interface BundleActivator {
    void start(BundleContext bundleContext) throws Exception;

    void stop(BundleContext bundleContext) throws Exception;
}
