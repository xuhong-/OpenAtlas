package org.osgi.service.startlevel;

import org.osgi.framework.Bundle;

public interface StartLevel {
    int getBundleStartLevel(Bundle bundle);

    int getInitialBundleStartLevel();

    int getStartLevel();

    boolean isBundlePersistentlyStarted(Bundle bundle);

    void setBundleStartLevel(Bundle bundle, int i);

    void setInitialBundleStartLevel(int i);

    void setStartLevel(int i);
}
