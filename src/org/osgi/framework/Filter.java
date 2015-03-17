package org.osgi.framework;

import java.util.Dictionary;

public interface Filter {
    boolean equals(Object obj);

    int hashCode();

    boolean match(Dictionary dictionary);

    boolean match(ServiceReference serviceReference);

    String toString();
}
