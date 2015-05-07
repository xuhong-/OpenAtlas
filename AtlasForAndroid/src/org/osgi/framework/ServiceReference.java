package org.osgi.framework;

public interface ServiceReference {
    Bundle getBundle();

    Object getProperty(String str);

    String[] getPropertyKeys();

    Bundle[] getUsingBundles();
}
