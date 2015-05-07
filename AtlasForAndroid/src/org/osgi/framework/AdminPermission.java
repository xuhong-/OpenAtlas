package org.osgi.framework;

import java.security.BasicPermission;
import java.security.Permission;
import java.security.PermissionCollection;

public final class AdminPermission extends BasicPermission {
    private static final long serialVersionUID = 7348630669480294335L;

    public AdminPermission() {
        super("AdminPermission");
    }

    public AdminPermission(String str, String str2) {
        this();
    }

    public boolean implies(Permission permission) {
        return permission instanceof AdminPermission;
    }

    public boolean equals(Object obj) {
        return obj instanceof AdminPermission;
    }

    public PermissionCollection newPermissionCollection() {
        return new AdminPermissionCollection();
    }
}
