package org.osgi.framework;

import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;
import java.util.NoSuchElementException;

final class AdminPermissionCollection extends PermissionCollection {
    private static final long serialVersionUID = -7328900470853808407L;
    private boolean hasElement;

    public AdminPermissionCollection() {
        this.hasElement = false;
    }

    public void add(Permission permission) {
        if (!(permission instanceof AdminPermission)) {
            throw new IllegalArgumentException("invalid permission: "
                    + permission);
        } else if (isReadOnly()) {
            throw new SecurityException(
                    "attempt to add Component Permission to Component readonly PermissionCollection");
        } else {
            this.hasElement = true;
        }
    }

    public boolean implies(Permission permission) {
        return this.hasElement && (permission instanceof AdminPermission);
    }

    public Enumeration elements() {
        return new Enumeration() {
            private boolean more;

            {
                this.more = AdminPermissionCollection.this.hasElement;
            }

            public boolean hasMoreElements() {
                return this.more;
            }

            public Object nextElement() {
                if (this.more) {
                    this.more = false;
                    return new AdminPermission();
                }
                throw new NoSuchElementException();
            }
        };
    }
}
