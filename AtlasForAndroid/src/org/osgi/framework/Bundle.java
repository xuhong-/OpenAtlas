package org.osgi.framework;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Dictionary;

public interface Bundle {
    public static final int ACTIVE = 32;
    public static final int INSTALLED = 2;
    public static final int RESOLVED = 4;
    public static final int STARTING = 8;
    public static final int STOPPING = 16;
    public static final int UNINSTALLED = 1;

    long getBundleId();

    Dictionary<String, String> getHeaders();

    String getLocation();

    ServiceReference[] getRegisteredServices();

    URL getResource(String str);

    ServiceReference[] getServicesInUse();

    int getState();

    boolean hasPermission(Object obj);

    void start() throws BundleException;

    void stop() throws BundleException;

    void uninstall() throws BundleException;

    void update() throws BundleException;

    void update(File file) throws BundleException;

    void update(InputStream inputStream) throws BundleException;
}
