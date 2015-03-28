/**
 *  OpenAtlasForAndroid
The MIT License (MIT) Copyright (AwbDebug) 2015 Bunny Blue,achellies

Permission is hereby granted, free of charge, to any person obtaining mApp copy of this software
and associated documentation files (the "Software"), to deal in the Software 
without restriction, including without limitation the rights to use, copy, modify, 
merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies 
or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
@author BunnyBlue
 * **/
package android.taobao.atlas.hack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {
	public static boolean sIsReflectAvailable;

	static {
		sIsReflectAvailable = true;
	}


	public static java.lang.reflect.Method getMethod(java.lang.Class<?> r2,
			java.lang.String r3, java.lang.Class<?>... r4) {
		try {
			Method r0 = r2.getDeclaredMethod(r3, r4);
			r0.setAccessible(true);
			return r0;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return null;

	}

	public static Object invokeMethod(Method method, Object obj,
			Object... objArr) {
		try {
			method.setAccessible(true);
			return method.invoke(obj, objArr);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e2) {
			e2.printStackTrace();
			return null;
		} catch (InvocationTargetException e3) {
			e3.printStackTrace();
			return null;
		}
	}

	public static Object fieldGet(Class<?> cls, Object obj, String str) {
		try {
			Field declaredField = cls.getDeclaredField(str);
			declaredField.setAccessible(true);
			return declaredField.get(obj);
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchFieldException e2) {
			e2.printStackTrace();
			return null;
		} catch (IllegalArgumentException e3) {
			e3.printStackTrace();
			return null;
		} catch (IllegalAccessException e4) {
			e4.printStackTrace();
			return null;
		}
	}

	public static Object fieldGet(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.get(obj);
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e2) {
			e2.printStackTrace();
			return null;
		} catch (IllegalAccessException e3) {
			e3.printStackTrace();
			return null;
		}
	}


	public static boolean fieldSet(java.lang.reflect.Field r2,
			java.lang.Object r3, java.lang.Object r4) {
		r2.setAccessible(true);
		try {
			r2.set(r3, r4);
			return true;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* JADX WARNING: inconsistent code. */
	/* Code decompiled incorrectly, please refer to instructions dump. */
	public static boolean fieldSet(java.lang.Class<?> r3, java.lang.Object r4,
			java.lang.String r5, java.lang.Object r6) {
		try {
			Field r1 = r3.getDeclaredField(r5);
			r1.setAccessible(true);
			r1.set(r4, r6);
			return true;
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}


	public static java.lang.reflect.Field getField(java.lang.Class<?> r2,
			java.lang.String r3) {
		try {
			Field r0 = r2.getDeclaredField(r3);
			r0.setAccessible(true);
			return r0;
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return null;
	}
}
