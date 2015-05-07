package org.osgi.framework;

import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;
import java.util.Hashtable;

final class PackagePermissionCollection extends PermissionCollection {
    private static final long serialVersionUID = -3350758995234427603L;
    private boolean all_allowed;
    private Hashtable permissions;

    public PackagePermissionCollection() {
        this.permissions = new Hashtable();
        this.all_allowed = false;
    }

    public void add(Permission permission) {
        if (!(permission instanceof PackagePermission)) {
            throw new IllegalArgumentException("invalid permission: "
                    + permission);
        } else if (isReadOnly()) {
            throw new SecurityException(
                    "attempt to add Component Permission to Component readonly PermissionCollection");
        } else {
            PackagePermission packagePermission = (PackagePermission) permission;
            String name = packagePermission.getName();
            PackagePermission packagePermission2 = (PackagePermission) this.permissions
                    .get(name);
            if (packagePermission2 != null) {
                int mask = packagePermission2.getMask();
                int mask2 = packagePermission.getMask();
                if (mask != mask2) {
                    this.permissions.put(name, new PackagePermission(name,
                            mask2 | mask));
                }
            } else {
                this.permissions.put(name, permission);
            }
            if (!this.all_allowed && name.equals(GetUserInfoRequest.version)) {
                this.all_allowed = true;
            }
        }
    }

    public boolean implies(Permission permission) {
        if (!(permission instanceof PackagePermission)) {
            return false;
        }
        PackagePermission packagePermission;
        int mask;
        int i;
        String name;
        String str;
        int i2;
        String str2;
        int lastIndexOf;
        PackagePermission packagePermission2 = (PackagePermission) permission;
        int mask2 = packagePermission2.getMask();
        if (this.all_allowed) {
            packagePermission = (PackagePermission) this.permissions
                    .get(GetUserInfoRequest.version);
            if (packagePermission != null) {
                mask = packagePermission.getMask() | 0;
                if ((mask & mask2) == mask2) {
                    return true;
                }
                i = mask;
                name = packagePermission2.getName();
                packagePermission = (PackagePermission) this.permissions
                        .get(name);
                if (packagePermission != null) {
                    i |= packagePermission.getMask();
                    if ((i & mask2) == mask2) {
                        return true;
                    }
                }
                mask = name.length() - 1;
                str = name;
                i2 = i;
                str2 = str;
                while (true) {
                    lastIndexOf = str2.lastIndexOf(".", mask);
                    if (lastIndexOf != -1) {
                        return false;
                    }
                    str2 = str2.substring(0, lastIndexOf + 1)
                            + GetUserInfoRequest.version;
                    packagePermission = (PackagePermission) this.permissions
                            .get(str2);
                    if (packagePermission != null) {
                        i2 |= packagePermission.getMask();
                        if ((i2 & mask2) == mask2) {
                            return true;
                        }
                    }
                    mask = lastIndexOf - 1;
                }
            }
        }
        i = 0;
        name = packagePermission2.getName();
        packagePermission = (PackagePermission) this.permissions.get(name);
        if (packagePermission != null) {
            i |= packagePermission.getMask();
            if ((i & mask2) == mask2) {
                return true;
            }
        }
        mask = name.length() - 1;
        str = name;
        i2 = i;
        str2 = str;
        while (true) {
            lastIndexOf = str2.lastIndexOf(".", mask);
            if (lastIndexOf != -1) {
                return false;
            }
            str2 = str2.substring(0, lastIndexOf + 1)
                    + GetUserInfoRequest.version;
            packagePermission = (PackagePermission) this.permissions.get(str2);
            if (packagePermission != null) {
                i2 |= packagePermission.getMask();
                if ((i2 & mask2) == mask2) {
                    return true;
                }
            }
            mask = lastIndexOf - 1;
        }
    }

    public Enumeration elements() {
        return this.permissions.elements();
    }
}
