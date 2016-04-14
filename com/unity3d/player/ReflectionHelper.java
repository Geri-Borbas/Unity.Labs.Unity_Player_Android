/*
 * Decompiled with CFR 0_114.
 */
package com.unity3d.player;

import com.unity3d.player.m;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

final class ReflectionHelper {
    protected static boolean LOG = false;
    protected static final boolean LOGV = false;
    private static a[] a = new a[4096];

    ReflectionHelper() {
    }

    private static boolean a(a a2) {
        a a3 = a[a2.hashCode() & a.length - 1];
        if (!a2.equals(a3)) {
            return false;
        }
        a2.a = a3.a;
        return true;
    }

    private static void a(a a2, Member member) {
        a2.a = member;
        ReflectionHelper.a[a2.hashCode() & ReflectionHelper.a.length - 1] = a2;
    }

    protected static Constructor getConstructorID(Class class_, String string) {
        Constructor constructor = null;
        a a2 = new a(class_, "", string);
        if (ReflectionHelper.a(a2)) {
            constructor = (Constructor)a2.a;
        } else {
            Class[] arrclass = ReflectionHelper.a(string);
            float f2 = 0.0f;
            for (Constructor constructor2 : class_.getConstructors()) {
                float f3 = ReflectionHelper.a(Void.TYPE, constructor2.getParameterTypes(), arrclass);
                if (f3 <= f2) continue;
                constructor = constructor2;
                f2 = f3;
                if (f2 == 1.0f) break;
            }
            ReflectionHelper.a(a2, constructor);
        }
        if (constructor == null) {
            throw new NoSuchMethodError("<init>" + string + " in class " + class_.getName());
        }
        return constructor;
    }

    protected static Method getMethodID(Class class_, String string, String string2, boolean bl) {
        Method method = null;
        a a2 = new a(class_, string, string2);
        if (ReflectionHelper.a(a2)) {
            method = (Method)a2.a;
        } else {
            Class[] arrclass = ReflectionHelper.a(string2);
            float f2 = 0.0f;
            while (class_ != null) {
                for (Method method2 : class_.getDeclaredMethods()) {
                    float f3;
                    if (bl != Modifier.isStatic(method2.getModifiers()) || method2.getName().compareTo(string) != 0 || (f3 = ReflectionHelper.a(method2.getReturnType(), method2.getParameterTypes(), arrclass)) <= f2) continue;
                    method = method2;
                    f2 = f3;
                    if (f2 == 1.0f) break;
                }
                if (f2 == 1.0f || class_.isPrimitive() || class_.isInterface() || class_.equals(Object.class) || class_.equals(Void.TYPE)) break;
                class_ = class_.getSuperclass();
            }
            ReflectionHelper.a(a2, method);
        }
        if (method == null) {
            Object[] arrobject = new Object[4];
            arrobject[0] = bl ? "non-static" : "static";
            arrobject[1] = string;
            arrobject[2] = string2;
            arrobject[3] = class_.getName();
            throw new NoSuchMethodError(String.format("no %s method with name='%s' signature='%s' in class L%s;", arrobject));
        }
        return method;
    }

    protected static Field getFieldID(Class class_, String string, String string2, boolean bl) {
        Field field = null;
        a a2 = new a(class_, string, string2);
        if (ReflectionHelper.a(a2)) {
            field = (Field)a2.a;
        } else {
            Class[] arrclass = ReflectionHelper.a(string2);
            float f2 = 0.0f;
            while (class_ != null) {
                for (Field field2 : class_.getDeclaredFields()) {
                    float f3;
                    if (bl != Modifier.isStatic(field2.getModifiers()) || field2.getName().compareTo(string) != 0 || (f3 = ReflectionHelper.a(field2.getType(), null, arrclass)) <= f2) continue;
                    field = field2;
                    f2 = f3;
                    if (f2 == 1.0f) break;
                }
                if (f2 == 1.0f || class_.isPrimitive() || class_.isInterface() || class_.equals(Object.class) || class_.equals(Void.TYPE)) break;
                class_ = class_.getSuperclass();
            }
            ReflectionHelper.a(a2, field);
        }
        if (field == null) {
            Object[] arrobject = new Object[4];
            arrobject[0] = bl ? "non-static" : "static";
            arrobject[1] = string;
            arrobject[2] = string2;
            arrobject[3] = class_.getName();
            throw new NoSuchFieldError(String.format("no %s field with name='%s' signature='%s' in class L%s;", arrobject));
        }
        return field;
    }

