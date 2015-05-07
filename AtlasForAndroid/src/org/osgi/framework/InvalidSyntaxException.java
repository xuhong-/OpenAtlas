package org.osgi.framework;

public class InvalidSyntaxException extends Exception {
    private static final long serialVersionUID = -4295194420816491875L;
    private final transient String filter;

    public InvalidSyntaxException(String str, String str2) {
        super(str);
        this.filter = str2;
    }

    public final String getFilter() {
        return this.filter;
    }
}
