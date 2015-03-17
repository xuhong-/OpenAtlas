package org.osgi.service.packageadmin;

import org.osgi.framework.Bundle;

public interface PackageAdmin {
    ExportedPackage getExportedPackage(String str);

    ExportedPackage[] getExportedPackages(Bundle bundle);

    void refreshPackages(Bundle[] bundleArr);
}