    private static float a(Class class_, Class class_2) {
        if (class_.equals(class_2)) {
            return 1.0f;
        }
        if (!class_.isPrimitive() && !class_2.isPrimitive()) {
            try {
                if (class_.asSubclass(class_2) != null) {
                    return 0.5f;
                }
            }
            catch (ClassCastException v0) {}
            try {
                if (class_2.asSubclass(class_) != null) {
                    return 0.1f;
                }
            }
            catch (ClassCastException v1) {}
        }
        return 0.0f;
    }

    private static float a(Class class_, Class[] arrclass, Class[] arrclass2) {
        if (arrclass2.length == 0) {
            return 0.1f;
        }
        if ((arrclass == null ? 0 : arrclass.length) + 1 != arrclass2.length) {
            return 0.0f;
        }
        float f2 = 1.0f;
        int n2 = 0;
        if (arrclass != null) {
            for (Class class_2 : arrclass) {
                f2 *= ReflectionHelper.a(class_2, arrclass2[n2++]);
            }
        }
        return f2 * ReflectionHelper.a(class_, arrclass2[arrclass2.length - 1]);
    }

    private static Class[] a(String arrclass) {
        Class class_;
        int[] arrn = new int[]{0};
        ArrayList<Class> serializable2 = new ArrayList<Class>();
        while (arrn[0] < arrclass.length() && (class_ = ReflectionHelper.a((String)arrclass, arrn)) != null) {
            serializable2.add(class_);
        }
        int n2 = 0;
        arrclass = new Class[serializable2.size()];
        for (Class class_2 : serializable2) {
            arrclass[n2++] = class_2;
        }
        return arrclass;
    }

    private static Class a(String object, int[] arrn) {
        while (arrn[0] < object.length()) {
            int[] arrn2 = arrn;
            int n2 = arrn2[0];
            arrn2[0] = n2 + 1;
            int n3 = object.charAt(n2);
            if (n3 == 40 || n3 == 41) continue;
            if (n3 == 76) {
                n3 = object.indexOf(59, arrn[0]);
                if (n3 == -1) break;
                object = object.substring(arrn[0], n3);
                arrn[0] = n3 + 1;
                object = object.replace('/', '.');
                try {
                    object = Class.forName((String)object);
                }
                catch (ClassNotFoundException v2) {
                    break;
                }
                return object;
            }
            if (n3 == 90) {
                return Boolean.TYPE;
            }
            if (n3 == 73) {
                return Integer.TYPE;
            }
            if (n3 == 70) {
                return Float.TYPE;
            }
            if (n3 == 86) {
                return Void.TYPE;
            }
            if (n3 == 66) {
                return Byte.TYPE;
            }
            if (n3 == 83) {
                return Short.TYPE;
            }
            if (n3 == 74) {
                return Long.TYPE;
            }
            if (n3 == 68) {
                return Double.TYPE;
            }
            if (n3 == 91) {
                return Array.newInstance(ReflectionHelper.a((String)object, arrn), 0).getClass();
            }
            m.Log(5, "! parseType; " + (char)n3 + " is not known!");
            break;
        }
        return null;
    }

    private static native Object nativeProxyInvoke(int var0, String var1, Object[] var2);

    private static native void nativeProxyFinalize(int var0);

    protected static Object newProxyInstance(int n2, Class class_) {
        return ReflectionHelper.newProxyInstance(n2, new Class[]{class_});
    }

    protected static Object newProxyInstance(final int n2, final Class[] arrclass) {
        return Proxy.newProxyInstance(ReflectionHelper.class.getClassLoader(), arrclass, new InvocationHandler(){

            @Override
            public final Object invoke(Object object, Method method, Object[] arrobject) {
                return ReflectionHelper.nativeProxyInvoke(n2, method.getName(), arrobject);
            }

            protected final void finalize() {
                try {
                    ReflectionHelper.nativeProxyFinalize(n2);
                    return;
                }
                finally {
                    super.finalize();
                }
            }
        });
    }

    private static final class a {
        private final Class b;
        private final String c;
        private final String d;
        private final int e;
        public volatile Member a;

        a(Class class_, String string, String string2) {
            this.b = class_;
            this.c = string;
            this.d = string2;
            int n2 = 527 + this.b.hashCode();
            n2 = 31 * n2 + this.c.hashCode();
            this.e = n2 = 31 * n2 + this.d.hashCode();
        }

        public final int hashCode() {
            return this.e;
        }

        public final boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (object instanceof a) {
                object = (a)object;
                if (this.e == object.e && this.d.equals(object.d) && this.c.equals(object.c) && this.b.equals(object.b)) {
                    return true;
                }
                return false;
            }
            return false;
        }
    }

}

