package org.osgi.framework;

import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;
import java.util.Hashtable;

final class ServicePermissionCollection extends PermissionCollection {
    private static final long serialVersionUID = 662615640374640621L;
    private boolean all_allowed;
    private Hashtable permissions;

    public ServicePermissionCollection() {
        this.permissions = new Hashtable();
        this.all_allowed = false;
    }

    public void add(Permission permission) {
        if (!(permission instanceof ServicePermission)) {
            throw new IllegalArgumentException("invalid permission: " + permission);
        } else if (isReadOnly()) {
            throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");
        } else {
            ServicePermission servicePermission = (ServicePermission) permission;
            String name = servicePermission.getName();
            ServicePermission servicePermission2 = (ServicePermission) this.permissions.get(name);
            if (servicePermission2 != null) {
                int mask = servicePermission2.getMask();
                int mask2 = servicePermission.getMask();
                if (mask != mask2) {
                    this.permissions.put(name, new ServicePermission(name, mask2 | mask));
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
        if (!(permission instanceof ServicePermission)) {
            return false;
        }
        ServicePermission servicePermission;
        int mask;
        int i;
        String name;
        String str;
        int i2;
        String str2;
        int lastIndexOf;
        ServicePermission servicePermission2 = (ServicePermission) permission;
        int mask2 = servicePermission2.getMask();
        if (this.all_allowed) {
            servicePermission = (ServicePermission) this.permissions.get(GetUserInfoRequest.version);
            if (servicePermission != null) {
                mask = servicePermission.getMask() | 0;
                if ((mask & mask2) == mask2) {
                    return true;
                }
                i = mask;
                name = servicePermission2.getName();
                servicePermission = (ServicePermission) this.permissions.get(name);
                if (servicePermission != null) {
                    i |= servicePermission.getMask();
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
                    str2 = str2.substring(0, lastIndexOf + 1) + GetUserInfoRequest.version;
                    servicePermission = (ServicePermission) this.permissions.get(str2);
                    if (servicePermission != null) {
                        i2 |= servicePermission.getMask();
                        if ((i2 & mask2) == mask2) {
                            return true;
                        }
                    }
                    mask = lastIndexOf - 1;
                }
            }
        }
        i = 0;
        name = servicePermission2.getName();
        servicePermission = (ServicePermission) this.permissions.get(name);
        if (servicePermission != null) {
            i |= servicePermission.getMask();
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
            str2 = str2.substring(0, lastIndexOf + 1) + GetUserInfoRequest.version;
            servicePermission = (ServicePermission) this.permissions.get(str2);
            if (servicePermission != null) {
                i2 |= servicePermission.getMask();
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
