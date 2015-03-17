package android.taobao.atlas.hack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.taobao.atlas.hack.Hack.HackDeclaration.HackAssertionException;

public class AssertionArrayException extends Exception {
    private static final long serialVersionUID = 1;
    private List<HackAssertionException> mAssertionErr;

    public AssertionArrayException(String str) {
        super(str);
        this.mAssertionErr = new ArrayList();
    }

    public void addException(HackAssertionException hackAssertionException) {
        this.mAssertionErr.add(hackAssertionException);
    }

    public void addException(List<HackAssertionException> list) {
        this.mAssertionErr.addAll(list);
    }

    public List<HackAssertionException> getExceptions() {
        return this.mAssertionErr;
    }

    public static AssertionArrayException mergeException(AssertionArrayException assertionArrayException, AssertionArrayException assertionArrayException2) {
        if (assertionArrayException == null) {
            return assertionArrayException2;
        }
        if (assertionArrayException2 == null) {
            return assertionArrayException;
        }
        AssertionArrayException assertionArrayException3 = new AssertionArrayException(assertionArrayException.getMessage() + ";" + assertionArrayException2.getMessage());
        assertionArrayException3.addException(assertionArrayException.getExceptions());
        assertionArrayException3.addException(assertionArrayException2.getExceptions());
        return assertionArrayException3;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (HackAssertionException hackAssertionException : this.mAssertionErr) {
            stringBuilder.append(hackAssertionException.toString()).append(";");
            try {
                if (hackAssertionException.getCause() instanceof NoSuchFieldException) {
                    Field[] declaredFields = hackAssertionException.getHackedClass().getDeclaredFields();
                    stringBuilder.append(hackAssertionException.getHackedClass().getName()).append(".").append(hackAssertionException.getHackedFieldName()).append(";");
                    for (Field name : declaredFields) {
                        stringBuilder.append(name.getName()).append("/");
                    }
                } else if (hackAssertionException.getCause() instanceof NoSuchMethodException) {
                    Method[] declaredMethods = hackAssertionException.getHackedClass().getDeclaredMethods();
                    stringBuilder.append(hackAssertionException.getHackedClass().getName()).append("->").append(hackAssertionException.getHackedMethodName()).append(";");
                    for (int i = 0; i < declaredMethods.length; i++) {
                        if (hackAssertionException.getHackedMethodName().equals(declaredMethods[i].getName())) {
                            stringBuilder.append(declaredMethods[i].toGenericString()).append("/");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            stringBuilder.append("@@@@");
        }
        return stringBuilder.toString();
    }
}
