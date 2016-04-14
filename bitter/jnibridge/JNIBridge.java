/*
 * Decompiled with CFR 0_114.
 */
package bitter.jnibridge;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JNIBridge {
    static native Object invoke(long var0, Class var2, Method var3, Object[] var4);

    static native void delete(long var0);

    static Object newInterfaceProxy(long l2, Class[] arrclass) {
        return Proxy.newProxyInstance(JNIBridge.class.getClassLoader(), arrclass, new a(l2));
    }

    static void disableInterfaceProxy(Object object) {
        ((a)Proxy.getInvocationHandler(object)).a();
    }

    private static final class a
    implements InvocationHandler {
        private Object a = new Object[0];
        private long b;

        public a(long l2) {
            this.b = l2;
        }

        @Override
        public final Object invoke(Object object, Method method, Object[] arrobject) {
            object = this.a;
            synchronized (object) {
                if (this.b == 0) {
                    return null;
                }
                return JNIBridge.invoke(this.b, method.getDeclaringClass(), method, arrobject);
            }
        }

        public final void finalize() {
            Object object = this.a;
            synchronized (object) {
                if (this.b == 0) {
                    return;
                }
                JNIBridge.delete(this.b);
                return;
            }
        }

        public final void a() {
            Object object = this.a;
            synchronized (object) {
                this.b = 0;
                return;
            }
        }
    }

}

