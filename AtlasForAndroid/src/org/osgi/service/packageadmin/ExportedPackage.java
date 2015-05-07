package org.osgi.service.packageadmin;

import org.osgi.framework.Bundle;

public interface ExportedPackage {
    Bundle getExportingBundle();

    Bundle[] getImportingBundles();

    String getName();

    String getSpecificationVersion();

    boolean isRemovalPending();
}
